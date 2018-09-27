package com.datatransforming.workflow;

/**
 * 
 * @author Kien Pham
 *
 * @param <T>
 */
public abstract class AbstractBuilder<D>
{
  private AbstractBuilder<D> nextBuilder;  
  
  public void setNextBuilder(AbstractBuilder<D> nextBuilder){
	  this.nextBuilder=nextBuilder;
  }
  
  public AbstractBuilder<D> getNextBuilder(){
	  return this.nextBuilder;
  }

  /**
   * 
   * @param transaction
   * @param builderDTO
   * @throws ServiceException
   */
  public abstract void execute(AbstractTransactionModel transaction, D builderDTO) throws ServiceException;

}
