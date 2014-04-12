/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ebanking.bean;

import com.example.ebanking.model.InvestmentPlan;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author PradeepSamuel
 */
@ManagedBean
@RequestScoped
public class InvestmentBean {

    /**
     * Creates a new instance of InvestmentBean
     */
    public InvestmentBean() {
    }

    private double returnPercentage;
    private int durationInDays;
    private double penaltyPercentage;
    private ArrayList<InvestmentPlan> investmentPlanList;

    public double getReturnPercentage() {
        return returnPercentage;
    }

    public void setReturnPercentage(double returnPercentage) {
        this.returnPercentage = returnPercentage;
    }

    public int getDurationInDays() {
        return durationInDays;
    }

    public void setDurationInDays(int durationInDays) {
        this.durationInDays = durationInDays;
    }

    public double getPenaltyPercentage() {
        return penaltyPercentage;
    }

    public void setPenaltyPercentage(double penaltyPercentage) {
        this.penaltyPercentage = penaltyPercentage;
    }

    public ArrayList<InvestmentPlan> getInvestmentPlanList() {
        investmentPlanList = InvestmentPlan.getInvestmentPlans();
        return investmentPlanList;
    }

    public void setInvestmentPlanList(ArrayList<InvestmentPlan> investmentPlanList) {
        this.investmentPlanList = investmentPlanList;
    }

    public String addInvestmentPlan() {
        InvestmentPlan investmentPlan = new InvestmentPlan();
        investmentPlan.setDurationInDays(durationInDays);
        investmentPlan.setInvestmentReturnsPercent(returnPercentage);
        investmentPlan.setPenaltyPercent(penaltyPercentage);
        investmentPlan.saveInvestmentPlan();
        return "createInvestmentPlan";
    }
    
    

}
