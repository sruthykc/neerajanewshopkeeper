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
import com.diviso.graeshoppe.client.store.api.PreOrderSettingsResourceApi;
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
import com.diviso.graeshoppe.client.store.model.PreOrderSettingsDTO;
import com.diviso.graeshoppe.client.store.model.ReplyDTO;
import com.diviso.graeshoppe.client.store.model.ReviewDTO;
import com.diviso.graeshoppe.client.store.model.StoreAddressDTO;
import com.diviso.graeshoppe.client.store.model.StoreBundleDTO;
import com.diviso.graeshoppe.client.store.model.StoreDTO;
import com.diviso.graeshoppe.client.store.model.StoreSettingsDTO;
import com.diviso.graeshoppe.client.store.model.StoreTypeDTO;
import com.diviso.graeshoppe.client.store.model.TypeDTO;
import com.diviso.graeshoppe.client.store.model.UserRatingDTO;
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
	private NotificationResourceApi notificationResource;
	@Autowired
	private ReplyResourceApi replyResourceApi;
	
	@Autowired
	private PreOrderSettingsResourceApi preOrderSettingsResourceApi;

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
    
    
	@Autowired
	private QueryService queryService;
	/*
	 * @Autowired LoadControllerApi loadControllerApi;
	 */

	
	
	private final Logger log = LoggerFactory.getLogger(CommandResource.class);

	
	public EntryLineItemResourceApi getEntryLineItemResourceApi() {
		return entryLineItemResourceApi;
	}

	public void setEntryLineItemResourceApi(EntryLineItemResourceApi entryLineItemResourceApi) {
		this.entryLineItemResourceApi = entryLineItemResourceApi;
	}

	public ReasonResourceApi getReasonResourceApi() {
		return reasonResourceApi;
	}

	public void setReasonResourceApi(ReasonResourceApi reasonResourceApi) {
		this.reasonResourceApi = reasonResourceApi;
	}

	public LocationResourceApi getLocationResourceApi() {
		return locationResourceApi;
	}

	public void setLocationResourceApi(LocationResourceApi locationResourceApi) {
		this.locationResourceApi = locationResourceApi;
	}

	
	public StockEntryResourceApi getStockEntryResourceApi() {
		return stockEntryResourceApi;
	}

	public void setStockEntryResourceApi(StockEntryResourceApi stockEntryResourceApi) {
		this.stockEntryResourceApi = stockEntryResourceApi;
	}

	
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

	@PutMapping("/customers")
	public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO customerDTO) {
		return customerResourceApi.updateCustomerUsingPUT(customerDTO);
	}

	@DeleteMapping("/customers/{id}")
	public void deleteCustomer(@PathVariable Long id) {
		Long contactid = customerResourceApi.getCustomerUsingGET(id).getBody().getContactId();
		customerResourceApi.deleteCustomerUsingDELETE(id);
		this.deleteContact(contactid);
	}

	@PostMapping("/contacts")
	public ResponseEntity<ContactDTO> createContact(@RequestBody ContactDTO contact) {
		return this.contactResourceApi.createContactUsingPOST(contact);
	}
	
	@PutMapping("/contacts")
	public ResponseEntity<ContactDTO> updateContact(@RequestBody ContactDTO contact) {
		return this.contactResourceApi.updateContactUsingPUT(contact);
	}

	@DeleteMapping("/contacts/{id}")
	public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
		return this.contactResourceApi.deleteContactUsingDELETE(id);
	}

	@DeleteMapping("/store-types/{id}")
	public ResponseEntity<Void> deleteStoreType(@PathVariable Long id) {
		return this.storeTypeResourceApi.deleteStoreTypeUsingDELETE(id);
	}

	@PutMapping("/categories")
	public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO) {
		return categoryResourceApi.updateCategoryUsingPUT(categoryDTO);
	}

	@DeleteMapping("/categories/{id}")
	public void deleteCategory(@PathVariable Long id) {
		categoryResourceApi.deleteCategoryUsingDELETE(id);
	}

	@PostMapping("/unit-of-meassurement")
	public ResponseEntity<UOMDTO> createUOM(@RequestBody UOMDTO uomDTO) {
		return uomResourceApi.createUOMUsingPOST(uomDTO);
	}

	@PostMapping("/productCategory")
	public ResponseEntity<CategoryDTO> createProductCategory(@RequestBody CategoryDTO categoryDTO) {
		return categoryResourceApi.createCategoryUsingPOST(categoryDTO);
	}

	@PostMapping("/products")
	public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
		return productResourceApi.createProductUsingPOST(productDTO);
	}

	@PutMapping("/products")
	public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO) {
		return productResourceApi.updateProductUsingPUT(productDTO);
	}

	@DeleteMapping("/products/{id}")
	public void deleteProduct(@PathVariable Long id) {
		productResourceApi.deleteProductUsingDELETE(id);
	}

	@PostMapping("/sales")
	public ResponseEntity<SaleDTO> createSale(@RequestBody SaleDTO saleDTO) {
		saleDTO.date(Instant.now());

		return saleResourceApi.createSaleUsingPOST(saleDTO);
	}

	@PutMapping("/sales")
	public ResponseEntity<SaleDTO> updateSale(@RequestBody SaleDTO saleDTO) {
		return this.saleResourceApi.updateSaleUsingPUT(saleDTO);
	}

	@DeleteMapping("/sales/{id}")
	public void deleteSale(@PathVariable Long id) {
		this.ticketLineResourceApi.findAllTicketLinesBySaleIdUsingGET(id).getBody().forEach(ticket -> {
			this.deleteTicketline(ticket.getId());
		});
		this.saleResourceApi.deleteSaleUsingDELETE(id);
	}

	@PostMapping("/ticket-lines")
	public ResponseEntity<TicketLineDTO> createTickerLine(@RequestBody TicketLineDTO ticketLineDTO) {
		return this.ticketLineResourceApi.createTicketLineUsingPOST(ticketLineDTO);
	}

	@PutMapping("/ticket-lines")
	public ResponseEntity<TicketLineDTO> updateTicketLine(@RequestBody TicketLineDTO ticketLineDTO) {
		return ticketLineResourceApi.updateTicketLineUsingPUT(ticketLineDTO);
	}

	@DeleteMapping("/ticket-lines/{id}")
	public void deleteTicketline(@PathVariable Long id) {
		ticketLineResourceApi.deleteTicketLineUsingDELETE(id);
	}

	@PutMapping("/uoms")
	public ResponseEntity<UOMDTO> updateUOM(@RequestBody UOMDTO uomDTO) {
		return uomResourceApi.updateUOMUsingPUT(uomDTO);
	}

	@DeleteMapping("/uoms/{id}")
	public void deleteUOM(@PathVariable Long id) {
		uomResourceApi.deleteUOMUsingDELETE(id);
	}

	@PostMapping("/stock-currents")
	public ResponseEntity<StockCurrentDTO> createStockCurrent(@RequestBody StockCurrentDTO stockCurrent) {
		return this.stockCurrentResourceApi.createStockCurrentUsingPOST(stockCurrent);
	}

	@PutMapping("/stock-currents")
	public ResponseEntity<StockCurrentDTO> updateStockCurrent(@RequestBody StockCurrentDTO StockCurrent) {
		return this.stockCurrentResourceApi.updateStockCurrentUsingPUT(StockCurrent);
	}

	@PostMapping("/stores")
	public ResponseEntity<StoreDTO> createStore(@RequestBody StoreDTO storeDTO) {
		return this.storeResourceApi.createStoreUsingPOST(storeDTO);
	}
	
	
