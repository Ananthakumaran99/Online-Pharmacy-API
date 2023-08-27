package com.jsp.onlinepharmacy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.onlinepharmacy.DAO.AddressDao;
import com.jsp.onlinepharmacy.DTO.AddressDto;
import com.jsp.onlinepharmacy.entity.Address;
import com.jsp.onlinepharmacy.exception.AddressIdNotFoundException;
import com.jsp.onlinepharmacy.util.ResponseStructure;

@Service
public class AddressService {

	@Autowired
	private AddressDao dao;

	public ResponseEntity<ResponseStructure<AddressDto>> saveAddress(Address address) {
		Address dbAddress = dao.saveAddress(address);
		AddressDto dto = new AddressDto();
		dto.setAddressId(dbAddress.getAddressId());
		dto.setStreetName(dbAddress.getStreetName());
		dto.setCity(dbAddress.getCity());
		dto.setState(dbAddress.getState());
		dto.setPincode(dbAddress.getPincode());
		ResponseStructure<AddressDto> structure = new ResponseStructure<AddressDto>();
		structure.setMessage("address saved successfully");
		structure.setStatus(HttpStatus.CREATED.value());
		structure.setData(dto);

		return new ResponseEntity<ResponseStructure<AddressDto>>(structure, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<AddressDto>> findAddressById(int addressId) {
		Address dbAddress = dao.findAddressById(addressId);
		if (dbAddress != null) {
			AddressDto dto = new AddressDto();
			dto.setAddressId(dbAddress.getAddressId());
			dto.setStreetName(dbAddress.getStreetName());
			dto.setCity(dbAddress.getCity());
			dto.setState(dbAddress.getState());
			dto.setPincode(dbAddress.getPincode());
			ResponseStructure<AddressDto> structure = new ResponseStructure<AddressDto>();
			structure.setMessage("address fetched successfully");
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setData(dto);
			return new ResponseEntity<ResponseStructure<AddressDto>>(structure, HttpStatus.FOUND);
		}
		else {
			throw new AddressIdNotFoundException("sorry failed to fetch the data");
		}
		

	}

	public ResponseEntity<ResponseStructure<AddressDto>> updateAddressById(int addressId, Address address) {
		Address dbAddress = dao.updateAddessById(addressId, address);
		if (dbAddress != null) {
			AddressDto dto = new AddressDto();
			dto.setAddressId(dbAddress.getAddressId());
			dto.setStreetName(dbAddress.getStreetName());
			dto.setCity(dbAddress.getCity());
			dto.setState(dbAddress.getState());
			dto.setPincode(dbAddress.getPincode());
			ResponseStructure<AddressDto> structure = new ResponseStructure<AddressDto>();
			structure.setMessage("address updated successfully");
			structure.setStatus(HttpStatus.OK.value());
			structure.setData(dto);
			return new ResponseEntity<ResponseStructure<AddressDto>>(structure, HttpStatus.OK);
		}

		else {
			throw new AddressIdNotFoundException("sorry failed to update the data");
		}

	}

	public ResponseEntity<ResponseStructure<AddressDto>> deleteAddressById(int addressId) {
		Address dbAddress = dao.deleteAddessById(addressId);
		if (dbAddress != null) {
			AddressDto dto = new AddressDto();
			dto.setAddressId(dbAddress.getAddressId());
			dto.setStreetName(dbAddress.getStreetName());
			dto.setCity(dbAddress.getCity());
			dto.setState(dbAddress.getState());
			dto.setPincode(dbAddress.getPincode());
			ResponseStructure<AddressDto> structure = new ResponseStructure<AddressDto>();
			structure.setMessage("address deleted successfully");
			structure.setStatus(HttpStatus.GONE.value());
			structure.setData(dto);
			return new ResponseEntity<ResponseStructure<AddressDto>>(structure, HttpStatus.GONE);
		}

		else {
			throw new AddressIdNotFoundException("sorry failed to delete the data");
		}
	}

}
