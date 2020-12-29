package com.rutwik.farmerohfarmer.Repositories;

import java.util.List;

import com.rutwik.farmerohfarmer.Constants.IsOrdered;
import com.rutwik.farmerohfarmer.Models.Cart;
import com.rutwik.farmerohfarmer.Models.Customer;
import com.rutwik.farmerohfarmer.Models.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long>{

	boolean existsByProductAndCustomerAndIsOrdered(Product product, Customer customer, IsOrdered no);

	Cart findByProductAndCustomerAndIsOrdered(Product product, Customer customer, IsOrdered no);

	boolean existsByCustomer(Customer customer);

	List<Cart> findAllByCustomer(Customer customer);
    
}
