package com.cognizant.auth.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.cognizant.auth.dao.UserDAO;
import com.cognizant.auth.entity.AuthResponseEntity;
import com.cognizant.auth.entity.UserData;
import com.cognizant.auth.service.CustomerDetailsService;
import com.cognizant.auth.service.JwtUtil;

@SpringBootTest
public class AuthControllerTest {
	
	@InjectMocks
	AuthController authController;
	
	AuthResponseEntity authResponse;
	
	UserDetails userdetails;
	
	@Mock
	JwtUtil jwtutil=new JwtUtil();
	
	@Mock
	CustomerDetailsService custdetailservice = new CustomerDetailsService();
	
	@Mock
	UserDAO userservice;
	
	@Test
	public void loginTest() {
		UserData user = new UserData("grace","grace",null);
		System.out.println(custdetailservice.loadUserByUsername("grace"));
		UserDetails loadUserByUsername = custdetailservice.loadUserByUsername("grace");
		UserDetails value = new User(user.getUserid(),user.getUpassword(),new ArrayList<>());
		when(custdetailservice.loadUserByUsername("grace")).thenReturn(value);
		when(jwtutil.generateToken(loadUserByUsername)).thenReturn("token");
		ResponseEntity<?> login = authController.login(user);
		assertEquals(200, login.getStatusCodeValue());
	}

	@Test
	public void loginTestFailed() {

		UserData user = new UserData("grace", "grace",null);
		UserDetails loadUserByUsername = custdetailservice.loadUserByUsername("grace");
		UserDetails value = new User(user.getUserid(), "grace11", new ArrayList<>());
		when(custdetailservice.loadUserByUsername("grace")).thenReturn(value);
		when(jwtutil.generateToken(loadUserByUsername)).thenReturn("token");
		ResponseEntity<?> login = authController.login(user);
		assertEquals( 403, login.getStatusCodeValue());
	}

	@Test
	public void validateTestValidtoken() {

		when(jwtutil.validateToken("token")).thenReturn(true);
		when(jwtutil.extractUsername("token")).thenReturn("grace");
		UserData user1 = new UserData("grace", "grace", "grace");
		Optional<UserData> data = Optional.of(user1);
		when(userservice.findById("grace")).thenReturn(data);
		ResponseEntity<?> validity = authController.getValidity("bearer token");
		assertEquals( true, validity.getBody().toString().contains("true"));

	}

	@Test
	public void validateTestInValidtoken() {

		
		when(jwtutil.validateToken("token")).thenReturn(false);
		ResponseEntity<?> validity = authController.getValidity("bearer token");
		assertEquals( true, validity.getBody().toString().contains("false"));
	}

	
}
