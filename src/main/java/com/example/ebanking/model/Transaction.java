package com.example.ebanking.model;

import com.example.ebanking.dao.ObjectDao;
import com.example.ebanking.utils.JodaDateTimeConverter;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import javax.persistence.*;
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
    
    @Convert(converter= JodaDateTimeConverter.class)
    private DateTime transactionTime;
    private String description;
    private double debit;
    private String formattedDebit;
    private double credit;
    private String formattedCredit;

    @ManyToOne(fetch = FetchType.EAGER)
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

    public void saveTransaction() {
        ObjectDao<Transaction> transactionDao = new ObjectDao<Transaction>();
        transactionDao.addObject(this);
    }

    public void updateTransaction(){
        ObjectDao<Transaction> transactionDao = new ObjectDao<Transaction>();
        transactionDao.updateObject(this, this.getTransactionId(), Transaction.class);
    }

    public void deleteTransaction(){
        ObjectDao<Transaction> transactionDao = new ObjectDao<Transaction>();
        transactionDao.deleteObject(this, this.getTransactionId(), Transaction.class);
    }

    public static Transaction getTransactionById(long id) {
        ObjectDao<Transaction> dao = new ObjectDao<Transaction>();
        return dao.getObjectById(id, Transaction.class);
    }

    public static ArrayList<Transaction> getTransactions() {
        ObjectDao<Transaction> dao = new ObjectDao<Transaction>();
        return dao.getAllObjects(Transaction.class, "Transaction");
    }

    public static ArrayList<Transaction> getAccountTransactions(String accountNumber) {
        ArrayList<Transaction> transactions;
        ObjectDao accountDao = new ObjectDao();
        transactions = accountDao.getAllObjectsByCondition("Transaction", "sourceAccount_accountId = " + accountNumber);
        return transactions;
    }
}
