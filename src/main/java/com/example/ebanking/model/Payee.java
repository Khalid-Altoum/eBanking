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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity
@Table
public class Payee implements Serializable{
    @Id
     @GeneratedValue
    private Long payeeId;
    
     @Column
    private String name;
    
     
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private PayeeAccount payeeAccount;

    public Long getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(Long payeeId) {
        this.payeeId = payeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PayeeAccount getPayeeAccount() {
        return payeeAccount;
    }

    public void setPayeeAccount(PayeeAccount payeeAccount) {
        this.payeeAccount = payeeAccount;
    }
    
     // Hibernate Methods
    public long savePayee() {
        ObjectDao<Payee> accountDao = new ObjectDao<Payee>();
        return accountDao.addObject(this);
    }

    public void updatePayee() throws IllegalAccessException, InvocationTargetException {
        ObjectDao<Payee> payeeDao = new ObjectDao<Payee>();
        payeeDao.updateObject(this, this.getPayeeId(), Payee.class);
    }

    public void deletePayee() throws IllegalAccessException, InvocationTargetException {
        ObjectDao<Payee> payeeDao = new ObjectDao<Payee>();
        payeeDao.deleteObject(this, this.getPayeeId(), Payee.class);
    }

    public static Payee getPayeeById(long id) {
        Payee payeeHolder = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            payeeHolder = (Payee) session.get(Payee.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return payeeHolder;
    }

    public static ArrayList<Payee> getPayees() {
        ArrayList<Payee> payee;
        ObjectDao accountDao = new ObjectDao();
        payee = accountDao.getAllObjects("Payee");
        return payee;
    }
    
}
