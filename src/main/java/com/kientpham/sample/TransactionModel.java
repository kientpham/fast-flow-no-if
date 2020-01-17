package com.kientpham.sample;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionModel {

	private String transactionId;
	
	private String inputValue;
	
	private String status;
	
	private String errorMessage;
	
}
