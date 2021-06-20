package com.ecare.dao;

import com.ecare.domain.NumberEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NumberDAO extends AbstractHibernateDAO {

    public NumberDAO() {
        setClazz(NumberEntity.class);
    }

    public List<Object[]> getFreeNumbers() {
        return getCurrentSession().createNamedQuery("FreeNumber.findUnbooked", Object[].class).getResultList();
    }

    public List<NumberEntity> findByPhone(int phoneNumber) {
        return getCurrentSession().createNamedQuery("FreeNumber.findByPhone", NumberEntity.class)
                .setParameter(phoneNumber, phoneNumber)
                .getResultList();
    }

}
