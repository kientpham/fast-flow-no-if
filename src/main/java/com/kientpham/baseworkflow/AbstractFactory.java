package com.kientpham.baseworkflow;

import java.util.List;


/**
 * @author FPT Software
 *
 * @param <T>
 *            Generic Transaction Model.
 * @param <D>
 *            Generic OmibusDTO.
 */

public abstract class AbstractFactory<T,D> {
	
	protected MasterWorkflow<T,D> workflow ;	

	protected abstract MasterWorkflow<T,D> initiateWorkflow();
	
	protected abstract BaseOmnibusDTO<T, D> initiateBaseOmnibusDTO() throws WorkflowException; 

//	/**
//	 * 
//	 * @return message
//	 */
//	public List<T> processRequest(List<?> inputParams) {
//		
//		try {					
//			workflow = this.initiateWorkflow();
//			BaseOmnibusDTO<T,D> baseOmniBusDTO=this.initiateBaseOmnibusDTO();									
//			return workflow.executeWorkflow(inputParams,baseOmniBusDTO);			
//		} catch (WorkflowException e) {
//			return null;
//		}
//	}
	
	public List<T> startWorkflow(List<T> transactions) {
		
		try {					
			workflow = this.initiateWorkflow();
			BaseOmnibusDTO<T,D> baseOmniBusDTO=this.initiateBaseOmnibusDTO();									
			return workflow.executeWorkflow(transactions,baseOmniBusDTO);			
		} catch (WorkflowException e) {
			return null;
		}
	}
}