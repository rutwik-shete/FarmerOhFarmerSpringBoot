package com.rutwik.farmerohfarmer.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.rutwik.farmerohfarmer.Constants;

@Entity
@Table(name="cart",schema=Constants.SCHEMA_NAME)
public class Cart {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "product_quantity")
    private int productQuantity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    Cart(){
        super();
    }

    Cart(Product product , int productQuantity , Customer customer){
        super();
        this.product = product;
        this.productQuantity = productQuantity;
        this.customer = customer;
    }

    public long getId() {
        return this.id;
    }

    public int getproductQuantity() {
        return this.productQuantity;
    }

    public void setproductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
