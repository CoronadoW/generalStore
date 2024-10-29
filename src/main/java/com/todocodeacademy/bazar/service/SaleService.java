package com.todocodeacademy.bazar.service;

import com.todocodeacademy.bazar.dto.ProductsDto;
import com.todocodeacademy.bazar.dto.RequestSaleDto;
import com.todocodeacademy.bazar.exception.ClientNotFoundException;
import com.todocodeacademy.bazar.exception.ProductNotFoundException;
import com.todocodeacademy.bazar.exception.ProductStockNotEnoughException;
import com.todocodeacademy.bazar.exception.SaleNotFoundException;
import com.todocodeacademy.bazar.model.Client;
import com.todocodeacademy.bazar.model.Product;
import com.todocodeacademy.bazar.model.Sale;
import com.todocodeacademy.bazar.model.SoldProduct;
import com.todocodeacademy.bazar.repository.IClientRepository;
import com.todocodeacademy.bazar.repository.IProductRepository;
import com.todocodeacademy.bazar.repository.ISaleRepository;
import com.todocodeacademy.bazar.repository.ISoldProductRepository;
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
    private final ISoldProductRepository iSoldProdRepo;

    @Autowired
    public SaleService(ISaleRepository iSaleRepo, IClientRepository iClientRepo, IProductRepository iProdRepo, ISoldProductRepository iSoldProdRepo) {
        this.iSaleRepo = iSaleRepo;
        this.iClientRepo = iClientRepo;
        this.iProdRepo = iProdRepo;
        this.iSoldProdRepo = iSoldProdRepo;
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
    @Transactional
    public String deleteSale(Long saleCode) {
        if (!iSaleRepo.existsById(saleCode)) {
            throw new SaleNotFoundException("Sale not found");
        }
        iSaleRepo.deleteById(saleCode);
        return "Sale deleted succesfully";
    }

    @Override
    @Transactional
    public Sale editSale(Sale sale) {
        Sale sal = iSaleRepo.findById(sale.getSaleCode())
                .orElseThrow(() -> new SaleNotFoundException("Sale not found"));
        sal.setClient(sale.getClient());
        sal.setSaleDate(sale.getSaleDate());
        sal.setSoldProductList(sale.getSoldProductList());
        sal.setTotalBySale(sale.getTotalBySale());
        return iSaleRepo.save(sal);
    }

    @Override
    public List<SoldProduct> soldProductsBySale(Long saleCode) {
        Sale sale = iSaleRepo.findById(saleCode)
                .orElseThrow(() -> new SaleNotFoundException("Sale not found"));
        return sale.getSoldProductList();
    }

    @Override
    public int numberOfSalesByDate(LocalDate localDate) {
        return this.salesByDate(localDate).size();
    }

    @Override
    public Double totalRevenueByDate(LocalDate localDate) {
        return this.salesByDate(localDate).stream()
                .mapToDouble(Sale::getTotalBySale)
                .sum();
    }

    @Override
    public List<Sale> salesByDate(LocalDate localDate) {
        return this.getAllSales().stream()
                .filter(sale -> sale.getSaleDate().equals(localDate))
                .collect(Collectors.toList());
    }

    @Override
    public List<Sale> salesToday() {
        return this.getAllSales().stream()
                .filter(sale -> sale.getSaleDate().equals(LocalDate.now()))
                .collect(Collectors.toList());
    }

    @Override
    public Sale getBetterSale() {
        List<Sale> salesList = this.getAllSales();
        Double betterTotal = 0.0;
        Sale betterSale = null;

        for (Sale sale : salesList) {
            if (sale.getTotalBySale() > betterTotal) {
                betterTotal = sale.getTotalBySale();
                betterSale = sale;
            }
        }
        if (betterSale == null) {
            throw new SaleNotFoundException("No sale found");
        }
        return betterSale;
    }

    @Override
    public Double totalBySale(Long saleCode) {
        Sale sale = iSaleRepo.findById(saleCode)
                .orElseThrow(() -> new SaleNotFoundException("Sale not found"));
        return sale.getTotalBySale();
    }

    //-------------------------------------------------------------------------------
    @Override
    @Transactional
    public Sale createSale(RequestSaleDto reqSaleDto) {
        Sale sale = new Sale();
        sale.setSaleDate(LocalDate.now());
        sale.setClient(this.getClientFromRequestBody(reqSaleDto));
        //iSaleRepo.save(sale);

        List<SoldProduct> soldProdList = this.createSoldProductsAndList(reqSaleDto, sale);
        sale.setSoldProductList(soldProdList);
        
        sale.setTotalBySale(this.getTotalBySale(soldProdList));
        
        iSaleRepo.save(sale);
        
        this.updateStock(soldProdList);
        return sale;
    }

    public List<SoldProduct> createSoldProductsAndList(RequestSaleDto reqSaleDto, Sale sale) {
        List<SoldProduct> soldProductsList = new ArrayList();
        List<ProductsDto> filteredByStockDtoList = this.getFilteredProdsByStock(reqSaleDto);

        Map<Long, Product> prodMap = iProdRepo.findAll().stream()
                .collect(Collectors.toMap(Product::getProductCode, Function.identity()));

        for (ProductsDto prodDto : filteredByStockDtoList) {
            Product prod = prodMap.get(prodDto.getProdCodeDto());
            if (prod == null) {
                throw new ProductNotFoundException("Product not found");
            }
            SoldProduct soldProd = new SoldProduct();
            soldProd.setProductCode(prod.getProductCode());
            soldProd.setNameSold(prod.getName());
            soldProd.setBrandSold(prod.getBrand());
            soldProd.setQtySold(prodDto.getRequiredQtyDto());
            soldProd.setCostSold(prod.getCost());
            soldProd.setTotalBySoldProduct(prod.getCost() * prodDto.getRequiredQtyDto());
            soldProd.setSale(sale);
            
            iSoldProdRepo.save(soldProd);
            soldProductsList.add(soldProd);
        }
        return soldProductsList;
    }

    public List<ProductsDto> getFilteredProdsByStock(RequestSaleDto reqSaleDto) {
        List<ProductsDto> filteredByStockDtoList = new ArrayList();
        Map<Long, Product> productMap = iProdRepo.findAll().stream()
                .collect(Collectors.toMap(Product::getProductCode, Function.identity()));

        for (ProductsDto prodDto : reqSaleDto.getProdsDtoList()) {
            Product prod = productMap.get(prodDto.getProdCodeDto());
            if (prodDto.getRequiredQtyDto() > prod.getQuantityAvailable()) {
                throw new ProductStockNotEnoughException("Stock not enough. The stock for this product is " + prod.getQuantityAvailable());
            }
            filteredByStockDtoList.add(prodDto);
        }
        return filteredByStockDtoList;
    }

    public Double getTotalBySale(List<SoldProduct> soldProdList) {
        return soldProdList.stream()
                .mapToDouble(SoldProduct::getTotalBySoldProduct)
                .sum();
    }

    //Traigo el cliente con el clientId del body de la Request
    public Client getClientFromRequestBody(RequestSaleDto reqSaleDto) {
        return iClientRepo.findByDni(reqSaleDto.getClientDniDto())
                .orElseThrow(() -> new ClientNotFoundException("Client not found"));
    }

    public void updateStock(List<SoldProduct> soldProdList) {
        List<Product> productsList = new ArrayList();
        Map<Long, Product> prodListMap = iProdRepo.findAll().stream()
                .collect(Collectors.toMap(Product::getProductCode, Function.identity()));
        for (SoldProduct soldProd : soldProdList) {
            Product prod = prodListMap.get(soldProd.getProductCode());
            if (prod == null) {
                throw new ProductNotFoundException("Product not found");
            }
            prod.setQuantityAvailable(prod.getQuantityAvailable() - soldProd.getQtySold());
            productsList.add(prod);
            //iProdRepo.save(prod);
        }
        iProdRepo.saveAll(productsList);
    }

}

