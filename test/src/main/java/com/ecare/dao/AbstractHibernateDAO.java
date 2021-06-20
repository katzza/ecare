package com.ecare.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.LockModeType;
import java.io.Serializable;
import java.util.List;

@Slf4j
public abstract class AbstractHibernateDAO<T extends Serializable> {
    private Class<T> clazz;

    @Autowired
    SessionFactory sessionFactory;

    public void setClazz(Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }

    public T findById(int id) {
        log.info("findById: "+id+ clazz.toString());
        return getCurrentSession().get(clazz, id);
    }

    public List findAll() {
        return getCurrentSession().createQuery("from " + clazz.getName()).list();
    }

    public T save(T entity) {
        getCurrentSession().saveOrUpdate(entity);
        log.info("Saved in database: "+entity.toString());
        return entity;
    }

    public T update(T entity) {
        log.info("Update : "+entity.toString());
        return (T) getCurrentSession().merge(entity);
    }

    public void delete(T entity) {
        getCurrentSession().delete(entity);
    }

    public void deleteById(int entityId) {
        T entity = findById(entityId);
        delete(entity);
    }

    protected final Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

}
