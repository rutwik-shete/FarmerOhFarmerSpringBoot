package com.rutwik.farmerohfarmer.Repositories;

import java.util.List;

import com.rutwik.farmerohfarmer.Models.Farmer;
import com.rutwik.farmerohfarmer.Models.Order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {

	List<Order> findAllByFarmer(Farmer farmer);

	boolean existsByFarmer(Farmer farmer);
    
}
