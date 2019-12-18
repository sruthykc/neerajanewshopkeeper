package com.diviso.graeshoppe.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.diviso.graeshoppe.client.order.model.aggregator.OrderLine;

import com.diviso.graeshoppe.client.aggregators.SaleAggregate;
import com.diviso.graeshoppe.client.customer.api.ContactResourceApi;
import com.diviso.graeshoppe.client.customer.api.CustomerResourceApi;
import com.diviso.graeshoppe.client.customer.model.Contact;
import com.diviso.graeshoppe.client.customer.model.ContactDTO;
import com.diviso.graeshoppe.client.customer.model.Customer;
import com.diviso.graeshoppe.client.customer.model.CustomerDTO;
import com.diviso.graeshoppe.client.order.api.OrderQueryResourceApi;
import com.diviso.graeshoppe.client.order.model.Notification;
import com.diviso.graeshoppe.client.order.model.OpenTask;
import com.diviso.graeshoppe.client.order.model.Order;
import com.diviso.graeshoppe.client.order.model.aggregator.OrderLine;

import com.diviso.graeshoppe.client.order.model.aggregator.AuxilaryOrderLine;
import com.diviso.graeshoppe.client.order.model.aggregator.Offer;
import com.diviso.graeshoppe.client.product.api.AuxilaryLineItemResourceApi;
import com.diviso.graeshoppe.client.product.api.CategoryResourceApi;
import com.diviso.graeshoppe.client.product.api.ComboLineItemResourceApi;
import com.diviso.graeshoppe.client.product.api.ProductResourceApi;
import com.diviso.graeshoppe.client.product.api.StockCurrentResourceApi;
import com.diviso.graeshoppe.client.product.api.StockEntryResourceApi;
import com.diviso.graeshoppe.client.product.api.UomResourceApi;
import com.diviso.graeshoppe.client.product.model.*;
import com.diviso.graeshoppe.client.report.api.OrderMasterResourceApi;
import com.diviso.graeshoppe.client.report.api.QueryResourceApi;
import com.diviso.graeshoppe.client.report.api.ReportResourceApi;
import com.diviso.graeshoppe.client.report.model.*;
import com.diviso.graeshoppe.client.sale.api.SaleResourceApi;
import com.diviso.graeshoppe.client.sale.api.TicketLineResourceApi;
import com.diviso.graeshoppe.client.sale.model.Sale;
import com.diviso.graeshoppe.client.sale.model.TicketLine;
import com.diviso.graeshoppe.client.sale.model.SaleDTO;
import com.diviso.graeshoppe.client.sale.model.TicketLineDTO;
import com.diviso.graeshoppe.client.store.api.*;
import com.diviso.graeshoppe.client.store.model.*;

import com.diviso.graeshoppe.service.CustomerQueryService;
import com.diviso.graeshoppe.service.OfferQueryService;
import com.diviso.graeshoppe.service.OrderQueryService;
import com.diviso.graeshoppe.service.ProductQueryService;
import com.diviso.graeshoppe.service.QueryService;
import com.diviso.graeshoppe.service.ReportQueryService;
import com.diviso.graeshoppe.service.SaleQueryService;
import com.diviso.graeshoppe.service.StoreQueryService;
import com.diviso.graeshoppe.service.dto.PdfDTO;

import com.diviso.graeshoppe.web.rest.util.ServiceUtility;

@RestController
@RequestMapping("/api/query")
public class QueryResource {

	@Autowired
	OfferQueryService offerQueryService;

	@Autowired
	OrderQueryService orderQueryService;

	@Autowired
	ProductQueryService productQueryService;

	@Autowired
	CustomerQueryService customerQueryService;

	@Autowired
	StoreQueryService storeQueryService;

	@Autowired
	ReportQueryService reportQueryService;

	@Autowired
	SaleQueryService saleQueryService;

	@Autowired
	private OrderQueryResourceApi orderQueryResourceApi;

	@Autowired
	SaleResourceApi saleResourceApi;

	private final Logger log = LoggerFactory.getLogger(QueryResource.class);

	/**************************
	 * Customer related End Points**********************
	 * 
	 * /**
	 * 
	 * @param name
	 * @param pageable
	 * @return
	 */
	@GetMapping("/findAllCustomersByName/{name}") // 26 11 19 its working
	public Page<Customer> findAllCustomersByName(@PathVariable String name, Pageable pageable) {
		log.debug("<<<<<<<< findAllCustomer by name >>>>>>>>>>", name);
		return customerQueryService.findAllCustomersByName(name, pageable);
	}

