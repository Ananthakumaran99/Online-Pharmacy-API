package com.jsp.onlinepharmacy.DAO;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.onlinepharmacy.entity.Admin;
import com.jsp.onlinepharmacy.repository.AdminRepo;

@Repository
public class AdminDao {

	@Autowired
	private AdminRepo repo;

	public Admin saveAdmin(Admin admin) {
		return repo.save(admin);
	}

	public Admin findAdminById(int adminId) {
		Optional<Admin> optional = repo.findById(adminId);
		if (optional.isPresent()) {
			return optional.get();

		}
		return null;
	}

	public Admin updateAdminById(int adminId, Admin admin) {
		Optional<Admin> optional = repo.findById(adminId);
		if (optional.isPresent()) {
			admin.setAdminId(adminId);
			return  repo.save(admin);
		

		}
		return null;
	}

	public Admin deleteAdminById(int adminId) {
		Optional<Admin> optional = repo.findById(adminId);
		if (optional.isPresent()) {
			Admin admin = optional.get();
			repo.delete(admin);
			return admin;

		}
		return null;

	}
}
