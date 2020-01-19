package com.kientpham.sample;

import org.springframework.stereotype.Component;

import com.kientpham.baseworkflow.BaseBuilder;
import com.kientpham.baseworkflow.BaseOmnibusDTO;
import com.kientpham.baseworkflow.WorkflowException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class Builder2 implements BaseBuilder<TransactionModel,SharedDTO>{

	@Override
	public void execute(BaseOmnibusDTO<TransactionModel, SharedDTO> omniBusDTO) throws WorkflowException {
		log.info("Execute builder 2 with transaction:"+omniBusDTO.getTransaction().getInputValue());
		//log.info("Second builder shared DTO:"+omniBusDTO.getSharedDTO().getAnything());		
		//omniBusDTO.getSharedDTO().setAnything("builder 2");
		omniBusDTO.getSharedDTO().setAnything("builder 2: " +omniBusDTO.getTransaction().getInputValue());
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
