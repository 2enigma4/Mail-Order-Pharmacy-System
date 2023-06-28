package com.cognizant.auth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import com.cognizant.auth.dao.UserDAO;
import com.cognizant.auth.entity.UserData;

@SpringBootTest
public class ServiceTest {

	UserDetails userdetails;
	
	@InjectMocks
	CustomerDetailsService custdetailservice;

	@Mock
	UserDAO userservice;

	@Test
	 void loadUserByUsernameTest() {
		
		UserData user1=new UserData("grace","grace",null);
		Optional<UserData> data =Optional.of(user1) ;
		when(userservice.findById("grace")).thenReturn(data);
		UserDetails loadUserByUsername2 = custdetailservice.loadUserByUsername("grace");
		assertEquals(user1.getUserid(),loadUserByUsername2.getUsername());
	}

}
