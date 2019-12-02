package com.diviso.graeshoppe.client.customer.api;

import org.springframework.cloud.openfeign.FeignClient;
import com.diviso.graeshoppe.client.customer.CustomerClientConfiguration;

@FeignClient(name="${customer.name:customer}", url="${customer.url}", configuration = CustomerClientConfiguration.class)
public interface StateResourceApiClient extends StateResourceApi {
}