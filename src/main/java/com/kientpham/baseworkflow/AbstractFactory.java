package com.kientpham.baseworkflow;

import java.util.List;

/**
 * @author trungkienbk@gmail.com
 *
 * @param <T>
 *            Generic Transaction Model.
 * @param <D>
 *            Generic OmibusDTO.
 */

public abstract class AbstractFactory<T, D> {

	protected MasterWorkflow<T, D> workflow;

	protected abstract MasterWorkflow<T, D> initiateWorkflow();

	protected abstract BaseOmnibusDTO<T, D> initiateBaseOmnibusDTO() throws WorkflowException;

	public List<T> startWorkflow(List<T> transactions) {

		try {
			workflow = this.initiateWorkflow();
			BaseOmnibusDTO<T, D> baseOmniBusDTO = this.initiateBaseOmnibusDTO();
			return workflow.executeWorkflow(transactions, baseOmniBusDTO);
		} catch (WorkflowException e) {
			return null;
		}
	}

}