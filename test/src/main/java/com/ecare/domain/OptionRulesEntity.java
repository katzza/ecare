package com.ecare.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "option_rules", schema = "public", catalog = "eCare")
public class OptionRulesEntity implements Serializable {

    @Id
    @Column(name = "rule_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ruleId;

    @Column(name = "rule_name", nullable = false, unique = true)
    private String ruleName;

    @Column(name = "use_together")
    private boolean useTogether;

    @Column(name = "use_not_together")
    private boolean useNotTogether;

  /*  @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private OptionEntity optionByOptionIdLeft;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private OptionEntity optionByOptionIdRight;*/

    public OptionRulesEntity(String ruleName, Boolean useTogether, Boolean useNotTogether, OptionEntity optionByOptionIdLeft, OptionEntity optionByOptionIdRight) {
        this.ruleName = ruleName;
        this.useTogether = useTogether;
        this.useNotTogether = useNotTogether;
        // this.optionByOptionIdLeft = optionByOptionIdLeft;
        // this.optionByOptionIdRight = optionByOptionIdRight;
    }
}
