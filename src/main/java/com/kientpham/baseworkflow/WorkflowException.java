package com.kientpham.baseworkflow;

/**
 * 
 * @author FPT Software
 *
 */
public class WorkflowException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String failed = "FAILED";	

	/** give more information for custom exception */
	private String status;
	
	public String getStatus() {
		return this.status;
	}
	public void setStatus(String status) {
		this.status=status;
	}

	/**
	 * 
	 * @param errorMessage
	 * @param isCustomException
	 */
	public WorkflowException(String errorMessage, String status) {
		super(errorMessage);
		//log.info(String.format("MESSAGE: %s", errorMessage.toUpperCase(Locale.US)));
		this.status = status;
	}

	/**
	 * 
	 * @param errorMessage
	 * @param e
	 */
	public WorkflowException(String errorMessage, String status, Exception e) {
		super(errorMessage);
		//log.error(String.format("EXCEPTION: %s", e.getMessage()), e);
		this.status = status;
	}

	/**
	 * 
	 * @param errorMessage
	 * @param e
	 */
	public WorkflowException(String errorMessage, Exception e) {
		super(errorMessage);
		//log.error(String.format("EXCEPTION: %s", e.getMessage()), e);
		this.status = failed;
	}

	/**
	 * 
	 * @param errorMessage
	 * @param e
	 */
	public WorkflowException(String errorMessage) {
		super(errorMessage);
		//log.error(String.format("EXCEPTION: %s", errorMessage.toUpperCase(Locale.US)));
		this.status = failed;
	}
}
