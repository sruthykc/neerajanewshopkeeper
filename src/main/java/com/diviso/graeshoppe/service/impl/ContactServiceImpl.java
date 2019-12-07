package com.diviso.graeshoppe.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.diviso.graeshoppe.client.customer.model.Contact;
import com.diviso.graeshoppe.service.ContactService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ContactServiceImpl implements ContactService{
	
	private final Logger log=LoggerFactory.getLogger(ContactServiceImpl.class);
	
	@Autowired
	RestHighLevelClient restHighLevel;

	@Override
	public List<Contact> findContactsByMobileNumber(Long mobileNumber,Pageable page) {
		log.debug("<<<<<<< findContactsByMobileNumber >>>>>>>",mobileNumber);
		
		List<Contact> contacts =new ArrayList<Contact>();
		SearchSourceBuilder builder =new SearchSourceBuilder();
		TermQueryBuilder term = new TermQueryBuilder("mobileNumber",mobileNumber);
		builder.from(0);
		builder.size(5);
		builder.query(term);
		
		SearchRequest sr =new SearchRequest("contact");
		sr.source(builder);
		SearchResponse response = null;
		
		try {
			response =restHighLevel.search(sr, RequestOptions.DEFAULT);
		}catch(IOException e){
			e.printStackTrace();
		}
		for(SearchHit s :response.getHits()) {
			Contact contact =new Contact();
			contact=new ObjectMapper().convertValue(s.getSourceAsMap(), Contact.class);
			contacts.add(contact);
		}
		return contacts;
	}

}
