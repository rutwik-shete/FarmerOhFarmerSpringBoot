package com.rutwik.farmerohfarmer.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rutwik.farmerohfarmer.Models.Customer;


@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long>{

	Customer findByEmailAndPassword(String email, String password);

	boolean existsByEmail(String email);

}
