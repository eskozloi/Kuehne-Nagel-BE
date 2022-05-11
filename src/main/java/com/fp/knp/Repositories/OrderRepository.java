package com.fp.knp.Repositories;

import com.fp.knp.Models.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {
    @Query("SELECT o FROM Order o WHERE o.submissionDate = :date")
    Iterable<Order> findByDate(Date date);

    @Query("SELECT o FROM Order o WHERE o.customerId = :id")
    Iterable<Order> findByCustomerId(Integer id);
}
