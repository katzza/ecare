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
    public ClientDto findById(int clientId) {
        return convertToDto(findEntityById(clientId));
    }

    @Transactional
    public ClientEntity findEntityById(int clientId) {
        try {
            return (ClientEntity) clientDAO.findById(clientId);
        } catch (UserNotFoundException ex) {
            return new ClientEntity();
        }
    }

    public void createClient(UserEntity userEntity) {
        ClientEntity clientEntity = new ClientEntity(userEntity);
        clientDAO.save(clientEntity);
    }

    private ClientDto convertToDto(ClientEntity clientEntity) {
        ClientDto client = modelMapper.map(clientEntity, ClientDto.class);
        return client;
    }

    private ClientEntity convertToEntity(ClientDto clientDto) {
        return modelMapper.map(clientDto, ClientEntity.class);
    }



}
