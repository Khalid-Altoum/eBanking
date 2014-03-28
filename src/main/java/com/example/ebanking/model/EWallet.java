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
public class EWallet extends Account implements Serializable {


    @Override
    public long saveAccount() {
        ObjectDao<EWallet> accountDao = new ObjectDao<EWallet>();
        return accountDao.addObject(this);
    }

    public void updateAccount() throws IllegalAccessException, InvocationTargetException {
        ObjectDao<EWallet> accountDao = new ObjectDao<EWallet>();
        accountDao.updateObject(this, this.getAccountId(), EWallet.class);
    }

    @Override
    public void deleteAccount() throws IllegalAccessException, InvocationTargetException {
        ObjectDao<EWallet> accountDao = new ObjectDao<EWallet>();
        accountDao.deleteObject(this, this.getAccountId(), EWallet.class);
    }

    public static EWallet getElectronicWalletById(long id) {
        EWallet accountHolder = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            accountHolder = (EWallet) session.get(EWallet.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return accountHolder;
    }

    public static ArrayList<EWallet> getElectronicWallets() {
        ArrayList<EWallet> accounts;
        ObjectDao accountDao = new ObjectDao<EWallet>();
        accounts = accountDao.getAllObjects("EWallet");
        return accounts;
    }

}