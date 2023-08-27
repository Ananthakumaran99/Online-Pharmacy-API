package com.jsp.onlinepharmacy.DTO;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class StaffDto {

	private int staffId;
	private String staffName;
	private String email;
	
	@ManyToOne
	private AdminDto adminDto;
	
	@OneToOne
	private MedicalStoreDto storeDto;
}
