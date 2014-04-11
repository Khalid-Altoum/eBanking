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
import com.example.ebanking.model.Payee;
import com.example.ebanking.model.PayeeAccount;
import com.example.ebanking.model.SavingAccount;
import java.util.ArrayList;
import java.util.Map;
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
    private ArrayList<PayeeAccount> payeeList;

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

    public ArrayList<PayeeAccount> getPayeeList() {
        payeeList = getPayeeAccountsForClient();
        return payeeList;
    }

    public void setPayeeList(ArrayList<PayeeAccount> payeeList) {
        this.payeeList = payeeList;
    }

    public String addPayee() {
        if (accountNumber != null && payeeName != null) {
            PayeeAccount payeeAccount = new PayeeAccount();
            payeeAccount.setAccountNumber(accountNumber);
            payeeAccount.setPayee(Payee.getPayeeByName(payeeName));
            payeeAccount.setOpenedDate(DateTime.now());
            payeeAccount.setCurrency("CAD");
            payeeAccount.setCurrencySign("$");
            payeeAccount.setStatus(Account.AccountStatus.ACTIVE);
            payeeAccount.setClient(getClientfromSession());
            payeeAccount.setBalance(0);
            payeeAccount.saveAccount();
            return "addPayee";
        }
        return "payeeAddError";
    }

    public Client getClientfromSession() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        String clientNumber = (String) session.getAttribute("clientNumber");
        return Client.getClientByAccountNumber(clientNumber);
    }

    public ArrayList<PayeeAccount> getPayeeAccountsForClient() {
        Client client = getClientfromSession();
        ArrayList<Account> allAccounts = Account.getAllClientAccounts(client.getUserId());
        ArrayList<PayeeAccount> payeeAccounts = (ArrayList) Account.getPayeeAccounts(allAccounts);
        return payeeAccounts;
    }

    public String InferAccountType(Account selectedAccount) {
        String accountTypeInferred = "NA";
        if (selectedAccount instanceof ChequingAccount) {
            accountTypeInferred = "Chequing";
        } else if (selectedAccount instanceof SavingAccount) {
            accountTypeInferred = "Saving";
        } else if (selectedAccount instanceof InvestmentAccount) {
            accountTypeInferred = "Investment";
        } else if (selectedAccount instanceof PayeeAccount) {
            accountTypeInferred = "Payee";
        }
        return accountTypeInferred;
    }
    
    public String naviagateToPayeeTransfer(){
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String,String> parameters = context.getExternalContext().getRequestParameterMap();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        session.setAttribute("selectedPayeeAccountId", parameters.get("selectedPayeeAccountId"));
        return "tranferToPayee";
    }
}
