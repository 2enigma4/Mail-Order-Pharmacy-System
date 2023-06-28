package com.cognizant.refill.entity;

import org.springframework.web.bind.annotation.PathVariable;

import lombok.Data;

@Data
public class Refilltemp {

	long i; 
	Boolean payStatus;
	int quantity;
	String location;
	String memberId;
}