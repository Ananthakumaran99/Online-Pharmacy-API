package com.jsp.onlinepharmacy.DAO;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.onlinepharmacy.entity.Address;
import com.jsp.onlinepharmacy.repository.AddressRepo;

@Repository
public class AddressDao {

	@Autowired
	private AddressRepo repo;

	public Address saveAddress(Address address) {
		return repo.save(address);
	}

	public Address findAddressById(int addressId) {
		Optional<Address> optional = repo.findById(addressId);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	public Address updateAddessById(int addressId, Address address) {
		Optional<Address> optional = repo.findById(addressId);
		if (optional.isPresent()) {
			address.setAddressId(addressId);
			return repo.save(address);
		}
		return null;
	}

	public Address deleteAddessById(int addressId) {
		Optional<Address> optional = repo.findById(addressId);
		if (optional.isPresent()) {
			Address address = optional.get();
			repo.delete(address);
			return optional.get();
		}
		return null;
	}

}
