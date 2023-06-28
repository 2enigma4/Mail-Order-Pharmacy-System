package com.cognizant.drug.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.cognizant.drug.entity.ValidateToken;

@SpringBootTest
public class ValidateTokenTest {

	ValidateToken token1 = new ValidateToken();
	ValidateToken token2 = new ValidateToken("Uid",true);
	
	@Test
	void testUid() {
		token1.setUid("Uid");
		assertEquals( "Uid", token1.getUid());
	}

	@Test
	void testIsValid() {
		token1.setValid(true);
		assertEquals( true, token1.isValid());
	}
	
	@Test
	void testToString() {
		String str = token1.toString();
		assertEquals(token1.toString(), str);
	}


}
