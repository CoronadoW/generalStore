
package com.todocodeacademy.bazar.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductsDto {
    
    private Long prodCodeDto;
    private int requiredQtyDto;

    public ProductsDto() {
    }

    public ProductsDto(Long prodCodeDto, int requiredQtyDto) {
        this.prodCodeDto = prodCodeDto;
        this.requiredQtyDto = requiredQtyDto;
    }
}

