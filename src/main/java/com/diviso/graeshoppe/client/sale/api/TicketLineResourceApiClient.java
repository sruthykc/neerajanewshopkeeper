package com.diviso.graeshoppe.client.sale.api;

import org.springframework.cloud.openfeign.FeignClient;
import com.diviso.graeshoppe.client.sale.SaleClientConfiguration;

@FeignClient(name="${sale.name:sale}", url="${sale.url:dev.ci1.divisosofttech.com:8086/}", configuration = SaleClientConfiguration.class)
public interface TicketLineResourceApiClient extends TicketLineResourceApi {
}