	@GetMapping("/findAllCustomers") // 26 11 19 its working
	Page<Customer> findAllCustomers(Pageable pageable) {
		log.debug("<<<<<<<<<< findAllCustomers >>>>>>>>>>");
		return customerQueryService.findAllCustomers(pageable);
	}

	@GetMapping("/findCustomerById/{id}") // 06 12 19 it's working
	public ResponseEntity<CustomerDTO> findCustomerById(@PathVariable Long id) {
		CustomerDTO result = customerQueryService.findCustomerById(id);
		return ResponseEntity.ok().body(result);
		/*
		 * log.debug("<<<<<<<<< findCustomerById >>>>>>>>", id); return
		 * customerQueryService.findCustomerById(id);
		 */
	}

	@GetMapping("/findContactById/{id}") // 6 12 19 it's working
	public ResponseEntity<ContactDTO> findContactById(@PathVariable Long id) {
		ContactDTO result = customerQueryService.findContactById(id);
		return ResponseEntity.ok().body(result);
	}

	/*
	 * @GetMapping("/contact/{mobileNumber}") // 6 12 19 it,s working public
	 * List<Contact> findContacts(@PathVariable Long mobileNumber, Pageable page) {
	 * log.debug("<<<<<<<<<< findContacts >>>>>>>", mobileNumber);
	 * 
	 * return contactService.findContactsByMobileNumber(mobileNumber,page);
	 */
	/*
	 * SearchSourceBuilder ssb = new SearchSourceBuilder();
	 * ssb.query(termQuery("mobileNumber", mobileNumber)); SearchRequest sr =
	 * su.generateSearchRequest("contact", page.getPageSize(), page.getPageNumber(),
	 * ssb);
	 * 
	 * SearchResponse searchResponse = null;
	 * 
	 * try { rhlc.search(sr, RequestOptions.DEFAULT); } catch (IOException e) {
	 * e.printStackTrace(); }
	 * 
	 * return su.getPageResult(searchResponse, page, new Contact());
	 */

	// }

	/**
	 * 
	 * @return
	 * @deprecated
	 */
	@GetMapping("/customers/export") // not working
	public ResponseEntity<PdfDTO> exportCustomers() {
		PdfDTO pdf = new PdfDTO();
		// pdf.setPdf(this.customerResourceApi.getPdfAllCustomersUsingGET().getBody());
		pdf.setContentType("application/pdf");
		return ResponseEntity.ok().body(pdf);
	}

	// ******************************************Product Related end
	// points**********************

	/**
	 * @author Prince
	 * @param searchTerm
	 * @param storeId
	 * @param pageable
	 * @return page of orders
	 * 
	 * @description getting all products in page as input a name and storeId
	 */

	@GetMapping("/findAllProductByNameAndStoreId/{name}/{storeId}") // 26 11 19 it,s working
	public Page<Product> findAllProductByNameAndStoreId(@PathVariable String name, @PathVariable String storeId,
			Pageable pageable) {
		return productQueryService.findAllProductByNameAndStoreId(name, storeId, pageable);
	}

	/**
	 * 
	 * @param iDPcode
	 * @param pageable
	 * @return products page
	 * 
	 * @description getting all products in page as input an idpcode
	 */
	@GetMapping("/findAllProductsByIdpCode/{idpCode}") // 26 11 19 it,s working
	public Page<Product> findAllProductsByIdpCode(@PathVariable String idpCode, Pageable pageable) {
		return productQueryService.findAllProductsByIdpCode(idpCode, pageable);
	}

	/**
	 * 
	 * @param id
	 * @return
	 * 
	 * @description find a product as input an id
	 */

	@GetMapping("/findProductDTOById/{id}") // its working
	public ResponseEntity<ProductDTO> findProductDTOById(@PathVariable Long id) {
		return ResponseEntity.ok().body(productQueryService.findProductDTOById(id));
	}

	/**
	 * 
	 * @param storeId
	 * @param pageable
	 * @return category page
	 * 
	 * @description findAll categories by storeId
	 */
	// findAllCategories
	@GetMapping("/findAllCategoriesByIdpCode/{idpCode}") // 6 12 19 it's working
	public ResponseEntity<Page<Category>> findAllCategoriesByIdpCode(@PathVariable String idpCode, Pageable pageable) {

		return ResponseEntity.ok().body(productQueryService.findAllCategoriesByIdpCode(idpCode, pageable));

	}

