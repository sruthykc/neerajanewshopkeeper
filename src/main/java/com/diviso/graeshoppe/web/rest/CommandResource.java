package com.diviso.graeshoppe.web.rest;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diviso.graeshoppe.client.aggregators.CustomerAggregator;
import com.diviso.graeshoppe.client.customer.api.ContactResourceApi;
import com.diviso.graeshoppe.client.customer.api.CustomerResourceApi;
import com.diviso.graeshoppe.client.customer.model.ContactDTO;
import com.diviso.graeshoppe.client.customer.model.CustomerDTO;
import com.diviso.graeshoppe.client.order.api.ApprovalDetailsResourceApi;
import com.diviso.graeshoppe.client.order.api.NotificationResourceApi;
import com.diviso.graeshoppe.client.order.api.OrderCommandResourceApi;
import com.diviso.graeshoppe.client.order.model.ApprovalDetailsDTO;
import com.diviso.graeshoppe.client.order.model.NotificationDTO;
import com.diviso.graeshoppe.client.order.model.Order;
import com.diviso.graeshoppe.client.order.model.OrderDTO;
import com.diviso.graeshoppe.client.product.api.AddressResourceApi;
import com.diviso.graeshoppe.client.product.api.AuxilaryLineItemResourceApi;
import com.diviso.graeshoppe.client.product.api.CategoryResourceApi;
import com.diviso.graeshoppe.client.product.api.ComboLineItemResourceApi;
import com.diviso.graeshoppe.client.product.api.DiscountResourceApi;
import com.diviso.graeshoppe.client.product.api.EntryLineItemResourceApi;
import com.diviso.graeshoppe.client.product.api.LocationResourceApi;
import com.diviso.graeshoppe.client.product.api.ProductResourceApi;
import com.diviso.graeshoppe.client.product.api.ReasonResourceApi;
import com.diviso.graeshoppe.client.product.api.StockCurrentResourceApi;
import com.diviso.graeshoppe.client.product.api.StockEntryResourceApi;
import com.diviso.graeshoppe.client.product.api.UomResourceApi;
import com.diviso.graeshoppe.client.product.model.AddressDTO;
import com.diviso.graeshoppe.client.product.model.AuxilaryLineItemDTO;
import com.diviso.graeshoppe.client.product.model.CategoryDTO;
import com.diviso.graeshoppe.client.product.model.ComboLineItemDTO;
import com.diviso.graeshoppe.client.product.model.DiscountDTO;
import com.diviso.graeshoppe.client.product.model.EntryLineItemDTO;
import com.diviso.graeshoppe.client.product.model.LocationDTO;
import com.diviso.graeshoppe.client.product.model.ProductDTO;
import com.diviso.graeshoppe.client.product.model.ReasonDTO;
import com.diviso.graeshoppe.client.product.model.StockCurrentDTO;
import com.diviso.graeshoppe.client.product.model.StockEntryDTO;
import com.diviso.graeshoppe.client.product.model.UOMDTO;
import com.diviso.graeshoppe.client.sale.api.SaleResourceApi;
import com.diviso.graeshoppe.client.sale.api.TicketLineResourceApi;
import com.diviso.graeshoppe.client.sale.model.SaleDTO;
import com.diviso.graeshoppe.client.sale.model.TicketLineDTO;
import com.diviso.graeshoppe.client.store.api.BannerResourceApi;
import com.diviso.graeshoppe.client.store.api.DeliveryInfoResourceApi;
import com.diviso.graeshoppe.client.store.api.ReplyResourceApi;
import com.diviso.graeshoppe.client.store.api.ReviewResourceApi;
import com.diviso.graeshoppe.client.store.api.StoreAddressResourceApi;
import com.diviso.graeshoppe.client.store.api.StoreResourceApi;
import com.diviso.graeshoppe.client.store.api.StoreSettingsResourceApi;
import com.diviso.graeshoppe.client.store.api.StoreTypeResourceApi;
import com.diviso.graeshoppe.client.store.api.TypeResourceApi;
import com.diviso.graeshoppe.client.store.api.UserRatingResourceApi;
import com.diviso.graeshoppe.client.store.model.BannerDTO;
import com.diviso.graeshoppe.client.store.model.DeliveryInfoDTO;
import com.diviso.graeshoppe.client.store.model.ReplyDTO;
import com.diviso.graeshoppe.client.store.model.ReviewDTO;
import com.diviso.graeshoppe.client.store.model.StoreAddressDTO;
import com.diviso.graeshoppe.client.store.model.StoreBundleDTO;
import com.diviso.graeshoppe.client.store.model.StoreDTO;
import com.diviso.graeshoppe.client.store.model.StoreSettingsDTO;
import com.diviso.graeshoppe.client.store.model.StoreTypeDTO;
import com.diviso.graeshoppe.client.store.model.TypeDTO;
import com.diviso.graeshoppe.client.store.model.UserRatingDTO;
import com.diviso.graeshoppe.service.OrderQueryService;
import com.diviso.graeshoppe.service.QueryService;

