package com.rutwik.farmerohfarmer.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rutwik.farmerohfarmer.Models.Customer;
import com.rutwik.farmerohfarmer.Repositories.CustomerRepository;

@RestController
@RequestMapping("/api/")
public class FarmerController {

	@Autowired
	private CustomerRepository customerRepository;
	
	@GetMapping("welcome")
	public String welcomeMessage() {
		return "Hello Farmer";
	}
	
	@GetMapping("customer")
	public List<Customer> getAllCustomer(){
		return this.customerRepository.findAll();
	}
	
	@GetMapping("addCustomer")
	public Customer addCustomer(){
		Customer newCust = new Customer("Rutwik","9765393401","rutwik.shete@gmail.com","Rutya","Sinhagad Road",411041);
		this.customerRepository.save(newCust);
		return newCust;
	}
	@GetMapping("updateCustomer")
	public Customer updateCustomer() {
		long id = 1L;
		Customer newCust = this.customerRepository.findOne(id);
		newCust.setName("Rutya Shete");
		return newCust;
	}
}
