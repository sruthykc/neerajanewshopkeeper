package com.diviso.graeshoppe.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.diviso.graeshoppe.client.store.api.BannerResourceApi;
import com.diviso.graeshoppe.client.store.api.DeliveryInfoResourceApi;
import com.diviso.graeshoppe.client.store.api.StoreAddressResourceApi;
import com.diviso.graeshoppe.client.store.api.StoreResourceApi;
import com.diviso.graeshoppe.client.store.api.StoreSettingsResourceApi;
import com.diviso.graeshoppe.client.store.api.StoreTypeResourceApi;
import com.diviso.graeshoppe.client.store.api.TypeResourceApi;
import com.diviso.graeshoppe.client.store.model.Banner;
import com.diviso.graeshoppe.client.store.model.BannerDTO;
import com.diviso.graeshoppe.client.store.model.DeliveryInfo;
import com.diviso.graeshoppe.client.store.model.DeliveryInfoDTO;
import com.diviso.graeshoppe.client.store.model.Review;
import com.diviso.graeshoppe.client.store.model.Store;
import com.diviso.graeshoppe.client.store.model.StoreAddress;
import com.diviso.graeshoppe.client.store.model.StoreAddressDTO;
import com.diviso.graeshoppe.client.store.model.StoreBundleDTO;
import com.diviso.graeshoppe.client.store.model.StoreDTO;
import com.diviso.graeshoppe.client.store.model.StoreSettings;
import com.diviso.graeshoppe.client.store.model.StoreSettingsDTO;
import com.diviso.graeshoppe.client.store.model.StoreType;
import com.diviso.graeshoppe.client.store.model.StoreTypeDTO;
import com.diviso.graeshoppe.client.store.model.Type;
import com.diviso.graeshoppe.client.store.model.TypeDTO;
import com.diviso.graeshoppe.client.store.model.UserRating;
import com.diviso.graeshoppe.service.StoreQueryService;
import com.diviso.graeshoppe.service.mapper.BannerMapper;
import com.diviso.graeshoppe.service.mapper.*;
import com.diviso.graeshoppe.web.rest.util.ServiceUtility;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.diviso.graeshoppe.client.store.model.PreOrderSettings;

import com.diviso.graeshoppe.client.store.model.PreOrderSettingsDTO;
@Service
public class StoreQueryServiceImpl implements StoreQueryService {

	@Autowired
	private ServiceUtility serviceUtility;
	@Autowired
	StoreResourceApi storeResourceApi;

	@Autowired

	private StoreAddressResourceApi storeAddressResourceApi;

	@Autowired
	private StoreSettingsResourceApi storeSettingsResourceApi;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	BannerMapper bannerMapper;
	@Autowired
	StoreMapper storeMapper;
	@Autowired
	DeliveryInfoMapper deliveryInfoMapper;
	@Autowired
	StoreTypeMapper storeTypeMapper;
	@Autowired
	TypeMapper typeMapper;
	@Autowired
	PreOrderSettingsMapper preOrderSettingsMapper;
	private RestHighLevelClient restHighLevelClient;

	public StoreQueryServiceImpl(RestHighLevelClient restHighLevelClient) {

		this.restHighLevelClient = restHighLevelClient;
	}

	/**
	 * @param storeId
	 * @param pageable
	 */
	/*
	 * @Override public Page<Review> findAllReviews(String storeId, Pageable
	 * pageable) {
	 * 
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
	 * builder.query(termQuery("store.regNo", storeId));
	 * 
	 * SearchRequest searchRequest = serviceUtility.generateSearchRequest("review",
	 * pageable.getPageSize(), pageable.getPageNumber(), builder);
	 * 
	 * SearchResponse searchResponse = null;
	 * 
	 * try { searchResponse = restHighLevelClient.search(searchRequest,
	 * RequestOptions.DEFAULT); } catch (IOException e) { // TODO Auto-generated
	 * e.printStackTrace(); } return serviceUtility.getPageResult(searchResponse,
	 * pageable, new Review()); }
	 */

	/**
	 * @param regNo
	 */
	@Override
	public Store findStoreByRegNo(String regNo) {

		QueryBuilder queryBuilder = QueryBuilders.termQuery("regNo.keyword", regNo);
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryBuilder);

