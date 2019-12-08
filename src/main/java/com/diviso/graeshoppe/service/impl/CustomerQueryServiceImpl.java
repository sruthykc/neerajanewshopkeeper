package com.diviso.graeshoppe.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

import java.io.IOException;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.diviso.graeshoppe.client.customer.api.ContactResourceApi;
import com.diviso.graeshoppe.client.customer.api.CustomerResourceApi;
import com.diviso.graeshoppe.client.customer.model.ContactDTO;
import com.diviso.graeshoppe.client.customer.model.Customer;
import com.diviso.graeshoppe.client.customer.model.CustomerDTO;
import com.diviso.graeshoppe.service.CustomerQueryService;
import com.diviso.graeshoppe.web.rest.util.ServiceUtility;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Prince
 *
 */
/**
 * @author Prince
 *
 */
/**
 * @author Prince
 *
 */
@Service
public class CustomerQueryServiceImpl implements CustomerQueryService{
	
	

	private final Logger log = LoggerFactory.getLogger(QueryServiceImpl.class);

	@Autowired
	private RestHighLevelClient restHighLevelClient;
	@Autowired
	CustomerResourceApi customerResourceApi;
	@Autowired
	private ServiceUtility serviceUtility;
	

	@Autowired
	private ContactResourceApi contactResourceApi;


	 

	/**
	 * @param name
	 * 
	 */
	@Override
	public Page<Customer> findAllCustomersByName(String searchTerm, Pageable pageable) {
		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query( matchQuery("name", searchTerm) );

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("customer", pageable.getPageSize(), pageable.getPageNumber(),
				builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}
		return serviceUtility.getPageResult(searchResponse, pageable, new Customer());
	}
	
	/**
	 * @param
	 */
	@Override
	public Page<Customer> findAllCustomers(Pageable pageable) {
		
		SearchSourceBuilder builder = new SearchSourceBuilder();
		
		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(matchAllQuery());

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("customer", pageable.getPageSize(), pageable.getPageNumber(),
				builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}
		return serviceUtility.getPageResult(searchResponse, pageable, new Customer());
	}
	public ResponseEntity<CustomerDTO> findCustomerById(Long id) {
		log.debug("<<<<<<<<< findCustomerById >>>>>>>>", id);
		return customerResourceApi.getCustomerUsingGET(id);
	}
	public ResponseEntity<ContactDTO> findContactById( Long id) {
		return this.contactResourceApi.getContactUsingGET(id);
	}
}
