package com.kientpham.baseworkflow;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StopWatch;

/**
 * @author trungkienbk@gmail.com
 *
 * @param <T>
 *            Generic Transaction Model.
 * @param <D>
 *            Generic OmibusDTO.
 */
public class MasterWorkflow<T, D> {

	private List<BaseBuilder<T, D>> builderList;

	private BaseTransactionManager<T, D> baseTransactionManager;

	private List<BaseBuilderPrePost<T, D>> preExecuteBuilderList;

	private List<BaseBuilderPrePost<T, D>> postExecuteBuilderList;

	public void setPreExecuteBuilder(BaseBuilderPrePost<T, D> builder) {
		if (preExecuteBuilderList == null)
			preExecuteBuilderList = new ArrayList<BaseBuilderPrePost<T, D>>();

		preExecuteBuilderList.add(builder);
	}

	public void setPostExecuteBuilder(BaseBuilderPrePost<T, D> builder) {
		if (postExecuteBuilderList == null)
			postExecuteBuilderList = new ArrayList<BaseBuilderPrePost<T, D>>();

		postExecuteBuilderList.add(builder);
	}

	/**
	 * 
	 * @param baseTransaction
	 */
	public void setBaseTransactionManager(BaseTransactionManager<T, D> baseTransaction) {
		this.baseTransactionManager = baseTransaction;
	}

	/**
	 * @param builder
	 */
	public void setFirstBuilder(BaseBuilder<T, D> builder) {
		builderList = new ArrayList<BaseBuilder<T, D>>();
		builderList.add(builder);
	}

	/**
	 * @param builder
	 */
	public void setNextBuilder(BaseBuilder<T, D> builder) {
		builderList.add(builder);
	}

	/**
	 * Go through each transaction to process
	 * 
	 * @param transactionList
	 * @throws ServiceException
	 */
	public List<T> executeWorkflow(List<T> transactionList, BaseOmnibusDTO<T, D> baseOmniBusDTO)
			throws WorkflowException {
		StopWatch watch = new StopWatch();
		watch.start();
		checkConditions(transactionList);
		preExecute(transactionList, baseOmniBusDTO);
		mainTransactionProcessing(transactionList, baseOmniBusDTO);
		postExecute(transactionList, baseOmniBusDTO);
		watch.stop();
		System.out.println("Total time: " + watch.getTotalTimeMillis());
		return transactionList;
	}

	private void checkConditions(List<T> transactionList) throws WorkflowException {
		if (baseTransactionManager == null || builderList == null) {
			throw new WorkflowException("Missing Transacion Manager or Builder List.");
		}
		if (transactionList == null || transactionList.size() == 0) {
			throw new WorkflowException("There is no transaction to process");
		}
	}

	private void mainTransactionProcessing(List<T> transactionList, BaseOmnibusDTO<T, D> baseOmniBusDTO) {
		if (transactionList.size() == 1) {
			this.execute(transactionList.get(0), baseOmniBusDTO);
		} else {
			transactionList.parallelStream().forEach(t -> execute(t, baseOmniBusDTO));
		}
	}

	private void preExecute(List<T> transactionList, BaseOmnibusDTO<T, D> omniBusDTO) throws WorkflowException {
		if (preExecuteBuilderList != null) {
			for (BaseBuilderPrePost<T, D> builder : preExecuteBuilderList) {
				builder.execute(transactionList, omniBusDTO);
			}
		}
	}

	private void execute(T transaction, BaseOmnibusDTO<T, D> omniBusDTO) {
		try {
			BaseOmnibusDTO<T, D> transOmniBusDTO = new BaseOmnibusDTO<T, D>();
			transOmniBusDTO.setSharedDTO(omniBusDTO.getSharedDTO());
			transOmniBusDTO.setTransaction(transaction);
			for (BaseBuilder<T, D> builder : builderList) {
				builder.execute(transOmniBusDTO);
			}
		} catch (WorkflowException e) {
			baseTransactionManager.updateTransactionWhenException(transaction, e);
		} finally {
			baseTransactionManager.saveTransaction(transaction);
		}
	}

	private void postExecute(List<T> transactionList, BaseOmnibusDTO<T, D> omniBusDTO) throws WorkflowException {
		if (postExecuteBuilderList != null) {
			for (BaseBuilderPrePost<T, D> builder : postExecuteBuilderList) {
				builder.execute(transactionList, omniBusDTO);
			}
		}
	}
}
