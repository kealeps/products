package com.products.app.products.models.repository;

import com.products.app.productscommons.models.entity.Product;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long>{
    
}
