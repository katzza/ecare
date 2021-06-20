package com.ecare.dao;

import com.ecare.domain.ContractOptionsEntity;
import com.ecare.domain.TariffOptionsEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContractOptionDAO extends AbstractHibernateDAO {

    public ContractOptionDAO() {
        setClazz(ContractOptionsEntity.class);
    }


    public List<ContractOptionsEntity> findByContractAndOption(int contractId, int optionId) {
        return getCurrentSession().createNamedQuery("ContractOptions.findByContractIdOptionId", ContractOptionsEntity.class)
                .setParameter("contract_id", contractId)
                .setParameter("option_id", optionId)
                .getResultList();

    }
}
