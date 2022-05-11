package com.fp.knp.Repositories;

import com.fp.knp.Models.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:productName%")
    Iterable<Product> findByName(String productName);

    @Query("SELECT p FROM Product p WHERE p.skuCode = :skuCode")
    Product findBySkuCode(String skuCode);

    @Query("SELECT p FROM Product p WHERE p.unitPrice = :unitPrice")
    Iterable<Product> findByUnitPrice(Double unitPrice);

    @Query("SELECT p FROM Product p WHERE p.unitPrice BETWEEN :from AND :to")
    Iterable<Product> findByUnitPriceRange(Double from, Double to);
}
