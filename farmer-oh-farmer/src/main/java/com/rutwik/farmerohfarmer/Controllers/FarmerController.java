package com.rutwik.farmerohfarmer.Controllers;

import com.rutwik.farmerohfarmer.Models.Farmer;
import com.rutwik.farmerohfarmer.Models.Output;
import com.rutwik.farmerohfarmer.Repositories.FarmerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class FarmerController {
    
    @Autowired
    private FarmerRepository farmerRepository;

    @GetMapping("welcomeFarmer")
	public String welcomeMessage() {
		return "Hello Farmer";
    }
    
    @PostMapping(path = "/addFarmer", consumes = "application/json", produces = "application/json")
	public Output addFarmer(@RequestBody Farmer farmer){
		if(!this.farmerRepository.existsByEmail(farmer.getEmail())){
			this.farmerRepository.save(farmer);
			farmer.setPassword(null);
			return new Output("Success","Farmer Signed Up Successfully",farmer);
		}
		return new Output("Failed","SignUp Failed , Farmer Already Exists",null);
	}

	@PostMapping(path = "/loginFarmer", consumes = "application/json", produces = "application/json")
	public Output loginFarmer(@RequestBody Farmer farmer){
		Farmer farmerFound = farmerRepository.findByEmailAndPassword(farmer.getEmail(),farmer.getPassword());
		if(farmerFound != null){
			farmerFound.setPassword(null);
			return new Output("Success","Farmer LoggedIn Successfully",farmerFound);
			
		}
		return new Output("Failed","Login Failed , Username Or Password Wrong",null);
	}

	@GetMapping("updateFarmer")
	public Farmer updateFarmer() {
		long id = 1L;
		Farmer newFarmer = this.farmerRepository.findById(id).get();
		System.out.println("Hello There");
		newFarmer.setName("Rahuri Farmer");
		farmerRepository.save(newFarmer);
		return newFarmer;
	}

}
