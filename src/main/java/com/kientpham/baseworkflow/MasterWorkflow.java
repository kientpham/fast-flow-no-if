package com.kientpham.baseworkflow;

import java.util.ArrayList;
import java.util.List;

/**
 * @author FPT Software
 *
 * @param <T>
 *            Generic Transaction Model.
 * @param <D>
 *            Generic OmibusDTO.
 */
public class MasterWorkflow<T,D> {

	private List<BaseBuilder<T,D>> builderList;

	private BaseTransactionManager<T,D> baseTransactionManager;		
		
	private List<BaseBuilder<T,D>> preExecuteBuilderList;
	
	public void setPreExecuteBuilder(BaseBuilder<T,D> builder) {
		if (preExecuteBuilderList==null)
			preExecuteBuilderList=new ArrayList<BaseBuilder<T,D>>();
		preExecuteBuilderList.add(builder);
	}

	/**
	 * 
	 * @param baseTransaction
	 */
	public void setBaseTransactionManager(BaseTransactionManager<T,D> baseTransaction) {
		this.baseTransactionManager = baseTransaction;
	}

	/**
	 * @param builder
	 */
	public void setFirstBuilder(BaseBuilder<T,D> builder) {
		builderList = new ArrayList<BaseBuilder<T,D>>();
		builderList.add(builder);
	}

	/**
	 * @param builder
	 */
	public void setNextBuilder(BaseBuilder<T,D> builder) {
		builderList.add(builder);
	}

	/**
	 * Go through each transaction to process
	 * 
	 * @param transactionList
	 * @throws ServiceException
	 */
	public List<T> executeWorkflow(List<T> transactionList,BaseOmnibusDTO<T,D> baseOmniBusDTO) throws WorkflowException {
		if (baseTransactionManager == null || builderList == null) {
			throw new WorkflowException("Could not excute the work flow");
		}	

		preExecute(baseOmniBusDTO);		
		execute(transactionList,baseOmniBusDTO);
		return transactionList;
	}

	private void execute(List<T> transactionList, BaseOmnibusDTO<T,D> omniBusDTO) {
		for (T transaction : transactionList) {		
			try {			
				omniBusDTO.setTransaction(transaction);
				for (BaseBuilder<T,D> builder : builderList) {					
					builder.execute(omniBusDTO);
				}
			} catch (WorkflowException e) {
				baseTransactionManager.updateTransactionWhenException(transaction, e);
			} finally {
				baseTransactionManager.saveTransaction(transaction);
			}
		}
	}

	private void preExecute(BaseOmnibusDTO<T,D> omniBusDTO) throws WorkflowException {
		if (preExecuteBuilderList!=null) {
			for (BaseBuilder<T,D> builder : preExecuteBuilderList) {
				builder.execute(omniBusDTO);
			}
		}
	}

}