		SearchResponse searchResponse = serviceUtility.searchResponseForObject("store", queryBuilder);
		return serviceUtility.getObjectResult(searchResponse, new Store());

		/*
		 * try { searchResponse = restHighLevelClient.search(searchRequest,
		 * RequestOptions.DEFAULT); } catch (IOException e) { // TODO Auto-generated
		 * e.printStackTrace(); }
		 * 
		 * return serviceUtility.getObjectResult(searchResponse, new Store());
		 */
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

	private List<DeliveryInfoDTO> findDeliveryInfoByStoreId(Long id) {

		QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(matchAllQuery()).filter(QueryBuilders.termQuery("store.id", id));
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryBuilder);

		SearchResponse searchResponse = serviceUtility.searchResponseForSourceBuilder("deliveryinfo", builder);
		SearchHit[] searchHit = searchResponse.getHits().getHits();

		List<DeliveryInfoDTO> deliveryInfoDTOList = new ArrayList<>();
		for (SearchHit hit : searchHit) {
			DeliveryInfo deliveryInfo = objectMapper.convertValue(hit.getSourceAsMap(), DeliveryInfo.class);
			DeliveryInfoDTO deliveryInfoDTO = deliveryInfoMapper.toDto(deliveryInfo);
			deliveryInfoDTOList.add(deliveryInfoDTO);
		}
		return deliveryInfoDTOList;
		/*
		 * builder.query(termQuery("store.id", id));
		 * 
		 * SearchRequest searchRequest = new SearchRequest("deliveryinfo");
		 * 
		 * SearchResponse searchResponse = null;
		 * 
		 * try { searchResponse = restHighLevelClient.search(searchRequest,
		 * RequestOptions.DEFAULT); } catch (IOException e) { // TODO Auto-generated
		 * e.printStackTrace(); }
		 * 
		 * SearchHit[] searchHit = searchResponse.getHits().getHits();
		 * 
		 * List<DeliveryInfoDTO> deliveryInfoDTOList = new ArrayList<>();
		 * 
		 * for (SearchHit hit : searchHit) { DeliveryInfo deliveryInfo =
		 * objectMapper.convertValue(hit.getSourceAsMap(), DeliveryInfo.class);
		 * DeliveryInfoDTO deliveryInfoDTO = deliveryInfoMapper.toDto(deliveryInfo);
		 * deliveryInfoDTOList.add(deliveryInfoDTO); }
		 * 
		 * return deliveryInfoDTOList;
		 */
		// return serviceUtility.getPageResult(searchResponse, pageable, new
		// DeliveryInfo());

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

	private List<TypeDTO> findAllDeliveryTypesByStoreId(String storeId) {

		QueryBuilder queryBuilder = QueryBuilders.boolQuery()
				.must(matchAllQuery()).filter(QueryBuilders.termQuery("store.regNo", storeId));
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryBuilder);
		
		SearchResponse response = serviceUtility.searchResponseForSourceBuilder("deliveryinfo", builder);
		SearchHit[] searchHit = response.getHits().getHits();
		List<DeliveryInfo> deliveryInfos = new ArrayList<>();
		for (SearchHit hit : searchHit) {
			DeliveryInfo deliveryInfo = objectMapper.convertValue(hit.getSourceAsMap(), DeliveryInfo.class);
			deliveryInfos.add(deliveryInfo);
		}
		List<TypeDTO> typesDTOList = new ArrayList<TypeDTO>();

		deliveryInfos.forEach(deliveryInfo -> {
			Type result = deliveryInfo.getType();
			TypeDTO typeDTO = typeMapper.toDto(result);
			typesDTOList.add(typeDTO);

		});
		return typesDTOList;
		
