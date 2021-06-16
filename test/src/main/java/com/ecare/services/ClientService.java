package com.ecare.services;

import com.ecare.dao.ClientDAO;
import com.ecare.domain.ClientEntity;
import com.ecare.domain.UserEntity;
import com.ecare.dto.ClientDto;
import com.ecare.dto.ContractDto;
import com.ecare.dto.UserDto;
import com.ecare.error.UserAlreadyExistException;
import com.ecare.error.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClientService {
    @Autowired
    ClientDAO clientDAO;
    @Autowired
    ContractService contractService;
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
    public Optional<String> saveClient(ClientDto client) {
        clientDAO.save(convertToEntity(client));
        return Optional.empty();
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
        List<ClientEntity> clientByUserEmail = clientDAO.findClientByUserEmail(email);
        if (clientByUserEmail.size() == 0) {
            throw new UserNotFoundException("There is no client with that email: "
                    + email);
        } else {
            ClientDto client = convertToDto(clientByUserEmail.get(0));
          /*  List<ContractDto> contracts = contractService.getContractsByClientId(client.getClientId());
            client.setClientContracts(contracts);*/
            log.info(client.toString());
            return client;
        }
    }

    @Transactional
    public ClientDto findByPhone(String phoneNumber) {
        List<ClientEntity> clientByPhoneNumber = clientDAO.findClientByPhone(phoneNumber);
        if (clientByPhoneNumber.size() == 0) {
            throw new UserNotFoundException("There is no client with that phone number: "
                    + phoneNumber);
        } else {
            ClientDto client = convertToDto(clientByPhoneNumber.get(0));
            log.info(client.toString());
            return client;
        }
    }

    @Transactional
    public ClientDto findById(int id) {
        try {
            ClientEntity clientEntity = (ClientEntity) clientDAO.findById(id);
            ClientDto client = convertToDto(clientEntity);
            return client;
        } catch (UserNotFoundException ex) {
            ClientEntity client = new ClientEntity();
            return convertToDto(client);
        }
    }

    private ClientDto convertToDto(ClientEntity clientEntity) {
        ClientDto client = modelMapper.map(clientEntity, ClientDto.class);
      /*  if (client.user == null) {
            client.setUser(userService.convertToDto(clientEntity.getUser()));
        }*/
       /* List<ContractDto> contracts = contractService.getContractsByClientId(client.getClientId());
        client.setClientContracts(contracts);*/
        return client;
    }

    private ClientEntity convertToEntity(ClientDto clientDto) {
        return modelMapper.map(clientDto, ClientEntity.class);
    }

}
