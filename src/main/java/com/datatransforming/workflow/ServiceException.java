package com.datatransforming.workflow;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ServiceException extends Exception
{

  /**
   * serialVersionUID for serializable class
   */
  private static final long serialVersionUID = 1L;

  /**
   * Exception constructor with Throwable as parameter
   * 
   * @param e
   */
  public ServiceException(Throwable e)
  {
    super(e);
    log.error(e.getMessage());
  }

  /**
   * Exception constructor with message and Throwable as parameter
   * 
   * @param message
   * @param e
   */
  public ServiceException(String message, Throwable e)
  {
    super(message, e);
    log.error(message + e.getMessage());
  }

  /**
   * Exception constructor with message as parameter
   * 
   * @param message
   */
  public ServiceException(String message)
  {
    super(message);
    log.error(message);
  }

}
