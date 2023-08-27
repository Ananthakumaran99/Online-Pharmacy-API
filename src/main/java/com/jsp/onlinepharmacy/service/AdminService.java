package com.jsp.onlinepharmacy.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.onlinepharmacy.DAO.AdminDao;
import com.jsp.onlinepharmacy.DTO.AdminDto;
import com.jsp.onlinepharmacy.entity.Admin;
import com.jsp.onlinepharmacy.exception.AdminIdNotFoundException;
import com.jsp.onlinepharmacy.util.ResponseStructure;

@Service
public class AdminService {

	@Autowired
	private AdminDao dao;

	@Autowired
	private ModelMapper modelMapper;

	public ResponseEntity<ResponseStructure<AdminDto>> saveAdmin(Admin admin) {
		Admin dbAdmin = dao.saveAdmin(admin);
		AdminDto adminDto = this.modelMapper.map(dbAdmin, AdminDto.class);
		ResponseStructure<AdminDto> structure = new ResponseStructure<AdminDto>();
		structure.setMessage("Admin saved successfully");
		structure.setStatus(HttpStatus.CREATED.value());
		structure.setData(adminDto);

		return new ResponseEntity<ResponseStructure<AdminDto>>(structure, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<AdminDto>> findAdminById(int adminId) {
		Admin dbAdmin = dao.findAdminById(adminId);
		if (dbAdmin != null) {
			AdminDto adminDto = this.modelMapper.map(dbAdmin, AdminDto.class);
			ResponseStructure<AdminDto> structure = new ResponseStructure<AdminDto>();
			structure.setMessage("AdminData fetched successfully");
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setData(adminDto);

			return new ResponseEntity<ResponseStructure<AdminDto>>(structure, HttpStatus.FOUND);

		} else {
			throw new AdminIdNotFoundException("sorry failed to fetch Admin Data");
		}

	}

	public ResponseEntity<ResponseStructure<AdminDto>> updateAdminById(int adminId, Admin admin) {
		Admin dbAdmin = dao.updateAdminById(adminId, admin);
		if (dbAdmin != null) {
			AdminDto adminDto = this.modelMapper.map(dbAdmin, AdminDto.class);
			ResponseStructure<AdminDto> structure = new ResponseStructure<AdminDto>();
			structure.setMessage("AdminData updated successfully");
			structure.setStatus(HttpStatus.OK.value());
			structure.setData(adminDto);

			return new ResponseEntity<ResponseStructure<AdminDto>>(structure, HttpStatus.OK);

		} else {
			throw new AdminIdNotFoundException("sorry failed to update Admin Data");
		}
	}

	public ResponseEntity<ResponseStructure<AdminDto>> deleteAdminById(int adminId) {
		Admin dbAdmin = dao.deleteAdminById(adminId);
		if (dbAdmin != null) {
			AdminDto adminDto = this.modelMapper.map(dbAdmin, AdminDto.class);
			ResponseStructure<AdminDto> structure = new ResponseStructure<AdminDto>();
			structure.setMessage("AdminData deleted successfully");
			structure.setStatus(HttpStatus.GONE.value());
			structure.setData(adminDto);

			return new ResponseEntity<ResponseStructure<AdminDto>>(structure, HttpStatus.GONE);

		} else {
			throw new AdminIdNotFoundException("sorry failed to delete Admin Data");
		}
	}
}
