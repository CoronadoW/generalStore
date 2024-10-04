
package com.todocodeacademy.bazar.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;
    
    //@NotNull(message = "The name cannot be null")
    //@Size(min = 2, max = 15, message = "The name must be between 2 and 15 characters")
    private String name;
    //@NotNull(message = "The last name cannot be null")
    //@Size(min = 2, max = 30, message = "The last name must be between 2 and 30 characters")
    private String lastName;
    //@NotNull(message = "The Dni cannot be null")
    //@Pattern(regexp = "\\d{8}" , message = "DNI must have 8 digits")
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
