package com.quickship.logisticshub.controller;
import com.quickship.logisticshub.service.PackageService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AnalyticsController {

    private final PackageService service;

    public AnalyticsController(PackageService service) {
        this.service = service;
    }

    @PreAuthorize("hasRoleAny('MANAGER', 'DRIVER')")
    @GetMapping("/analytics/revenue")
    public Double getProjectedRevenue() {
        return service.getProjectedRevenue();
    }
}