package com.kienp.sampleworkflow;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kienp.workflow.AbstractRequest;
import com.kienp.workflow.MasterWorkflow;
import com.kienp.workflow.WorkflowException;

import lombok.Setter;

@Setter
@Component
public class TestRequest extends AbstractRequest<TransactionModel, OmnibusDTO> {

	private String requestValue1;

	private String requestValue2;

	@Autowired
	private MasterWorkflow<TransactionModel, OmnibusDTO> sampleWorkflow;

	@Autowired
	private TransactionManager transactionManager;

	@Autowired
	private FirstBuilder firstBuilder;

	@Autowired
	private SecondBuilder secondBuilder;

	@Override
	protected List<TransactionModel> getTransactionModel() throws WorkflowException {
		TransactionModel transaction = new TransactionModel();
		transaction.setTransactionId("1");
		transaction.setInputValue(requestValue1 + "-" + requestValue2);
		transaction.setStatus("START");
		transaction.setErrorMessage("No error yet!");
		return Collections.singletonList(transaction);
	}

	@Override
	protected MasterWorkflow<TransactionModel, OmnibusDTO> initiateWorkflow() {
		sampleWorkflow.setBaseTransactionManager(transactionManager);
		sampleWorkflow.setNextBuilder(firstBuilder);
		sampleWorkflow.setNextBuilder(secondBuilder);
		return sampleWorkflow;
	}

}
