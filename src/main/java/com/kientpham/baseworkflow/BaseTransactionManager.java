package com.kientpham.baseworkflow;

/**
 * @author trungkienbk@gmail.com
 *
 * @param <T>
 *            Generic Transaction Model.
 * @param <D>
 *            Generic OmibusDTO.
 */
public interface BaseTransactionManager<T,D> {

	/**
	 * @param transaction
	 * @param omnibusDTO
	 */
	public void saveTransaction(T transaction);

	/**
	 * @param transaction
	 * @param e
	 */
	public void updateTransactionWhenException(T transaction, WorkflowException e);	

}