@RestController
@RequestMapping("/api/command")
public class CommandResource {

	@Autowired
	private UomResourceApi uomResourceApi;

	@Autowired
	private OrderCommandResourceApi orderCommandResourceApi;
	@Autowired
	private CategoryResourceApi categoryResourceApi;
	
	@Autowired 
	private StockEntryResourceApi stockEntryResourceApi;
	
	@Autowired
	private ProductResourceApi productResourceApi;
	@Autowired
	private ContactResourceApi contactResourceApi;
	@Autowired
	private CustomerResourceApi customerResourceApi;
	@Autowired
	private SaleResourceApi saleResourceApi;
	@Autowired
	private StockCurrentResourceApi stockCurrentResourceApi;

	@Autowired
	private StoreResourceApi storeResourceApi;

	@Autowired
	private ReplyResourceApi replyResourceApi;

	@Autowired
	private UserRatingResourceApi userRatingResourceApi;

	@Autowired
	private ReviewResourceApi reviewResourceApi;

	@Autowired
	TicketLineResourceApi ticketLineResourceApi;

	@Autowired
	DeliveryInfoResourceApi deliveryInfoResourceApi;

	@Autowired
	TypeResourceApi typeResourceApi;

	@Autowired
	private NotificationResourceApi notificationResourceApi;
	@Autowired
	AuxilaryLineItemResourceApi auxilaryLineItemResourceApi;

	@Autowired
	ComboLineItemResourceApi comboLineItemResourceApi;

	@Autowired
	BannerResourceApi bannerResourceApi;
	
	@Autowired
	private ApprovalDetailsResourceApi approvalDetailsApi;

	@Autowired
	private StoreAddressResourceApi storeAddressResourceApi;

	@Autowired
	private StoreSettingsResourceApi storeSettingsResourceApi;

	@Autowired
	private StoreTypeResourceApi storeTypeResourceApi;
	
	@Autowired
	private DiscountResourceApi discountResourceApi;
	
    @Autowired
    private EntryLineItemResourceApi entryLineItemResourceApi;
    
    @Autowired
    private ReasonResourceApi reasonResourceApi;
    
    @Autowired
    private LocationResourceApi locationResourceApi;
    
    @Autowired
    AddressResourceApi addressResourceApi;
    
    @Autowired private OrderQueryService orderQueryService;
    
	/*
	 * @Autowired private QueryService queryService;
	 */
	/*
	 * @Autowired LoadControllerApi loadControllerApi;
	 */

	
	
	private final Logger log = LoggerFactory.getLogger(CommandResource.class);

	/**
	 * 
	 * @return entryLineItemResourceApi
	 */
	public EntryLineItemResourceApi getEntryLineItemResourceApi() {
		return entryLineItemResourceApi;
	}

	/**
	 * 
	 * @param entryLineItemResourceApi
	 */
	public void setEntryLineItemResourceApi(EntryLineItemResourceApi entryLineItemResourceApi) {
		this.entryLineItemResourceApi = entryLineItemResourceApi;
	}

	/**
	 * 
	 * @return 
	 */
	public ReasonResourceApi getReasonResourceApi() {
		return reasonResourceApi;
	}

	/**
	 * 
	 * @param reasonResourceApi
	 */
	public void setReasonResourceApi(ReasonResourceApi reasonResourceApi) {
		this.reasonResourceApi = reasonResourceApi;
	}

	/**
	 * 
	 * @return locationResourceApi
	 */
	public LocationResourceApi getLocationResourceApi() {
		return locationResourceApi;
	}

	/**
	 * 
	 * @param locationResourceApi
	 */
	public void setLocationResourceApi(LocationResourceApi locationResourceApi) {
		this.locationResourceApi = locationResourceApi;
	}

	/**
	 * 
	 * @return StockEntryResourceApi
	 */
	public StockEntryResourceApi getStockEntryResourceApi() {
		return stockEntryResourceApi;
	}

	/**
	 * 
	 * @param stockEntryResourceApi
	 */
	public void setStockEntryResourceApi(StockEntryResourceApi stockEntryResourceApi) {
		this.stockEntryResourceApi = stockEntryResourceApi;
	}

