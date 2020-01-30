package com.kientpham.sample;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.kientpham.baseworkflow.BaseBuilder;
import com.kientpham.baseworkflow.BaseOmnibusDTO;
import com.kientpham.baseworkflow.WorkflowException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class Builder3 implements BaseBuilder<TransactionModel,SharedDTO>, Runnable{

	@Async
	@Override
	public void execute(BaseOmnibusDTO<TransactionModel, SharedDTO> omniBusDTO) throws WorkflowException {
		log.info("Execute builder 3 with transaction:"+omniBusDTO.getTransaction().getInputValue());
		log.info("ShareDTO:"+omniBusDTO.getSharedDTO().getAnything());
//		omniBusDTO.getSharedDTO().setAnything("builder 3");	
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		System.out.println("Inside : " + Thread.currentThread().getName());
		
	}

}
