package com.cognizant.drug.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.drug.entity.DrugDetails;


@Repository
public interface DrugDetailsRepository extends JpaRepository<DrugDetails, String>{

//	Optional<DrugDetails> findById(String id);
	
	Optional<DrugDetails> findBydrugName(String name);

}