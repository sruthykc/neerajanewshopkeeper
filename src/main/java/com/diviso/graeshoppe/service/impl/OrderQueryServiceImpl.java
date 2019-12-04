package com.diviso.graeshoppe.service.impl;

import static org.elasticsearch.action.search.SearchType.QUERY_THEN_FETCH;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import com.diviso.graeshoppe.client.order.model.Notification;
import com.diviso.graeshoppe.client.order.model.Order;
import com.diviso.graeshoppe.client.order.model.OrderLine;

import com.diviso.graeshoppe.service.OrderQueryService;
import com.diviso.graeshoppe.web.rest.util.ServiceUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * 
 * @author Prince
 *
 */
@Service
public class OrderQueryServiceImpl implements OrderQueryService {

	int i = 0;
	Long count = 0L;

	private final Logger log = LoggerFactory.getLogger(QueryServiceImpl.class);
	@Autowired
	private ServiceUtility serviceUtility;
	
	private RestHighLevelClient restHighLevelClient;


	public OrderQueryServiceImpl( RestHighLevelClient restHighLevelClient) {
		
		this.restHighLevelClient = restHighLevelClient;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.diviso.graeshoppe.service.QueryService#findOrderByStatusName(java.lang.
	 * String)
	 */
	/**
	 * @param statusname
	 * @param storeid
	 * @param deliverytype
	 */
	@Override
	public Page<Order> findOrderByStatusNameAndDeliveryType(String statusName, String storeId, String deliveryType,
			Pageable pageable) {

		SearchResponse searchResponse = null;

		SearchSourceBuilder builder = new SearchSourceBuilder();

		SearchRequest searchRequest = null;

		if (deliveryType.equals("all")) {

			/*
			 * String[] include = new String[] { "" };
			 * 
			 * String[] exclude = new String[] {};
			 * 
			 * builder.fetchSource(include, exclude);
			 */

			builder.query(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("status.name.keyword", statusName))
					.must(QueryBuilders.matchQuery("storeId", storeId)).must(QueryBuilders.matchQuery("deliveryInfo.deliveryType.keyword", deliveryType))).sort("id", SortOrder.DESC);

			searchRequest = serviceUtility.generateSearchRequest("order", pageable.getPageSize(),
					pageable.getPageNumber(), builder);

			try {
				searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
			} catch (IOException e) { // TODO Auto-generated
				e.printStackTrace();
			}
		} else {

			builder.query(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("status.name.keyword", statusName))
					.must(QueryBuilders.matchQuery("storeId", storeId))
					.must(QueryBuilders.matchQuery("deliveryInfo.deliveryType.keyword", deliveryType)))
					.sort("id", SortOrder.DESC);

			searchRequest = serviceUtility.generateSearchRequest("order", pageable.getPageSize(),
					pageable.getPageNumber(), builder);

			try {
				searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
			} catch (IOException e) { // TODO Auto-generated
				e.printStackTrace();
			}
		}

		return serviceUtility.getPageResult(searchResponse, pageable, new Order());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.diviso.graeshoppe.service.QueryService#findOrderLinesByStoreId(java.
	 * lang.String)
	 */
	
	/**
	 * @param storeid
	 */
	@Override
	public Page<Order> findOrderByStoreId(String storeId, Pageable pageable) {

		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(termQuery("storeId", storeId)).sort("id", SortOrder.DESC);

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("customer", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}
		Page<Order> orderPage = serviceUtility.getPageResult(searchResponse, pageable, new Order());

		orderPage.forEach(order -> {

			order.setOrderLines(new HashSet<OrderLine>(findOrderLinesByOrderId(order.getId())));

		});

		return orderPage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.diviso.graeshoppe.service.QueryService#findOrderByStoreId(java.lang.
	 * String)
	 */
	/**
	 * @param orderId
	 */
	@Override
	public List<OrderLine> findOrderLinesByOrderId(Long orderId) {

		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(termQuery("order.id", orderId));

		Pageable pageable = PageRequest.of(2, 20);

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("customer", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}
		return serviceUtility.getPageResult(searchResponse, pageable, new Order()).getContent();
	}

	/**
	 *@param receiverId 
	 */
	@Override
	public Page<Notification> findNotificationByReceiverId(String receiverId, Pageable pageable) {

		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(termQuery("receiverId", receiverId)).sort("id", SortOrder.DESC);

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("notification", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}
		return serviceUtility.getPageResult(searchResponse, pageable, new Notification());

	}

