package com.rutwik.farmerohfarmer.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.rutwik.farmerohfarmer.Constants;

@Entity
@Table(name = "Locations" , schema = Constants.SCHEMA_NAME)
public class Locations extends Dates{
    
    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name="deliveryPincode")
    private int deliveryPincode = 0;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "farmerId", nullable = false)
    private Farmer farmer;


    public Locations() {
        super();
    }

    public Locations(int deliveryPincode, Farmer farmer){
        super();
        this.deliveryPincode = deliveryPincode;
        this.farmer = farmer;
    }

    public long getId() {
		return id;
	}

    public int getdeliveryPincode() {
        return this.deliveryPincode;
    }

    public void setdeliveryPincode(int deliveryPincode) {
        this.deliveryPincode = deliveryPincode;
    }

    public long getFarmerId(){
        return farmer.getId();
    }    

    public String getFarmerName(){
        return farmer.getName();
    }

    public double getFarmerRating(){
        return farmer.getRating();
    }
   
}
