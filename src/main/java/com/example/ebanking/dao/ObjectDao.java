/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ebanking.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.logging.*;
import javax.persistence.*;
import org.apache.commons.beanutils.BeanUtils;

public class ObjectDao<T> {

    private static final String PERSISTENCE_UNIT_NAME = "eBankingPU";
    private EntityManagerFactory emf;
    //private EntityManager em;

    private T t;

    public T get() {
        return this.t;
    }

    public void set(T t1) {
        this.t = t1;
    }

    public ObjectDao() {
        getEMF();
    }

    public EntityManagerFactory getEMF() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return emf;
    }

    public void addObject(Object entity) {
        EntityManager em = this.getEMF().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    /* Method to UPDATE */
    public void updateObject(Object entity, long id, Class<T> ClassName) {
        EntityManager em = this.getEMF().createEntityManager();
        try {
            em.getTransaction().begin();
            this.t = (T) em.find(ClassName, id);
            Object orig = entity;
            Object dest = this.t;
            BeanUtils.copyProperties(dest, orig);
            entity = t;
            t = null;
            em.getTransaction().commit();
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ObjectDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(ObjectDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            em.close();
        }
    }

    /* Method to DELETE an employee from the records */
    public void deleteObject(Object object, long id, Class<T> ClassName) {
        EntityManager em = this.getEMF().createEntityManager();
        try {
            em.getTransaction().begin();
            T entity = em.find(ClassName, id);
            em.remove(entity);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public T getObjectById(long id, Class<T> ClassName) {
        EntityManager em = this.getEMF().createEntityManager();
        try {
            return em.find(ClassName, id);
        } finally {
            em.close();
        }
    }

    public ArrayList getAllObjects(Class<T> ClassName, String tableName) {
        EntityManager em = this.getEMF().createEntityManager();
        ArrayList entities = null;
        try {
            entities = (ArrayList) em.createQuery("SELECT tb FROM " + tableName + " tb ", ClassName).getResultList();

            return entities;
        } finally {
            em.close();
        }
    }

    public ArrayList getAllObjectsByCondition(String tableName, String whereString) {
        EntityManager em = this.getEMF().createEntityManager();
        ArrayList entities = null;
        try {
            entities = (ArrayList) em.createQuery("SELECT tb FROM " + tableName + " tb WHERE " + whereString).getResultList();

            return entities;
        } finally {
            em.close();
        }
    }

}
