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
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table
@PrimaryKeyJoinColumn(name = "accountId")

public class CreditAccount extends Account implements Serializable {

    private double creditLimit;

    private double availableCredit;

    // Credit Card Info
    // Use the account number as the credit card number
    @Column
    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    private DateTime expiryDate;

    @Column
    private int CVS;
    // done with cards info

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private CreditPlan creditPlan;

    public CreditAccount() { super();
    }

    public CreditAccount(String creditCardNumber, double creditLimit, DateTime expiryDate, String cvs) {
       super();
        this.setAccountNumber(creditCardNumber);
        
        this.availableCredit = creditLimit;
        this.creditLimit = creditLimit;
        this.expiryDate = expiryDate;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public double getAvailableCredit() {
        return availableCredit;
    }

    public void setAvailableCredit(double availableCrdit) {
        this.availableCredit = availableCrdit;
    }

    public CreditPlan getCreditPlan() {
        return creditPlan;
    }

    public void setCreditPlan(CreditPlan creditPlan) {
        this.creditPlan = creditPlan;
    }

    public DateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(DateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getCVS() {
        return CVS;
    }

    public void setCVS(int CVS) {
        this.CVS = CVS;
    }

    @Override
    public long saveAccount() {
        ObjectDao creditAccountDao = new ObjectDao();
        return creditAccountDao.addObject(this);
    }

    @Override
    public void updateAccount() throws IllegalAccessException, InvocationTargetException {
        ObjectDao creditAccountDao = new ObjectDao();
        creditAccountDao.updateObject(this, this.getAccountId(), CreditAccount.class);
    }

    public void deleteCreditAccount() throws IllegalAccessException, InvocationTargetException {
        ObjectDao creditAccountDao = new ObjectDao();
        creditAccountDao.deleteObject(this, this.getAccountId(), CreditAccount.class);
    }

    public static CreditAccount getCreditAccountById(long id) {
        CreditAccount creditAccountHolder = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            creditAccountHolder = (CreditAccount) session.get(CreditAccount.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return creditAccountHolder;
    }

    public static ArrayList<CreditAccount> getCreditAccounts() {
        ArrayList<CreditAccount> creditAccounts;
        ObjectDao creditAccountDao = new ObjectDao();
        creditAccounts = creditAccountDao.getAllObjects("CreditAccount");
        return creditAccounts;
    }

    @Override
    public boolean withdraw(double amount, String description) throws IllegalAccessException, InvocationTargetException {
        boolean isDone = false;

        if (this.getAvailableCredit() >= amount) {
            double interestAmount = amount * this.creditPlan.getCashAdvanceInterest();
            double amountIncludesInterest = amount + interestAmount;

            double updatedAvailableCredit = this.getAvailableCredit();
            updatedAvailableCredit -= amountIncludesInterest;
            this.setAvailableCredit(updatedAvailableCredit);

            try {
                this.updateAccount();
                isDone = true;
                Transaction tr = new Transaction(this, amountIncludesInterest, 0, description);
                tr.saveTransaction();
            } catch (Exception e) {
                return false;
            }
        }
        return isDone;
    }
}

