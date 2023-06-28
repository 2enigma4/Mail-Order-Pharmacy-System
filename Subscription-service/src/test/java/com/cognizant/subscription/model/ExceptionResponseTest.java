package com.cognizant.subscription.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest
public class ExceptionResponseTest {


	@Test
	void test() {
		
		
		
		String str = "2019-03-04 11:30";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
		
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		ExceptionResponse anotherExceptionResponse = new ExceptionResponse("message",dateTime,HttpStatus.OK);
		exceptionResponse.setMessge("message");
		exceptionResponse.setTimestamp(dateTime);
		exceptionResponse.setStatus(HttpStatus.OK);
		
		assertEquals(anotherExceptionResponse.getMessge(),exceptionResponse.getMessge());
		assertEquals(anotherExceptionResponse.getTimestamp(), exceptionResponse.getTimestamp());
		assertEquals(anotherExceptionResponse.getStatus(),exceptionResponse.getStatus());
	}

}

