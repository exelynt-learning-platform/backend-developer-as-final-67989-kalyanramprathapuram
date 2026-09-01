package com.service;

import java.util.List;

import com.dto.resource.ResourceRequest;
import com.dto.resource.ResourceResponse;

public interface ResourceService {

    ResourceResponse createResource(ResourceRequest request);

    List<ResourceResponse> getAllResources();

    ResourceResponse getResourceById(Long id);

    ResourceResponse updateResource(Long id, ResourceRequest request);

    void deleteResource(Long id);
}