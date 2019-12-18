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


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.diviso.graeshoppe.client.customer.model.Customer;
/*import com.diviso.graeshoppe.client.order.model.Order;
import com.diviso.graeshoppe.client.product.api.AuxilaryLineItemResourceApi;
import com.diviso.graeshoppe.client.product.api.CategoryResourceApi;
import com.diviso.graeshoppe.client.product.api.ComboLineItemResourceApi;
import com.diviso.graeshoppe.client.product.api.ProductResourceApi;
import com.diviso.graeshoppe.client.product.api.StockCurrentResourceApi;
import com.diviso.graeshoppe.client.product.api.StockEntryResourceApi;
import com.diviso.graeshoppe.client.product.api.UomResourceApi;*/
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
import com.diviso.graeshoppe.client.product.model.ProductBundle;
import com.diviso.graeshoppe.client.product.model.ProductDTO;
import com.diviso.graeshoppe.client.product.model.Reason;
import com.diviso.graeshoppe.client.product.model.StockCurrent;
import com.diviso.graeshoppe.client.product.model.StockCurrentDTO;
import com.diviso.graeshoppe.client.product.model.StockEntry;
import com.diviso.graeshoppe.client.product.model.StockEntryBundle;
import com.diviso.graeshoppe.client.product.model.StockEntryDTO;
import com.diviso.graeshoppe.client.product.model.UOM;
import com.diviso.graeshoppe.client.product.model.UOMDTO;
import com.diviso.graeshoppe.client.store.model.Store;
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
	/*@Autowired
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
*/
	private RestHighLevelClient restHighLevelClient;

	public ProductQueryServiceImpl(RestHighLevelClient restHighLevelClient) {

		this.restHighLevelClient = restHighLevelClient;
	}

	/**
	 * @param storeId
	 */
	
	private Page<Product> findProductByCategoryId(Long categoryId, String storeId, Pageable pageable) {
		log.debug("<<<<<<<<< findProductByCategoryId >>>>>>>>>", categoryId, storeId);
		QueryBuilder queryDsl = QueryBuilders.boolQuery().must(matchAllQuery())
				.filter(termQuery("categoryId", categoryId)).must(termQuery("storeId.keyword", storeId));

		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryDsl);
		SearchResponse searchRespose = serviceUtility.searchResponseForPage("product", builder, pageable);

		return serviceUtility.getPageResult(searchRespose, pageable, new Product());

		/*
		 * builder.query(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery(
		 * "category.id", categoryId)) .must(QueryBuilders.matchQuery("iDPcode",
		 * storeId)));
		 * 
		 * 
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
		QueryBuilder queryDsl = QueryBuilders.boolQuery().must(matchAllQuery())
				.filter(QueryBuilders.matchQuery("name", name)).must(QueryBuilders.matchQuery(storeId, storeId));

		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryDsl);
		SearchResponse searchRespose = serviceUtility.searchResponseForPage("product", builder, pageable);
		return serviceUtility.getPageResult(searchRespose, pageable, new Product());

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

		// QueryBuilder queryDsl = termQuery("iDPcode.keyword", idpCode);
		QueryBuilder queryDsl = QueryBuilders.boolQuery().must(matchAllQuery())
				.filter(termQuery("iDPcode.keyword", idpCode));
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
	
	private Page<Product> findProducts(Pageable pageable) {
		log.debug("<<<<<<<<<<<< findProducts >>>>>>>>>>>>", pageable);

		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(matchAllQuery());
		SearchResponse searchRespose = serviceUtility.searchResponseForPage("product", builder, pageable);
		return serviceUtility.getPageResult(searchRespose, pageable, new Product());

		/*
		 * builder.query(matchAllQuery());
		 * 
		 * 
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
	 * @param idpCode
	 */
	@Override
	public Page<Product> findNotAuxNotComboProductsByIDPcode(String idpCode, Pageable pageable) {
		log.debug("<<<<<<<  findNotAuxNotComboProductsByIDPcode >>>>>>>>", idpCode);
		// QueryBuilder queryDsl = QueryBuilders.termQuery("iDPcode.keyword", idpCode);
		QueryBuilder queryDsl = QueryBuilders.boolQuery().must(matchAllQuery())
				.filter(termQuery("iDPcode.keyword", idpCode));

		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryDsl);
		SearchResponse searchRespose = serviceUtility.searchResponseForPage("product", builder, pageable);
		return serviceUtility.getPageResult(searchRespose, pageable, new Product());

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

		// QueryBuilder queryDsl = QueryBuilders.termsQuery("storeId.keyword", storeId);
		QueryBuilder queryDsl = QueryBuilders.boolQuery().must(matchAllQuery())
				.filter(termQuery("storeId.keyword", storeId));

		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryDsl);
		SearchResponse searchRespose = serviceUtility.searchResponseForPage("product", builder, pageable);
		return serviceUtility.getPageResult(searchRespose, pageable, new Product());

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

		// QueryBuilder queryDsl = QueryBuilders.termQuery("id", id);
		QueryBuilder queryDsl = QueryBuilders.boolQuery().must(matchAllQuery()).filter(termQuery("id", id));
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryDsl);
		SearchResponse response = serviceUtility.searchResponseForObject("product", queryDsl);
		return serviceUtility.getObjectResult(response, new Product());

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
	
	private Page<Category> findAllCategories(Pageable pageable) {
		QueryBuilder queryDsl = QueryBuilders.matchAllQuery();
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryDsl);
		SearchResponse searchRespose = serviceUtility.searchResponseForPage("Category", builder, pageable);
		return serviceUtility.getPageResult(searchRespose, pageable, new Category());

		/*
		 * builder.query(matchAllQuery());
		 * 
		 *
		 */
	}

	/**
	 * @param pageable
	 */
	
	private List<String> findAllUom(Pageable pageable) {

		/*
		 * QueryBuilder queryDsl = QueryBuilders.matchAllQuery(); SearchSourceBuilder
		 * builder = new SearchSourceBuilder(); builder.query(queryDsl); SearchResponse
		 * searchRespose = serviceUtility.searchResponseForSourceBuilder("distinct_uom",
		 * builder); return serviceUtility.getListResult(searchRespose, pageable, new
		 * UOM());
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
	
	public Page<EntryLineItem> findAllEntryLineItemsByIdpCode(String idpCode, Pageable pageable) {
		// QueryBuilder queryDsl = QueryBuilders.termQuery("product.iDPcode.keyword",
		// idpCode);
		QueryBuilder queryDsl = QueryBuilders.boolQuery().must(matchAllQuery())
				.filter(termQuery("product.iDPcode.keyword", idpCode));
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryDsl);
		SearchResponse searchRespose = serviceUtility.searchResponseForPage("entrylineitem", builder, pageable);
		return serviceUtility.getPageResult(searchRespose, pageable, new EntryLineItem());
		/*
		 * builder.query(termQuery("product.iDPcode.keyword", storeId));
		 * 
		 *
		 */
	}

	/**
	 * @param storeId
	 * @param pageable
	 * 
	 */
	
	private Page<StockCurrent> findAllStockCurrents(String storeId, Pageable pageable) {
		// QueryBuilder queryDsl = QueryBuilders.termQuery("iDPcode.keyword", storeId);
		QueryBuilder queryDsl = QueryBuilders.boolQuery().must(matchAllQuery())
				.filter(termQuery("iDPcode.keyword", storeId));
		SearchSourceBuilder builder = new SearchSourceBuilder();
		
		builder.query(queryDsl);
		SearchResponse searchRespose = serviceUtility.searchResponseForPage("stockcurrent", builder, pageable);
		return serviceUtility.getPageResult(searchRespose, pageable, new StockCurrent());

	

	}

	/**
	 * @param storeId
	 * 
	 */
	@Override
	public Page<StockEntry> findAllStockEntriesbyIdpCode(String idpCode, Pageable pageable) {

		QueryBuilder queryDsl = QueryBuilders.boolQuery().must(matchAllQuery())
				.filter(QueryBuilders.termQuery("iDPcode.keyword", idpCode));
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryDsl);
		SearchResponse searchRespose = serviceUtility.searchResponseForPage("stockentry", builder, pageable);
		return serviceUtility.getPageResult(searchRespose, pageable, new StockEntry());
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
	
	private Page<StockCurrent> findAllStockCurrentByCategoryId(Long categoryId, String storeId, Pageable pageable) {

		QueryBuilder queryDsl = QueryBuilders.boolQuery()
				.must(QueryBuilders./* match */termQuery("product.category.id", categoryId))

				.must(QueryBuilders.termQuery("iDPcode.keyword", storeId));
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryDsl);
		SearchResponse searchRespose = serviceUtility.searchResponseForPage("stockcurrent", builder, pageable);
		return serviceUtility.getPageResult(searchRespose, pageable, new StockCurrent());

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
	/*
	 * @Override public StockCurrent findStockCurrentByProductId(Long productId,
	 * String storeId) {
	 * 
	 * QueryBuilder queryDsl =
	 * QueryBuilders.boolQuery().must(matchAllQuery()).filter(QueryBuilders.
	 * termQuery("product.id", productId))
	 * .must(QueryBuilders.termQuery("product.iDPcode.keyword", storeId));
	 * SearchSourceBuilder builder = new SearchSourceBuilder();
	 * builder.query(queryDsl); SearchResponse searchRespose =
	 * serviceUtility.searchResponseForObject("stockcurrent", queryDsl); return
	 * serviceUtility.getObjectResult(searchRespose, new StockCurrent()); }
	 */

	/**
	 * @param storeId
	 */
	/*
	 * @Override public StockEntry findStockEntryByProductId(Long productId, String
	 * storeId) {
	 * 
	 * QueryBuilder queryDsl =
	 * QueryBuilders.boolQuery().must(matchAllQuery()).filter(QueryBuilders.
	 * termQuery("product.id", productId))
	 * .must(QueryBuilders.termQuery("product.userId.keyword", storeId));
	 * 
	 * SearchSourceBuilder builder = new SearchSourceBuilder();
	 * builder.query(queryDsl); SearchResponse searchRespose =
	 * serviceUtility.searchResponseForObject("stockentry", queryDsl); return
	 * serviceUtility.getObjectResult(searchRespose, new StockEntry());
	 */
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
	// }

	/**
	 * @param storeId
	 * @param pageable
	 */
	/*
	 * @Override public Page<StockCurrent> findStockCurrentByProductName(String
	 * name, String storeId, Pageable pageable) {
	 * 
	 * QueryBuilder queryDsl =
	 * QueryBuilders.boolQuery().must(matchAllQuery()).filter(QueryBuilders.
	 * termQuery("product.name.keyword", name))
	 * .must(QueryBuilders.termQuery("product.iDPcode.keyword", storeId));
	 * SearchSourceBuilder builder = new SearchSourceBuilder();
	 * builder.query(queryDsl); SearchResponse searchRespose =
	 * serviceUtility.searchResponseForPage("stockcurrent", builder, pageable);
	 * return serviceUtility.getPageResult(searchRespose, pageable, new
	 * StockCurrent());
	 */
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
	// }

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
	
	public Page<AuxilaryLineItem> findAuxilaryLineItemsByIDPcode(String idpCode, Pageable pageable) {
		// QueryBuilder queryDsl = QueryBuilders.termQuery("product.iDPcode.keyword",
		// iDPcode);
		QueryBuilder queryDsl = QueryBuilders.boolQuery().must(matchAllQuery())
				.filter(QueryBuilders.termQuery("product.iDPcode.keyword", idpCode));
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryDsl);

		SearchResponse searchRespose = serviceUtility.searchResponseForPage("auxilarylineitem", builder, pageable);
		return serviceUtility.getPageResult(searchRespose, pageable, new AuxilaryLineItem());

		
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
	public Page<UOM> findUOMByIDPcode(String idpCode, Pageable pageable) {
		QueryBuilder queryDsl = QueryBuilders.boolQuery().must(matchAllQuery())
				.filter(QueryBuilders.termQuery("iDPcode.keyword", idpCode));
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryDsl);
		SearchResponse searchRespose = serviceUtility.searchResponseForPage("uom", builder, pageable);
		return serviceUtility.getPageResult(searchRespose, pageable, new UOM());

		/*
		 * builder.query(termQuery("iDPcode.keyword", iDPcode));
		 * 
		 
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

		QueryBuilder queryDsl = QueryBuilders.boolQuery().must(matchAllQuery())
				.filter(QueryBuilders.termQuery("id", id));

		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryDsl);

		SearchResponse searchResponse = serviceUtility.searchResponseForObject("category", queryDsl);
		return serviceUtility.getObjectResult(searchResponse, new Category());

		/*
		 * //builder.query(termQuery("id", id)); builder.query(queryDsl);
		
		 */
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

		QueryBuilder queryDsl = QueryBuilders.boolQuery().must(matchAllQuery())
				.filter(QueryBuilders.termQuery("id", id));

		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryDsl);

		SearchResponse searchResponse = serviceUtility.searchResponseForObject("uom", queryDsl);
		return serviceUtility.getObjectResult(searchResponse, new UOM());

		/*
		 * SearchSourceBuilder builder = new SearchSourceBuilder();
		 * 
		 * builder.query(termQuery("id", id));
		 * 
		 * SearchRequest searchRequest = new SearchRequest("uom");
		 * 
		 * searchRequest.source(builder); SearchResponse searchResponse = null;
		 * 
		 * try { searchResponse = restHighLevelClient.search(searchRequest,
		 * RequestOptions.DEFAULT); } catch (IOException e) { // TODO Auto-generated
		 * e.printStackTrace(); }
		 * 
		 * return serviceUtility.getObjectResult(searchResponse, new UOM());
		 */
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
	
	private List<ComboLineItem> finAllComboLineItemsByProductId(Long id) {
		QueryBuilder queryDsl = QueryBuilders.boolQuery().must(matchAllQuery())
				.filter(QueryBuilders.termQuery("product.id", id));
		SearchSourceBuilder builder = new SearchSourceBuilder();

		builder.query(queryDsl);

		SearchRequest searchRequest = new SearchRequest("combolineitem");

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		SearchHit[] searchHit = searchResponse.getHits().getHits();

		List<ComboLineItem> comboLineItemList = new ArrayList<>();

		for (SearchHit hit : searchHit) {

			comboLineItemList.add(objectMapper.convertValue(hit.getSourceAsMap(), ComboLineItem.class));
		}

		return comboLineItemList;
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
	
	private List<AuxilaryLineItem> findAllAuxilaryProductsByProductId(Long productId) {
		// builder.query(termQuery("product.id", productId));
		QueryBuilder queryDsl = QueryBuilders.boolQuery().must(matchAllQuery())
				.filter(QueryBuilders.termQuery("product.id", productId));
		SearchSourceBuilder builder = new SearchSourceBuilder();

		SearchRequest searchRequest = new SearchRequest("auxilarylineitem");

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		SearchHit[] searchHit = searchResponse.getHits().getHits();

		List<AuxilaryLineItem> auxilaryLineItemList = new ArrayList<>();

		for (SearchHit hit : searchHit) {

			auxilaryLineItemList.add(objectMapper.convertValue(hit.getSourceAsMap(), AuxilaryLineItem.class));
		}

		return auxilaryLineItemList;
	}

	/**
	 * @param id
	 */
	
	private StockEntry findStockEntryById(Long id) {

		// builder.query(termQuery("id", id));
		QueryBuilder queryDsl = QueryBuilders.boolQuery().must(matchAllQuery())
				.filter(QueryBuilders.termQuery("id", id));

		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryDsl);

		SearchResponse searchResponse = serviceUtility.searchResponseForObject("stockentry", queryDsl);
		return serviceUtility.getObjectResult(searchResponse, new StockEntry());

	

	}

	/**
	 * @param productId
	 */
	
	private Discount findDiscountByProductId(Long productId) {
	//	builder.query(termQuery("id", productId));
		QueryBuilder queryDsl = QueryBuilders.boolQuery().must(matchAllQuery())
				.filter(QueryBuilders.termQuery("id", productId));

		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryDsl);

		SearchResponse searchResponse = serviceUtility.searchResponseForObject("discount", queryDsl);
		return serviceUtility.getObjectResult(searchResponse, new Discount());

		
	
	}

	/**
	 * @param id
	 */
	
	private List<EntryLineItem> findAllEntryLineItemsByStockEntryId(Long id) {
		//builder.query(termQuery("stockentry.id", id));
		
		SearchSourceBuilder builder = new SearchSourceBuilder();



		SearchRequest searchRequest = new SearchRequest("entrylineitem");

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		SearchHit[] searchHit = searchResponse.getHits().getHits();

		List<EntryLineItem> entryLineItemList = new ArrayList<>();

		for (SearchHit hit : searchHit) {

			entryLineItemList.add(objectMapper.convertValue(hit.getSourceAsMap(), EntryLineItem.class));
		}

		return entryLineItemList;

	}

	/**
	 * @param id
	 */
	
	private Reason findReasonByStockEntryId(Long id) {
		//builder.query(termQuery("id", id));
		
		
		QueryBuilder queryDsl = QueryBuilders.boolQuery().must(matchAllQuery())
				.filter(QueryBuilders.termQuery("id", id));

		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryDsl);

		SearchResponse searchResponse = serviceUtility.searchResponseForObject("reason", queryDsl);
		return serviceUtility.getObjectResult(searchResponse, new Reason());

		
	}

	/**
	 * @param id
	 */
	
	private Location findLocationByStockEntryId(Long id) {

		//builder.query(termQuery("id", id));
		
		QueryBuilder queryDsl = QueryBuilders.boolQuery().must(matchAllQuery())
				.filter(QueryBuilders.termQuery("id", id));

		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryDsl);

		SearchResponse searchResponse = serviceUtility.searchResponseForObject("stockentry", queryDsl);
		 StockEntry stockentry = serviceUtility.getObjectResult(searchResponse, new StockEntry());
		 return stockentry.getLocation();
		/*

		StockEntry stockentry = serviceUtility.getObjectResult(searchResponse, new StockEntry());

		return stockentry.getLocation();*/
	}

	/**
	 * @param idpcode
	 */
	@Override
	public Page<Location> findLocationByIdpcode(String idpcode, Pageable pageable) {
		//builder.query(termQuery("iDPcode.keyword", idpcode));
		
		QueryBuilder queryDsl = QueryBuilders.boolQuery().must(matchAllQuery())
				.filter(QueryBuilders.termQuery("iDPcode.keyword", idpcode));
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryDsl);
		SearchResponse searchRespose = serviceUtility.searchResponseForPage("location", builder, pageable);
		return serviceUtility.getPageResult(searchRespose, pageable, new Location());
		
		
		
		
	
	}

	/**
	 * @param idpcode
	 * @param pageable
	 */
	@Override
	public Page<Reason> findReasonByIdpcode(String idpCode, Pageable pageable) {
		//builder.query(termQuery("iDPcode.keyword", idpcode));
		
		QueryBuilder queryDsl = QueryBuilders.boolQuery().must(matchAllQuery())
				.filter(QueryBuilders.termQuery("iDPcode.keyword", idpCode));
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryDsl);
		SearchResponse searchRespose = serviceUtility.searchResponseForPage("reason", builder, pageable);
		return serviceUtility.getPageResult(searchRespose, pageable, new Reason());
		
		
		
	
	}

	/**
	 * @param id
	 * @param pageable
	 */
	@Override
	public Page<EntryLineItem> findAllEntryLineItemsByStockEntryId(String id, Pageable pageable) {

		//builder.query(termQuery("stockentry.id.keyword", id));
		
		QueryBuilder queryDsl = QueryBuilders.boolQuery().must(matchAllQuery())
				.filter(QueryBuilders.termQuery("stockentry.id.keyword", id));
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryDsl);
		SearchResponse searchRespose = serviceUtility.searchResponseForPage("entrylineitem", builder, pageable);
		return serviceUtility.getPageResult(searchRespose, pageable, new EntryLineItem());
		
		
		
		
		
		/*SearchSourceBuilder builder = new SearchSourceBuilder();

	

		SearchRequest searchRequest = serviceUtility.generateSearchRequest("entrylineitem", pageable.getPageSize(),
				pageable.getPageNumber(), builder);

		SearchResponse searchResponse = null;

		try {
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		return serviceUtility.getPageResult(searchResponse, pageable, new EntryLineItem());
*/
	}

	/**
	 * @param id
	 */
	
	private Address findAddressByStockEntryId(Long id) {
		
		
	//	builder.query(termQuery("id", id));
		QueryBuilder queryDsl = QueryBuilders.boolQuery().must(matchAllQuery())
				.filter(QueryBuilders.termQuery("id", id));

		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryDsl);

		SearchResponse searchResponse = serviceUtility.searchResponseForObject("location", queryDsl);
		Location location =  serviceUtility.getObjectResult(searchResponse, new Location());
		 return  location.getAddress();

		/*
		Location location = serviceUtility.getObjectResult(searchResponse, new Location());

		return location.getAddress();*/

	}

	/**
	 * @param storeId
	 * @param name
	 * @param pageable
	 * 
	 */
	@Override
	public Page<Category> findAllCategoriesByNameAndIdpCode(String name, String idpCode, Pageable pageable) {
		QueryBuilder queryDsl = QueryBuilders.boolQuery().must(matchAllQuery())
				.filter(QueryBuilders.matchQuery("name.keyword", name).prefixLength(3))
				.must(QueryBuilders.termQuery("iDPcode.keyword", idpCode));
		
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryDsl);
		SearchResponse searchRespose = serviceUtility.searchResponseForPage("category", builder, pageable);
		return serviceUtility.getPageResult(searchRespose, pageable, new Category());
		
		/*

		builder.query(QueryBuilders.boolQuery().must(matchAllQuery())
				.filter(QueryBuilders.matchQuery("name.keyword", name).prefixLength(3))
				.must(QueryBuilders.termQuery("iDPcode.keyword", idpCode)));

		
*/
	}

	/**
	 * @param iDPcode
	 */
	@Override
	public Page<Category> findAllCategoriesByIdpCode(String idpCode, Pageable pageable) {

		QueryBuilder queryDsl = QueryBuilders.boolQuery().must(matchAllQuery())
				.filter(QueryBuilders.termQuery("iDPcode.keyword", idpCode));
		
		
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		//searchSourceBuilder.query(QueryBuilders.termQuery("iDPcode.keyword", idpCode));
		searchSourceBuilder.query(queryDsl);
		SearchResponse searchResponse = serviceUtility.searchResponseForPage("category", searchSourceBuilder, pageable);

		Page<Category> page = serviceUtility.getPageResult(searchResponse, pageable, new Customer());

		return page;

		

	}

	public Page<CategoryDTO> findAllCategoryDTOsByIdpCode(String idpCode, Pageable pageable) {
		//builder.query(QueryBuilders.termQuery("iDPcode.keyword", iDPcode));
		QueryBuilder queryDsl = QueryBuilders.boolQuery().must(matchAllQuery())
				.filter(QueryBuilders.termQuery("iDPcode.keyword", idpCode));
		
		SearchSourceBuilder builder = new SearchSourceBuilder();

		builder.query(queryDsl);

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
			

			CategoryDTO categoryDTO = categoryMapper.toDto(category);

			list.add(categoryDTO);
		}

		return new PageImpl(list, pageable, searchResponse.getHits().getTotalHits());

	}

	public CategoryDTO findCategoryDTOById(Long id) {
	//	QueryBuilder queryDsl = QueryBuilders.termQuery("id", id);
		QueryBuilder queryDsl = QueryBuilders.boolQuery().must(matchAllQuery())
				.filter(QueryBuilders.termQuery("id", id));
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryDsl);
		SearchResponse searchResponse = serviceUtility.searchResponseForObject("category", queryDsl);
		Category category = serviceUtility.getObjectResult(searchResponse, new Category());
		return categoryMapper.toDto(category);
		// return categoryResourceApi.getCategoryUsingGET(id);
	}

	public ProductDTO findProductDTOById( Long id) {
		QueryBuilder queryDsl = QueryBuilders.boolQuery().must(matchAllQuery())
				.filter(QueryBuilders.termQuery("id", id));
	//	QueryBuilder queryDsl = QueryBuilders.termQuery("id", id);
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryDsl);
		SearchResponse searchResponse = serviceUtility.searchResponseForObject("product", queryDsl);
		Product product = serviceUtility.getObjectResult(searchResponse, new Product());
		return productMapper.toDto(product);
		// return productResourceApi.getProductUsingGET(id);
	}

	public StockEntryDTO findStockEntryDTOById(Long id) {
		QueryBuilder queryDsl = QueryBuilders.boolQuery().must(matchAllQuery())
				.filter(QueryBuilders.termQuery("id", id));
		//QueryBuilder queryDsl = QueryBuilders.termQuery("id", id);
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
		QueryBuilder queryDsl = QueryBuilders.boolQuery().must(matchAllQuery())
				.filter(QueryBuilders.termQuery("id", id));
		//QueryBuilder queryDsl = QueryBuilders.termQuery("id", id);
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryDsl);
		SearchResponse searchResponse = serviceUtility.searchResponseForObject("uom", queryDsl);
		UOM uom = serviceUtility.getObjectResult(searchResponse, new UOM());
		return uomMapper.toDto(uom);
		// return uomResourceApi.getUOMUsingGET(id);
	}

	public AuxilaryLineItemDTO findAuxilaryLineItemById(Long id) {
		QueryBuilder queryDsl = QueryBuilders.boolQuery().must(matchAllQuery())
				.filter(QueryBuilders.termQuery("id", id));
		//QueryBuilder queryDsl = QueryBuilders.termQuery("id", id);
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryDsl);
		SearchResponse searchResponse = serviceUtility.searchResponseForObject("auxilaryLineItem", queryDsl);
		AuxilaryLineItem auxilaryLineItem = serviceUtility.getObjectResult(searchResponse, new AuxilaryLineItem());
		return auxilaryLineItemMapper.toDto(auxilaryLineItem);

		// return auxilaryLineItemResourceApi.getAuxilaryLineItemUsingGET(id);
	}

	public ComboLineItemDTO findCombolineItemById(Long id) {
		QueryBuilder queryDsl = QueryBuilders.boolQuery().must(matchAllQuery())
				.filter(QueryBuilders.termQuery("id", id));
		//QueryBuilder queryDsl = QueryBuilders.termQuery("id", id);
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(queryDsl);
		SearchResponse searchResponse = serviceUtility.searchResponseForObject("comboLineItem", queryDsl);
		ComboLineItem comboLineItem = serviceUtility.getObjectResult(searchResponse, new ComboLineItem());
		return comboLineItemMapper.toDto(comboLineItem);
		// return comboLineItemResourceApi.getComboLineItemUsingGET(id);
	}
	public StockEntryBundle getStockEntryBundleById(Long id) {

		StockEntry stockEntry = findStockEntryById(id);
		List<EntryLineItem> entryLineItems = findAllEntryLineItemsByStockEntryId(stockEntry.getId());
		Reason reason = findReasonByStockEntryId(stockEntry.getId());
		Location location = findLocationByStockEntryId(stockEntry.getId());
		Address address = findAddressByStockEntryId(stockEntry.getId());
		StockEntryBundle stockEntryBundle = new StockEntryBundle();

		stockEntryBundle.setEntryLineItems(entryLineItems);
		stockEntryBundle.setLocation(location);
		stockEntryBundle.setReason(reason);
		stockEntryBundle.setStockEntry(stockEntry);
		stockEntryBundle.getLocation().setAddress(address);
		return stockEntryBundle;
	}
	public ProductBundle getProductBundleById( Long id) {

		Product product = findProductById(id);

		List<ComboLineItem> comboLineItem = finAllComboLineItemsByProductId(product.getId());

		List<AuxilaryLineItem> auxilaryLineItem = findAllAuxilaryProductsByProductId(product.getId());

		Discount discount = findDiscountByProductId(product.getId());

		ProductBundle productBundle = new ProductBundle();

		productBundle.setDiscount(discount);

		productBundle.setComboLineItems(comboLineItem);

		productBundle.setAuxilaryLineItems(auxilaryLineItem);

		productBundle.setProduct(product);

		return productBundle;
	}
	

}
