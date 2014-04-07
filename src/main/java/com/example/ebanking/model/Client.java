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
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author HMD
 */
@Entity
@Table
@PrimaryKeyJoinColumn(name = "userId")
public class Client extends User implements Serializable {

    @Column
    private long age;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Account> accounts;

    @OneToMany(mappedBy = "relatedClient", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<ClientCard> clientCards;

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<ClientCard> getClientCards() {
        return clientCards;
    }

    public void setClientCards(List<ClientCard> clientCards) {
        this.clientCards = clientCards;
    }

   

    @Override
    public long saveUser() {
        ObjectDao<Client> userDao = new ObjectDao<Client>();
        return userDao.addObject(this);
    }

    @Override
    public void updateUser() throws IllegalAccessException, InvocationTargetException {
        ObjectDao<Client> userDao = new ObjectDao<Client>();
        userDao.updateObject(this, this.getUserId(), Client.class);
    }

    @Override
    public void deleteUser() throws IllegalAccessException, InvocationTargetException {
        ObjectDao<Client> userDao = new ObjectDao<Client>();
        userDao.deleteObject(this, this.getUserId(), Client.class);
    }

    public static Client getClientsById(long id) {
        Client userHolder = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            userHolder = (Client) session.get(Client.class, id);
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

    public static Client getClientByAccountNumber(String clientNumber) {
        Client clientHolder = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            clientHolder = (Client) session.createCriteria(User.class).
                    add(Restrictions.eq("userName", clientNumber)).
                    uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return clientHolder;
    }

    

}
