package com.diviso.graeshoppe.service;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.diviso.graeshoppe.client.customer.model.Customer;
import com.diviso.graeshoppe.client.order.model.Notification;
import com.diviso.graeshoppe.client.order.model.Order;
import com.diviso.graeshoppe.client.order.model.OrderLine;
import com.diviso.graeshoppe.client.product.model.Address;
import com.diviso.graeshoppe.client.product.model.AuxilaryLineItem;
import com.diviso.graeshoppe.client.product.model.Category;
import com.diviso.graeshoppe.client.product.model.ComboLineItem;
import com.diviso.graeshoppe.client.product.model.Discount;
import com.diviso.graeshoppe.client.product.model.EntryLineItem;
import com.diviso.graeshoppe.client.product.model.Location;
import com.diviso.graeshoppe.client.product.model.Product;
import com.diviso.graeshoppe.client.product.model.Reason;
import com.diviso.graeshoppe.client.product.model.StockCurrent;
import com.diviso.graeshoppe.client.product.model.StockEntry;
import com.diviso.graeshoppe.client.product.model.UOM;
import com.diviso.graeshoppe.client.report.model.AuxItem;
import com.diviso.graeshoppe.client.report.model.ComboItem;
import com.diviso.graeshoppe.client.report.model.OrderMaster;
import com.diviso.graeshoppe.client.sale.model.Sale;
import com.diviso.graeshoppe.client.sale.model.TicketLine;
import com.diviso.graeshoppe.client.store.model.*;


public interface QueryService {
	public Page<Category> findAllCategories(Pageable pageable);

	public Page<Product> findProductByCategoryId(Long categoryId, String storeId, Pageable pageable);

	public Page<Customer> findAllCustomers(String searchTerm, Pageable pageable);
	/**
	 * @param statusName
	 */
	public Page<Order> findOrderByStatusNameAndDeliveryType(String statusName, String storeId, String deliveryType,Pageable pageable);

	public List<String> findAllUom(Pageable pageable);

	public Page<Customer> findAllCustomersWithoutSearch(Pageable pageable);

	/**
	 * @param pageable
	 * @return
	 */
	public Page<Product> findAllProduct(String storeId, Pageable pageable);

	/**
	 * 
	 * @param storeId
	 * @param pageable
	 * @return 
	 */
	public Page<EntryLineItem> findAllEntryLineItems(String storeId, Pageable pageable);

	/**
	 * @return
	 */
	public Page<Sale> findSales(String storeId, Pageable pageable);

	/**
	 * 
	 * @param saleId
	 * @return
	 */
	public List<TicketLine> findTicketLinesBySaleId(Long saleId);

	/**
	 * 
	 * @param storeId
	 * @param pageable
	 * @return
	 */
	public Page<StockCurrent> findAllStockCurrents(String storeId, Pageable pageable);

	/**
	 * 
	 * @param storeId
	 * @param pageable
	 * @return
	 */
	public Page<StockEntry> findAllStockEntries(String storeId, Pageable pageable);

	/**
	 * 
	 * @param searchTerm
	 * @param storeId
	 * @param pageable
	 * @return
	 */
	public Page<Product> findAllProductBySearchTerm(String searchTerm, String storeId, Pageable pageable);

	/**
	 * 
	 * @param categoryId
	 * @param storeId
	 * @param pageable
	 * @return
	 */
	public Page<StockCurrent> findAllStockCurrentByCategoryId(Long categoryId, String storeId, Pageable pageable);

	/**
	 * 
	 * @param productId
	 * @param storeId
	 * @return
	 */
	public StockCurrent findStockCurrentByProductId(Long productId, String storeId);

	/**
	 * 
	 * @param productId
	 * @param storeId
	 * @return
	 */
	public StockEntry findStockEntryByProductId(Long productId, String storeId);

	/**
	 * 
	 * @param name
	 * @param storeId
	 * @param pageable
	 * @return
	 */
	public Page<StockCurrent> findStockCurrentByProductName(String name, String storeId, Pageable pageable);
/**
 * 
 * @param storeId
 * @param pageable
 * @return
 */
	public Page<Product> findAllProducts(String storeId, Pageable pageable);

	/**
	 * 
	 * @param storeId
	 * @param pageable
	 * @return
	 */
	public Page<Review> findAllReviews(String storeId, Pageable pageable);

	/**
	 * 
	 * @param storeId
	 * @param pageable
	 * @return
	 */
	public Page<UserRating> findAllUserRatings(String storeId, Pageable pageable);

	/**
	 * 
	 * @param regNo
	 * @return
	 */
	public Store findStoreByRegNo(String regNo);

	/**
	 * @param id
	 * @return
	 */
	public Page<DeliveryInfo> findDeliveryInfoByStoreId(Long id);

	/**
	 * @param storeId
	 */
	public Page<Order> findOrderByStoreId(String storeId, Pageable pageable);

	/**
	 * @param orderId
	 * @return
	 */
	List<OrderLine> findOrderLinesByOrderId(Long orderId);

