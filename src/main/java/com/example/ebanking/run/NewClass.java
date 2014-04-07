/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ebanking.run;

import com.example.ebanking.model.*;
import com.example.ebanking.utils.DateUtil;
import java.lang.reflect.InvocationTargetException;
import org.joda.time.DateTime;

/**
 *
 * @author Khalid
 */
public class NewClass {

    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException {
//        ClosedTermInvestment closed = new ClosedTermInvestment(.04, 30, .15);
//        closed.saveInvestmentPlan();
//
//        OpenTermInvestment open = new OpenTermInvestment(0, 0, .004);
//        open.saveInvestmentPlan();
//
//        DateTime startDate = new DateTime(2014, 1, 30, 0, 0);
//        DateTime endDate = new DateTime(2014, 3, 30, 0, 0);
//        InvestmentAccount ia = new InvestmentAccount(startDate, endDate, open);
//       long id= ia.saveAccount();
        ClosedTermInvestment closed = ClosedTermInvestment.getClosedTermInvestmentById(2);
        InvestmentAccount ia = InvestmentAccount.getInvestmentAccountById(1);
//        ia.setAccountNumber("Investment0001");
//        ia.setClient(null);
//        ia.setBalance(1000);
//        ia.setInvestmentPlan(closed);
//        ia.updateAccount();
       
        DateTime today = new DateTime(2014, 3, 31, 0, 0);
        System.out.println(" The ROI = "+ia.calculateReturnOfInvestment(today));
        
    }
}
