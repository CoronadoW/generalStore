
package com.todocodeacademy.bazar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler (SaleAlreadyExistsException.class)
    public ResponseEntity<String> saleAlreadyExistsExceptionHandler ( SaleAlreadyExistsException exep){
        return new ResponseEntity(exep.getMessage(), HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler (SaleNotFoundException.class)
    public ResponseEntity<String> saleNotFoundExceptionHandler( SaleNotFoundException saleNot){
        return new ResponseEntity( saleNot.getMessage(), HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<String> productAlreadyExistsExceptionHandler(ProductAlreadyExistsException prodAlreExcep){
        return new ResponseEntity<>(prodAlreExcep.getMessage(), HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> productNotFoundExceptionHandler(ProductNotFoundException prodNotFoundExcep){
        return new ResponseEntity<>(prodNotFoundExcep.getMessage(), HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(ClientAlreadyExistsException.class)
    public ResponseEntity<String> clientAlreadyExistsExceptionHandler(ClientAlreadyExistsException clientExcep){
        return new ResponseEntity<>(clientExcep.getMessage(), HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<String> clientNotFoundExceptionHandler(ClientNotFoundException clientNotFoundExcep){
        return new ResponseEntity<>( clientNotFoundExcep.getMessage(), HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(ProductStockNotEnoughException.class)
    public ResponseEntity<String> productStockNotEnoughHandler(ProductStockNotEnoughException notEnoughExcep){
        return new ResponseEntity<>( notEnoughExcep.getMessage(), HttpStatus.CONFLICT);
    }

}
