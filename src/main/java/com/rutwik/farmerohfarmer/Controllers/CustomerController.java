package com.rutwik.farmerohfarmer.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.rutwik.farmerohfarmer.Constants;
import com.rutwik.farmerohfarmer.Constants.IsOrdered;
import com.rutwik.farmerohfarmer.Models.Cart;
import com.rutwik.farmerohfarmer.Models.Customer;
import com.rutwik.farmerohfarmer.Models.Farmer;
import com.rutwik.farmerohfarmer.Models.Locations;
import com.rutwik.farmerohfarmer.Models.Order;
import com.rutwik.farmerohfarmer.Models.Output;
import com.rutwik.farmerohfarmer.Models.Product;
import com.rutwik.farmerohfarmer.Repositories.CartRepository;
import com.rutwik.farmerohfarmer.Repositories.CustomerRepository;
import com.rutwik.farmerohfarmer.Repositories.FarmerRepository;
import com.rutwik.farmerohfarmer.Repositories.LocationsRepository;
import com.rutwik.farmerohfarmer.Repositories.OrderRepository;
import com.rutwik.farmerohfarmer.Repositories.ProductRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private LocationsRepository locationsRepository;

	@Autowired
	private FarmerRepository farmerRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private OrderRepository orderRepository;

	@GetMapping(path = "/welcome")
	public String welcomeMessage() {
		return "Hello Customer";
	}

	@PostMapping(path = "/addCustomer", consumes = "application/json", produces = "application/json")
	public Output addCustomer(@RequestBody Customer customer) {
		if (!this.customerRepository.existsByEmail(customer.getEmail())) {
			this.customerRepository.save(customer);
			customer.setPassword(null);
			return new Output("Success", "Customer Signed Up Successfully", customer);
		}
		return new Output("Failed", "SignUp Failed , Customer Already Exists", null);
	}

	@PostMapping(path = "/loginCustomer", consumes = "application/json", produces = "application/json")
	public Output loginCustomer(@RequestBody Map<String, String> customer) {
		Customer customerFound = customerRepository.findByEmailAndPassword(customer.get("email"), customer.get("password"));
		if (customerFound != null) {
			return new Output("Success", "Customer LoggedIn Successfully", customerFound);

		}
		return new Output("Failed", "Login Failed , Username Or Password Wrong", null);
	}

	@PostMapping(path = "/searchFarmerByLocation", consumes = "application/json", produces = "application/json")
	public Output searchFarmerByLocation(@RequestBody Map<String, Integer> input) {
		int pincode = input.get("pincode");
		List<Locations> famerFoundList = locationsRepository.findAllByDeliveryPincode(pincode);
		List<FarmerByLocationOutput> result = new ArrayList<FarmerByLocationOutput>();
		if(!famerFoundList.isEmpty()){
			for (Locations locations : famerFoundList) {
				result.add(new FarmerByLocationOutput(locations.getFarmerName(), locations.getFarmerId(),
						locations.getFarmerRating()));
			}
			return new Output("Success", "Farmers Fetched Easily", result);
		}
		return new Output("Failed","No Farmers Deliver At This Location",null);
		
	}

	public class FarmerByLocationOutput {
		private String farmerName;
		private long farmerId;
		private double farmerRating;

		public FarmerByLocationOutput(String farmerName, long farmerId, double farmerRating) {
			this.farmerName = farmerName;
			this.farmerId = farmerId;
			this.farmerRating = farmerRating;
		}

		public String getFarmerName() {
			return this.farmerName;
		}

		public void setFarmerName(String farmerName) {
			this.farmerName = farmerName;
		}

		public long getFarmerId() {
			return this.farmerId;
		}

		public void setFarmerID(long farmerId) {
			this.farmerId = farmerId;
		}

		public double getFarmerRating() {
			return this.farmerRating;
		}

		public void serFarmerRating(double farmerRating) {
			this.farmerRating = farmerRating;
		}
	}

	@PostMapping(path = "/getProductsFromFarmer", consumes = "application/json", produces = "application/json")
	public Output getProductsFromFarmer(@RequestBody Map<String, Integer> inputData) {
		long farmerId = inputData.get("farmerId");
		if(farmerRepository.existsById(farmerId)){
			Farmer farmer = farmerRepository.findById(farmerId).get();
			List<Product> productList = productRepository.findAllByFarmer(farmer);
			if(!productList.isEmpty()){
				return new Output("Success","Farmer Products Fetched Successfully",productList);
			}
			return new Output ("Failed","No Products Available For The Farmer",null);
		}
		return new Output("Failed","Farmer Not Found",null);
	}

	@PostMapping(path="/addProductToCart",consumes = "application/json", produces = "application/json")
	public Output addToCart(@RequestBody Map<String,Integer> item){
		long customerId = item.get("customerId");
		long productId = item.get("productId");
		int productQuantity = item.get("productQuantity");
		if(customerRepository.existsById(customerId)){//customer Not Exist
			Customer customer = customerRepository.findById(customerId).get();
			if(productRepository.existsById(productId)){//product Not Exist
				Product product = productRepository.findById(productId).get();
				if(!cartRepository.existsByProductAndCustomerAndIsOrdered(product,customer,IsOrdered.NO)){
					cartRepository.save(new Cart(product,productQuantity,customer));
					return new Output("Success","Item Added To Cart Successfully",null);
				}
				else{
					Cart updateCartItem = cartRepository.findByProductAndCustomerAndIsOrdered(product,customer,IsOrdered.NO);
					updateCartItem.setproductQuantity(productQuantity);
					cartRepository.save(updateCartItem);
					//write another api to update from the cart
					return new Output("Success","Item Updated To Cart Successfully",null);
				}
			}
			else{
				return new Output("Failed","Product Does Not Exists",null);
			}
		}
		return new Output("Failed","Customer Does Not Exist",null);
	}

	@PostMapping(path="/placeOrder",consumes = "application/json",produces = "application/json")
	public Output placeOrder(@RequestBody Map<String,Integer> orderInfo){
		long customerId = orderInfo.get("customerId");
		if(customerRepository.existsById(customerId)){
			Customer customer = customerRepository.findById(customerId).get();
			if(cartRepository.existsByCustomer(customer)){
				List<Cart> allCartItems = cartRepository.findAllByCustomer(customer);
				orderRepository.save(new Order());
				//farmer
				//customer
				//courier
				//delivery Status
				
			}
			else{
				return new Output("Failed","Cart Empty",null);
			}
		}
		return new Output("Failed","Customer Does Not Exist",null);
	}
}
