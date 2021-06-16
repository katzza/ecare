package com.ecare.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Client.findByUserEmail",
                query = "SELECT c FROM ClientEntity c where c.user.email = :email"),
        @NamedQuery(name = "Client.findByPhone",
                query = "SELECT c FROM ClientEntity c where c.clientId  = (SELECT t.clientByClientId.clientId FROM ContractEntity t where t.phoneNumber = :phoneNumber)")
})

@Table(name = "client", schema = "public", catalog = "eCare")
public class ClientEntity implements Serializable {

    @Id
    @Column(name = "client_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int clientId;

    @Column(name = "name", length = 30)
    private String name;

    @Column(name = "surname", length = 30)
    private String surname;

    @Column(name = "birthdate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

    @Column(name = "passport_number", length = 10)
    private String passport;

    @Column(name = "address")
    private String address;

  /*  @Column(name = "user_id")
    private int userId;*/

/* @OneToOne(fetch = FetchType.LAZY, cascade =CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity userByUserId = new UserEntity();*/

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clientByClientId")
    private List<ContractEntity> clientContracts = new ArrayList<>(); //1 Client - Many Contracts

    public ClientEntity(String name, String surname, LocalDate birthdate, String passport, String address, UserEntity user) {
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.passport = passport;
        this.address = address;
        this.user = user;
    }

    public ClientEntity(UserEntity user) {
        this.user = user;
    }
}
