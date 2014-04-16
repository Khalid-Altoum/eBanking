/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ebanking.model;

import com.example.ebanking.dao.ObjectDao;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import javax.persistence.*;
import org.joda.time.DateTime;

@Entity
@Table
@PrimaryKeyJoinColumn(name = "accountId")

public class CreditAccount extends Account implements Serializable {

    private double creditLimit;

    private double availableCredit;


    private DateTime expiryDate;


    private int CVS;

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
    public void saveAccount() {
        ObjectDao<CreditAccount> creditAccountDao = new ObjectDao<CreditAccount>();
        creditAccountDao.addObject(this);
    }

    @Override
    public void updateAccount(){
        ObjectDao<CreditAccount> creditAccountDao = new ObjectDao<CreditAccount>();
        creditAccountDao.updateObject(this, this.getAccountId(), CreditAccount.class);
    }

    public void deleteCreditAccount(){
        ObjectDao creditAccountDao = new ObjectDao();
        creditAccountDao.deleteObject(this, this.getAccountId(), CreditAccount.class);
    }

    public static CreditAccount getCreditAccountById(long id) {
        ObjectDao<CreditAccount> dao = new ObjectDao<CreditAccount>();
        return dao.getObjectById(id, CreditAccount.class);
    }

    public static ArrayList<CreditAccount> getCreditAccounts() {
        ObjectDao<CreditAccount> dao = new ObjectDao<CreditAccount>();
        return dao.getAllObjects(CreditAccount.class, "CreditAccount");
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

