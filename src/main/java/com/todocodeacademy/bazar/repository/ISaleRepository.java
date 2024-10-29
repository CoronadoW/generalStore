
package com.todocodeacademy.bazar.repository;

import com.todocodeacademy.bazar.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISaleRepository extends JpaRepository<Sale, Long>{
    boolean existsBySaleCode(Long saleCode);
}
