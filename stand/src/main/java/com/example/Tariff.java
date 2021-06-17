package com.example;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.ManagedBean;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ManagedBean
public class Tariff implements Serializable {
    private String name;
    private String description;
    private int price;
}
