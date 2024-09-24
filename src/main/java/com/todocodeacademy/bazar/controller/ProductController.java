
package com.todocodeacademy.bazar.controller;

import com.todocodeacademy.bazar.model.Product;
import com.todocodeacademy.bazar.service.IProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    IProductService iProdServ;

    @PostMapping("/product/create")
    public ResponseEntity<String> createProduct(@RequestBody Product product) {
        iProdServ.createProduct(product);
        return new ResponseEntity<>("Product " + product.getName() + " was created succesfully", HttpStatus.CREATED);
    }

    @GetMapping("/product/get/{productCode}")
    public ResponseEntity<Product> getProduct(@PathVariable Long productCode) {
        return new ResponseEntity(iProdServ.getProduct(productCode), HttpStatus.OK);
    }

    @GetMapping("/product/getAll")
    public ResponseEntity<List<Product>> getAllProduct() {
        return new ResponseEntity(iProdServ.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/badStock")
    public ResponseEntity<List<Product>> stockAtMinimun() {
        return new ResponseEntity(iProdServ.stockAtMinimun(), HttpStatus.OK);
    }

    @DeleteMapping("/product/delete/{productCode}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productCode) {
        iProdServ.deleteProduct(productCode);
        return new ResponseEntity("Product deleted", HttpStatus.NO_CONTENT);
    }

    @PutMapping("/product/edit/{productCode}")
    public ResponseEntity<String> editProduct(@PathVariable Long productCode,
            @RequestBody Product product) {
        iProdServ.editProduct(productCode, product);
        return new ResponseEntity("Product " + product.getName() + " was edited", HttpStatus.NO_CONTENT);
    }
} 
