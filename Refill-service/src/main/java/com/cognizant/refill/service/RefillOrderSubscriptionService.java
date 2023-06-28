package com.cognizant.refill.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cognizant.refill.entity.RefillOrder;
import com.cognizant.refill.exception.InvalidTokenException;


/** Interface which have the methods of service class */
@Service
public interface RefillOrderSubscriptionService {

	
	

	public List<RefillOrder> getall(String token) throws InvalidTokenException;
	/**
	 * @param subscriptionId
	 * @param token
	 * @throws InvalidTokenException
	 */
	public void deleteBySubscriptionId(long subscriptionId, String token) throws InvalidTokenException;

}
