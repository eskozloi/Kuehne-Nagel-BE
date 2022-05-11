package com.fp.knp.Models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "order_lines", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull(message = "Product id is required")
    @Column(name = "product_id")
    private Integer productId;

    @NotNull(message = "Quantity is required")
    @Column(name = "quantity")
    private Integer quantity;

    @NotNull(message = "Order id is required")
    @Column(name = "order_id")
    private Integer orderId;

    public OrderLine() {
    }

    public OrderLine(Integer productId, Integer quantity, Integer orderId) {
        this.productId = productId;
        this.quantity = quantity;
        this.orderId = orderId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
