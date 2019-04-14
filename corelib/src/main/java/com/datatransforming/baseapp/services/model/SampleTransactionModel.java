package com.datatransforming.baseapp.services.model;

import com.datatransforming.workflow.AbstractTransactionModel;
import com.datatransforming.workflow.ServiceException;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SampleTransactionModel extends AbstractTransactionModel {

	private String inputValue;
	
	@Override
	public void saveTransaction() throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveWithError(ServiceException e) throws ServiceException {
		// TODO Auto-generated method stub
		
	}

}
