package com.kientpham.sample;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kientpham.baseworkflow.RequestHandlerBase;
import com.kientpham.baseworkflow.WorkflowException;

@Component
public class RequestTest1 implements RequestHandlerBase<TransactionModel> {

	@Autowired
	private TestFactory testFactory;
	
	@Override
	public List<TransactionModel> processListTransactions(List<?> inputParams) throws WorkflowException {
		return null;
	}

	@Override
	public TransactionModel processTransaction(List<?> inputParams) throws WorkflowException {
		TransactionModel transaction = new TransactionModel();
		transaction.setTransactionId("1");
		transaction.setInputValue(inputParams.get(0).toString() + "-" + inputParams.get(1).toString());
		transaction.setStatus("START");
		transaction.setErrorMessage("No error yet!");		
		return testFactory.startWorkflow(transaction);
	}

}
