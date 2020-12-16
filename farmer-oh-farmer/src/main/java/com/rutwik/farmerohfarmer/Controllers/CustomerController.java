package com.rutwik.farmerohfarmer.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.rutwik.farmerohfarmer.Models.Customer;
import com.rutwik.farmerohfarmer.Models.Locations;
import com.rutwik.farmerohfarmer.Models.Output;
import com.rutwik.farmerohfarmer.Repositories.CustomerRepository;
import com.rutwik.farmerohfarmer.Repositories.LocationsRepository;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private LocationsRepository locationsRepository;
	
	@GetMapping(path = "/welcome")
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

	@PostMapping(path="/searchFarmerByLocation",consumes = "application/json",produces = "application/json")
	public Output searchFarmerByLocation(@RequestBody Map<String,Integer> input){
		int pincode = input.get("pincode");
		List<Locations> famerFoundList = locationsRepository.findAllByDeliveryPincode(pincode);
		List<FarmerByLocationOutput> result = new ArrayList<FarmerByLocationOutput>();
		for(Locations locations : famerFoundList){
			result.add(new FarmerByLocationOutput(locations.getFarmerName(), locations.getFarmerId(), locations.getFarmerRating()));
		}
		return new Output("Success","Farmers Fetched Easily",result);
	}

	public class FarmerByLocationOutput {
		private String farmerName ;
		private long farmerId;
		private double farmerRating;

		public FarmerByLocationOutput(String farmerName, long farmerId, double farmerRating) {
			this.farmerName = farmerName;
			this.farmerId = farmerId;
			this.farmerRating = farmerRating;
		}

		public String getFarmerName(){
			return this.farmerName;
		}
		public void setFarmerName(String farmerName){
			this.farmerName = farmerName;
		}

		public long getFarmerId(){
			return this.farmerId;
		}
		public void setFarmerID(long farmerId){
			this.farmerId = farmerId;
		}

		public double getFarmerRating(){
			return this.farmerRating;
		}
		public void serFarmerRating(double farmerRating){
			this.farmerRating = farmerRating;
		}
	}
}
