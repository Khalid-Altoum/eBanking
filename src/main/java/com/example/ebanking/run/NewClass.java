/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.ebanking.run;

import com.example.ebanking.model.*;
import com.example.ebanking.utils.DateUtil;
import org.joda.time.DateTime;

/**
 *
 * @author Khalid
 */
public class NewClass {
    public static void main(String[] args) {
        ClosedTermInvestment closed = new ClosedTermInvestment(.04, 30, .15);
        closed.saveInvestmentPlan();
        
        OpenTermInvestment open= new OpenTermInvestment(0, 0, .004);
        open.saveInvestmentPlan();
    
    
    
    
    
    
    
    
    
    
    
    
    }
}
