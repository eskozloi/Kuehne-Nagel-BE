package com.fp.knp.Models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "products", uniqueConstraints = @UniqueConstraint(columnNames = {"id", "sku_code"}))
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @NotBlank(message = "SKU code is required")
    @Column(name = "sku_code")
    private String skuCode;

    @Column(name = "unit_price")
    private Double unitPrice;

    public Product() {
    }

    public Product(String name, String skuCode, Double unitPrice) {
        this.name = name;
        this.skuCode = skuCode;
        this.unitPrice = unitPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
