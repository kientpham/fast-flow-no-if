package com.datatransforming.workflow;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

/**
 * This class is to run the common process for all factories
 * 
 * @author Kien Pham
 *
 * @param <T>
 *            is for transaction of each factory
 */
@Getter
@Setter
@Log4j2
public abstract class AbstractWorkflow<D> {

	private AbstractBuilder<D> firstBuilder;

	private AbstractTransactionModel transaction;

	private D builderDTO;

	/**
	 * This is for initiate concrete builders
	 */
	public abstract void initFactory();

	public String processRequest(AbstractRequestModel requestModel) throws ServiceException {

		String message = String.format("START process request $1s%", requestModel.toString());		
		log.info(message);

		List<AbstractTransactionModel> transactionList = requestModel.getTransactionByRequestValue();
		for (AbstractTransactionModel transaction : transactionList) {
			this.executeProcess(transaction);
		}
		
		message = "END process request successfully: " + requestModel.toString();
		log.info(message);
		return message;
	}

	public void executeProcess(AbstractTransactionModel transaction) throws ServiceException {
		try {
			AbstractBuilder<D> builder = firstBuilder;
			while (builder != null) {
				builder.execute(transaction, builderDTO);
				builder = builder.getNextBuilder();
			}			
			transaction.saveTransaction();
		} catch (ServiceException e) {
			transaction.saveWithError(e);
		}
	}
}
