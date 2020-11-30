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
    
    @Column(name="delivery_pincode")
    private int delivery_pincode = 0;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "farmer_id", nullable = false)
    private Farmer farmer;


    public Locations() {
        super();
    }

    public Locations(int delivery_pincode, Farmer farmer){
        super();
        this.delivery_pincode = delivery_pincode;
        this.farmer = farmer;
    }

    public long getId() {
		return id;
	}

    public int getDelivery_pincode() {
        return this.delivery_pincode;
    }

    public void setDelivery_pincode(int delivery_pincode) {
        this.delivery_pincode = delivery_pincode;
    }

    

    
}
