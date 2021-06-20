package com.ecare.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "option", schema = "public", catalog = "eCare")

@NamedQueries({
        @NamedQuery(name = "Option.findByName",
                query = "SELECT o FROM OptionEntity o where o.optionName = :option_name"),
        @NamedQuery(name = "Option.getBaseOptions",
                query = "SELECT o.optionId , o.optionName  FROM OptionEntity o where o.isBase = true"),
        @NamedQuery(name = "Option.getOptionsByParameters",
                query = "SELECT o.optionId , o.optionName  FROM OptionEntity o where o.baseOptionId = :base_option and o.isMulti= :is_multi order by o.optionName"),

        @NamedQuery(name = "Option.getByBaseIsMultiAndTariffId",
                query = "SELECT o  FROM OptionEntity o where o.baseOptionId = :base_option and o.isMulti =:is_multi and o.optionId in " +
                        "(SELECT t.optionByOptionId.optionId FROM TariffOptionsEntity t WHERE t.tariffByTariffId.tariffId = : tariff_id)"),

        //shows base-dependent and NON-base dependent too
        @NamedQuery(name = "Option.getTariffAddedMultiAndFreeOptions",
                query = "SELECT o  FROM OptionEntity o where o.isMulti = true and o.optionId in " +
                        "(SELECT t.optionByOptionId.optionId FROM TariffOptionsEntity t WHERE t.tariffByTariffId.tariffId = : tariff_id)"),

        @NamedQuery(name = "Option.getNotTariffAddedFreeOptions",
                query = "SELECT o.optionId , o.optionName  FROM OptionEntity o where o.isMulti = true and o.baseOptionId = 0 and o.optionId NOT IN " +
                        "(SELECT t.optionByOptionId.optionId FROM TariffOptionsEntity t WHERE t.tariffByTariffId.tariffId = : tariff_id)"),

        @NamedQuery(name = "Option.getNotTariffAddedMultioptions",
                query = "SELECT o.optionId , o.optionName  FROM OptionEntity o where o.baseOptionId = :base_option and o.isMulti= true and o.optionId NOT IN " +
                        " (SELECT t.optionByOptionId.optionId FROM TariffOptionsEntity t WHERE t.tariffByTariffId.tariffId = : tariff_id)"),

        @NamedQuery(name = "Option.getAllSalesOptions",
                query = "SELECT o FROM OptionEntity o where o.isBase = false order by o.baseOptionId, o.optionName"),
})

public class OptionEntity implements Serializable {
    @Id
    @Column(name = "option_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int optionId;

    @Column(name = "option_name", nullable = false, unique = true)
    private String optionName;
    @Column(name = "month_price")
    private int monthPrice;
    @Column(name = "connection_price")
    private int connectionPrice;
    @Column(name = "description")
    private String description;
    @Column(name = "base_option")
    private int baseOptionId;      //Self Referencing Table
    @Column(name = "is_base")
    private boolean isBase;  //not rename to isBaseOption because of mapper

    @Column(name = "is_multi")
    private boolean isMulti;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "optionByOptionId")
    private List<ContractAddedOptionsEntity> contractAddedOptionsEntities = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "optionByOptionId")
    private List<TariffOptionsEntity> tariffOptionsEntities = new ArrayList<>();

}