//	Order Related Command Resources starts here
	
	@PostMapping("/markAsDelivered/{orderId}")
	public void markOrderAsDelivered(@PathVariable String orderId) {
		Order order=queryService.findOrderByOrderId(orderId);
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(order.getId());
		orderDTO.setDate(OffsetDateTime.ofInstant(order.getDate(), ZoneId.systemDefault()));
		orderDTO.setOrderId(order.getOrderId());
		orderDTO.setCustomerId(order.getCustomerId());
		orderDTO.setStoreId(order.getStoreId());
		orderDTO.setGrandTotal(order.getGrandTotal());
		orderDTO.setEmail(order.getEmail());
		orderDTO.setDeliveryInfoId(order.getDeliveryInfo().getId());
		orderDTO.setSubTotal(order.getSubTotal());
		if(order.getApprovalDetails()!=null) {
			orderDTO.setApprovalDetailsId(order.getApprovalDetails().getId());
		}
		orderDTO.setPaymentRef(order.getPaymentRef());
		orderDTO.setStatusId(5l);
		orderCommandResourceApi.updateOrderUsingPUT(orderDTO);
		NotificationDTO notificationDTO=new NotificationDTO();
		notificationDTO.setTitle("Order Delivered");
		notificationDTO.setMessage("Hi, Your order has been delivered successfuly!");
		notificationDTO.setReceiverId(orderDTO.getCustomerId());
		notificationDTO.setStatus("unread");
		notificationDTO.setDate(OffsetDateTime.now());
		notificationDTO.setTargetId(orderDTO.getOrderId());
		notificationDTO.setType("Order-Delivered");
		notificationResource.createNotificationUsingPOST(notificationDTO);
	}
	
	@PostMapping("/acceptOrder/{taskId}")
	public ResponseEntity<com.diviso.graeshoppe.client.order.model.CommandResource> acceptOrder(@PathVariable String taskId,@RequestBody ApprovalDetailsDTO approvalDetailsDTO) {
		return approvalDetailsApi.createApprovalDetailsUsingPOST(taskId, approvalDetailsDTO);
	}

	public ResponseEntity<OrderDTO> updateOrder(OrderDTO orderDTO) {
		return orderCommandResourceApi.updateOrderUsingPUT(orderDTO);
	}
	
	@PutMapping("/notifications")
	public ResponseEntity<NotificationDTO> updateNotification(@RequestBody NotificationDTO notificationDTO) {
		return notificationResourceApi.updateNotificationUsingPUT(notificationDTO);
	}
	
