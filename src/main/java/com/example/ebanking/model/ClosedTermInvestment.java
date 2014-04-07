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
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author HMD
 */
@Entity
@Table
@PrimaryKeyJoinColumn(name = "investmentPlanId")

public class ClosedTermInvestment extends InvestmentPlan implements Serializable {

    public ClosedTermInvestment() {
    }

    public ClosedTermInvestment(double penaltyPercent, int durationInDays, double investmentReturnsPercent) {

        this.setPenaltyPercent(penaltyPercent);
        this.setDurationInDays(durationInDays);
        this.setInvestmentReturnsPercent(investmentReturnsPercent);

    }

    @Override
    public long saveInvestmentPlan() {
        ObjectDao<ClosedTermInvestment> investmentPlanDao = new ObjectDao<ClosedTermInvestment>();
        return investmentPlanDao.addObject(this);
    }

    @Override
    public void updateInvestmentPlan() throws IllegalAccessException, InvocationTargetException {
        ObjectDao<ClosedTermInvestment> investmentPlanDao = new ObjectDao<ClosedTermInvestment>();
        investmentPlanDao.updateObject(this, this.getInvestmentPlanId(), ClosedTermInvestment.class);
    }

    @Override
    public void deleteInvestmentPlan() throws IllegalAccessException, InvocationTargetException {
        ObjectDao<ClosedTermInvestment> investmentPlanDao = new ObjectDao<ClosedTermInvestment>();
        investmentPlanDao.deleteObject(this, this.getInvestmentPlanId(), ClosedTermInvestment.class);
    }

    public static ClosedTermInvestment getClosedTermInvestmentById(long id) {
        ClosedTermInvestment investmentPlanHolder = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            investmentPlanHolder = (ClosedTermInvestment) session.get(ClosedTermInvestment.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return investmentPlanHolder;
    }

    public static ArrayList<ClosedTermInvestment> getClosedTermInvestments() {
        ArrayList<ClosedTermInvestment> investmentPlans;
        ObjectDao investmentPlanDao = new ObjectDao();
        investmentPlans = investmentPlanDao.getAllObjects("ClosedTermInvestment");
        return investmentPlans;
    }

}
