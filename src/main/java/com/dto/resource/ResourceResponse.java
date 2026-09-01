package com.dto.resource;

public class ResourceResponse {

    private Long id;

    private String name;

    private String type;

    private String description;

    private Boolean available;

    // Default constructor
    public ResourceResponse() {
    }

    // Constructor
    public ResourceResponse(
            Long id,
            String name,
            String type,
            String description,
            Boolean available) {

        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.available = available;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}