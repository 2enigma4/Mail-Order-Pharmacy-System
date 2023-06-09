package com.cognizant.drug.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cognizant.drug.dao.DrugDetailsRepository;
import com.cognizant.drug.dao.DrugLocationRepository;
import com.cognizant.drug.entity.DrugDetails;
import com.cognizant.drug.entity.DrugLocationDetails;
import com.cognizant.drug.entity.ResponseForSuccess;
import com.cognizant.drug.entity.Stock;
import com.cognizant.drug.exception.DrugNotFoundException;
import com.cognizant.drug.exception.InvalidTokenException;
import com.cognizant.drug.exception.StockNotFoundException;
import com.cognizant.drug.restclients.AuthFeign;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DrugDetailsServiceImpl implements DrugDetailsService {

	@Autowired
	private DrugDetailsRepository drugDetailsRepository;

	@Autowired
	private DrugLocationRepository drugLocationRepository;

	@Autowired
	private AuthFeign authFeign;


	String drugNotFound = "Drug Not Found";

	String invalidToken = "Invalid Token Received";

	String stockNotAvailable = "Stock Unavailable at your location";

	/**
	 * 
	 * @param id
	 * @param token
	 * @return
	 * @throws InvalidTokenException
	 */
	@Override
	public DrugDetails getDrugById(String id, String token) throws InvalidTokenException,DrugNotFoundException {
		log.info("start--serviceimpl--getDrugById");
		DrugDetails drugDetails = null;
		
		System.out.println("************************************"+authFeign.getValidity(token).getBody());
		
		if (authFeign.getValidity(token).getBody().isValid()) {
			try {
				drugDetails = drugDetailsRepository.findById(id).orElseThrow(() -> new DrugNotFoundException(drugNotFound));
			}catch (DrugNotFoundException d){
				throw new DrugNotFoundException(drugNotFound);
			}
		} else
			throw new InvalidTokenException(invalidToken);
		log.info("end--serviceimpl--getDrugById");
		return drugDetails;
	}


	/**
	 * 
	 * @param name
	 * @param token
	 * @return
	 * @throws InvalidTokenException
	 */
	@Override
	public DrugDetails getDrugByName(String name, String token) throws InvalidTokenException {
		log.info("start--serviceimpl--getDrugByName");
		if (authFeign.getValidity(token).getBody().isValid()) {
			try {
				return drugDetailsRepository.findBydrugName(name).get();
			} catch (NoSuchElementException e) {
				throw new DrugNotFoundException(drugNotFound);
			}
		} else
			throw new InvalidTokenException(invalidToken);
	
	}

	/**
	 * 
	 * @param id
	 * @param location
	 * @param token
	 * @return
	 * @throws InvalidTokenException
	 * @throws StockNotFoundException
	 */
	@Override
	public Stock getDispatchableDrugStock(String id, String location, String token)
			throws InvalidTokenException, StockNotFoundException {
		log.info("start--serviceimpl--getDispatchableDrugStock");
		if (authFeign.getValidity(token).getBody().isValid()) {
			DrugDetails details = null;
			try {
				details = drugDetailsRepository.findById(id).get();
			} catch (Exception e) {

				throw new DrugNotFoundException(drugNotFound);
			}
			Stock stock = null;
			for (DrugLocationDetails dld : details.getDruglocationQuantities()) {
				if (dld.getLocation().equals(location)) {
					stock = new Stock(id, details.getDrugName(), details.getExpiryDate(), dld.getQuantity());
					break;
				}
			}
			if (stock == null)
				throw new StockNotFoundException(stockNotAvailable);
			else
			{
				log.info("End--serviceimpl--getDispatchableDrugStock");
				return stock;
			}
		}
		throw new InvalidTokenException(invalidToken);
	}

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
	@Override
	public ResponseEntity<ResponseForSuccess> updateQuantity(String drugName, String location, int quantity, String token)
			throws InvalidTokenException, StockNotFoundException {
		log.info("start--serviceimpl-- updateQuantity");
		if (authFeign.getValidity(token).getBody().isValid()) {
			DrugDetails details = new DrugDetails();
			try {
				details = drugDetailsRepository.findBydrugName(drugName).get();
			} catch (Exception e) {

				throw new DrugNotFoundException(invalidToken);
			}
			List<DrugLocationDetails> dummy = details.getDruglocationQuantities().stream()
					.filter(x -> x.getLocation().equalsIgnoreCase(location)).collect(Collectors.toList());

			if (dummy.isEmpty()) {
				throw new StockNotFoundException(stockNotAvailable);
			}

			else if (dummy.get(0).getQuantity() >= quantity && quantity >0) {

				DrugLocationDetails allDetails = drugLocationRepository.findByserialId(dummy.get(0).getSerialId()).get(0);
				int val = allDetails.getQuantity() - quantity;
				allDetails.setQuantity(val);
				drugLocationRepository.save(allDetails);
				log.info("End--serviceimpl-- updateQuantity");
				return new ResponseEntity<>(new ResponseForSuccess("Refill Done Successfully"),
						HttpStatus.OK);
			} else
				throw new StockNotFoundException(stockNotAvailable);
		}
		throw new InvalidTokenException(invalidToken);
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public List<DrugDetails> getAllDrugs(String token) throws InvalidTokenException{
		if (authFeign.getValidity(token).getBody().isValid()) {
		log.info("start--serviceimpl--  getAllDrugs");
		return drugDetailsRepository.findAll();
		}else {
			throw new InvalidTokenException(invalidToken);
		}
	}

	/**
	 * 
	 * @param name
	 * @param location
	 * @param token
	 * @return
	 * @throws InvalidTokenException
	 * @throws StockNotFoundException
	 */
	/*
	 * @Override public Stock getDispatchableDrugStockByName(String name, String
	 * location, String token) throws InvalidTokenException, StockNotFoundException
	 * { log.info("start--serviceimpl--getDispatchableDrugStockByName"); if
	 * (authFeign.getValidity(token).getBody().isValid()) { DrugDetails details =
	 * null; try { details = drugDetailsRepository.findBydrugName(name).get(); }
	 * catch (Exception e) {
	 * 
	 * throw new DrugNotFoundException(drugNotFound); } Stock stock = null; for
	 * (DrugLocationDetails dld : details.getDruglocationQuantities()) { if
	 * (dld.getLocation().equals(location)) { stock = new Stock(details.getDrugId(),
	 * name, details.getExpiryDate(), dld.getQuantity()); } } if (stock == null)
	 * throw new StockNotFoundException(stockNotAvailable); else {
	 * log.info("end--serviceimpl--getDispatchableDrugStockByName"); return stock; }
	 * } throw new InvalidTokenException(invalidToken);
	 * 
	 * }
	 */
}

