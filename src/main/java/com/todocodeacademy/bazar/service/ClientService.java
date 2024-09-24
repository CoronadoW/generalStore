
package com.todocodeacademy.bazar.service;
import com.todocodeacademy.bazar.exception.ClientAlreadyExistsException;
import com.todocodeacademy.bazar.exception.ClientNotFoundException;
import com.todocodeacademy.bazar.model.Client;
import com.todocodeacademy.bazar.repository.IClientRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService implements IClientService{
    
    private final IClientRepository iClientRepo;
    
    @Autowired
    public ClientService (IClientRepository iClientRepo){
        this.iClientRepo = iClientRepo;
    }

    @Override
    public Client createClient(Client client) {
        if(iClientRepo.existsById(client.getClientId())){
            throw new ClientAlreadyExistsException("Client already exists");
        }
        return iClientRepo.save(client);
    }

    @Override
    public Client getClient(Long clientId) {
        return iClientRepo.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException("Client not found"));
    }

    @Override
    public List<Client> getAllClients() {
        return iClientRepo.findAll();
    }

    @Override
    public void deleteClient(Long clientId) {
        if(!iClientRepo.existsById(clientId)){
            throw new ClientNotFoundException("Client not found");            
        }
        iClientRepo.deleteById(clientId);
    }

    @Override
    public Client editClient(Long clientId, Client client) {
        Client cli = iClientRepo.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException("Client not found"));
        cli.setName(client.getName());
        cli.setLastName(client.getLastName());
        cli.setDni(client.getDni());
        return iClientRepo.save(cli);
    }

   
}
