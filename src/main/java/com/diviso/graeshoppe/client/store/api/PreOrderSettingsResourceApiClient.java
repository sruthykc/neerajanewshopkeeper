package com.diviso.graeshoppe.client.store.api;

import org.springframework.cloud.openfeign.FeignClient;
import com.diviso.graeshoppe.client.store.StoreClientConfiguration;

@FeignClient(name="${store.name:store}", url="${store.url}", configuration = StoreClientConfiguration.class)
public interface PreOrderSettingsResourceApiClient extends PreOrderSettingsResourceApi {
}