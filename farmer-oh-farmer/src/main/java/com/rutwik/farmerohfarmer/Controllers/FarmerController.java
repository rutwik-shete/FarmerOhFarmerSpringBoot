package com.rutwik.farmerohfarmer.Controllers;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.rutwik.farmerohfarmer.Models.Farmer;
import com.rutwik.farmerohfarmer.Models.Locations;
import com.rutwik.farmerohfarmer.Models.Output;
import com.rutwik.farmerohfarmer.Repositories.FarmerRepository;
import com.rutwik.farmerohfarmer.Repositories.LocationsRepository;

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

	@Autowired
	private LocationsRepository locationsRepository;

	@GetMapping("welcomeFarmer")
	public String welcomeMessage() {
		return "Hello Farmer";
	}

	@PostMapping(path = "/addFarmer", consumes = "application/json", produces = "application/json")
	public Output addFarmer(@RequestBody Farmer farmer) {
		if (!this.farmerRepository.existsByEmail(farmer.getEmail())) {
			this.farmerRepository.save(farmer);
			farmer.setPassword(null);
			return new Output("Success", "Farmer Signed Up Successfully", farmer);
		}
		return new Output("Failed", "SignUp Failed , Farmer Already Exists", null);
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

	@PostMapping(path = "/addFarmerDeliveryLocations", consumes = "application/json", produces = "application/json")
	public Output addFarmerDeliveryLocations(@RequestBody Map<String, Integer[]> locationsData) {
		long farmerId = locationsData.get("farmerId")[0];
		Integer[] locations = locationsData.get("locations");
		boolean isSuccess = false;
		Optional<Farmer> farmerFound = farmerRepository.findById(farmerId);
		if(!farmerFound.isEmpty()){
			Farmer farmer = farmerFound.get();
			for(int location : locations){
				if(!locationsRepository.existsByDeliveryPincodeAndFarmer(location,farmer)){
					locationsRepository.save(new Locations(location,farmer));
					isSuccess = true;
				}
			}
			if(isSuccess){
				return new Output("Success","Locations Added Successfully",farmer);
			}
			else{
				return new Output("Failed","Locations Already Exists",null);
			}
		}
		return new Output("Failed","Farmer Not Found",null);
	}

}
