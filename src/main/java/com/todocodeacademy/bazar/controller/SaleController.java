
package com.todocodeacademy.bazar.controller;

import com.todocodeacademy.bazar.dto.SaleDto;
import com.todocodeacademy.bazar.model.Product;
import com.todocodeacademy.bazar.model.Sale;
import com.todocodeacademy.bazar.service.ISaleService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaleController {
    @Autowired
    ISaleService iSaleServ;
    
    @PostMapping("/sale/create")
    public ResponseEntity<String> createSale( @RequestBody Sale sale){
       Sale createdSale = iSaleServ.createSale(sale);
       return new ResponseEntity("Sale created succesfully", HttpStatus.CREATED);
    }
    
    @GetMapping ("/sale/get/{saleCode}")
    public ResponseEntity<Sale> getSale(@PathVariable Long saleCode){
        return new ResponseEntity<>(iSaleServ.getSale(saleCode), HttpStatus.OK);
    }
    
    @GetMapping("sale/getall")
    public ResponseEntity<List<Sale>> getAllSales(){
        return new ResponseEntity<>(iSaleServ.getAllSales(), HttpStatus.OK);
    }
    
    @DeleteMapping("/sale/delete/{saleCode}")
    public  ResponseEntity<String> deleteSale(@PathVariable Long saleCode){
        iSaleServ.deleteSale(saleCode);
        return new ResponseEntity<>("Sale deleted succesfully", HttpStatus.OK);
    }
    
    @PutMapping("/sale/edit/{saleCode}")
    public ResponseEntity<String> editSale(@PathVariable Long saleCode ,@RequestBody Sale sale){
        iSaleServ.editSale(saleCode, sale);
        return new ResponseEntity<>("Sale with sale code " + sale.getSaleCode()+" was edited succesfully", HttpStatus.OK);
    }
    
    @GetMapping ("/sale/getproducts/{saleCode}")
    public ResponseEntity<List<Product>> getProductsBySale(@PathVariable Long saleCode){
        return new ResponseEntity<>(iSaleServ.productsBySale(saleCode), HttpStatus.OK);
    }
    
    @GetMapping("sale/totalandamountofsales/{localDate}")
    public ResponseEntity<String> totalAndAmountofSalesbyDate(@PathVariable LocalDate localDate){
        return new ResponseEntity<>("The number of sales on " + localDate + " was " + iSaleServ.numberOfSalesByDate(localDate) + " and the total reveneu was " + iSaleServ.totalSaleByDate(localDate), HttpStatus.OK);
    }
    
    @GetMapping("/sale/productsnumber/{localDate}")
    public ResponseEntity<String> numberOfSalesByDate(@PathVariable LocalDate localDate){
        int quantSales = iSaleServ.numberOfSalesByDate(localDate);
        return new ResponseEntity<>("In this date were did " + quantSales + " sales.", HttpStatus.OK);
    }
    
    @GetMapping("/sale/totalbydate/{localDate}")
    public ResponseEntity<String> totalSaleByDate(@PathVariable LocalDate localDate){
        return new ResponseEntity<>("The total of the sales in this date id : " + iSaleServ.totalSaleByDate(localDate), HttpStatus.OK);
    }
    
    @GetMapping("/sale/getsalestoday")
    public ResponseEntity<List<Sale>> salesToday(){
        return new ResponseEntity<>(iSaleServ.salesToday(), HttpStatus.OK);
    }
    
    @GetMapping ("/sale/better")
    public ResponseEntity<SaleDto> betterSale(){
        return new ResponseEntity<>(iSaleServ.getBetterSale(), HttpStatus.OK);
    }
    
    @GetMapping("/sale/totalbysale/{saleCode}")
    public ResponseEntity<String> totalBySale(@PathVariable Long saleCode){
        return new ResponseEntity<>("The revenue for this sale is: " + iSaleServ.totalBySale(saleCode), HttpStatus.OK);
    }
}