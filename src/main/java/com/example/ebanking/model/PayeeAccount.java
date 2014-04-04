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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.HibernateException;
import org.hibernate.Session;

@Entity
@Table
@PrimaryKeyJoinColumn(name = "accountId")
public class PayeeAccount extends Account implements Serializable{
    
    @OneToOne(mappedBy = "payeeAccount", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Payee payee;

    public Payee getPayee() {
        return payee;
    }

    public void setPayee(Payee payee) {
        this.payee = payee;
    }
    
    @Override
    public long saveAccount() {
        ObjectDao<PayeeAccount> accountDao = new ObjectDao<PayeeAccount>();
        return accountDao.addObject(this);
    }

    @Override
    public void updateAccount() throws IllegalAccessException, InvocationTargetException {
        ObjectDao<PayeeAccount> payeeAccountDao = new ObjectDao<PayeeAccount>();
        payeeAccountDao.updateObject(this, this.getAccountId(), PayeeAccount.class);
    }

    @Override
    public void deleteAccount() throws IllegalAccessException, InvocationTargetException {
        ObjectDao payeeAccount = new ObjectDao();
        payeeAccount.deleteObject(this, this.getAccountId(), PayeeAccount.class);
    }

    public static PayeeAccount getPayeeAccountById(long id) {
        PayeeAccount payeeAccountHolder = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            payeeAccountHolder = (PayeeAccount) session.get(PayeeAccount.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return payeeAccountHolder;
    }

    public static ArrayList<PayeeAccount> getPayeeAccounts() {
        ArrayList<PayeeAccount> payeeAccounts;
        ObjectDao payeeAccountDao = new ObjectDao();
        payeeAccounts = payeeAccountDao.getAllObjects("PayeeAccount");
        return payeeAccounts;
    }
}
