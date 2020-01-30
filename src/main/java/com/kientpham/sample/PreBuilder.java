package com.kientpham.sample;

import java.util.List;

import org.springframework.stereotype.Component;

import com.kientpham.baseworkflow.BaseBuilderPrePost;
import com.kientpham.baseworkflow.BaseOmnibusDTO;
import com.kientpham.baseworkflow.WorkflowException;

@Component
public class PreBuilder implements BaseBuilderPrePost<TransactionModel,SharedDTO>{

	@Override
	public void execute(List<TransactionModel> transactionList, BaseOmnibusDTO<TransactionModel, SharedDTO> omniBusDTO)
			throws WorkflowException {
		transactionList.get(0).setInputValue("1");
		omniBusDTO.getSharedDTO().setAnything("anything");
		
	}

	

}
