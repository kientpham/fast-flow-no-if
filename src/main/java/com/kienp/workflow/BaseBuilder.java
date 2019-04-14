package com.kienp.workflow;

/**
 * @author Kien Pham - trungkienbk@gmail.com
 *
 * @param <D>
 *            Generic OmnibusDTO.
 */
public interface BaseBuilder<D> {
	/**
	 * 
	 * @param transaction
	 * @param omnibusDTO
	 * @throws ServiceException
	 */
	public abstract void execute(D omnibusDTO) throws WorkflowException;

}
