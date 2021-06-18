package com.ecare.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class HotTariffDto {
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
}
