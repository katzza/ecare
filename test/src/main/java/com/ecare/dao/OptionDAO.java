package com.ecare.dao;

import com.ecare.domain.OptionEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OptionDAO extends AbstractHibernateDAO {

    public OptionDAO() {
        setClazz(OptionEntity.class);
    }

    public List<OptionEntity> findNonBaseOptions() {
        return getCurrentSession().createNamedQuery("Option.getAllSalesOptions", OptionEntity.class).getResultList();
    }

    public List<OptionEntity> findByName(String optionName) {
        return getCurrentSession().createNamedQuery("Option.findByName", OptionEntity.class)
                .setParameter("option_name", optionName)
                .getResultList();
    }

    public List<Object[]> getBaseOptions() {
        return getCurrentSession().createNamedQuery("Option.getBaseOptions", Object[].class).getResultList();
    }

    public List<Object[]> getOptionsByParameters(int numberBaseOption, boolean isMultiple) {
        return getCurrentSession().createNamedQuery("Option.getOptionsByParameters", Object[].class)
                .setParameter("base_option", numberBaseOption)
                .setParameter("is_multi", isMultiple)
                .getResultList();
    }

    public List<Object[]> getUnselectedFreeOptions(int tariffId) {
        return getCurrentSession().createNamedQuery("Option.getNotTariffAddedFreeOptions", Object[].class)
                .setParameter("tariff_id", tariffId)
                .getResultList();
    }

    public List<OptionEntity> getByBaseIsMultuAndTariffId(int numberBaseOption, boolean isMulti, int tariffId) {
        return getCurrentSession().createNamedQuery("Option.getByBaseIsMultiAndTariffId", OptionEntity.class)
                .setParameter("base_option", numberBaseOption)
                .setParameter("is_multi", isMulti)
                .setParameter("tariff_id", tariffId)
                .getResultList();
    }

    public List<OptionEntity> getTariffAddedMultiAndFreeoptions(int tariffId) {
        return getCurrentSession().createNamedQuery("Option.getTariffAddedMultiAndFreeOptions", OptionEntity.class)
                .setParameter("tariff_id", tariffId)
                .getResultList();
    }



/*    public List<Object[]> getUnselectedAdditionalOptions(int optionId) {
        return getCurrentSession().createNamedQuery("Option.getUnselectedAdditionalOptions", Object[].class)
                .setParameter("main_option_id", optionId)
                .getResultList();
    }*/ //old logic

    public List<OptionEntity> findByMainOptionId(int optionId) {
        return getCurrentSession().createNamedQuery("Option.getOptionsByMainOptionId", OptionEntity.class)
                .setParameter("main_option_id", optionId)
                .getResultList();
    }


}
