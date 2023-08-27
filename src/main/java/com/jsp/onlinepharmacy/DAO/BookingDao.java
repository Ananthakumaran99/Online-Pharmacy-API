package com.jsp.onlinepharmacy.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.onlinepharmacy.entity.Booking;
import com.jsp.onlinepharmacy.repository.BookingRepo;

@Repository
public class BookingDao {

	
	@Autowired
	private BookingRepo repo;
	
	public Booking saveBooking(Booking booking) {
		return repo.save(booking);
	}
}
