package com.fp.knp.Repositories;

import com.fp.knp.Models.OrderLine;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLineRepository extends CrudRepository<OrderLine, Integer> {
    @Query("SELECT ol FROM OrderLine ol WHERE ol.orderId = :orderId")
    Iterable<OrderLine> findByOrderId(Integer orderId);

    @Query("SELECT ol FROM OrderLine ol WHERE ol.productId = :productId")
    Iterable<OrderLine> findByProductId(Integer productId);

    @Query("SELECT ol FROM OrderLine ol WHERE ol.quantity = :quantity")
    Iterable<OrderLine> findByQuantity(Integer quantity);

    @Query("SELECT ol FROM OrderLine ol WHERE ol.quantity BETWEEN :from AND :to")
    Iterable<OrderLine> findByQuantityRange(Integer from, Integer to);

    /*@Query("UPDATE OrderLine SET quantity = :quantity WHERE id = :id")
    Iterable<OrderLine> updateQuantity( Integer id, Double quantity);*/
}
