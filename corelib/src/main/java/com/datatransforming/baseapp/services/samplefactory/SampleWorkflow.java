package com.datatransforming.baseapp.services.samplefactory;

import org.springframework.stereotype.Component;

import com.datatransforming.baseapp.services.model.SampleBuilderDTO;
import com.datatransforming.baseapp.services.model.SampleTransactionModel;
import com.datatransforming.workflow.MasterWorkflow;

/**
 * 
 * @author Kien Pham
 *
 */
@Component
public class SampleWorkflow extends MasterWorkflow<SampleBuilderDTO> {
	
	public SampleWorkflow() {
		this.setBuilderDTO(new SampleBuilderDTO());
		this.setTransactionModel(new SampleTransactionModel());
		FirstBuilder firstBuilder = new FirstBuilder();
		this.setFirstBuilder(firstBuilder);
		firstBuilder.setNextBuilder(new SecondBuilder());
	}

//	@Override
//	@PostConstruct
//	public void initFactory() {
//		this.setBuilderDTO(new SampleBuilderDTO());
//		this.setTransactionModel(new SampleTransactionModel());
//		FirstBuilder firstBuilder = new FirstBuilder();
//		this.setFirstBuilder(firstBuilder);
//		firstBuilder.setNextBuilder(new SecondBuilder());
//	}

}