		/*
		 * builder.query(termQuery("store.regNo", storeId));
		 * 
		 * SearchRequest searchRequest = new SearchRequest("deliveryinfo");
		 * 
		 * SearchResponse searchResponse = null;
		 * 
		 * try { searchResponse = restHighLevelClient.search(searchRequest,
		 * RequestOptions.DEFAULT); } catch (IOException e) { // TODO Auto-generated
		 * e.printStackTrace(); }
		 * 
		 * SearchHit[] searchHit = searchResponse.getHits().getHits();
		 * 
		 * List<DeliveryInfo> deliveryInfos = new ArrayList<>();
		 * 
		 * for (SearchHit hit : searchHit) { DeliveryInfo deliveryInfo =
		 * objectMapper.convertValue(hit.getSourceAsMap(), DeliveryInfo.class); //
		 * DeliveryInfoDTO deliveryInfoDTO=deliveryInfoMapper.toDto(deliveryInfo);
		 * deliveryInfos.add(deliveryInfo); }
		 * 
		 * List<TypeDTO> typesDTOList = new ArrayList<TypeDTO>();
		 * 
		 * deliveryInfos.forEach(deliveryInfo -> { Type result = deliveryInfo.getType();
		 * TypeDTO typeDTO = typeMapper.toDto(result); typesDTOList.add(typeDTO);
		 * 
		 * });
		 * 
		 * return typesDTOList;
		 */

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

	private List<StoreTypeDTO> findAllStoreTypesByStoreId(String regNo) {
		QueryBuilder queryBuilder = QueryBuilders.boolQuery()
				.must(matchAllQuery()).filter(QueryBuilders.termQuery("store.regNo.keyword", regNo));

		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryBuilder);
		SearchResponse searchResponse = serviceUtility.searchResponseForSourceBuilder("storetype", builder);
		SearchHit[] searchHit = searchResponse.getHits().getHits();
		List<StoreTypeDTO> storeTypeDTOList = new ArrayList<>();
		for (SearchHit hit : searchHit) {
			StoreType storeType = objectMapper.convertValue(hit.getSourceAsMap(), StoreType.class);
			StoreTypeDTO storeTypeDTO = storeTypeMapper.toDto(storeType);
			storeTypeDTOList.add(storeTypeDTO);
		}

		return storeTypeDTOList;
		
