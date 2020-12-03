package com.rutwik.farmerohfarmer.Models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.rutwik.farmerohfarmer.Constants;

@Entity
@Table(name="Order",schema=Constants.SCHEMA_NAME)
public class Order extends Dates{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "delivery_status")
    private String deliveryStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "farmer_id", nullable = false)
    private Farmer farmer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "courier_id", nullable = false)
    private Courier courier;

    @OneToMany(mappedBy = "order" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Set<OrderContent> orderContent;
    
    Order(){
        super();
    }

    Order(Farmer farmer, Customer customer, Courier courier){
        this.farmer = farmer;
        this.customer = customer;
        this.courier = courier;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }  

    public String getDeliveryStatus() {
        return this.deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Farmer getFarmer() {
        return this.farmer;
    }

    public void setFarmer(Farmer farmer) {
        this.farmer = farmer;
    } 

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Courier getCourier() {
        return this.courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }
}
