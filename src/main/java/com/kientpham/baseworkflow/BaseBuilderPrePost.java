package com.kientpham.baseworkflow;

import java.util.List;

/**
 * @author trungkienbk@gmail.com
 *
 * @param <D>
 *            Generic OmnibusDTO.
 */
public interface BaseBuilderPrePost<T,D> {
	/**
	 * 
	 * @param transaction
	 * @param omnibusDTO
	 * @throws ServiceException
	 */	
	public abstract void execute(List<T> transactionList, BaseOmnibusDTO<T,D> omniBusDTO) throws WorkflowException;
	
}