	/**
	 * 
	 * @param customerAggregator
	 * @return customer register
	 */
	@PostMapping("/customers/register-customer")
	public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerAggregator customerAggregator) {

		CustomerDTO customerDTO = new CustomerDTO();
		ContactDTO contactDTO = new ContactDTO();
		customerDTO.setName(customerAggregator.getName());
		contactDTO.setMobileNumber(customerAggregator.getMobileNumber());
		ContactDTO resultDTO = contactResourceApi.createContactUsingPOST(contactDTO).getBody();
		customerDTO.setContactId(resultDTO.getId());
		return customerResourceApi.createCustomerUsingPOST(customerDTO);

	}

	/**
	 * 
	 * @param customerDTO
	 * @return update customer
	 */
	@PutMapping("/customers")
	public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO customerDTO) {
		return customerResourceApi.updateCustomerUsingPUT(customerDTO);
	}

	/**
	 * 
	 * @param id 
	 * @description delete customer
	 */
	@DeleteMapping("/customers/{id}")
	public void deleteCustomer(@PathVariable Long id) {
		Long contactid = customerResourceApi.getCustomerUsingGET(id).getBody().getContactId();
		customerResourceApi.deleteCustomerUsingDELETE(id);
		this.deleteContact(contactid);
	}

	/**
	 * 
	 * @param contact
	 * @return create contact
	 */
	@PostMapping("/contacts")
	public ResponseEntity<ContactDTO> createContact(@RequestBody ContactDTO contact) {
		return this.contactResourceApi.createContactUsingPOST(contact);
	}
	
	/**
	 * 
	 * @param contact
	 * @return update contact
	 */
	@PutMapping("/contacts")
	public ResponseEntity<ContactDTO> updateContact(@RequestBody ContactDTO contact) {
		return this.contactResourceApi.updateContactUsingPUT(contact);
	}

	/**.
	 * 
	 * @param id
	 * @return delete
	 */
	@DeleteMapping("/contacts/{id}")
	public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
		return this.contactResourceApi.deleteContactUsingDELETE(id);
	}

	/**
	 * 
	 * @param id
	 * @return input a id 
	 * 
	 * @description deleting store using an id
	 */
	@DeleteMapping("/store-types/{id}")
	public ResponseEntity<Void> deleteStoreType(@PathVariable Long id) {
		return this.storeTypeResourceApi.deleteStoreTypeUsingDELETE(id);
	}

	/**
	 * 
	 * @param categoryDTO
	 * @return update category
	 */
	@PutMapping("/categories")
	public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO) {
		return categoryResourceApi.updateCategoryUsingPUT(categoryDTO);
	}

	/**
	 * 
	 * @param id
	 * 
	 * @description delete category by using id
	 */
	
	@DeleteMapping("/categories/{id}")
	public void deleteCategory(@PathVariable Long id) {
		categoryResourceApi.deleteCategoryUsingDELETE(id);
	}

	/**
	 * 
	 * @param uomDTO
	 * @return create uom
	 */
	@PostMapping("/unit-of-meassurement")
	public ResponseEntity<UOMDTO> createUOM(@RequestBody UOMDTO uomDTO) {
		return uomResourceApi.createUOMUsingPOST(uomDTO);
	}

	/**
	 * 
	 * @param categoryDTO
	 * @return create product category
	 */
	@PostMapping("/productCategory")
	public ResponseEntity<CategoryDTO> createProductCategory(@RequestBody CategoryDTO categoryDTO) {
		return categoryResourceApi.createCategoryUsingPOST(categoryDTO);
	}

	/**
	 * 
	 * @param productDTO
	 * @return create product
	 */
	@PostMapping("/products")
	public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
		return productResourceApi.createProductUsingPOST(productDTO);
	}

	/**
	 * 
	 * @param productDTO
	 * @return update product
	 */
	@PutMapping("/products")
	public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO) {
		return productResourceApi.updateProductUsingPUT(productDTO);
	}

	/**
	 * 
	 * @param id
	 * 
	 * @description delete product using id
	 */
	@DeleteMapping("/products/{id}")
	public void deleteProduct(@PathVariable Long id) {
		productResourceApi.deleteProductUsingDELETE(id);
	}
	/**
	 * 
	 * @param saleDTO
	 * @return create sale
	 */

	@PostMapping("/sales")
	public ResponseEntity<SaleDTO> createSale(@RequestBody SaleDTO saleDTO) {
		saleDTO.date(Instant.now());

		return saleResourceApi.createSaleUsingPOST(saleDTO);
	}

	/**
	 * 
	 * @param saleDTO
	 * @return update sale
	 */
	@PutMapping("/sales")
	public ResponseEntity<SaleDTO> updateSale(@RequestBody SaleDTO saleDTO) {
		return this.saleResourceApi.updateSaleUsingPUT(saleDTO);
	}

	/**
	 * 
	 * @param id
	 * @description delete sale
	 */
	@DeleteMapping("/sales/{id}")
	public void deleteSale(@PathVariable Long id) {
		this.ticketLineResourceApi.findAllTicketLinesBySaleIdUsingGET(id).getBody().forEach(ticket -> {
			this.deleteTicketline(ticket.getId());
		});
		this.saleResourceApi.deleteSaleUsingDELETE(id);
	} 

	/**
	 * 
	 * @param ticketLineDTO
	 * @return ticketline object
	 */
	@PostMapping("/ticket-lines")
	public ResponseEntity<TicketLineDTO> createTickerLine(@RequestBody TicketLineDTO ticketLineDTO) {
		return this.ticketLineResourceApi.createTicketLineUsingPOST(ticketLineDTO);
	}

	/**
	 * 
	 * @param ticketLineDTO
	 * @return update ticketline
	 */
	@PutMapping("/ticket-lines")
	public ResponseEntity<TicketLineDTO> updateTicketLine(@RequestBody TicketLineDTO ticketLineDTO) {
		return ticketLineResourceApi.updateTicketLineUsingPUT(ticketLineDTO);
	}

	/**
	 * 
	 * @param id
	 * @description delete by ticketline using id 
	 */
	@DeleteMapping("/ticket-lines/{id}")
	public void deleteTicketline(@PathVariable Long id) {
		ticketLineResourceApi.deleteTicketLineUsingDELETE(id);
	}

	/**
	 * 
	 * @param uomDTO
	 * @return update uom
	 */
	@PutMapping("/uoms")
	public ResponseEntity<UOMDTO> updateUOM(@RequestBody UOMDTO uomDTO) {
		return uomResourceApi.updateUOMUsingPUT(uomDTO);
	}

	/**
	 * 
	 * @param id
	 */
	@DeleteMapping("/uoms/{id}")
	public void deleteUOM(@PathVariable Long id) {
		uomResourceApi.deleteUOMUsingDELETE(id);
	}

	/**
	 * 
	 * @param stockCurrent
	 * @return create stock current
	 */
	@PostMapping("/stock-currents")
	public ResponseEntity<StockCurrentDTO> createStockCurrent(@RequestBody StockCurrentDTO stockCurrent) {
		return this.stockCurrentResourceApi.createStockCurrentUsingPOST(stockCurrent);
	}

	/**
	 * 
	 * @param StockCurrent
	 * @return update stockCurrent
	 */
	@PutMapping("/stock-currents")
	public ResponseEntity<StockCurrentDTO> updateStockCurrent(@RequestBody StockCurrentDTO StockCurrent) {
		return this.stockCurrentResourceApi.updateStockCurrentUsingPUT(StockCurrent);
	}

	/**
	 * 
	 * @param storeDTO
	 * @return create store
	 */
	@PostMapping("/stores")
	public ResponseEntity<StoreDTO> createStore(@RequestBody StoreDTO storeDTO) {
		return this.storeResourceApi.createStoreUsingPOST(storeDTO);
	}
	
	
