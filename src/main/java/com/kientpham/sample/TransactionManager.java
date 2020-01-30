package com.kientpham.sample;

import org.springframework.stereotype.Component;

import com.kientpham.baseworkflow.BaseTransactionManager;
import com.kientpham.baseworkflow.WorkflowException;

@Component
public class TransactionManager implements BaseTransactionManager<TransactionModel, SharedDTO>{

//	@Override
//	public SharedDTO initiateOmnibusDTO(TransactionModel transaction) {
//		SharedDTO omnibusDTO=new SharedDTO();
//		omnibusDTO.setAnything(transaction.getInputValue());
//		return omnibusDTO;
//	}


	@Override
	public void saveTransaction(TransactionModel transaction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTransactionWhenException(TransactionModel transaction, WorkflowException e) {
		transaction.setStatus(e.getStatus());
		transaction.setErrorMessage(e.getMessage());		
		
	}

}
