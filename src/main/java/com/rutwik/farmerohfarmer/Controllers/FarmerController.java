package com.rutwik.farmerohfarmer.Controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.rutwik.farmerohfarmer.Constants.IsActive;
import com.rutwik.farmerohfarmer.Models.Farmer;
import com.rutwik.farmerohfarmer.Models.Order;
import com.rutwik.farmerohfarmer.Models.Output;
import com.rutwik.farmerohfarmer.Models.Product;
import com.rutwik.farmerohfarmer.Models.ProductData;
import com.rutwik.farmerohfarmer.Models.Locations;
import com.rutwik.farmerohfarmer.Repositories.FarmerRepository;
import com.rutwik.farmerohfarmer.Repositories.LocationsRepository;
import com.rutwik.farmerohfarmer.Repositories.OrderRepository;
import com.rutwik.farmerohfarmer.Repositories.ProductDataRepository;
import com.rutwik.farmerohfarmer.Repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
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
	
	@Autowired
	private OrderRepository orderRepository;


	@GetMapping("welcome")
	public String welcomeMessage() {
		return "Hello Farmer";
	}

	@PostMapping(path = "/addFarmer", consumes = "application/json", produces = "application/json")
	public Output addFarmer(@RequestBody Farmer farmer) {
		if (!this.farmerRepository.existsByEmail(farmer.getEmail())) {
			this.farmerRepository.save(farmer);
			return new Output("Success", "Farmer Signed Up Successfully", farmer);
		}
		return new Output("Failed", "SignUp Failed , Farmer Already Exists", null);
	}

	@PostMapping(path = "/loginFarmer", consumes = "application/json", produces = "application/json")
	public Output loginFarmer(@RequestBody Map<String, String> farmer){
		Farmer farmerFound = farmerRepository.findByEmailAndPassword(farmer.get("email"),farmer.get("password"));
		if(farmerFound != null){
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
        long productDataID = product.getProductDataId();
        long farmerID = product.getFarmerId();
        if(productDataRepository.existsById(productDataID)){
            ProductData productDataFound = productDataRepository.findById(productDataID).get();
            product.setProductData(productDataFound);
            if(farmerRepository.existsById(farmerID)){
                Farmer farmerFound = farmerRepository.findById(farmerID).get();
                product.setFarmer(farmerFound);
                if(!productRepository.existsByFarmerAndProductDataAndIsActive(farmerFound,productDataFound,IsActive.YES)){
                    productRepository.save(product);
                    return new Output("Success","Product Added",product);
                }
                else{
					Product updateProduct = productRepository.findAllByFarmerAndProductData(farmerFound,productDataFound);
					updateProduct.setMeasurement(product.getMeasurement());
					updateProduct.setCost(product.getCost());
					productRepository.save(updateProduct);
                    return new Output("Success","Product Updated Successfully",product);
                }
            }
            else{
                return new Output("Failed","Product Not Added, Farmer ID Does Not Exists",null);
            }
        }
        return new Output("Failed","Product Not Added, ProductData ID Does Not Exists",null);
	}
	
	@PostMapping(path="/getOrders" ,consumes="application/json" , produces = "application/json")
	public Output getOrders(@RequestBody Map<String,String> farmerInfo){
		try{
			long farmerId = Long.parseLong(farmerInfo.get("farmerId"));
			if(farmerRepository.existsById(farmerId)){
				Farmer farmer = farmerRepository.findById(farmerId).get();
				if(orderRepository.existsByFarmer(farmer)){
					List<Order> OrderList = orderRepository.findAllByFarmer(farmer);
					return new Output("Success","Orders Fetched Successfully",OrderList);
				}
				else{
					return new Output("Failed","No Orders For Th Farmer",null);
				}
			}
			else{
				return new Output("Failed","Farmer Doesnt Exist",null);
			}
		}
		catch(Exception e){
			return new Output("Failed","Something Went Wrong",null);
		}
	}

	@PostMapping(path="/getProducts", consumes="application/json", produces="application/json")
	public Output getProducts(@RequestBody Map<String,String> farmerInfo){
		try{
			long farmerId = Long.parseLong(farmerInfo.get("farmerId"));
			if(farmerRepository.existsById(farmerId)){
				Farmer farmer = farmerRepository.findById(farmerId).get();
				if(productRepository.existsByFarmer(farmer)){
					List<Product> ProductList = productRepository.findAllByFarmerAndIsActive(farmer,IsActive.YES);
					return new Output("Success","Orders Fetched Successfully",ProductList);
				}
				else{
					return new Output("Failed","No Orders For The Farmer",null);
				}
			}
			else{
				return new Output("Failed","Farmer Doesnt Exist",null);
			}
		}
		catch(Exception e){
			return new Output("Failed","Something Went Wrong",null);
		}
		
	}

	@PostMapping(path="/removeProduct", consumes = "application/json", produces = "application/json")
	public Output removeProduct(@RequestBody Map<String,String> productInfo){
		try{
			long productId = Long.parseLong(productInfo.get("productId"));
			if(productRepository.existsById(productId)){
				Product product = productRepository.findById(productId).get();
				product.setIsActive(IsActive.NO);
				productRepository.save(product);
				return new Output("Success","Product Removed Successfully",null);
			}
			return new Output("Falied","Product Not Removed",null);
		}
		catch(Exception e){
			return new Output("Falied","Product Not Removed",null);
		}
		
	}

}
