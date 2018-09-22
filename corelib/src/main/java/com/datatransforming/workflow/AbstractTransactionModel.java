package com.datatransforming.workflow;

public abstract class AbstractTransactionModel {

	public abstract void saveTransaction() throws ServiceException;

	public abstract void saveWithError(ServiceException e) throws ServiceException;
	
}
