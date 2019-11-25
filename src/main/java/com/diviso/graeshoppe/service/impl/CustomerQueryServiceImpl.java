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

import org.springframework.stereotype.Service;


import com.diviso.graeshoppe.client.customer.domain.Customer;
import com.diviso.graeshoppe.service.CustomerQueryService;
import com.diviso.graeshoppe.web.rest.util.ServiceUtility;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CustomerQueryServiceImpl implements CustomerQueryService{
	
	

	private final Logger log = LoggerFactory.getLogger(QueryServiceImpl.class);

	//@Autowired
	private RestHighLevelClient restHighLevelClient;


	@Autowired
	private ServiceUtility serviceUtility;
	
	
	  public CustomerQueryServiceImpl(
	  RestHighLevelClient restHighLevelClient) {
		  
	  }
	  //this.restHighLevelClient = restHighLevelClient; }
	 


	@Override
	public Page<Customer> findAllCustomers(String searchTerm, Pageable pageable) {
		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(matchQuery("name", searchTerm).prefixLength(3));

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
	
	
	@Override
	public Page<Customer> findAllCustomersWithoutSearch(Pageable pageable) {
		
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
	
}
