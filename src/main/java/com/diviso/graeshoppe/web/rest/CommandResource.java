package com.diviso.graeshoppe.web.rest;

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
import com.diviso.graeshoppe.client.customer.model.ContactDTO;
import com.diviso.graeshoppe.client.customer.model.CustomerDTO;
import com.diviso.graeshoppe.client.order.api.NotificationResourceApi;
import com.diviso.graeshoppe.client.order.api.OrderCommandResourceApi;
import com.diviso.graeshoppe.client.order.model.ApprovalDetailsDTO;
import com.diviso.graeshoppe.client.order.model.NotificationDTO;
import com.diviso.graeshoppe.client.order.model.Order;
import com.diviso.graeshoppe.client.order.model.OrderDTO;
import com.diviso.graeshoppe.client.product.api.EntryLineItemResourceApi;
import com.diviso.graeshoppe.client.product.api.LocationResourceApi;
import com.diviso.graeshoppe.client.product.api.ReasonResourceApi;
import com.diviso.graeshoppe.client.product.api.StockEntryResourceApi;
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
import com.diviso.graeshoppe.client.sale.model.SaleDTO;
import com.diviso.graeshoppe.client.sale.model.TicketLineDTO;
import com.diviso.graeshoppe.client.store.api.BannerResourceApi;
import com.diviso.graeshoppe.client.store.api.DeliveryInfoResourceApi;
import com.diviso.graeshoppe.client.store.api.PreOrderSettingsResourceApi;
import com.diviso.graeshoppe.client.store.api.StoreAddressResourceApi;
import com.diviso.graeshoppe.client.store.api.StoreResourceApi;
import com.diviso.graeshoppe.client.store.api.StoreSettingsResourceApi;
import com.diviso.graeshoppe.client.store.api.StoreTypeResourceApi;
import com.diviso.graeshoppe.client.store.api.TypeResourceApi;
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
import com.diviso.graeshoppe.service.CustomerCommandService;
import com.diviso.graeshoppe.service.OrderCommandService;
import com.diviso.graeshoppe.service.OrderQueryService;
import com.diviso.graeshoppe.service.ProductCommandService;
import com.diviso.graeshoppe.service.SaleCommandService;
import com.diviso.graeshoppe.service.StoreCommandService;

@RestController
@RequestMapping("/api/command")
public class CommandResource {

	@Autowired
	private OrderCommandResourceApi orderCommandResourceApi;  //used in this.markOrderAsDelivered()
	
	@Autowired 
	private StockEntryResourceApi stockEntryResourceApi;
	
	@Autowired
	private StoreResourceApi storeResourceApi;		//used in this.createStoreBundle

	@Autowired
	private NotificationResourceApi notificationResource;		//used in this.markOrderAsDelivered()
	
	@Autowired
	private PreOrderSettingsResourceApi preOrderSettingsResourceApi;	

	@Autowired
	DeliveryInfoResourceApi deliveryInfoResourceApi;				

	@Autowired
	TypeResourceApi typeResourceApi;								
	
	@Autowired
	BannerResourceApi bannerResourceApi;						
	
	@Autowired
	private StoreAddressResourceApi storeAddressResourceApi;		

	@Autowired
	private StoreSettingsResourceApi storeSettingsResourceApi;		

	@Autowired
	private StoreTypeResourceApi storeTypeResourceApi;				
	
    @Autowired
    private EntryLineItemResourceApi entryLineItemResourceApi;
    
    @Autowired
    private ReasonResourceApi reasonResourceApi;
    
    @Autowired
    private LocationResourceApi locationResourceApi;
 
    @Autowired
    OrderQueryService orderQueryService;
    
    @Autowired
    private CustomerCommandService customerCommandService;
    
    @Autowired
    private ProductCommandService productCommandService;
    
    @Autowired
    private StoreCommandService storeCommandService;
    
    @Autowired
    private OrderCommandService orderCommandService;
    
