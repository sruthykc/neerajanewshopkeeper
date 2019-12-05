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
import com.diviso.graeshoppe.client.sale.domain.Sale;
import com.diviso.graeshoppe.client.sale.domain.TicketLine;
import com.diviso.graeshoppe.client.sale.model.SaleDTO;
import com.diviso.graeshoppe.client.sale.model.TicketLineDTO;
import com.diviso.graeshoppe.client.store.api.*;
import com.diviso.graeshoppe.client.store.model.*;
import com.diviso.graeshoppe.service.CustomerQueryService;
import com.diviso.graeshoppe.service.OrderQueryService;
import com.diviso.graeshoppe.service.ProductQueryService;
import com.diviso.graeshoppe.service.ReportQueryService;
import com.diviso.graeshoppe.service.SaleQueryService;
import com.diviso.graeshoppe.service.StoreQueryService;
import com.diviso.graeshoppe.service.dto.PdfDTO;
import com.diviso.graeshoppe.service.dto.SaleAggregate;
import com.diviso.graeshoppe.web.rest.util.ServiceUtility;

@RestController
@RequestMapping("/api/query")
public class QueryResource {
	/**
	 * Rafeeq
	 */

	@Autowired
	RestHighLevelClient rhlc;

	@Autowired
	ServiceUtility su;

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
	SaleResourceApi saleResourceApi;

	@Autowired
	UomResourceApi uomResourceApi;

	@Autowired
	CategoryResourceApi categoryResourceApi;

	@Autowired
	CustomerResourceApi customerResourceApi;

	@Autowired
	private ProductResourceApi productResourceApi;

	@Autowired
	private TicketLineResourceApi ticketLineResourceApi;

	@Autowired
	private ContactResourceApi contactResourceApi;

	@Autowired
	private StockCurrentResourceApi stockCurrentResourceApi;

	@Autowired
	private StockEntryResourceApi stockEntryResourceApi;

	@Autowired
	private StoreResourceApi storeResourceApi;

	@Autowired
	private DeliveryInfoResourceApi deliveryInfoResourceApi;

	@Autowired
	private TypeResourceApi typeResourceApi;

	@Autowired
	private StoreTypeResourceApi storeTypeResourceApi;

	@Autowired
	private BannerResourceApi bannerResourceApi;

	@Autowired
	ComboLineItemResourceApi comboLineItemResourceApi;

	@Autowired
	private AuxilaryLineItemResourceApi auxilaryLineItemResourceApi;

	@Autowired
	private StoreAddressResourceApi storeAddressResourceApi;

	@Autowired
	private StoreSettingsResourceApi storeSettingsResourceApi;

	@Autowired
	private OrderQueryResourceApi orderQueryResourceApi;

	@Autowired
	private ReportResourceApi reportResourceApi;

	@Autowired
	OrderMasterResourceApi orderMasterResourceApi;

	@Autowired
	QueryResourceApi queryResourceApi;

	private final Logger log = LoggerFactory.getLogger(QueryResource.class);

	/**
	 * 
	 * @param taskName
	 * @param orderId
	 * @param storeId
	 * @return getTaskDetails
	 * 
	 * @description input a taskName ,orderId and storeId to get a task details
	 */

