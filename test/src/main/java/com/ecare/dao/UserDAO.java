package com.ecare.dao;

import com.ecare.domain.ContractEntity;
import com.ecare.domain.UserEntity;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class UserDAO extends AbstractHibernateDAO {
    public UserDAO() {
        setClazz(UserEntity.class);
    }

    public List<UserEntity> findByEmail(String email) {
        return getCurrentSession().createNamedQuery("User.findByEmail", UserEntity.class)
                .setParameter("email", email)
                .getResultList();
    }

    @Override
    public List<UserEntity> findAll() {
        return getCurrentSession().createNamedQuery("User.findAll", UserEntity.class).list();
    }
}
