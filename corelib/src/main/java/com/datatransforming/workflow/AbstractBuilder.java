package com.datatransforming.workflow;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author FPT - KONG
 *
 * @param <T>
 */
@Getter
@Setter
public abstract class AbstractBuilder<D>
{
  private AbstractBuilder<D> nextBuilder;  
  
  public abstract void execute(AbstractTransactionModel transaction, D builderDTO) throws ServiceException;

}
