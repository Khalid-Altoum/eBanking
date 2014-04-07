/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ebanking.bean;

import com.example.ebanking.model.Account;
import com.example.ebanking.model.ChequingAccount;
import com.example.ebanking.model.InvestmentAccount;
import com.example.ebanking.model.SavingAccount;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author PradeepSamuel
 */
@ManagedBean
@RequestScoped
public class AccountSummary {

    /**
     * Creates a new instance of AccountSummary
     */
    public AccountSummary() {
    }

    private Account selectedAccount;
    private String accountType;

    public Account getSelectedAccount() {
        selectedAccount = getSelectedAccountFromSession();
        return selectedAccount;
    }

    public String getAccountType() {
        accountType = InferAccountType(selectedAccount);
        return accountType;
    }

    private Account getSelectedAccountFromSession() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        String selectedAccountId = (String) session.getAttribute("selectedAccountId");
        return Account.getAccountById(Long.parseLong(selectedAccountId));
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
