package com.diviso.graeshoppe.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.diviso.graeshoppe.client.product.model.Product;
import com.diviso.graeshoppe.client.sale.api.SaleResourceApi;
import com.diviso.graeshoppe.client.sale.api.TicketLineResourceApi;
import com.diviso.graeshoppe.client.sale.model.Sale;
import com.diviso.graeshoppe.client.sale.model.TicketLine;
import com.diviso.graeshoppe.client.sale.model.SaleDTO;
import com.diviso.graeshoppe.client.sale.model.TicketLineDTO;
import com.diviso.graeshoppe.service.SaleQueryService;
import com.diviso.graeshoppe.web.rest.util.ServiceUtility;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SaleQueryServiceImpl implements SaleQueryService {
	@Autowired
	private ServiceUtility serviceUtility;

	@Autowired
	SaleResourceApi saleResourceApi;

	@Autowired
	private TicketLineResourceApi ticketLineResourceApi;
	private RestHighLevelClient restHighLevelClient;

	public SaleQueryServiceImpl(RestHighLevelClient restHighLevelClient) {

		this.restHighLevelClient = restHighLevelClient;
	}

	/**
	 * @param storeId
	 */
	@Override
	public Page<Sale> findSales(String storeId, Pageable pageable) {
		QueryBuilder queryBuilder = QueryBuilders.boolQuery()
				.must(QueryBuilders.termQuery("userId.keyword", storeId));

		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryBuilder);
		SearchResponse searchResponse = serviceUtility.searchResponseForPage("sale", builder, pageable);
		return serviceUtility.getPageResult(searchResponse, pageable, new Sale());
		
		
		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 * 
		 * 
		 * builder.query(termQuery("userId.keyword", storeId)).sort("date",
		 * SortOrder.DESC);
		 * 
		 * SearchRequest searchRequest = serviceUtility.generateSearchRequest("sale",
		 * pageable.getPageSize(), pageable.getPageNumber(), builder);
		 * 
		 * SearchResponse searchResponse = null;
		 * 
		 * try { searchResponse = restHighLevelClient.search(searchRequest,
		 * RequestOptions.DEFAULT); } catch (IOException e) { // TODO Auto-generated
		 * e.printStackTrace(); } return serviceUtility.getPageResult(searchResponse,
		 * pageable, new Sale());
		 */

	}

	/**
	 * @param saleId
	 */
	@Override
	public List<TicketLine> findAllTicketLinesBySaleId(Long saleId) {

		List<TicketLine> ticketLines = new ArrayList<TicketLine>();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		searchSourceBuilder.query(termQuery("id", saleId));

		SearchRequest searchRequest = new SearchRequest("ticketline");
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}
		for (SearchHit hit : searchResponse.getHits()) {
			TicketLine ticketLine = new ObjectMapper().convertValue(hit.getSourceAsString(), TicketLine.class);
			ticketLines.add(ticketLine); // new ObjectMapper().convertValue [return a jason object]
											// new ObjectMapper().readValue [return a string value]
		}
		return ticketLines;
		// return serviceUtility.getPageResult(searchResponse, pageable, new
		// TicketLine());

	}

	/**
	 * 
	 * @param id
	 * @return sale
	 * 
	 * @description find sales by id
	 */
	// it's working
	public ResponseEntity<SaleDTO> findSaleById(Long id) {
		return this.saleResourceApi.getSaleUsingGET(id);
	}

	public ResponseEntity<List<TicketLineDTO>> findAllTicketlines(Integer page, Integer size, ArrayList<String> sort) {
		return ticketLineResourceApi.getAllTicketLinesUsingGET(page, size, sort);
	}

	public ResponseEntity<TicketLineDTO> findOneTicketLines(Long id) {
		return ticketLineResourceApi.getTicketLineUsingGET(id);
	}

}
