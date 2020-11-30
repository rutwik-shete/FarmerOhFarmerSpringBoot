package com.rutwik.farmerohfarmer.Repositories;

import com.rutwik.farmerohfarmer.Models.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long>{
    
}
