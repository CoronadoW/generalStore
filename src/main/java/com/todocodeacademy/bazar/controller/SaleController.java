
package com.todocodeacademy.bazar.controller;

import com.todocodeacademy.bazar.dto.RequestSaleDto;
import com.todocodeacademy.bazar.model.Product;
import com.todocodeacademy.bazar.model.Sale;
import com.todocodeacademy.bazar.model.SoldProduct;
import com.todocodeacademy.bazar.service.ISaleService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sale")
public class SaleController {
    
    private final ISaleService iSaleServ;
    
    @Autowired
    public SaleController(ISaleService iSaleServ) {
        this.iSaleServ = iSaleServ;
    }
    
    @PostMapping("/create")
    public ResponseEntity<Sale> createSale(@Valid @RequestBody RequestSaleDto reqSaleDto){
       Sale createdSale = iSaleServ.createSale(reqSaleDto);
       return new ResponseEntity<>(createdSale, HttpStatus.CREATED);
    }

    @GetMapping ("/get/{saleCode}")
    public ResponseEntity<Sale> getSale(@PathVariable Long saleCode){
        return new ResponseEntity<>(iSaleServ.getSale(saleCode), HttpStatus.OK);
    }
    
    @GetMapping("/getall")
    public ResponseEntity<List<Sale>> getAllSales(){
        return new ResponseEntity<>(iSaleServ.getAllSales(), HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{saleCode}")
    public  ResponseEntity<String> deleteSale(@PathVariable Long saleCode){
        iSaleServ.deleteSale(saleCode);
        return new ResponseEntity<>("Sale deleted succesfully", HttpStatus.OK);
    }
    
    @PutMapping("/edit")
    public ResponseEntity<String> editSale(@Valid @RequestBody Sale sale){
        iSaleServ.editSale( sale);
        return new ResponseEntity<>("Sale with sale code " + sale.getSaleCode()+" was edited succesfully", HttpStatus.OK);
    }
    
    @GetMapping ("/getsoldproductsbysale/{saleCode}")
    public ResponseEntity<List<SoldProduct>> getSoldProductsBySale(@PathVariable Long saleCode){
        return new ResponseEntity<>(iSaleServ.soldProductsBySale(saleCode), HttpStatus.OK);
    }
    
    @GetMapping("/salesqtyandrevenuebydate/{localDate}")
    public ResponseEntity<String> totalAndAmountofSalesbyDate(@PathVariable LocalDate localDate){
        return new ResponseEntity<>("The number of sales on " + localDate + " was " + iSaleServ.numberOfSalesByDate(localDate) + " and the total reveneu was " + iSaleServ.totalRevenueByDate(localDate), HttpStatus.OK);
    }
    
    @GetMapping("/numberofsalesbydate/{localDate}")
    public ResponseEntity<String> numberOfSalesByDate(@PathVariable LocalDate localDate){
        int quantSales = iSaleServ.numberOfSalesByDate(localDate);
        return new ResponseEntity<>("In this date were did " + quantSales + " sales.", HttpStatus.OK);
    }
    
    @GetMapping("/totalrevenuebydate/{localDate}")
    public ResponseEntity<String> totalRevenueByDate(@PathVariable LocalDate localDate){
        return new ResponseEntity<>("The total revenue in " + localDate +" is : " + iSaleServ.totalRevenueByDate(localDate), HttpStatus.OK);
    }
    
    @GetMapping("/getsalestoday")
    public ResponseEntity<List<Sale>> salesToday(){
        return new ResponseEntity<>(iSaleServ.salesToday(), HttpStatus.OK);
    }
    
    @GetMapping ("/salesbydate/{localDate}")
    public ResponseEntity <List<Sale>> getSalesByDate(@PathVariable LocalDate localDate){
        return new ResponseEntity<>(iSaleServ.salesByDate(localDate), HttpStatus.OK);
    }
    @GetMapping ("/bettersale")
    public ResponseEntity<Sale> betterSale(){
        return new ResponseEntity<>(iSaleServ.getBetterSale(), HttpStatus.OK);
    }
    
    @GetMapping("/totalbysale/{saleCode}")
    public ResponseEntity<String> totalBySale(@PathVariable Long saleCode){
        return new ResponseEntity<>("The revenue for this sale is: " + iSaleServ.totalBySale(saleCode), HttpStatus.OK);
    }
        
}
