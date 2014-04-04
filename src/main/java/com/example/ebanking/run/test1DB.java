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
import java.util.Iterator;
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
        
//        Client cl2 = Client.getClientsById(8);
//        
//        List ss = Account.getPersonalAccount(cl2.getAccounts());
//        
//        for (Iterator<Account> it = ss.iterator(); it.hasNext();) {
//            String ac = it.next().toString();
//            System.out.println(ac);
//        }
      
        
        Address address = new Address();
        address.setApartmentNumber("1502");
        address.setCity("Montreal");
        address.setCountry("Canada");
        address.setPostalCode("H3H 2J2");
        address.setProvince("Quebec");
        address.setStreetName("Rue St.Mathieu");
        address.setStreetNumber("2055");
        address.saveAddress();
        
        Client client = new Client();
        client.setEmail("pradeep.samuel90@gmail.com");
        client.setFirstName("Pradeep Samuel");
        client.setGender("Male");
        client.setLastName("Daniel");
        client.setPassword("1234");
        client.setPhoneNumber("514-430-8730");
        client.setUserName("pradeep samuel");
        client.setUserAddress(address);
        client.setAge(23);
        
        Account account = new Account(1200, client);
        account.setAccountNumber("345662636251673");
        account.setCurrency("CAD");
        account.setCurrencySign("$");
        account.setOpenedDate(new DateTime());
        account.setStatus(Account.AccountStatus.ACTIVE);
        
        account.saveAccount();
        
        ArrayList<Account> accounts = new ArrayList();
        accounts.add(account);
        
        client.setAccounts(accounts);
        client.saveUser();
    }
}
