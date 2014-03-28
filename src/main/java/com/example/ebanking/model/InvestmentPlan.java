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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author HMD
 */
@Entity
@Table(name = "InvestmentPlan")
@Inheritance(strategy = InheritanceType.JOINED)
public class InvestmentPlan implements Serializable{
    @Id
    @GeneratedValue
    private Long investmentPlanId;

    public Long getInvestmentPlanId() {
        return investmentPlanId;
    }

    public void setInvestmentPlanId(Long id) {
        this.investmentPlanId = id;
    }
    
    

    public long saveInvestmentPlan()  {
        ObjectDao<InvestmentPlan> investmentPlanDao = new ObjectDao<InvestmentPlan>();
        return investmentPlanDao.addObject(this);
    }

    public void updateInvestmentPlan() throws IllegalAccessException, InvocationTargetException {
        ObjectDao<InvestmentPlan> investmentPlanDao = new ObjectDao<InvestmentPlan>();
        investmentPlanDao.updateObject(this, this.getInvestmentPlanId(), InvestmentPlan.class);
    }

    public void deleteInvestmentPlan() throws IllegalAccessException, InvocationTargetException {
        ObjectDao<InvestmentPlan> investmentPlanDao = new ObjectDao<InvestmentPlan>();
        investmentPlanDao.deleteObject(this, this.getInvestmentPlanId(), InvestmentPlan.class);
    }

    public static InvestmentPlan getInvestmentPlanById(long id) {
        InvestmentPlan investmentPlanHolder = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            investmentPlanHolder = (InvestmentPlan) session.get(InvestmentPlan.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return investmentPlanHolder;
    }

    public static ArrayList<InvestmentPlan> getInvestmentPlans() {
        ArrayList<InvestmentPlan> investmentPlans;
        ObjectDao investmentPlanDao = new ObjectDao();
        investmentPlans = investmentPlanDao.getAllObjects("InvestmentPlan");
        return investmentPlans;
    }
}
