
package com.todocodeacademy.bazar.exception;

public class SoldProductNotFoundException extends RuntimeException{
    
    public SoldProductNotFoundException(String message){
        super(message);
    }
}
