package com.ecare.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class TariffDto {

    private int tariffId;

    @NotNull
    @NotEmpty
    @NotBlank
    private String tariffName;

    @NotNull
    @NotEmpty
    @NotBlank
    private String tariffDescription;

    @NotNull
    private int price;

    @NotNull
    private boolean isBaseTariff;

    private int callsOptionId;
    private String callsOptionNames;
    private Map<String, Integer> callsOptions = new HashMap<>();
    private OptionDto callsOption;

    private int internetOptionId;
    private String internetOptionNames;
    private Map<String, Integer> internetOptions = new HashMap<>();
    private OptionDto internetOption;

    private int travelOptionId;
    private String travelOptionName;
    private Map<String, Integer> travelOptions = new HashMap<>();
    private OptionDto travelOption;

    private List <Integer> multipleOptionIds;
    private String multipleOptionName;
    private Map<String, Integer> multipleOptions = new HashMap<>();
    private List<OptionDto> multipleOptionDtos = new ArrayList<>();

    private List <Integer> freeOptionIds;
    private String freeOptionName;
    private Map<String, Integer> freeOptions = new HashMap<>();
    private List<OptionDto> freeOptionDtos = new ArrayList<>();

}
