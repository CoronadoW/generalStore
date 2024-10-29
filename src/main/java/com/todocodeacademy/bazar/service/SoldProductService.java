
package com.todocodeacademy.bazar.service;

import com.todocodeacademy.bazar.exception.SoldProductNotFoundException;
import com.todocodeacademy.bazar.model.SoldProduct;
import com.todocodeacademy.bazar.repository.ISoldProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SoldProductService implements ISoldProductService{
    
    private final ISoldProductRepository iSoldProdRepo;

    @Autowired
    public SoldProductService(ISoldProductRepository iSoldProdRepo) {
        this.iSoldProdRepo = iSoldProdRepo;
    }

    @Override
    public SoldProduct createSoldProduct(SoldProduct soldProduct) {
        return iSoldProdRepo.save(soldProduct);
    }

    @Override
    public SoldProduct getSoldProduct(Long id) {
        return iSoldProdRepo.findById(id)
                .orElseThrow(() -> new SoldProductNotFoundException("Sold Product Not Found"));
    }

    @Override
    public List<SoldProduct> getAllSoldProduct() {
        return iSoldProdRepo.findAll();
    }
    
    
}
