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
@Table(name = "option_added_options", schema = "public", catalog = "eCare")
@NamedQueries({
  @NamedQuery(name = "OptionOptions.findByBothIds",
                query = "SELECT o FROM OptionOptionsEntity o where o.mainOptionByOptionId.optionId = :main_option_id and o.addOptionByOptionId.optionId =: additional_option_id"),
})
public class OptionOptionsEntity implements Serializable {

    @Id
    @JoinColumn(name = "main_option_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private OptionEntity mainOptionByOptionId;

    @Id
    @JoinColumn(name = "additional_option_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private OptionEntity addOptionByOptionId;

}
