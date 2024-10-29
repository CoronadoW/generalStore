package com.todocodeacademy.bazar.service;

import com.todocodeacademy.bazar.dto.RequestSaleDto;
import com.todocodeacademy.bazar.model.Sale;
import com.todocodeacademy.bazar.model.SoldProduct;
import java.time.LocalDate;
import java.util.List;

public interface ISaleService {

    public Sale createSale(RequestSaleDto reqSaleDto);

    public Sale getSale(Long saleCode);

    public List<Sale> getAllSales();

    public String deleteSale(Long saleCode);

    public Sale editSale(Sale sale);

    public List<SoldProduct> soldProductsBySale(Long saleCode);

    public int numberOfSalesByDate(LocalDate localDate);

    public Double totalRevenueByDate(LocalDate localDate);

    public List<Sale> salesByDate(LocalDate localDate);

    public List<Sale> salesToday();

    public Sale getBetterSale();

    public Double totalBySale(Long saleCode);
}
