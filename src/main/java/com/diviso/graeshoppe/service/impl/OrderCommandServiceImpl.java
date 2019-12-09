package com.diviso.graeshoppe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.diviso.graeshoppe.client.order.api.ApprovalDetailsResourceApi;
import com.diviso.graeshoppe.client.order.api.NotificationResourceApi;
import com.diviso.graeshoppe.client.order.api.OrderCommandResourceApi;
import com.diviso.graeshoppe.client.order.model.ApprovalDetailsDTO;
import com.diviso.graeshoppe.client.order.model.CommandResource;
import com.diviso.graeshoppe.client.order.model.NotificationDTO;
import com.diviso.graeshoppe.client.order.model.OrderDTO;
import com.diviso.graeshoppe.service.OrderCommandService;

@Service
public class OrderCommandServiceImpl implements OrderCommandService{

	@Autowired
	private OrderCommandResourceApi orderCommandResourceApi;
	
	@Autowired
	private NotificationResourceApi notificationResourceApi;
	
	@Autowired
	private ApprovalDetailsResourceApi approvalDetailsApi;

	@Override
	public ResponseEntity<OrderDTO> updateOrder(OrderDTO orderDTO) {
		return orderCommandResourceApi.updateOrderUsingPUT(orderDTO);
	}

	@Override
	public ResponseEntity<NotificationDTO> updateNotification(NotificationDTO notificationDTO) {
		
		return notificationResourceApi.updateNotificationUsingPUT(notificationDTO);
	}

	@Override
	public ResponseEntity<CommandResource> createApprovalDetails(String taskId, ApprovalDetailsDTO approvalDetailsDTO) {
		
		return approvalDetailsApi.createApprovalDetailsUsingPOST(taskId, approvalDetailsDTO);

	}

}
