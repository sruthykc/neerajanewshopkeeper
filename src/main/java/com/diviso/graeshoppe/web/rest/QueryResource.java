package com.diviso.graeshoppe.web.rest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.diviso.graeshoppe.client.customer.domain.Customer;
import com.diviso.graeshoppe.client.customer.model.ContactDTO;
import com.diviso.graeshoppe.client.customer.model.CustomerDTO;
import com.diviso.graeshoppe.client.order.api.OrderQueryResourceApi;
import com.diviso.graeshoppe.client.order.api.ReportQueryResourceApi;
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
import com.diviso.graeshoppe.client.report.api.OrderMasterResourceApi;
import com.diviso.graeshoppe.client.report.api.QueryResourceApi;
import com.diviso.graeshoppe.client.report.api.ReportCommandResourceApi;
import com.diviso.graeshoppe.client.report.api.ReportResourceApi;
import com.diviso.graeshoppe.client.report.model.AuxItem;
import com.diviso.graeshoppe.client.report.model.ComboItem;
import com.diviso.graeshoppe.client.report.model.OrderLine;
import com.diviso.graeshoppe.client.report.model.OrderMaster;
import com.diviso.graeshoppe.client.report.model.OrderMasterDTO;
import com.diviso.graeshoppe.client.report.model.ReportSummary;
import com.diviso.graeshoppe.client.sale.api.SaleResourceApi;
import com.diviso.graeshoppe.client.sale.api.TicketLineResourceApi;
import com.diviso.graeshoppe.client.sale.domain.Sale;
import com.diviso.graeshoppe.client.sale.domain.TicketLine;
import com.diviso.graeshoppe.client.sale.model.SaleDTO;
import com.diviso.graeshoppe.client.sale.model.TicketLineDTO;
import com.diviso.graeshoppe.client.store.api.BannerResourceApi;
import com.diviso.graeshoppe.client.store.api.DeliveryInfoResourceApi;
import com.diviso.graeshoppe.client.store.api.StoreAddressResourceApi;
import com.diviso.graeshoppe.client.store.api.StoreResourceApi;
import com.diviso.graeshoppe.client.store.api.StoreSettingsResourceApi;
import com.diviso.graeshoppe.client.store.api.StoreTypeResourceApi;
import com.diviso.graeshoppe.client.store.api.TypeResourceApi;
import com.diviso.graeshoppe.client.store.domain.Banner;
import com.diviso.graeshoppe.client.store.domain.Store;
import com.diviso.graeshoppe.client.store.domain.StoreAddress;
import com.diviso.graeshoppe.client.store.domain.StoreSettings;
import com.diviso.graeshoppe.client.store.domain.Type;
import com.diviso.graeshoppe.client.store.model.BannerDTO;
import com.diviso.graeshoppe.client.store.model.DeliveryInfoDTO;
import com.diviso.graeshoppe.client.store.model.StoreAddressDTO;
import com.diviso.graeshoppe.client.store.model.StoreBundleDTO;
import com.diviso.graeshoppe.client.store.model.StoreDTO;
import com.diviso.graeshoppe.client.store.model.StoreSettingsDTO;
import com.diviso.graeshoppe.client.store.model.StoreTypeDTO;
import com.diviso.graeshoppe.client.store.model.TypeDTO;
import com.diviso.graeshoppe.service.CustomerQueryService;
import com.diviso.graeshoppe.service.OrderQueryService;
import com.diviso.graeshoppe.service.ProductQueryService;
import com.diviso.graeshoppe.service.QueryService;
import com.diviso.graeshoppe.service.ReportQueryService;
import com.diviso.graeshoppe.service.SaleQueryService;
import com.diviso.graeshoppe.service.StoreQueryService;
import com.diviso.graeshoppe.service.dto.PdfDTO;
import com.diviso.graeshoppe.service.dto.SaleAggregate;

@RestController
@RequestMapping("/api/query")
public class QueryResource {

	@Autowired
	QueryService queryService;

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

	@GetMapping("/taskDetails/{taskName}/{orderId}/{storeId}")
	public ResponseEntity<OpenTask> getTaskDetails(@PathVariable String taskName, @PathVariable String orderId,
			@PathVariable String storeId) {
		return orderQueryResourceApi.getTaskDetailsUsingGET(taskName, orderId, storeId);

	}

