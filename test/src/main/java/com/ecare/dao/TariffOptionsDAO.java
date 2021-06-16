package com.ecare.dao;

import com.ecare.domain.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TariffOptionsDAO extends AbstractHibernateDAO {
    public TariffOptionsDAO() {
        setClazz(TariffOptionsEntity.class);
    }

    public List<TariffOptionsEntity> findByTariffIdOptionId(int tariffId, int optionId) {
        return getCurrentSession().createNamedQuery("TariffOptions.findByTariffIdOptionId", TariffOptionsEntity.class)
                .setParameter("tariff_id", tariffId)
                .setParameter("option_id", optionId)
                .getResultList();
    }

    public void delete(int tariffId, int optionId) {
        TariffOptionsEntity tariffsOptionToDelete = findByTariffIdOptionId(tariffId, optionId).get(0);
        delete(tariffsOptionToDelete);
    }
}

