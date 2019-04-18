package com.kienp.workflow;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * @author Kien Pham - trungkienbk@gmail.com
 *
 * @param <T>
 *            Generic Transaction Model.
 * @param <D>
 *            Generic OmibusDTO.
 */
@Component
public class MasterWorkflow<T, D> {

	private List<BaseBuilder<D>> builderList;

	private BaseTransactionManager<T, D> baseTransaction;

	/**
	 * 
	 * @param baseTransaction
	 */
	public void setBaseTransactionManager(BaseTransactionManager<T, D> baseTransaction) {
		this.baseTransaction = baseTransaction;
	}

	/**
	 * @param builder
	 */
	public void setFirstBuilder(BaseBuilder<D> builder) {
		builderList = new ArrayList<BaseBuilder<D>>();
		builderList.add(builder);
	}

	/**
	 * @param builder
	 */
	public void setNextBuilder(BaseBuilder<D> builder) {
		builderList.add(builder);
	}

	/**
	 * Go through each transaction to process
	 * 
	 * @param transactionList
	 * @throws ServiceException
	 */
	public void executeWorkflow(List<T> transactionList) throws WorkflowException {
		if (baseTransaction == null || builderList == null) {
			throw new WorkflowException("Could not excute the work flow");
		}
		for (T transaction : transactionList) {
			D omnibusDTO = baseTransaction.initiateOmnibusDTO(transaction);
			try {
				for (BaseBuilder<D> builder : builderList) {
					builder.execute(omnibusDTO);
				}
			} catch (WorkflowException e) {
				baseTransaction.updateTransactionWhenException(transaction, e);
			} finally {
				baseTransaction.saveTransaction(transaction, omnibusDTO);
			}
		}
	}

}
