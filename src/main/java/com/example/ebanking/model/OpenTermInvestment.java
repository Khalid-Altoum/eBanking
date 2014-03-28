/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.ebanking.model;

import java.io.Serializable;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author HMD
 */
@Entity
@Table
@PrimaryKeyJoinColumn(name = "investmentPlanId")
  
    
public class OpenTermInvestment extends InvestmentPlan implements Serializable{
    @Id
    private Long openedTermInvestmentId;
    
    private double interestRate;

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public Long getOpenedTermInvestmentId() {
        return openedTermInvestmentId;
    }

    public void setOpenedTermInvestmentId(Long openedTermInvestmentId) {
        this.openedTermInvestmentId = openedTermInvestmentId;
    }
    
    
}