//	Order Related Command Resources starts here
	/**
	 * 
	 * @param orderId
	 * 
	 */
	@PostMapping("/markAsDelivered/{orderId}")
	public void markOrderAsDelivered(@PathVariable String orderId) {
		Order order=orderQueryService.findOrderByOrderId(orderId);
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(order.getId());
		orderDTO.setDate(OffsetDateTime.ofInstant(order.getDate(), ZoneId.systemDefault()));
		orderDTO.setOrderId(order.getOrderId());
		orderDTO.setCustomerId(order.getCustomerId());
		orderDTO.setStoreId(order.getStoreId());
		orderDTO.setGrandTotal(order.getGrandTotal());
		orderDTO.setEmail(order.getEmail());
		orderDTO.setDeliveryInfoId(order.getDeliveryInfo().getId());
		if(order.getApprovalDetails()!=null) {
			orderDTO.setApprovalDetailsId(order.getApprovalDetails().getId());
		}
		orderDTO.setPaymentRef(order.getPaymentRef());
		orderDTO.setStatusId(5l);
		orderCommandResourceApi.updateOrderUsingPUT(orderDTO);
	}
	/**
	 * 
	 * @param taskId
	 * @param approvalDetailsDTO
	 * @return 
	 */
	@PostMapping("/acceptOrder/{taskId}")
	public ResponseEntity<com.diviso.graeshoppe.client.order.model.CommandResource> acceptOrder(@PathVariable String taskId,@RequestBody ApprovalDetailsDTO approvalDetailsDTO) {
		return approvalDetailsApi.createApprovalDetailsUsingPOST(taskId, approvalDetailsDTO);
	}

	/**
	 * 
	 * @param orderDTO
	 * @return update order
	 */
	public ResponseEntity<OrderDTO> updateOrder(OrderDTO orderDTO) {
		return orderCommandResourceApi.updateOrderUsingPUT(orderDTO);
	}
	/**
	 * 
	 * @param notificationDTO
	 * @return update notifications
	 */
	@PutMapping("/notifications")
	public ResponseEntity<NotificationDTO> updateNotification(@RequestBody NotificationDTO notificationDTO) {
		return notificationResourceApi.updateNotificationUsingPUT(notificationDTO);
	}
	
