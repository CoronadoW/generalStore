
package com.todocodeacademy.bazar.repository;

import com.todocodeacademy.bazar.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<Product , Long>{
    Product findByProductCode(Long productCode);
}
