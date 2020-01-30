package com.kientpham.sample;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kientpham.baseworkflow.WorkflowException;

@RestController
public class Controller {

	@Autowired
	private RequestTest1 requestTest1;
	
	@Autowired
	private RequestTest2 requestTest2;

	@RequestMapping("/test2")
	public String index() {

		
		List<String> inputValues = Arrays.asList("test 2");		
		List<TransactionModel> transactionList;
		StringBuilder sb = new StringBuilder();
		try {
			transactionList = requestTest2.startProcessing(inputValues);
			
			for (TransactionModel transaction : transactionList) {
				sb.append(" " + transaction.getTransactionId());
			}
		} catch (WorkflowException e) {
			e.printStackTrace();
			return "There is error while processing: " + e.getMessage();
		}

		return "Process Successfully transactionId:" + sb.toString();
	}
	
	@RequestMapping("/")
	public String index1() {

		List<String> inputValues = Arrays.asList("test 1", "Test 3");
		TransactionModel trans=new TransactionModel();
		try {
			trans=requestTest1.startProcessing(inputValues).get(0);			
			
		} catch (WorkflowException e) {
			e.printStackTrace();
			return "There is error while processing: " + e.getMessage();
		}

		return "Process Successfully transactionId:" + trans.getTransactionId();
	}
}
