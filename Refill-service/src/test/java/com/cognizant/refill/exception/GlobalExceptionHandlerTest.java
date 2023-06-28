package com.cognizant.refill.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.naming.ServiceUnavailableException;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest
public class GlobalExceptionHandlerTest {

	@InjectMocks
	GlobalExceptionHandler globalExceptionHandler;
	
	@Test
	void invalidTokenException()
	{
		assertEquals(HttpStatus.UNAUTHORIZED, globalExceptionHandler.invalidTokenException
				(new InvalidTokenException("invalidTokenException")).getStatusCode());
	}
	
	@Test
	void subscriptionIdNotFoundException()
	{
		assertEquals(HttpStatus.NOT_FOUND, globalExceptionHandler.subscriptionNotFoundException
				(new SubscriptionNotFoundException("subscriptionIdNotFoundException")).getStatusCode());
	}
	
	@Test
	void serviceUnavailableException()
	{
		assertEquals(HttpStatus.SERVICE_UNAVAILABLE, globalExceptionHandler.serviceUnavailableException
				(new ServiceUnavailableException("serviceUnavailableException")).getStatusCode());
	}
	
	@Test
	void drugQuantityNotAvailable()
	{
		assertEquals(HttpStatus.NOT_FOUND, globalExceptionHandler.drugQuantityNotAvailable
				(new DrugQuantityNotAvailable("DrugQuantityNotAvailable")).getStatusCode());
	}
	

}