//	Order related commands ends here
	
	/**
	 * 
	 * @param storeDTO
	 * @return update store
	 */
	@PutMapping("/stores")
	public ResponseEntity<StoreDTO> updateStore(@RequestBody StoreDTO storeDTO) {
		return this.storeResourceApi.updateStoreUsingPUT(storeDTO);
	}

	/**
	 * 
	 * @param id
	 * @return delete store using id
	 */
	@DeleteMapping("/stores/{id}")
	public ResponseEntity<Void> deleteStore(@PathVariable Long id) {
		return this.storeResourceApi.deleteStoreUsingDELETE(id);
	}

	/**
	 * 
	 * @param replyDTO
	 * @return create reply
	 */
	@PostMapping("/replies")
	public ResponseEntity<ReplyDTO> createReply(@RequestBody ReplyDTO replyDTO) {
		return this.replyResourceApi.createReplyUsingPOST(replyDTO);
	}

	/**
	 * 
	 * @param replyDTO
	 * @return update reply
	 */
	@PutMapping("/replies")
	public ResponseEntity<ReplyDTO> updateReply(@RequestBody ReplyDTO replyDTO) {
		return this.replyResourceApi.updateReplyUsingPUT(replyDTO);
	}

	/**
	 * 
	 * @param id
	 * @return delete reply by using id
	 */
	@DeleteMapping("/replies/{id}")
	public ResponseEntity<Void> deleteReply(@PathVariable Long id) {
		return this.replyResourceApi.deleteReplyUsingDELETE(id);
	}

	/**
	 * 
	 * @param userRatingDTO
	 * @return create userRating by using 
	 */
	@PostMapping("/user-ratings")
	public ResponseEntity<UserRatingDTO> createUserRating(@RequestBody UserRatingDTO userRatingDTO) {
		return this.userRatingResourceApi.createUserRatingUsingPOST(userRatingDTO);
	}

	/**
	 * 
	 * @param userRatingDTO
	 * @return update user rating 
	 */
	@PutMapping("/user-ratings")
	public ResponseEntity<UserRatingDTO> updateUserRating(@RequestBody UserRatingDTO userRatingDTO) {
		return this.userRatingResourceApi.updateUserRatingUsingPUT(userRatingDTO);
	}

	/**
	 * 
	 * @param id
	 * @return delete user rating
	 */
	@DeleteMapping("/user-ratings/{id}")
	public ResponseEntity<Void> deleteUserRating(@PathVariable Long id) {
		return this.userRatingResourceApi.deleteUserRatingUsingDELETE(id);
	}

	/**
	 * 
	 * @param reviewDTO
	 * @return create userRating
	 */
	@PostMapping("/reviews")
	public ResponseEntity<ReviewDTO> createUserRating(@RequestBody ReviewDTO reviewDTO) {
		return this.reviewResourceApi.createReviewUsingPOST(reviewDTO);
	}

	/**
	 * 
	 * @param reviewDTO
	 * @return update user rating 
	 */ 
	@PutMapping("/reviews")
	public ResponseEntity<ReviewDTO> updateUserRating(@RequestBody ReviewDTO reviewDTO) {
		return this.reviewResourceApi.updateReviewUsingPUT(reviewDTO);
	}

	/**
	 * 
	 * @param id
	 * @return delete review by using id
	 */
	@DeleteMapping("/reviews/{id}")
	public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
		return this.reviewResourceApi.deleteReviewUsingDELETE(id);
	}

	/**
	 * 
	 * @param deliveryInfoDTO
	 * @return create deliveryinfo
	 */
	@PostMapping("/delivery-infos")
	public ResponseEntity<DeliveryInfoDTO> createDeliveryInfo(@RequestBody DeliveryInfoDTO deliveryInfoDTO) {
		return this.deliveryInfoResourceApi.createDeliveryInfoUsingPOST(deliveryInfoDTO);
	}

	/**
	 * 
	 * @param deliveryInfoDTO
	 * @return update delivery info
	 */
	@PutMapping("/delivery-infos")
	public ResponseEntity<DeliveryInfoDTO> updateDeliveryInfo(@RequestBody DeliveryInfoDTO deliveryInfoDTO) {
		return this.deliveryInfoResourceApi.updateDeliveryInfoUsingPUT(deliveryInfoDTO);
	}

	/**
	 * 
	 * @param id
	 * @description delete by delivery info by using id
	 */
	@DeleteMapping("/delivery-infos/{id}")
	public void deleteDeliveryInfo(@PathVariable Long id) {
		this.deliveryInfoResourceApi.deleteDeliveryInfoUsingDELETE(id);
	}

	/**
	 * 
	 * @param typeDTO
	 * @return create type 
	 */
	@PostMapping("/types")
	public ResponseEntity<TypeDTO> createType(@RequestBody TypeDTO typeDTO) {
		return this.typeResourceApi.createTypeUsingPOST(typeDTO);
	}

	/**
	 * 
	 * @param typeDTO
	 * @return update type
	 */
	@PutMapping("/types")
	public ResponseEntity<TypeDTO> updateType(@RequestBody TypeDTO typeDTO) {
		return this.typeResourceApi.updateTypeUsingPUT(typeDTO);
	}

	/**
	 * 
	 * @param id
	 * @return delet type
	 */
	@DeleteMapping("/types/{id}")
	public ResponseEntity<Void> deleteType(@PathVariable Long id) {
		return this.typeResourceApi.deleteTypeUsingDELETE(id);
	}

	/*
	 * @PostMapping("/load-products") public void
	 * loadProducts(@RequestParam("file") MultipartFile file) throws IOException
	 * { // upload and save the file then load
	 * log.info("::::::::::::::::::file:::::::::::::::::::::: "+ file);
	 * loadControllerApi.loadUsingPOST(file.getBytes()); }
	 */

	/**
	 * 
	 * @param auxilaryLineItemDTO
	 * @return create auxilarylineitem
	 */
	@PostMapping("/auxilarylineitem")
	public ResponseEntity<AuxilaryLineItemDTO> createAuxilaryLineItem(
			@RequestBody AuxilaryLineItemDTO auxilaryLineItemDTO) {
		return this.auxilaryLineItemResourceApi.createAuxilaryLineItemUsingPOST(auxilaryLineItemDTO);
	}

	/**
	 * 
	 * @param auxilaryLineItemDTO
	 * @return update AuxilaryLineItem 
	 */
	@PutMapping("/auxilarylineitem")
	public ResponseEntity<AuxilaryLineItemDTO> updateAuxilaryLineItem(
			@RequestBody AuxilaryLineItemDTO auxilaryLineItemDTO) {
		return this.auxilaryLineItemResourceApi.updateAuxilaryLineItemUsingPUT(auxilaryLineItemDTO);
	}

	/**
	 * 
	 * @param id
	 * @return delete AuxilaryLineItem
	 */
	@DeleteMapping("/auxilarylineitem/{id}")
	public ResponseEntity<Void> deleteAuxilaryLineIteam(@PathVariable Long id) {
		return this.auxilaryLineItemResourceApi.deleteAuxilaryLineItemUsingDELETE(id);
	}

	/**
	 * 
	 * @param comboLineItemDTO
	 * @return create combo line item 
	 */
	@PostMapping("/combolineitem")
	public ResponseEntity<ComboLineItemDTO> createComboLineItem(@RequestBody ComboLineItemDTO comboLineItemDTO) {
		return this.comboLineItemResourceApi.createComboLineItemUsingPOST(comboLineItemDTO);
	}

	/**
	 * 
	 * @param comboLineItemDTO
	 * @return update combo lne item 
	 */
	@PutMapping("/combolineitem")
	public ResponseEntity<ComboLineItemDTO> updateComboLineItem(@RequestBody ComboLineItemDTO comboLineItemDTO) {
		return this.comboLineItemResourceApi.updateComboLineItemUsingPUT(comboLineItemDTO);
	}

	/**
	 * 
	 * @param id
	 * @return delete combo item 
	 */
	@DeleteMapping("/combolineitem/{id}")
	public ResponseEntity<Void> deleteComboLineItem(@PathVariable Long id) {
		return this.comboLineItemResourceApi.deleteComboLineItemUsingDELETE(id);
	}

	/**
	 * 
	 * @param bannerDTO
	 * @return createbanner
	 */
	@PostMapping("/banner")
	public ResponseEntity<BannerDTO> createBanner(@RequestBody BannerDTO bannerDTO) {
		return this.bannerResourceApi.createBannerUsingPOST(bannerDTO);
	}

	/**
	 * 
	 * @param bannerDTO
	 * @return update banner
	 */
	@PutMapping("/banner")
	public ResponseEntity<BannerDTO> updateBanner(@RequestBody BannerDTO bannerDTO) {
		return this.bannerResourceApi.updateBannerUsingPUT(bannerDTO);
	}

	/**
	 * 
	 * @param id 
	 * @return deletbanner
	 */
	@DeleteMapping("/banner/{id}")
	public ResponseEntity<Void> deleteBanner(@PathVariable Long id) {
		return this.bannerResourceApi.deleteBannerUsingDELETE(id);
	}
	
	/**
	 * 
	 * @param stockEntryDTO
	 * @return create stock entry 
	 */
	@PostMapping("/stock-entry")
	public ResponseEntity<StockEntryDTO> createStockEntry(@RequestBody StockEntryDTO stockEntryDTO) {
		return this.stockEntryResourceApi.createStockEntryUsingPOST(stockEntryDTO);
	}
	
	/**
	 * 
	 * @param stockEntryDTO
	 * @return upadte stock entry
	 */
	@PutMapping("/stock-entry")
	public ResponseEntity<StockEntryDTO> updateStockEntry(@RequestBody StockEntryDTO stockEntryDTO) {
		return this.stockEntryResourceApi.updateStockEntryUsingPUT(stockEntryDTO);
	}

	/**
	 * 
	 * @param id
	 * @return delete stock entry
	 */
	@DeleteMapping("/stock-entry/{id}")
	public ResponseEntity<Void> deleteStockEntry(@PathVariable Long id) {
		return this.stockEntryResourceApi.deleteStockEntryUsingDELETE(id);
	}
	
	/**
	 * 
	 * @param entrylineitemDTO
	 * @return create entry line item
	 */
	@PostMapping("/entryLineItem")
	public ResponseEntity<EntryLineItemDTO> createEntryLineItem(@RequestBody EntryLineItemDTO entrylineitemDTO) {
		
		log.info("////////////////////////////////////////////////////"+entrylineitemDTO);
		return this.entryLineItemResourceApi.createEntryLineItemUsingPOST(entrylineitemDTO);
	}

	/**
	 * 
	 * @param entrylineitemDTO
	 * @return update entry line item
	 */
	@PutMapping("/entryLineItem")
	public ResponseEntity<EntryLineItemDTO> updateEntryLineItem(@RequestBody EntryLineItemDTO entrylineitemDTO) {
		return this.entryLineItemResourceApi.updateEntryLineItemUsingPUT(entrylineitemDTO);
	}

	/**
	 * 
	 * @param id
	 * @return delete entrty line item
	 */
	@DeleteMapping("/entryLineItem/{id}")
	public ResponseEntity<Void> deleteEntryLineItem(@PathVariable Long id) {
		return this.entryLineItemResourceApi.deleteEntryLineItemUsingDELETE(id);
	}
	
	/**
	 * 
	 * @param reasonDTO
	 * @return create reason
	 */
	@PostMapping("/reason")
	public ResponseEntity<ReasonDTO> createReason(@RequestBody ReasonDTO reasonDTO) {
		return this.reasonResourceApi.createReasonUsingPOST(reasonDTO);
	}

	/**
	 * 
	 * @param reasonDTO
	 * @return update reason 
	 */
	@PutMapping("/reason")
	public ResponseEntity<ReasonDTO> updateReason(@RequestBody ReasonDTO reasonDTO) {
		return this.reasonResourceApi.updateReasonUsingPUT(reasonDTO);
	}

	/**
	 * 
	 * @param id
	 * @return delete reason
	 */
	@DeleteMapping("/reason/{id}")
	public ResponseEntity<Void> deleteReason(@PathVariable Long id) {
		return this.reasonResourceApi.deleteReasonUsingDELETE(id);
	}
	
	/**
	 * 
	 * @param locationDTO
	 * @return create location 
	 */
	@PostMapping("/location")
	public ResponseEntity<LocationDTO> createLocation(@RequestBody LocationDTO locationDTO) {
		return this.locationResourceApi.createLocationUsingPOST(locationDTO);
	}

	/**
	 * 
	 * @param locationDTO
	 * @return update location 
	 */
	@PutMapping("/location")
	public ResponseEntity<LocationDTO> updateLocation(@RequestBody LocationDTO locationDTO) {
		return this.locationResourceApi.updateLocationUsingPUT(locationDTO);
	}

	/**
	 * 
	 * @param id
	 * @return delete location 
	 */
	@DeleteMapping("/location/{id}")
	public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
		return this.locationResourceApi.deleteLocationUsingDELETE(id);
	}
	
	/**
	 * 
	 * @param addressDTO
	 * @return create product address
	 */
	@PostMapping("/product/address")
	public ResponseEntity<AddressDTO> createProductAddress(@RequestBody AddressDTO addressDTO) {
		return this.addressResourceApi.createAddressUsingPOST(addressDTO);
	}

	/**
	 * 
	 * @param addressDTO
	 * @return update product address
	 */
	@PutMapping("/product/address")
	public ResponseEntity<AddressDTO> updateProductAddress(@RequestBody AddressDTO addressDTO) {
		return this.addressResourceApi.updateAddressUsingPUT(addressDTO);
	}

	/**
	 * 
	 * @param id
	 * @return delete product address
	 */
	@DeleteMapping("/product/address/{id}")
	public ResponseEntity<Void> deleteProductAddress(@PathVariable Long id) {
		return this.addressResourceApi.deleteAddressUsingDELETE(id);
	}
	
	/**
	 * 
	 * @param discountDTO
	 * @return create discount
	 */
	@PostMapping("/discount")
	public ResponseEntity<DiscountDTO> createDiscount(@RequestBody DiscountDTO discountDTO) {
		return this.discountResourceApi.createDiscountUsingPOST(discountDTO);
	}

	/**
	 * 
	 * @param discountDTO
	 * @return update discount
	 */
	@PutMapping("/discount")
	public ResponseEntity<DiscountDTO> updateDiscount(@RequestBody DiscountDTO discountDTO) {
		return  this.discountResourceApi.updateDiscountUsingPUT(discountDTO);
	}
	
	/**
	 * 
	 * @param id
	 * @return delete discount
	 */
	@DeleteMapping("/discount/{id}")
	public ResponseEntity<Void> deleteDiscount(@PathVariable Long id) {
		return this.discountResourceApi.deleteDiscountUsingDELETE(id);
	}
	
	/**
	 * 
	 * @param storeBundleDTO
	 * @return create store bundle
	 */
	@PostMapping("/storeBundle")
	public ResponseEntity<StoreBundleDTO> createStoreBundle(@RequestBody StoreBundleDTO storeBundleDTO) {

		StoreDTO storeDTO = storeBundleDTO.getStore();

		StoreAddressDTO storeAddressDTO = storeBundleDTO.getStoreAddress();
		if (storeAddressDTO != null) {
			if (storeAddressDTO.getId() == null) {
				storeAddressDTO = storeAddressResourceApi.createStoreAddressUsingPOST(storeAddressDTO).getBody();
			} else {
				storeAddressDTO = storeAddressResourceApi.updateStoreAddressUsingPUT(storeAddressDTO).getBody();
			}
		}

		StoreSettingsDTO storeSettingsDTO = storeBundleDTO.getStoreSettings();
		if (storeSettingsDTO != null) {
			if (storeSettingsDTO.getId() == null) {
				storeSettingsDTO = storeSettingsResourceApi.createStoreSettingsUsingPOST(storeSettingsDTO).getBody();
			} else {
				storeSettingsDTO = storeSettingsResourceApi.updateStoreSettingsUsingPUT(storeSettingsDTO).getBody();
			}
		}

		List<DeliveryInfoDTO> deliveryInfos = storeBundleDTO.getDeliveryInfos();
		List<DeliveryInfoDTO> savedDeliveryInfos = new ArrayList<DeliveryInfoDTO>();

		if (deliveryInfos != null) {

			deliveryInfos.forEach(deliveryInfo -> {

				if (deliveryInfo.getId() == null) {
					savedDeliveryInfos.add(deliveryInfoResourceApi.createDeliveryInfoUsingPOST(deliveryInfo).getBody());
				} else {
					savedDeliveryInfos.add(deliveryInfoResourceApi.updateDeliveryInfoUsingPUT(deliveryInfo).getBody());
				}
			});
		}

		List<TypeDTO> types = storeBundleDTO.getTypes();
		List<TypeDTO> savedTypes = new ArrayList<TypeDTO>();

		if (types != null) {
			types.forEach(type -> {

				if (type.getId() == null) {
					savedTypes.add(typeResourceApi.createTypeUsingPOST(type).getBody());
				} else {
					savedTypes.add(typeResourceApi.updateTypeUsingPUT(type).getBody());
				}
			});
		}

		List<StoreTypeDTO> storeType = storeBundleDTO.getStoreType();
		List<StoreTypeDTO> savedStoreType = new ArrayList<StoreTypeDTO>();

		if (storeType != null) {

			storeType.forEach(storetype -> {
				if (storetype.getId() == null) {

					savedStoreType.add(storeTypeResourceApi.createStoreTypeUsingPOST(storetype).getBody());
				} else {
					savedStoreType.add(storeTypeResourceApi.updateStoreTypeUsingPUT(storetype).getBody());
				}
			});
		}

		List<BannerDTO> banners = storeBundleDTO.getBanners();
		List<BannerDTO> savedBanners = new ArrayList<BannerDTO>();

		if (storeType != null) {
			banners.forEach(banner -> {
				if (banner.getId() == null) {
					savedBanners.add(bannerResourceApi.createBannerUsingPOST(banner).getBody());
				} else {
					savedBanners.add(bannerResourceApi.updateBannerUsingPUT(banner).getBody());
				}
			});
		}

		StoreBundleDTO storeBundle = new StoreBundleDTO();

		storeBundle.setBanners(savedBanners);
		storeBundle.setDeliveryInfos(savedDeliveryInfos);
		storeBundle.setStoreType(savedStoreType);
		storeBundle.setTypes(savedTypes);
		storeBundle.setStore(storeDTO);
		storeBundle.setStoreAddress(storeAddressDTO);
		storeBundle.setStoreSettings(storeSettingsDTO);
		//do this added in if
		storeDTO.setStoreSettingsId(storeSettingsDTO.getId());
		storeDTO.setStoreAddressId(storeAddressDTO.getId());

		if (storeDTO != null) {
			if (storeDTO.getId() == null) {
				storeDTO = storeResourceApi.createStoreUsingPOST(storeDTO).getBody();
			} else {
				storeDTO = storeResourceApi.updateStoreUsingPUT(storeDTO).getBody();
			}
		}

		return ResponseEntity.ok().body(storeBundle);
	}



	
}
