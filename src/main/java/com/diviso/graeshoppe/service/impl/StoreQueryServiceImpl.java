package com.diviso.graeshoppe.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.diviso.graeshoppe.client.store.model.Banner;
import com.diviso.graeshoppe.client.store.model.DeliveryInfo;
import com.diviso.graeshoppe.client.store.model.Review;
import com.diviso.graeshoppe.client.store.model.Store;
import com.diviso.graeshoppe.client.store.model.StoreType;
import com.diviso.graeshoppe.client.store.model.Type;
import com.diviso.graeshoppe.client.store.model.UserRating;
import com.diviso.graeshoppe.service.StoreQueryService;
import com.diviso.graeshoppe.web.rest.util.ServiceUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class StoreQueryServiceImpl implements StoreQueryService {

	@Autowired
	private ServiceUtility serviceUtility ;

	
	@Autowired
	private ObjectMapper objectMapper;
	
	private RestHighLevelClient restHighLevelClient;


	public StoreQueryServiceImpl(RestHighLevelClient restHighLevelClient) {
		
		this.restHighLevelClient = restHighLevelClient;
	}
	
	/**
	 * @param storeId
	 * @param pageable
	 */
	@Override
	public Page<Review> findAllReviews(String storeId, Pageable pageable) {
		
		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(termQuery("store.regNo", storeId));

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("review", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}
		return serviceUtility.getPageResult(searchResponse, pageable, new Review());
	}
	
	/**
	 * @param storeId
	 * @param pageable
	 */
	@Override
	public Page<UserRating> findAllUserRatings(String storeId, Pageable pageable) {
		
		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(termQuery("store.regNo", storeId));

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("userrating", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}
		return serviceUtility.getPageResult(searchResponse, pageable, new UserRating());
	
	}
	
	/**
	 * @param regNo
	 */
	@Override
	public Store findStoreByRegNo(String regNo) {
		
		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(termQuery("regNo", regNo));

		SearchRequest searchRequest = new SearchRequest("store");

		searchRequest.source(builder);
		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getObjectResult(searchResponse, new Store());

	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.diviso.graeshoppe.service.QueryService#findDeliveryInfoByStoreId(java
	 * .lang.Long)
	 */
	/**
	 * @param id
	 */
	@Override
	public Page<DeliveryInfo> findDeliveryInfoByStoreId(Long id) {
		

		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(termQuery("store.id", id));

		Pageable pageable = PageRequest.of(2, 20);
		SearchRequest searchRequest = serviceUtility.generateSearchRequest("deliveryinfo", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}
		return serviceUtility.getPageResult(searchResponse, pageable, new DeliveryInfo());
	

	}

	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.diviso.graeshoppe.service.QueryService#findAllDeliveryTypesByStoreId(java
	 * .lang.Long, org.springframework.data.domain.Pageable)
	 */
	/**
	 * @param storeId
	 */
	@Override
	public List<Type> findAllDeliveryTypesByStoreId(String storeId) {

		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(termQuery("store.regNo", storeId));

		Pageable pageable = PageRequest.of(2, 20);
		SearchRequest searchRequest = serviceUtility.generateSearchRequest("type", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}
		Page<DeliveryInfo> deliveryinfos = serviceUtility.getPageResult(searchResponse, pageable, new Type());
	

		List<Type> types = new ArrayList<Type>();

		deliveryinfos.forEach(deliveryInfo -> {
			types.add(deliveryInfo.getType());

		});

		return types;

	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.diviso.graeshoppe.service.QueryService#findAllStoreTypesByStoreId(
	 * java.lang.String)
	 */
	/**
	 * @param regNo
	 */
	@Override
	public List<StoreType> findAllStoreTypesByStoreId(String regNo) {

		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(termQuery("store.regNo", regNo));

		Pageable pageable = PageRequest.of(2, 20);
		SearchRequest searchRequest = serviceUtility.generateSearchRequest("storetype", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}
		return serviceUtility.getPageResult(searchResponse, pageable, new StoreType()).getContent();
	


	}

	/**
	 * @param storeId
	 */
	@Override
	public Page<Banner> findBannersByStoreId(String storeId,Pageable pageable ) {
		SearchSourceBuilder builder = new SearchSourceBuilder();

		/*
		 * String[] include = new String[] { "" };
		 * 
		 * String[] exclude = new String[] {};
		 * 
		 * builder.fetchSource(include, exclude);
		 */

		builder.query(termQuery("store.regNo.keyword", storeId));

		//Pageable pageable = PageRequest.of(2, 20);
		SearchRequest searchRequest = serviceUtility.generateSearchRequest("banner", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}
		return serviceUtility.getPageResult(searchResponse, pageable, new DeliveryInfo());
	

	}

	/**
	 * @param regNo
	 */
	@Override public List<Banner> findAllBannersByStoreId(String regNo) {
		
		 SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			/*
			 * String[] includeFields = new String[] { "iDPcode", "image" }; String[]
			 * excludeFields = new String[] { "category.*" };
			 * searchSourceBuilder.fetchSource(includeFields, excludeFields);
			 */
			searchSourceBuilder.query(termQuery("store.regNo.keyword",regNo));

			SearchRequest searchRequest = new SearchRequest("banner");
			searchRequest.source(searchSourceBuilder);
			SearchResponse searchResponse = null;
			try {
				searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
			} catch (IOException e) { // TODO Auto-generated
				e.printStackTrace();
			}

			SearchHit[] searchHit = searchResponse.getHits().getHits();

			List<Banner> bannerList = new ArrayList<>();

			for (SearchHit hit : searchHit) {
				bannerList.add(objectMapper.convertValue(hit.getSourceAsMap(), Banner.class));
			}

			return bannerList;
	
	}
		  
	
	
	
	
	

}
