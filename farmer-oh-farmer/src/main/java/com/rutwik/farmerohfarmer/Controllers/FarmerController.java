package com.rutwik.farmerohfarmer.Controllers;

import java.util.Map;
import java.util.Optional;

import com.rutwik.farmerohfarmer.Models.Farmer;
import com.rutwik.farmerohfarmer.Models.Output;
import com.rutwik.farmerohfarmer.Models.Product;
import com.rutwik.farmerohfarmer.Models.ProductData;
import com.rutwik.farmerohfarmer.Models.Locations;
import com.rutwik.farmerohfarmer.Repositories.FarmerRepository;
import com.rutwik.farmerohfarmer.Repositories.LocationsRepository;
import com.rutwik.farmerohfarmer.Repositories.ProductDataRepository;
import com.rutwik.farmerohfarmer.Repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/farmer")
public class FarmerController {

	@Autowired
	private FarmerRepository farmerRepository;

	@Autowired
	private LocationsRepository locationsRepository;

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
    private ProductDataRepository productDataRepository;


	@GetMapping("welcome")
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

	@PostMapping(path = "/addDeliveryLocations", consumes = "application/json", produces = "application/json")
	public Output addDeliveryLocations(@RequestBody Map<String, Integer[]> locationsData) {
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

	@PostMapping(path = "/addProduct",consumes = "application/json",produces = "application/json")
    public Output addProduct(@RequestBody Product product){
        long productDataID = product.getProductData().getId();
        long farmerID = product.getFarmer().getId();
        if(productDataRepository.existsById(productDataID)){
            ProductData productDataFound = productDataRepository.findById(productDataID).get();
            product.setProductData(productDataFound);
            if(farmerRepository.existsById(farmerID)){
                Farmer farmerFound = farmerRepository.findById(farmerID).get();
                product.setFarmer(farmerFound);
                if(!productRepository.existsByFarmerAndProductData(farmerFound,productDataFound)){
                    productRepository.save(product);
                    return new Output("Success","Product Added",product);
                }
                else{
                    return new Output("Failed","Product Already Exists",null);
                }
            }
            else{
                return new Output("Failed","Product Not Added, Farmer ID Does Not Exists",null);
            }
        }
        return new Output("Failed","Product Not Added, ProductData ID Does Not Exists",null);
    }


}
