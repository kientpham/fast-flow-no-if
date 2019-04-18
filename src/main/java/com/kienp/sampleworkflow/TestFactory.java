package com.kienp.sampleworkflow;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kienp.workflow.AbstractFactory;
import com.kienp.workflow.MasterWorkflow;
import com.kienp.workflow.WorkflowException;

@Component
public class TestFactory extends AbstractFactory<TransactionModel, OmnibusDTO> {

	@Autowired
	private MasterWorkflow<TransactionModel, OmnibusDTO> sampleworkflow;
	
	@Autowired
	private TransactionManager transactionManager;

	@Autowired
	private FirstBuilder firstBuilder;

	@Autowired
	private SecondBuilder secondBuilder;

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
		//sampleworkflow =new MasterWorkflow<TransactionModel, OmnibusDTO>();
		sampleworkflow.setBaseTransactionManager(transactionManager);
		sampleworkflow.setFirstBuilder(firstBuilder);
		sampleworkflow.setNextBuilder(secondBuilder);
		return sampleworkflow;
	}
}
