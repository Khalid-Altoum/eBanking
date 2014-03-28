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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 *
 * @author Peyman
 */

@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public class Account implements Serializable   {

    @Id
    @GeneratedValue
    protected Long accountId;

    @Column
    private String accountNumber;

    @Column
    private double balance;

    @Column
    private String currency;

    @Column
    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    private DateTime openedDate;

   // @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  //  protected Client relatedClient;

    public Account() {
        this.setBalance(0);
        this.setCurrency("CAD");
    }

    public Account(double startingBalance, Client customer) {
        this.setBalance(startingBalance);
//        this.setRelatedCustomer(customer);
        this.setCurrency("CAD");

    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public DateTime getOpenedDate() {
        return openedDate;
    }

    public void setOpenedDate(DateTime openedDate) {
        this.openedDate = openedDate;
    }

 

    public long saveAccount()  {
        ObjectDao<Account> accountDao = new ObjectDao<Account>();
        return accountDao.addObject(this);
    }

    public void updateAccount() throws IllegalAccessException, InvocationTargetException {
        ObjectDao<Account> accountDao = new ObjectDao<Account>();
        accountDao.updateObject(this, this.getAccountId(), Account.class);
    }

    public void deleteAccount() throws IllegalAccessException, InvocationTargetException {
        ObjectDao<Account> accountDao = new ObjectDao<Account>();
        accountDao.deleteObject(this, this.getAccountId(), Account.class);
    }

    public static Account getAccountById(long id) {
        Account accountHolder = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            accountHolder = (Account) session.get(Account.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return accountHolder;
    }

    public static ArrayList<Account> getAccounts() {
        ArrayList<Account> accounts;
        ObjectDao accountDao = new ObjectDao();
        accounts = accountDao.getAllObjects("Account");
        return accounts;
    }

    public boolean withdraw(double amount) throws IllegalAccessException, InvocationTargetException {
        boolean isDone = false;

        
            double balance = this.getBalance();
            balance -= amount;
            this.setBalance(balance);
            isDone = true;
            this.updateAccount();
        return isDone;
    }

    public void deposite(double amount) throws IllegalAccessException, InvocationTargetException {
        double balance = this.getBalance();
        balance += amount;
        this.setBalance(balance);
        this.updateAccount();
    }

    public static boolean transfer(Account sourceAccount, Account targetAccount, double amount) {

        double sourceBalance = sourceAccount.getBalance();
        double targetBalance = targetAccount.getBalance();
        boolean isDone = false;

        if (sourceBalance >= amount) {
            sourceBalance -= amount;
            targetBalance += amount;

            sourceAccount.setBalance(sourceBalance);
            targetAccount.setBalance(targetBalance);
            isDone = true;
        }
        return isDone;
    }

    // TO DO
    public void payBill(double amount) {
    }

   
}
