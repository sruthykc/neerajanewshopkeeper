package com.diviso.graeshoppe.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.diviso.graeshoppe.client.customer.model.ContactDTO;
import com.diviso.graeshoppe.client.customer.model.Customer;
import com.diviso.graeshoppe.client.customer.model.CustomerDTO;

public interface CustomerQueryService {
	
	public Page<Customer> findAllCustomers(Pageable pageable);


	public Page<Customer> findAllCustomersByName(String name, Pageable pageable);

	public CustomerDTO findCustomerById(Long id);
	
	public ContactDTO findContactById( Long id) ;
}
