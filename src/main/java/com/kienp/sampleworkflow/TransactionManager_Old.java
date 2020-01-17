package com.kienp.sampleworkflow;

import org.springframework.stereotype.Component;

import com.kienp.workflow.BaseTransactionManager;
import com.kienp.workflow.WorkflowException;

@Component
public class TransactionManager_Old implements BaseTransactionManager<TransactionModel, OmnibusDTO>{

	@Override
	public OmnibusDTO initiateOmnibusDTO(TransactionModel transaction) {
		OmnibusDTO omnibusDTO=new OmnibusDTO();
		omnibusDTO.setAnything(transaction.getInputValue());
		return omnibusDTO;
	}

	@Override
	public void saveTransaction(TransactionModel transaction, OmnibusDTO omnibusDTO) {
		// TODO save to DB
	}

	@Override
	public void updateTransactionWhenException(TransactionModel transaction, WorkflowException e) {
		transaction.setStatus(e.getStatus());
		transaction.setErrorMessage(e.getMessage());		
	}

}
