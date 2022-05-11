package com.fp.knp.Services;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fp.knp.Models.Customer;
import com.fp.knp.Models.Order;
import com.fp.knp.Models.OrderLine;
import com.fp.knp.Models.Product;
import com.fp.knp.Repositories.CustomerRepository;
import com.fp.knp.Repositories.OrderLineRepository;
import com.fp.knp.Repositories.OrderRepository;
import com.fp.knp.Repositories.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.*;
import java.util.stream.StreamSupport;

@Service
public class OrderService {
    private final OrderRepository order;
    private final OrderLineRepository orderLine;

    // Temporary solution
    private final CustomerRepository customer;
    private final ProductRepository product;

    public OrderService(OrderRepository order, OrderLineRepository orderLine, CustomerRepository customer, ProductRepository product) {
        this.order = order;
        this.orderLine = orderLine;
        this.customer = customer;
        this.product = product;
    }

    public Iterable<Order> getAllOrders(ObjectNode json) {
        Iterable<Order> orders = this.order.findAll();
        if(json != null) {
            ArrayList<Order> orderList = new ArrayList<>(StreamSupport.stream(orders.spliterator(), false).toList());
            Iterator<String> objects = json.fieldNames();
            while(objects.hasNext()) {
                String object = objects.next();
                Iterator<String> sortOptions = json.get(object).fieldNames();
                if(object.equals("order")) {
                    while (sortOptions.hasNext()) {
                        String sortOption = sortOptions.next();
                        Iterator<Order> orderIterator = orders.iterator();
                        if(sortOption.equals("id")) {
                            while (orderIterator.hasNext()) {
                                Order order = orderIterator.next();
                                if(order.getId() == json.get(object).get(sortOption).asInt()) { continue; }
                                orderList.remove(order);
                            }
                        }
                        else if(sortOption.equals("customerId")) {
                           while (orderIterator.hasNext()) {
                               Order order = orderIterator.next();
                               if(order.getCustomerId() == json.get(object).get(sortOption).asInt()) { continue; }
                               orderList.remove(order);
                           }
                        }
                        else if(sortOption.equals("submissionDate")) {

                            while(orderIterator.hasNext()) {
                                Order order = orderIterator.next();
                                if(order.getSubmissionDate().equals(Date.valueOf(json.get(object).get(sortOption).asText()))) { continue; }
                                orderList.remove(order);
                            }
                        }
                    }
                }
                else if(object.equals("orderLine")){
                    while (sortOptions.hasNext()) {
                        String sortOption = sortOptions.next();
                        ArrayList<Order> objsToDelete = new ArrayList<>();
                        if(sortOption.equals("id")) {
                            OrderLine orderLine = this.getOrderLineById(json.get(object).get(sortOption).asInt());
                            if(orderLine == null) { orderList.clear(); continue; }
                            Iterator<Order> orderIterator = orders.iterator();
                            while (orderIterator.hasNext()) {
                                Order order = orderIterator.next();
                                if(Objects.equals(order.getId(), orderLine.getOrderId())) { break; }
                                orderList.remove(order);
                            }
                        }
                        else if(sortOption.equals("productId")) {
                            Iterable<OrderLine> orderLines = this.getOrderLinesByProductId(json.get(object).get(sortOption).asInt());
                            objsToDelete = this.filterOutput("orderLine", orderLines, orders);
                        }
                        else if(sortOption.equals("quantity")) {
                            Iterable<OrderLine> orderLines;
                            if(json.get(object).get(sortOption).isArray()) { orderLines = this.getOrderLinesByQuantityRange(json.get(object).get(sortOption).get(0).asInt(), json.get(object).get(sortOption).get(1).asInt()); }
                            else { orderLines = this.getOrderLinesByQuantity(json.get(object).get(sortOption).asInt()); }
                            objsToDelete = this.filterOutput("orderLine", orderLines, orders);
                        }
                        else if(sortOption.equals("orderId")) {
                            Iterable<OrderLine> orderLines = this.getOrderLinesByOrderId(json.get(object).get(sortOption).asInt());
                            objsToDelete = this.filterOutput("orderLine", orderLines, orders);
                        }
                        deleteFromOutput(orderList, objsToDelete);
                    }
                }
                else if(object.equals("customer")) {
                    CustomerService customerService = new CustomerService(this.customer);
                    while (sortOptions.hasNext()) {
                        String sortOption = sortOptions.next();
                        ArrayList<Order> objsToDelete = new ArrayList<>();
                        if (sortOption.equals("id")) {
                            Iterator<Order> orderIterator = orders.iterator();
                            while (orderIterator.hasNext()) {
                                Order order = orderIterator.next();
                                if(order.getCustomerId() == json.get(object).get(sortOption).asInt()) { continue; }
                                orderList.remove(order);
                            }
                        }
                        else if(sortOption.equals("registrationCode")) {
                            Customer customer = customerService.getCustomerByRegistrationCode(json.get(object).get(sortOption).asText());
                            dellByConcreteDataC(orders, orderList, customer);
                        }
                        else if(sortOption.equals("fullName")) {
                            Iterable<Customer> customers = customerService.getCustomersByFullName(json.get(object).get(sortOption).asText());
                            objsToDelete = this.filterOutput("customer", customers, orders);
                        }
                        else if(sortOption.equals("email")) {
                            Customer customer = customerService.getCustomerByEmail(json.get(object).get(sortOption).asText());
                            dellByConcreteDataC(orders, orderList, customer);
                        }
                        else if(sortOption.equals("phoneNumber")) {
                            Iterable<Customer> customers = customerService.getCustomersByTelephone(json.get(object).get(sortOption).asText());
                            objsToDelete = this.filterOutput("customer", customers, orders);
                        }
                        deleteFromOutput(orderList, objsToDelete);
                    }
                }
                else if(object.equals("product")) {
                    ProductService productService = new ProductService(this.product);
                    while (sortOptions.hasNext()) {
                        String sortOption = sortOptions.next();
                        ArrayList<Order> objsToDelete = new ArrayList<>();
                        if (sortOption.equals("id")) {
                            Iterable<OrderLine> orderLines = this.getOrderLinesByProductId(json.get(object).get(sortOption).asInt());
                            objsToDelete = this.filterOutput("orderLine", orderLines, orders);
                        }
                        else if(sortOption.equals("name")) {
                            Iterable<Product> products = productService.getProductsByName(json.get(object).get(sortOption).asText());
                            ArrayList<OrderLine> orderLines = getOrderLinesFromProducts(orders, products);
                            objsToDelete = this.filterOutput("orderLine", orderLines, orders);
                        }
                        else if(sortOption.equals("skuCode")) {
                            Product product = productService.getProductBySkuCode(json.get(object).get(sortOption).asText());
                            Iterable<OrderLine> orderLines = this.getOrderLinesByProductId(product.getId());
                            objsToDelete = this.filterOutput("orderLine", orderLines, orders);
                        }
                        else if(sortOption.equals("unitPrice")) {
                            Iterable<Product> products;
                            if(json.get(object).get(sortOption).isArray()) { products = productService.getProductsByUnitPriceRange(json.get(object).get(sortOption).get(0).asDouble(), json.get(object).get(sortOption).get(1).asDouble()); }
                            else { products = productService.getProductsByUnitPrice(json.get(object).get(sortOption).asDouble()); }
                            ArrayList<OrderLine> orderLines = getOrderLinesFromProducts(orders, products);
                            objsToDelete = this.filterOutput("orderLine", orderLines, orders);
                        }
                        deleteFromOutput(orderList, objsToDelete);
                    }
                }
            }
            return orderList;
        }
        return orders;
    }

