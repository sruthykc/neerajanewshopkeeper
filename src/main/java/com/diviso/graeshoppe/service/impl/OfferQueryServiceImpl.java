package com.diviso.graeshoppe.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diviso.graeshoppe.client.order.model.aggregator.Offer;
import com.diviso.graeshoppe.service.OfferQueryService;
import com.diviso.graeshoppe.web.rest.util.ServiceUtility;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OfferQueryServiceImpl implements OfferQueryService{

	private Logger log =LoggerFactory.getLogger(OfferQueryServiceImpl.class);
	
	@Autowired
	ServiceUtility serviceUtility;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Override
	public List<Offer> findOfferLinesByOrderId(Long orderId) {
		log.debug("<<<<<<<<findOfferLinesByOrderId >>>>>>>",orderId);
		QueryBuilder queryBuilder =QueryBuilders.termQuery("order.id", orderId);
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryBuilder);
		SearchResponse response = null;
		List<Offer> offers = new ArrayList<Offer>();
		SearchHit[] searchHit = response.getHits().getHits();
		response=serviceUtility.searchResponseForSourceBuilder("offerline", builder);
		for(SearchHit hit :searchHit) {
			offers.add(objectMapper.convertValue(hit.getSourceAsMap(), Offer.class));
		}
		
		return offers;
	}

}
