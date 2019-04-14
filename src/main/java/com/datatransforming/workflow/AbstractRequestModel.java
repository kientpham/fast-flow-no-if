package com.datatransforming.workflow;

import java.util.List;

import lombok.ToString;

/**
 * 
 * @author Kien Pham
 * 
 *         This class is the abstract model to send request for workflow.
 *
 */
@ToString
public abstract class AbstractRequestModel<D> {

	public abstract List<AbstractTransactionModel> getTransactionByRequestValue() throws ServiceException;

	public abstract MasterWorkflow<D> getWorkflow();

	public String processRequest() {
		try {

			List<AbstractTransactionModel> transactionList = this.getTransactionByRequestValue();
			MasterWorkflow<D> workflow = this.getWorkflow();
			workflow.processRequest(transactionList);
		} catch (ServiceException e) {

		}
		return "";
	}

}
