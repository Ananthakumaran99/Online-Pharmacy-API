package com.jsp.onlinepharmacy.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.onlinepharmacy.entity.Address;
import com.jsp.onlinepharmacy.entity.Customer;
import com.jsp.onlinepharmacy.repository.CustomerRepo;

@Repository
public class CustomerDao {

	@Autowired
	private CustomerRepo repo;

	public Customer saveCustomer(Customer customer) {
		return repo.save(customer);
	}

	public Customer updateCustomer(int customerId, Customer customer) {
		Optional<Customer> optional = repo.findById(customerId);
		if (optional.isPresent()) {
			customer.setCustomerId(customerId);
			customer.setAddresses(optional.get().getAddresses());
			customer.setBookings(optional.get().getBookings());

			return repo.save(customer);
		} else {
			return null;
		}
	}

	public Customer findCustomerById(int customerId) {
		Optional<Customer> optional = repo.findById(customerId);
		if (optional.isPresent()) {
			return optional.get();

		}
		return null;
	}

	public Customer deleteCustomerById(int customerId) {
		Optional<Customer> optional = repo.findById(customerId);
		if (optional.isPresent()) {
			Customer customer = optional.get();
			List<Address> addresses = customer.getAddresses();
			for (Address address : addresses) {
				address.setCustomer(null);
			}
			repo.delete(customer);
			return customer;

		}
		return null;
	}

}
