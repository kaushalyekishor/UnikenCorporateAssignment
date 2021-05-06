package com.uniken.corporate.bean;

public class Corporate {

	private int id;
	private String name;
	private String accountNumber;
	
	
	public Corporate(int id, String name, String accountNumber) {
		super();
		this.id = id;
		this.name = name;
		this.accountNumber = accountNumber;
	}
	
	public Corporate(String name, String accountNumber) {
		super();
		this.name = name;
		this.accountNumber = accountNumber;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	
	
}