	/**
	 * 
	 * @param name
	 * @param storeId
	 * @param pageable
	 * @return category page
	 * 
	 * @description getting all category details as input a name and storeid
	 */
	@GetMapping("/findAllCategoriesByNameAndIdpCode/{name}/{idpCode}") // it's working
	public Page<Category> findAllCategoriesByNameAndIdpCode(@PathVariable String name, @PathVariable String idpCode,
			Pageable pageable) {
		log.debug("<<<<<<<<<<<< findAllCategoryByNameAndIdpCode >>>>>>>>>>>>", name, idpCode);
		return productQueryService.findAllCategoriesByNameAndIdpCode(name, idpCode, pageable);
	}

	/**
	 * 
	 * @param iDPcode
	 * @param pageable
	 * @return category list
	 * 
	 * @description findAll categories without image as input a idpcode
	 */
	// findAllCategorywithoutImage
	@GetMapping("/findAllCategoryDTOsByIdpCode/{idpCode}") // it's working
	public ResponseEntity<Page<CategoryDTO>> findAllCategoryDTOsByIdpCode(@PathVariable String idpCode,
			Pageable pageable) {
		return ResponseEntity.ok().body(productQueryService.findAllCategoryDTOsByIdpCode(idpCode, pageable));
	}

	@GetMapping("/findAllEntryLineItemsByIdpCode/{idpCode}") // no data
	public ResponseEntity<List<EntryLineItem>> findAllEntryLineItemsByIdpCode(@PathVariable String idpCode,
			Pageable pageable) {
		log.debug("<<<<<<<<<< findAllEntryLineItems >>>>>>>>>>", idpCode);
		return ResponseEntity.ok()
				.body(this.productQueryService.findAllEntryLineItemsByIdpCode(idpCode, pageable).getContent());
	}

	/**
	 * 
	 * @param storeId
	 * @param pageable
	 * @return stockEntry page
	 * 
	 * @description getting all stock entry as input a storeId
	 */
	@GetMapping("/findAllStockEntriesByIdpCode/{idpCode}") // no data
	public ResponseEntity<Page<StockEntry>> findAllStockEntriesByIdpcode(@PathVariable String idpCode,
			Pageable pageable) {
		log.debug("<<<<<< findAllStockEntries >>>>>>>>>", idpCode);
		return ResponseEntity.ok().body(productQueryService.findAllStockEntriesbyIdpCode(idpCode, pageable));
	}

	/**
	 * 
	 * @param id
	 * @return stock entry object
	 * 
	 * @description find one stock entries
	 */

