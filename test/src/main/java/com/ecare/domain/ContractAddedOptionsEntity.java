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
@Table(name = "contract_added_options", schema = "public", catalog = "eCare")
public class ContractAddedOptionsEntity implements Serializable {

    @Id
    @JoinColumn(name = "contract_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ContractEntity contractByContractId;

    @Id
    @JoinColumn(name = "option_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private OptionEntity optionByOptionId;

    public ContractAddedOptionsEntity(ContractEntity contractByContractId, OptionEntity optionByOptionId) {
        this.contractByContractId = contractByContractId;
        this.optionByOptionId = optionByOptionId;
    }
}
