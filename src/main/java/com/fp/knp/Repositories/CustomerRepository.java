package com.fp.knp.Repositories;

import com.fp.knp.Models.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    @Query("SELECT c FROM Customer c WHERE c.fullName LIKE %:name%")
    Iterable<Customer> findByFullName(String name);

    @Query("SELECT c FROM Customer c WHERE c.registrationCode = :redistrationCode")
    Customer findByRegistrationCode(String redistrationCode);

    @Query("SELECT c FROM Customer c WHERE c.email = :email")
    Customer findByEmail(String email);

    @Query("SELECT c FROM Customer c WHERE c.telephone = :telephone")
    Iterable<Customer> findByTelephone(String telephone);
}
