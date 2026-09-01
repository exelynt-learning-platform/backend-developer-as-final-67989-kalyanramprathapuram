package com.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dto.resource.ResourceRequest;
import com.dto.resource.ResourceResponse;
import com.entity.Resource;
import com.exception.ResourceNotFoundException;
import com.repository.ResourceRepository;
import com.service.ResourceService;

@Service
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;

    public ResourceServiceImpl(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @Override
    public ResourceResponse createResource(ResourceRequest request) {

        Resource resource = new Resource();

        resource.setName(request.getName());
        resource.setType(request.getType());
        resource.setDescription(request.getDescription());
        resource.setAvailable(request.isAvailable());

        Resource savedResource = resourceRepository.save(resource);

        return convertToResponse(savedResource);
    }

    @Override
    public List<ResourceResponse> getAllResources() {

        return resourceRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    public ResourceResponse getResourceById(Long id) {

        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Resource not found with id: " + id
                        )
                );

        return convertToResponse(resource);
    }

    @Override
    public ResourceResponse updateResource(
            Long id,
            ResourceRequest request) {

        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Resource not found with id: " + id
                        )
                );

        resource.setName(request.getName());
        resource.setType(request.getType());
        resource.setDescription(request.getDescription());
        resource.setAvailable(request.isAvailable());

        Resource updatedResource =
                resourceRepository.save(resource);

        return convertToResponse(updatedResource);
    }

    @Override
    public void deleteResource(Long id) {

        if (!resourceRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Resource not found with id: " + id
            );
        }

        resourceRepository.deleteById(id);
    }

    private ResourceResponse convertToResponse(Resource resource) {

        ResourceResponse response = new ResourceResponse();

        response.setId(resource.getId());
        response.setName(resource.getName());
        response.setType(resource.getType());
        response.setDescription(resource.getDescription());
        response.setAvailable(resource.isAvailable());

        return response;
    }
}