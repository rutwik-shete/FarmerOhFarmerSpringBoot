package com.rutwik.farmerohfarmer.Controllers;

import com.rutwik.farmerohfarmer.Models.Farmer;
import com.rutwik.farmerohfarmer.Models.Locations;
import com.rutwik.farmerohfarmer.Repositories.FarmerRepository;
import com.rutwik.farmerohfarmer.Repositories.LocationsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/")
public class LocationsController{
    
    @Autowired
    LocationsRepository locationsRepository;
    @Autowired
    FarmerRepository farmerRepository;

    @GetMapping(value="addLocations")
    public Locations addLocations() {
        Farmer farmer = farmerRepository.findById(1L).get();
        Locations newLocation = new Locations(411041,farmer);
        locationsRepository.save(newLocation);
        return newLocation;
    }
    

}
