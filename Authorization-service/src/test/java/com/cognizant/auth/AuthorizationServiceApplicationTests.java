package com.cognizant.auth;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthorizationServiceApplicationTests {

	@Test
	void contextLoads() {
	}

	
	void main() {
		AuthorizationServiceApplication.main(new String[] {});
		assertTrue(true);
	}
}
