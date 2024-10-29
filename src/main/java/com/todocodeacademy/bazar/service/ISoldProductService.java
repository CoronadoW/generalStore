
package com.todocodeacademy.bazar.service;

import com.todocodeacademy.bazar.model.SoldProduct;
import java.util.List;


public interface ISoldProductService {
    
    public SoldProduct createSoldProduct(SoldProduct soldProduct);
    
    public SoldProduct getSoldProduct(Long id);
    
    public List<SoldProduct> getAllSoldProduct();
    
}
