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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author Peyman
 */
@Entity
@Table
public class CreditPlan implements Serializable {

    @Id
    @GeneratedValue
    protected Long creditPlanId;

    @Column
    private double cashAdvanceInterest;

    @Column
    private double interestRate;

    @OneToMany(mappedBy = "creditPlan", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<CreditAccount> creditAccounts;

    public CreditPlan() {
        this.creditAccounts=new ArrayList<CreditAccount>();
    }

    public CreditPlan(double cashAdvanceInterest, double interestRate) {
        this.cashAdvanceInterest = cashAdvanceInterest;
        this.interestRate = interestRate;
        this.creditAccounts=new ArrayList<CreditAccount>();
    }

    public Long getCreditPlanId() {
        return creditPlanId;
    }

    public void setCreditPlanId(Long creditPlanId) {
        this.creditPlanId = creditPlanId;
    }

    public double getCashAdvanceInterest() {
        return cashAdvanceInterest;
    }

    public void setCashAdvanceInterest(double cashAdvanceInterest) {
        this.cashAdvanceInterest = cashAdvanceInterest;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public List<CreditAccount> getCreditAccounts() {
        return creditAccounts;
    }

    public void setCreditAccounts(List<CreditAccount> creditAccounts) {
        this.creditAccounts = creditAccounts;
    }

    public long saveCreditPlan() {
        ObjectDao<CreditPlan> accountDao = new ObjectDao<CreditPlan>();
        return accountDao.addObject(this);
    }

    public void updateCreditPlan() throws IllegalAccessException, InvocationTargetException {
        ObjectDao<CreditPlan> creditPlanDao = new ObjectDao<CreditPlan>();
        creditPlanDao.updateObject(this, this.getCreditPlanId(), CreditPlan.class);
    }

    public void deleteCreditPlan() throws IllegalAccessException, InvocationTargetException {
        ObjectDao creditPlanDao = new ObjectDao();
        creditPlanDao.deleteObject(this, this.getCreditPlanId(), CreditPlan.class);
    }

    public static CreditPlan getCreditPlanById(long id) {
        CreditPlan creditPlanHolder = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            creditPlanHolder = (CreditPlan) session.get(CreditPlan.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return creditPlanHolder;
    }

    public static ArrayList<CreditPlan> getCreditPlans() {
        ArrayList<CreditPlan> creditPlans;
        ObjectDao creditPlanDao = new ObjectDao();
        creditPlans = creditPlanDao.getAllObjects("CreditPlan");
        return creditPlans;
    }

}