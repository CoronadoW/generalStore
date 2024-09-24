
package com.todocodeacademy.bazar.exception;

public class ProductAlreadyExistsException extends RuntimeException{
    
    public ProductAlreadyExistsException(String message){
        super(message);
    }
}
