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
import com.rutwik.farmerohfarmer.Constants.IsActive;

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

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "Product_Data_id", nullable = false)
    private ProductData productData;

    @Column(name="is_active")
    private IsActive isActive = IsActive.YES;

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

    public long getFarmerId(){
        return farmer.getId();
    }

    public void setFarmer(Farmer farmer){
        this.farmer = farmer;
    }

    public void setIsActive(IsActive isActive){
        this.isActive = isActive;
    }

    public long getProductDataId(){
        return productData.getId();
    }

    public String getProductDataName() {
        return productData.getName();
    }
    public String getProductDataPhotoLink() {
        return productData.getPhotoLink();
    }

    public void setProductData(ProductData productData) {
        this.productData = productData;
    }
}
