package com.diviso.graeshoppe.service;

import org.springframework.http.ResponseEntity;

import com.diviso.graeshoppe.client.order.model.OrderDTO;

public interface OrderCommandService {

	ResponseEntity<OrderDTO> updateOrder(OrderDTO orderDTO);
}
