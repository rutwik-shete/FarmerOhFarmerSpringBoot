package com.rutwik.farmerohfarmer.Repositories;

import com.rutwik.farmerohfarmer.Models.ProductData;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDataRepository extends JpaRepository<ProductData,Long> {
    
}
