/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ebanking.model;

import com.example.ebanking.dao.ObjectDao;
import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.*;

@Entity
public class CreditPlan implements Serializable {

    @Id
    @GeneratedValue
    protected Long creditPlanId;
    private double cashAdvanceInterest;
    private double interestRate;

    public CreditPlan() {

    }

    public CreditPlan(double cashAdvanceInterest, double interestRate) {
        this.cashAdvanceInterest = cashAdvanceInterest;
        this.interestRate = interestRate;
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

    public void saveCreditPlan() {
        ObjectDao<CreditPlan> accountDao = new ObjectDao<CreditPlan>();
        accountDao.addObject(this);
    }

    public void updateCreditPlan() {
        ObjectDao<CreditPlan> creditPlanDao = new ObjectDao<CreditPlan>();
        creditPlanDao.updateObject(this, this.getCreditPlanId(), CreditPlan.class);
    }

    public void deleteCreditPlan() {
        ObjectDao<CreditPlan> creditPlanDao = new ObjectDao<CreditPlan>();
        creditPlanDao.deleteObject(this, this.getCreditPlanId(), CreditPlan.class);
    }

    public static CreditPlan getCreditPlanById(long id) {
        ObjectDao<CreditPlan> dao = new ObjectDao<CreditPlan>();
        return dao.getObjectById(id, CreditPlan.class);
    }

    public static ArrayList<CreditPlan> getCreditPlans() {
        ObjectDao<CreditPlan> dao = new ObjectDao<CreditPlan>();
        return dao.getAllObjects(CreditPlan.class, "CreditPlan");
    }

}
