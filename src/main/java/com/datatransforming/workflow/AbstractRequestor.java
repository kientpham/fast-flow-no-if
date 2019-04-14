package com.datatransforming.workflow;

import java.util.List;

import lombok.ToString;
import lombok.extern.log4j.Log4j2;

/**
 * 
 * @author Kien Pham
 * 
 *         This class is the abstract model to send request for workflow.
 *
 */
@ToString
@Log4j2
public abstract class AbstractRequestor<D> {

	public abstract List<AbstractTransactionModel> getTransactionByRequestValue() throws ServiceException;

	public abstract AbstractWorkflow<D> getWorkflow();

	/**
	 * 
	 * @return message
	 */
	public List<AbstractTransactionModel> processRequest() {
		log.info("START WORKFLOW: " + this.toString());		
		List<AbstractTransactionModel> transactionList = null;
		try {
			transactionList = this.getTransactionByRequestValue();
			AbstractWorkflow<D> workflow = this.getWorkflow();
			workflow.processRequest(transactionList);
		} catch (ServiceException e) {
			return null;
		}		
		return transactionList;
	}
}