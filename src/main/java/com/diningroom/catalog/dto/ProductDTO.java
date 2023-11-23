package com.diningroom.catalog.dto;

import com.diningroom.catalog.entitties.Product;

import java.math.BigDecimal;

public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Long brand_id;

    public ProductDTO(Long id, String name, String description, BigDecimal price, Long brand_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.brand_id = brand_id;
    }

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.brand_id = product.getBrand().getId();
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(Long brand_id) {
        this.brand_id = brand_id;
    }
}