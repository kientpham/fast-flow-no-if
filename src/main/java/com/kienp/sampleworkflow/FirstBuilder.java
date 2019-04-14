package com.kienp.sampleworkflow;

import org.springframework.stereotype.Component;

import com.kienp.workflow.BaseBuilder;
import com.kienp.workflow.WorkflowException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class FirstBuilder implements BaseBuilder<OmnibusDTO>{

	@Override
	public void execute(OmnibusDTO omnibusDTO) throws WorkflowException {
		log.info("First builder: "+omnibusDTO.getAnything());
		omnibusDTO.setAnything("another thing");
	}

}
