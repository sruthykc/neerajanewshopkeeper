package com.diviso.graeshoppe.client.report.api;

import org.springframework.cloud.openfeign.FeignClient;
import com.diviso.graeshoppe.client.report.ReportClientConfiguration;

@FeignClient(name="${report.name:report}", url="${report.url}", configuration = ReportClientConfiguration.class)
public interface ReportCommandResourceApiClient extends ReportCommandResourceApi {
}