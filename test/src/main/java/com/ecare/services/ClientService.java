package com.ecare.services;

import com.ecare.dao.ClientDAO;
import com.ecare.domain.ClientEntity;
import com.ecare.domain.UserEntity;
import com.ecare.dto.ClientDto;
import com.ecare.error.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClientService {
    @Autowired
    ClientDAO clientDAO;
    @Autowired
    ContractFacade contractFacade;
    @Autowired
    UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public List<ClientDto> getAllClients() {
        List<ClientEntity> clients = clientDAO.findAll();
        return clients.stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveClient(ClientDto client) {
        clientDAO.save(convertToEntity(client));
    }

    @Transactional
    public void updateClient(ClientDto client) {
        ClientEntity clientFromDb = (ClientEntity) clientDAO.findById(client.getClientId());
        int clientId = clientFromDb.getClientId();
        UserEntity user = clientFromDb.getUser();
        ClientEntity clientUpdated = convertToEntity(client);
        clientUpdated.setUser(user);
        clientUpdated.setClientId(clientId);
        clientDAO.update(clientUpdated);
    }

    @Transactional
    public ClientDto findClientByUserEmail(String email) {
        return clientDAO.findClientByUserEmail(email).stream()     //todo rewrite methods
                .findFirst()
                .map(this::getClientDto)
                .orElseThrow(() -> new UserNotFoundException(String.format("There is no client with that email:%s", email)));
    }

    private ClientDto getClientDto(ClientEntity client) {
        log.info(client.toString());
        return convertToDto(client);
    }


    @Transactional
    public ClientDto findByPhone(String phoneNumber) {
        return clientDAO.findClientByPhone(phoneNumber).stream()
                .findFirst()
                .map(this::getClientDto)
                .orElseThrow(() -> new UserNotFoundException(String.format("There is no client with that phone number:%s", phoneNumber)));
    }

    @Transactional
    public ClientDto findById(int id) {
        try {
            ClientEntity clientEntity = (ClientEntity) clientDAO.findById(id);
            return convertToDto(clientEntity);
        } catch (UserNotFoundException ex) {
            ClientEntity client = new ClientEntity();
            return convertToDto(client);
        }
    }

    private ClientDto convertToDto(ClientEntity clientEntity) {
        /*  if (client.user == null) {
            client.setUser(userService.convertToDto(clientEntity.getUser()));
        }*/
       /* List<ContractDto> contracts = contractService.getContractsByClientId(client.getClientId());
        client.setClientContracts(contracts);*/
        return modelMapper.map(clientEntity, ClientDto.class);
    }

    private ClientEntity convertToEntity(ClientDto clientDto) {
        return modelMapper.map(clientDto, ClientEntity.class);
    }


    public void createClient(UserEntity userEntity) {
        ClientEntity clientEntity = new ClientEntity(userEntity);
        clientDAO.save(clientEntity);
    }

}
