package com.rutwik.farmerohfarmer.Repositories;

import com.rutwik.farmerohfarmer.Models.Order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
    
}
