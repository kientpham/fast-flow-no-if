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
public abstract class AbstractFactory<T, D> {
	
	protected MasterWorkflow<T, D> workflow ;
	
	/**
	 * @return
	 * @throws WorkflowException
	 */
	protected abstract List<T> getTransactionModel(List<?> inputList) throws WorkflowException;

	/**
	 * @return
	 */
	protected abstract MasterWorkflow<T, D> initiateWorkflow();

	/**
	 * 
	 * @return message
	 */
	public List<T> processRequest(List<?> inputList) {
		try {
			log.info(String.format("START Process request: %s", this.toString()));
			List<T> transactionList = this.getTransactionModel(inputList);			
			workflow = this.initiateWorkflow();
			workflow.executeWorkflow(transactionList);
			return transactionList;
		} catch (WorkflowException e) {
			return null;
		}
	}
}