//	Order related commands ends here
	
	@PutMapping("/stores")
	public ResponseEntity<StoreDTO> updateStore(@RequestBody StoreDTO storeDTO) {
		return this.storeResourceApi.updateStoreUsingPUT(storeDTO);
	}

	@DeleteMapping("/stores/{id}")
	public ResponseEntity<Void> deleteStore(@PathVariable Long id) {
		return this.storeResourceApi.deleteStoreUsingDELETE(id);
	}

	@PostMapping("/replies")
	public ResponseEntity<ReplyDTO> createReply(@RequestBody ReplyDTO replyDTO) {
		return this.replyResourceApi.createReplyUsingPOST(replyDTO);
	}

	@PutMapping("/replies")
	public ResponseEntity<ReplyDTO> updateReply(@RequestBody ReplyDTO replyDTO) {
		return this.replyResourceApi.updateReplyUsingPUT(replyDTO);
	}

	@DeleteMapping("/replies/{id}")
	public ResponseEntity<Void> deleteReply(@PathVariable Long id) {
		return this.replyResourceApi.deleteReplyUsingDELETE(id);
	}

	@PostMapping("/user-ratings")
	public ResponseEntity<UserRatingDTO> createUserRating(@RequestBody UserRatingDTO userRatingDTO) {
		return this.userRatingResourceApi.createUserRatingUsingPOST(userRatingDTO);
	}

	@PutMapping("/user-ratings")
	public ResponseEntity<UserRatingDTO> updateUserRating(@RequestBody UserRatingDTO userRatingDTO) {
		return this.userRatingResourceApi.updateUserRatingUsingPUT(userRatingDTO);
	}

	@DeleteMapping("/user-ratings/{id}")
	public ResponseEntity<Void> deleteUserRating(@PathVariable Long id) {
		return this.userRatingResourceApi.deleteUserRatingUsingDELETE(id);
	}

	@PostMapping("/reviews")
	public ResponseEntity<ReviewDTO> createUserRating(@RequestBody ReviewDTO reviewDTO) {
		return this.reviewResourceApi.createReviewUsingPOST(reviewDTO);
	}

	@PutMapping("/reviews")
	public ResponseEntity<ReviewDTO> updateUserRating(@RequestBody ReviewDTO reviewDTO) {
		return this.reviewResourceApi.updateReviewUsingPUT(reviewDTO);
	}

	@DeleteMapping("/reviews/{id}")
	public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
		return this.reviewResourceApi.deleteReviewUsingDELETE(id);
	}

	@PostMapping("/delivery-infos")
	public ResponseEntity<DeliveryInfoDTO> createDeliveryInfo(@RequestBody DeliveryInfoDTO deliveryInfoDTO) {
		return this.deliveryInfoResourceApi.createDeliveryInfoUsingPOST(deliveryInfoDTO);
	}

	@PutMapping("/delivery-infos")
	public ResponseEntity<DeliveryInfoDTO> updateDeliveryInfo(@RequestBody DeliveryInfoDTO deliveryInfoDTO) {
		return this.deliveryInfoResourceApi.updateDeliveryInfoUsingPUT(deliveryInfoDTO);
	}

	@DeleteMapping("/delivery-infos/{id}")
	public void deleteDeliveryInfo(@PathVariable Long id) {
		this.deliveryInfoResourceApi.deleteDeliveryInfoUsingDELETE(id);
	}

	@PostMapping("/types")
	public ResponseEntity<TypeDTO> createType(@RequestBody TypeDTO typeDTO) {
		return this.typeResourceApi.createTypeUsingPOST(typeDTO);
	}

	@PutMapping("/types")
	public ResponseEntity<TypeDTO> updateType(@RequestBody TypeDTO typeDTO) {
		return this.typeResourceApi.updateTypeUsingPUT(typeDTO);
	}

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

	@PostMapping("/auxilarylineitem")
	public ResponseEntity<AuxilaryLineItemDTO> createAuxilaryLineItem(
			@RequestBody AuxilaryLineItemDTO auxilaryLineItemDTO) {
		return this.auxilaryLineItemResourceApi.createAuxilaryLineItemUsingPOST(auxilaryLineItemDTO);
	}

	@PutMapping("/auxilarylineitem")
	public ResponseEntity<AuxilaryLineItemDTO> updateAuxilaryLineItem(
			@RequestBody AuxilaryLineItemDTO auxilaryLineItemDTO) {
		return this.auxilaryLineItemResourceApi.updateAuxilaryLineItemUsingPUT(auxilaryLineItemDTO);
	}

	@DeleteMapping("/auxilarylineitem/{id}")
	public ResponseEntity<Void> deleteAuxilaryLineIteam(@PathVariable Long id) {
		return this.auxilaryLineItemResourceApi.deleteAuxilaryLineItemUsingDELETE(id);
	}

	@PostMapping("/combolineitem")
	public ResponseEntity<ComboLineItemDTO> createComboLineItem(@RequestBody ComboLineItemDTO comboLineItemDTO) {
		return this.comboLineItemResourceApi.createComboLineItemUsingPOST(comboLineItemDTO);
	}

	@PutMapping("/combolineitem")
	public ResponseEntity<ComboLineItemDTO> updateComboLineItem(@RequestBody ComboLineItemDTO comboLineItemDTO) {
		return this.comboLineItemResourceApi.updateComboLineItemUsingPUT(comboLineItemDTO);
	}

	@DeleteMapping("/combolineitem/{id}")
	public ResponseEntity<Void> deleteComboLineItem(@PathVariable Long id) {
		return this.comboLineItemResourceApi.deleteComboLineItemUsingDELETE(id);
	}

	@PostMapping("/banner")
	public ResponseEntity<BannerDTO> createBanner(@RequestBody BannerDTO bannerDTO) {
		return this.bannerResourceApi.createBannerUsingPOST(bannerDTO);
	}

	@PutMapping("/banner")
	public ResponseEntity<BannerDTO> updateBanner(@RequestBody BannerDTO bannerDTO) {
		return this.bannerResourceApi.updateBannerUsingPUT(bannerDTO);
	}

	@DeleteMapping("/banner/{id}")
	public ResponseEntity<Void> deleteBanner(@PathVariable Long id) {
		return this.bannerResourceApi.deleteBannerUsingDELETE(id);
	}
	
	@PostMapping("/stock-entry")
	public ResponseEntity<StockEntryDTO> createStockEntry(@RequestBody StockEntryDTO stockEntryDTO) {
		return this.stockEntryResourceApi.createStockEntryUsingPOST(stockEntryDTO);
	}
	
	@PutMapping("/stock-entry")
	public ResponseEntity<StockEntryDTO> updateStockEntry(@RequestBody StockEntryDTO stockEntryDTO) {
		return this.stockEntryResourceApi.updateStockEntryUsingPUT(stockEntryDTO);
	}

	
	@DeleteMapping("/stock-entry/{id}")
	public ResponseEntity<Void> deleteStockEntry(@PathVariable Long id) {
		return this.stockEntryResourceApi.deleteStockEntryUsingDELETE(id);
	}
	
	@PostMapping("/entryLineItem")
	public ResponseEntity<EntryLineItemDTO> createEntryLineItem(@RequestBody EntryLineItemDTO entrylineitemDTO) {
		
		log.info("////////////////////////////////////////////////////"+entrylineitemDTO);
		return this.entryLineItemResourceApi.createEntryLineItemUsingPOST(entrylineitemDTO);
	}

	@PutMapping("/entryLineItem")
	public ResponseEntity<EntryLineItemDTO> updateEntryLineItem(@RequestBody EntryLineItemDTO entrylineitemDTO) {
		return this.entryLineItemResourceApi.updateEntryLineItemUsingPUT(entrylineitemDTO);
	}

	@DeleteMapping("/entryLineItem/{id}")
	public ResponseEntity<Void> deleteEntryLineItem(@PathVariable Long id) {
		return this.entryLineItemResourceApi.deleteEntryLineItemUsingDELETE(id);
	}
	
	@PostMapping("/reason")
	public ResponseEntity<ReasonDTO> createReason(@RequestBody ReasonDTO reasonDTO) {
		return this.reasonResourceApi.createReasonUsingPOST(reasonDTO);
	}

	@PutMapping("/reason")
	public ResponseEntity<ReasonDTO> updateReason(@RequestBody ReasonDTO reasonDTO) {
		return this.reasonResourceApi.updateReasonUsingPUT(reasonDTO);
	}

	@DeleteMapping("/reason/{id}")
	public ResponseEntity<Void> deleteReason(@PathVariable Long id) {
		return this.reasonResourceApi.deleteReasonUsingDELETE(id);
	}
	
	@PostMapping("/location")
	public ResponseEntity<LocationDTO> createLocation(@RequestBody LocationDTO locationDTO) {
		return this.locationResourceApi.createLocationUsingPOST(locationDTO);
	}

	@PutMapping("/location")
	public ResponseEntity<LocationDTO> updateLocation(@RequestBody LocationDTO locationDTO) {
		return this.locationResourceApi.updateLocationUsingPUT(locationDTO);
	}

	@DeleteMapping("/location/{id}")
	public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
		return this.locationResourceApi.deleteLocationUsingDELETE(id);
	}
	
	@PostMapping("/product/address")
	public ResponseEntity<AddressDTO> createProductAddress(@RequestBody AddressDTO addressDTO) {
		return this.addressResourceApi.createAddressUsingPOST(addressDTO);
	}

	@PutMapping("/product/address")
	public ResponseEntity<AddressDTO> updateProductAddress(@RequestBody AddressDTO addressDTO) {
		return this.addressResourceApi.updateAddressUsingPUT(addressDTO);
	}

	@DeleteMapping("/product/address/{id}")
	public ResponseEntity<Void> deleteProductAddress(@PathVariable Long id) {
		return this.addressResourceApi.deleteAddressUsingDELETE(id);
	}
	
	@PostMapping("/store/preOrderSettings")
	public ResponseEntity<PreOrderSettingsDTO> createPreOrderSettings(@RequestBody PreOrderSettingsDTO preOrderSettingsDTO) {
		return this.preOrderSettingsResourceApi.createPreOrderSettingsUsingPOST(preOrderSettingsDTO);
	}
	
	@PutMapping("/store/preOrderSettings")
	public ResponseEntity<PreOrderSettingsDTO> updatePreOrderSettings(@RequestBody PreOrderSettingsDTO preOrderSettingsDTO) {
		return this.preOrderSettingsResourceApi.updatePreOrderSettingsUsingPUT(preOrderSettingsDTO);
	}


	@DeleteMapping("/store/preOrderSettings")
	public ResponseEntity<Void> deletePreOrderSettings(@PathVariable Long id) {
		return this.preOrderSettingsResourceApi.deletePreOrderSettingsUsingDELETE(id);
				
	}
	
	@PostMapping("/discount")
	public ResponseEntity<DiscountDTO> createDiscount(@RequestBody DiscountDTO discountDTO) {
		return this.discountResourceApi.createDiscountUsingPOST(discountDTO);
	}

	@PutMapping("/discount")
	public ResponseEntity<DiscountDTO> updateDiscount(@RequestBody DiscountDTO discountDTO) {
		return  this.discountResourceApi.updateDiscountUsingPUT(discountDTO);
	}
	
	@DeleteMapping("/discount/{id}")
	public ResponseEntity<Void> deleteDiscount(@PathVariable Long id) {
		return this.discountResourceApi.deleteDiscountUsingDELETE(id);
	}
	
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

		PreOrderSettingsDTO preOrderSettingsDTO = storeBundleDTO.getPreOrderSettings();
		
		if (preOrderSettingsDTO != null) {
			if (preOrderSettingsDTO.getId() == null) {
				preOrderSettingsDTO = preOrderSettingsResourceApi.createPreOrderSettingsUsingPOST(preOrderSettingsDTO).getBody();
			} else {
				preOrderSettingsDTO = preOrderSettingsResourceApi.updatePreOrderSettingsUsingPUT(preOrderSettingsDTO).getBody();
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
		storeDTO.setPreOrderSettingsId(preOrderSettingsDTO.getId());
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
