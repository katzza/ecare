package com.ecare.dao;

import com.ecare.domain.OptionOptionsEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OptionOptionsDAO extends AbstractHibernateDAO {
    public OptionOptionsDAO() {
        setClazz(OptionOptionsEntity.class);
    }

    public void deleteOpionsOption(int mainOptionId, int additionalOptionId) {
        OptionOptionsEntity optionOptionsToDelete = findByBothIds(mainOptionId, additionalOptionId).get(0);
        delete(optionOptionsToDelete);
    }

    private List<OptionOptionsEntity> findByBothIds(int mainOptionId, int additionalOptionId) {
        return getCurrentSession().createNamedQuery("OptionOptions.findByBothIds", OptionOptionsEntity.class)
                .setParameter("main_option_id", mainOptionId)
                .setParameter("additional_option_id", additionalOptionId)
                .getResultList();
    }
}

