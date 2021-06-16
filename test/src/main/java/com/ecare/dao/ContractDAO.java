package com.ecare.dao;

import com.ecare.domain.ContractEntity;
import com.ecare.domain.TariffEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ContractDAO extends AbstractHibernateDAO {
    public ContractDAO() {
        setClazz(ContractEntity.class);
    }

    public List<ContractEntity> getContractsByClientId(int client_id) {
        return getCurrentSession().createNamedQuery("Contract.findByClientId", ContractEntity.class)
                .setParameter("client_id", client_id)
                .getResultList();
    }

    public List<ContractEntity> getContractsByTariffId(int tariff_id) {
        return getCurrentSession().createNamedQuery("Contract.findByTariffId", ContractEntity.class)
                .setParameter("tariff_id", tariff_id)
                .getResultList();
    }

    @Override
    public List<ContractEntity> findAll() {
        return getCurrentSession().createQuery("from " + "ContractEntity order by phoneNumber").list();
    }

}
