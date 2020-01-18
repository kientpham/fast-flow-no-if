package com.kientpham.sample;

import org.springframework.stereotype.Component;

import com.kientpham.baseworkflow.BaseBuilder;
import com.kientpham.baseworkflow.BaseOmnibusDTO;
import com.kientpham.baseworkflow.WorkflowException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class FirstBuilder implements BaseBuilder<TransactionModel,SharedDTO>{

	@Override
	public void execute(BaseOmnibusDTO<TransactionModel, SharedDTO> omniBusDTO) throws WorkflowException {
		log.info("First builder transaction:"+omniBusDTO.getTransaction().getInputValue());
		log.info("First builder shared DTO:"+omniBusDTO.getSharedDTO().getAnything());
		
		omniBusDTO.getSharedDTO().setAnything("set by builder 1");
		
		
	}

}
