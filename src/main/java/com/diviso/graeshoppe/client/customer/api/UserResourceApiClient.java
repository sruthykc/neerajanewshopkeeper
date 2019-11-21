package com.diviso.graeshoppe.client.customer.api;

import org.springframework.cloud.openfeign.FeignClient;
import com.diviso.graeshoppe.client.customer.ClientConfiguration;

@FeignClient(name="${customer.name:customer}", url="${customer.url}", configuration = ClientConfiguration.class)
public interface UserResourceApiClient extends UserResourceApi {
}