/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.ebanking.run;
import com.example.ebanking.model.*;
import java.lang.reflect.InvocationTargetException;
import java.text.NumberFormat;
import java.util.Locale;
import org.joda.time.DateTime;
/**
 *
 * @author Khalid
 */
public class test1DB {
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException {
//        Account ch = new Account();
//        ch.setAccountNumber("CH0000000000000001");
//        ch.setBalance(50000);
//        ch.setOpenedDate(DateTime.now());
//        ch.saveAccount();
//        
      Account ac = Account.getAccountById(1);
     ac.deposite(1000);
     ac.withdraw(500.90);
     ac.updateAccount();
        
        
//         String s =NumberFormat.getCurrencyInstance(Locale.CANADA).format(55);
//         System.out.println(s);
    }
}
