/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.ebanking.model;

import com.example.ebanking.dao.ObjectDao;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import javax.persistence.*;

@Entity
@Table
@PrimaryKeyJoinColumn(name = "accountId")
public class SavingAccount extends Account implements Serializable{
    
  
    public SavingAccount(){super();}
    
    
    
    
      public void saveSavingAccount() {
        ObjectDao<SavingAccount> savingAccountDao = new ObjectDao<SavingAccount>();
        savingAccountDao.addObject(this);
    }

    public void updateSavingAccount(){
        ObjectDao<SavingAccount> savingAccountDao = new ObjectDao<SavingAccount>();
        savingAccountDao.updateObject(this, this.getAccountId(), SavingAccount.class);
    }

    public void deleteSavingAccount() throws IllegalAccessException, InvocationTargetException {
        ObjectDao<SavingAccount> savingAccountDao = new ObjectDao<SavingAccount>();
        savingAccountDao.deleteObject(this, this.getAccountId(), SavingAccount.class);
    }

    public static SavingAccount getSavingAccountById(long id) {
        ObjectDao<SavingAccount> dao = new ObjectDao<SavingAccount>();
        return dao.getObjectById(id, SavingAccount.class);
    }

    public static ArrayList<SavingAccount> getSavingAccounts() {
        ObjectDao<SavingAccount> dao = new ObjectDao<SavingAccount>();
        return dao.getAllObjects(SavingAccount.class, "SavingAccount");
    }
}
