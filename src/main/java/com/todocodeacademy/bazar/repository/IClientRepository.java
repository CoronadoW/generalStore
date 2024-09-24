
package com.todocodeacademy.bazar.repository;

import com.todocodeacademy.bazar.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IClientRepository extends JpaRepository<Client, Long>{
    
}
