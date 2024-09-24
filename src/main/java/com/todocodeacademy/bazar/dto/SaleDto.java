
package com.todocodeacademy.bazar.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SaleDto {
    
    private Long saleCode;
    private Double total;
    private int numberOfProducts;
    private String clientName;
    private String clientLastName;   

    public SaleDto() {
    }

    public SaleDto(Long saleCode, Double total, int numberOfProducts, String clientName, String clientLastName) {
        this.saleCode = saleCode;
        this.total = total;
        this.numberOfProducts = numberOfProducts;
        this.clientName = clientName;
        this.clientLastName = clientLastName;
    }

}
