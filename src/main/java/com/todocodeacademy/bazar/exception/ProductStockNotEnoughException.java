
package com.todocodeacademy.bazar.exception;

public class ProductStockNotEnoughException extends RuntimeException{
    
    public ProductStockNotEnoughException(String message){
        super(message);
    }
    
}
