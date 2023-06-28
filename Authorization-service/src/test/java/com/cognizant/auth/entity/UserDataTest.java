package com.cognizant.auth.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserDataTest {
	
	UserData auth = new UserData();
	UserData auth1 = new UserData("grace", "grace", "grace");


	@Test
	void testName() {
		auth.setUpassword("Name");
		assertEquals( "Name", auth.getUpassword());
	}

	@Test
	void testIsValid() {
		auth.setUserid("a");
		assertEquals("a", auth.getUserid());
	}
	
	@Test
	void testToken() {
		auth.setAuthToken("ad");
		assertEquals("ad", auth.getAuthToken());
	}
	
	@Test
	void testToString() {
		String string = auth1.toString();
		assertEquals(auth1.toString(),string);
	}


}
