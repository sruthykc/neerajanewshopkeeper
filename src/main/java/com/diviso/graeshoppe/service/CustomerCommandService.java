package com.diviso.graeshoppe.service;

import org.springframework.http.ResponseEntity;

import com.diviso.graeshoppe.client.aggregators.CustomerAggregator;
import com.diviso.graeshoppe.client.customer.model.ContactDTO;
import com.diviso.graeshoppe.client.customer.model.CustomerDTO;

public interface CustomerCommandService {

	public ResponseEntity<CustomerDTO> createCustomer(CustomerAggregator customerAggregator);
	
	public ResponseEntity<CustomerDTO> updateCustomer(CustomerDTO customerDTO);

	public void deleteCustomer(Long id);

	public ResponseEntity<ContactDTO> createContact(ContactDTO contact);
	
	public ResponseEntity<ContactDTO> updateContact(ContactDTO contact);
	
	public ResponseEntity<Void> deleteContact(Long id);
	
}
