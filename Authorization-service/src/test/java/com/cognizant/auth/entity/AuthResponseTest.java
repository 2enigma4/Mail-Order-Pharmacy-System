package com.cognizant.auth.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthResponseTest {

	AuthResponseEntity auth = new AuthResponseEntity();

	@Test
	void testUid() {
		auth.setUid("grace");
		assertEquals( "grace", auth.getUid());
	}
	
	@Test
	void testIsValid() {
		auth.setValid(true);
		assertEquals( true, auth.isValid());
	}

}
