package com.datatransforming.workflow;

/**
 * 
 * @author Kien Pham (trungkienbk@gmail.com)
 *
 */
public abstract class AbstractTransactionModel {

	/**
	 * Save to database
	 * @throws ServiceException
	 */
	public abstract void saveTransaction() throws ServiceException;

	/**
	 * Set status, error message and other information to the transaction
	 * @param e
	 * @throws ServiceException
	 */
	public abstract void saveWithError(ServiceException e) throws ServiceException;
	
}
