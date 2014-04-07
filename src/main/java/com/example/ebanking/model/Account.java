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
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;


@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public class Account implements Serializable {

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
    private String currencySign;

    @Column
    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    private DateTime openedDate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Client client;

    @OneToMany(mappedBy = "sourceAccount", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Transaction> sourceTransactions;

    public static enum AccountStatus {

        ACTIVE, BLOCKED, INACTIVE
    };

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    public Account() {
        this.balance = 0;
        this.currency = "CAD";
        this.currencySign = "$";
        this.status = AccountStatus.ACTIVE;
        this.openedDate = DateTime.now();
        this.sourceTransactions = new ArrayList<Transaction>();
    }

    public Account(double startingBalance, Client client) {
        this.balance = startingBalance;
        this.client = client;
        this.currency = "CAD";
        this.currencySign = "$";
        this.status = AccountStatus.ACTIVE;
        this.openedDate = DateTime.now();
        this.sourceTransactions = new ArrayList<Transaction>();
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public List<Transaction> getSourceTransactions() {
        return sourceTransactions;
    }

    public void setSourceTransactions(List<Transaction> sourceTransactions) {
        this.sourceTransactions = sourceTransactions;
    }

    public String getCurrencySign() {
        return currencySign;
    }

    public void setCurrencySign(String currencySign) {
        this.currencySign = currencySign;
    }

    // Hibernate Methods
    public long saveAccount() {
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

    public static ArrayList<Account> getAllClientAccounts(Long clientId) {
        ArrayList<Account> accounts;
        ObjectDao accountDao = new ObjectDao();
        accounts = accountDao.getAllObjectsByCondition("Account", "client_userId = " + clientId.toString());
        return accounts;
    }

    public boolean withdraw(double amount, String description) throws IllegalAccessException, InvocationTargetException {
        boolean isDone = false;

        double balance = this.getBalance();
        balance -= amount;
        this.setBalance(balance);
        try {
            this.updateAccount();
            isDone = true;
            String transactionDescription= "Withdraw: " + description;
            Transaction tr = new Transaction(this, amount, 0, transactionDescription);
            tr.saveTransaction();
        } catch (Exception e) {
            return false;
        }
        return isDone;
    }

    public boolean deposit(double amount, String description) throws IllegalAccessException, InvocationTargetException {
        boolean isDone = false;

        double balance = this.getBalance();
        balance += amount;
        this.setBalance(balance);
        try {
            this.updateAccount();
            isDone = true;
            String transactionDescription= "Deposite: " + description;
            Transaction tr = new Transaction(this, 0, amount,  transactionDescription);
            tr.saveTransaction();
        } catch (Exception e) {
            return false;
        }
        return isDone;
    }

    public static boolean transfer(Account sourceAccount, Account targetAccount, double amount, String description) {

        double sourceBalance = sourceAccount.getBalance();
        double targetBalance = targetAccount.getBalance();
        boolean isDone = false;

        if (sourceBalance >= amount) {
            sourceBalance -= amount;
            targetBalance += amount;

            try {
                sourceAccount.setBalance(sourceBalance);
                sourceAccount.updateAccount();

                targetAccount.setBalance(targetBalance);
                targetAccount.updateAccount();

                isDone = true;
                String transactionDescription= "Transfer From: " + description;
                Transaction sourceTransaction = new Transaction(sourceAccount, amount, 0, transactionDescription);
                transactionDescription= "Transfer To: " + description;
                Transaction targetTransaction = new Transaction(targetAccount, 0, amount, transactionDescription);
                sourceTransaction.saveTransaction();
                targetTransaction.saveTransaction();
            } catch (Exception e) {
                return false;
            }
        }
        return isDone;

    }

    @Override
    public String toString() {
        return "Account{" + "accountId=" + accountId + ", accountNumber=" + accountNumber + ", balance=" + balance + ", currency=" + currency + ", currencySign=" + currencySign + ", openedDate=" + openedDate + ", client=" + client + ", sourceTransactions=" + sourceTransactions + ", status=" + status + '}';
    }

    public static List<Account> getPayeeAccounts(List<Account> clientAccounts) {
        List<Account> accounts = new ArrayList<Account>();

        for (Account ac : clientAccounts) {
            if (ac instanceof PayeeAccount) {
                accounts.add(ac);
            }
        }
        return accounts;
    }
    public static List<Account> getPersonalAccount(List<Account> clientAccounts){
    List<Account> accounts = new ArrayList<Account>();
    for (Account ac : clientAccounts) {
            if (!(ac instanceof PayeeAccount) && !(ac instanceof InvestmentAccount)) {
                accounts.add(ac);
            }
        }
        return accounts;
    }
    public static List<Account> getInvestmentAccounts(List<Account> clientAccounts) {
        List<Account> accounts = new ArrayList<Account>();

        for (Account ac : clientAccounts) {
            if (ac instanceof InvestmentAccount) {
                accounts.add(ac);
            }
        }
        return accounts;
    }

    // TO DO
    public void payBill(double amount) {
    }

}
