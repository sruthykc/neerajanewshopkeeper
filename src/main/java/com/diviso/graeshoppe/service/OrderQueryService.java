package com.diviso.graeshoppe.service;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.diviso.graeshoppe.client.order.model.Notification;
import com.diviso.graeshoppe.client.order.model.Order;
import com.diviso.graeshoppe.client.order.model.OrderLine;

public interface OrderQueryService {
	
	/**
	 * @param statusName
	 */
	public Page<Order> findOrderByStatusNameAndDeliveryType(String statusName, String storeId, String deliveryType,Pageable pageable);

	/**
	 * @param storeId
	 */
	public Page<Order> findOrderByStoreId(String storeId, Pageable pageable);

	/**
	 * @param orderId
	 * @return
	 */
	List<OrderLine> findOrderLinesByOrderId(Long orderId);
	
	public Page<Notification> findNotificationByReceiverId(String receiverId,Pageable pageable);
	
	//public Long findOrderCountByDateAndStatusName(String statusName, Instant date);
	
	public Page<Order> findOrderByDatebetweenAndStoreId(Instant from, Instant to, String storeId);
	
	public Long getNotificationCountByReceiveridAndStatus(String status, String receiverId);
	
	public Long findNotificationCountByReceiverIdAndStatusName(String receiverId, String status);
	
	/**
	 * @param orderId
	 */
	public Order findOrderByOrderId(String orderId);
	
	

}
