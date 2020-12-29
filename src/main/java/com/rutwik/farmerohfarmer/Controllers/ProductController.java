package com.rutwik.farmerohfarmer.Controllers;

import java.util.List;

import com.rutwik.farmerohfarmer.Models.Output;
import com.rutwik.farmerohfarmer.Models.ProductData;
import com.rutwik.farmerohfarmer.Repositories.ProductDataRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductDataRepository productDataRepository;

    @PostMapping(path="/addProductData",consumes = "application/json",produces = "application/json")
    public Output addProductData(@RequestBody ProductData productData){
        if(!productDataRepository.existsByNameIgnoreCase(productData.getName())){
            productDataRepository.save(productData);
            return new Output("Success","ProductData Added",productData);
        }
        return new Output("Failed","Product With Same Name Exists",null); 
    }

    @GetMapping(path = "/getProductData")
	public Output getProductData(){
        List<ProductData> productDataList = productDataRepository.findAll();
        if(!productDataList.isEmpty()){
            return new Output("Success","Product Data Fetched Successfully",productDataList);
        }
		return new Output("Failed","Product Data Fetching Failed",null);
	}

}
