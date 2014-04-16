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
public class OpenTermInvestment extends InvestmentPlan implements Serializable {

    public OpenTermInvestment() {
    }

    public OpenTermInvestment(double penaltyPercent, int durationInDays, double investmentReturnsPercent) {

        this.setPenaltyPercent(penaltyPercent);
        this.setDurationInDays(durationInDays);
        this.setInvestmentReturnsPercent(investmentReturnsPercent);

    }

    @Override
    public void saveInvestmentPlan() {
        ObjectDao<OpenTermInvestment> investmentPlanDao = new ObjectDao<OpenTermInvestment>();
        investmentPlanDao.addObject(this);
    }

    @Override
    public void updateInvestmentPlan()  {
        ObjectDao<OpenTermInvestment> investmentPlanDao = new ObjectDao<OpenTermInvestment>();
        investmentPlanDao.updateObject(this, this.getInvestmentPlanId(), OpenTermInvestment.class);
    }

    @Override
    public void deleteInvestmentPlan() {
        ObjectDao<OpenTermInvestment> investmentPlanDao = new ObjectDao<OpenTermInvestment>();
        investmentPlanDao.deleteObject(this, this.getInvestmentPlanId(), OpenTermInvestment.class);
    }

    public static OpenTermInvestment getOpenTermInvestmentById(long id) {
       ObjectDao<OpenTermInvestment> dao = new ObjectDao<OpenTermInvestment>();
        return dao.getObjectById(id, OpenTermInvestment.class);
    }

    public static ArrayList<OpenTermInvestment> getOpenTermInvestments() {
      ObjectDao<OpenTermInvestment> dao = new ObjectDao<OpenTermInvestment>();
        return dao.getAllObjects(OpenTermInvestment.class, "OpenTermInvestment");  
    }
}
