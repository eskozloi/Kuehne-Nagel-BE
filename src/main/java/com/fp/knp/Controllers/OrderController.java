package com.fp.knp.Controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fp.knp.Models.Order;
import com.fp.knp.Models.OrderLine;
import com.fp.knp.Services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/order", headers = "Accept=application/json")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public Iterable<Order> getAllOrders(@RequestBody(required = false) ObjectNode json) {
        return orderService.getAllOrders(json);
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Integer id) {
        return orderService.getOrderById(id);
    }

    @GetMapping("/{id}/line")
    public Iterable<OrderLine> getOrderLinesByOrderId(@PathVariable Integer id) {
        return orderService.getOrderLinesByOrderId(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Order createOrder(@Valid @RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @GetMapping("/line")
    public Iterable<OrderLine> getAllOrderLines() {
        return orderService.getAllOrderLines();
    }

    @GetMapping("/line/{id}")
    public OrderLine getOrderLineById(@PathVariable Integer id) {
        return orderService.getOrderLineById(id);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/line/{id}")
    public OrderLine updateOrderLineById(@PathVariable Integer id, @RequestBody ObjectNode json) {
        System.out.println(id);
        return orderService.updateOrderLineById(id, json);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/line")
    public OrderLine createOrderLine(@Valid @RequestBody OrderLine orderLine) {
        return orderService.createOrderLine(orderLine);
    }
}
