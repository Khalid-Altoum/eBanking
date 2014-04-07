/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ebanking.bean;

import com.example.ebanking.model.Account;
import com.example.ebanking.model.Client;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pr_danie
 */
@ManagedBean
@RequestScoped
public class AccountsBean {

    /**
     * Creates a new instance of AccountsBean
     */
    public AccountsBean() {
    }

    private ArrayList<Account> personalAccountsList;
    private ArrayList<Account> investmentAccountsList;

    public ArrayList<Account> getPersonalAccountsList() {
        populatePersonalAccounts();
        return personalAccountsList;
    }

    public void setPersonalAccountsList(ArrayList<Account> personalAccountsList) {
        this.personalAccountsList = personalAccountsList;
    }

    public ArrayList<Account> getInvestmentAccountsList() {
        populateInvestmentAccounts();
        return investmentAccountsList;
    }

    public void setInvestmentAccountsList(ArrayList<Account> investmentAccountsList) {
        this.investmentAccountsList = investmentAccountsList;
    }

    public void populatePersonalAccounts() {
        Client client = getClientfromSession();
        ArrayList<Account> allAccounts = Account.getAllClientAccounts(client.getUserId());
        ArrayList<Account> personalAccounts = (ArrayList) Account.getPersonalAccount(allAccounts);
        personalAccountsList = personalAccounts;
    }

    public void populateInvestmentAccounts() {
        Client client = getClientfromSession();
        ArrayList<Account> allAccounts = Account.getAllClientAccounts(client.getUserId());
        ArrayList<Account> investmentAccounts = (ArrayList) Account.getInvestmentAccounts(allAccounts);
        investmentAccountsList = investmentAccounts;
    }

    public Client getClientfromSession() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        String clientNumber = (String) session.getAttribute("clientNumber");
        return Client.getClientByAccountNumber(clientNumber);
    }

    public String naviagateToAccount() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String,String> parameters = context.getExternalContext().getRequestParameterMap();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        session.setAttribute("selectedAccountId", parameters.get("selectedAccountId"));
        return "accountsSummary";
    }
}
