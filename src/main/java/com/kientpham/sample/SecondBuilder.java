package com.kientpham.sample;

import org.springframework.stereotype.Component;

import com.kientpham.baseworkflow.BaseBuilder;
import com.kientpham.baseworkflow.BaseOmnibusDTO;
import com.kientpham.baseworkflow.WorkflowException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class SecondBuilder implements BaseBuilder<TransactionModel,SharedDTO>{

	@Override
	public void execute(BaseOmnibusDTO<TransactionModel, SharedDTO> omniBusDTO) throws WorkflowException {
		log.info("Second builder shared DTO:"+omniBusDTO.getSharedDTO().getAnything());
		//log.info("Second builder transaction:"+omniBusDTO.getTransaction().getInputValue());
		omniBusDTO.getSharedDTO().setAnything("set by builder 2");	
		
	}

}
