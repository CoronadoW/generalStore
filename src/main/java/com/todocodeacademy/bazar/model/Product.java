package com.todocodeacademy.bazar.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Product {

    @Id
    private Long productCode;
    private String name;
    private String brand;
    private Double cost;
    private Double quantityAvailable;

    public Product() {
    }

    public Product(Long productCode, String name, String brand, Double cost, Double quantityAvailable) {
        this.productCode = productCode;
        this.name = name;
        this.brand = brand;
        this.cost = cost;
        this.quantityAvailable = quantityAvailable;
    }
    
    

}
