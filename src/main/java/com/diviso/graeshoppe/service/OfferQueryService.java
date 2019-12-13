package com.diviso.graeshoppe.service;

import java.util.List;

import com.diviso.graeshoppe.client.order.model.aggregator.Offer;

public interface OfferQueryService {

	List<Offer> findOfferLinesByOrderId(Long orderId);

}
