package com.diviso.graeshoppe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.diviso.graeshoppe.client.aggregators.CustomerAggregator;
import com.diviso.graeshoppe.client.customer.api.ContactResourceApi;
import com.diviso.graeshoppe.client.customer.api.CustomerResourceApi;
import com.diviso.graeshoppe.client.customer.model.ContactDTO;
import com.diviso.graeshoppe.client.customer.model.Customer;
import com.diviso.graeshoppe.client.customer.model.CustomerDTO;
import com.diviso.graeshoppe.service.CustomerCommandService;

@Service
public class CustomerCommandServiceImpl implements CustomerCommandService{
	
	@Autowired
	private ContactResourceApi contactResourceApi;
	
	@Autowired
	private CustomerResourceApi customerResourceApi;


	@Override
	public ResponseEntity<CustomerDTO> createCustomer(CustomerAggregator customerAggregator) {
		
		CustomerDTO customerDTO = new CustomerDTO();
		ContactDTO contactDTO = new ContactDTO();
		customerDTO.setName(customerAggregator.getName());
		contactDTO.setMobileNumber(customerAggregator.getMobileNumber());
		ContactDTO resultDTO = contactResourceApi.createContactUsingPOST(contactDTO).getBody();
		customerDTO.setContactId(resultDTO.getId());
		return customerResourceApi.createCustomerUsingPOST(customerDTO);

	}

	@Override
	public ResponseEntity<CustomerDTO> updateCustomer(CustomerDTO customerDTO) {
		
		return customerResourceApi.updateCustomerUsingPUT(customerDTO);
	}

	@Override
	public void deleteCustomer(Long id) {
		
		Long contactid = customerResourceApi.getCustomerUsingGET(id).getBody().getContactId();
		customerResourceApi.deleteCustomerUsingDELETE(id);
		contactResourceApi.deleteContactUsingDELETE(contactid);
		
	}

	@Override
	public ResponseEntity<ContactDTO> createContact(ContactDTO contact) {
		
		return contactResourceApi.createContactUsingPOST(contact);
	}

	@Override
	public ResponseEntity<ContactDTO> updateContact(ContactDTO contact) {
		
		return contactResourceApi.updateContactUsingPUT(contact);
	}

	@Override
	public ResponseEntity<Void> deleteContact(Long id) {
		
		return contactResourceApi.deleteContactUsingDELETE(id);
	}

}
