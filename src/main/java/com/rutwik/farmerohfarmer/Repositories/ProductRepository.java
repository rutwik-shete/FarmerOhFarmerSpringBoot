package com.rutwik.farmerohfarmer.Repositories;

import java.util.List;

import com.rutwik.farmerohfarmer.Models.Farmer;
import com.rutwik.farmerohfarmer.Models.Product;
import com.rutwik.farmerohfarmer.Models.ProductData;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long>{

	boolean existsByFarmerAndProductData(Farmer farmerFound, ProductData productDataFound);

	List<Product> findAllByFarmer(Farmer farmer);
    
}
