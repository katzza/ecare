package com.ecare.services;

import com.ecare.dao.ClientDAO;
import com.ecare.dao.ContractDAO;
import com.ecare.dao.NumberDAO;
import com.ecare.dao.TariffDAO;
import com.ecare.domain.ClientEntity;
import com.ecare.domain.ContractEntity;
import com.ecare.domain.NumberEntity;
import com.ecare.domain.TariffEntity;
import com.ecare.dto.ContractDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;


@Slf4j
@Service
public class ContractService {

    @Autowired
    ContractDAO contractDAO;
    @Autowired
    ClientService clientService;
    @Autowired
    ClientDAO clientDAO;
    @Autowired
    TariffDAO tariffDAO;
    @Autowired
    NumberDAO numberDAO;
    @Autowired
    ModelMapper modelMapper;

    @Transactional
    public List<ContractEntity> getContractsByTariffId(int id) {
        return contractDAO.getContractsByTariffId(id);
    }

    @Transactional
    public ContractDto findById(int id) {
        return convertToDto((ContractEntity) contractDAO.findById(id));
    }

    @Transactional
    public void blockContractByClient(int id) {
        ContractEntity contract = (ContractEntity) contractDAO.findById(id);
        contract.setBlockedByUser(true);
        contractDAO.update(contract);
    }

    @Transactional
    public void unblockContractByClient(int id) {
        ContractEntity contract = (ContractEntity) contractDAO.findById(id);
        contract.setBlockedByUser(false);
        contractDAO.update(contract);
    }

    @Transactional
    public int blockContractByCompany(int id) {
        ContractEntity contract = (ContractEntity) contractDAO.findById(id);
        contract.setBlockedByCompany(true);
        contractDAO.update(contract);
        return contract.getClientByClientId().getClientId();
    }

    @Transactional
    public int unblockContractByCompany(int id) {
        ContractEntity contract = (ContractEntity) contractDAO.findById(id);
        contract.setBlockedByCompany(false);
        contractDAO.update(contract);
        return contract.getClientByClientId().getClientId();
    }

    @Transactional
    public void updateContract(ContractDto contractDto) {
        ContractEntity contract = (ContractEntity) contractDAO.findById(contractDto.getContractId());
        contract.setTariffByTariffId((TariffEntity) tariffDAO.findById(contractDto.getTariffId().getTariffId()));
        contractDAO.update(contract);
    }

    @Transactional
    public boolean isBlocked(ContractDto contractDto) {
        ContractEntity contract = (ContractEntity) contractDAO.findById(contractDto.getContractId());
        return contract.isBlockedByCompany() || contract.isBlockedByUser();
    }

    @Transactional
    public void showNumbers(ContractDto contractDto) {
        Map<String, Integer> mapNumbers = contractDto.getNumbers();
        numberDAO.getFreeNumbers().forEach(array -> mapNumbers.put((String) array[1], (Integer) array[0]));
    }

    @Transactional
    public void generateFreeNumbers() {
        if (numberDAO.getFreeNumbers().size() < 3) {
            ThreadLocalRandom random = ThreadLocalRandom.current();
            for (int i = 0; i < 3; i++) {
                int phoneNumber = random.nextInt(9000001) + 999999;
                NumberEntity newNumber = new NumberEntity(Integer.toString(phoneNumber));
                numberDAO.save(newNumber);
            }
        }
    }

    @Transactional
    public void showTariffandOptions(ContractDto contractDto) {
        Map<String, Integer> mapTariffs = contractDto.getTariffs();
        tariffDAO.getTariffNamesAndIds().forEach(array -> mapTariffs.put((String) array[1], (Integer) array[0]));
    }

    @Transactional
    public Optional<String> save(ContractDto contractDto) {
        ContractEntity contract = new ContractEntity();
        ClientEntity client = (ClientEntity) clientDAO.findById(contractDto.getClientId());
        contract.setClientByClientId(client);
        TariffEntity tariff = (TariffEntity) tariffDAO.findById(contractDto.getTariffId().getTariffId());
        contract.setTariffByTariffId(tariff);
        NumberEntity number = (NumberEntity) numberDAO.findById(contractDto.getPhoneNumber().getId());
        number.setBooked(true);
        numberDAO.update(number);
        contract.setPhoneNumber(number.getPhoneNumber());
        contractDAO.save(contract);
        return Optional.empty();
    }

    private ContractDto convertToDto(ContractEntity contractEntity) {
      /*  ContractDto contract = modelMapper.map(contractEntity, ContractDto.class);
        contract.setTariff(contract.getTariffId().getTariffId());
        return contract;*/
        return modelMapper.map(contractEntity, ContractDto.class);
    }

    private ContractEntity convertToEntity(ContractDto contractDto) {
      /*  ContractEntity contractEntity = modelMapper.map(contractDto, ContractEntity.class);
        contractEntity.setTariffByTariffId((TariffEntity) tariffDAO.findById(contractDto.getTariff()));*/
        return modelMapper.map(contractDto, ContractEntity.class);
    }


}
