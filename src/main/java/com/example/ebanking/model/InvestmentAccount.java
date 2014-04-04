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
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.HibernateException;
import org.hibernate.Session;

@Entity
@Table
@PrimaryKeyJoinColumn(name = "accountId")
public class InvestmentAccount extends Account implements Serializable {
    
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private InvestmentPlan investmentPlan;

    public InvestmentPlan getInvestmentPlan() {
        return investmentPlan;
    }

    public void setInvestmentPlan(InvestmentPlan investmentPlan) {
        this.investmentPlan = investmentPlan;
    }
    
    
     @Override
    public long saveAccount() {
        ObjectDao<InvestmentAccount> accountDao = new ObjectDao<InvestmentAccount>();
        return accountDao.addObject(this);
    }

    @Override
    public void updateAccount() throws IllegalAccessException, InvocationTargetException {
        ObjectDao<InvestmentAccount> investmentAccountDao = new ObjectDao<InvestmentAccount>();
        investmentAccountDao.updateObject(this, this.getAccountId(), InvestmentAccount.class);
    }

    @Override
    public void deleteAccount() throws IllegalAccessException, InvocationTargetException {
        ObjectDao investmentAccountDao = new ObjectDao();
        investmentAccountDao.deleteObject(this, this.getAccountId(), InvestmentAccount.class);
    }

    public static InvestmentAccount getInvestmentAccountById(long id) {
        InvestmentAccount investmentAccountHolder = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            investmentAccountHolder = (InvestmentAccount) session.get(InvestmentAccount.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return investmentAccountHolder;
    }

    public static ArrayList<InvestmentAccount> getInvestmentAccounts() {
        ArrayList<InvestmentAccount> investmentAccounts;
        ObjectDao investmentAccountDao = new ObjectDao();
        investmentAccounts = investmentAccountDao.getAllObjects("InvestmentAccount");
        return investmentAccounts;
    }

     @Override
    public boolean withdraw(double amount,String description) throws IllegalAccessException, InvocationTargetException {
        boolean isDone = false;

        
        
        return isDone;
    }
    
    
}
