package com.rutwik.farmerohfarmer.Repositories;

import com.rutwik.farmerohfarmer.Models.Farmer;
import com.rutwik.farmerohfarmer.Models.Locations;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationsRepository extends JpaRepository<Locations,Long>{

	boolean existsByDeliveryPincodeAndFarmer(int location, Farmer farmerFound);

	

}
