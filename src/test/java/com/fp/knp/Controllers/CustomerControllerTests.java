package com.fp.knp.Controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllCustomers() throws Exception {
        mockMvc.perform(get("/customer"))
                .andExpect(status().isOk());
    }

    @Test
    public void getCustomerById() throws Exception {
        mockMvc.perform(get("/customer/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2));
    }

    @Test
    public void createCustomer() throws Exception {
        mockMvc.perform(post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"registrationCode\":\"buxcode\"," +
                                "\"fullName\":\"Buxavich Oleh\"," +
                                "\"email\":\"bux@gmail.com\"," +
                                "\"telephone\":\"+37200001111\"}"))
                .andExpect(status().isCreated());
    }

}
