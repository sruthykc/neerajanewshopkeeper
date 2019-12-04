package com.diviso.graeshoppe.client.order.api;

import org.springframework.cloud.openfeign.FeignClient;
import com.diviso.graeshoppe.client.order.OrderClientConfiguration;

@FeignClient(name="${order.name:order}", url="${order.url:dev.ci2.divisosofttech.com:8088/}", configuration = OrderClientConfiguration.class)
public interface OrderResourceApiClient extends OrderResourceApi {
}