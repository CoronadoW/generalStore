
package com.todocodeacademy.bazar.exception;

public class ClientAlreadyExistsException extends RuntimeException{
    
    public ClientAlreadyExistsException(String message){
        super(message);
    }
}
