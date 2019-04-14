package com.kienp.workflow;

/**
 * @author Kien Pham - trungkienbk@gmail.com
 *
 * @param <T>
 *            Generic Transaction Model.
 * @param <D>
 *            Generic OmibusDTO.
 */
public interface BaseTransactionManager<T, D> {
	/**
	 * @param transaction
	 * @return
	 */
	public D initiateOmnibusDTO(T transaction);

	/**
	 * @param transaction
	 * @param omnibusDTO
	 */
	public void saveTransaction(T transaction, D omnibusDTO);

	/**
	 * @param transaction
	 * @param e
	 */
	public void updateTransactionWhenException(T transaction, WorkflowException e);
}
