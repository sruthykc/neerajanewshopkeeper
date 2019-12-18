package com.diviso.graeshoppe.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.io.IOException;
import java.util.List;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
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
import com.diviso.graeshoppe.client.customer.model.Contact;
import com.diviso.graeshoppe.client.customer.model.ContactDTO;
import com.diviso.graeshoppe.client.customer.model.Customer;
import com.diviso.graeshoppe.client.customer.model.CustomerDTO;
import com.diviso.graeshoppe.client.store.model.Store;
import com.diviso.graeshoppe.service.CustomerQueryService;
import com.diviso.graeshoppe.web.rest.util.ServiceUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.diviso.graeshoppe.service.mapper.ContactMapper;
import com.diviso.graeshoppe.service.mapper.CustomerMapper;
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
	
	

	private final Logger log = LoggerFactory.getLogger(CustomerQueryServiceImpl.class);
	
	@Autowired
	private RestHighLevelClient restHighLevelClient;
	
	@Autowired
	private ServiceUtility serviceUtility;
	

	@Autowired
	ContactMapper contactMapper;
	 
	@Autowired
	CustomerMapper customerMapper;
	/**
	 * @param name
	 * 
	 */
	@Override
	public Page<Customer> findAllCustomersByName(String name, Pageable pageable) {
		
		QueryBuilder dslQuery = QueryBuilders.boolQuery().must(QueryBuilders.matchAllQuery())
				.filter(QueryBuilders.termQuery("name.keyword", name));
		
		
		
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(dslQuery);
		SearchResponse searchResponse = serviceUtility.searchResponseForPage("customer", searchSourceBuilder, pageable);

		Page<Customer> page= serviceUtility.getPageResult(searchResponse, pageable, new Customer());

		
		return page;
		
		
	/*	SearchSourceBuilder builder = new SearchSourceBuilder();

		
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 

		builder.query( matchQuery("name", searchTerm) );

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("customer", pageable.getPageSize(), pageable.getPageNumber(),
				builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}
		return serviceUtility.getPageResult(searchResponse, pageable, new Customer());*/
	}
	
	/**
	 * @param
	 */
	@Override
	public Page<Customer> findAllCustomers(Pageable pageable) {
		
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(matchAllQuery());
		SearchResponse searchResponse = serviceUtility.searchResponseForPage("customer", searchSourceBuilder, pageable);

		Page<Customer> page= serviceUtility.getPageResult(searchResponse, pageable, new Customer());

		
		return page;
		
		/*SearchSourceBuilder builder = new SearchSourceBuilder();
		
		
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 

		builder.query(matchAllQuery());

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("customer", pageable.getPageSize(), pageable.getPageNumber(),
				builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}
		return serviceUtility.getPageResult(searchResponse, pageable, new Customer());*/
	}
	public CustomerDTO findCustomerById(Long id) {
		QueryBuilder dslQuery = QueryBuilders.boolQuery().must(QueryBuilders.matchAllQuery())
				.filter(QueryBuilders.termQuery("id", id));
		
		SearchResponse searchResponse = serviceUtility.searchResponseForObject("customer", dslQuery);
		Customer result=serviceUtility.getObjectResult(searchResponse, new Customer());
		return customerMapper.toDto(result);
		
		
	}
	public ContactDTO findContactById( Long id) {

		QueryBuilder dslQuery = QueryBuilders.boolQuery().must(QueryBuilders.matchAllQuery())
				.filter(QueryBuilders.termQuery("id", id));
		
		SearchResponse searchResponse = serviceUtility.searchResponseForObject("contact", dslQuery);
		Contact result=serviceUtility.getObjectResult(searchResponse, new Contact());
		return contactMapper.toDto(result);
		
		
		
	}
	
}
