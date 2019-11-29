package com.diviso.graeshoppe.client.customer.api;

import org.springframework.cloud.openfeign.FeignClient;
import com.diviso.graeshoppe.client.customer.ClientConfiguration;

@FeignClient(name="${customer.name:customer}", url="${customer.url:dev.ci1.divisosofttech.com:8088/}", configuration = ClientConfiguration.class)
public interface FavouriteStoreResourceApiClient extends FavouriteStoreResourceApi {
}