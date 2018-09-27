package com.datatransforming.workflow;

import java.util.List;

/**
 * This class is to run the common process for all factories
 * 
 * @author Kien Pham
 *
 * @param <T>
 *            is for transaction of each factory
 */
public class AbstractWorkflow<D> {

	private AbstractBuilder<D> firstBuilder;
	
	public void setFirstBuilder(AbstractBuilder<D> firstBuilder){
		this.firstBuilder=firstBuilder;
	}

	private AbstractTransactionModel transactionModel;
	
	public void setTransactionModel(AbstractTransactionModel transactionModel){
		this.transactionModel=transactionModel;
	}
	
	public AbstractTransactionModel  getTransactionModel(){
		return this.transactionModel;
	}

	private D builderDTO;
	
	public void setBuilderDTO(D builderDTO){
		this.builderDTO=builderDTO;
	}

	/**
	 * Go through each transaction to process
	 * @param transactionList
	 * @throws ServiceException
	 */
	public void processRequest(List<AbstractTransactionModel> transactionList) throws ServiceException {
		AbstractBuilder<D> builder;
		for (AbstractTransactionModel transaction : transactionList) {
			try {
				builder = firstBuilder;
				while (builder != null) {
					builder.execute(transaction, builderDTO);
					builder = builder.getNextBuilder();
				}
			} catch (ServiceException e) {
				transaction.saveWithError(e);
			} finally {
				transaction.saveTransaction();
			}
		}
	}
}