	@GetMapping("/orderStatus/{statusName}/{storeId}/{deliveryType}")
	public Page<Order> findOrderByStatusName(@PathVariable String statusName, @PathVariable String storeId,
			@PathVariable String deliveryType, Pageable pageable) {
		return orderQueryService.findOrderByStatusNameAndDeliveryType(statusName, storeId, deliveryType, pageable);
	}

	@GetMapping("/findAllProductByCategoryId/{categoryId}/{storeId}")
	public Page<Product> findAllProductsByCategoryId(@PathVariable Long categoryId, @PathVariable String storeId,
			Pageable pageable) {
		return productQueryService.findProductByCategoryId(categoryId, storeId, pageable);
	}

	@GetMapping("/findProductBySearchTerm/{searchTerm}/{storeId}")
	public Page<Product> findAllProductBySearchTerm(@PathVariable String searchTerm, @PathVariable String storeId,
			Pageable pageable) {
		return productQueryService.findAllProductBySearchTerm(searchTerm, storeId, pageable);
	}

	@GetMapping("/findAllProducts/{iDPcode}")
	public Page<Product> findAllProducts(@PathVariable String iDPcode, Pageable pageable) {
		return productQueryService.findAllProducts(iDPcode, pageable);
	}

	@GetMapping("/products/{id}")
	public ResponseEntity<ProductDTO> findProduct(@PathVariable Long id) {
		return this.productResourceApi.getProductUsingGET(id);
	}

	@GetMapping("/findAllStockCurrentByProductName/{name}/{storeId}")
	public Page<StockCurrent> findAllStockCurrentByProductName(@PathVariable String name, @PathVariable String storeId,
			Pageable pageable) {
		return productQueryService.findStockCurrentByProductName(name, storeId, pageable);
	}

	@GetMapping("/findAllStockCurrentsByCategoryId/{categoryId}/{storeId}")
	public Page<StockCurrent> findAllStockCurrentByCategory(@PathVariable Long categoryId, @PathVariable String storeId,
			Pageable pageable) {
		return productQueryService.findAllStockCurrentByCategoryId(categoryId, storeId, pageable);
	}

	@GetMapping("/findStockCurrentByProductId/{productId}/{storeId}")
	public ResponseEntity<StockCurrent> findStockCurrentByProductId(@PathVariable Long productId,
			@PathVariable String storeId) {
		return ResponseEntity.ok().body(productQueryService.findStockCurrentByProductId(productId, storeId));
	}

	@GetMapping("/findStockCurrentDTOByProductId/{productId}")
	public ResponseEntity<StockCurrentDTO> findStockCurrentDTOByProductId(@PathVariable Long productId) {
		return this.stockCurrentResourceApi.getStockCurrentByProductIdUsingGET(productId);
	}

	@GetMapping("/stockcurrentByIDPcode/{iDPcode}")
	public ResponseEntity<Page<StockCurrent>> getAllStockCurrentsByIDPcode(@PathVariable String iDPcode,
			Pageable pageable) {
		return ResponseEntity.ok().body(productQueryService.findAllStockCurrents(iDPcode, pageable));
	}

	@GetMapping("/stock-currents/{id}")
	public ResponseEntity<StockCurrentDTO> findOneStockCurrent(@PathVariable Long id) {
		return this.stockCurrentResourceApi.getStockCurrentUsingGET(id);
	}

	////////////////////////

	@GetMapping("/findAllCustomer/{searchTerm}")
	public Page<Customer> findAllCustomers(@PathVariable String searchTerm, @PathVariable String storeId,
			Pageable pageable) {
		return customerQueryService.findAllCustomers(searchTerm, pageable);
	}

	@GetMapping("/findAllCustomers")
	public Page<Customer> findAllCustomersWithoutSearch(Pageable pageable) {
		return customerQueryService.findAllCustomersWithoutSearch(pageable);
	}

	@GetMapping("/customers/{id}")
	public ResponseEntity<CustomerDTO> findCustomerById(@PathVariable Long id) {
		return this.customerResourceApi.getCustomerUsingGET(id);
	}

	@GetMapping("/contacts/{id}")
	public ResponseEntity<ContactDTO> findContactById(@PathVariable Long id) {
		return this.contactResourceApi.getContactUsingGET(id);
	}

