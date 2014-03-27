/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.ebanking.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 *
 * @author HMD
 */
@Entity
@Table(name = "InvestmentPlan")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class InvestmentPlan {
    
}