//TO_DO @rafeek
	
	 /* public Long findOrderCountByDateAndStatusName(String statusName, Instant
	  date) {*/
		 
		  
		  
		  
		  
		  
	  
		/*
		 * SearchQuery searchQuery = new
		 * NativeSearchQueryBuilder().withQuery(matchAllQuery())
		 * .withSearchType(QUERY_THEN_FETCH).withIndices("order").withTypes("order")
		 * .addAggregation(AggregationBuilders.terms("date").field("date.keyword")
		 * .order(org.elasticsearch.search.aggregations.bucket.terms.Terms.Order.
		 * aggregation("avgPrice", true))
		 * .subAggregation(AggregationBuilders.avg("avgPrice").field("grandTotal"))
		 * .subAggregation(AggregationBuilders.terms("statusName").field(
		 * "status.name.keyword"))) .build();
		 * 
		 * AggregatedPage<Order> result =
		 * elasticsearchTemplate.queryForPage(searchQuery, Order.class);
		 * 
		 * TermsAggregation orderAgg = result.getAggregation("date",
		 * TermsAggregation.class); List<Entry> storeBasedEntry = new
		 * ArrayList<Entry>(); log.info(",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,," +
		 * orderAgg.getBuckets()); orderAgg.getBuckets().forEach(bucket -> {
		 * 
		 * List<Entry> listStore = bucket.getAggregation("statusName",
		 * TermsAggregation.class).getBuckets();
		 * log.info(",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,," + listStore); for (int i =
		 * 0; i < listStore.size(); i++) {
		 * log.info(",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,," + listStore.get(i)); if
		 * (bucket.getKey().equals(date.toString())) { if
		 * (listStore.get(i).getKey().equals(statusName)) {
		 * 
		 * storeBasedEntry .add(bucket.getAggregation("statusName",
		 * TermsAggregation.class).getBuckets().get(i)); } }
		 * 
		 * }
		 * 
		 * }); storeBasedEntry.forEach(e -> { count = e.getCount(); }); return count;
		 */ 
		//  return 0l;}
	 

	/**
	 * @param from
	 * @param to
	 * @param storeId
	 */
	@Override
	public Page<Order> findOrderByDatebetweenAndStoreId(Instant from, Instant to, String storeId) {
		// .........

		Pageable pageable = PageRequest.of(2, 20);

		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(QueryBuilders.boolQuery().must(termQuery("storeId", storeId))
				.must(rangeQuery("date").gte(from).lte(to)));

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("order", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}
		return serviceUtility.getPageResult(searchResponse, pageable, new Order());

	}

	/**
	 * @param status
	 * @param receiverId
	 */
	@Override
	public Long getNotificationCountByReceiveridAndStatus(String status, String receiverId) {
		log.info(".............." + status + ".............." + receiverId);
		
		Pageable pageable = PageRequest.of(2, 20);

		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(QueryBuilders.boolQuery()
				.must(termQuery("status.keyword", status)).must(QueryBuilders.matchQuery("receiverId", receiverId)));

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("notification", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}
		List<Notification> notifications = serviceUtility.getPageResult(searchResponse, pageable, new Notification()).getContent();
		return (long) notifications.size();
	}

	/**
	 * @param receiverId
	 * @param status
	 */
	@Override
	public Long findNotificationCountByReceiverIdAndStatusName(String receiverId, String status) {
		

		Pageable pageable = PageRequest.of(2, 20);

		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery(
				  "receiverId", receiverId)) .must(QueryBuilders.matchQuery("status", status)));

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("notification", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}
		List<Notification> notifications = serviceUtility.getPageResult(searchResponse, pageable, new Notification()).getContent();
		
		return (long)notifications.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.diviso.graeshoppe.service.QueryService#findOrderByOrderId(java.lang.
	 * String)
	 */
	/**
	 * @param orderId
	 */
	@Override
	public Order findOrderByOrderId(String orderId) {

		
		SearchSourceBuilder searchBuilder = new SearchSourceBuilder();
		/*
		 * String[] include = new String[] { "", "", "" };
		 * 
		 * searchBuilder.fetchSource(include, exclude);
		 */
		searchBuilder.query(termQuery("orderId.keyword", orderId));

		SearchRequest searchRequest = new SearchRequest("order");

		searchRequest.source(searchBuilder);
		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getObjectResult(searchResponse, new Order());
	
	}

	

}