	@GetMapping("/customers/export")
	public ResponseEntity<PdfDTO> exportCustomers() {
		PdfDTO pdf = new PdfDTO();
		pdf.setPdf(this.customerResourceApi.getPdfAllCustomersUsingGET().getBody());
		pdf.setContentType("application/pdf");
		return ResponseEntity.ok().body(pdf);
	}

	/////////////////////

	@GetMapping("/findAllCateogories/{storeId}")
	public ResponseEntity<Page<Category>> findAllCategories(@PathVariable String storeId, Pageable pageable) {

		return ResponseEntity.ok().body(productQueryService.findAllCategories(storeId, pageable));

	}

	@GetMapping("/findCategoryBySearchTerm/{searchTerm}/{storeId}")
	public Page<Category> findAllCategoryBySearchTerm(@PathVariable String searchTerm, @PathVariable String storeId,
			Pageable pageable) {
		return productQueryService.findAllCategoryBySearchTerm(searchTerm, storeId, pageable);
	}

	@GetMapping("/findAllCategoriesWithOutImage/{iDPcode}")
	public ResponseEntity<List<CategoryDTO>> findAllCategoriesWithOutImage(@PathVariable String iDPcode,
			Pageable pageable) {
		return ResponseEntity.ok()
				.body(categoryResourceApi
						.listToDToUsingPOST(productQueryService.findAllCategories(iDPcode, pageable).getContent()).getBody()
						.stream().map(c -> {
							c.setImage(null);
							return c;
						}).collect(Collectors.toList()));
	}

	@PutMapping("/categories")
	public ResponseEntity<CategoryDTO> updateCategory(CategoryDTO categoryDTO) {

		return categoryResourceApi.updateCategoryUsingPUT(categoryDTO);

	}

	////////////////////////////

	@GetMapping("/findStockEntryByProductId/{productId}/{storeId}")
	public ResponseEntity<StockEntry> findStockEntryByProductId(@PathVariable Long productId,
			@PathVariable String storeId) {
		return ResponseEntity.ok().body(productQueryService.findStockEntryByProductId(productId, storeId));
	}

	///////////////////////////////

	@GetMapping("/ticket-lines")
	public ResponseEntity<List<TicketLineDTO>> findAllTicketlines(Integer page, Integer size, ArrayList<String> sort) {
		return ticketLineResourceApi.getAllTicketLinesUsingGET(page, size, sort);
	}

	@GetMapping("/ticket-lines/{saleId}")
	public ResponseEntity<List<TicketLine>> findAllTicketLinesBySaleId(@PathVariable Long saleId) {
		return ResponseEntity.ok().body(saleQueryService.findTicketLinesBySaleId(saleId));
	}

	@GetMapping("/ticket-lines/{id}")
	public ResponseEntity<TicketLineDTO> findOneTicketLines(@PathVariable Long id) {
		return ticketLineResourceApi.getTicketLineUsingGET(id);
	}

	//////////////////////////////////

	@GetMapping("/sales/{id}")
	public ResponseEntity<SaleDTO> findSaleById(@PathVariable Long id) {
		return this.saleResourceApi.getSaleUsingGET(id);
	}

