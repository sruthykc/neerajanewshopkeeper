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
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.diviso.graeshoppe.client.customer.model.Customer;
import com.diviso.graeshoppe.client.order.api.OrderQueryResourceApi;
import com.diviso.graeshoppe.client.order.model.Notification;
import com.diviso.graeshoppe.client.order.model.OpenTask;
import com.diviso.graeshoppe.client.order.model.Order;
import com.diviso.graeshoppe.client.order.model.OrderLine;
import com.diviso.graeshoppe.client.order.model.aggregator.AuxilaryOrderLine;
import com.diviso.graeshoppe.client.report.api.OrderMasterResourceApi;
import com.diviso.graeshoppe.client.store.model.Store;
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


	private final Logger log = LoggerFactory.getLogger(OrderQueryServiceImpl.class);
	@Autowired
	private ServiceUtility serviceUtility;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	OrderMasterResourceApi orderMasterResourceApi;

	@Autowired
	private OrderQueryResourceApi orderQueryResourceApi;

	private RestHighLevelClient restHighLevelClient;

	public OrderQueryServiceImpl(RestHighLevelClient restHighLevelClient) {

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
	public Page<Order> findOrderByStatusNameAndStoreIdAndDeliveryType(String statusName, String storeId, String deliveryType,
			Pageable pageable) {
		log.debug("<<<<<<<<<< findOrderByStatusNameAndDeliveryType >>>>>>>>>",statusName,deliveryType);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		SearchRequest searchRequest = null;
		SearchResponse searchResponse = null;
		QueryBuilder dslQuery =null;
		
		
		
		

		if (deliveryType.equals("all")) {

			 dslQuery =QueryBuilders.boolQuery().must(QueryBuilders.termQuery("status.name.keyword", statusName))
					.must(QueryBuilders.termQuery("storeId.keyword", storeId));
			
			
        }
		else {

			 dslQuery =	QueryBuilders.boolQuery().must(QueryBuilders.termQuery("status.name.keyword", statusName))
					.must(QueryBuilders.termQuery("storeId.keyword", storeId))
					.must(QueryBuilders.termQuery("deliveryInfo.deliveryType.keyword", deliveryType));
			
		
		}
		FieldSortBuilder sortById = SortBuilders.fieldSort("id").order(SortOrder.DESC);
		searchSourceBuilder.query(dslQuery);
		searchSourceBuilder .sort(sortById);
		searchResponse = serviceUtility.searchResponseForPage("order", searchSourceBuilder, pageable);
		
		/*searchRequest = serviceUtility.generateSearchRequest("order", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}*/
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

		QueryBuilder dslQuery = QueryBuilders.boolQuery()
				.must(QueryBuilders.matchAllQuery())
				.filter(QueryBuilders.termQuery("storeId.keyword", storeId));
		
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(dslQuery);
		
		searchSourceBuilder.sort(new FieldSortBuilder("id").order(SortOrder.DESC));
		SearchResponse searchResponse = serviceUtility.searchResponseForPage("order", searchSourceBuilder, pageable);

		Page<Order> orderPage = serviceUtility.getPageResult(searchResponse, pageable, new Order());

		/*
		 * SearchSourceBuilder builder = new SearchSourceBuilder();
		 * 
		 * 
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 * 
		 * 
		 * builder.query(termQuery("storeId.keyword", storeId)).sort("id",
		 * SortOrder.DESC);
		 * 
		 * SearchRequest searchRequest = serviceUtility.generateSearchRequest("order",
		 * pageable.getPageSize(), pageable.getPageNumber(), builder);
		 * 
		 * SearchResponse searchResponse = null;
		 * 
		 * try { searchResponse = restHighLevelClient.search(searchRequest,
		 * RequestOptions.DEFAULT); } catch (IOException e) { // TODO Auto-generated
		 * e.printStackTrace(); } Page<Order> orderPage =
		 * serviceUtility.getPageResult(searchResponse, pageable, new Order());
		 */
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
		log.debug("<<<<<<<< findOrderLinesByOrderId  >>>>>>>>>.", orderId);

		
		  QueryBuilder dslQuery=
		  QueryBuilders.boolQuery().must(QueryBuilders.matchAllQuery())
		  .filter(QueryBuilders.termQuery("orderId", orderId));
			FieldSortBuilder sortById = SortBuilders.fieldSort("id").order(SortOrder.DESC);
		//QueryBuilder queryDsl = termQuery("orderId", orderId);
		
	
		
		  SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		 	 
		  searchSourceBuilder.query(dslQuery);
		  searchSourceBuilder.sort(sortById);
		  SearchRequest searchRequest = new SearchRequest("orderline");
		  searchRequest.source(searchSourceBuilder);
		  SearchResponse searchResponse = null;
		  
		  try { searchResponse = restHighLevelClient.search(searchRequest,
		  RequestOptions.DEFAULT); } catch (IOException e) { // TODO Auto-generated
		  e.printStackTrace(); } SearchHit[] searchHit =
		  searchResponse.getHits().getHits();
		  
		  List<OrderLine> orderLineList = new ArrayList<>();
		  
		  for (SearchHit hit : searchHit) {

			  orderLineList.add(objectMapper.convertValue(hit.getSourceAsMap(), OrderLine.class));
		  }
		  return orderLineList;
		 

	}

	/**
	 * @param receiverId
	 */
	@Override
	public Page<Notification> findNotificationByReceiverId(String receiverId, Pageable pageable) {
		log.debug("<<<<<<<<<<< findNotificationByReceiverId >>>>>>>>", receiverId);
		QueryBuilder queryDsl = QueryBuilders.boolQuery()
				.must(QueryBuilders.matchAllQuery())
				.filter(QueryBuilders.termQuery("receiverId.keyword", receiverId));
		FieldSortBuilder sortById = SortBuilders.fieldSort("id").order(SortOrder.DESC);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(queryDsl);
		searchSourceBuilder.sort(sortById);
		SearchResponse searchResponse = serviceUtility.searchResponseForPage("notification", searchSourceBuilder, pageable);
		Page<Notification> page = serviceUtility.getPageResult(searchResponse, pageable, new Notification());
		return page;

		/*
		 
		 * builder.query(termQuery("receiverId.keyword", receiverId)).sort("id",
		 * SortOrder.DESC);
		
		 */
	}

//TO_DO @rafeek

	/*
	 * public Long findOrderCountByDateAndStatusName(String statusName, Instant
	 * date) {
	 */

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
	// return 0l;}

	/**
	 * @param from
	 * @param to
	 * @param storeId
	 */
	@Override
	public Page<Order> findOrderByDatebetweenAndStoreId(Instant from, Instant to, String storeId, Pageable pageable) {

		log.debug("<<<<<<<<< findOrderByDatebetweenAndStoreId >>>>>>>>>>",from,to,storeId);
		QueryBuilder queryDsl = QueryBuilders.boolQuery()
				.must(termQuery("storeId.keyword",storeId))
				.must(rangeQuery("date").gte(from).lte(to));
				
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(queryDsl);
		SearchResponse searchResponse =serviceUtility.searchResponseForPage("order", searchSourceBuilder, pageable);
		Page<Order> page = serviceUtility.getPageResult(searchResponse, pageable, new Order());
		return page;
		
		/*
		 * SearchSourceBuilder builder = new SearchSourceBuilder();
		 * 
		 * 
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 * 
		 * 
		 * builder.query(QueryBuilders.boolQuery().must(termQuery("storeId.keyword",
		 * storeId)) .must(rangeQuery("date").gte(from).lte(to)));
		 * 
		 * SearchRequest searchRequest = serviceUtility.generateSearchRequest("order",
		 * pageable.getPageSize(), pageable.getPageNumber(), builder);
		 * 
		 * SearchResponse searchResponse = null;
		 * 
		 * try { searchResponse = restHighLevelClient.search(searchRequest,
		 * RequestOptions.DEFAULT); } catch (IOException e) { // TODO Auto-generated
		 * e.printStackTrace(); } return serviceUtility.getPageResult(searchResponse,
		 * pageable, new Order());
		 */
	}

	/**
	 * @param status
	 * @param receiverId
	 */
	@Override
	
	public Long getNotificationCountByReceiveridAndStatus(String status, String receiverId) {
		log.info(".............." + status + ".............." + receiverId);

		/*
		 * QueryBuilder queryDsl =
		 * QueryBuilders.boolQuery().must(termQuery("status.keyword",status))
		 * .must(termQuery("receiverId.keyword",receiverId)); SearchSourceBuilder searchSourceBuilder =
		 * new SearchSourceBuilder(); searchSourceBuilder.query(queryDsl);
		 * 
		 * SearchResponse searchResponse = serviceUtility.searchResponseForObject("notification",
		 * queryDsl); Notification result = serviceUtility.getObjectResult(searchResponse, new
		 * Notification()); return ;
		 */
				
		CountRequest countRequest = new CountRequest("notification");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		searchSourceBuilder.query(QueryBuilders.boolQuery().must(termQuery("status.keyword", status))
				.must(QueryBuilders.termQuery("receiverId.keyword", receiverId)));

		countRequest.source(searchSourceBuilder);
		CountResponse countResponse = null;
		try {
			countResponse = restHighLevelClient.count(countRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return countResponse.getCount();
		/*
		 * SearchSourceBuilder builder = new SearchSourceBuilder();
		 * 
		 * builder.query(QueryBuilders.boolQuery().must(termQuery("status.keyword",
		 * status)) .must(QueryBuilders.matchQuery("receiverId", receiverId)));
		 * 
		 * SearchRequest searchRequest =
		 * serviceUtility.generateSearchRequest("notification", pageable.getPageSize(),
		 * pageable.getPageNumber(), builder);
		 * 
		 * SearchResponse searchResponse = null;
		 * 
		 * try { searchResponse = restHighLevelClient.search(searchRequest,
		 * RequestOptions.DEFAULT); } catch (IOException e) { // TODO Auto-generated
		 * e.printStackTrace(); } List<Notification> notifications =
		 * serviceUtility.getPageResult(searchResponse, pageable, new Notification())
		 * .getContent(); return (long) notifications.size();
		 */
	}

	/**
	 * @param receiverId
	 * @param status
	 */

	/*@Override
	public Long findNotificationCountByReceiverIdAndStatusName(String receiverId, String status) {
		CountRequest countRequest = new CountRequest("notification");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		searchSourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("receiverId", receiverId))
				.must(QueryBuilders.matchQuery("status", status)));

		countRequest.source(searchSourceBuilder);
		CountResponse countResponse = null;
		try {
			countResponse = restHighLevelClient.count(countRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return countResponse.getCount();
	}*/
		/*
		 * SearchSourceBuilder builder = new SearchSourceBuilder();
		 * 
		 * 
		 * 
		 * 
		 * 
		 * builder.query(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery(
		 * "receiverId", receiverId)) .must(QueryBuilders.matchQuery("status",
		 * status)));
		 * 
		 * SearchRequest searchRequest =
		 * serviceUtility.generateSearchRequest("notification", pageable.getPageSize(),
		 * pageable.getPageNumber(), builder);
		 * 
		 * SearchResponse searchResponse = null;
		 * 
		 * try { searchResponse = restHighLevelClient.search(searchRequest,
		 * RequestOptions.DEFAULT); } catch (IOException e) { // TODO Auto-generated
		 * e.printStackTrace(); } List<Notification> notifications =
		 * serviceUtility.getPageResult(searchResponse, pageable, new Notification())
		 * .getContent();
		 * 
		 * return (long) notifications.size();
		 */


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

		//QueryBuilder dslQuery = termQuery("orderId.keyword", orderId);
		QueryBuilder dslQuery = QueryBuilders.boolQuery()
				.must(QueryBuilders.matchAllQuery())
				.filter(QueryBuilders.termQuery("orderId.keyword", orderId));
		
		SearchResponse searchResponse = serviceUtility.searchResponseForObject("order", dslQuery);
		return serviceUtility.getObjectResult(searchResponse, new Order());

		/*
		 * SearchSourceBuilder searchBuilder = new SearchSourceBuilder();
		 * 
		 * String[] include = new String[] { "", "", "" };
		 * 
		 * searchBuilder.fetchSource(include, exclude);
		 * 
		 * searchBuilder.query(termQuery("orderId.keyword", orderId));
		 * 
		 * SearchRequest searchRequest = new SearchRequest("order");
		 * 
		 * searchRequest.source(searchBuilder); SearchResponse searchResponse = null;
		 * 
		 * try { searchResponse = restHighLevelClient.search(searchRequest,
		 * RequestOptions.DEFAULT); } catch (IOException e) { // TODO Auto-generated
		 * e.printStackTrace(); }
		 * 
		 * return serviceUtility.getObjectResult(searchResponse, new Order());
		 */
	}

	@Override
	public Long orderCountByCustomerIdAndStoreId(String customerId, String storeId) {
		log.debug("<<<<<<<<<<<<< orderCountByCustomerIdAndStoreId >>>>>>>>>", customerId, storeId);
		///////// create builders and queries //////////////

		SearchSourceBuilder builder = new SearchSourceBuilder();
		TermQueryBuilder getCustomer = new TermQueryBuilder("customerId.keyword", customerId);
		TermQueryBuilder getStore = new TermQueryBuilder("storeId.keyword", storeId);
		builder.query(QueryBuilders.boolQuery().must(getCustomer).must(getStore));

		// SearchRequest request =new SearchRequest("order"); //indexname
		CountRequest countRequest = new CountRequest("order");
		countRequest.source(builder);
		CountResponse response = null;

		try {
			response = restHighLevelClient.count(countRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}

		long count = response.getCount();
		return count;

		/*
		 * CountRequest countRequest = new CountRequest("order"); // <1>
		 * SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); // <2>
		 * searchSourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.
		 * termQuery("customerId.keyword", customerId))
		 * .must(QueryBuilders.termQuery("status.name.keyword",statusName ))); // <3>
		 * countRequest.source(searchSourceBuilder);
		 * 
		 * CountResponse countResponse = null; try { countResponse =
		 * restHighLevelClient.count(countRequest, RequestOptions.DEFAULT); } catch
		 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 * 
		 * long count = countResponse.getCount();
		 * 
		 * return count;
		 */
	}

	public ResponseEntity<OpenTask> getTaskDetails(String taskName, String orderId, String storeId) {
		return orderQueryResourceApi.getTaskDetailsUsingGET(taskName, orderId, storeId);

	}

	public ResponseEntity<List<Order>> getTasks(String assignee, String assigneeLike, String candidateGroup,
			String candidateGroups, String candidateUser, String createdAfter, String createdBefore, String createdOn,
			String name, String nameLike) {
		List<OpenTask> openTasks = orderQueryResourceApi.getTasksUsingGET(assignee, assigneeLike, candidateGroup,
				candidateGroups, candidateUser, createdAfter, createdBefore, createdOn, name, nameLike).getBody();

		log.info("...........openTasks...................." + openTasks);
		List<Order> orders = new ArrayList<Order>();

		openTasks.forEach(opentask -> {

			orders.add(findOrderByOrderId(opentask.getOrderId()));

		});
		return ResponseEntity.ok().body(orders);
	}

	@Override
	public Page<com.diviso.graeshoppe.client.order.model.aggregator.OrderLine> findAllOrderLinesByOrderId(Long orderId,
			Pageable pageable) {
		log.debug("<<<<<<<<<<< findAllOrderLinesByOrderId >>>>>>>>",orderId);
	//	QueryBuilder queryBuilder = QueryBuilders.termQuery("order.id", orderId);
		QueryBuilder dslQuery = QueryBuilders.boolQuery()
				.must(QueryBuilders.matchAllQuery())
				.filter(QueryBuilders.termQuery("order.id", orderId));
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(dslQuery);
		SearchResponse response = serviceUtility.searchResponseForPage("orderline", builder, pageable);
		return serviceUtility.getPageResult(response, pageable, new OrderLine());
	}

	@Override
	public Page<AuxilaryOrderLine> findAuxilaryOrderLineByOrderLineId(Long orderLineId, Pageable pageable) {
		log.debug("<<<<<< findAuxilaryOrderLineByOrderLineId >>>>>>>>",orderLineId,pageable);
		//QueryBuilder querybuilder = QueryBuilders.termQuery("orderLine.id", orderLineId);
		QueryBuilder dslQuery = QueryBuilders.boolQuery()
				.must(QueryBuilders.matchAllQuery())
				.filter(QueryBuilders.termQuery("orderLine.id", orderLineId));
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(dslQuery);
		SearchResponse response = serviceUtility.searchResponseForPage("auxilaryorderline", builder, pageable);
		return serviceUtility.getPageResult(response, pageable, new AuxilaryOrderLine());
	}
}
