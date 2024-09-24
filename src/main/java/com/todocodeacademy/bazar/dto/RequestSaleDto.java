
package com.todocodeacademy.bazar.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RequestSaleDto {
    
    private LocalDate saleDateDto;
    private Long clientIdDto;
    private List<ProductsDto> prodsDtoList;

    public RequestSaleDto() {
    }

    public RequestSaleDto(LocalDate saleDateDto, Long clientIdDto, List<ProductsDto> prodsDtoList) {
        this.saleDateDto = saleDateDto;
        this.clientIdDto = clientIdDto;
        this.prodsDtoList = prodsDtoList;
    }
    
    

      
}