		/*
		 * builder.query(termQuery("store.regNo", regNo));
		 * 
		 * SearchRequest searchRequest = new SearchRequest("storetype");
		 * 
		 * SearchResponse searchResponse = null;
		 * 
		 * try { searchResponse = restHighLevelClient.search(searchRequest,
		 * RequestOptions.DEFAULT); } catch (IOException e) { // TODO Auto-generated
		 * e.printStackTrace(); }
		 * 
		 * SearchHit[] searchHit = searchResponse.getHits().getHits();
		 * 
		 * List<StoreTypeDTO> storeTypeDTOList = new ArrayList<>();
		 * 
		 * for (SearchHit hit : searchHit) { StoreType storeType =
		 * objectMapper.convertValue(hit.getSourceAsMap(), StoreType.class);
		 * StoreTypeDTO storeTypeDTO = storeTypeMapper.toDto(storeType);
		 * storeTypeDTOList.add(storeTypeDTO); }
		 * 
		 * return storeTypeDTOList;
		 */
		// return serviceUtility.getPageResult(searchResponse, pageable, new
		// StoreType()).getContent();

	}

	/**
	 * @param storeId
	 */
	@Override
	public Page<Banner> findBannersByStoreId(String storeId, Pageable pageable) {
		QueryBuilder queryBuilder = QueryBuilders.boolQuery()
				.must(matchAllQuery()).filter(QueryBuilders.termQuery("store.regNo.keyword", storeId));
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryBuilder);
		SearchResponse response = serviceUtility.searchResponseForPage("banner", builder, pageable);
		return serviceUtility.getPageResult(response, pageable, new Banner());

		/*
		 * builder.query(termQuery("store.regNo.keyword", storeId)); SearchRequest
		 * searchRequest = serviceUtility.generateSearchRequest("banner",
		 * pageable.getPageSize(), pageable.getPageNumber(), builder);
		 * 
		 * SearchResponse searchResponse = null;
		 * 
		 * try { searchResponse = restHighLevelClient.search(searchRequest,
		 * RequestOptions.DEFAULT); } catch (IOException e) { // TODO Auto-generated
		 * e.printStackTrace(); } return serviceUtility.getPageResult(searchResponse,
		 * pageable, new DeliveryInfo());
		 */

	}

	/**
	 * @param regNo
	 */

	private List<BannerDTO> findAllBannersByStoreId(String regNo) {

		//QueryBuilder queryBuilder = QueryBuilders.termQuery("store.regNo.keyword", regNo);
		QueryBuilder dslQuery = QueryBuilders.boolQuery()
				.must(matchAllQuery()).filter(QueryBuilders.termQuery("store.regNo.keyword", regNo));
		
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		searchSourceBuilder.query(dslQuery);

	//	SearchRequest searchRequest = new SearchRequest("banner");
		//searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = serviceUtility.searchResponseForSourceBuilder("banner", searchSourceBuilder);
		/*SearchResponse searchResponse = null;
		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}*/

		SearchHit[] searchHit = searchResponse.getHits().getHits();


		List<BannerDTO> bannerDTOList = new ArrayList<>();
		for (SearchHit hit : searchHit) {
			Banner banner = objectMapper.convertValue(hit.getSourceAsMap(), Banner.class);
			BannerDTO bannerDTO = bannerMapper.toDto(banner);
			bannerDTOList.add(bannerDTO);
		}
		return bannerDTOList;
		
		/*
		 * searchSourceBuilder.query(termQuery("store.regNo.keyword", regNo));
		 * 
		 * SearchRequest searchRequest = new SearchRequest("banner");
		 * searchRequest.source(searchSourceBuilder); SearchResponse searchResponse =
		 * null; try { searchResponse = restHighLevelClient.search(searchRequest,
		 * RequestOptions.DEFAULT); } catch (IOException e) { // TODO Auto-generated
		 * e.printStackTrace(); }
		 * 
		 * SearchHit[] searchHit = searchResponse.getHits().getHits();
		 * 
		 * 
		 * List<BannerDTO> bannerDTOList = new ArrayList<>();
		 * 
		 * for (SearchHit hit : searchHit) { Banner banner =
		 * objectMapper.convertValue(hit.getSourceAsMap(), Banner.class); BannerDTO
		 * bannerDTO = bannerMapper.toDto(banner); bannerDTOList.add(bannerDTO); }
		 * 
		 * return bannerDTOList;
		 */

	}

	public StoreDTO findStoreDTOByRegNo(String regNo) {
		Store store = findStoreByRegNo(regNo);
		return storeMapper.toDto(store);
		// return storeResourceApi.getStoreUsingGET(store.getId()).getBody();
	}

	
	private PreOrderSettingsDTO findPreOrderSettingsByRegNo(String regNo) {
		//QueryBuilder queryBuilder = QueryBuilders.termQuery("regNo.keyword", regNo);
		QueryBuilder dslQuery = QueryBuilders.boolQuery()
				.must(matchAllQuery()).filter(QueryBuilders.termQuery("regNo.keyword", regNo));
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(dslQuery);

		SearchResponse searchResponse = serviceUtility.searchResponseForObject("store", dslQuery);
		Store store= serviceUtility.getObjectResult(searchResponse, new Store());
		return preOrderSettingsMapper.toDto( store.getPreOrderSettings());
		
	}
	
	
	
	
	public ResponseEntity<StoreBundleDTO> getStoreBundle(String regNo) {

		Store store = findStoreByRegNo(regNo);

		StoreAddress storeAdrress = store.getStoreAddress();

		StoreSettings storeSettings = store.getStoreSettings();

		StoreDTO storeDTO = new StoreDTO();

		List<DeliveryInfoDTO> deliveryDTOs = new ArrayList<DeliveryInfoDTO>();

		List<TypeDTO> typeDTOs = new ArrayList<TypeDTO>();

		List<StoreTypeDTO> storeTypeDTO = new ArrayList<StoreTypeDTO>();

		List<BannerDTO> bannerDTO = new ArrayList<BannerDTO>();

		if (store != null) {
			storeDTO = storeResourceApi.getStoreUsingGET(store.getId()).getBody();

			deliveryDTOs.addAll(findDeliveryInfoByStoreId(storeDTO.getId()));

			typeDTOs.addAll(findAllDeliveryTypesByStoreId(regNo));

			storeTypeDTO.addAll(findAllStoreTypesByStoreId(regNo));

			bannerDTO.addAll(findAllBannersByStoreId(regNo));

		}
		StoreAddressDTO storeAddressDTO = new StoreAddressDTO();
		if (storeAdrress != null) {
			storeAddressDTO = storeAddressResourceApi.getStoreAddressUsingGET(storeAdrress.getId()).getBody();

		}

		StoreSettingsDTO storeSettingsDTO = new StoreSettingsDTO();

		if (storeSettings != null) {
			storeSettingsDTO = storeSettingsResourceApi.getStoreSettingsUsingGET(storeSettings.getId()).getBody();
		}

		
		
		
		StoreBundleDTO bundle = new StoreBundleDTO();

		bundle.setStore(storeDTO);

		bundle.setDeliveryInfos(deliveryDTOs);

		bundle.setTypes(typeDTOs);

		bundle.setBanners(bannerDTO);

		bundle.setStoreType(storeTypeDTO);

		bundle.setStoreSettings(storeSettingsDTO);

		bundle.setStoreAddress(storeAddressDTO);
		
		bundle.setPreOrderSettings(findPreOrderSettingsByRegNo(regNo));

		return ResponseEntity.ok().body(bundle);

	}

	/*
	 * public ResponseEntity<StoreBundleDTO> getStoreBundle(String regNo) {
	 * 
	 * Store store = findStoreByRegNo(regNo);
	 * 
	 * StoreAddress storeAdrress = store.getStoreAddress();
	 * 
	 * StoreSettings storeSettings = store.getStoreSettings();
	 * 
	 * StoreDTO storeDTO = new StoreDTO();
	 * 
	 * List<DeliveryInfoDTO> deliveryDTOs = new ArrayList<DeliveryInfoDTO>();
	 * 
	 * List<TypeDTO> typeDTOs = new ArrayList<TypeDTO>();
	 * 
	 * List<StoreTypeDTO> storeTypeDTO = new ArrayList<StoreTypeDTO>();
	 * 
	 * List<BannerDTO> bannerDTO = new ArrayList<BannerDTO>();
	 * 
	 * if (store != null) { storeDTO= storeMapper.toDto(store);
	 * 
	 * // storeDTO = storeResourceApi.getStoreUsingGET(store.getId()).getBody();
	 * 
	 * deliveryDTOs.addAll(deliveryInfoResourceApi
	 * .listToDtoUsingPOST1(findDeliveryInfoByStoreId(storeDTO.getId()).getContent()
	 * ).getBody());
	 * 
	 * typeDTOs.addAll(typeResourceApi.listToDtoUsingPOST3(
	 * findAllDeliveryTypesByStoreId(regNo)).getBody());
	 * 
	 * storeTypeDTO.addAll(storeTypeResourceApi.listToDtoUsingPOST2(
	 * findAllStoreTypesByStoreId(regNo)).getBody());
	 * 
	 * bannerDTO.addAll(bannerResourceApi.listToDtoUsingPOST(findAllBannersByStoreId
	 * (regNo)).getBody()); } StoreAddressDTO storeAddressDTO = new
	 * StoreAddressDTO(); if (storeAdrress != null) { storeAddressDTO =
	 * storeAddressResourceApi.getStoreAddressUsingGET(storeAdrress.getId()).getBody
	 * ();
	 * 
	 * }
	 * 
	 * StoreSettingsDTO storeSettingsDTO = new StoreSettingsDTO();
	 * 
	 * if (storeSettings != null) { storeSettingsDTO =
	 * storeSettingsResourceApi.getStoreSettingsUsingGET(storeSettings.getId()).
	 * getBody(); }
	 * 
	 * StoreBundleDTO bundle = new StoreBundleDTO();
	 * 
	 * bundle.setStore(storeDTO);
	 * 
	 * bundle.setDeliveryInfos(deliveryDTOs);
	 * 
	 * bundle.setTypes(typeDTOs);
	 * 
	 * bundle.setBanners(bannerDTO);
	 * 
	 * bundle.setStoreType(storeTypeDTO);
	 * 
	 * bundle.setStoreSettings(storeSettingsDTO);
	 * 
	 * bundle.setStoreAddress(storeAddressDTO);
	 * 
	 * return ResponseEntity.ok().body(bundle);
	 * 
	 * }
	 * 
	 * 
	 */

	public /* ResponseEntity<BannerDTO> */ BannerDTO findBanner(Long id) {
		QueryBuilder dslQuery = QueryBuilders.boolQuery()
				.must(matchAllQuery()).filter(termQuery("id", id));

		SearchResponse searchResponse = serviceUtility.searchResponseForObject("banner", dslQuery);
		Banner result = serviceUtility.getObjectResult(searchResponse, new Banner());

		return bannerMapper.toDto(result);

		// return bannerResourceApi.getBannerUsingGET(id);
	}

}
