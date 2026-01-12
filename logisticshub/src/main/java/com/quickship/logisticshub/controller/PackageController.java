package com.quickship.logisticshub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.quickship.logisticshub.model.Packages;
import com.quickship.logisticshub.service.PackageService;


@RestController
@RequestMapping("/api/v1/packages")
public class PackageController {

    private final PackageService service;

    public PackageController(PackageService service) {
        this.service = service;
    }

    @Operation(
        summary = "Add a new package",
        description = "Creates a new package entry in the system"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Package created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid package data"),
        @ApiResponse(responseCode = "409", description = "Package already exists")
    })
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<Packages> addPackage(@RequestBody Packages pkg) {

         Packages createdPkg = service.addPackage(pkg);

        // return 202 Accepted with package details
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(createdPkg);
    }

}
