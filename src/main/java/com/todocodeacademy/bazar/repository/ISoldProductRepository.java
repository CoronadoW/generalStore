
package com.todocodeacademy.bazar.repository;

import com.todocodeacademy.bazar.model.SoldProduct;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ISoldProductRepository extends JpaRepository <SoldProduct, Long>{
    
}
