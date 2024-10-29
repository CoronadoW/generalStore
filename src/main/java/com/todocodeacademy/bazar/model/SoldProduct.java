package com.todocodeacademy.bazar.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "SoldProduct",
        indexes = {
            @Index(name = "idxProductCode", columnList = "productCode")})
public class SoldProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private Long productCode;
    @Column(nullable = false)
    private String nameSold;
    @Column(nullable = false)
    private String brandSold;
    @Column(nullable = false)
    private int qtySold;
    @Column(nullable = false)
    private Double costSold;
    @Column(nullable = false)
    private Double totalBySoldProduct;    
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fk_saleCode")
    private Sale sale;

    public SoldProduct() {
    }

    public SoldProduct(Long id, Long productCode, String nameSold, String brandSold, int qtySold, Double costSold, Double totalBySoldProduct, Sale sale) {
        this.id = id;
        this.productCode = productCode;
        this.nameSold = nameSold;
        this.brandSold = brandSold;
        this.qtySold = qtySold;
        this.costSold = costSold;
        this.totalBySoldProduct = totalBySoldProduct;
        this.sale = sale;
    }

    @Override
    public String toString() {
        return "SoldProduct{" + "id=" + id + ", productCode=" + productCode + ", nameSold=" + nameSold + ", brandSold=" + brandSold + ", qtySold=" + qtySold + ", costSold=" + costSold + ", totalBySoldProduct=" + totalBySoldProduct + ", sale=" + sale + '}';
    }

}
