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
		transaction.setInputValue(inputParams.get(0).toString() +" trans 1");
		transaction.setStatus("START");
		transaction.setErrorMessage("No error yet!");
		listTransactionModel.add(transaction);
		TransactionModel transaction2 = new TransactionModel();
		transaction2.setTransactionId("2");
		transaction2.setInputValue(inputParams.get(0).toString() + " trans 2");
		transaction2.setStatus("START");
		transaction2.setErrorMessage("No error yet!");
		listTransactionModel.add(transaction2);
		return testFactory.startWorkflow(listTransactionModel);
	}
}
