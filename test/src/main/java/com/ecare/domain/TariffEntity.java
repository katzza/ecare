package com.ecare.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@ToString
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "tariff", schema = "public", catalog = "eCare")
@NamedQueries({
        @NamedQuery(name = "Tariff.findByTariffId",
                query = "SELECT t FROM TariffEntity t where t.tariffId = :tariff_id"),
        @NamedQuery(name = "Tariff.findByName",
                query = "SELECT t FROM TariffEntity t where t.tariffName = :tariff_name"),
        @NamedQuery(name = "Tariff.findBaseTariff",
                query = "SELECT t FROM TariffEntity t where t.isBaseTariff = true"),
        @NamedQuery(name = "Tariff.getAllTariffs",
                query = "SELECT t.tariffId,t.tariffName from TariffEntity t"),
})
public class TariffEntity implements Serializable {

    @Id
    @Column(name = "tariff_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tariffId;

    @Column(name = "tariff_name", nullable = false, unique = true)
    private String tariffName;

    @Column(name = "tariff_description")
    private String tariffDescription;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "base_tariff", nullable = false)
    private boolean isBaseTariff;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tariffByTariffId")
    private List<ContractEntity> contractEntities = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tariffByTariffId", cascade = CascadeType.ALL)
    private List<TariffOptionsEntity> tariffOptionEntities = new ArrayList<>();

}
