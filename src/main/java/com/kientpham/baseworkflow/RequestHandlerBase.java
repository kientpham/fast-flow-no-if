package com.kientpham.baseworkflow;

import java.util.List;

public interface RequestHandlerBase<T>
{
  
  /**
   * @return
   * @throws WorkflowException
   */
  public abstract List<T> processRequest(List<?> inputParams) throws WorkflowException;
  

}
