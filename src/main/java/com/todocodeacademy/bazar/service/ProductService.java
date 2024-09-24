
package com.todocodeacademy.bazar.service;

import com.todocodeacademy.bazar.exception.ProductAlreadyExistsException;
import com.todocodeacademy.bazar.exception.ProductNotFoundException;
import com.todocodeacademy.bazar.model.Product;
import com.todocodeacademy.bazar.repository.IProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements IProductService {

    private final IProductRepository iProdRepo;
    
    @Autowired
    public ProductService(IProductRepository iProdRepo) {
        this.iProdRepo = iProdRepo;
    }

    @Override
    public Product createProduct(Product product) {
        if (iProdRepo.existsById(product.getProductCode())) {
            throw new ProductAlreadyExistsException("Product already exists with that product code");
        }
        return iProdRepo.save(product);
    }

    @Override
    public Product getProduct(Long productCode) {
        return iProdRepo.findById(productCode)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    @Override
    public List<Product> getAllProducts() {
        return iProdRepo.findAll();
    }

    @Override
    public List<Product> stockAtMinimun() {
        return iProdRepo.findAll().stream()
                .filter(prod -> prod.getQuantityAvailable() < 5)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProduct(Long productCode) {
        if (!iProdRepo.existsById(productCode)) {
            throw new ProductNotFoundException("Product not found with this product code");
        }
        iProdRepo.deleteById(productCode);
    }

    @Override
    public Product editProduct(Long productCode, Product product) {
        Product prod = iProdRepo.findById(productCode)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        prod.setName(product.getName());
        prod.setBrand(product.getBrand());
        prod.setCost(product.getCost());
        prod.setQuantityAvailable(product.getQuantityAvailable());
        return iProdRepo.save(prod);
    }
}
