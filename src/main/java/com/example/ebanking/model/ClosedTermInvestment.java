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


/**
 *
 * @author HMD
 */
@Entity
public class ClosedTermInvestment extends InvestmentPlan implements Serializable {

    public ClosedTermInvestment() {
    }

    public ClosedTermInvestment(double penaltyPercent, int durationInDays, double investmentReturnsPercent) {

        this.setPenaltyPercent(penaltyPercent);
        this.setDurationInDays(durationInDays);
        this.setInvestmentReturnsPercent(investmentReturnsPercent);

    }

    @Override
    public void saveInvestmentPlan() {
        ObjectDao<ClosedTermInvestment> investmentPlanDao = new ObjectDao<ClosedTermInvestment>();
        investmentPlanDao.addObject(this);
    }

    @Override
    public void updateInvestmentPlan()  {
        ObjectDao<ClosedTermInvestment> investmentPlanDao = new ObjectDao<ClosedTermInvestment>();
        investmentPlanDao.updateObject(this, this.getInvestmentPlanId(), ClosedTermInvestment.class);
    }

    @Override
    public void deleteInvestmentPlan() {
        ObjectDao<ClosedTermInvestment> investmentPlanDao = new ObjectDao<ClosedTermInvestment>();
        investmentPlanDao.deleteObject(this, this.getInvestmentPlanId(), ClosedTermInvestment.class);
    }

    public static ClosedTermInvestment getClosedTermInvestmentById(long id) {
        ObjectDao<ClosedTermInvestment> dao = new ObjectDao<ClosedTermInvestment>();
        return dao.getObjectById(id, ClosedTermInvestment.class);
    }

    public static ArrayList<ClosedTermInvestment> getClosedTermInvestments() {
        ObjectDao<ClosedTermInvestment> dao = new ObjectDao<ClosedTermInvestment>();
        return dao.getAllObjects(ClosedTermInvestment.class, "ClosedTermInvestment");
    }

}
