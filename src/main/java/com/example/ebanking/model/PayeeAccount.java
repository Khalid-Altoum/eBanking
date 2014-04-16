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

@Entity
public class PayeeAccount extends Account implements Serializable {

    @ManyToOne(fetch = FetchType.EAGER)
    private Payee payee;

    public Payee getPayee() {
        return payee;
    }

    public void setPayee(Payee payee) {
        this.payee = payee;
    }

    @Override
    public void saveAccount() {
        ObjectDao<PayeeAccount> accountDao = new ObjectDao<PayeeAccount>();
        accountDao.addObject(this);
    }

    @Override
    public void updateAccount() {
        ObjectDao<PayeeAccount> payeeAccountDao = new ObjectDao<PayeeAccount>();
        payeeAccountDao.updateObject(this, this.getAccountId(), PayeeAccount.class);
    }

    @Override
    public void deleteAccount() {
        ObjectDao<PayeeAccount> payeeAccountDao = new ObjectDao<PayeeAccount>();
        payeeAccountDao.deleteObject(this, this.getAccountId(), PayeeAccount.class);
    }

    public static PayeeAccount getPayeeAccountById(long id) {
        ObjectDao<PayeeAccount> dao = new ObjectDao<PayeeAccount>();
        return dao.getObjectById(id, PayeeAccount.class);
    }

    public static ArrayList<PayeeAccount> getPayeeAccounts() {
        ObjectDao<PayeeAccount> dao = new ObjectDao<PayeeAccount>();
        return dao.getAllObjects(PayeeAccount.class, "PayeeAccount");
    }
}
