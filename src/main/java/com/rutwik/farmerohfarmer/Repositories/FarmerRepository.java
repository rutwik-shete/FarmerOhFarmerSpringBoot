package com.rutwik.farmerohfarmer.Repositories;

import com.rutwik.farmerohfarmer.Models.Farmer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmerRepository extends JpaRepository<Farmer,Long>{

	boolean existsByEmail(String email);

	Farmer findByEmailAndPassword(String email, String password);
    
}
