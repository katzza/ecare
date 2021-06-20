package com.ecare.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ContractDto {

    private int contractId;
    private TariffDto tariffId;
    private NumberDto phoneNumber;
    private boolean blockedByUser = false;
    private boolean blockedByCompany;
    private List<OptionDto> contractAddedOptions;

    private int clientId;
    private String clientEmail;
    private Map<String, Integer> tariffs = new HashMap<>();
    private Map<String, Integer> numbers = new HashMap<>();
    private Map<String, Integer> contractOptions = new HashMap<>();
    private List<Integer> selectedContractOptionIds;

  /*
    private TariffEntity tariffByTariffId; // many contracts by one Tariff
    private List<ContractOptionsEntity> contractAddedOptionsEntities = new ArrayList<>();*/

}
