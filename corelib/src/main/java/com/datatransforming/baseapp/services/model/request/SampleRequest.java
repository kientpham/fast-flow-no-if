package com.datatransforming.baseapp.services.model.request;

import java.util.ArrayList;
import java.util.List;

import com.datatransforming.baseapp.services.model.SampleBuilderDTO;
import com.datatransforming.baseapp.services.model.SampleTransactionModel;
import com.datatransforming.baseapp.services.samplefactory.SampleWorkflow;
import com.datatransforming.workflow.*;

import lombok.ToString;

@ToString
public class SampleRequest extends AbstractRequestModel<SampleBuilderDTO> {

	//Test
	private String requestValue;

	public SampleRequest(String requestValue) {
		this.requestValue = requestValue;
	}

	@Override
	public List<AbstractTransactionModel> getTransactionByRequestValue() throws ServiceException {

		SampleTransactionModel transaction = new SampleTransactionModel();
		transaction.setInputValue(requestValue);
		List<AbstractTransactionModel> listTransaction = new ArrayList<AbstractTransactionModel>();
		listTransaction.add(transaction);
		return listTransaction;
	}

	@Override
	public MasterWorkflow<SampleBuilderDTO> getWorkflow() {
		return new SampleWorkflow();
	}
}
