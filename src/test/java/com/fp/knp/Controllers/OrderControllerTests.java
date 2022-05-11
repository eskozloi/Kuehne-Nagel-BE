package com.fp.knp.Controllers;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllOrders() throws Exception {
        mockMvc.perform(get("/order"))
                .andExpect(status().isOk());
    }

    @Test
    void getOrderById() throws Exception {
        mockMvc.perform(get("/order/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3));
    }

    @Test
    void getOrderLinesByOrderId() throws Exception {
        mockMvc.perform(get("/order/1/line"))
                .andExpect(status().isOk());
    }

    @Test
    void createOrder() throws Exception {
        mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerId\":\"1\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void getAllOrderLines() throws Exception {
        mockMvc.perform(get("/order/line"))
                .andExpect(status().isOk());
    }

    @Test
    void getOrderLineById() throws Exception {
        mockMvc.perform(get("/order/line/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2));
    }

    @Test
    void updateOrderLineById() throws Exception {
        mockMvc.perform(put("/order/line/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"quantity\":\"10\"}"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.quantity").value(10));
    }

    @Test
    void createOrderLine() throws Exception {
        mockMvc.perform(post("/order/line")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\":\"3\",\"quantity\":\"24\",\"orderId\":\"2\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void getAllOrdersWithFilter() throws Exception {
        String filter = "{\"order\":{\"submissionDate\":\""+Date.valueOf(LocalDate.now().format(DateTimeFormatter.ISO_DATE))+"\"}," +
                "\"orderLine\":{\"productId\":\"1\",\"quantity\":[\"2\",\"96\"]}," +
                "\"customer\":{\"fullName\":\"Esko\"}," +
                "\"product\":{\"unitPrice\":[\"100\",\"1000\"]}}";
        mockMvc.perform(get("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filter))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").value(3));
    }
}