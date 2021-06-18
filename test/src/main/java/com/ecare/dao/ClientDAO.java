package com.ecare.dao;

import com.ecare.domain.ClientEntity;
import com.ecare.domain.TariffEntity;
import com.ecare.domain.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class ClientDAO extends AbstractHibernateDAO {
    public ClientDAO() {
        setClazz(ClientEntity.class);
    }


    public List<ClientEntity> findClientByUserEmail(String email) {
        return getCurrentSession().createNamedQuery("Client.findByUserEmail", ClientEntity.class)
                .setParameter("email", email)
                .getResultList();
    }

    public List<ClientEntity> findClientByPhone(String phoneNumber) {
        return getCurrentSession().createNamedQuery("Client.findByPhone", ClientEntity.class)
                .setParameter("phoneNumber", phoneNumber)
                .getResultList();
    }

    @Override
    public List<ClientEntity> findAll() {
        return getCurrentSession().createQuery("from " + "ClientEntity order by surname").list();
    }
}
