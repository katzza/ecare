package com.ecare.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class NumberDto {
    private int id;
    @NotNull
    @NotBlank
    @Size(min =7, max =10, message ="Phonenumber length must be greater than 1")
    private String phoneNumber;
}
