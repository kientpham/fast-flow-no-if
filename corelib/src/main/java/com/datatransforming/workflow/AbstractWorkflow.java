package com.datatransforming.workflow;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

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
public class AbstractWorkflow<D> {

	private AbstractBuilder<D> firstBuilder;

	private AbstractTransactionModel transactionModel;

	private D builderDTO;

	/**
	 * This is for initiate concrete builders
	 */
	// public abstract void initFactory();

	public String processRequest(List<AbstractTransactionModel> transactionList) throws ServiceException {

		AbstractBuilder<D> builder;
		for (AbstractTransactionModel transaction : transactionList) {
			try {
				builder = firstBuilder;
				while (builder != null) {
					builder.execute(transaction, builderDTO);
					builder = builder.getNextBuilder();
				}
				transaction.saveTransaction();
			} catch (ServiceException e) {
				transaction.saveWithError(e);
			}
		}

		return "Test";// message;
	}

}
