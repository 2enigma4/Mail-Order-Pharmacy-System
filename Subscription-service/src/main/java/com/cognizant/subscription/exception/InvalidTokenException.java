package com.cognizant.subscription.exception;

@SuppressWarnings("serial")
public class InvalidTokenException extends Exception {

	public InvalidTokenException()
	{
		
	}
	public InvalidTokenException(String message)
	{
		super(message);
	}
}
