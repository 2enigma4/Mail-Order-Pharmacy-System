package com.cognizant.refill.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.refill.dao.RefillOrderRepository;
import com.cognizant.refill.entity.RefillOrder;
import com.cognizant.refill.exception.InvalidTokenException;
import com.cognizant.refill.restclients.AuthFeign;

import lombok.extern.slf4j.Slf4j;

/**Service class which holds the business logic*/
@Service
@Slf4j
public class RefillOrderSubscriptionServiceImpl implements RefillOrderSubscriptionService {


	@Autowired
	RefillOrderRepository refillOrderRepository;
	
	@Autowired
	private AuthFeign authFeign;

	String msg = "Invalid Credentials";

	

	
	@Override
	public List<RefillOrder> getall(String token) throws InvalidTokenException {
		//get all refill order subscriptions
		log.info("inside getall method");

		if (authFeign.getValidity(token).getBody().isValid()) {
			return refillOrderRepository.findAll();
		} else
			throw new InvalidTokenException(msg);
	}

	/**
	 * @param subscriptionId
	 * @param token
	 * @throws InvalidTokenException
	 */
	@Override
	@Transactional
	public void deleteBySubscriptionId(long subscriptionId, String token) throws InvalidTokenException {
		// stop refill orders after delete subscription
		log.info("inside deleteBySubscriptionId method");

		if (authFeign.getValidity(token).getBody().isValid()) {
			refillOrderRepository.deleteBySubscriptionId(subscriptionId);
		} else
			throw new InvalidTokenException(msg);
	}

}
