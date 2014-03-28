/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ebanking.run;

import com.example.ebanking.model.*;
import java.lang.reflect.InvocationTargetException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.joda.time.DateTime;

/**
 *
 * @author Khalid
 */
public class test1DB {

    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException {

//        Client cl = new Client();
//        cl.setFirstName("FirstName2");
//        cl.setLastName("LastName2");
//        cl.setAge(99);
//        cl.setEmail("a@b.com");
//        cl.setGender("Male");
//        cl.setUserName("01234567890");
//        cl.setPassword("1234");
//        cl.setPhoneNumber("514-000-9999");
//
//        //  cl = Client.getClientsById(id);
//        ChequingAccount ca = new ChequingAccount();
//        ca.setAccountNumber("Check002");
//        ca.setBalance(50000);
//        ca.setOpenedDate(DateTime.now());
//        ca.setClient(cl);
//
//        SavingAccount sa = new SavingAccount();
//        sa.setAccountNumber("Check002");
//        sa.setBalance(50000);
//        sa.setOpenedDate(DateTime.now());
//        sa.setClient(cl);
//
//        List<Account> accounts = new ArrayList<Account>();
//        accounts.add(sa);
//        accounts.add(ca);
//
//        cl.setAccounts(accounts);
//        long id = cl.saveUser();

        
        
        
        
        Client cl2 = Client.getClientsById(8);
        
        cl2.getAccounts().toArray().toString();
        
        
        
        
//        Account ch = new Account();
//        ch.setAccountNumber("CH0000000000000001");
//        ch.setBalance(50000);
//        ch.setOpenedDate(DateTime.now());
//        ch.saveAccount();
//        
//        Account ac = Account.getAccountById(1);
//        ac.deposite(1000, "Deposit");
//        ac.withdraw(500.90, "Withdraw");
//        ac.updateAccount();
//         String s =NumberFormat.getCurrencyInstance(Locale.CANADA).format(55);
//         System.out.println(s);
    }
}
