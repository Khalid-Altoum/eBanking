/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ebanking.bean;

import com.example.ebanking.model.Account;
import com.example.ebanking.model.Client;
import com.example.ebanking.model.Payee;
import com.example.ebanking.model.PayeeAccount;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.joda.time.DateTime;

/**
 *
 * @author PradeepSamuel
 */
@ManagedBean
@RequestScoped
public class PayeeBean {

    /**
     * Creates a new instance of PayeeBean
     */
    public PayeeBean() {
    }

    private String payeeName;
    private String accountNumber;

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String addPayee() {
        PayeeAccount payeeAccount = new PayeeAccount();
        payeeAccount.setAccountNumber(accountNumber);
        payeeAccount.setPayee(Payee.getPayeeByName(payeeName));
        payeeAccount.setOpenedDate(DateTime.now());
        payeeAccount.setCurrency("Canadian Dollars");
        payeeAccount.setCurrencySign("$");
        payeeAccount.setStatus(Account.AccountStatus.ACTIVE);
        payeeAccount.setClient(getClientfromSession());
        payeeAccount.setBalance(0);
        payeeAccount.saveAccount();
        return "payees";
    }

    public Client getClientfromSession() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        String clientNumber = (String) session.getAttribute("clientNumber");
        return Client.getClientByAccountNumber(clientNumber);
    }
}
