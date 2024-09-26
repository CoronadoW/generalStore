package com.todocodeacademy.bazar.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Product {

    @Id    
    @NotNull(message = "The product code cannot be null")
    private Long productCode;
    
    @NotNull(message = "The name cannot be null")
    @Size(min = 2, max = 20, message = "The name must be between 2 and 20 characters")
    private String name;
    
    @NotNull(message = "The brand cannot be null")
    @Size(min = 2, max = 20, message = "The brand must be between 2 and 20 characters")
    private String brand;
    
    @NotNull(message = "The cost cannot be null")    
    private Double cost;
    
    @NotNull (message = "The quantity cannot be null")
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
