package com.ecare.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class OptionDto {

    private int optionId;
    @NotNull
    @NotBlank
    @Size(min =1, max = 40, message ="Name length must be greater than 1")
    private String optionName;
    private int monthPrice;
    private int connectionPrice;
    @NotNull
    @NotBlank
    @Size(min =1, max = 60, message ="Description length must be greater than 1")
    private String description;
    private int baseOptionId;
    private String baseOptionName;
    private boolean isBase;
    private boolean isMulti;
/*    private List <Integer> additionalOptionIds;
    private String additionalOptionName;*/
    private Map<String, Integer> baseOptions = new HashMap<>();
   /* private Map<String, Integer> additionalOptions = new HashMap<>();
    private List<OptionDto> additionalOptionDtos = new ArrayList<>();*/
}
