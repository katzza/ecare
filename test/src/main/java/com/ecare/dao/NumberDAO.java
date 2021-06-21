package com.ecare.dao;

import com.ecare.domain.NumberEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.List;

@Transactional
@Repository
public class NumberDAO extends AbstractHibernateDAO {

    public NumberDAO() {
        setClazz(NumberEntity.class);
    }

    public List<Object[]> getFreeNumbers() {
        return getCurrentSession().createNamedQuery("FreeNumber.findUnbooked", Object[].class)
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .getResultList();
    }

    public List<NumberEntity> findByPhone(String phoneNumber) {
        return getCurrentSession().createNamedQuery("FreeNumber.findByPhone", NumberEntity.class)
                .setParameter("phone_number", phoneNumber)
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .getResultList();
    }

}
