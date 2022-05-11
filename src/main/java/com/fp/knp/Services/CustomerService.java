package com.fp.knp.Services;

import com.fp.knp.Models.Customer;
import com.fp.knp.Repositories.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customer;

    public CustomerService(CustomerRepository customer) { this.customer = customer; }

    public Iterable<Customer> getAllCustomers() { return this.customer.findAll(); }

    public Iterable<Customer> getCustomersByFullName(String fullName) {
        return this.customer.findByFullName(fullName);
    }

    public Customer getCustomerByRegistrationCode(String redistrationCode) {
        return this.customer.findByRegistrationCode(redistrationCode);
    }

    public Customer getCustomerByEmail(String email) {
        return this.customer.findByEmail(email);
    }

    public Iterable<Customer> getCustomersByTelephone(String telephone) {
        return this.customer.findByTelephone(telephone);
    }


    public Customer getCustomerById(Integer id) {
        try {
            return this.customer.findById(id).get();
        } catch (Exception e) {
            return null;
        }
    }

    public Customer createCustomer(Customer customer) { return this.customer.save(customer); }

}
