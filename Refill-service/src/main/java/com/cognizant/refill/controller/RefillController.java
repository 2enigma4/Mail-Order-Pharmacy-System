package com.cognizant.refill.controller;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.refill.entity.RefillOrder;
import com.cognizant.refill.entity.Refilltemp;
import com.cognizant.refill.exception.DrugQuantityNotAvailable;
import com.cognizant.refill.exception.InvalidTokenException;
import com.cognizant.refill.exception.RefillEmptyException;
import com.cognizant.refill.exception.StockNotFoundException;
import com.cognizant.refill.exception.SubscriptionIdNotFoundException;
import com.cognizant.refill.exception.SubscriptionListEmptyException;
import com.cognizant.refill.exception.SubscriptionNotFoundException;
import com.cognizant.refill.service.RefillOrderService;
import com.cognizant.refill.service.RefillOrderSubscriptionService;

import feign.FeignException;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin(origins="*")
public class RefillController {

	@Autowired
	public RefillOrderService service;

	@Autowired
	RefillOrderSubscriptionService refillOrderSubscriptionService;

	@ApiOperation(value = "View status of the subscriptions per id", response = ResponseEntity.class)
	@GetMapping(path = "/viewRefillStatus/{subid}")
	public ResponseEntity<List<RefillOrder>> viewRefillStatus(@RequestHeader("Authorization") String token,
			@PathVariable("subid") long subId) throws SubscriptionIdNotFoundException, InvalidTokenException {
		log.info("Inside Refill Controller viewRefillStatus method");
		return ResponseEntity.ok().body(service.getStatus(subId, token));
	}

	@ApiOperation(value = "View all refill by member Id", response = ResponseEntity.class)
	@GetMapping(path = "/viewRefillStatusforAll/{mid}")
	public ResponseEntity<List<RefillOrder>> viewRefillStatusByMemberId(@RequestHeader("Authorization") String token,
			@PathVariable("mid") String memberId) throws InvalidTokenException, RefillEmptyException {
		log.info("Inside Refill Controller viewRefillStatus method");
		return ResponseEntity.ok().body(service.getStatusByMember(memberId, token));
	}

	@ApiOperation(value = "View payment dues as of date", response = ResponseEntity.class)
	@GetMapping(path = "/getRefillPaymentDues/{subscriptionId}")
	public ResponseEntity<Boolean> getRefillPaymentDues(@RequestHeader("Authorization") String token,
			@PathVariable("subscriptionId") long subscriptionId) throws InvalidTokenException {
		log.info("Inside Refill Controller getRefillDuesAsOfDate method");

		return ResponseEntity.ok().body(service.getRefillPaymentDues(subscriptionId, token));
	}

	@ApiOperation(value = "View refill dues as of date", response = ResponseEntity.class)
	@GetMapping(path = "/getRefillDuesAsOfDate/{memberId}/{date}")
	public ResponseEntity<List<RefillOrder>> getRefillDuesAsOfDate(@RequestHeader("Authorization") String token,
			@PathVariable("memberId") String memberId, @PathVariable("date") String date)
			throws Exception {
		log.info("Inside Refill Controller getRefillDuesAsOfDate method");
		try
		{
			return ResponseEntity.ok().body(service.getRefillDuesAsOfDate(memberId, date, token));
		}
		
		catch (FeignException e) {
			
			System.out.println("@@@@@@"+e.getMessage());
			throw new SubscriptionListEmptyException("No subscription Found.Please subscribe");
		}catch (Exception e) {
			throw new Exception("Please select a valid date");
		}
		
		
	}

	// yha change latest
	@ApiOperation(value = "Request refill", response = ResponseEntity.class)
	@PostMapping(path = "/requestAdhocRefill")
	public ResponseEntity<RefillOrder> requestAdhocRefill(@RequestHeader("Authorization") String token,
			@RequestBody Refilltemp temp) throws DrugQuantityNotAvailable, StockNotFoundException, SubscriptionNotFoundException
			 {
		log.info("Inside Refill Controller requestAdhocRefill method");

		try {
			return ResponseEntity.accepted()
					.body(service.requestAdhocRefill(temp.getI(), temp.getPayStatus(), temp.getQuantity(), temp.getLocation(), token, temp.getMemberId()));
		} catch (FeignException  e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			if(e.getMessage().contains("\"messge\":\"Stock Unavailable at your location\""))
			{
				throw new StockNotFoundException("Stock Not available at your location");
			}
			 if(e.getMessage().contains("\"messge\":\"Subscription Not found\""))
			{
				throw new SubscriptionNotFoundException("Subscription id Not Found");
			}
			
		
		}
		 catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}


	@ApiOperation(value = "View list of refills", response = ResponseEntity.class)
	@GetMapping(path = "/viewRefillOrderSubscriptionStatus")
	public ResponseEntity<List<RefillOrder>> viewRefillOrderSubscriptionStatus(
			@RequestHeader("Authorization") String token) throws InvalidTokenException {
		log.info("Inside Refill Controller viewRefillOrderSubscriptionStatus method");

		return ResponseEntity.ok().body(refillOrderSubscriptionService.getall(token));
	}

	@ApiOperation(value = "Delete subscription", response = ResponseEntity.class)
	@DeleteMapping(path = "/deleteBySubscriptionId/{subscriptionId}")
	public void deleteBySubscriptionId(@RequestHeader("Authorization") String token,
			@PathVariable("subscriptionId") long subscriptionId) throws InvalidTokenException {
		log.info("Inside Refill Controller deleteBySubscriptionId method");

		refillOrderSubscriptionService.deleteBySubscriptionId(subscriptionId, token);
	}

}
