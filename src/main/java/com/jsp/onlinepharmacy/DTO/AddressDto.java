package com.jsp.onlinepharmacy.DTO;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AddressDto {
		private int addressId;
		private String StreetName;
		private String city;
		private String state;
		private long pincode;
	}

