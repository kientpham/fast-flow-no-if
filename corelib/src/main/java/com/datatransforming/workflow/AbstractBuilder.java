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
   * 
   * @param transaction
   * @param builderDTO
   * @throws ServiceException
   */
  public abstract void execute(AbstractTransactionModel transaction, D builderDTO) throws ServiceException;

}
