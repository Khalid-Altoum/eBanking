/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ebanking.bean;

import com.example.ebanking.model.Account;
import com.example.ebanking.model.ChequingAccount;
import com.example.ebanking.model.Client;
import com.example.ebanking.model.InvestmentAccount;
import com.example.ebanking.model.SavingAccount;
import java.util.ArrayList;
import java.util.HashMap;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;

/**
 *
 * @author PradeepSamuel
 */
@ManagedBean
@RequestScoped
public class TransferBean {

    /**
     * Creates a new instance of TransferBean
     */
    public TransferBean() {
    }

    private long fromAccount;
    private long toAccount;
    private HashMap<String, Long> fromAccounts;
    private HashMap<String, Long> toAccounts;
    private double amountToTransfer;

    public long getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(long fromAccount) {
        this.fromAccount = fromAccount;
    }

    public long getToAccount() {
        return toAccount;
    }

    public void setToAccount(long toAccount) {
        this.toAccount = toAccount;
    }

    public HashMap<String, Long> getFromAccounts() {
        fromAccounts = getfromClientAccounts();
        return fromAccounts;
    }

    public void setFromAccounts(HashMap<String, Long> fromAccounts) {
        this.fromAccounts = fromAccounts;
    }

    public HashMap<String, Long> getToAccounts() {
        return toAccounts;
    }

    public void setToAccounts(HashMap<String, Long> toAccounts) {
        this.toAccounts = toAccounts;
    }

    public double getAmountToTransfer() {
        return amountToTransfer;
    }

    public void setAmountToTransfer(double amountToTransfer) {
        this.amountToTransfer = amountToTransfer;
    }

    public String transferAmount() {
        System.out.println("==========> To Account" + toAccount);
        System.out.println("==========> From Account" + fromAccount);
        Account fromAccountObj = Account.getAccountById(fromAccount);
        Account toAccountObj = Account.getAccountById(toAccount);
        if (Account.transfer(fromAccountObj, toAccountObj, amountToTransfer, "Online Tranfer from account " + fromAccountObj.getAccountNumber())) {
            return "transfer";
        }
        return "loginError";
    }

    public void selectAccount(ValueChangeEvent event) {
        fromAccount = Long.parseLong(event.getNewValue().toString());
        toAccounts = getToClientAccounts();
    }

    private Client getClientfromSession() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        String clientNumber = (String) session.getAttribute("clientNumber");
        return Client.getClientByAccountNumber(clientNumber);
    }

    private HashMap<String, Long> getfromClientAccounts() {
        Client client = getClientfromSession();
        HashMap<String, Long> tempClientAccounts = new HashMap();
        ArrayList<Account> clientAccounts = Account.getAllClientAccounts(client.getUserId());
        for (Account clientAccount : clientAccounts) {
            tempClientAccounts.put(clientAccount.getAccountNumber() + " " + InferAccountType(clientAccount) + " Account", clientAccount.getAccountId());
        }
        return tempClientAccounts;
    }

    private HashMap<String, Long> getToClientAccounts() {
        Client client = getClientfromSession();
        HashMap<String, Long> tempClientAccounts = new HashMap();
        ArrayList<Account> clientAccounts = Account.getAllClientAccounts(client.getUserId());
        for (Account clientAccount : clientAccounts) {
            if (clientAccount.getAccountId() != fromAccount) {
                tempClientAccounts.put(clientAccount.getAccountNumber() + " " + InferAccountType(clientAccount) + " Account", clientAccount.getAccountId());
            }
        }
        return tempClientAccounts;
    }

    public String InferAccountType(Account selectedAccount) {
        String accountTypeInferred = "NA";
        if (selectedAccount instanceof ChequingAccount) {
            accountTypeInferred = "Chequing";
        } else if (selectedAccount instanceof SavingAccount) {
            accountTypeInferred = "Saving";
        } else if (selectedAccount instanceof InvestmentAccount) {
            accountTypeInferred = "Investment";
        }
        return accountTypeInferred;
    }
}
