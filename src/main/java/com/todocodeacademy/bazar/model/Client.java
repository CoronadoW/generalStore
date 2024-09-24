
package com.todocodeacademy.bazar.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long clientId;
    private String name;
    private String lastName;
    private String dni;
    

    public Client() {
    }

    public Client(Long clientId, String name, String lastName, String dni) {
        this.clientId = clientId;
        this.name = name;
        this.lastName = lastName;
        this.dni = dni;
    }
    
    
            
}
