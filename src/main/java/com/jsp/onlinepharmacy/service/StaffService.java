package com.jsp.onlinepharmacy.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.onlinepharmacy.DAO.AdminDao;
import com.jsp.onlinepharmacy.DAO.MedicalStoreDao;
import com.jsp.onlinepharmacy.DAO.StaffDao;
import com.jsp.onlinepharmacy.DTO.AdminDto;
import com.jsp.onlinepharmacy.DTO.MedicalStoreDto;
import com.jsp.onlinepharmacy.DTO.StaffDto;
import com.jsp.onlinepharmacy.entity.Admin;
import com.jsp.onlinepharmacy.entity.MedicalStore;
import com.jsp.onlinepharmacy.entity.Staff;
import com.jsp.onlinepharmacy.exception.AdminIdNotFoundException;
import com.jsp.onlinepharmacy.exception.MedicalStoreIdNotFoundException;
import com.jsp.onlinepharmacy.exception.StaffIdNOtFoundException;
import com.jsp.onlinepharmacy.util.ResponseStructure;

@Service
public class StaffService {

	@Autowired
	private StaffDao dao;

	@Autowired
	private MedicalStoreDao storeDao;

	@Autowired
	private AdminDao adminDao;

	@Autowired
	private ModelMapper modelMapper;

	public ResponseEntity<ResponseStructure<StaffDto>> saveStaff(int adminId, int storeId, Staff staff) {

		MedicalStore dbMedicalStore = storeDao.findMedicalStoreById(storeId);
		if (dbMedicalStore != null) {
			staff.setMedicalStore(dbMedicalStore);
			Admin dbAdmin = adminDao.findAdminById(adminId);
			if (dbAdmin != null) {
				staff.setAdmin(dbAdmin);
				Staff dbStaff = dao.saveStaff(staff);
				StaffDto dbStaffDto = this.modelMapper.map(dbStaff, StaffDto.class);
				dbStaffDto.setAdminDto(this.modelMapper.map(dbStaff.getAdmin(), AdminDto.class));
				dbStaffDto.setStoreDto(this.modelMapper.map(dbStaff.getMedicalStore(), MedicalStoreDto.class));

				ResponseStructure<StaffDto> structure = new ResponseStructure<StaffDto>();
				structure.setMessage("medical Store saved successfully");
				structure.setStatus(HttpStatus.CREATED.value());
				structure.setData(dbStaffDto);
				return new ResponseEntity<ResponseStructure<StaffDto>>(structure, HttpStatus.CREATED);

			} else {
				throw new AdminIdNotFoundException("sorry failed to add staff");
			}
		} else {
			throw new MedicalStoreIdNotFoundException("sorry failed to add staff");
		}
	}

	public ResponseEntity<ResponseStructure<StaffDto>> updateStaff(int staffId, Staff staff) {
		Staff dbStaff = dao.updateStaff(staffId, staff);
		if (dbStaff != null) {
			StaffDto dbStaffDto = this.modelMapper.map(dbStaff, StaffDto.class);
			dbStaffDto.setAdminDto(this.modelMapper.map(dbStaff.getAdmin(), AdminDto.class));
			dbStaffDto.setStoreDto(this.modelMapper.map(dbStaff.getMedicalStore(), MedicalStoreDto.class));

			ResponseStructure<StaffDto> structure = new ResponseStructure<StaffDto>();
			structure.setMessage("Staff updated successfully");
			structure.setStatus(HttpStatus.OK.value());
			structure.setData(dbStaffDto);
			return new ResponseEntity<ResponseStructure<StaffDto>>(structure, HttpStatus.OK);

		} else {
			throw new StaffIdNOtFoundException("sorry failed to update STaff");
		}

	}

	public ResponseEntity<ResponseStructure<StaffDto>> findStaffById(int staffId) {
		Staff dbStaff = dao.findStaffById(staffId);
		if (dbStaff != null) {
			StaffDto dbStaffDto = this.modelMapper.map(dbStaff, StaffDto.class);
			dbStaffDto.setAdminDto(this.modelMapper.map(dbStaff.getAdmin(), AdminDto.class));
			dbStaffDto.setStoreDto(this.modelMapper.map(dbStaff.getMedicalStore(), MedicalStoreDto.class));

			ResponseStructure<StaffDto> structure = new ResponseStructure<StaffDto>();
			structure.setMessage("Staff Data fetched successfully");
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setData(dbStaffDto);
			return new ResponseEntity<ResponseStructure<StaffDto>>(structure, HttpStatus.FOUND);

		} else {
			throw new StaffIdNOtFoundException("sorry failed to fetch Staff");
		}
	}

	public ResponseEntity<ResponseStructure<StaffDto>> deleteStaffById(int staffId) {
		Staff dbStaff = dao.deleteStaffById(staffId);
		if (dbStaff != null) {
			StaffDto dbStaffDto = this.modelMapper.map(dbStaff, StaffDto.class);
			dbStaffDto.setAdminDto(this.modelMapper.map(dbStaff.getAdmin(), AdminDto.class));
			dbStaffDto.setStoreDto(this.modelMapper.map(dbStaff.getMedicalStore(), MedicalStoreDto.class));

			ResponseStructure<StaffDto> structure = new ResponseStructure<StaffDto>();
			structure.setMessage("Staff Data deleted successfully");
			structure.setStatus(HttpStatus.GONE.value());
			structure.setData(dbStaffDto);
			return new ResponseEntity<ResponseStructure<StaffDto>>(structure, HttpStatus.GONE);

		} else {
			throw new StaffIdNOtFoundException("sorry failed to delete Staff");
		}
	}

}
