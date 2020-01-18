package com.kientpham.baseworkflow;

import java.util.List;

/**
 * 
 * @author trungkienbk@gmail.com
 *
 * @param <T>
 */
public interface RequestHandlerBase<T>
{
  
  /**
   * @return
   * @throws WorkflowException
   */
  public abstract List<T> startProcessing(List<?> inputParams) throws WorkflowException;    

}
