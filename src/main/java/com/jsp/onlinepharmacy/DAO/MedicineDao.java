package com.jsp.onlinepharmacy.DAO;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.onlinepharmacy.entity.Medicine;
import com.jsp.onlinepharmacy.repository.MedicineRepo;

@Repository
public class MedicineDao {

	@Autowired
	private MedicineRepo repo;

	public Medicine addMedicine(Medicine medicine) {
		return repo.save(medicine);
	}

	public Medicine updateMedicine(int medicineId, Medicine medicine) {
		Optional<Medicine> optional = repo.findById(medicineId);
		if (optional.isPresent()) {

			Medicine oldMedicine = optional.get();
			medicine.setMedicineId(medicineId);
			medicine.setMedicalStore(oldMedicine.getMedicalStore());
			return repo.save(medicine);
		} else {
			return null;
		}
	}

	public Medicine findMedicineById(int medicineId) {
		Optional<Medicine> optional = repo.findById(medicineId);
		if (optional.isPresent()) {
			return optional.get();

		} else {
			return null;
		}
	}

	public Medicine deleteMedicineById(int medicineId) {
		Optional<Medicine> optional = repo.findById(medicineId);
		if (optional.isPresent()) {
			repo.delete(optional.get());
			return optional.get();

		} else {
			return null;
		}
	}

}
