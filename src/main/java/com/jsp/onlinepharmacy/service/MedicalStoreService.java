package com.jsp.onlinepharmacy.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.onlinepharmacy.DAO.AddressDao;
import com.jsp.onlinepharmacy.DAO.AdminDao;
import com.jsp.onlinepharmacy.DAO.MedicalStoreDao;
import com.jsp.onlinepharmacy.DTO.AddressDto;
import com.jsp.onlinepharmacy.DTO.AdminDto;
import com.jsp.onlinepharmacy.DTO.MedicalStoreDto;
import com.jsp.onlinepharmacy.entity.Address;
import com.jsp.onlinepharmacy.entity.Admin;
import com.jsp.onlinepharmacy.entity.MedicalStore;
import com.jsp.onlinepharmacy.exception.AddressIdNotFoundException;
import com.jsp.onlinepharmacy.exception.AdminIdNotFoundException;
import com.jsp.onlinepharmacy.exception.MedicalStoreIdNotFoundException;
import com.jsp.onlinepharmacy.util.ResponseStructure;

@Service
public class MedicalStoreService {

	@Autowired
	private MedicalStoreDao storeDao;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AdminDao adminDao;

	@Autowired
	private AddressDao addressDao;

	public ResponseEntity<ResponseStructure<MedicalStoreDto>> saveMedicalStore(int adminId, int addressId,
			MedicalStoreDto medicalStoreDto) {

		MedicalStore medicalStore = this.modelMapper.map(medicalStoreDto, MedicalStore.class);
		Admin dbAdmin = adminDao.findAdminById(adminId);
		if (dbAdmin != null) {
			medicalStore.setAdmin(dbAdmin);
			Address dbAddress = addressDao.findAddressById(addressId);
			if (dbAddress != null) {
				medicalStore.setAddress(dbAddress);
				MedicalStore dbMedicalStore = storeDao.saveMedicalStore(medicalStore);

				Address dbMedicalAddress = dbMedicalStore.getAddress();
				AddressDto addressDto = this.modelMapper.map(dbMedicalAddress, AddressDto.class);

				Admin dbMedicalStoreAdmin = dbMedicalStore.getAdmin();
				AdminDto adminDto = this.modelMapper.map(dbMedicalStoreAdmin, AdminDto.class);

				MedicalStoreDto storeDto = this.modelMapper.map(dbMedicalStore, MedicalStoreDto.class);
				storeDto.setAddressDto(addressDto);
				storeDto.setAdminDto(adminDto);

				ResponseStructure<MedicalStoreDto> structure = new ResponseStructure<MedicalStoreDto>();
				structure.setMessage("medical Store saved successfully");
				structure.setStatus(HttpStatus.CREATED.value());
				structure.setData(storeDto);
				return new ResponseEntity<ResponseStructure<MedicalStoreDto>>(structure, HttpStatus.CREATED);
			} else {
				throw new AddressIdNotFoundException("sorry failed to add Medical Store");
			}

		} else {
			throw new AdminIdNotFoundException("sorry failed to add Medical Store");
		}

	}

	public ResponseEntity<ResponseStructure<MedicalStoreDto>> updateMedicalStore(int storeId,
			MedicalStore medicalStore) {
		MedicalStore dbMedicalStore = storeDao.updateMedicalStore(storeId, medicalStore);
		if (dbMedicalStore != null) {

			Address dbMedicalAddress = dbMedicalStore.getAddress();
			AddressDto addressDto = this.modelMapper.map(dbMedicalAddress, AddressDto.class);

			Admin dbMedicalStoreAdmin = dbMedicalStore.getAdmin();
			AdminDto adminDto = this.modelMapper.map(dbMedicalStoreAdmin, AdminDto.class);

			MedicalStoreDto storeDto = this.modelMapper.map(dbMedicalStore, MedicalStoreDto.class);
			storeDto.setAddressDto(addressDto);
			storeDto.setAdminDto(adminDto);

			ResponseStructure<MedicalStoreDto> structure = new ResponseStructure<MedicalStoreDto>();
			structure.setMessage("medical Store updated successfully");
			structure.setStatus(HttpStatus.OK.value());
			structure.setData(storeDto);
			return new ResponseEntity<ResponseStructure<MedicalStoreDto>>(structure, HttpStatus.OK);
		} else {
			throw new MedicalStoreIdNotFoundException("Sorry failed to update medical Store");
		}

	}

	public ResponseEntity<ResponseStructure<MedicalStoreDto>> getMedicalStoreById(int storeId) {
		MedicalStore dbMedicalStore = storeDao.findMedicalStoreById(storeId);
		if (dbMedicalStore != null) {
			Address dbMedicalAddress = dbMedicalStore.getAddress();
			AddressDto addressDto = this.modelMapper.map(dbMedicalAddress, AddressDto.class);

			Admin dbMedicalStoreAdmin = dbMedicalStore.getAdmin();
			AdminDto adminDto = this.modelMapper.map(dbMedicalStoreAdmin, AdminDto.class);

			MedicalStoreDto storeDto = this.modelMapper.map(dbMedicalStore, MedicalStoreDto.class);
			storeDto.setAddressDto(addressDto);
			storeDto.setAdminDto(adminDto);

			ResponseStructure<MedicalStoreDto> structure = new ResponseStructure<MedicalStoreDto>();
			structure.setMessage("medical Store fetched successfully");
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setData(storeDto);
			return new ResponseEntity<ResponseStructure<MedicalStoreDto>>(structure, HttpStatus.FOUND);
		} else {
			throw new MedicalStoreIdNotFoundException("Sorry failed to update medical Store");
		}

	}

	public ResponseEntity<ResponseStructure<MedicalStoreDto>> deleteMedicalStoreById(int storeId) {
		MedicalStore dbMedicalStore = storeDao.deleteMedicalStoreById(storeId);
		if (dbMedicalStore != null) {
			
			Address dbMedicalAddress = dbMedicalStore.getAddress();
			AddressDto addressDto = this.modelMapper.map(dbMedicalAddress, AddressDto.class);

			Admin dbMedicalStoreAdmin = dbMedicalStore.getAdmin();
			AdminDto adminDto = this.modelMapper.map(dbMedicalStoreAdmin, AdminDto.class);

			MedicalStoreDto storeDto = this.modelMapper.map(dbMedicalStore, MedicalStoreDto.class);
			storeDto.setAddressDto(addressDto);
			storeDto.setAdminDto(adminDto);   //in case of bi-direction we need not do these things
			
			ResponseStructure<MedicalStoreDto> structure = new ResponseStructure<MedicalStoreDto>();
			structure.setMessage("medical Store deleted successfully");
			structure.setStatus(HttpStatus.GONE.value());
			structure.setData(storeDto);
			return new ResponseEntity<ResponseStructure<MedicalStoreDto>>(structure, HttpStatus.GONE);
		} else {
			throw new MedicalStoreIdNotFoundException("Sorry failed to delete medical Store");
		}
	}

}