	@GetMapping("/taskDetails/{taskName}/{orderId}/{storeId}")
	public ResponseEntity<OpenTask> getTaskDetails(@PathVariable String taskName, @PathVariable String orderId,
			@PathVariable String storeId) {
		return orderQueryResourceApi.getTaskDetailsUsingGET(taskName, orderId, storeId);

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

	@GetMapping("/orderStatus/{statusName}/{storeId}/{deliveryType}") // 27 11 19 it's working
	public Page<Order> findOrderByStatusName(@PathVariable String statusName, @PathVariable String storeId,
			@PathVariable String deliveryType, Pageable pageable) {
		return orderQueryService.findOrderByStatusNameAndDeliveryType(statusName, storeId, deliveryType, pageable);
	}

	/**
	 * @author Prince
	 * @param searchTerm
	 * @param storeId
	 * @param pageable
	 * @return page of orders
	 * 
	 * @description getting all products in page as input a name and storeId
	 */

	@GetMapping("/findProductBySearchTerm/{searchTerm}/{storeId}") // 26 11 19 it,s working
	public Page<Product> findAllProductBySearchTerm(@PathVariable String searchTerm, @PathVariable String storeId,
			Pageable pageable) {
		return productQueryService.findAllProductBySearchTerm(searchTerm, storeId, pageable);
	}

	/**
	 * 
	 * @param iDPcode
	 * @param pageable
	 * @return products page
	 * 
	 * @description getting all products in page as input an idpcode
	 */
	@GetMapping("/findAllProducts/{iDPcode}") // 26 11 19 it,s working
	public Page<Product> findAllProducts(@PathVariable String iDPcode, Pageable pageable) {
		return productQueryService.findAllProducts(iDPcode, pageable);
	}

	/**
	 * 
	 * @param id
	 * @return
	 * 
	 * @description find a product as input an id
	 */

	@GetMapping("/products/{id}")
	public ResponseEntity<ProductDTO> findProduct(@PathVariable Long id) {
		return this.productResourceApi.getProductUsingGET(id);
	}

	////////////////////////

	/**
	 * 
	 * @param searchTerm
	 * @param pageable
	 * @return
	 */
	@GetMapping("/findAllCustomersByName/{searchTerm}") // 26 11 19 not working
	public Page<Customer> findAllCustomersByName(@PathVariable String searchTerm, Pageable pageable) {
		log.debug("<<<<<<<< findAllCustomer by search term >>>>>>>>>>", searchTerm);
		return customerQueryService.findAllCustomersByName(searchTerm, pageable);
	}

	@GetMapping("/findAllCustomers") // 26 11 19 not working public
	Page<Customer> findAllCustomers(Pageable pageable) {
		log.debug("<<<<<<<<<< findAllCustomers >>>>>>>>>>");
		return customerQueryService.findAllCustomers(pageable);
	}

	@GetMapping("/customers/{id}")
	public ResponseEntity<CustomerDTO> findCustomerById(@PathVariable Long id) {
		log.debug("<<<<<<<<< findCustomerById >>>>>>>>", id);
		return this.customerResourceApi.getCustomerUsingGET(id);
	}

	@GetMapping("/contacts/{id}")
	public ResponseEntity<ContactDTO> findContactById(@PathVariable Long id) {
		return this.contactResourceApi.getContactUsingGET(id);
	}

	@GetMapping("contact/{mobileNumber}")
	public Page<Contact> findContacts(@PathVariable Long mobileNumber, Pageable page) {
		log.debug("<<<<<<<<<< findContacts >>>>>>>", mobileNumber);

		SearchSourceBuilder ssb = new SearchSourceBuilder();
		ssb.query(termQuery("mobileNumber", mobileNumber));
		SearchRequest sr = su.generateSearchRequest("contact", page.getPageSize(), page.getPageNumber(), ssb);

		SearchResponse searchResponse = null;

		try {
			rhlc.search(sr, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return su.getPageResult(searchResponse, page, new Contact());

	}

	@GetMapping("ordercountBycustomeridandstatus/{customerId}/{statusName}")
	public Long orderCountByCustomerIdAndStatusName(@PathVariable String customerId, @PathVariable String name) {
		log.debug("<<<<<<<<<<< OrderCount >>>>>>>>>>", customerId, name);
		return orderQueryService.orderCountByCustomerIdAndStatusName(customerId, name);

	}

	/**
	 * 
	 * @return
	 * @deprecated
	 */
	@GetMapping("/customers/export")
	public ResponseEntity<PdfDTO> exportCustomers() {
		PdfDTO pdf = new PdfDTO();
		pdf.setPdf(this.customerResourceApi.getPdfAllCustomersUsingGET().getBody());
		pdf.setContentType("application/pdf");
		return ResponseEntity.ok().body(pdf);
	}

	/////////////////////
	/**
	 * 
	 * @param storeId
	 * @param pageable
	 * @return category page
	 * 
	 * @description findAll categories by storeId
	 */
	@GetMapping("/findAllCateogories/{storeId}") // 29 11 19 it's working
	public ResponseEntity<Page<Category>> findAllCategories(@PathVariable String storeId, Pageable pageable) {

		return ResponseEntity.ok().body(productQueryService.findAllCategories(storeId, pageable));

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
	@GetMapping("/findCategoryBySearchTerm/{searchTerm}/{storeId}")
	public Page<Category> findAllCategoryBySearchTermAndStoreId(@PathVariable String searchTerm,
			@PathVariable String storeId, Pageable pageable) {
		log.debug("<<<<<<<<<<<< findAllCategoryBySearchTermAndStoreId >>>>>>>>>>>>", searchTerm, storeId);
		return productQueryService.findAllCategoryBySearchTermAndStoreId(searchTerm, storeId, pageable);
	}

	/**
	 * 
	 * @param iDPcode
	 * @param pageable
	 * @return category list
	 * 
	 * @description findAll categories without image as input a idpcode
	 */
	@GetMapping("/findAllCategoriesWithOutImage/{iDPcode}")
	public ResponseEntity<List<CategoryDTO>> findAllCategoriesWithOutImage(@PathVariable String iDPcode,
			Pageable pageable) {
		return ResponseEntity.ok()
				.body(categoryResourceApi
						.listToDToUsingPOST(productQueryService.findAllCategories(iDPcode, pageable).getContent())
						.getBody().stream().map(c -> {
							c.setImage(null);
							return c;
						}).collect(Collectors.toList()));
	}

	/**
	 * 
	 * @param categoryDTO
	 * @return category object
	 * 
	 * @description update category
	 */
	@PutMapping("/categories")
	public ResponseEntity<CategoryDTO> updateCategory(CategoryDTO categoryDTO) {

		return categoryResourceApi.updateCategoryUsingPUT(categoryDTO);

	}

	/**
	 * 
	 * @param page
	 * @param size
	 * @param sort
	 * @return list ticketline
	 * 
	 * @description getting all details in sort
	 */
	@GetMapping("/ticket-lines")
	public ResponseEntity<List<TicketLineDTO>> findAllTicketlines(Integer page, Integer size, ArrayList<String> sort) {
		return ticketLineResourceApi.getAllTicketLinesUsingGET(page, size, sort);
	}

	/**
	 * 
	 * @param saleId
	 * @return list ticketline
	 * 
	 * @description find ticketlines by saleId
	 */
	@GetMapping("/ticket-lines/{saleId}") // 29 11 19 no data in database
	public ResponseEntity<List<TicketLine>> findAllTicketLinesBySaleId(@PathVariable Long saleId) {
		return ResponseEntity.ok().body(saleQueryService.findTicketLinesBySaleId(saleId));
	}

	/**
	 * 
	 * @param id
	 * @return
	 * 
	 * @description find one ticket line details
	 */
	@GetMapping("/ticket-lines/{id}")
	public ResponseEntity<TicketLineDTO> findOneTicketLines(@PathVariable Long id) {
		return ticketLineResourceApi.getTicketLineUsingGET(id);
	}

	//////////////////////////////////

	/**
	 * 
	 * @param id
	 * @return sale
	 * 
	 * @description find sales by id
	 */
	@GetMapping("/sales/{id}")
	public ResponseEntity<SaleDTO> findSaleById(@PathVariable Long id) {
		return this.saleResourceApi.getSaleUsingGET(id);
	}

	/**
	 * 
	 * @param storeId
	 * @param pageable
	 * @return sale page
	 */
	@GetMapping("/findallsales/{storeId}")
	public Page<Sale> findSales(@PathVariable String storeId, Pageable pageable) {
		log.debug("<<<<<<<<<<< findSales >>>>>>>>", storeId);
		return saleQueryService.findSales(storeId, pageable);
	}

	/**
	 * @author Prince
	 * @param storeId
	 * @param pageable
	 * @return
	 */
	@GetMapping("/sales/combined/{storeId}")
	public ResponseEntity<Page<SaleAggregate>> findAllSaleAggregates(@PathVariable String storeId, Pageable pageable) {
		List<SaleAggregate> sales = new ArrayList<SaleAggregate>();
		this.findSales(storeId, pageable).getContent().forEach(sale -> {
			SaleAggregate saleAgg = new SaleAggregate();
			saleAgg.setSale(sale);
			sales.add(saleAgg);
		});
		sales.forEach(sale -> {
			sale.setCustomer(this.findCustomerById(sale.getSale().getCustomerId()).getBody());
			sale.setTicketLines(this.findAllTicketLinesBySaleId(sale.getSale().getId()).getBody());
		});
		PageImpl<SaleAggregate> res = new PageImpl<SaleAggregate>(sales);
		return ResponseEntity.ok().body(res);
	}

	//////////////////////////

	@GetMapping("/entryLineItem/{storeId}")
	public ResponseEntity<List<EntryLineItem>> findAllEntryLineItems(@PathVariable String storeId, Pageable pageable) {
		log.debug("<<<<<<<<<< findAllEntryLineItems >>>>>>>>>>", storeId);
		return ResponseEntity.ok().body(this.productQueryService.findAllEntryLineItems(storeId, pageable).getContent());
	}

	/**
	 * 
	 * @param storeId
	 * @param pageable
	 * @return stockEntry page
	 * 
	 * @description getting all stock entry as input a storeId
	 */
	@GetMapping("/stock-entries/{storeId}")
	public ResponseEntity<Page<StockEntry>> findAllStockEntries(@PathVariable String storeId, Pageable pageable) {
		log.debug("<<<<<< findAllStockEntries >>>>>>>>>", storeId);
		return ResponseEntity.ok().body(productQueryService.findAllStockEntries(storeId, pageable));
	}

	/**
	 * 
	 * @param id
	 * @return stock entry object
	 * 
	 * @description find one stock entries
	 */
	@GetMapping("/stock-entries/findbyid/{id}")
	public ResponseEntity<StockEntryDTO> findOneStockEntry(@PathVariable Long id) {
		return this.stockEntryResourceApi.getStockEntryUsingGET(id);
	}

	/**
	 * 
	 * @param searchTerm
	 * @param page
	 * @param size
	 * @param sort
	 * @return
	 */
	@GetMapping("/stock-current/{searchTerm}")
	public ResponseEntity<List<StockCurrentDTO>> searchStockCurrents(@PathVariable String searchTerm, Integer page,
			Integer size, ArrayList<String> sort) {
		return this.stockCurrentResourceApi.searchStockCurrentsUsingGET(searchTerm, page, size, sort);
	}

	///////////////////////////////

	@GetMapping("/stores/{regNo}") // it's working
	public Store findStoreByRegNo(@PathVariable String regNo) {
		return this.storeQueryService.findStoreByRegNo(regNo);
	}

	@GetMapping("/storeDTO/{regNo}")
	public StoreDTO findStoreDTOByRegNo(@PathVariable String regNo) {
		Store store = storeQueryService.findStoreByRegNo(regNo);

		return storeResourceApi.getStoreUsingGET(store.getId()).getBody();
	}

	/**
	 * 
	 * @param regNo
	 * @param page
	 * @param size
	 * @param sort
	 * @return
	 */
	@GetMapping("/storeBundle/{regNo}")
	public ResponseEntity<StoreBundleDTO> getStoreBundle(@PathVariable String regNo, Integer page, Integer size,
			ArrayList<String> sort) {

		Store store = storeQueryService.findStoreByRegNo(regNo);

		StoreAddress storeAdrress = store.getStoreAddress();

		StoreSettings storeSettings = store.getStoreSettings();

		StoreDTO storeDTO = new StoreDTO();

		List<DeliveryInfoDTO> deliveryDTOs = new ArrayList<DeliveryInfoDTO>();

		List<TypeDTO> typeDTOs = new ArrayList<TypeDTO>();

		List<StoreTypeDTO> storeTypeDTO = new ArrayList<StoreTypeDTO>();

		List<BannerDTO> bannerDTO = new ArrayList<BannerDTO>();

		if (store != null) {
			storeDTO = storeResourceApi.getStoreUsingGET(store.getId()).getBody();

			deliveryDTOs.addAll(deliveryInfoResourceApi
					.listToDtoUsingPOST1(storeQueryService.findDeliveryInfoByStoreId(storeDTO.getId()).getContent())
					.getBody());

			typeDTOs.addAll(typeResourceApi.listToDtoUsingPOST3(storeQueryService.findAllDeliveryTypesByStoreId(regNo))
					.getBody());

			storeTypeDTO.addAll(storeTypeResourceApi
					.listToDtoUsingPOST2(storeQueryService.findAllStoreTypesByStoreId(regNo)).getBody());

			bannerDTO.addAll(
					bannerResourceApi.listToDtoUsingPOST(storeQueryService.findAllBannersByStoreId(regNo)).getBody());
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

		return ResponseEntity.ok().body(bundle);

	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/productBundle/{id}")
	public ResponseEntity<ProductBundle> getProductBundle(@PathVariable Long id) {

		Product product = productQueryService.findProductById(id);

		List<ComboLineItem> comboLineItem = productQueryService.finAllComboLineItemsByProductId(product.getId());

		List<AuxilaryLineItem> auxilaryLineItem = productQueryService
				.findAllAuxilaryProductsByProductId(product.getId());

		Discount discount = productQueryService.findDiscountByProductId(product.getId());

		ProductBundle productBundle = new ProductBundle();

		productBundle.setDiscount(discount);

		productBundle.setComboLineItems(comboLineItem);

		productBundle.setAuxilaryLineItems(auxilaryLineItem);

		productBundle.setProduct(product);

		return ResponseEntity.ok().body(productBundle);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/stockEntryBundle/{id}")
	public ResponseEntity<StockEntryBundle> getStockEntryBundle(@PathVariable Long id) {

		StockEntry stockEntry = productQueryService.findStockEntryById(id);
		List<EntryLineItem> entryLineItems = productQueryService
				.findAllEntryLineItemsByStockEntryId(stockEntry.getId());
		Reason reason = productQueryService.findReasonByStockEntryId(stockEntry.getId());
		Location location = productQueryService.findLocationByStockEntryId(stockEntry.getId());
		Address address = productQueryService.findAddressByStockEntryId(stockEntry.getId());
		StockEntryBundle stockEntryBundle = new StockEntryBundle();

		stockEntryBundle.setEntryLineItems(entryLineItems);
		stockEntryBundle.setLocation(location);
		stockEntryBundle.setReason(reason);
		stockEntryBundle.setStockEntry(stockEntry);
		stockEntryBundle.getLocation().setAddress(address);
		return ResponseEntity.ok().body(stockEntryBundle);
	}

	/**
	 * 
	 * @param storeId
	 * @param pageable
	 * @return
	 */
	@GetMapping("/ordersbystoreId/{storeId}") // 26 11 19 not working
	public Page<Order> findOrderLineByStoreId(@PathVariable String storeId, Pageable pageable) {
		log.debug("<<<<<<<< findOrderLineByStoreId >>>>>>>>>>", storeId);
		return orderQueryService.findOrderByStoreId(storeId, pageable);

	}

	/**
	 * 
	 * @param iDPcode
	 * @param pageable
	 * @return
	 */
	@GetMapping("/auxilarylineitems/{iDPcode}")
	public ResponseEntity<Page<AuxilaryLineItem>> getAuxilaryLineItemsByStoreId(@PathVariable String iDPcode,
			Pageable pageable) {
		log.debug("<<<<<<<<<<<< getAuxilaryLineItemsByStoreId >>>>>>>>>", iDPcode);
		return ResponseEntity.ok().body(productQueryService.findAuxilaryLineItemsByIDPcode(iDPcode, pageable));

	}

	/**
	 * 
	 * @param iDPcode
	 * @param pageable
	 * @return
	 */
	@GetMapping("/UOM/{iDPcode}")
	public ResponseEntity<Page<UOM>> findUOMByIDPcode(@PathVariable String iDPcode, Pageable pageable) {
		return ResponseEntity.ok().body(productQueryService.findUOMByIDPcode(iDPcode, pageable));
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/uom/{id}")
	public ResponseEntity<UOMDTO> findUOM(@PathVariable Long id) {
		return uomResourceApi.getUOMUsingGET(id);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/category/{id}")
	public ResponseEntity<CategoryDTO> findCategory(@PathVariable Long id) {
		return categoryResourceApi.getCategoryUsingGET(id);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/auxilaryitem/{id}")
	public ResponseEntity<AuxilaryLineItemDTO> findAuxilaryLineItem(@PathVariable Long id) {
		return auxilaryLineItemResourceApi.getAuxilaryLineItemUsingGET(id);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/combolineitem/{id}")
	public ResponseEntity<ComboLineItemDTO> findCombolineItem(@PathVariable Long id) {
		return comboLineItemResourceApi.getComboLineItemUsingGET(id);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/banner/{id}")
	public ResponseEntity<BannerDTO> findBanner(@PathVariable Long id) {
		return bannerResourceApi.getBannerUsingGET(id);
	}

	/**
	 * 
	 * @param iDPcode
	 * @param pageable
	 * @return
	 */
	@GetMapping("/not-aux-combo-products/{iDPcode}") // 26 11 19 it's working
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
	public ResponseEntity<Page<Product>> getAllAuxilaryProduct(@PathVariable String storeId) {
		return ResponseEntity.ok().body(productQueryService.findAllAuxilaryProducts(storeId));

	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/product/{id}") // 26 11 19 it's working
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
	 * @param id
	 * @return
	 */
	@GetMapping("/uombyid/{id}")
	public ResponseEntity<UOM> findUOMById(@PathVariable Long id) {
		return ResponseEntity.ok().body(productQueryService.findUOMById(id));
	}

	/**
	 * 
	 * @param storeId
	 * @return
	 */
	@GetMapping("/store-banners/{storeId}") // 26 11 19 partially working
	public ResponseEntity<Page<Banner>> findBannerByStoreId(@PathVariable String storeId) {
		return ResponseEntity.ok().body(storeQueryService.findBannersByStoreId(storeId));
	}

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
	@GetMapping("/opentasks")
	public ResponseEntity<List<OpenTask>> getOpenTasks(@RequestParam(required = false) String assignee,
			@RequestParam(required = false) String assigneeLike, @RequestParam(required = false) String candidateGroup,
			@RequestParam(required = false) String candidateGroups,
			@RequestParam(required = false) String candidateUser, @RequestParam(required = false) String createdAfter,
			@RequestParam(required = false) String createdBefore, @RequestParam(required = false) String createdOn,
			@RequestParam(required = false) String name, @RequestParam(required = false) String nameLike) {
		return orderQueryResourceApi.getTasksUsingGET(assignee, assigneeLike, candidateGroup, candidateGroups,
				candidateUser, createdAfter, createdBefore, createdOn, name, nameLike);
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
	 * @param orderLineId
	 * @param pageable
	 * @return
	 */
	@GetMapping("/findAuxItemByOrderLineId/{orderLineId}") // 26 11 19 no data in databazse
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
	@GetMapping("/findComboItemByOrderLineId/{orderLineId}") // 26 11 19 no data in databazse
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
	@GetMapping("/orderLineByOrderMasterId/{orderMasterId}")
	public ResponseEntity<Page<OrderLine>> findOrderLineByOrderMasterId(@PathVariable Long orderMasterId,
			Pageable pageable) {
		Page<OrderLine> orderLine = reportQueryService.findOrderLineByOrderMasterId(orderMasterId, pageable);
		return ResponseEntity.ok().body(orderLine);
	}

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
	 * @param orderNumber
	 * @return
	 */
	@GetMapping("/getOrderDocket/{orderNumber}")
	public ResponseEntity<PdfDTO> getOrderDocket(@PathVariable String orderNumber) {
		PdfDTO pdf = new PdfDTO();
		pdf.setPdf(this.reportResourceApi.getReportWithAuxAndComboAsPdfUsingGET(orderNumber).getBody());
		pdf.setContentType("application/pdf");
		return ResponseEntity.ok().body(pdf);
	}

	/**
	 * 
	 * @param orderNumber
	 * @return
	 */
	@GetMapping("/exportDocket/{orderNumber}")
	public ResponseEntity<byte[]> exportOrderDocket(@PathVariable String orderNumber) {
		return reportResourceApi.getReportAsPdfUsingGET(orderNumber);

	}

	/**
	 * 
	 * @param date
	 * @param storeId
	 * @return
	 */
	@GetMapping("/ordersummary/{date}/{storeId}")
	public ResponseEntity<PdfDTO> getOrderSummary(@PathVariable String date, @PathVariable String storeId) {
		PdfDTO pdf = new PdfDTO();
		pdf.setPdf(this.reportResourceApi.getReportSummaryAsPdfUsingGET(date, storeId).getBody());
		pdf.setContentType("application/pdf");
		return ResponseEntity.ok().body(pdf);
	}

	/**
	 * 
	 * @param expectedDelivery
	 * @param storeName
	 * @return
	 */
	@GetMapping("/ordersummaryview/{expectedDelivery}/{storeName}")
	public ResponseEntity<ReportSummary> createReportSummary(@PathVariable String expectedDelivery,
			@PathVariable String storeName) {
		return reportResourceApi.createReportSummaryUsingGET1(expectedDelivery, storeName);
	}

	/**
	 * 
	 * @param receiverId
	 * @param pageable
	 * @return
	 */
	@GetMapping("/notification/{receiverId}") // not working
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
	@GetMapping("/notification/{status}/{receiverId}") // not working
	public Long getNotificationCountByReceiveridAndStatus(@PathVariable String status,
			@PathVariable String receiverId) {

		return orderQueryService.getNotificationCountByReceiveridAndStatus(status, receiverId);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/stock-entry/{id}")
	public ResponseEntity<StockEntryDTO> findStockEntryById(Long id) {

		return stockEntryResourceApi.getStockEntryUsingGET(id);
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

	@GetMapping("/reason/{idpcode}")
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
	@GetMapping("/findallentrylineitems/{id}")
	public Page<EntryLineItem> findAllEntryLineItemsByStockEntryId(@PathVariable String id, Pageable pageable) {

		return productQueryService.findAllEntryLineItemsByStockEntryId(id, pageable);

	}

	/**
	 * 
	 * @param receiverId
	 * @param status
	 * @return
	 * 
	 * @document findnotification count
	 */
	@GetMapping("/findnotificationcount/{receiverId}/{status}") // not working
	Long findNotificationCountByReceiverIdAndStatusName(String receiverId, String status) {
		return orderQueryService.findNotificationCountByReceiverIdAndStatusName(receiverId, status);
	}

	/////////////////////////////

	// Reports

	/////////////////////////////

	/**
	 * 
	 * @param idpcode
	 * @return
	 * 
	 * @document getting allproducts as pdf for input a idpcode
	 */
	@GetMapping("/report/allproducts/{idpcode}")
	public ResponseEntity<PdfDTO> getAllProducts(@PathVariable String idpcode) {
		PdfDTO pdf = new PdfDTO();
		pdf.setPdf(this.productResourceApi.exportProductListAsPdfUsingGET(idpcode).getBody());
		pdf.setContentType("application/pdf");
		return ResponseEntity.ok().body(pdf);
	}

	/**
	 * 
	 * @param idpcode
	 * @return
	 * 
	 * @document getallcategories by idpcode
	 */
	@GetMapping("/report/allcategories/{idpcode}")
	public ResponseEntity<PdfDTO> getAllCategories(@PathVariable String idpcode) {
		PdfDTO pdf = new PdfDTO();
		pdf.setPdf(this.categoryResourceApi.exportCategoryListAsPdfUsingGET(idpcode).getBody());
		pdf.setContentType("application/pdf");
		return ResponseEntity.ok().body(pdf);
	}

	/**
	 * 
	 * @param idpcode
	 * @return pdf details for
	 * 
	 * @document return a details from sockcurrentdetails
	 */
	@GetMapping("/report/currentstock/{idpcode}")
	public ResponseEntity<PdfDTO> getCurrentStock(@PathVariable String idpcode) {
		PdfDTO pdf = new PdfDTO();
		pdf.setPdf(this.stockCurrentResourceApi.exportStockCurrentListAsPdfUsingGET(idpcode).getBody());
		pdf.setContentType("application/pdf");
		return ResponseEntity.ok().body(pdf);
	}

}
