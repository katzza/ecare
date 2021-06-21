package com.ecare.dao;

import com.ecare.domain.TariffEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class TariffDAO extends AbstractHibernateDAO {
    public TariffDAO() {
        setClazz(TariffEntity.class);
    }

    public List<TariffEntity> getTariffByTariffId(int tariff_id) {
        return getCurrentSession().createNamedQuery("Tariff.findByTariffId", TariffEntity.class)
                .setParameter("tariff_id", tariff_id)
                .getResultList();
    }

    public List<TariffEntity> getBaseTariff() {
        return getCurrentSession().createNamedQuery("Tariff.findBaseTariff", TariffEntity.class)
                .getResultList();
    }

    public List<TariffEntity> findByName(String tariffName) {
        return getCurrentSession().createNamedQuery("Tariff.findByName", TariffEntity.class)
                .setParameter("tariff_name", tariffName)
                .getResultList();
    }

    public List<TariffEntity> getChampionTariffs() {
        return getCurrentSession().createNamedQuery("Tariff.findChampions", TariffEntity.class)
                .getResultList();
    }

    public List<Object[]> getTariffNamesAndIds() {
        return getCurrentSession().createNamedQuery("Tariff.getAllTariffs", Object[].class).getResultList();
    }


    @Override
    public List<TariffEntity> findAll() {
        return getCurrentSession().createQuery("from " + "TariffEntity order by tariffName").list();
    }


}
