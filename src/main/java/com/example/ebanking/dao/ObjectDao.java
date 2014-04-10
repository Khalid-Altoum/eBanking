/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ebanking.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Khalid
 */
public class ObjectDao<T> {

    private static SessionFactory factory;
    private T t;

    public T get() {
        return this.t;
    }

    public void set(T t1) {
        this.t = t1;
    }

    public ObjectDao() {
        makeSessionFactory();
    }

    public static void makeSessionFactory() {

        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (HibernateException ex) {

            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Long addObject(Object object) {
        Session session = factory.openSession();
        Transaction tx = null;
        Long id = null;
        try {
            tx = session.beginTransaction();

            id = (Long) session.save(object);

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return id;
    }

//    public void addOrUpdateObject(Object object) {
//        Session session = factory.openSession();
//        Transaction tx = null;
//        try {
//            tx = session.beginTransaction();
//            if (session.contains(object)) {
//                session.saveOrUpdate(object);
//            } else {
//                session.merge(object);
//            }
//
//            tx.commit();
//        } catch (HibernateException e) {
//            if (tx != null) {
//                tx.rollback();
//            }
//            e.printStackTrace();
//        } finally {
//            session.close();
//        }
//
//    }

    /* Method to UPDATE salary for an employee */
    public void updateObject(Object object, long id, Class<T> ClassName) throws IllegalAccessException, InvocationTargetException {
        Session session = factory.openSession();
       // Transaction tx = null;
        try {
             session.beginTransaction();
             
            this.t = (T) session.load(ClassName, id);
            BeanUtils.copyProperties(t,object);
           // session.evict(object);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    /* Method to DELETE an employee from the records */

    public void deleteObject(Object object, long id, Class<T> ClassName) throws IllegalAccessException, InvocationTargetException {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            // Deleting Object

            this.t = (T) session.load(ClassName, id);
            session.delete(t);
            session.flush();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public ArrayList getAllObjects(String tableName) {
        Session session = factory.openSession();
        ArrayList objects = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            // Get All Physicians 
            objects = (ArrayList) session.createQuery("FROM " + tableName).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return objects;
    }

    public ArrayList getAllObjectsByCondition(String tableName, String whereString) {
        Session session = factory.openSession();
        ArrayList objects = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            // Get All Physicians 
            objects = (ArrayList) session.createQuery("FROM " + tableName + " WHERE " + whereString).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return objects;
    }
    
     public int updateUsingSQL(String tableName,String setString, String whereString) {
        // tableName i.e. Account
        // setString i.e accountNumber = "Saving-0011-001",balance=1000
        // whereSring i.e accountId = 3
        StatelessSession session = factory.openStatelessSession();
        Transaction tx = null;
        int result=0;
        try {
            tx = session.beginTransaction();
            Query query= session.createQuery("update " + tableName + " set "+ setString +" where " + whereString);
            result = query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }
}


//Query query = session.createQuery("update Stock set stockName = :stockName" +
//    				" where stockCode = :stockCode");
//query.setParameter("stockName", "DIALOG1");
//query.setParameter("stockCode", "7277");
//int result = query.executeUpdate();