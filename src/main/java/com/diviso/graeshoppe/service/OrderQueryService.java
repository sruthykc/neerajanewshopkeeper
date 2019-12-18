package com.diviso.graeshoppe.service;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.diviso.graeshoppe.client.order.model.Notification;
import com.diviso.graeshoppe.client.order.model.OpenTask;
import com.diviso.graeshoppe.client.order.model.Order;
import com.diviso.graeshoppe.client.order.model.OrderLine;
import com.diviso.graeshoppe.client.order.model.aggregator.AuxilaryOrderLine;

/**
 * 
 * @author Prince
 *
 */
public interface OrderQueryService {

	/**
	 * @param statusName
	 * @param storeid
	 * @param deliveryType
	 * @param pageable
	 */
	public Page<Order> findOrderByStatusNameAndStoreIdAndDeliveryType(String statusName, String storeId,
			String deliveryType, Pageable pageable);

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
	 * 
	 * @param receiverId
	 * @param pageable
	 * @return
	 */
	public Page<Notification> findNotificationByReceiverId(String receiverId, Pageable pageable);

	// public Long findOrderCountByDateAndStatusName(String statusName, Instant
	// date);

	/**
	 * 
	 * @param from
	 * @param to
	 * @param storeId
	 * @return
	 */
	public Page<Order> findOrderByDatebetweenAndStoreId(Instant from, Instant to, String storeId, Pageable pageable);

	/**
	 * 
	 * @param status
	 * @param receiverId
	 * @return
	 */
	public Long getNotificationCountByReceiveridAndStatus(String status, String receiverId);

	/**
	 * 
	 * @param receiverId
	 * @param status
	 * @return
	 */
	//public Long findNotificationCountByReceiverIdAndStatusName(String receiverId, String status);

	/**
	 * @param orderId
	 */
	public Order findOrderByOrderId(String orderId);

	public Long orderCountByCustomerIdAndStoreId(String customerId, String storeId);

	public ResponseEntity<OpenTask> getTaskDetails(String taskName, String orderId, String storeId);

	public ResponseEntity<List<Order>> getTasks(String assignee, String assigneeLike, String candidateGroup,
			String candidateGroups, String candidateUser, String createdAfter, String createdBefore, String createdOn,
			String name, String nameLike);

	public Page<com.diviso.graeshoppe.client.order.model.aggregator.OrderLine> findAllOrderLinesByOrderId(Long orderId, Pageable pageable);

	public Page<AuxilaryOrderLine> findAuxilaryOrderLineByOrderLineId(Long orderLineId, Pageable pageable);

	// List<OrderLine> findOrderLinesByOrderId(Long orderId, Pageable pageable);
}
