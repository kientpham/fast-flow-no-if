package com.kientpham.sample;

import org.springframework.stereotype.Component;

import com.kientpham.baseworkflow.BaseBuilder;
import com.kientpham.baseworkflow.BaseOmnibusDTO;
import com.kientpham.baseworkflow.WorkflowException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class Builder1 implements BaseBuilder<TransactionModel,SharedDTO>{

	@Override
	public void execute(BaseOmnibusDTO<TransactionModel, SharedDTO> omniBusDTO) throws WorkflowException {
		//omniBusDTO.getTransaction().setInputValue(omniBusDTO.getTransaction().getInputValue() + "More");
		log.info("ShareDTO.getAnything():"+omniBusDTO.getSharedDTO().getAnything());
		log.info("Execute builder 1 with transaction:"+omniBusDTO.getTransaction().getInputValue());
//		omniBusDTO.getSharedDTO().setAnything("builder 1");
		omniBusDTO.getTransaction().setInputValue(omniBusDTO.getTransaction().getInputValue() + "X");
		omniBusDTO.getSharedDTO().setAnything(omniBusDTO.getTransaction().getInputValue());
//		log.info("First builder shared DTO:"+omniBusDTO.getSharedDTO().getAnything());
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}

}
