package com.diviso.graeshoppe.client.order.api;

import org.springframework.cloud.openfeign.FeignClient;

import com.diviso.graeshoppe.client.order.OrderClientConfiguration;

@FeignClient(name="${order.name:order}", url="${order.url}", configuration = OrderClientConfiguration.class)
public interface OrderLineResourceApiClient extends OrderLineResourceApi {
}