package com.diviso.graeshoppe.service.impl;

import static org.elasticsearch.action.search.SearchType.QUERY_THEN_FETCH;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.util.PageCacheRecycler;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.InternalOrder.Aggregation;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
//import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.diviso.graeshoppe.client.customer.model.Customer;
import com.diviso.graeshoppe.client.order.model.Order;
import com.diviso.graeshoppe.client.product.api.AuxilaryLineItemResourceApi;
import com.diviso.graeshoppe.client.product.api.CategoryResourceApi;
import com.diviso.graeshoppe.client.product.api.ComboLineItemResourceApi;
import com.diviso.graeshoppe.client.product.api.ProductResourceApi;
import com.diviso.graeshoppe.client.product.api.StockCurrentResourceApi;
import com.diviso.graeshoppe.client.product.api.StockEntryResourceApi;
import com.diviso.graeshoppe.client.product.api.UomResourceApi;
import com.diviso.graeshoppe.client.product.model.Address;
import com.diviso.graeshoppe.client.product.model.AuxilaryLineItem;
import com.diviso.graeshoppe.client.product.model.AuxilaryLineItemDTO;
import com.diviso.graeshoppe.client.product.model.Category;
import com.diviso.graeshoppe.client.product.model.CategoryDTO;
import com.diviso.graeshoppe.client.product.model.ComboLineItem;
import com.diviso.graeshoppe.client.product.model.ComboLineItemDTO;
import com.diviso.graeshoppe.client.product.model.Discount;
import com.diviso.graeshoppe.client.product.model.EntryLineItem;
import com.diviso.graeshoppe.client.product.model.Location;
import com.diviso.graeshoppe.client.product.model.Product;
import com.diviso.graeshoppe.client.product.model.ProductDTO;
import com.diviso.graeshoppe.client.product.model.Reason;
import com.diviso.graeshoppe.client.product.model.StockCurrent;
import com.diviso.graeshoppe.client.product.model.StockCurrentDTO;
import com.diviso.graeshoppe.client.product.model.StockEntry;
import com.diviso.graeshoppe.client.product.model.StockEntryDTO;
import com.diviso.graeshoppe.client.product.model.UOM;
import com.diviso.graeshoppe.client.product.model.UOMDTO;
import com.diviso.graeshoppe.domain.ResultBucket;
import com.diviso.graeshoppe.service.ProductQueryService;
import com.diviso.graeshoppe.service.mapper.*;
import com.diviso.graeshoppe.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.web.rest.util.ServiceUtility;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProductQueryServiceImpl implements ProductQueryService {

	Logger log = LoggerFactory.getLogger(ProductQueryServiceImpl.class);

	@Autowired
	private ServiceUtility serviceUtility;

	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	CategoryMapper categoryMapper;

	@Autowired
	ProductMapper productMapper;
	@Autowired
	StockEntryMapper stockEntryMapper;
	@Autowired
	UOMMapper uomMapper;
	@Autowired
	AuxilaryLineItemMapper auxilaryLineItemMapper;
	@Autowired
	ComboLineItemMapper comboLineItemMapper;
	@Autowired
	UomResourceApi uomResourceApi;

	@Autowired
	CategoryResourceApi categoryResourceApi;

	@Autowired
	private ProductResourceApi productResourceApi;
	@Autowired
	StockCurrentResourceApi stockCurrentResourceApi;

	@Autowired
	private StockEntryResourceApi stockEntryResourceApi;

	@Autowired
	ComboLineItemResourceApi comboLineItemResourceApi;

	@Autowired
	private AuxilaryLineItemResourceApi auxilaryLineItemResourceApi;

	private RestHighLevelClient restHighLevelClient;

	public ProductQueryServiceImpl(RestHighLevelClient restHighLevelClient) {

		this.restHighLevelClient = restHighLevelClient;
	}

	/**
	 * @param storeId
	 */
	@Override
	public Page<Product> findProductByCategoryId(Long categoryId, String storeId, Pageable pageable) {
		log.debug("<<<<<<<<< findProductByCategoryId >>>>>>>>>", categoryId, storeId);
		QueryBuilder qb = QueryBuilders.boolQuery().must(matchAllQuery()).filter(termQuery("categoryId", categoryId))
				.must(termQuery("storeId.keyword", storeId));

		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(qb);
		SearchResponse sr = serviceUtility.searchResponseForPage("product", builder, pageable);

		return serviceUtility.getPageResult(sr, pageable, new Product());

		/*
		 * builder.query(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery(
		 * "category.id", categoryId)) .must(QueryBuilders.matchQuery("iDPcode",
		 * storeId)));
		 * 
		 * SearchRequest searchRequest = serviceUtility.generateSearchRequest("product",
		 * pageable.getPageSize(), pageable.getPageNumber(), builder);
		 * 
		 * SearchResponse searchResponse = null;
		 * 
		 * try { searchResponse = restHighLevelClient.search(searchRequest,
		 * RequestOptions.DEFAULT); } catch (IOException e) { // TODO Auto-generated
		 * e.printStackTrace(); } return serviceUtility.getPageResult(searchResponse,
		 * pageable, new Product());
		 */
	}

	/**
	 * @param name
	 * @param storeId
	 * 
	 */
	@Override
	public Page<Product> findAllProductByNameAndStoreId(String name, String storeId, Pageable pageable) {
		log.debug("<<<<<<<<<< findAllProductBySearchTerm >>>>>>>>>", name, storeId);
		QueryBuilder qb = QueryBuilders.boolQuery().must(matchAllQuery()).filter(QueryBuilders.matchQuery("name", name))
				.must(QueryBuilders.matchQuery(storeId, storeId));

		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(qb);
		SearchResponse sr = serviceUtility.searchResponseForPage("product", builder, pageable);
		return serviceUtility.getPageResult(sr, pageable, new Product());

		/*
		 * builder.query(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("name",
		 * searchTerm).prefixLength(3)) .must(QueryBuilders.matchQuery("iDPcode",
		 * storeId)));
		 * 
		 * SearchRequest searchRequest = serviceUtility.generateSearchRequest("product",
		 * pageable.getPageSize(), pageable.getPageNumber(), builder);
		 * 
		 * SearchResponse searchResponse = null;
		 * 
		 * try { searchResponse = restHighLevelClient.search(searchRequest,
		 * RequestOptions.DEFAULT); } catch (IOException e) { // TODO Auto-generated
		 * e.printStackTrace(); } return serviceUtility.getPageResult(searchResponse,
		 * pageable, new Product() );
		 */
	}

	/**
	 * @param storeId
	 */
	@Override
	public Page<Product> findAllProductsByIdpCode(String idpCode, Pageable pageable) {
		log.debug("<<<<<<<<<< findAllProductsByIdpCode >>>>>>>>", idpCode);

		QueryBuilder queryDsl = termQuery("iDPcode.keyword", idpCode);
		
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(queryDsl);
		searchSourceBuilder.sort(new FieldSortBuilder("id").order(SortOrder.DESC));
		SearchResponse searchResponse = serviceUtility.searchResponseForPage("product", searchSourceBuilder, pageable);
		return serviceUtility.getPageResult(searchResponse, pageable, new Product());

		/*
		 * if (findProducts(pageable) == null) { throw new
		 * BadRequestAlertException("NO products exist", "Product", "no products"); }
		 * SearchSourceBuilder builder = new SearchSourceBuilder();
		 * builder.query(termQuery("iDPcode", storeId)).sort("id", SortOrder.DESC);
		 * 
		 * SearchRequest searchRequest = serviceUtility.generateSearchRequest("product",
		 * pageable.getPageSize(), pageable.getPageNumber(), builder);
		 * 
		 * SearchResponse searchResponse = null;
		 * 
		 * try { searchResponse = restHighLevelClient.search(searchRequest,
		 * RequestOptions.DEFAULT); } catch (IOException e) { // TODO Auto-generated
		 * e.printStackTrace(); } return serviceUtility.getPageResult(searchResponse,
		 * pageable, new Product() );
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.diviso.graeshoppe.service.QueryService#findProducts(org.springframework.
	 * data.domain.Pageable)
	 */
	/**
	 * @param
	 */
	@Override
	public Page<Product> findProducts(Pageable pageable) {
		log.debug("<<<<<<<<<<<< findProducts >>>>>>>>>>>>", pageable);

		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(matchAllQuery());
		SearchResponse sr = serviceUtility.searchResponseForPage("product", builder, pageable);
		return serviceUtility.getPageResult(sr, pageable, new Product());

		/*
		 * builder.query(matchAllQuery());
		 * 
		 * SearchRequest searchRequest = serviceUtility.generateSearchRequest("product",
		 * pageable.getPageSize(), pageable.getPageNumber(), builder);
		 * 
		 * SearchResponse searchResponse = null;
		 * 
		 * try { searchResponse = restHighLevelClient.search(searchRequest,
		 * RequestOptions.DEFAULT); } catch (IOException e) { // TODO Auto-generated
		 * e.printStackTrace(); } return serviceUtility.getPageResult(searchResponse,
		 * pageable, new Product() );
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.diviso.graeshoppe.service.QueryService#
	 * findNotAuxNotComboProductsByIDPcode(java.lang.String,
	 * org.springframework.data.domain.Pageable)
	 */
	/**
	 * @param iDPcode
	 */
	@Override
	public Page<Product> findNotAuxNotComboProductsByIDPcode(String iDPcode, Pageable pageable) {
		log.debug("<<<<<<<  findNotAuxNotComboProductsByIDPcode >>>>>>>>", iDPcode);
		QueryBuilder qb = QueryBuilders.termQuery("iDPcode.keyword", iDPcode);
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(qb);
		SearchResponse sr = serviceUtility.searchResponseForPage("product", builder, pageable);
		return serviceUtility.getPageResult(sr, pageable, new Product());

		/*
		 * builder.query(termQuery("iDPcode", iDPcode));
		 * 
		 * SearchRequest searchRequest = serviceUtility.generateSearchRequest("product",
		 * pageable.getPageSize(), pageable.getPageNumber(), builder);
		 * 
		 * SearchResponse searchResponse = null;
		 * 
		 * try { searchResponse = restHighLevelClient.search(searchRequest,
		 * RequestOptions.DEFAULT); } catch (IOException e) { // TODO Auto-generated
		 * e.printStackTrace(); }
		 * 
		 * List<Product> products = serviceUtility.getPageResult(searchResponse,
		 * pageable, new Product() ) .getContent();
		 * 
		 * List<Product> notAuxNotComboProducts = new ArrayList<Product>();
		 * 
		 * products.forEach(p -> {
		 * 
		 * if ((p.isIsAuxilaryItem() == false) && (p.getComboLineItems() == null)) {
		 * 
		 * notAuxNotComboProducts.add(p); }
		 * 
		 * });
		 * 
		 * return new PageImpl(notAuxNotComboProducts);
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.diviso.graeshoppe.service.QueryService#findAllAuxilaryProducts()
	 */
	/**
	 * @param storeId
	 */
	@Override
	public Page<Product> findAllAuxilaryProducts(String storeId, Pageable pageable) {

		QueryBuilder qb = QueryBuilders.termsQuery("storeId.keyword", storeId);
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(qb);
		SearchResponse sr = serviceUtility.searchResponseForPage("product", builder, pageable);
		return serviceUtility.getPageResult(sr, pageable, new Product());

		/*
		 * builder.query(termQuery("iDPcode", storeId));
		 * 
		 * Pageable pageable = PageRequest.of(2, 20);
		 * 
		 * SearchRequest searchRequest = serviceUtility.generateSearchRequest("product",
		 * pageable.getPageSize(), pageable.getPageNumber(), builder);
		 * 
		 * SearchResponse searchResponse = null;
		 * 
		 * try { searchResponse = restHighLevelClient.search(searchRequest,
		 * RequestOptions.DEFAULT); } catch (IOException e) { // TODO Auto-generated
		 * e.printStackTrace(); }
		 * 
		 * List<Product> products = serviceUtility.getPageResult(searchResponse,
		 * pageable, new Product() ) .getContent();
		 * 
		 * List<Product> auxilaryProducts = new ArrayList<Product>(); products.forEach(p
		 * -> {
		 * 
		 * if (p.isIsAuxilaryItem().equals(true)) { auxilaryProducts.add(p); } });
		 * 
		 * return new PageImpl(auxilaryProducts);
		 */ }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.diviso.graeshoppe.service.QueryService#findProductById(java.lang.Long)
	 */
	/**
	 * @param id
	 */
	@Override
	public Product findProductById(Long id) {
		QueryBuilder qb = QueryBuilders.termQuery("id", id);
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(qb);
		SearchResponse sr = serviceUtility.searchResponseForObject("product", qb);
		return serviceUtility.getObjectResult(sr, new Product());

		/*
		 * builder.query(termQuery("id", id));
		 * 
		 * SearchRequest searchRequest = new SearchRequest("product");
		 * 
		 * searchRequest.source(builder); SearchResponse searchResponse = null;
		 * 
		 * try { searchResponse = restHighLevelClient.search(searchRequest,
		 * RequestOptions.DEFAULT); } catch (IOException e) { // TODO Auto-generated
		 * e.printStackTrace(); }
		 * 
		 * return serviceUtility.getObjectResult(searchResponse, new Product());
		 */ }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.diviso.graeshoppe.service.QueryService#findAllCategories(org.
	 * springframework.data.domain.Pageable)
	 */
	/**
	 * @param pageable
	 */
	@Override
	public Page<Category> findAllCategories(Pageable pageable) {
		QueryBuilder qb = QueryBuilders.matchAllQuery();
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(qb);
		SearchResponse sr = serviceUtility.searchResponseForPage("Category", builder, pageable);
		return serviceUtility.getPageResult(sr, pageable, new Category());

		/*
		 * builder.query(matchAllQuery());
		 * 
		 * SearchRequest searchRequest =
		 * serviceUtility.generateSearchRequest("category", pageable.getPageSize(),
		 * pageable.getPageNumber(), builder);
		 * 
		 * SearchResponse searchResponse = null;
		 * 
		 * try { searchResponse = restHighLevelClient.search(searchRequest,
		 * RequestOptions.DEFAULT); } catch (IOException e) { // TODO Auto-generated
		 * e.printStackTrace(); }
		 * 
		 * return serviceUtility.getPageResult(searchResponse, pageable, new
		 * Category());
		 */
	}

	/**
	 * @param pageable
	 */
	@Override
	public List<String> findAllUom(Pageable pageable) {

		/*
		 * QueryBuilder qb = QueryBuilders.matchAllQuery(); SearchSourceBuilder builder
		 * = new SearchSourceBuilder(); builder.query(qb); SearchResponse sr =
		 * serviceUtility.searchResponseForSourceBuilder("distinct_uom", builder);
		 * return serviceUtility.getListResult(sr, pageable, new UOM());
		 */

		List<String> uomList = new ArrayList<String>();
		List<ResultBucket> resultBucketList = new ArrayList<>();
		SearchRequest searchRequest = new SearchRequest("uom");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(matchAllQuery());
		searchSourceBuilder.aggregation(AggregationBuilders.terms("distinct_uom").field("name.keyword"));

		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = null;
		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("elasticsearch response: {} totalhitssshits" + searchResponse.getHits().getTotalHits());

		System.out.println("elasticsearch response: {} hits .toostring" + searchResponse.toString());
		// searchResponse.getHits(). Aggregations
		Aggregations aggregations = null;
		aggregations = searchResponse.getAggregations();
		Terms uomAgg = searchResponse.getAggregations().get("distinct_uom");
		for (int i = 0; i < uomAgg.getBuckets().size(); i++) {
			uomList.add(uomAgg.getBuckets().get(i).getKey().toString());

		}

		return uomList;

	}

	/**
	 * @param storeId
	 */
	@Override
	public Page<EntryLineItem> findAllEntryLineItemsByIdpCode(String idpCode, Pageable pageable) {
		QueryBuilder qb = QueryBuilders.termQuery("product.iDPcode.keyword", idpCode);
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(qb);
		SearchResponse sr = serviceUtility.searchResponseForPage("entrylineitem", builder, pageable);
		return serviceUtility.getPageResult(sr, pageable, new EntryLineItem());
		/*
		 * builder.query(termQuery("product.iDPcode.keyword", storeId));
		 * 
		 * SearchRequest searchRequest =
		 * serviceUtility.generateSearchRequest("entrylineitem", pageable.getPageSize(),
		 * pageable.getPageNumber(), builder);
		 * 
		 * SearchResponse searchResponse = null;
		 * 
		 * try { searchResponse = restHighLevelClient.search(searchRequest,
		 * RequestOptions.DEFAULT); } catch (IOException e) { // TODO Auto-generated
		 * e.printStackTrace(); }
		 * 
		 * return serviceUtility.getPageResult(searchResponse, pageable, new
		 * EntryLineItem());
		 */
	}

	/**
	 * @param storeId
	 * @param pageable
	 * 
	 */
	@Override
	public Page<StockCurrent> findAllStockCurrents(String storeId, Pageable pageable) {
		QueryBuilder qb = QueryBuilders.termQuery("iDPcode.keyword", storeId);
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(termQuery("iDPcode.keyword", storeId));
		SearchResponse sr = serviceUtility.searchResponseForPage("stockcurrent", builder, pageable);
		return serviceUtility.getPageResult(sr, pageable, new StockCurrent());

		/*
		 * SearchRequest searchRequest =
		 * serviceUtility.generateSearchRequest("stockcurrent", pageable.getPageSize(),
		 * pageable.getPageNumber(), builder);
		 * 
		 * SearchResponse searchResponse = null;
		 * 
		 * try { searchResponse = restHighLevelClient.search(searchRequest,
		 * RequestOptions.DEFAULT); } catch (IOException e) { // TODO Auto-generated
		 * e.printStackTrace(); }
		 * 
		 * return serviceUtility.getPageResult(searchResponse, pageable, new
		 * StockCurrent());
		 */

	}

	/**
	 * @param storeId
	 * 
	 */
	@Override
	public Page<StockEntry> findAllStockEntriesbyIdpCode(String idpCode, Pageable pageable) {

		QueryBuilder qb = QueryBuilders.boolQuery().must(matchAllQuery()).filter(QueryBuilders.termQuery("iDPcode.keyword", idpCode));
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(qb);
		SearchResponse sr = serviceUtility.searchResponseForPage("stockentry", builder, pageable);
		return serviceUtility.getPageResult(sr, pageable, new StockEntry());
		/*
		 * builder.query(termQuery("iDPcode.keyword", storeId));
		 * 
		 * SearchRequest searchRequest =
		 * serviceUtility.generateSearchRequest("stockentry", pageable.getPageSize(),
		 * pageable.getPageNumber(), builder);
		 * 
		 * SearchResponse searchResponse = null;
		 * 
		 * try { searchResponse = restHighLevelClient.search(searchRequest,
		 * RequestOptions.DEFAULT); } catch (IOException e) { // TODO Auto-generated
		 * e.printStackTrace(); }
		 * 
		 * return serviceUtility.getPageResult(searchResponse, pageable, new
		 * StockEntry());
		 */
	}

	/**
	 * @param categoryId
	 * @param storeId
	 * @param pageable
	 */
	@Override
	public Page<StockCurrent> findAllStockCurrentByCategoryId(Long categoryId, String storeId, Pageable pageable) {

		QueryBuilder qb = QueryBuilders.boolQuery().must(matchAllQuery()).filter(QueryBuilders.matchQuery("product.category.id", categoryId))
				.must(QueryBuilders.termQuery("iDPcode.keyword", storeId));
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(qb);
		SearchResponse sr = serviceUtility.searchResponseForPage("stockcurrent", builder, pageable);
		return serviceUtility.getPageResult(sr, pageable, new StockCurrent());

		/*
		 * builder.query(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery(
		 * "product.category.id", categoryId))
		 * .must(QueryBuilders.termQuery("iDPcode.keyword", storeId)));
		 * 
		 * SearchRequest searchRequest =
		 * serviceUtility.generateSearchRequest("stockcurrent", pageable.getPageSize(),
		 * pageable.getPageNumber(), builder);
		 * 
		 * SearchResponse searchResponse = null;
		 * 
		 * try { searchResponse = restHighLevelClient.search(searchRequest,
		 * RequestOptions.DEFAULT); } catch (IOException e) { // TODO Auto-generated
		 * e.printStackTrace(); }
		 * 
		 * return serviceUtility.getPageResult(searchResponse, pageable, new
		 * StockCurrent());
		 */
	}

	/**
	 * @param storeId
	 */
	@Override
	public StockCurrent findStockCurrentByProductId(Long productId, String storeId) {

		QueryBuilder qb = QueryBuilders.boolQuery().must(matchAllQuery()).filter(QueryBuilders.termQuery("product.id", productId))
				.must(QueryBuilders.termQuery("product.iDPcode.keyword", storeId));
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(qb);
		SearchResponse sr = serviceUtility.searchResponseForObject("stockcurrent", qb);
		return serviceUtility.getObjectResult(sr, new StockCurrent());

		/*
		 * builder.query(QueryBuilders.boolQuery().must(QueryBuilders.termQuery(
		 * "product.id", productId))
		 * .must(QueryBuilders.termQuery("product.iDPcode.keyword", storeId)));
		 * 
		 * SearchRequest searchRequest = new SearchRequest("stockcurrent");
		 * 
		 * searchRequest.source(builder); SearchResponse searchResponse = null;
		 * 
		 * try { searchResponse = restHighLevelClient.search(searchRequest,
		 * RequestOptions.DEFAULT); } catch (IOException e) { // TODO Auto-generated
		 * e.printStackTrace(); }
		 * 
		 * return serviceUtility.getObjectResult(searchResponse, new StockCurrent());
		 */
	}

	/**
	 * @param storeId
	 */
	@Override
	public StockEntry findStockEntryByProductId(Long productId, String storeId) {

		QueryBuilder qb = QueryBuilders.boolQuery().must(matchAllQuery()).filter(QueryBuilders.termQuery("product.id", productId))
				.must(QueryBuilders.termQuery("product.userId.keyword", storeId));

		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(qb);
		SearchResponse sr = serviceUtility.searchResponseForObject("stockentry", qb);
		return serviceUtility.getObjectResult(sr, new StockEntry());

		/*
		 * builder.query(QueryBuilders.boolQuery().must(QueryBuilders.termQuery(
		 * "product.id", productId))
		 * .must(QueryBuilders.termQuery("product.userId.keyword", storeId)));
		 * 
		 * SearchRequest searchRequest = new SearchRequest("stockentry");
		 * 
		 * searchRequest.source(builder); SearchResponse searchResponse = null;
		 * 
		 * try { searchResponse = restHighLevelClient.search(searchRequest,
		 * RequestOptions.DEFAULT); } catch (IOException e) { // TODO Auto-generated
		 * e.printStackTrace(); }
		 * 
		 * return serviceUtility.getObjectResult(searchResponse, new StockEntry());
		 */
	}

	/**
	 * @param storeId
	 * @param pageable
	 */
	@Override
	public Page<StockCurrent> findStockCurrentByProductName(String name, String storeId, Pageable pageable) {

		QueryBuilder qb = QueryBuilders.boolQuery().must(matchAllQuery()).filter(QueryBuilders.termQuery("product.name.keyword", name))
				.must(QueryBuilders.termQuery("product.iDPcode.keyword", storeId));
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(qb);
		SearchResponse sr = serviceUtility.searchResponseForPage("stockcurrent", builder, pageable);
		return serviceUtility.getPageResult(sr, pageable, new StockCurrent());

		/*
		 * builder.query(QueryBuilders.boolQuery()
		 * .must(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("product.name",
		 * name)) .must(QueryBuilders.termQuery("product.iDPcode.keyword", storeId))));
		 * 
		 * SearchRequest searchRequest =
		 * serviceUtility.generateSearchRequest("stockcurrent", pageable.getPageSize(),
		 * pageable.getPageNumber(), builder);
		 * 
		 * SearchResponse searchResponse = null;
		 * 
		 * try { searchResponse = restHighLevelClient.search(searchRequest,
		 * RequestOptions.DEFAULT); } catch (IOException e) { // TODO Auto-generated
		 * e.printStackTrace(); }
		 * 
		 * Page<StockCurrent> stockCurrentpage =
		 * serviceUtility.getPageResult(searchResponse, pageable, new StockCurrent());
		 * 
		 * List<StockCurrent> stockList = stockCurrentpage.stream() .filter(stockcurrent
		 * -> (stockcurrent.getProduct().isIsAuxilaryItem() == false))
		 * .collect(Collectors.toList());
		 * 
		 * return new PageImpl(stockList);
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.diviso.graeshoppe.service.QueryService#findAuxilaryLineItemsByStoreId
	 * (java.lang.String, org.springframework.data.domain.Pageable)
	 */
	/**
	 * @param iDPcode
	 * @param pageable
	 */
	@Override
	public Page<AuxilaryLineItem> findAuxilaryLineItemsByIDPcode(String iDPcode, Pageable pageable) {
		QueryBuilder qb = QueryBuilders.termQuery("product.iDPcode.keyword", iDPcode);
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(qb);

		SearchResponse sr = serviceUtility.searchResponseForPage("auxilarylineitem", builder, pageable);
		return serviceUtility.getPageResult(sr, pageable, new AuxilaryLineItem());

		/*
		 * builder.query(termQuery("product.iDPcode.keyword", iDPcode));
		 * 
		 * SearchRequest searchRequest =
		 * serviceUtility.generateSearchRequest("auxilarylineitem",
		 * pageable.getPageSize(), pageable.getPageNumber(), builder);
		 * 
		 * SearchResponse searchResponse = null;
		 * 
		 * try { searchResponse = restHighLevelClient.search(searchRequest,
		 * RequestOptions.DEFAULT); } catch (IOException e) { // TODO Auto-generated
		 * e.printStackTrace(); }
		 * 
		 * return serviceUtility.getPageResult(searchResponse, pageable, new
		 * AuxilaryLineItem());
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.diviso.graeshoppe.service.QueryService#findUOMByStoreId(java.lang.
	 * String, org.springframework.data.domain.Pageable)
	 */
	/**
	 * @param iDPcode
	 */
	@Override
	public Page<UOM> findUOMByIDPcode(String iDPcode, Pageable pageable) {
		QueryBuilder qb = QueryBuilders.boolQuery().must(matchAllQuery()).filter(QueryBuilders.termQuery("iDPcode.keyword", iDPcode));
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(qb);
		SearchResponse sr = serviceUtility.searchResponseForPage("uom", builder, pageable);
		return serviceUtility.getPageResult(sr, pageable, new UOM());

		/*
		 * builder.query(termQuery("iDPcode.keyword", iDPcode));
		 * 
		 * SearchRequest searchRequest = serviceUtility.generateSearchRequest("uom",
		 * pageable.getPageSize(), pageable.getPageNumber(), builder);
		 * 
		 * SearchResponse searchResponse = null;
		 * 
		 * try { searchResponse = restHighLevelClient.search(searchRequest,
		 * RequestOptions.DEFAULT); } catch (IOException e) { // TODO Auto-generated
		 * e.printStackTrace(); }
		 * 
		 * return serviceUtility.getPageResult(searchResponse, pageable, new UOM());
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.diviso.graeshoppe.service.QueryService#findCategoryById(java.lang.Long)
	 */
	/**
	 * @param id
	 */

	@Override
	public Category findCategoryById(Long id) {
		SearchSourceBuilder builder = new SearchSourceBuilder();

		builder.query(termQuery("id", id));

		SearchRequest searchRequest = new SearchRequest("category");

		searchRequest.source(builder);
		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getObjectResult(searchResponse, new Category());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.diviso.graeshoppe.service.QueryService#findUOMById(java.lang.Long)
	 */
	/**
	 * @param id
	 */
	@Override
	public UOM findUOMById(Long id) {

		SearchSourceBuilder builder = new SearchSourceBuilder();

		builder.query(termQuery("id", id));

		SearchRequest searchRequest = new SearchRequest("uom");

		searchRequest.source(builder);
		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getObjectResult(searchResponse, new UOM());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.diviso.graeshoppe.service.QueryService#finAllComboLineItemsByProductId(
	 * java.lang.Long)
	 */
	/**
	 * @param id
	 */
	@Override
	public List<ComboLineItem> finAllComboLineItemsByProductId(Long id) {

		SearchSourceBuilder builder = new SearchSourceBuilder();

		builder.query(termQuery("product.id", id));

		Pageable pageable = PageRequest.of(2, 20);
		SearchRequest searchRequest = serviceUtility.generateSearchRequest("combolineitem", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getPageResult(searchResponse, pageable, new ComboLineItem()).getContent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.diviso.graeshoppe.service.QueryService#findAllAuxilaryProductsByProductId
	 * ()
	 */
	/**
	 * @param productId
	 */
	@Override
	public List<AuxilaryLineItem> findAllAuxilaryProductsByProductId(Long productId) {

		SearchSourceBuilder builder = new SearchSourceBuilder();

		builder.query(termQuery("product.id", productId));

		Pageable pageable = PageRequest.of(2, 20);
		SearchRequest searchRequest = serviceUtility.generateSearchRequest("auxilarylineitem", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getPageResult(searchResponse, pageable, new AuxilaryLineItem()).getContent();

	}

	/**
	 * @param id
	 */
	@Override
	public StockEntry findStockEntryById(Long id) {

		SearchSourceBuilder builder = new SearchSourceBuilder();

		builder.query(termQuery("id", id));

		SearchRequest searchRequest = new SearchRequest("stockentry");

		searchRequest.source(builder);
		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getObjectResult(searchResponse, new StockEntry());

	}

	/**
	 * @param productId
	 */
	@Override
	public Discount findDiscountByProductId(Long productId) {

		SearchSourceBuilder builder = new SearchSourceBuilder();

		builder.query(termQuery("id", productId));

		SearchRequest searchRequest = new SearchRequest("discount");

		searchRequest.source(builder);
		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getObjectResult(searchResponse, new Discount());

	}

	/**
	 * @param id
	 */
	@Override
	public List<EntryLineItem> findAllEntryLineItemsByStockEntryId(Long id) {

		SearchSourceBuilder builder = new SearchSourceBuilder();

		builder.query(termQuery("stockentry.id", id));

		Pageable pageable = PageRequest.of(2, 20);
		SearchRequest searchRequest = serviceUtility.generateSearchRequest("entrylineitem", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getPageResult(searchResponse, pageable, new EntryLineItem()).getContent();

	}

	/**
	 * @param id
	 */
	@Override
	public Reason findReasonByStockEntryId(Long id) {

		SearchSourceBuilder builder = new SearchSourceBuilder();

		builder.query(termQuery("id", id));

		SearchRequest searchRequest = new SearchRequest("reason");

		searchRequest.source(builder);
		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getObjectResult(searchResponse, new Reason());

	}

	/**
	 * @param id
	 */
	@Override
	public Location findLocationByStockEntryId(Long id) {

		SearchSourceBuilder builder = new SearchSourceBuilder();

		builder.query(termQuery("id", id));

		SearchRequest searchRequest = new SearchRequest("stockentry");

		searchRequest.source(builder);
		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		StockEntry stockentry = serviceUtility.getObjectResult(searchResponse, new StockEntry());

		return stockentry.getLocation();
	}

	/**
	 * @param idpcode
	 */
	@Override
	public Page<Location> findLocationByIdpcode(String idpcode, Pageable pageable) {

		SearchSourceBuilder builder = new SearchSourceBuilder();

		builder.query(termQuery("iDPcode.keyword", idpcode));

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("location", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getPageResult(searchResponse, pageable, new Location());

	}

	/**
	 * @param idpcode
	 * @param pageable
	 */
	@Override
	public Page<Reason> findReasonByIdpcode(String idpcode, Pageable pageable) {

		SearchSourceBuilder builder = new SearchSourceBuilder();

		builder.query(termQuery("iDPcode.keyword", idpcode));

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("reason", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getPageResult(searchResponse, pageable, new Reason());

	}

	/**
	 * @param id
	 * @param pageable
	 */
	@Override
	public Page<EntryLineItem> findAllEntryLineItemsByStockEntryId(String id, Pageable pageable) {

		SearchSourceBuilder builder = new SearchSourceBuilder();

		builder.query(termQuery("stockentry.id.keyword", id));

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("entrylineitem", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getPageResult(searchResponse, pageable, new EntryLineItem());

	}

	/**
	 * @param id
	 */
	@Override
	public Address findAddressByStockEntryId(Long id) {

		SearchSourceBuilder builder = new SearchSourceBuilder();

		builder.query(termQuery("id", id));

		SearchRequest searchRequest = new SearchRequest("location");

		searchRequest.source(builder);
		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		Location location = serviceUtility.getObjectResult(searchResponse, new Location());

		return location.getAddress();

	}

	/**
	 * @param storeId
	 * @param name
	 * @param pageable
	 * 
	 */
	@Override
	public Page<Category> findAllCategoriesByNameAndIdpCode(String name, String idpCode, Pageable pageable) {

		SearchSourceBuilder builder = new SearchSourceBuilder();

		builder.query(QueryBuilders.boolQuery().must(matchAllQuery()).filter(QueryBuilders.matchQuery("name.keyword", name).prefixLength(3))
				.must(QueryBuilders.termQuery("iDPcode.keyword", idpCode)));

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("category", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getPageResult(searchResponse, pageable, new Category());

	}

	/**
	 * @param iDPcode
	 */
	@Override
	public Page<Category> findAllCategoriesByIdpCode(String idpCode, Pageable pageable) {

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.termQuery("iDPcode.keyword", idpCode));
		SearchResponse searchResponse = serviceUtility.searchResponseForPage("category", searchSourceBuilder, pageable);

		Page<Category> page= serviceUtility.getPageResult(searchResponse, pageable, new Customer());

		
		return page;
		
		
		
		
		
		
		
		
		
		/*SearchSourceBuilder builder = new SearchSourceBuilder();

		builder.query(QueryBuilders.termQuery("iDPcode.keyword", idpCode));

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("category", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getPageResult(searchResponse, pageable, new Category());*/

	}

	public Page<CategoryDTO> findAllCategoryDTOsByIdpCode(String iDPcode, Pageable pageable) {
		SearchSourceBuilder builder = new SearchSourceBuilder();

		builder.query(QueryBuilders.termQuery("iDPcode.keyword", iDPcode));

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("category", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		// Page serviceUtility.getPageResult(searchResponse, pageable, new Category());

		SearchHit[] searchHit = searchResponse.getHits().getHits();

		List<CategoryDTO> list = new ArrayList<>();

		for (SearchHit hit : searchHit) {

			Category category = objectMapper.convertValue(hit.getSourceAsMap(), Category.class);
			System.out.println("categoryKKKKKKKKKKKKKKKKKKKKKKKKK" + category);

			CategoryDTO categoryDTO = categoryMapper.toDto(category);

			System.out.println("categoryDTOOOOOOOOOOOOOOO" + categoryDTO);
			list.add(categoryDTO);
		}

		return new PageImpl(list, pageable, searchResponse.getHits().getTotalHits());

	}

	public CategoryDTO findCategoryDTOById(Long id) {
		QueryBuilder queryDsl = QueryBuilders.termQuery("id", id);
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryDsl);
		SearchResponse searchResponse = serviceUtility.searchResponseForObject("category", queryDsl);
		Category category = serviceUtility.getObjectResult(searchResponse, new Category());
		return categoryMapper.toDto(category);
		// return categoryResourceApi.getCategoryUsingGET(id);
	}

	public ProductDTO findProductDTOById(@PathVariable Long id) {
		QueryBuilder queryDsl = QueryBuilders.termQuery("id", id);
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryDsl);
		SearchResponse searchResponse = serviceUtility.searchResponseForObject("product", queryDsl);
		Product product = serviceUtility.getObjectResult(searchResponse, new Product());
		return productMapper.toDto(product);
		// return productResourceApi.getProductUsingGET(id);
	}

	public StockEntryDTO findStockEntryDTOById(Long id) {
		QueryBuilder queryDsl = QueryBuilders.termQuery("id", id);
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryDsl);
		SearchResponse searchResponse = serviceUtility.searchResponseForObject("stockentry", queryDsl);
		StockEntry stockEntry = serviceUtility.getObjectResult(searchResponse, new StockEntry());
		return stockEntryMapper.toDto(stockEntry);
		// return stockEntryResourceApi.getStockEntryUsingGET(id);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */

	public UOMDTO findUOMDTOById(Long id) {
		QueryBuilder queryDsl = QueryBuilders.termQuery("id", id);
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryDsl);
		SearchResponse searchResponse = serviceUtility.searchResponseForObject("uom", queryDsl);
		UOM uom = serviceUtility.getObjectResult(searchResponse, new UOM());
		return uomMapper.toDto(uom);
		// return uomResourceApi.getUOMUsingGET(id);
	}

	public AuxilaryLineItemDTO findAuxilaryLineItemById(Long id) {
		QueryBuilder queryDsl = QueryBuilders.termQuery("id", id);
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryDsl);
		SearchResponse searchResponse = serviceUtility.searchResponseForObject("auxilaryLineItem", queryDsl);
		AuxilaryLineItem auxilaryLineItem = serviceUtility.getObjectResult(searchResponse, new AuxilaryLineItem());
		return auxilaryLineItemMapper.toDto(auxilaryLineItem);

		// return auxilaryLineItemResourceApi.getAuxilaryLineItemUsingGET(id);
	}

	public ComboLineItemDTO findCombolineItemById(Long id) {
		QueryBuilder queryDsl = QueryBuilders.termQuery("id", id);
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryDsl);
		SearchResponse searchResponse = serviceUtility.searchResponseForObject("comboLineItem", queryDsl);
		ComboLineItem comboLineItem = serviceUtility.getObjectResult(searchResponse, new ComboLineItem());
		return comboLineItemMapper.toDto(comboLineItem);
		// return comboLineItemResourceApi.getComboLineItemUsingGET(id);
	}

	/*
	 * public ResponseEntity<List<StockCurrentDTO>>
	 * searchStockCurrents(@PathVariable String searchTerm, Integer page, Integer
	 * size, ArrayList<String> sort) { return
	 * stockCurrentResourceApi.searchStockCurrentsUsingGET(searchTerm, page, size,
	 * sort); }
	 */

}
