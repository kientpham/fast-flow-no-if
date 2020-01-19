package com.kientpham.sample;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kientpham.baseworkflow.RequestHandlerBase;
import com.kientpham.baseworkflow.WorkflowException;

@Component
public class RequestTest2 implements RequestHandlerBase<TransactionModel> {

	@Autowired
	private TestFactory testFactory;
	
	@Override
	public List<TransactionModel> startProcessing(List<?> inputParams) throws WorkflowException {
		List<TransactionModel> listTransactionModel = new ArrayList<TransactionModel>();
		TransactionModel transaction = new TransactionModel();
		transaction.setTransactionId("1");
		transaction.setInputValue(" trans 1");
		transaction.setStatus("START");
		transaction.setErrorMessage("No error yet!");
		listTransactionModel.add(transaction);
		TransactionModel transaction2 = new TransactionModel();
		transaction2.setTransactionId("2");
		transaction2.setInputValue(" trans 2");
		transaction2.setStatus("START");
		transaction2.setErrorMessage("No error yet!");		
		listTransactionModel.add(transaction2);
		
		TransactionModel transaction3 = new TransactionModel();
		transaction3.setTransactionId("3");
		transaction3.setInputValue(" trans 3");
		transaction3.setStatus("START");
		transaction3.setErrorMessage("No error yet!");		
		listTransactionModel.add(transaction3);
		
		TransactionModel transaction4 = new TransactionModel();
		transaction4.setTransactionId("4");
		transaction4.setInputValue(" trans 4");
		transaction4.setStatus("START");
		transaction4.setErrorMessage("No error yet!");		
		listTransactionModel.add(transaction4);
		
		TransactionModel transaction5 = new TransactionModel();
		transaction5.setTransactionId("5");
		transaction5.setInputValue(" trans 5");
		transaction5.setStatus("START");
		transaction5.setErrorMessage("No error yet!");		
		listTransactionModel.add(transaction5);
		
		return testFactory.startWorkflow(listTransactionModel);
	}
}
