package com.diviso.graeshoppe.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.diviso.graeshoppe.client.customer.model.Contact;

public interface ContactService {

	List<Contact> findContactsByMobileNumber(Long mobileNumber, Pageable page);

	
}
