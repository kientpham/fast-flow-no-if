package com.kientpham.sample;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kientpham.baseworkflow.AbstractFactory;
import com.kientpham.baseworkflow.BaseOmnibusDTO;
import com.kientpham.baseworkflow.MasterWorkflow;
import com.kientpham.baseworkflow.WorkflowException;

@Component
public class TestFactory extends AbstractFactory<TransactionModel, SharedDTO> {

//	@Autowired
//	private MasterWorkflow<TransactionModel, SharedDTO> sampleworkflow;
	
	@Autowired
	private TransactionManager transactionManager;
	
	@Autowired
	private FirstBuilder firstBuilder;

	@Autowired
	private SecondBuilder secondBuilder;

//	@Override
//	protected List<TransactionModel> getTransactionModel(List<?> inputList) throws WorkflowException {
//		TransactionModel transaction = new TransactionModel();
//		transaction.setTransactionId("1");
//		transaction.setInputValue(inputList.get(0).toString() + "-" + inputList.get(1).toString());
//		transaction.setStatus("START");
//		transaction.setErrorMessage("No error yet!");
//		return Collections.singletonList(transaction);
//	}

	@Override
	protected MasterWorkflow<TransactionModel, SharedDTO> initiateWorkflow() {
		workflow=new MasterWorkflow<TransactionModel, SharedDTO>();
		workflow.setBaseTransactionManager(transactionManager);
		workflow.setFirstBuilder(firstBuilder);
		workflow.setNextBuilder(secondBuilder);
		return workflow;
	}

	@Override
	protected BaseOmnibusDTO<TransactionModel, SharedDTO> initiateBaseOmnibusDTO() throws WorkflowException {
		BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO=new BaseOmnibusDTO<TransactionModel, SharedDTO>();  
		SharedDTO sharedDTO=new SharedDTO();
		sharedDTO.setAnything("Initiating share DTO");
		omnibusDTO.setSharedDTO(sharedDTO);		
		return omnibusDTO;
	}
}
