package com.diviso.graeshoppe.client.store.api;

import org.springframework.cloud.openfeign.FeignClient;
import com.diviso.graeshoppe.client.store.ClientConfiguration;

@FeignClient(name="${store.name:store}", url="${store.url:dev.ci1.divisosofttech.com:8071/}", configuration = ClientConfiguration.class)
public interface ReplyResourceApiClient extends ReplyResourceApi {
}