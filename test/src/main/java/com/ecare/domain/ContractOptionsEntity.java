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
        @NamedQuery(name = "ContractOptions.findByContractIdOptionId",
                query = "SELECT o FROM ContractOptionsEntity o " +
                        "where o.contractByContractId.contractId = :contract_id and o.optionByOptionId.optionId =: option_id"),
})
@Table(name = "contract_added_options", schema = "public", catalog = "eCare")
public class ContractOptionsEntity implements Serializable {

    @Id
    @JoinColumn(name = "contract_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ContractEntity contractByContractId;

    @Id
    @JoinColumn(name = "option_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private OptionEntity optionByOptionId;

    public ContractOptionsEntity(ContractEntity contractByContractId, OptionEntity optionByOptionId) {
        this.contractByContractId = contractByContractId;
        this.optionByOptionId = optionByOptionId;
    }
}
