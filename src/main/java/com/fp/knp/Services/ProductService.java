package com.fp.knp.Services;

import com.fp.knp.Models.Product;
import com.fp.knp.Repositories.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository product;

    public ProductService(ProductRepository product) { this.product = product; }

    public Iterable<Product> getAllProducts() { return this.product.findAll(); }

    public Iterable<Product> getProductsByName(String productName) {
        return this.product.findByName(productName);
    }

    public Product getProductBySkuCode(String skuCode) {
        return this.product.findBySkuCode(skuCode);
    }

    public Iterable<Product> getProductsByUnitPrice(double price) {
        return this.product.findByUnitPrice(price);
    }

    public Iterable<Product> getProductsByUnitPriceRange(Double unitPriceFrom, Double unitPriceTo) {
        if(unitPriceFrom > unitPriceTo) { Double tmpVar = unitPriceFrom; unitPriceFrom = unitPriceTo; unitPriceTo = tmpVar; }
        return this.product.findByUnitPriceRange(unitPriceFrom, unitPriceTo);
    }

    public Product getProductById(Integer id) {
        try{
            return this.product.findById(id).get();
        } catch (Exception e) {
            return null;
        }
    }

    public Product createProduct(Product product) { return this.product.save(product); }

}