	/**
	 * @param storeId
	 * @param pageable
	 * @return
	 */
	Page<Category> findAllCategories(String storeId, Pageable pageable);

	/**
	 * @param storeId
	 * @param pageable
	 * @return
	 */
	public Page<AuxilaryLineItem> findAuxilaryLineItemsByIDPcode(String iDPcode, Pageable pageable);

	/**
	 * @param storeId
	 * @param pageable
	 * @return
	 */
	public Page<UOM> findUOMByIDPcode(String iDPcode, Pageable pageable);

	/**
	 * @param storeId
	 * @param pageable
	 * @return
	 */
	List<Type> findAllDeliveryTypesByStoreId(String storeId);

	/**
	 * @param regNo
	 * @return
	 */
	public List<StoreType> findAllStoreTypesByStoreId(String regNo);

	/**
	 * @param regNo
	 * @return
	 */
	public List<Banner> findAllBannersByStoreId(String regNo);

	/**
	 * @param iDPcode
	 * @param pageable
	 * @return
	 */
	public Page<Product> findNotAuxNotComboProductsByIDPcode(String iDPcode, Pageable pageable);

	/**
	 * @return
	 */
	public Page<Product> findAllAuxilaryProducts(String storeId);

	/**
	 * @param id
	 * @return
	 */
	public Product findProductById(Long id);

	/**
	 * @param id
	 * @return
	 */
	public Category findCategoryById(Long id);

	/**
	 * @param id
	 * @return
	 */
	public UOM findUOMById(Long id);

	/**
	 * @param id
	 */
	public List<ComboLineItem> finAllComboLineItemsByProductId(Long id);

	/**
	 * @param productId
	 * 
	 */
	public List<AuxilaryLineItem> findAllAuxilaryProductsByProductId(Long productId);

	public Page<Banner> findBannersByStoreId(String storeId);

	/**
	 * @param orderId
	 */
	public Order findOrderByOrderId(String orderId);

	/**
	 * 
	 * @param pageable
	 * @return
	 */
	public Page<Product> findProducts(Pageable pageable);

	/**
	 * 
	 * @param receiverId
	 * @param pageable
	 * @return
	 */
	public Page<Notification> findNotificationByReceiverId(String receiverId,Pageable pageable);

	/**
	 * 
	 * @param statusName
	 * @param date
	 * @return
	 */
	public Long findOrderCountByDateAndStatusName(String statusName, Instant date);

	/**
	 * 
	 * @param from
	 * @param to
	 * @param storeId
	 * @return
	 */
	public Page<Order> findOrderByDatebetweenAndStoreId(Instant from, Instant to, String storeId);

	/**
	 * 
	 * @param status
	 * @param receiverId
	 * @return
	 */
	public Long getNotificationCountByReceiveridAndStatus(String status, String receiverId);

	/**
	 * 
	 * @param searchTerm
	 * @param storeId
	 * @param pageable
	 * @return
	 */
	public Page<Category> findAllCategoryBySearchTerm(String searchTerm, String storeId, Pageable pageable);

	/**
	 * 
	 * @param productId
	 * @return
	 */
	public Discount findDiscountByProductId(Long productId);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public StockEntry findStockEntryById(Long id);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<EntryLineItem> findAllEntryLineItemsByStockEntryId(Long id);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Reason findReasonByStockEntryId(Long id);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Location findLocationByStockEntryId(Long id);

	/**
	 * 
	 * @param idpcode
	 * @param pageable
	 * @return
	 */
	public Page<Location> findLocationByIdpcode(String idpcode, Pageable pageable);

	/**
	 * 
	 * @param idpcode
	 * @param pageable
	 * @return
	 */
	public Page<Reason> findReasonByIdpcode(String idpcode, Pageable pageable);

	/**
	 * 
	 * @param id
	 * @param pageable
	 * @return
	 */
	public Page<EntryLineItem> findAllEntryLineItemsByStockEntryId(String id, Pageable pageable);

	/**
	 * 
	 * @param receiverId
	 * @param status
	 * @return
	 */
	public Long findNotificationCountByReceiverIdAndStatusName(String receiverId, String status);

	/**
	 * 
	 * @param orderId
	 * @return
	 */
	public OrderMaster findOrderMasterByOrderId(String orderId);

	/**
	 * 
	 * @param orderLineId
	 * @param pageable
	 * @return
	 */
	public Page<AuxItem> findAuxItemByOrderLineId(Long orderLineId, Pageable pageable);

	/**
	 * 
	 * @param orderLineId
	 * @param pageable
	 * @return
	 */
	public Page<ComboItem> findComboItemByOrderLineId(Long orderLineId,Pageable pageable);

	/**
	 * 
	 * @param orderMasterId
	 * @param pageable
	 * @return
	 */
	public Page<com.diviso.graeshoppe.client.report.model.OrderLine> findOrderLineByOrderMasterId(Long orderMasterId,
			Pageable pageable);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Address findAddressByStockEntryId(Long id);

}
