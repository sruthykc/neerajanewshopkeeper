package com.diviso.graeshoppe.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.diviso.graeshoppe.client.customer.model.Customer;

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
	public Page<Customer> findAllCustomersByName(String searchTerm, Pageable pageable);

}
