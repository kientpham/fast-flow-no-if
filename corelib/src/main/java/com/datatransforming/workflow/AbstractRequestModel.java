package com.datatransforming.workflow;

import java.util.List;

public abstract class AbstractRequestModel {

	public abstract List<AbstractTransactionModel> getTransactionByRequestValue() throws ServiceException;	
	
	public abstract String toString();
}
