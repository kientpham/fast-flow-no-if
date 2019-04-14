package com.datatransforming.baseapp.services.samplefactory;

import com.datatransforming.baseapp.services.model.SampleBuilderDTO;
import com.datatransforming.workflow.AbstractBuilder;
import com.datatransforming.workflow.AbstractTransactionModel;
import com.datatransforming.workflow.ServiceException;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class FirstBuilder extends AbstractBuilder<SampleBuilderDTO>{

	@Override
	public void execute(AbstractTransactionModel transaction, SampleBuilderDTO builderDTO) throws ServiceException {
		log.info("First builder: "+transaction.toString());
		builderDTO.setInputValue(transaction.toString());
		
		
	}



}
