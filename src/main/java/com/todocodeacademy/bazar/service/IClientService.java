
package com.todocodeacademy.bazar.service;

import com.todocodeacademy.bazar.model.Client;
import java.util.List;

public interface IClientService {
    
    public Client createClient(Client client);
    
    public Client getClient(Long clientId);
    
    public List<Client> getAllClients();
    
    public void deleteClient(Long clientId);
    
    public Client editClient(Long clientId, Client client);
}
