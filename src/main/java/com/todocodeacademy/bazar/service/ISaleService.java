package com.todocodeacademy.bazar.service;

import com.todocodeacademy.bazar.dto.SaleDto;
import com.todocodeacademy.bazar.model.Product;
import com.todocodeacademy.bazar.model.Sale;
import java.time.LocalDate;
import java.util.List;

public interface ISaleService {

    public Sale createSale(Sale sale);

    public Sale getSale(Long saleCode);
    
    public List<Sale> getAllSales();

    public String deleteSale(Long saleCode);

    public Sale editSale(Long saleCode, Sale sale);

    public List<Product> productsBySale(Long saleCode);

    public int numberOfSalesByDate(LocalDate localDate);

    public Double totalRevenueByDate(LocalDate localDate);

    public List<Sale> salesToday();
    
    public SaleDto getBetterSale();
    
    public Double totalBySale(Long saleCode);

}
