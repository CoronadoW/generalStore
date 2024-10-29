
package com.todocodeacademy.bazar.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RequestSaleDto {
    
    //private Long clientIdDto;
    private String clientDniDto;
    private List<ProductsDto> prodsDtoList;

    public RequestSaleDto() {
    }

    public RequestSaleDto(String clientDniDto, List<ProductsDto> prodsDtoList) {
        this.clientDniDto = clientDniDto;
        this.prodsDtoList = prodsDtoList;
    }

    

    

    
    
    

      
}
