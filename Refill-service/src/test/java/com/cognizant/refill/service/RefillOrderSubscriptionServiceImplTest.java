package com.cognizant.refill.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cognizant.refill.dao.RefillOrderRepository;
import com.cognizant.refill.entity.RefillOrder;
import com.cognizant.refill.entity.ValidateToken;
import com.cognizant.refill.exception.InvalidTokenException;
import com.cognizant.refill.exception.SubscriptionIdNotFoundException;
import com.cognizant.refill.restclients.AuthFeign;
import com.cognizant.refill.restclients.DrugDetailClient;
import com.cognizant.refill.restclients.SubscriptionClient;

@SpringBootTest
public class RefillOrderSubscriptionServiceImplTest {

	@Mock
	RefillOrderServiceImpl refillOrderServiceImpl;

	@InjectMocks
	RefillOrderSubscriptionServiceImpl refillOrderSubscriptionService;

	@Mock
	DrugDetailClient drugDetailClient;

	@Mock
	SubscriptionClient subscriptionClient;

	@Mock
	RefillOrderRepository refillOrderRepository;

	@Mock
	AuthFeign authFeign;

	@Test
	void getall() throws InvalidTokenException {

		ArrayList<RefillOrder> list = new ArrayList<>();
		RefillOrder refillOrder = new RefillOrder(1, LocalDate.now(), true, 1, 3, "azeem");
		list.add(refillOrder);
		ValidateToken validateToken = new ValidateToken("azeem", "azeem", true);
		ResponseEntity<ValidateToken> response = new ResponseEntity<ValidateToken>(validateToken, HttpStatus.OK);
		when(authFeign.getValidity("token")).thenReturn(response);
		when(refillOrderRepository.findAll()).thenReturn(list);
		List<RefillOrder> actual = (refillOrderSubscriptionService.getall("token"));
		assertEquals(list, actual);

	}
	
	@Test
	void getallByInvalidToken() throws InvalidTokenException {

		ArrayList<RefillOrder> list = new ArrayList<>();
		RefillOrder refillOrder = new RefillOrder(1, LocalDate.now(), true, 1, 3, "azeem");
		list.add(refillOrder);
		ValidateToken validateToken = new ValidateToken("azeem", "azeem", false);
		ResponseEntity<ValidateToken> response = new ResponseEntity<ValidateToken>(validateToken, HttpStatus.OK);
		when(authFeign.getValidity("token")).thenReturn(response);
		when(refillOrderRepository.findAll()).thenReturn((ArrayList<RefillOrder>) list);

		assertThrows(InvalidTokenException.class, () -> refillOrderSubscriptionService.getall("token"));

	}
	
	
	@Test
	void deleteBySubscriptionId() throws SubscriptionIdNotFoundException, InvalidTokenException {

		ArrayList<RefillOrder> list = new ArrayList<>();
		RefillOrder refillOrder = new RefillOrder(1, LocalDate.now(), true, 1, 3, "azeem");
		RefillOrder refillOrder2 = new RefillOrder(1, LocalDate.now(), true, 2, 3, "azeem");
		list.add(refillOrder);
		ValidateToken validateToken = new ValidateToken("azeem", "azeem", true);
		ResponseEntity<ValidateToken> response = new ResponseEntity<ValidateToken>(validateToken, HttpStatus.OK);
		when(authFeign.getValidity("token")).thenReturn(response);
		refillOrderSubscriptionService.deleteBySubscriptionId(1, "token");
		int size = refillOrderRepository.findAll().size();
		assertEquals(list.size()-1, size);

	}
	
	@Test
	void deleteBySubscriptionIdByInvalidToken() throws InvalidTokenException {

		ArrayList<RefillOrder> list = new ArrayList<>();
		RefillOrder refillOrder = new RefillOrder(1, LocalDate.now(), true, 1, 3, "azeem");
		list.add(refillOrder);
		ValidateToken validateToken = new ValidateToken("azeem", "azeem", false);
		ResponseEntity<ValidateToken> response = new ResponseEntity<ValidateToken>(validateToken, HttpStatus.OK);
		when(authFeign.getValidity("token")).thenReturn(response);
		assertThrows(InvalidTokenException.class, () -> refillOrderSubscriptionService.deleteBySubscriptionId(1, "token"));

	}


}
