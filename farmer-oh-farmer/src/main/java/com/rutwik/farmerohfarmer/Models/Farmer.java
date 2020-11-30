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
@Table(name = "Farmer",schema = Constants.SCHEMA_NAME)
public class Farmer extends Dates{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;
	
	@Column(name="address")
	private String address;
	
	@Column(name="pincode")
	private int pincode = 0;
	
	@Column(name="rating")
	private double rating = 0;
    
    @OneToMany(mappedBy = "farmer", fetch = FetchType.LAZY,
    cascade = CascadeType.ALL)
	private Set<Locations> locations;
	
	@OneToMany(mappedBy = "farmer", fetch = FetchType.LAZY,
    cascade = CascadeType.ALL)
	private Set<Product> product;
	
	@OneToMany(mappedBy = "farmer", fetch = FetchType.LAZY,
    cascade = CascadeType.ALL)
	private Set<Courier> couriers;
	
	@OneToMany(mappedBy = "farmer", fetch = FetchType.LAZY,
    cascade = CascadeType.ALL)
    private Set<Order> orders;


	public Farmer(String name, String phone, String email, String password, String address, int pincode) {
		super();
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.password = password;
		this.address = address;
		this.pincode = pincode;
		this.rating = 0.0;
	}

	public Farmer() {
		super();
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPincode() {
		return pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
	}

	public double getRating() {
		return rating;
	}
	
	public void setRating(Double rating) {
		System.out.println("This Is Rating"+rating);
		if(rating != null){
			this.rating = rating.doubleValue();
		}
		else{
			this.rating = 0.0;
		}

	}	
	
}