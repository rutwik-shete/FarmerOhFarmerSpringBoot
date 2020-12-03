package com.rutwik.farmerohfarmer.Models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.rutwik.farmerohfarmer.Constants;

@Entity
@Table(name="Product_Data",schema = Constants.SCHEMA_NAME)
public class ProductData extends Dates {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "photo_link")
    private String photoLink;

    @OneToMany(mappedBy="productData", fetch = FetchType.LAZY,
    cascade = CascadeType.ALL)
    private Set<Product> product;

    public ProductData() {
        super();
    }

    public ProductData(String name , String photoLink){
        super();
        this.name = name ;
        this.photoLink = photoLink;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoLink() {
        return this.photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

}
