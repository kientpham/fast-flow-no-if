package com.kienp.sampleworkflow;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kienp.workflow.AbstractFactory;
import com.kienp.workflow.MasterWorkflow;
import com.kienp.workflow.WorkflowException;

@Component
public class TestFactory_Old extends AbstractFactory<TransactionModel, OmnibusDTO> {

	@Autowired
	private MasterWorkflow<TransactionModel, OmnibusDTO> sampleworkflow;
	
	@Autowired
	private TransactionManager_Old transactionManager;

	@Autowired
	private FirstBuilder_Old firstBuilder;

	@Autowired
	private SecondBuilder_Old secondBuilder;

	@Override
	protected List<TransactionModel> getTransactionModel(List<?> inputList) throws WorkflowException {
		TransactionModel transaction = new TransactionModel();
		transaction.setTransactionId("1");
		transaction.setInputValue(inputList.get(0).toString() + "-" + inputList.get(1).toString());
		transaction.setStatus("START");
		transaction.setErrorMessage("No error yet!");
		return Collections.singletonList(transaction);
	}

	@Override
	protected MasterWorkflow<TransactionModel, OmnibusDTO> initiateWorkflow() {
		workflow=new MasterWorkflow<TransactionModel, OmnibusDTO>();
		workflow.setBaseTransactionManager(transactionManager);
		workflow.setFirstBuilder(firstBuilder);
		workflow.setNextBuilder(secondBuilder);
		return workflow;
	}
}