	@GetMapping("/findallsales/{storeId}")
	public Page<Sale> findSales(@PathVariable String storeId, Pageable pageable) {
		return saleQueryService.findSales(storeId, pageable);
	}

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
		return ResponseEntity.ok().body(this.productQueryService.findAllEntryLineItems(storeId, pageable).getContent());
	}

	@GetMapping("/stock-entries/{storeId}")
	public ResponseEntity<Page<StockEntry>> findAllStockEntries(@PathVariable String storeId, Pageable pageable) {
		return ResponseEntity.ok().body(productQueryService.findAllStockEntries(storeId, pageable));
	}

	@GetMapping("/stock-entries/findbyid/{id}")
	public ResponseEntity<StockEntryDTO> findOneStockEntry(@PathVariable Long id) {
		return this.stockEntryResourceApi.getStockEntryUsingGET(id);
	}

	@GetMapping("/stock-current/{searchTerm}")
	public ResponseEntity<List<StockCurrentDTO>> searchStockCurrents(@PathVariable String searchTerm, Integer page,
			Integer size, ArrayList<String> sort) {
		return this.stockCurrentResourceApi.searchStockCurrentsUsingGET(searchTerm, page, size, sort);
	}

	///////////////////////////////

	@GetMapping("/stores/{regNo}")
	public Store findStoreByRegNo(@PathVariable String regNo) {
		return this.storeQueryService.findStoreByRegNo(regNo);
	}

	@GetMapping("/storeDTO/{regNo}")
	public StoreDTO findStoreDTOByRegNo(@PathVariable String regNo) {
		Store store = storeQueryService.findStoreByRegNo(regNo);

		return storeResourceApi.getStoreUsingGET(store.getId()).getBody();
	}

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
					.listToDtoUsingPOST(storeQueryService.findDeliveryInfoByStoreId(storeDTO.getId()).getContent())
					.getBody());

			typeDTOs.addAll(
					typeResourceApi.listToDtoUsingPOST3(storeQueryService.findAllDeliveryTypesByStoreId(regNo)).getBody());

			storeTypeDTO.addAll(
					storeTypeResourceApi.listToDtoUsingPOST2(storeQueryService.findAllStoreTypesByStoreId(regNo)).getBody());

			bannerDTO.addAll(
					bannerResourceApi.listToDtoUsingPOST(queryService.findAllBannersByStoreId(regNo)).getBody());
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
	
	/*
	 * @PostMapping("/createStore/{regNo}") public ResponseEntity<Createstore>
	 * createStore(Createstore store) {
	 * 
	 * 
	 * 
	 * 
	 * 
	 * }
	 */

	@GetMapping("/productBundle/{id}")
	public ResponseEntity<ProductBundle> getProductBundle(@PathVariable Long id) {

		Product product = productQueryService.findProductById(id);

		List<ComboLineItem> comboLineItem = productQueryService.finAllComboLineItemsByProductId(product.getId());

		List<AuxilaryLineItem> auxilaryLineItem = productQueryService.findAllAuxilaryProductsByProductId(product.getId());

		Discount discount = productQueryService.findDiscountByProductId(product.getId());

		ProductBundle productBundle = new ProductBundle();

		productBundle.setDiscount(discount);

		productBundle.setComboLineItems(comboLineItem);

		productBundle.setAuxilaryLineItems(auxilaryLineItem);

		productBundle.setProduct(product);

		return ResponseEntity.ok().body(productBundle);
	}

	@GetMapping("/stockEntryBundle/{id}")
	public ResponseEntity<StockEntryBundle> getStockEntryBundle(@PathVariable Long id) {

		StockEntry stockEntry = productQueryService.findStockEntryById(id);
		List<EntryLineItem> entryLineItems = productQueryService.findAllEntryLineItemsByStockEntryId(stockEntry.getId());
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

	@GetMapping("/ordersbystoreId/{storeId}")
	public Page<Order> findOrderLineByStoreId(@PathVariable String storeId, Pageable pageable) {

		return orderQueryService.findOrderByStoreId(storeId, pageable);

	}

	@GetMapping("/auxilarylineitems/{iDPcode}")
	public ResponseEntity<Page<AuxilaryLineItem>> getAuxilaryLineItemsByStoreId(@PathVariable String iDPcode,
			Pageable pageable) {

		return ResponseEntity.ok().body(productQueryService.findAuxilaryLineItemsByIDPcode(iDPcode, pageable));

	}

	@GetMapping("/UOM/{iDPcode}")
	public ResponseEntity<Page<UOM>> findUOMByIDPcode(@PathVariable String iDPcode, Pageable pageable) {
		return ResponseEntity.ok().body(productQueryService.findUOMByIDPcode(iDPcode, pageable));
	}

	@GetMapping("/uom/{id}")
	public ResponseEntity<UOMDTO> findUOM(@PathVariable Long id) {
		return uomResourceApi.getUOMUsingGET(id);
	}

	@GetMapping("/store/{id}")
	public ResponseEntity<StoreDTO> findStore(@PathVariable Long id) {
		return storeResourceApi.getStoreUsingGET(id);
	}

	@GetMapping("/category/{id}")
	public ResponseEntity<CategoryDTO> findCategory(@PathVariable Long id) {
		return categoryResourceApi.getCategoryUsingGET(id);
	}

	@GetMapping("/auxilaryitem/{id}")
	public ResponseEntity<AuxilaryLineItemDTO> findAuxilaryLineItem(@PathVariable Long id) {
		return auxilaryLineItemResourceApi.getAuxilaryLineItemUsingGET(id);
	}

	@GetMapping("/combolineitem/{id}")
	public ResponseEntity<ComboLineItemDTO> findCombolineItem(@PathVariable Long id) {
		return comboLineItemResourceApi.getComboLineItemUsingGET(id);
	}

	@GetMapping("/banner/{id}")
	public ResponseEntity<BannerDTO> findBanner(@PathVariable Long id) {
		return bannerResourceApi.getBannerUsingGET(id);
	}

	@GetMapping("/not-aux-combo-products/{iDPcode}")
	public ResponseEntity<Page<Product>> getNotAuxNotComboProductsByIDPcode(@PathVariable String iDPcode,
			Pageable pageable) {

		return ResponseEntity.ok().body(productQueryService.findNotAuxNotComboProductsByIDPcode(iDPcode, pageable));

	}

	@GetMapping("/auxilary-products/{storeId}")
	public ResponseEntity<Page<Product>> getAllAuxilaryProduct(@PathVariable String storeId) {
		return ResponseEntity.ok().body(productQueryService.findAllAuxilaryProducts(storeId));

	}

	@GetMapping("/delivery-Types/{storeId}")
	public List<Type> findAllDeliveryTypesByStoreId(@PathVariable String storeId) {

		return storeQueryService.findAllDeliveryTypesByStoreId(storeId);

	}

	@GetMapping("/product/{id}")
	public ResponseEntity<Product> findProductById(@PathVariable Long id) {
		return ResponseEntity.ok().body(productQueryService.findProductById(id));
	}

	@GetMapping("/categorybyid/{id}")
	public ResponseEntity<Category> findCategoryById(@PathVariable Long id) {
		return ResponseEntity.ok().body(productQueryService.findCategoryById(id));
	}

	@GetMapping("/uombyid/{id}")
	public ResponseEntity<UOM> findUOMById(@PathVariable Long id) {
		return ResponseEntity.ok().body(productQueryService.findUOMById(id));
	}

	@GetMapping("/store-banners/{storeId}")
	public ResponseEntity<Page<Banner>> findBannerByStoreId(@PathVariable String storeId) {
		return ResponseEntity.ok().body(storeQueryService.findBannersByStoreId(storeId));
	}

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

	@GetMapping("/orderByOrderId/{orderId}")
	public ResponseEntity<Order> findOrderByOrderId(@PathVariable String orderId) {
		Order order = orderQueryService.findOrderByOrderId(orderId);
		return ResponseEntity.ok().body(order);
	}

	@GetMapping("/findAuxItemByOrderLineId/{orderLineId}")
	public ResponseEntity<Page<AuxItem>> findAuxItemByOrderLineId(@PathVariable Long orderLineId, Pageable pageable) {
		Page<AuxItem> auxItem = reportQueryService.findAuxItemByOrderLineId(orderLineId, pageable);
		return ResponseEntity.ok().body(auxItem);
	}

	@GetMapping("/findComboItemByOrderLineId/{orderLineId}")
	public ResponseEntity<Page<ComboItem>> findComboItemByOrderLineId(@PathVariable Long orderLineId,
			Pageable pageable) {
		Page<ComboItem> comboItem = reportQueryService.findComboItemByOrderLineId(orderLineId, pageable);
		return ResponseEntity.ok().body(comboItem);
	}

	@GetMapping("/orderMasterByOrderId/{orderId}")
	public ResponseEntity<OrderMaster> findOrderMasterByOrderId(@PathVariable String orderId) {
		OrderMaster orderMaster = queryService.findOrderMasterByOrderId(orderId);
		return ResponseEntity.ok().body(orderMaster);
	}

	@GetMapping("/orderLineByOrderMasterId/{orderMasterId}")
	public ResponseEntity<Page<OrderLine>> findOrderLineByOrderMasterId(@PathVariable Long orderMasterId,
			Pageable pageable) {
		Page<OrderLine> orderLine = reportQueryService.findOrderLineByOrderMasterId(orderMasterId, pageable);
		return ResponseEntity.ok().body(orderLine);
	}

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

	@GetMapping("/getOrderDocket/{orderNumber}")
	public ResponseEntity<PdfDTO> getOrderDocket(@PathVariable String orderNumber) {
		PdfDTO pdf = new PdfDTO();
		pdf.setPdf(this.reportResourceApi.getReportWithAuxAndComboAsPdfUsingGET(orderNumber).getBody());
		pdf.setContentType("application/pdf");
		return ResponseEntity.ok().body(pdf);
	}

	@GetMapping("/exportDocket/{orderNumber}")
	public ResponseEntity<byte[]> exportOrderDocket(@PathVariable String orderNumber) {
		return reportResourceApi.getReportAsPdfUsingGET(orderNumber);

	}

	@GetMapping("/ordersummary/{date}/{storeId}")
	public ResponseEntity<PdfDTO> getOrderSummary(@PathVariable String date, @PathVariable String storeId) {
		PdfDTO pdf = new PdfDTO();
		pdf.setPdf(this.reportResourceApi.getReportSummaryAsPdfUsingGET(date, storeId).getBody());
		pdf.setContentType("application/pdf");
		return ResponseEntity.ok().body(pdf);
	}

	@GetMapping("/ordersummaryview/{expectedDelivery}/{storeName}")
	public ResponseEntity<ReportSummary> createReportSummary(@PathVariable String expectedDelivery,
			@PathVariable String storeName) {
		return reportResourceApi.createReportSummaryUsingGET1(expectedDelivery, storeName);
	}

	@GetMapping("/notification/{receiverId}")
	public ResponseEntity<Page<Notification>> findNotificationByReceiverId(@PathVariable String receiverId,
			Pageable pageable) {
		return ResponseEntity.ok().body(orderQueryService.findNotificationByReceiverId(receiverId, pageable));

	}

	@GetMapping("/notification/{status}/{receiverId}")
	public Long getNotificationCountByReceiveridAndStatus(@PathVariable String status,
			@PathVariable String receiverId) {

		return orderQueryService.getNotificationCountByReceiveridAndStatus(status, receiverId);
	}

	@GetMapping("/stock-entry/{id}")
	public ResponseEntity<StockEntryDTO> findStockEntryById(Long id) {

		return stockEntryResourceApi.getStockEntryUsingGET(id);
	}

	@GetMapping("/location/{idpcode}")
	public Page<Location> findLocationByRegNo(@PathVariable String idpcode, Pageable pageable) {
		return this.productQueryService.findLocationByIdpcode(idpcode, pageable);
	}

	@GetMapping("/reason/{idpcode}")
	public Page<Reason> findReasonByRegNo(@PathVariable String idpcode, Pageable pageable) {
		return this.productQueryService.findReasonByIdpcode(idpcode, pageable);
	}

	@GetMapping("/findallentrylineitems/{id}")
	public Page<EntryLineItem> findAllEntryLineItemsByStockEntryId(@PathVariable String id, Pageable pageable) {

		return productQueryService.findAllEntryLineItemsByStockEntryId(id, pageable);

	}

	@GetMapping("/findnotificationcount/{receiverId}/{status}")
	Long findNotificationCountByReceiverIdAndStatusName(String receiverId, String status) {
		return orderQueryService.findNotificationCountByReceiverIdAndStatusName(receiverId, status);
	}
	
	
	/////////////////////////////
	
	//Reports
	
	/////////////////////////////
	
	
	@GetMapping("/report/allproducts/{idpcode}")
	public ResponseEntity<PdfDTO> getAllProducts(@PathVariable String idpcode) {
		PdfDTO pdf = new PdfDTO();
		pdf.setPdf(this.productResourceApi.exportProductListAsPdfUsingGET(idpcode).getBody());
		pdf.setContentType("application/pdf");
		return ResponseEntity.ok().body(pdf);
	}

	
	@GetMapping("/report/allcategories/{idpcode}")
	public ResponseEntity<PdfDTO> getAllCategories(@PathVariable String idpcode) {
		PdfDTO pdf = new PdfDTO();
		pdf.setPdf(this.categoryResourceApi.exportCategoryListAsPdfUsingGET(idpcode).getBody());
		pdf.setContentType("application/pdf");
		return ResponseEntity.ok().body(pdf);
	}
	
	@GetMapping("/report/currentstock/{idpcode}")
	public ResponseEntity<PdfDTO> getCurrentStock(@PathVariable String idpcode) {
		PdfDTO pdf = new PdfDTO();
		pdf.setPdf(this.stockCurrentResourceApi.exportStockCurrentListAsPdfUsingGET(idpcode).getBody());
		pdf.setContentType("application/pdf");
		return ResponseEntity.ok().body(pdf);
	}


}