    @Autowired
    private SaleCommandService saleCommandService;
    
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
		return customerCommandService.createCustomer(customerAggregator);
	}

	@PutMapping("/customers")
	public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO customerDTO) {
		return customerCommandService.updateCustomer(customerDTO);
	}

	@DeleteMapping("/customers/{id}")
	public void deleteCustomer(@PathVariable Long id) {
		customerCommandService.deleteCustomer(id);
	}

	@PostMapping("/contacts")
	public ResponseEntity<ContactDTO> createContact(@RequestBody ContactDTO contact) {
		return customerCommandService.createContact(contact);
	}
	
	@PutMapping("/contacts")
	public ResponseEntity<ContactDTO> updateContact(@RequestBody ContactDTO contact) {
		return customerCommandService.updateContact(contact);
	}

	@DeleteMapping("/contacts/{id}")
	public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
		return customerCommandService.deleteContact(id);
	}

	@DeleteMapping("/store-types/{id}")
	public ResponseEntity<Void> deleteStoreType(@PathVariable Long id) {
		return storeCommandService.deleteStoreType(id);
	}

	@PutMapping("/categories")
	public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO) {
		return productCommandService.updateCategory(categoryDTO);
	}

	@DeleteMapping("/categories/{id}")
	public void deleteCategory(@PathVariable Long id) {
		productCommandService.deleteCategory(id);
	}

	@PostMapping("/unit-of-meassurement")
	public ResponseEntity<UOMDTO> createUOM(@RequestBody UOMDTO uomDTO) {
		return productCommandService.createUOM(uomDTO);
	}

	@PostMapping("/productCategory")
	public ResponseEntity<CategoryDTO> createProductCategory(@RequestBody CategoryDTO categoryDTO) {
		return productCommandService.createProductCategory(categoryDTO);
	}

	@PostMapping("/products")
	public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
		return productCommandService.createProduct(productDTO);
	}

	@PutMapping("/products")
	public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO) {
		return productCommandService.updateProduct(productDTO);
	}

	@DeleteMapping("/products/{id}")
	public void deleteProduct(@PathVariable Long id) {
		productCommandService.deleteProduct(id);
	}

	@PostMapping("/sales")
	public ResponseEntity<SaleDTO> createSale(@RequestBody SaleDTO saleDTO) {
		return saleCommandService.createSale(saleDTO);
	}

	@PutMapping("/sales")
	public ResponseEntity<SaleDTO> updateSale(@RequestBody SaleDTO saleDTO) {
		return saleCommandService.updateSale(saleDTO);
	}

	@DeleteMapping("/sales/{id}")
	public void deleteSale(@PathVariable Long id) {
		saleCommandService.deleteSale(id);
	}

	@PostMapping("/ticket-lines")
	public ResponseEntity<TicketLineDTO> createTickerLine(@RequestBody TicketLineDTO ticketLineDTO) { 			//correct mathod name
		return saleCommandService.createTickerLine(ticketLineDTO);
	}

	@PutMapping("/ticket-lines")
	public ResponseEntity<TicketLineDTO> updateTicketLine(@RequestBody TicketLineDTO ticketLineDTO) {
		return saleCommandService.updateTicketLine(ticketLineDTO);
	}

	@DeleteMapping("/ticket-lines/{id}")
	public void deleteTicketline(@PathVariable Long id) {
		saleCommandService.deleteTicketline(id);
	}

	@PutMapping("/uoms")
	public ResponseEntity<UOMDTO> updateUOM(@RequestBody UOMDTO uomDTO) {
		return productCommandService.updateUOM(uomDTO);
	}

	@DeleteMapping("/uoms/{id}")
	public void deleteUOM(@PathVariable Long id) {
		productCommandService.deleteUOM(id);
	}

	@PostMapping("/stock-currents")
	public ResponseEntity<StockCurrentDTO> createStockCurrent(@RequestBody StockCurrentDTO stockCurrent) {
		return productCommandService.createStockCurrent(stockCurrent);
	}

	@PutMapping("/stock-currents")
	public ResponseEntity<StockCurrentDTO> updateStockCurrent(@RequestBody StockCurrentDTO StockCurrent) {
		return productCommandService.updateStockCurrent(StockCurrent);
	}

	@PostMapping("/stores")
	public ResponseEntity<StoreDTO> createStore(@RequestBody StoreDTO storeDTO) {
		return storeCommandService.createStore(storeDTO);
	}
	
	
