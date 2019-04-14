package com.kienp.workflow;

import java.util.Locale;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

/**
 * 
 * @author Kien Pham - trungkienbk@gmail.com
 *
 */
@Getter
@Log4j2
public class WorkflowException extends Exception {

	private final String failed = "FAILED";

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/** give more information for custom exception */
	private final String status;

	/**
	 * 
	 * @param errorMessage
	 * @param isCustomException
	 */
	public WorkflowException(String errorMessage, String status) {
		super(errorMessage);
		log.info(String.format("MESSAGE: %s", errorMessage.toUpperCase(Locale.US)));
		this.status = status;
	}

	/**
	 * 
	 * @param errorMessage
	 * @param e
	 */
	public WorkflowException(String errorMessage, String status, Exception e) {
		super(errorMessage);
		log.error(String.format("EXCEPTION: %s", e.getMessage()), e);
		this.status = status;
	}

	/**
	 * 
	 * @param errorMessage
	 * @param e
	 */
	public WorkflowException(String errorMessage, Exception e) {
		super(errorMessage);
		log.error(String.format("EXCEPTION: %s", e.getMessage()), e);
		this.status = failed;
	}

	/**
	 * 
	 * @param errorMessage
	 * @param e
	 */
	public WorkflowException(String errorMessage) {
		super(errorMessage);
		log.error(String.format("EXCEPTION: %s", errorMessage.toUpperCase(Locale.US)));
		this.status = failed;
	}
}
