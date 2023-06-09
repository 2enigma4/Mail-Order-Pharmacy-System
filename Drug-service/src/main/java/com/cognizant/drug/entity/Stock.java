package com.cognizant.drug.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
	private String drugId;
	private String drugName;
	private Date expiryDate;
	private int stocks;
	
}
