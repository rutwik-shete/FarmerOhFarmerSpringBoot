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
import com.rutwik.farmerohfarmer.Constants.IsDelivered;

@Entity
@Table(name="Order",schema=Constants.SCHEMA_NAME)
public class Order extends Dates{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "delivery_status")
    private IsDelivered deliveryStatus = IsDelivered.PENDING;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "farmer_id", nullable = false)
    private Farmer farmer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "courier_id", nullable = false)
    private Courier courier;

    @Column(name="order_amount")
    private double orderAmount = 0;

    @OneToMany(mappedBy = "order" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Set<OrderContent> orderContent;
    
    public Order(){
        super();
    }

    public Order(Farmer farmer, Customer customer, Courier courier , Double orderAmount){
        this.farmer = farmer;
        this.customer = customer;
        this.courier = courier;
        this.orderAmount = orderAmount;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }  

    public IsDelivered getDeliveryStatus() {
        return this.deliveryStatus;
    }

    public void setDeliveryStatus(IsDelivered deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getFarmerName() {
        return this.farmer.getName();
    }

    public long getCustomerId() {
        return this.customer.getId();
    }

    public long getCourierId() {
        return this.courier.getId();
    }

    public double getOrderAmount(){
        return this.orderAmount;
    }

    public void setOrderAmount(double orderAmount){
        this.orderAmount = orderAmount;
    }

    public String getCustomerName(){
        return this.customer.getName();
    }

    public String getCustomerAddress(){
        return this.customer.getAddress();
    }

    public String getCustomerPhone(){
        return this.customer.getPhone();
    }

    public Set<OrderContent> getOrderContent(){
        return orderContent;
    }
}
