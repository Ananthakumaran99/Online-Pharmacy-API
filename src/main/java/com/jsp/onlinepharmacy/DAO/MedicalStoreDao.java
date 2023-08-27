package com.jsp.onlinepharmacy.DAO;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.onlinepharmacy.entity.MedicalStore;
import com.jsp.onlinepharmacy.repository.MedicalStoreRepo;

@Repository
public class MedicalStoreDao {

	@Autowired
	private MedicalStoreRepo repo;

	public MedicalStore saveMedicalStore(MedicalStore medicalStore) {

		return repo.save(medicalStore);
	}

	public MedicalStore updateMedicalStore(int storeId, MedicalStore medicalStore) {
		Optional<MedicalStore> optional = repo.findById(storeId);
		if (optional.isPresent()) {
			MedicalStore oldMedicalStore = optional.get();// in this we are having the old medical store values with
															// address and admin things
			medicalStore.setStoreId(storeId);
			medicalStore.setAddress(oldMedicalStore.getAddress());// we are setting the old address to the updated one
			medicalStore.setAdmin(oldMedicalStore.getAdmin());// we are setting the old Admin to the updated one
			return repo.save(medicalStore);
		}
		return null;
	}

	public MedicalStore findMedicalStoreById(int storeId) {
		Optional<MedicalStore> optional = repo.findById(storeId);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	public MedicalStore deleteMedicalStoreById(int storeId) {
		Optional<MedicalStore> optional = repo.findById(storeId);
		if (optional.isPresent()) {

			MedicalStore medicalStore = optional.get();
//			medicalStore.setAdmin(null);
//			medicalStore.setAddress(null); if it is bi-direction we can set it as null
			repo.delete(medicalStore);
			return medicalStore;

		}
		return null;
	}
}
