
package com.todocodeacademy.bazar.controller;

import com.todocodeacademy.bazar.model.Client;
import com.todocodeacademy.bazar.service.IClientService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class ClientController {
    
    private final IClientService iClientServ;
    
    @Autowired
    public ClientController(IClientService iClientServ) {
        this.iClientServ = iClientServ;
    }
    
    @PostMapping("/create")
    public ResponseEntity<String> createClient(@RequestBody Client client){
        iClientServ.createClient(client);
        return new ResponseEntity<>("Client created succesfully", HttpStatus.CREATED);
    }
    
    @GetMapping("/getclient/{clientId}")
    public ResponseEntity<Client> getClient(@PathVariable Long clientId){
        return new ResponseEntity<>(iClientServ.getClient(clientId), HttpStatus.OK);
    }
    
    @GetMapping("/getall")
    public ResponseEntity<List<Client>> getAllClients(){
        return new ResponseEntity<>(iClientServ.getAllClients(), HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{clientId}")
    public ResponseEntity<String> deleteClient(@PathVariable Long clientId){
        iClientServ.deleteClient(clientId);
        return new ResponseEntity<>("Client deleted succesfully", HttpStatus.OK);
    }
    
    @PutMapping("/edit/{clientId}")
    public ResponseEntity<String> editClient(@PathVariable Long clientId,
            @RequestBody Client client){        
        return new ResponseEntity<>("The client : " + iClientServ.editClient(clientId, client) + " was edited succesfully", HttpStatus.OK);
    }
    
}
