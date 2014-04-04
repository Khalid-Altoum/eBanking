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
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.HibernateException;
import org.hibernate.Session;
 
@Entity
@Table
@PrimaryKeyJoinColumn(name = "accountId")
public class ChequingAccount extends Account implements Serializable {

    @Override
    public long saveAccount() {
        ObjectDao<ChequingAccount> accountDao = new ObjectDao<ChequingAccount>();
        return accountDao.addObject(this);
    }

    @Override
    public void updateAccount() throws IllegalAccessException, InvocationTargetException {
        ObjectDao<ChequingAccount> checkingAccountDao = new ObjectDao<ChequingAccount>();
        checkingAccountDao.updateObject(this, this.getAccountId(), ChequingAccount.class);
    }

    @Override
    public void deleteAccount() throws IllegalAccessException, InvocationTargetException {
        ObjectDao checkingAccountDao = new ObjectDao();
        checkingAccountDao.deleteObject(this, this.getAccountId(), ChequingAccount.class);
    }

    public static ChequingAccount getCheckingAccountById(long id) {
        ChequingAccount checkingAccountHolder = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            checkingAccountHolder = (ChequingAccount) session.get(ChequingAccount.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return checkingAccountHolder;
    }

    public static ArrayList<ChequingAccount> getCheckingAccounts() {
        ArrayList<ChequingAccount> checkingAccounts;
        ObjectDao checkingAccountDao = new ObjectDao();
        checkingAccounts = checkingAccountDao.getAllObjects("CheckingAccount");
        return checkingAccounts;
    }

    @Override
    public boolean withdraw(double amount,String description) throws IllegalAccessException, InvocationTargetException {
        boolean isDone = false;

        if (this.getBalance() >= amount) {
            super.withdraw(amount,description);
            isDone = true;
        }
        return isDone;
    }
}
