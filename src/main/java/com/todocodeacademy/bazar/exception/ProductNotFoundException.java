
package com.todocodeacademy.bazar.exception;

public class ProductNotFoundException extends RuntimeException{
    
    public ProductNotFoundException(String message){
        super(message);
    }
    
}
