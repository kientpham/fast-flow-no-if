package com.kienp.workflow;

import java.util.List;

import lombok.ToString;
import lombok.extern.log4j.Log4j2;

/**
 * @author Kien Pham - trungkienbk@gmail.com
 *
 * @param <T>
 *            Generic Transaction Model.
 * @param <D>
 *            Generic OmibusDTO.
 */
@ToString
@Log4j2
public abstract class AbstractRequest<T, D> {
	/**
	 * @return
	 * @throws WorkflowException
	 */
	protected abstract List<T> getTransactionModel() throws WorkflowException;

	/**
	 * @return
	 */
	protected abstract MasterWorkflow<T, D> initiateWorkflow();

	/**
	 * 
	 * @return message
	 */
	public List<T> processRequest() {
		try {
			log.info(String.format("START Process request: %s", this.toString()));
			List<T> transactionList = this.getTransactionModel();
			MasterWorkflow<T, D> workflow = this.initiateWorkflow();
			workflow.executeWorkflow(transactionList);
			return transactionList;
		} catch (WorkflowException e) {
			return null;
		}
	}
}