package com.example.ebanking.model;

import com.example.ebanking.dao.ObjectDao;
import com.example.ebanking.persistence.HibernateUtil;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue
    private Long transactionId;

    @Column
    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    private DateTime transactionTime;

    @Column
    private String description;
    @Column
    private double debit;
    @Column
    private String formattedDebit;
    @Column
    private double credit;
    @Column
    private String formattedCredit;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Account sourceAccount;

    public Transaction() {
    }

    public Transaction(Account sourceAccount, double debit, double credit, String description) {
        this.sourceAccount = sourceAccount;
        this.description = description;
        this.debit = debit;
        this.formattedDebit = formatDoubleToCurrency(debit);
        this.credit = credit;
        this.formattedCredit = formatDoubleToCurrency(credit);
        this.transactionTime = DateTime.now();

    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public DateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(DateTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDebit() {
        return debit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public String getFormattedDebit() {
        return formattedDebit;
    }

    public void setFormattedDebit(String formattedDebit) {
        this.formattedDebit = formattedDebit;
    }

    public String getFormattedCredit() {
        return formattedCredit;
    }

    public void setFormattedCredit(String formattedCredit) {
        this.formattedCredit = formattedCredit;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public String formatDoubleToCurrency(double amount) {
        if (amount == 0) {
            return "";
        } else {
            String currencyString = NumberFormat.getCurrencyInstance(Locale.CANADA).format(amount);
            //return currencyString.replaceAll("\\.00", "");
            return currencyString;

        }
    }

    public long saveTransaction() {
        ObjectDao<Transaction> accountDao = new ObjectDao<Transaction>();
        return accountDao.addObject(this);
    }

    public void updateTransaction() throws IllegalAccessException, InvocationTargetException {
        ObjectDao<Transaction> accountDao = new ObjectDao<Transaction>();
        accountDao.updateObject(this, this.getTransactionId(), Transaction.class);
    }

    public void deleteTransaction() throws IllegalAccessException, InvocationTargetException {
        ObjectDao<Transaction> accountDao = new ObjectDao<Transaction>();
        accountDao.deleteObject(this, this.getTransactionId(), Transaction.class);
    }

    public static Transaction getTransactionById(long id) {
        Transaction accountHolder = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            accountHolder = (Transaction) session.get(Transaction.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return accountHolder;
    }

    public static ArrayList<Transaction> getTransactions() {
        ArrayList<Transaction> transactions;
        ObjectDao accountDao = new ObjectDao();
        transactions = accountDao.getAllObjects("Transaction");
        return transactions;
    }

    public static ArrayList<Transaction> getAccountTransactions(String accountNumber) {
        ArrayList<Transaction> transactions;
        ObjectDao accountDao = new ObjectDao();
        transactions = accountDao.getAllObjectsByCondition("Transaction", "sourceAccount_accountId = " + accountNumber);
        return transactions;
    }
}
