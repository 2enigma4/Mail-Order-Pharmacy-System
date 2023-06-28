package com.cognizant.subscription.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ResponseForSuccessTest {


	ResponseForSuccess response = new ResponseForSuccess();
	@Test
	void test() {
		response.setResponseMessage("This is a message");
		
		assertEquals("This is a message",response.getResponseMessage());
	}

}
