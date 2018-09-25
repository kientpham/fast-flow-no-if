package com.datatransforming.workflow;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Kien Pham
 *
 * @param <T>
 */
@Getter
@Setter
public abstract class AbstractBuilder<D>
{
  private AbstractBuilder<D> nextBuilder;  

  /**
   * This function is to build output package
   * 
   * @param inputDTO
   * @throws DdmfToDdpsException
   */
  public abstract void execute(AbstractTransactionModel transaction, D builderDTO) throws ServiceException;

}
