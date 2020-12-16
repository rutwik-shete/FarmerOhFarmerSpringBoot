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
@Table(name = "products", schema = Constants.SCHEMA_NAME)
public class Product extends Dates {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "cost")
    private double cost = 0;

    @Column(name = "measurement")
    private String measurement;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "farmer_id", nullable = false)
    private Farmer farmer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Product_Data_id", nullable = false)
    private ProductData productData;

    public Product() {
        super();
    }

    public Product(double cost, String measurement, Farmer farmer , ProductData productData){
        super();
        this.cost = cost;
        this.measurement = measurement;
        this.farmer = farmer;
        this.productData = productData;
    }

    public long getId() {
        return this.id;
    }

    public double getCost() {
        return this.cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getMeasurement() {
        return this.measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public Farmer getFarmer(){
        return farmer;
    }

    public void setFarmer(Farmer farmer){
        this.farmer = farmer;
    }

    public ProductData getProductData() {
        return this.productData;
    }

    public void setProductData(ProductData productData) {
        this.productData = productData;
    }
}
