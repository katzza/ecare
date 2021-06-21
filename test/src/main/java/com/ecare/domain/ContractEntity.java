package com.ecare.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "contract", schema = "public", catalog = "eCare")
@NamedQueries({
        @NamedQuery(name = "Contract.findByClientId",
                query = "SELECT c FROM ContractEntity c where c.clientByClientId.clientId = :client_id"), //!entity.field

        @NamedQuery(name = "Contract.findByTariffId",
                query = "SELECT c FROM ContractEntity c where c.tariffByTariffId.tariffId = :tariff_id"), //!entity.field
})
public class ContractEntity implements Serializable {

    @Id
    @Column(name = "contract_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int contractId;

    @Column(name = "phone_number", unique = true, length = 7)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String phoneNumber;

    @Column(name = "blocked_by_user")
    private boolean blockedByUser = false;
    @Column(name = "blocked_by_company")
    private boolean blockedByCompany;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private ClientEntity clientByClientId; // many contracts by one Client

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "tariff_id")
    private TariffEntity tariffByTariffId; // many contracts by one Tariff

/*    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contractByContractId")
    private List<ContractOptionsEntity> contractAddedOptionsEntities = new ArrayList<>();*/

}
