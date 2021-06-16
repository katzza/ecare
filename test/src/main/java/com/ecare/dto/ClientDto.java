package com.ecare.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Data
public class ClientDto {
    private int clientId;
    private UserDto user;

    @NotBlank
    @Size(min =1, message ="Name length must be greater than 1")
    private String name;

    @NotBlank
    @Size(min =1, message ="Surname length must be greater than 1")
    private String surname;

    @Past
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthdate;

    @NotBlank
    private String passport;

    private String address;

    private List<ContractDto> clientContracts;
}
