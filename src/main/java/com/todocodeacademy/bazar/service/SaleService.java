package com.todocodeacademy.bazar.service;

import com.todocodeacademy.bazar.dto.ProductsDto;
import com.todocodeacademy.bazar.dto.RequestSaleDto;
import com.todocodeacademy.bazar.dto.SaleDto;
import com.todocodeacademy.bazar.exception.ClientNotFoundException;
import com.todocodeacademy.bazar.exception.ProductNotFoundException;
import com.todocodeacademy.bazar.exception.ProductStockNotEnoughException;
import com.todocodeacademy.bazar.exception.SaleAlreadyExistsException;
import com.todocodeacademy.bazar.exception.SaleNotFoundException;
import com.todocodeacademy.bazar.model.Client;
import com.todocodeacademy.bazar.model.Product;
import com.todocodeacademy.bazar.model.Sale;
import com.todocodeacademy.bazar.repository.IClientRepository;
import com.todocodeacademy.bazar.repository.IProductRepository;
import com.todocodeacademy.bazar.repository.ISaleRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleService implements ISaleService {

    private final ISaleRepository iSaleRepo;
    private final IClientRepository iClientRepo;
    private final IProductRepository iProdRepo;

    @Autowired
    public SaleService(ISaleRepository iSaleRepo, IClientRepository iClientRepo, IProductRepository iProdRepo) {
        this.iSaleRepo = iSaleRepo;
        this.iClientRepo = iClientRepo;
        this.iProdRepo = iProdRepo;
    }

    @Override
    public Sale createSale(Sale sale) {
        if (iSaleRepo.existsById(sale.getSaleCode())) {
            throw new SaleAlreadyExistsException("Sale already exists");
        }
        return iSaleRepo.save(sale);
    }

    @Override
    public Sale getSale(Long saleCode) {
        return iSaleRepo.findById(saleCode)
                .orElseThrow(() -> new SaleNotFoundException("Sale not found"));
    }

    @Override
    public List<Sale> getAllSales() {
        return iSaleRepo.findAll();
    }

    @Override
    public String deleteSale(Long saleCode) {
        if (!iSaleRepo.existsById(saleCode)) {
            throw new SaleNotFoundException("Sale not found");
        }
        iSaleRepo.deleteById(saleCode);
        return "Sale deleted succesfully";
    }

    @Override
    public Sale editSale(Long saleCode, Sale sale) {
        Sale sal = iSaleRepo.findById(saleCode)
                .orElseThrow(() -> new SaleNotFoundException("Sale not found"));
        sal.setClient(sale.getClient());
        sal.setSaleDate(sale.getSaleDate());
        sal.setProductList(sale.getProductList());
        sal.setTotal(sale.getTotal());
        return iSaleRepo.save(sal);
    }

    @Override
    public List<Product> productsBySale(Long saleCode) {
        Sale sale = iSaleRepo.findById(saleCode)
                .orElseThrow(() -> new SaleNotFoundException("Sale not found"));
        return sale.getProductList();
    }

    @Override
    public int numberOfSalesByDate(LocalDate localDate) {
        List<Sale> salesList = this.getAllSales();
        int numberOfSales = 0;
        for (Sale sale : salesList) {
            if (localDate.isEqual(sale.getSaleDate())) {
                numberOfSales++;
            }
        }
        return numberOfSales;
    }

    @Override
    public Double totalRevenueByDate(LocalDate localDate) {
        List<Sale> salesList = this.getAllSales();
        Double total = 0.0;
        for (Sale sale : salesList) {
            if (sale.getSaleDate().isEqual(localDate)) {
                total = total + sale.getTotal();
            }
        }
        return total;
    }

    //Adicional
    @Override
    public List<Sale> salesToday() {
        List<Sale> salesList = this.getAllSales();
        List<Sale> salesToday = new ArrayList<>();
        for (Sale sale : salesList) {
            if (sale.getSaleDate().isEqual(LocalDate.now())) {
                salesToday.add(sale);
            }
        }
        return salesToday;
    }

    @Override
    public SaleDto getBetterSale() {
        List<Sale> salesList = this.getAllSales();
        Double betterTotal = 0.0;
        Sale betterSale = null;
        int numberProducts = 0;

        for (Sale sale : salesList) {
            if (sale.getTotal() > betterTotal) {
                betterTotal = sale.getTotal();
                betterSale = sale;
                numberProducts = sale.getProductList().size();
            }
        }
        if (betterSale == null) {
            throw new SaleNotFoundException("No sale found");
        }
        SaleDto saleDto = new SaleDto();
        saleDto.setSaleCode(betterSale.getSaleCode());
        saleDto.setTotal(betterTotal);
        saleDto.setNumberOfProducts(numberProducts);
        saleDto.setClientName(betterSale.getClient().getName());
        saleDto.setClientLastName(betterSale.getClient().getLastName());

        return saleDto;
    }

    @Override
    public Double totalBySale(Long saleCode) {
        Sale sale = iSaleRepo.findById(saleCode)
                .orElseThrow(() -> new SaleNotFoundException("Sale not found"));
        return sale.getTotal();
    }

    @Transactional
    public Sale forCreationOfSale(RequestSaleDto reqSaleDto) {
        LocalDate localDate = getLocalDateFromRequestBody(reqSaleDto);
        Client client = getClientFromRequestBody(reqSaleDto);
        List<Product> filteredProdsList = getFilteredProductsList(reqSaleDto);
        Double total = getTotalFromFilteredList(filteredProdsList);
        updateStock(reqSaleDto, filteredProdsList);

        Sale sale = new Sale();
        sale.setSaleDate(localDate);
        sale.setClient(client);
        sale.setProductList(filteredProdsList);
        sale.setTotal(total);

        return this.createSale(sale);
    }

    //Traigo el local date del body de la request
    public LocalDate getLocalDateFromRequestBody(RequestSaleDto reqSaleDto) {
        return reqSaleDto.getSaleDateDto();
    }

    //Traigo el cliente con el clientId del body de la Request
    public Client getClientFromRequestBody(RequestSaleDto reqSaleDto) {
        return iClientRepo.findById(reqSaleDto.getClientIdDto())
                .orElseThrow(() -> new ClientNotFoundException("Client not found"));
    }

    //Traigo la lista de productosDto del body de la request
    public List<ProductsDto> getProductsListFromRequest(RequestSaleDto reqSaleDto) {
        return reqSaleDto.getProdsDtoList();
    }

    //Obtengo una lista filtrada de productos que se encuentran en la BD por id y que no exceden el stosk
    public List<Product> getFilteredProductsList(RequestSaleDto reqSaleDto) {
        List<ProductsDto> prodDtoList = getProductsListFromRequest(reqSaleDto);
        //Creo una nueva lista de productos tipo Product para luego añadirles los productos que pasen los filtros
        List<Product> filteredProdsList = new ArrayList();
        //Comparo cada producto de la lista prodDtoList con los productos existentes, tanto su existencia como su cantidad disponible
        for (ProductsDto prodDto : prodDtoList) {
            Product product = iProdRepo.findById(prodDto.getProdCodeDto())
                    .orElseThrow(() -> new ProductNotFoundException("Producto not found"));
            if (product.getQuantityAvailable() < prodDto.getRequiredQtyDto()) {
                throw new ProductStockNotEnoughException("Stock not enough for this product");
            }
            //product.setQuantityAvailable(product.getQuantityAvailable() - prodDto.getRequiredQtyDto());
            filteredProdsList.add(product);
        }
        return filteredProdsList;
    }

    //Obtengo el total de la venta desde la lista filtrada de productos
    public Double getTotalFromFilteredList(List<Product> filteredProdsList) {
        Double total = 0.0;
        for (Product prod : filteredProdsList) {
            total = total + prod.getCost();
        }
        return total;
    }
    
    public void updateStock(RequestSaleDto reqSaleDto, List<Product> filteredProdsList){
        Map<Long, Product> productMap = filteredProdsList.stream()
                .collect(Collectors.toMap(Product::getProductCode, Function.identity()));
        for(ProductsDto prodDto : reqSaleDto.getProdsDtoList()){
            Product product = productMap.get(prodDto.getProdCodeDto());
            if(product != null){
                product.setQuantityAvailable(product.getQuantityAvailable() - prodDto.getRequiredQtyDto());
                iProdRepo.save(product);
            }
        }        
    }
    
    /*
    public void updateStock(RequestSaleDto reqSaleDto, List<Product> filteredProdsList) {
        //Convierto la lista filtrada de Productos en un HashMap, 
        //convirtiendo la lista en un Stream y se lo asigno al Map(HashMap)    
        Map<Long, Product> productMap = filteredProdsList.stream()
                //Agrego al Map, los pares Clave-Valor
                //Clave(Product :: getProductCode(tipo Long))
                //Valor (Function.identity() Como valor el mismo Producto, tipo Product
                .collect(Collectors.toMap(Product::getProductCode, Function.identity()));

        //Para cada productDto de la lista reqSaleDto(requestSaleDto)
        for (ProductsDto prodDto : reqSaleDto.getProdsDtoList()) {
            //Asigno a un objeto Product, un objeto de la productMap por su productCode
            //De esta manera cruzo la lista filteredProdList con la reqSaleDto, en lugar de un for detro de otro
            Product product = productMap.get(prodDto.getProdCodeDto());

            if (product != null) {
                product.setQuantityAvailable(product.getQuantityAvailable() - prodDto.getRequiredQtyDto());
                iProdRepo.save(product);

            }
        }
    }
    */

    /* public void updateStock(RequestSaleDto reqSaleDto, List<Product> filteredProdsList){
        for(ProductsDto prodDto : reqSaleDto.getProdsDtoList()){
            for(Product product : filteredProdsList){
                if(product.getProductCode().equals(prodDto.getProdCodeDto())){
                    product.setQuantityAvailable(product.getQuantityAvailable() - prodDto.getRequiredQtyDto());
                    iProdRepo.save(product);
                }
            }
        }
    }*/
}
