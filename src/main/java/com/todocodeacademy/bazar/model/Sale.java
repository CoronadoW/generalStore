
package com.todocodeacademy.bazar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    
    @NotNull(message = "The date cannot be null")
    private LocalDate saleDate;
    
    @NotNull(message = "The total cannot be null")
    private Double totalBySale;
    
    @ManyToOne
    @JoinColumn(name = "fk_clientId")
    @NotNull(message = "The client cannot be null")
    private Client client;
    
    
    @NotNull(message = "The list of products must not be null")
    @Size(min = 1, message = "The list must contain at least 1 product")
    @JsonManagedReference
    @ManyToMany 
    @JoinTable(name = "sale_soldProducto",
            joinColumns = @JoinColumn(name = "saleCode"),
            inverseJoinColumns = @JoinColumn(name = "productCode"))     
    private List<SoldProduct> soldProductList;

    public Sale() {
    }

    public Sale(Long saleCode, LocalDate saleDate, Double totalBySale, Client client, List<SoldProduct> soldProductList) {
        this.saleCode = saleCode;
        this.saleDate = saleDate;
        this.totalBySale = totalBySale;
        this.client = client;
        this.soldProductList = soldProductList;
    }

    @Override
    public String toString() {
        return "Sale{" + "saleCode=" + saleCode + ", saleDate=" + saleDate + ", totalBySale=" + totalBySale + ", client=" + client + ", soldProductList=" + soldProductList + '}';
    }

    

   
    
    
    
}
