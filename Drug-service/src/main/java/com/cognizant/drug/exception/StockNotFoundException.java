package com.cognizant.drug.exception;

@SuppressWarnings("serial")
public class StockNotFoundException extends Exception {
	public StockNotFoundException(String message) {
		super(message);
	}
}