//	Order Related Command Resources starts here
	
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
		return orderCommandService.createApprovalDetails(taskId, approvalDetailsDTO);
	}

	public ResponseEntity<OrderDTO> updateOrder(OrderDTO orderDTO) {
		return orderCommandService.updateOrder(orderDTO);
	}
	
	@PutMapping("/notifications")
	public ResponseEntity<NotificationDTO> updateNotification(@RequestBody NotificationDTO notificationDTO) {
		return orderCommandService.updateNotification(notificationDTO);
	}
	
//	Order related commands ends here
	
	@PutMapping("/stores")
	public ResponseEntity<StoreDTO> updateStore(@RequestBody StoreDTO storeDTO) {
		return storeCommandService.updateStore(storeDTO);
	}

	@DeleteMapping("/stores/{id}")
	public void deleteStore(@PathVariable Long id) {
		 storeCommandService.deleteStore(id);
	}

	@PostMapping("/replies")
	public ResponseEntity<ReplyDTO> createReply(@RequestBody ReplyDTO replyDTO) {
		return storeCommandService.createReply(replyDTO);
	}

	@PutMapping("/replies")
	public ResponseEntity<ReplyDTO> updateReply(@RequestBody ReplyDTO replyDTO) {
		return storeCommandService.updateReply(replyDTO);
	}

	@DeleteMapping("/replies/{id}")
	public ResponseEntity<Void> deleteReply(@PathVariable Long id) {
		return storeCommandService.deleteReply(id);
	}

	/*@PostMapping("/user-ratings")
	public ResponseEntity<UserRatingDTO> createUserRating(@RequestBody UserRatingDTO userRatingDTO) {
		return storeCommandService.createUserRating(userRatingDTO);
	}

	@PutMapping("/user-ratings")
	public ResponseEntity<UserRatingDTO> updateUserRating(@RequestBody UserRatingDTO userRatingDTO) {
		return storeCommandService.updateUserRating(userRatingDTO);
	}

	@DeleteMapping("/user-ratings/{id}")
	public ResponseEntity<Void> deleteUserRating(@PathVariable Long id) {
		return storeCommandService.deleteUserRating(id);
	}

	@PostMapping("/reviews")
	public ResponseEntity<ReviewDTO> createUserRating(@RequestBody ReviewDTO reviewDTO) {		//change method name
		return storeCommandService.createUserRating(reviewDTO);
	}

	@PutMapping("/reviews")
	public ResponseEntity<ReviewDTO> updateUserRating(@RequestBody ReviewDTO reviewDTO) {		//change method name
		return storeCommandService.updateUserRating(reviewDTO);
	}

	@DeleteMapping("/reviews/{id}")
	public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
		return storeCommandService.deleteReview(id);
	}
*/
	@PostMapping("/delivery-infos")
	public ResponseEntity<DeliveryInfoDTO> createDeliveryInfo(@RequestBody DeliveryInfoDTO deliveryInfoDTO) {
		return storeCommandService.createDeliveryInfo(deliveryInfoDTO);
	}

	@PutMapping("/delivery-infos")
	public ResponseEntity<DeliveryInfoDTO> updateDeliveryInfo(@RequestBody DeliveryInfoDTO deliveryInfoDTO) {
		return storeCommandService.updateDeliveryInfo(deliveryInfoDTO);
	}

	@DeleteMapping("/delivery-infos/{id}")
	public void deleteDeliveryInfo(@PathVariable Long id) {
		storeCommandService.deleteDeliveryInfo(id);
	}

	@PostMapping("/types")
	public ResponseEntity<TypeDTO> createType(@RequestBody TypeDTO typeDTO) {
		return storeCommandService.createType(typeDTO);
	}

	@PutMapping("/types")
	public ResponseEntity<TypeDTO> updateType(@RequestBody TypeDTO typeDTO) {
		return storeCommandService.updateType(typeDTO);
	}

	@DeleteMapping("/types/{id}")
	public ResponseEntity<Void> deleteType(@PathVariable Long id) {
		return storeCommandService.deleteType(id);
	}

	/*
	 * @PostMapping("/load-products") public void
	 * loadProducts(@RequestParam("file") MultipartFile file) throws IOException
	 * { // upload and save the file then load
	 * log.info("::::::::::::::::::file:::::::::::::::::::::: "+ file);
	 * loadControllerApi.loadUsingPOST(file.getBytes()); }
	 */

	@PostMapping("/auxilarylineitem")
	public ResponseEntity<AuxilaryLineItemDTO> createAuxilaryLineItem(@RequestBody AuxilaryLineItemDTO auxilaryLineItemDTO) {
		return productCommandService.createAuxilaryLineItem(auxilaryLineItemDTO);
	}

	@PutMapping("/auxilarylineitem")
	public ResponseEntity<AuxilaryLineItemDTO> updateAuxilaryLineItem(@RequestBody AuxilaryLineItemDTO auxilaryLineItemDTO) {
		return productCommandService.updateAuxilaryLineItem(auxilaryLineItemDTO);
	}

	@DeleteMapping("/auxilarylineitem/{id}")
	public ResponseEntity<Void> deleteAuxilaryLineIteam(@PathVariable Long id) {
		return productCommandService.deleteAuxilaryLineIteam(id);
	}

	@PostMapping("/combolineitem")
	public ResponseEntity<ComboLineItemDTO> createComboLineItem(@RequestBody ComboLineItemDTO comboLineItemDTO) {
		return productCommandService.createComboLineItem(comboLineItemDTO);
	}

	@PutMapping("/combolineitem")
	public ResponseEntity<ComboLineItemDTO> updateComboLineItem(@RequestBody ComboLineItemDTO comboLineItemDTO) {
		return productCommandService.updateComboLineItem(comboLineItemDTO);
	}

	@DeleteMapping("/combolineitem/{id}")
	public ResponseEntity<Void> deleteComboLineItem(@PathVariable Long id) {
		return productCommandService.deleteComboLineItem(id);
	}

	@PostMapping("/banner")
	public ResponseEntity<BannerDTO> createBanner(@RequestBody BannerDTO bannerDTO) {
		return storeCommandService.createBanner(bannerDTO);
	}

	@PutMapping("/banner")
	public ResponseEntity<BannerDTO> updateBanner(@RequestBody BannerDTO bannerDTO) {
		return storeCommandService.updateBanner(bannerDTO);
	}

	@DeleteMapping("/banner/{id}")
	public ResponseEntity<Void> deleteBanner(@PathVariable Long id) {
		return storeCommandService.deleteBanner(id);
	}
	
	@PostMapping("/stock-entry")
	public ResponseEntity<StockEntryDTO> createStockEntry(@RequestBody StockEntryDTO stockEntryDTO) {
		return productCommandService.createStockEntry(stockEntryDTO);
	}
	
	@PutMapping("/stock-entry")
	public ResponseEntity<StockEntryDTO> updateStockEntry(@RequestBody StockEntryDTO stockEntryDTO) {
		return productCommandService.updateStockEntry(stockEntryDTO);
	}

	
	@DeleteMapping("/stock-entry/{id}")
	public ResponseEntity<Void> deleteStockEntry(@PathVariable Long id) {
		return productCommandService.deleteStockEntry(id);
	}
	
	@PostMapping("/entryLineItem")
	public ResponseEntity<EntryLineItemDTO> createEntryLineItem(@RequestBody EntryLineItemDTO entrylineitemDTO) {
		
		log.info("////////////////////////////////////////////////////"+entrylineitemDTO);
		return productCommandService.createEntryLineItem(entrylineitemDTO);
	}

	@PutMapping("/entryLineItem")
	public ResponseEntity<EntryLineItemDTO> updateEntryLineItem(@RequestBody EntryLineItemDTO entrylineitemDTO) {
		return productCommandService.updateEntryLineItem(entrylineitemDTO);
	}

	@DeleteMapping("/entryLineItem/{id}")
	public ResponseEntity<Void> deleteEntryLineItem(@PathVariable Long id) {
		return productCommandService.deleteEntryLineItem(id);
	}
	
	@PostMapping("/reason")
	public ResponseEntity<ReasonDTO> createReason(@RequestBody ReasonDTO reasonDTO) {
		return productCommandService.createReason(reasonDTO);
	}

	@PutMapping("/reason")
	public ResponseEntity<ReasonDTO> updateReason(@RequestBody ReasonDTO reasonDTO) {
		return productCommandService.updateReason(reasonDTO);
	}

	@DeleteMapping("/reason/{id}")
	public ResponseEntity<Void> deleteReason(@PathVariable Long id) {
		return productCommandService.deleteReason(id);
	}
	
	@PostMapping("/location")
	public ResponseEntity<LocationDTO> createLocation(@RequestBody LocationDTO locationDTO) {
		return productCommandService.createLocation(locationDTO);
	}

	@PutMapping("/location")
	public ResponseEntity<LocationDTO> updateLocation(@RequestBody LocationDTO locationDTO) {
		return productCommandService.updateLocation(locationDTO);
	}

	@DeleteMapping("/location/{id}")
	public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
		return productCommandService.deleteLocation(id);
	}
	
	@PostMapping("/product/address")
	public ResponseEntity<AddressDTO> createProductAddress(@RequestBody AddressDTO addressDTO) {
		return productCommandService.createProductAddress(addressDTO);
	}

	@PutMapping("/product/address")
	public ResponseEntity<AddressDTO> updateProductAddress(@RequestBody AddressDTO addressDTO) {
		return productCommandService.updateProductAddress(addressDTO);
	}

	@DeleteMapping("/product/address/{id}")
	public ResponseEntity<Void> deleteProductAddress(@PathVariable Long id) {
		return productCommandService.deleteProductAddress(id);
	}
	
	@PostMapping("/store/preOrderSettings")
	public ResponseEntity<PreOrderSettingsDTO> createPreOrderSettings(@RequestBody PreOrderSettingsDTO preOrderSettingsDTO) {
		return storeCommandService.createPreOrderSettings(preOrderSettingsDTO);
	}
	
	@PutMapping("/store/preOrderSettings")
	public ResponseEntity<PreOrderSettingsDTO> updatePreOrderSettings(@RequestBody PreOrderSettingsDTO preOrderSettingsDTO) {
		return storeCommandService.updatePreOrderSettings(preOrderSettingsDTO);
	}


	@DeleteMapping("/store/preOrderSettings")
	public ResponseEntity<Void> deletePreOrderSettings(@PathVariable Long id) {
		return storeCommandService.deletePreOrderSettings(id);
				
	}
	
	@PostMapping("/discount")
	public ResponseEntity<DiscountDTO> createDiscount(@RequestBody DiscountDTO discountDTO) {
		return productCommandService.createDiscount(discountDTO);
	}

	@PutMapping("/discount")
	public ResponseEntity<DiscountDTO> updateDiscount(@RequestBody DiscountDTO discountDTO) {
		return productCommandService.updateDiscount(discountDTO);
	}
	
	@DeleteMapping("/discount/{id}")
	public ResponseEntity<Void> deleteDiscount(@PathVariable Long id) {
		return productCommandService.deleteDiscount(id);
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
