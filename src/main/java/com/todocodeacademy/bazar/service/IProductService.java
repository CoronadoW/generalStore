package com.todocodeacademy.bazar.service;

import com.todocodeacademy.bazar.model.Product;
import java.util.List;

public interface IProductService {

    public Product createProduct(Product product);

    public Product getProduct(Long productCode);

    public List<Product> getAllProducts();

    public List<Product> stockAtMinimun();

    public void deleteProduct(Long productCode);

    public Product editProduct(Long productCode, Product product);

}
