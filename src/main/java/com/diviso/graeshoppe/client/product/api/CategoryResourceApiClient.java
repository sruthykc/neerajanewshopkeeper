package com.diviso.graeshoppe.client.product.api;

import org.springframework.cloud.openfeign.FeignClient;
import com.diviso.graeshoppe.client.product.ProductClientConfiguration;

@FeignClient(name="${product.name:product}", url="${product.url:dev.ci1.divisosofttech.com:8084/}", configuration = ProductClientConfiguration.class)
public interface CategoryResourceApiClient extends CategoryResourceApi {
}