/*
    public Double totalRevenueBySale(List<ProductsDto> prodDtoWithTotalList) {
        Double totalOfSale = 0.0;
        for (ProductsDto prodDto : prodDtoWithTotalList) {
            totalOfSale = totalOfSale + prodDto.g);
        }
        return totalOfSale;
    }*/
 /*
    public void updateStock(RequestSaleDto reqSaleDto, List<Product> filteredProdsList) {
        Map<Long, Product> productMap = filteredProdsList.stream()
                .collect(Collectors.toMap(Product::getProductCode, Function.identity()));

        for (ProductsDto prodDto : reqSaleDto.getProdsDtoList()) {
            Product product = productMap.get(prodDto.getProdCodeDto());
            if (product != null) {
                product.setQuantityAvailable(product.getQuantityAvailable() - prodDto.getRequiredQtyDto());
                iProdRepo.save(product);
            }
        }
    }*/

 /*Obtengo una lista filtrada de productos que se encuentran en la BD por id y que no exceden el stosk
    public List<Product> getFilteredProductsList(RequestSaleDto reqSaleDto) {
        //Traigo la lista de productosDto del body de la request
        List<ProductsDto> prodDtoList = reqSaleDto.getProdsDtoList();
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
    
    @Transactional
    @Override
    public Sale forCreationOfSale(RequestSaleDto reqSaleDto) {
        List<SoldProduct> soldProductList = new ArrayList();
        for(Product product : getFilteredProductsList(reqSaleDto)){
            SoldProduct soldProduct = new SoldProduct();
            soldProduct.setProductCode(product.getProductCode());
            soldProduct.setName(product.getName());
            soldProduct.setBrand(product.getBrand());
            soldProduct.setCost(product.getCost());
            soldProduct.setTotalBySale(product.getCost());//
            soldProductList.add(soldProduct);
        }
        
        Double total = totalRevenueBySale(getTotalByProduct(filteredProdsList, reqSaleDto));
        Client client = getClientFromRequestBody(reqSaleDto);
        //List<SoldProduct> filteredProdsList = getFilteredProductsList(reqSaleDto);        
        updateStock(reqSaleDto, filteredProdsList);

        Sale sale = new Sale();
        sale.setSaleDate(LocalDate.now());
        sale.setTotal(total);
        sale.setClient(client);
        sale.setSoldProductList(soldProductList);
        //sale.setSoldProductList(filteredProdsList);
        

        return this.createSale(sale);
    }

    //Obtengo el total de la venta desde la lista filtrada de productos
    public List<ProductsDto> getTotalByProduct(List<Product> filteredProdsList, RequestSaleDto reqSaleDto) {

        Map<Long, ProductsDto> prodDtoMap = reqSaleDto.getProdsDtoList().stream()
                .collect(Collectors.toMap(ProductsDto::getProdCodeDto, Function.identity()));

        List<ProductsDto> prodDtoWithTotalList = new ArrayList();
        Double totalByProduct;

        for (Product product : filteredProdsList) {
            ProductsDto prodDto = prodDtoMap.get(product.getProductCode());
            if (prodDto != null) {
                totalByProduct = product.getCost() * prodDto.getRequiredQtyDto();
                prodDto.setTotalByProductDto(totalByProduct);
                prodDtoWithTotalList.add(prodDto);
            }
        }
        return prodDtoWithTotalList;
    }
 */
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
    //Usando doble for, o for anidado
    public void updateStock(RequestSaleDto reqSaleDto, List<Product> filteredProdsList){
        for(ProductsDto prodDto : reqSaleDto.getProdsDtoList()){
            for(Product product : filteredProdsList){
                if(product.getProductCode().equals(prodDto.getProdCodeDto())){
                    product.setQuantityAvailable(product.getQuantityAvailable() - prodDto.getRequiredQtyDto());
                    iProdRepo.save(product);
                }
            }
        }
    }*/
