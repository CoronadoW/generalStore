
package com.todocodeacademy.bazar.controller;

import com.todocodeacademy.bazar.model.SoldProduct;
import com.todocodeacademy.bazar.service.ISoldProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/soldproduct")
public class SoldProductController {
    
    private final ISoldProductService iSoldProdServ;

    @Autowired
    public SoldProductController(ISoldProductService iSoldProdServ) {
        this.iSoldProdServ = iSoldProdServ;
    }
    
    @PostMapping ("/create")
    public ResponseEntity<SoldProduct> createSoldProduct(@RequestBody SoldProduct soldProduct){
        return new ResponseEntity<>(iSoldProdServ.createSoldProduct(soldProduct), HttpStatus.CREATED);
    }
    
    @GetMapping ("/getall")
    public ResponseEntity<List<SoldProduct>> getAllSoldProduct(){
        return new ResponseEntity(iSoldProdServ.getAllSoldProduct(), HttpStatus.OK);
    }
    
}