	@GetMapping("/findStockEntryById/{id}") // not tested
	public ResponseEntity<StockEntryDTO> findStockEntryById(Long id) {

		return ResponseEntity.ok().body(productQueryService.findStockEntryDTOById(id));
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/getProductBundle/{id}") // its working
	public ResponseEntity<ProductBundle> getProductBundleById(@PathVariable Long id) {

	

		return ResponseEntity.ok().body(productQueryService.getProductBundleById(id));
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/getStockEntryBundleById/{id}") // not tested getStockEntryBundle
	public ResponseEntity<StockEntryBundle> getStockEntryBundleById(@PathVariable Long id) {

	
		return ResponseEntity.ok().body(productQueryService.getStockEntryBundleById(id));
	}

	/**
	 * 
	 * @param iDPcode
	 * @param pageable
	 * @return
	 */
	@GetMapping("/getAuxilaryLineItemsByIdpCode/{idpCode}") // its working
	public ResponseEntity<Page<AuxilaryLineItem>> getAuxilaryLineItemsByIdpCode(@PathVariable String idpCode,
			Pageable pageable) {
		log.debug("<<<<<<<<<<<< getAuxilaryLineItemsByStoreId >>>>>>>>>", idpCode);
		return ResponseEntity.ok().body(productQueryService.findAuxilaryLineItemsByIDPcode(idpCode, pageable));

	}

	/**
	 * 
	 * @param iDPcode
	 * @param pageable
	 * @return
	 */
	@GetMapping("/findUOMByIdpCode/{idpCode}") // 7 12 19 it's working
	public ResponseEntity<Page<UOM>> findUOMByIdpCode(@PathVariable String idpCode, Pageable pageable) {
		return ResponseEntity.ok().body(productQueryService.findUOMByIDPcode(idpCode, pageable));
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/findUOMById/{id}") // its working
	public ResponseEntity<UOMDTO> findUOMDTOById(@PathVariable Long id) {
		return ResponseEntity.ok().body(productQueryService.findUOMDTOById(id));
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/findCategoryDTOById/{id}") // its working findCategory change findCategoryById
	public ResponseEntity<CategoryDTO> findCategoryDTOById(@PathVariable Long id) {
		return ResponseEntity.ok().body(productQueryService.findCategoryDTOById(id));
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/findAuxilaryLineItemById/{id}") // its working
	public ResponseEntity<AuxilaryLineItemDTO> findAuxilaryLineItemById(@PathVariable Long id) {
		return ResponseEntity.ok().body(productQueryService.findAuxilaryLineItemById(id));
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/findCombolineItemById/{id}") // its working
	public ResponseEntity<ComboLineItemDTO> findCombolineItemById(@PathVariable Long id) {
		return ResponseEntity.ok().body(productQueryService.findCombolineItemById(id));
	}

	/**
	 * 
	 * @param iDPcode
	 * @param pageable
	 * @return
	 */
	@GetMapping("/getNotAuxNotComboProductsByIDPcode/{iDPcode}") // 26 11 19 it's working
	public ResponseEntity<Page<Product>> getNotAuxNotComboProductsByIDPcode(@PathVariable String iDPcode,
			Pageable pageable) {

		return ResponseEntity.ok().body(productQueryService.findNotAuxNotComboProductsByIDPcode(iDPcode, pageable));

	}

	/**
	 * 
	 * @param storeId
	 * @return
	 */
	@GetMapping("/auxilary-products/{storeId}") // 26 11 19 it's working
	public ResponseEntity<Page<Product>> getAllAuxilaryProduct(@PathVariable String storeId, Pageable pageable) {
		return ResponseEntity.ok().body(productQueryService.findAllAuxilaryProducts(storeId, pageable));

	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/findProductById/{id}") // 26 11 19 it's working
	public ResponseEntity<Product> findProductById(@PathVariable Long id) {
		return ResponseEntity.ok().body(productQueryService.findProductById(id));
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/categorybyid/{id}") // 26 11 19 it's working
	public ResponseEntity<Category> findCategoryById(@PathVariable Long id) {
		return ResponseEntity.ok().body(productQueryService.findCategoryById(id));
	}

	/**
	 * 
	 * @param idpcode
	 * @param pageable
	 * @return
	 */
	@GetMapping("/location/{idpcode}") // not working
	public Page<Location> findLocationByRegNo(@PathVariable String idpcode, Pageable pageable) {
		return this.productQueryService.findLocationByIdpcode(idpcode, pageable);
	}

	/**
	 * 
	 * @param idpcode
	 * @param pageable
	 * @return
	 * 
	 * @document
	 */

	@GetMapping("/reason/{idpcode}") // not working
	public Page<Reason> findReasonByRegNo(@PathVariable String idpcode, Pageable pageable) {
		return this.productQueryService.findReasonByIdpcode(idpcode, pageable);
	}

	/**
	 * 
	 * @param id
	 * @param pageable
	 * @return
	 * 
	 * @document
	 */
	@GetMapping("/findallentrylineitems/{id}") // no data
	public Page<EntryLineItem> findAllEntryLineItemsByStockEntryId(@PathVariable String id, Pageable pageable) {

		return productQueryService.findAllEntryLineItemsByStockEntryId(id, pageable);

	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/uombyid/{id}") // 26 11 19 it's working
	public ResponseEntity<UOM> findUOMById(@PathVariable Long id) {
		return ResponseEntity.ok().body(productQueryService.findUOMById(id));
	}

	/**
	 * 
	 * @param categoryDTO
	 * @return category object
	 * 
	 * @description update category
	 */
	/*
	 * @PutMapping("/categories") // public ResponseEntity<CategoryDTO>
	 * updateCategory(CategoryDTO categoryDTO) {
	 * 
	 * return categoryResourceApi.updateCategoryUsingPUT(categoryDTO);
	 * 
	 * }
	 */
	// ***********************Sale Related End Points***************************
	/**
	 * 
	 * @param page
	 * @param size
	 * @param sort
	 * @return list ticketline
	 * 
	 * @description getting all details in sort
	 */
	@GetMapping("/ticket-lines") // its working
	public ResponseEntity<List<TicketLineDTO>> findAllTicketlines(Integer page, Integer size, ArrayList<String> sort) {
		return saleQueryService.findAllTicketlines(page, size, sort);
	}

	/**
	 * 
	 * @param saleId
	 * @return list ticketline
	 * 
	 * @description find ticketlines by saleId
	 */
	/*
	 * @GetMapping("/findAllTicketLinesBySaleId/{saleId}") // 29 11 19 its working
	 * public ResponseEntity<List<TicketLine>>
	 * findAllTicketLinesBySaleId(@PathVariable Long saleId, Pageable pageable) {
	 * return
	 * ResponseEntity.ok().body(saleQueryService.findAllTicketLinesBySaleId(saleId,
	 * pageable)); }
	 */
	/**
	 * 
	 * @param id
	 * @return
	 * 
	 * @description find one ticket line details
	 */
	@GetMapping("/findOneTicketLines/{id}") // its working
	public ResponseEntity<TicketLineDTO> findOneTicketLines(@PathVariable Long id) {
		return saleQueryService.findOneTicketLines(id);
	}

	/**
	 * 
	 * @param storeId
	 * @param pageable
	 * @return sale page
	 */
	@GetMapping("/findallsales/{storeId}") // it's working
	public Page<Sale> findSales(@PathVariable String storeId, Pageable pageable) {
		log.debug("<<<<<<<<<<< findSales >>>>>>>>", storeId);
		return saleQueryService.findSales(storeId, pageable);
	}

	// plz refactor
	/**
	 * @author Prince
	 * @param storeId
	 * @param pageable
	 * @return
	 */

	@GetMapping("/findAllSaleAggregates/combined/{storeId}") // it's working public
	ResponseEntity<Page<SaleAggregate>> findAllSaleAggregates(@PathVariable String storeId, Pageable pageable) {
		List<SaleAggregate> sales = new ArrayList<SaleAggregate>();
		this.findSales(storeId, pageable).getContent().forEach(sale -> {
			SaleAggregate saleAgg = new SaleAggregate();
			saleAgg.setSale(sale);
			sales.add(saleAgg);
		});
		sales.forEach(sale -> {
			sale.setCustomer(this.findCustomerById(sale.getSale().getCustomerId()).getBody());
			sale.setTicketLines(saleQueryService.findAllTicketLinesBySaleId(sale.getSale().getId()));
		});
		PageImpl<SaleAggregate> res = new PageImpl<SaleAggregate>(sales);
		return ResponseEntity.ok().body(res);
	}

	@GetMapping("/findSaleById/{id}")
	public ResponseEntity<SaleDTO> findSaleById(@PathVariable Long id) {
		return saleQueryService.findSaleById(id);
	}
	@GetMapping("/printSale/{saleId}/{idpCode}")
	public ResponseEntity<PdfDTO> printSale(@PathVariable Long saleId, @PathVariable String idpCode) {
		PdfDTO pdf = new PdfDTO();
		pdf.setPdf(this.saleResourceApi.printSaleUsingGET(idpCode, saleId).getBody());
		pdf.setContentType("application/pdf");
		return ResponseEntity.ok().body(pdf);

	}

	/**
	 * 
	 * @param searchTerm
	 * @param page
	 * @param size
	 * @param sort
	 * @return
	 */
	/*
	 * @GetMapping("/searchStockCurrents/{searchTerm}") // it's working public
	 * ResponseEntity<List<StockCurrentDTO>> searchStockCurrents(@PathVariable
	 * String searchTerm, Integer page, Integer size, ArrayList<String> sort) {
	 * return productQueryService.searchStockCurrents(searchTerm, page, size, sort);
	 * }
	 */
	// *****************Store related End Points***********************

	@GetMapping("/stores/{regNo}") // 07 12 19 it's working
	public Store findStoreByRegNo(@PathVariable String regNo) {
		return this.storeQueryService.findStoreByRegNo(regNo);
	}

	@GetMapping("/storeDTO/{regNo}") // it's working
	public StoreDTO findStoreDTOByRegNo(@PathVariable String regNo) {

		return storeQueryService.findStoreDTOByRegNo(regNo);
	}

	/**
	 * 
	 * @param regNo
	 * @param page
	 * @param size
	 * @param sort
	 * @return
	 */
	@GetMapping("/getStoreBundle/{regNo}") // its working
	public ResponseEntity<StoreBundleDTO> getStoreBundle(@PathVariable String regNo) {

		return storeQueryService.getStoreBundle(regNo);

	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/banner/{id}") // its working
	public ResponseEntity<BannerDTO> findBanner(@PathVariable Long id) {
		BannerDTO bannerDTO = storeQueryService.findBanner(id);
		return ResponseEntity.ok().body(bannerDTO);
	}

	// ADD Pageable for return page
	/**
	 * 
	 * @param storeId
	 * @return
	 */
	@GetMapping("/store-banners/{storeId}") // 26 11 19 its working
	public ResponseEntity<Page<Banner>> findBannerByStoreId(@PathVariable String storeId, Pageable pageable) {
		return ResponseEntity.ok().body(storeQueryService.findBannersByStoreId(storeId, pageable));
	}

	// *****************************Report related End Points**********************

	/**
	 * @deprecated
	 * @param assignee
	 * @param assigneeLike
	 * @param candidateGroup
	 * @param candidateGroups
	 * @param candidateUser
	 * @param createdAfter
	 * @param createdBefore
	 * @param createdOn
	 * @param name
	 * @param nameLike
	 * @return
	 */
	/*
	 * @GetMapping("/opentasks") public ResponseEntity<List<OpenTask>>
	 * getOpenTasks(@RequestParam(required = false) String assignee,
	 * 
	 * @RequestParam(required = false) String assigneeLike, @RequestParam(required =
	 * false) String candidateGroup,
	 * 
	 * @RequestParam(required = false) String candidateGroups,
	 * 
	 * @RequestParam(required = false) String candidateUser, @RequestParam(required
	 * = false) String createdAfter,
	 * 
	 * @RequestParam(required = false) String createdBefore, @RequestParam(required
	 * = false) String createdOn,
	 * 
	 * @RequestParam(required = false) String name, @RequestParam(required = false)
	 * String nameLike) { return orderQueryResourceApi.getTasksUsingGET(assignee,
	 * assigneeLike, candidateGroup, candidateGroups, candidateUser, createdAfter,
	 * createdBefore, createdOn, name, nameLike); }
	 */

	/**
	 * 
	 * @param orderLineId
	 * @param pageable
	 * @return
	 */
	@GetMapping("/findAuxItemByOrderLineId/{orderLineId}") // it's working
	public ResponseEntity<Page<AuxItem>> findAuxItemByOrderLineId(@PathVariable Long orderLineId, Pageable pageable) {
		Page<AuxItem> auxItem = reportQueryService.findAuxItemByOrderLineId(orderLineId, pageable);
		return ResponseEntity.ok().body(auxItem);
	}

	/**
	 * 
	 * @param orderLineId
	 * @param pageable
	 * @return
	 */
	@GetMapping("/findComboItemByOrderLineId/{orderLineId}") // 26 11 19 no data in databazse Not tested
	public ResponseEntity<Page<ComboItem>> findComboItemByOrderLineId(@PathVariable Long orderLineId,
			Pageable pageable) {
		Page<ComboItem> comboItem = reportQueryService.findComboItemByOrderLineId(orderLineId, pageable);
		return ResponseEntity.ok().body(comboItem);
	}

	/**
	 * 
	 * @param orderId
	 * @return
	 */
	@GetMapping("/orderMasterByOrderId/{orderId}") // 26 11 19 it's working
	public ResponseEntity<OrderMaster> findOrderMasterByOrderId(@PathVariable String orderId) {
		OrderMaster orderMaster = reportQueryService.findOrderMasterByOrderId(orderId);
		return ResponseEntity.ok().body(orderMaster);
	}

	/**
	 * 
	 * @param orderMasterId
	 * @param pageable
	 * @return
	 */
	@GetMapping("/orderLineByOrderMasterId/{orderMasterId}") // not tested no data in database
	public ResponseEntity<Page<OrderLine>> findOrderLineByOrderMasterId(@PathVariable Long orderMasterId,
			Pageable pageable) {
		Page<OrderLine> orderLine = reportQueryService.findOrderLineByOrderMasterId(orderMasterId, pageable);
		return ResponseEntity.ok().body(orderLine);
	}

	/**
	 * 
	 * @param orderNumber
	 * @return
	 */
	@GetMapping("/getOrderDocket/{orderNumber}") // its working
	public ResponseEntity<PdfDTO> getOrderDocket(@PathVariable String orderNumber) {

		return reportQueryService.getOrderDocket(orderNumber);
	}

	/**
	 * 
	 * @param orderNumber
	 * @return
	 */
	@GetMapping("/exportDocket/{orderNumber}") // tested working
	public ResponseEntity<byte[]> exportOrderDocket(@PathVariable String orderNumber) {
		return reportQueryService.exportOrderDocket(orderNumber);

	}

	/**
	 * 
	 * @param date
	 * @param storeId
	 * @return
	 */
	@GetMapping("/ordersummary/{date}/{storeId}") // its working
	public ResponseEntity<PdfDTO> getOrderSummary(@PathVariable String date, @PathVariable String storeId) {
		return reportQueryService.getOrderSummary(date, storeId);
	}

	/**
	 * 
	 * @param expectedDelivery
	 * @param storeName
	 * @return
	 */
	@GetMapping("/ordersummaryview/{expectedDelivery}/{storeName}") // its working
	public ResponseEntity<ReportSummary> createReportSummary(@PathVariable String expectedDelivery,
			@PathVariable String storeName) {
		return reportQueryService.createReportSummary(expectedDelivery, storeName);
	}

	/**
	 * 
	 * @param idpcode
	 * @return
	 * 
	 * @document getting allproducts as pdf for input a idpcode
	 */
	@GetMapping("/report/getAllProductsByIdpCode/{idpcode}") // its working
	public ResponseEntity<PdfDTO> getAllProductsByIdpCode(@PathVariable String idpcode) {
		return reportQueryService.getAllProducts(idpcode);
	}

	/**
	 * 
	 * @param idpcode
	 * @return
	 * 
	 * @document getallcategories by idpcode
	 */
	@GetMapping("/report/getAllCategoriesByIdpCode/{idpcode}") // its working
	public ResponseEntity<PdfDTO> getAllCategoriesByIdpCode(@PathVariable String idpcode) {
		return reportQueryService.getAllCategories(idpcode);
	}

	/**
	 * 
	 * @param idpcode
	 * @return pdf details for
	 * 
	 * @document return a details from sockcurrentdetails
	 */
	@GetMapping("/report/getCurrentStockByIdpCode/{idpcode}") // its working
	public ResponseEntity<PdfDTO> getCurrentStockByIdpCode(@PathVariable String idpcode) {
		return reportQueryService.getCurrentStock(idpcode);

	}
	@GetMapping("/orderaggregator/{orderNumber}")
	public ResponseEntity<com.diviso.graeshoppe.client.report.model.OrderAggregator> getOrderAggregator(@PathVariable String orderNumber) {
		return reportQueryService.getOrderAggregator(orderNumber);
	}


	// ***********************Order related end Points*************************
	/**
	 * 
	 * @param assignee
	 * @param assigneeLike
	 * @param candidateGroup
	 * @param candidateGroups
	 * @param candidateUser
	 * @param createdAfter
	 * @param createdBefore
	 * @param createdOn
	 * @param name
	 * @param nameLike
	 * @return
	 */
	@GetMapping("/tasks")
	public ResponseEntity<List<Order>> getTasks(@RequestParam(required = false) String assignee,
			@RequestParam(required = false) String assigneeLike, @RequestParam(required = false) String candidateGroup,
			@RequestParam(required = false) String candidateGroups,
			@RequestParam(required = false) String candidateUser, @RequestParam(required = false) String createdAfter,
			@RequestParam(required = false) String createdBefore, @RequestParam(required = false) String createdOn,
			@RequestParam(required = false) String name, @RequestParam(required = false) String nameLike) {
		List<OpenTask> openTasks = orderQueryResourceApi.getTasksUsingGET(assignee, assigneeLike, candidateGroup,
				candidateGroups, candidateUser, createdAfter, createdBefore, createdOn, name, nameLike).getBody();

		log.info("...........openTasks...................." + openTasks);
		List<Order> orders = new ArrayList<Order>();

		openTasks.forEach(opentask -> {

			orders.add(orderQueryService.findOrderByOrderId(opentask.getOrderId()));

		});
		return ResponseEntity.ok().body(orders);
	}

	/**
	 * 
	 * @param taskName
	 * @param orderId
	 * @param storeId
	 * @return getTaskDetails
	 * 
	 * @description input a taskName ,orderId and storeId to get a task details
	 */

	@GetMapping("/getTaskDetails/{taskName}/{orderId}/{storeId}")
	public ResponseEntity<OpenTask> getTaskDetails(@PathVariable String taskName, @PathVariable String orderId,
			@PathVariable String storeId) {
		return orderQueryService.getTaskDetails(taskName, orderId, storeId);

	}

	/**
	 * @author Prince
	 * @param statusName
	 * @param storeId
	 * @param deliveryType
	 * @param pageable
	 * @return page of orders
	 * 
	 * @description if you input statusName,storeId and deliveryType to get Orders
	 *              in page
	 */
	// findOrderByStatusName
	@GetMapping("/findOrderByStatusNameAndStoreIdAndDeliveryType/{statusName}/{storeId}/{deliveryType}") // 27 11 19 //
																											// it's //
																											// working
	public Page<Order> findOrderByStatusNameAndStoreIdAndDeliveryType(@PathVariable String statusName,
			@PathVariable String storeId, @PathVariable String deliveryType, Pageable pageable) {
		return orderQueryService.findOrderByStatusNameAndStoreIdAndDeliveryType(statusName, storeId, deliveryType,
				pageable);
	}

	@GetMapping("orderCountByCustomerIdAndStoreId/{customerId}/{storeId}") // 07 12 19 it,s working
	public Long orderCountByCustomerIdAndStoreId(@PathVariable String customerId, @PathVariable String storeId) {
		log.debug("<<<<<<<<<<< OrderCount >>>>>>>>>>", customerId, storeId);
		return orderQueryService.orderCountByCustomerIdAndStoreId(customerId, storeId);

	}

	/**
	 * 
	 * @param orderId
	 * @return
	 */
	@GetMapping("/orderByOrderId/{orderId}") // 26 11 19 it's working
	public ResponseEntity<Order> findOrderByOrderId(@PathVariable String orderId) {
		Order order = orderQueryService.findOrderByOrderId(orderId);
		return ResponseEntity.ok().body(order);
	}

	/**
	 * 
	 * @param storeId
	 * @param pageable
	 * @return
	 */
	@GetMapping("/findOrderLineByStoreId/{storeId}") // 26 11 19 its working
	public Page<Order> findOrderLineByStoreId(@PathVariable String storeId, Pageable pageable) {
		log.debug("<<<<<<<< findOrderLineByStoreId >>>>>>>>>>", storeId);
		return orderQueryService.findOrderByStoreId(storeId, pageable);

	}

	/**
	 * 
	 * @param receiverId
	 * @param pageable
	 * @return
	 */
	@GetMapping("/notification/{receiverId}") //  working
	public ResponseEntity<Page<Notification>> findNotificationByReceiverId(@PathVariable String receiverId,
			Pageable pageable) {
		return ResponseEntity.ok().body(orderQueryService.findNotificationByReceiverId(receiverId, pageable));

	}

	/**
	 * 
	 * @param status
	 * @param receiverId
	 * @return
	 */
	@GetMapping("/notification/{status}/{receiverId}") //  working
	public Long getNotificationCountByReceiveridAndStatus(@PathVariable String status,
			@PathVariable String receiverId) {

		return orderQueryService.getNotificationCountByReceiveridAndStatus(status, receiverId);
	}

	/**
	 * 
	 * @param receiverId
	 * @param status
	 * @return
	 * 
	 * @document findnotification count
	 */
	@GetMapping("/findNotificationCountByReceiverIdAndStatusName/{receiverId}/{status}") // working
	Long findNotificationCountByReceiverIdAndStatusName(String receiverId, String status) {
		return orderQueryService.getNotificationCountByReceiveridAndStatus(receiverId, status);
	}

	

	
	@GetMapping("/findAllOrderLinesByOrderId/{orderId}")
	public Page<OrderLine> findAllOrderLinesByOrderId(@PathVariable Long orderId, Pageable pageable) {
		log.debug("<<<<<<<<<findAllOrderLinesByOrderId >>>>>>>>>>>", orderId);
		Page<OrderLine> page = orderQueryService.findAllOrderLinesByOrderId(orderId, pageable);
		return page;
	}

	

	@GetMapping("/findAuxilaryOrderLineByOrderLineId/{orderLineId}")
	public Page<AuxilaryOrderLine> findAuxilaryOrderLineByOrderLineId(@PathVariable Long orderLineId,
			Pageable pageable) {
		log.debug("<<<<<<<<findAuxilaryOrderLineByOrderLineId >>>>>>>>>>", orderLineId);
		return orderQueryService.findAuxilaryOrderLineByOrderLineId(orderLineId,pageable);
	}
	//***********************Offer related end points**********************
	@GetMapping("/findOfferLinesByOrderId/{orderId}")
	public List<Offer> findOfferLinesByOrderId(@PathVariable Long orderId) {
		return offerQueryService.findOfferLinesByOrderId(orderId);
	}

}
