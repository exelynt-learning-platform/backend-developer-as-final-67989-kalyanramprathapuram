package com.dto.resource;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ResourceRequest {

    @NotBlank(message = "Resource name is required")
    private String name;

    @NotBlank(message = "Resource type is required")
    private String type;

    @NotBlank(message = "Resource description is required")
    private String description;

    @NotNull(message = "Available status is required")
    private Boolean available;

    // Default constructor
    public ResourceRequest() {
    }

    // Constructor
    public ResourceRequest(
            String name,
            String type,
            String description,
            Boolean available) {

        this.name = name;
        this.type = type;
        this.description = description;
        this.available = available;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}