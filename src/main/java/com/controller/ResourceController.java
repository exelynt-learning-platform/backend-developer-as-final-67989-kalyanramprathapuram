package com.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dto.resource.ResourceRequest;
import com.dto.resource.ResourceResponse;
import com.service.ResourceService;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    // =========================================================
    // CREATE RESOURCE
    // POST /api/resources
    // =========================================================
    @PostMapping
    public ResponseEntity<ResourceResponse> createResource(
            @RequestBody ResourceRequest request) {

        ResourceResponse response =
                resourceService.createResource(request);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

    // =========================================================
    // GET ALL RESOURCES
    // GET /api/resources
    // =========================================================
    @GetMapping
    public ResponseEntity<List<ResourceResponse>> getAllResources() {

        List<ResourceResponse> resources =
                resourceService.getAllResources();

        return ResponseEntity.ok(resources);
    }

    // =========================================================
    // GET RESOURCE BY ID
    // GET /api/resources/1
    // =========================================================
    @GetMapping("/{id}")
    public ResponseEntity<ResourceResponse> getResourceById(
            @PathVariable Long id) {

        ResourceResponse response =
                resourceService.getResourceById(id);

        return ResponseEntity.ok(response);
    }

    // =========================================================
    // UPDATE RESOURCE
    // PUT /api/resources/1
    // =========================================================
    @PutMapping("/{id}")
    public ResponseEntity<ResourceResponse> updateResource(

            @PathVariable Long id,

            @RequestBody ResourceRequest request) {

        ResourceResponse response =
                resourceService.updateResource(
                        id,
                        request
                );

        return ResponseEntity.ok(response);
    }

    // =========================================================
    // DELETE RESOURCE
    // DELETE /api/resources/1
    // =========================================================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResource(
            @PathVariable Long id) {

        resourceService.deleteResource(id);

        return ResponseEntity.noContent().build();
    }
}