package com.cognizant.drug.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.cognizant.drug.entity.DrugDetails;
import com.cognizant.drug.entity.ResponseForSuccess;
import com.cognizant.drug.entity.Stock;
import com.cognizant.drug.exception.InvalidTokenException;
import com.cognizant.drug.exception.StockNotFoundException;



public interface DrugDetailsService {

	/**
	 * 
	 * @param id
	 * @param token
	 * @return
	 * @throws InvalidTokenException
	 */
	DrugDetails getDrugById(String id, String token) throws InvalidTokenException;

	/**
	 * 
	 * @param name
	 * @param token
	 * @return
	 * @throws InvalidTokenException
	 */
	DrugDetails getDrugByName(String name, String token) throws InvalidTokenException;

	/**
	 * 
	 * @param id
	 * @param location
	 * @param token
	 * @return
	 * @throws InvalidTokenException
	 * @throws StockNotFoundException
	 */
	Stock getDispatchableDrugStock(String id, String location, String token)
			throws InvalidTokenException, StockNotFoundException;
	
	
	/**
	 * 
	 * @param id
	 * @param location
	 * @param quantity
	 * @param token
	 * @return
	 * @throws InvalidTokenException
	 * @throws StockNotFoundException
	 */
	ResponseEntity<ResponseForSuccess> updateQuantity(String id, String location, int quantity, String token)
			throws InvalidTokenException,StockNotFoundException;

	/**
	 * 
	 * @return
	 */
	List<DrugDetails> getAllDrugs(String token) throws InvalidTokenException;

}
