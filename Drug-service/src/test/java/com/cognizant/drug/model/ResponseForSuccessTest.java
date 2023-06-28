package com.cognizant.drug.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.cognizant.drug.entity.ResponseForSuccess;

@SpringBootTest
public class ResponseForSuccessTest {

	@BeforeEach
	void setUp() throws Exception {
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		ResponseForSuccess successResponseOne = new ResponseForSuccess();
		successResponseOne.setResponseMessage("Success");
		assertEquals("Success", successResponseOne.getResponseMessage());
	}
	
	@Test
	void testAllArgs() {
		ResponseForSuccess successResponseOne = new ResponseForSuccess("Failure");
		assertEquals("Failure", successResponseOne.getResponseMessage());
	}

}
