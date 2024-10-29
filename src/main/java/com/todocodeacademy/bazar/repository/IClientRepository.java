
package com.todocodeacademy.bazar.repository;

import com.todocodeacademy.bazar.model.Client;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IClientRepository extends JpaRepository<Client, Long>{
    
   boolean existsByDni(String dni);
    
   Optional<Client> findByDni(String dni);
   
}