    // Filters output of orderList
    private ArrayList<Order> filterOutput(String objName, Iterable<?> objLines, Iterable<Order> orders){
        Map<Order, Integer> deletionQueue = new HashMap<>();
        int Iterations = 0;

        ArrayList<Order> objsToDelete = new ArrayList<>();

        Iterator<?> objLinesIterator = objLines.iterator();
        if(!objLinesIterator.hasNext()) { return null; }
        while (objLinesIterator.hasNext()) {
            Object objLine = objLinesIterator.next();
            Iterations++;
            Iterator<Order> orderIterator = orders.iterator();
            while (orderIterator.hasNext()) {
                Order order = orderIterator.next();
                if(this.compareOIds(order, objLine, objName)) { continue; }
                deletionQueue.merge(order, 1, Integer::sum);
            }
        }
        int finalIterations = Iterations;
        deletionQueue.forEach((key, value) -> { if(value == finalIterations) { objsToDelete.add(key); } });
        return objsToDelete;
    }

    // Temporary method to compare Order [...].id with another object [...].id
    // Must be replaced in the future with a better solution
    private boolean compareOIds(Order order, Object obj, String objType) {
        if(objType.equals("orderLine")) {
            if(Objects.equals(order.getId(), ((OrderLine) obj).getOrderId())) { return true; }
        }
        else if(objType.equals("customer")) {
            if(Objects.equals(order.getCustomerId(), ((Customer) obj).getId())) { return true; }
        }
        return false;
    }

