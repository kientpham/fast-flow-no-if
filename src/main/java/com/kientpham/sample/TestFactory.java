package com.kientpham.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kientpham.baseworkflow.AbstractFactory;
import com.kientpham.baseworkflow.BaseOmnibusDTO;
import com.kientpham.baseworkflow.MasterWorkflow;
import com.kientpham.baseworkflow.WorkflowException;

@Component
public class TestFactory extends AbstractFactory<TransactionModel, SharedDTO> {
	
	@Autowired
	private TransactionManager transactionManager;
	
	@Autowired
	private Builder1 firstBuilder;

	@Autowired
	private Builder2 secondBuilder;
	
	@Autowired
	private Builder3 thirdBuilder;

	@Override
	protected MasterWorkflow<TransactionModel, SharedDTO> initiateWorkflow() {
		workflow=new MasterWorkflow<TransactionModel, SharedDTO>();
		workflow.setBaseTransactionManager(transactionManager);
		workflow.setFirstBuilder(firstBuilder);
		workflow.setNextBuilder(secondBuilder);
		workflow.setNextBuilder(thirdBuilder);
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
