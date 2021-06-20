package com.ecare.services;

import com.ecare.dao.ContractOptionDAO;
import com.ecare.domain.ContractOptionsEntity;
import com.ecare.domain.ContractEntity;
import com.ecare.domain.OptionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ContractOptionService {

    @Autowired
    ContractOptionDAO contractOptionDAO;

    @Transactional
    public void save(ContractOptionsEntity contractOption) {
        contractOptionDAO.save(contractOption);
    }

    public Optional<String> saveContractOptionInDataBase(ContractEntity contract, OptionEntity option, ContractFacade contractFacade) {
        if (contractsOptionCombinationAbsent(contract, option)) {
            ContractOptionsEntity contractOption = new ContractOptionsEntity();
            contractOption.setContractByContractId(contract);
            contractOption.setOptionByOptionId(option);
            save(contractOption);
            return Optional.empty();
        } else
            return Optional.of(String.format("Combination of tariff %s and option %s is already appear in the database", contract.getContractId(), option.getOptionName()));
    }


    private boolean contractsOptionCombinationAbsent(ContractEntity contract, OptionEntity option) {
        if (contractOptionDAO.findByContractAndOption(contract.getContractId(), option.getOptionId()).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
