
package com.todocodeacademy.bazar.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long saleCode;
    private LocalDate saleDate;
    private Double total;
    @OneToOne
    @JoinColumn(name = "fk_clientId")
    private Client client;
    @ManyToMany 
    @JoinTable(name = "sale_producto",
            joinColumns = @JoinColumn(name = "saleCode"),
            inverseJoinColumns = @JoinColumn(name = "productCode"))
    private List<Product> productList;

    public Sale() {
    }

    public Sale(Long saleCode, LocalDate saleDate, Double total, Client client, List<Product> productList) {
        this.saleCode = saleCode;
        this.saleDate = saleDate;
        this.total = total;
        this.client = client;
        this.productList = productList;
    }

    
    
    
}
