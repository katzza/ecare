package com.ecare.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@EqualsAndHashCode
@Getter
@Setter
@Entity
@NoArgsConstructor

@NamedQueries({
        @NamedQuery(name = "TariffOptions.findByTariffIdOptionId",
                query = "SELECT t FROM TariffOptionsEntity t where t.tariffByTariffId.tariffId = :tariff_id and t.optionByOptionId.optionId =: option_id"),
})
@Table(name = "tariff_options", schema = "public", catalog = "eCare")
public class TariffOptionsEntity implements Serializable {

    @Id
    @JoinColumn(name = "tariff_id")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private TariffEntity tariffByTariffId;

    @Id
    @JoinColumn(name = "option_id")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private OptionEntity optionByOptionId;

}
