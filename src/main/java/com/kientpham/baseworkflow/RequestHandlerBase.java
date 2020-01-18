package com.kientpham.baseworkflow;

import java.util.List;

public interface RequestHandlerBase<T>
{
  
  /**
   * @return
   * @throws WorkflowException
   */
  public abstract List<T> processListTransactions(List<?> inputParams) throws WorkflowException;
  
  public abstract T processTransaction(List<?> inputParams) throws WorkflowException;  

}
