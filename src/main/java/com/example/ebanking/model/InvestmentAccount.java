/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ebanking.model;

import com.example.ebanking.dao.ObjectDao;
import com.example.ebanking.persistence.HibernateUtil;
import com.example.ebanking.utils.DateUtil;
import com.example.ebanking.utils.RandomUtil;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;
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
import org.joda.time.DateTimeUtils;

@Entity
@Table
@PrimaryKeyJoinColumn(name = "accountId")
public class InvestmentAccount extends Account implements Serializable {

    public InvestmentAccount() {
        super();
    }

    public InvestmentAccount(DateTime startDate, DateTime endDate, InvestmentPlan investmentPlan) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
        this.investmentPlan = investmentPlan;
    }
    @Column
    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    private DateTime startDate;

    @Column
    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    private DateTime endDate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private InvestmentPlan investmentPlan;

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public InvestmentPlan getInvestmentPlan() {
        return investmentPlan;
    }

    public void setInvestmentPlan(InvestmentPlan investmentPlan) {
        this.investmentPlan = investmentPlan;
    }

    @Override
    public long saveAccount() {
        ObjectDao<InvestmentAccount> accountDao = new ObjectDao<InvestmentAccount>();
        this.endDate = DateUtil.addDays(this.startDate, this.investmentPlan.getDurationInDays());
        return accountDao.addObject(this);
    }

    @Override
    public void updateAccount() throws IllegalAccessException, InvocationTargetException {
        ObjectDao<InvestmentAccount> investmentAccountDao = new ObjectDao<InvestmentAccount>();
        this.endDate = DateUtil.addDays(this.startDate, this.investmentPlan.getDurationInDays());
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
    public boolean withdraw(double amount, String description) throws IllegalAccessException, InvocationTargetException {
        boolean isDone = false;
        return isDone;
    }

    public double calculateReturnOfInvestment(DateTime today) {
        double result = 0;
        if (this.investmentPlan instanceof ClosedTermInvestment) {
            return calculateReturnOfInvestmentForClosedTermInvestment(today);
        } else if (this.investmentPlan instanceof OpenTermInvestment) {
            return calculateReturnOfInvestmentForOpenTermInvestment(today);
        }
        return result;
    }

    public double calculateReturnOfInvestmentForClosedTermInvestment(DateTime today) {
//        long duration = this.getInvestmentPlan().getDurationInDays();
        double returnOfInvestmentPercent = this.getInvestmentPlan().getInvestmentReturnsPercent();
        double returnOfInvestment = 0;

        int days = DateUtil.DateDifference(this.endDate, today);
        if (days > 0) {
            returnOfInvestment = (returnOfInvestmentPercent * this.getBalance()) + this.getBalance();
        }
        return returnOfInvestment;
    }

    public double calculateReturnOfInvestmentForOpenTermInvestment(DateTime today) {

        double returnOfInvestment = 0;

        int days = DateUtil.DateDifference(this.endDate, today);
        if (days > 0) {
            for (int i = 0; i < days; i++) {
                double returnOfInvestmentPercent = RandomUtil.randomInRange(0.0000001, 0.0003);
                returnOfInvestment = (returnOfInvestmentPercent * this.getBalance()) + this.getBalance();

            }

        }
        return returnOfInvestment;
    }
}
