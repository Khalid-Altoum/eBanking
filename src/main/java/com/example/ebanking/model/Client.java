/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ebanking.model;

import com.example.ebanking.dao.ObjectDao;
import com.example.ebanking.persistence.HibernateUtil;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author HMD
 */
@Entity
@Table
@PrimaryKeyJoinColumn(name = "userId")
public class Client extends User implements Serializable{
    
    @Column
    private long age;

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }
    
     public long saveUser()  {
        ObjectDao<Client> userDao = new ObjectDao<Client>();
        return userDao.addObject(this);
    }

    public void updateUser() throws IllegalAccessException, InvocationTargetException {
        ObjectDao<Client> userDao = new ObjectDao<Client>();
        userDao.updateObject(this, this.getUserId(), Client.class);
    }

    public void deleteUser() throws IllegalAccessException, InvocationTargetException {
        ObjectDao<Client> userDao = new ObjectDao<Client>();
        userDao.deleteObject(this, this.getUserId(), Client.class);
    }

    public static User getClientsById(long id) {
        User userHolder = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            userHolder = (User) session.get(Client.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return userHolder;
    }

    public static ArrayList<Client> getClients() {
        ArrayList<Client> clients;
        ObjectDao userDao = new ObjectDao();
        clients = userDao.getAllObjects("Client");
        return clients;
    }
    

}
