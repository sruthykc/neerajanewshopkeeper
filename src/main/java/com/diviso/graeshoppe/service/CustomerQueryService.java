package com.diviso.graeshoppe.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.diviso.graeshoppe.client.customer.model.ContactDTO;
import com.diviso.graeshoppe.client.customer.model.Customer;
import com.diviso.graeshoppe.client.customer.model.CustomerDTO;

public interface CustomerQueryService {
	
	/**
	 * 	
	 * @param pageable
	 * @return
	 */
	public Page<Customer> findAllCustomers(Pageable pageable);

	/**
	 * 
	 * @param searchTerm
	 * @param pageable
	 * @return
	 */
	public Page<Customer> findAllCustomersByName(String name, Pageable pageable);

	//public Page<Customer> getAllCustomers(Pageable pageable);
	public /*ResponseEntity<CustomerDTO>*/CustomerDTO findCustomerById(Long id);
	
	public /*ResponseEntity<ContactDTO>*/ContactDTO findContactById( Long id) ;
}
