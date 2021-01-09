package com.rutwik.farmerohfarmer.Repositories;

import java.util.Optional;

import com.rutwik.farmerohfarmer.Models.Courier;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourierRepository extends JpaRepository<Courier,Long>{

	boolean existsByFarmerId(long farmerId);

	Optional<Courier> findByFarmerId(long farmerId);
    
}
