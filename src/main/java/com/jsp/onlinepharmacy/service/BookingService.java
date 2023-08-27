package com.jsp.onlinepharmacy.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.onlinepharmacy.DAO.BookingDao;
import com.jsp.onlinepharmacy.DAO.CustomerDao;
import com.jsp.onlinepharmacy.DAO.MedicineDao;
import com.jsp.onlinepharmacy.DTO.BookingDto;
import com.jsp.onlinepharmacy.entity.Booking;
import com.jsp.onlinepharmacy.entity.Customer;
import com.jsp.onlinepharmacy.entity.Medicine;
import com.jsp.onlinepharmacy.exception.CustomerIdNotFoundException;
import com.jsp.onlinepharmacy.exception.MedicineIdNotFoundException;
import com.jsp.onlinepharmacy.util.ResponseStructure;

@Service
public class BookingService {

	@Autowired
	private BookingDao dao;

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private MedicineDao medicineDao;

	@Autowired
	private ModelMapper modelMapper;

	public ResponseEntity<ResponseStructure<Booking>> addBooking(int customerId, int medicineId,
			BookingDto bookingDto) {
		Booking booking = this.modelMapper.map(bookingDto, Booking.class);
		Customer dbCustomer = customerDao.findCustomerById(customerId);
		if (dbCustomer != null) {
			// customer is present
			Medicine dbMedicine = medicineDao.findMedicineById(medicineId);
			if (dbMedicine != null) {
				List<Medicine> medicines = new ArrayList<Medicine>();
				medicines.add(dbMedicine);
				booking.setCustomer(dbCustomer);
				booking.setMedicines(medicines);

				// update customer also because customer is having bi-direction
				List<Booking> bookings = new ArrayList<Booking>();
				bookings.add(booking);
				dbCustomer.setBookings(bookings);
				customerDao.updateCustomer(customerId, dbCustomer);
				dbMedicine.setStockQuantity(dbMedicine.getStockQuantity()-booking.getQuantity());
				Booking dbBooking = dao.saveBooking(booking);
				
				ResponseStructure<Booking> structure = new ResponseStructure<Booking>();
				structure.setMessage("Booking added successfully");
				structure.setStatus(HttpStatus.CREATED.value());
				structure.setData(dbBooking);
				return new ResponseEntity<ResponseStructure<Booking>>(structure, HttpStatus.CREATED);

			} else {
				throw new MedicineIdNotFoundException("sorry failed to add booking ");
			}
		} else {
			throw new CustomerIdNotFoundException("Sorry failed to add Booking");
		}

	}
}
