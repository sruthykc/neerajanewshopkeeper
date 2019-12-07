package com.diviso.graeshoppe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.diviso.graeshoppe.client.order.api.OrderCommandResourceApi;
import com.diviso.graeshoppe.client.order.model.OrderDTO;
import com.diviso.graeshoppe.service.OrderCommandService;

@Service
public class OrderCommandServiceImpl implements OrderCommandService{

	@Autowired
	private OrderCommandResourceApi orderCommandResourceApi;

	@Override
	public ResponseEntity<OrderDTO> updateOrder(OrderDTO orderDTO) {
		return orderCommandResourceApi.updateOrderUsingPUT(orderDTO);
	}

}
