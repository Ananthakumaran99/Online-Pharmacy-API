package com.jsp.onlinepharmacy.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.onlinepharmacy.DAO.AddressDao;
import com.jsp.onlinepharmacy.DAO.CustomerDao;
import com.jsp.onlinepharmacy.DTO.AddressDto;
import com.jsp.onlinepharmacy.DTO.CustomerDto;
import com.jsp.onlinepharmacy.entity.Address;
import com.jsp.onlinepharmacy.entity.Customer;
import com.jsp.onlinepharmacy.exception.AddressAlreadyMappedToCustomerException;
import com.jsp.onlinepharmacy.exception.AddressIdNotFoundException;
import com.jsp.onlinepharmacy.exception.CustomerIdNotFoundException;
import com.jsp.onlinepharmacy.util.ResponseStructure;

@Service
public class CustomerService {

	@Autowired
	private CustomerDao dao;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AddressDao addressDao;

	public ResponseEntity<ResponseStructure<CustomerDto>> saveCustomer(int addressId, Customer customer) {

		Address dbAddress = addressDao.findAddressById(addressId);

		if (dbAddress != null) {

			if (dbAddress.getCustomer() != null) {
				throw new AddressAlreadyMappedToCustomerException("Sorry");
			}
			dbAddress.setCustomer(customer);
			List<Address> addresses = new ArrayList<>();
			addresses.add(dbAddress);
			customer.setAddresses(addresses);
			Customer dbCustomer = dao.saveCustomer(customer);
			CustomerDto customerDto = this.modelMapper.map(dbCustomer, CustomerDto.class);
			List<AddressDto> addressDtos;
			for (Address address : dbCustomer.getAddresses()) {
				AddressDto addressDto = this.modelMapper.map(address, AddressDto.class);
				addressDtos = new ArrayList<AddressDto>();
				addressDtos.add(addressDto);
				customerDto.setAddresses(addressDtos);
			}
			customerDto.setBookings(null);
			ResponseStructure<CustomerDto> structure = new ResponseStructure<CustomerDto>();
			structure.setMessage("customer saved successfully");
			structure.setStatus(HttpStatus.CREATED.value());
			structure.setData(customerDto);
			return new ResponseEntity<ResponseStructure<CustomerDto>>(structure, HttpStatus.CREATED);

		} else {
			throw new AddressIdNotFoundException("sorry failed to save customer");
		}
	}

	public ResponseEntity<ResponseStructure<CustomerDto>> updateCustomer(int customerId, Customer customer) {
		Customer dbCustomer = dao.updateCustomer(customerId, customer);
		if (dbCustomer != null) {
			CustomerDto customerDto = this.modelMapper.map(dbCustomer, CustomerDto.class);
			List<AddressDto> addressDtos;
			for (Address address : dbCustomer.getAddresses()) {
				AddressDto addressDto = this.modelMapper.map(address, AddressDto.class);
				addressDtos = new ArrayList<AddressDto>();
				addressDtos.add(addressDto);
				customerDto.setAddresses(addressDtos);
			}
			customerDto.setBookings(null);
			ResponseStructure<CustomerDto> structure = new ResponseStructure<CustomerDto>();
			structure.setMessage("customer data updated successfully");
			structure.setStatus(HttpStatus.OK.value());
			structure.setData(customerDto);
			return new ResponseEntity<ResponseStructure<CustomerDto>>(structure, HttpStatus.OK);
		} else {
			throw new CustomerIdNotFoundException("sorry failed to update customer");
		}
	}

	public ResponseEntity<ResponseStructure<CustomerDto>> getCustomer(int customerId) {
		Customer dbCustomer = dao.findCustomerById(customerId);
		if (dbCustomer != null) {
			CustomerDto customerDto = this.modelMapper.map(dbCustomer, CustomerDto.class);
			List<AddressDto> addressDtos;
			for (Address address : dbCustomer.getAddresses()) {
				AddressDto addressDto = this.modelMapper.map(address, AddressDto.class);
				addressDtos = new ArrayList<AddressDto>();
				addressDtos.add(addressDto);
				customerDto.setAddresses(addressDtos);
			}
			customerDto.setBookings(null);
			ResponseStructure<CustomerDto> structure = new ResponseStructure<CustomerDto>();
			structure.setMessage("customer data fetched successfully");
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setData(customerDto);
			return new ResponseEntity<ResponseStructure<CustomerDto>>(structure, HttpStatus.FOUND);
		} else {
			throw new CustomerIdNotFoundException("sorry failed to fetch customer");
		}
	}

	public ResponseEntity<ResponseStructure<CustomerDto>> deleteCustomer(int customerId) {
		Customer dbCustomer=dao.deleteCustomerById(customerId);
		if (dbCustomer != null) {
			CustomerDto customerDto = this.modelMapper.map(dbCustomer, CustomerDto.class);
			List<AddressDto> addressDtos;
			for (Address address : dbCustomer.getAddresses()) {
				AddressDto addressDto = this.modelMapper.map(address, AddressDto.class);
				addressDtos = new ArrayList<AddressDto>();
				addressDtos.add(addressDto);
				customerDto.setAddresses(addressDtos);
			}
			customerDto.setBookings(null);
			ResponseStructure<CustomerDto> structure = new ResponseStructure<CustomerDto>();
			structure.setMessage("customer data deleted successfully");
			structure.setStatus(HttpStatus.GONE.value());
			structure.setData(customerDto);
			return new ResponseEntity<ResponseStructure<CustomerDto>>(structure, HttpStatus.GONE);
		} else {
			throw new CustomerIdNotFoundException("sorry failed to delete customer");
		}	}
}