    // Deletes objects from orderList
    private void deleteFromOutput(ArrayList<Order> orderList, ArrayList<Order> objsToDelete) {
        if(objsToDelete == null) { orderList.clear();
            return;
        }
        Iterator<Order> objsToDeleteIterator = objsToDelete.iterator();
        while(objsToDeleteIterator.hasNext()) {
            Order order = objsToDeleteIterator.next();
            orderList.remove(order);
        }
    }

    // Returns orderLines from products
    private ArrayList<OrderLine> getOrderLinesFromProducts(Iterable<Order> orders, Iterable<Product> products) {
        ArrayList<Order> objsToDelete;
        Iterator<Product> productIterator = products.iterator();
        ArrayList<OrderLine> orderLines = new ArrayList<>();
        while(productIterator.hasNext()) {
            Product product = productIterator.next();
            this.getOrderLinesByProductId(product.getId()).forEach(orderLines::add);
        }
        return orderLines;
    }

    // Returns orderLines by productId
    private void dellByConcreteDataC(Iterable<Order> orders, ArrayList<Order> orderList, Customer customer) {
        if(customer == null) { orderList.clear();
            return;
        }
        Iterator<Order> orderIterator = orders.iterator();
        while (orderIterator.hasNext()) {
            Order order = orderIterator.next();
            if(Objects.equals(order.getCustomerId(), customer.getId())) { continue; }
            orderList.remove(order);
        }
    }

    public Iterable<Order> getOrdersBySubmissionDate(Date date) {
        return this.order.findByDate(date);
    }

    public Iterable<Order> getOrdersByCustomerId(Integer productId) {
        return this.order.findByCustomerId(productId);
    }

    public Order getOrderById(@PathVariable Integer id) {
        try {
            return this.order.findById(id).get();
        } catch (Exception e) {
            return null;
        }
    }

    public Iterable<OrderLine> getOrderLinesByOrderId(@PathVariable Integer id) {
        try {
            return this.orderLine.findByOrderId(id);
        } catch (Exception e) {
            return null;
        }
    }

    public Order createOrder(Order order) { return this.order.save(order); }

    public Iterable<OrderLine> getAllOrderLines() { return this.orderLine.findAll(); }

    public OrderLine getOrderLineById(Integer id) {
        try {
            return this.orderLine.findById(id).get();
        } catch (Exception e) {
            return null;
        }
    }

    public OrderLine updateOrderLineById(Integer id, ObjectNode json) {
        OrderLine orderLine = this.getOrderLineById(id);
        if(orderLine != null && json != null) {
            Iterator<String> objects = json.fieldNames();
            while (objects.hasNext()) {
                String object = objects.next();
                if(object.equals("productId")) {
                    orderLine.setProductId(json.get("productId").asInt());
                    return this.orderLine.save(orderLine);
                }
                else if(object.equals("quantity")) {
                    orderLine.setQuantity(json.get("quantity").asInt());
                    return this.orderLine.save(orderLine);
                }
                else if(object.equals("orderId")) {
                    orderLine.setOrderId(json.get("orderId").asInt());
                    return this.orderLine.save(orderLine);
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
    }

    public Iterable<OrderLine> getOrderLinesByProductId(Integer productId) {
        return this.orderLine.findByProductId(productId);
    }

    public Iterable<OrderLine> getOrderLinesByQuantity(Integer quantity) {
        return this.orderLine.findByQuantity(quantity);
    }

    public Iterable<OrderLine> getOrderLinesByQuantityRange(Integer quantityRangeFrom, Integer quantityRangeTo) {
        if(quantityRangeFrom > quantityRangeTo) { Integer tmpVar = quantityRangeFrom; quantityRangeFrom = quantityRangeTo; quantityRangeTo = tmpVar; }
        return this.orderLine.findByQuantityRange(quantityRangeFrom, quantityRangeTo);
    }

    public OrderLine createOrderLine(OrderLine orderLine) { return this.orderLine.save(orderLine); }
}
