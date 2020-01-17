package com.kientpham.baseworkflow;

public class BaseOmnibusDTO<T, D> {
	
	private T transaction;

	private D sharedDTO;
	
	public T getTransaction() {
		return transaction;
	}
	
	public void setTransaction(T transaction) {
		this.transaction=transaction;
	}
	
	public D getSharedDTO() {
		return sharedDTO;
	}
	
	public void setSharedDTO(D sharedDTO) {
		this.sharedDTO=sharedDTO;
	}
}
