package com.rutwik.farmerohfarmer.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rutwik.farmerohfarmer.Models.Customer;
import com.rutwik.farmerohfarmer.Models.Output;
import com.rutwik.farmerohfarmer.Repositories.CustomerRepository;

@RestController
@RequestMapping("/api/")
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;
	
	@GetMapping(path = "/welcomeCustomer")
	public String welcomeMessage() {
		return "Hello Customer";
	}
	
	@PostMapping(path = "/addCustomer", consumes = "application/json", produces = "application/json")
	public Output addCustomer(@RequestBody Customer customer){
		if(!this.customerRepository.existsByEmail(customer.getEmail())){
			this.customerRepository.save(customer);
			customer.setPassword(null);
			return new Output("Success","Customer Signed Up Successfully",customer);
		}
		customer.setPassword(null);
		return new Output("Failed","SignUp Failed , Customer Already Exists",null);
	}

	@PostMapping(path = "/loginCustomer", consumes = "application/json", produces = "application/json")
	public Output loginCustomer(@RequestBody Customer customer){
		Customer customerFound = customerRepository.findByEmailAndPassword(customer.getEmail(),customer.getPassword());
		if(customerFound != null){
			customerFound.setPassword(null);
			return new Output("Success","Customer LoggedIn Successfully",customerFound);
			
		}
		return new Output("Failed","Login Failed , Username Or Password Wrong",null);
	}

	@GetMapping("updateCustomer")
	public Customer updateCustomer() {
		long id = 1L;
		Customer newCust = this.customerRepository.findById(id).get();
		System.out.println("Hello There");
		newCust.setName("Rutwik Shete");
		customerRepository.save(newCust);
		return newCust;
	}
}
