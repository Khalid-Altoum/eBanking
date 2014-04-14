/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ebanking.bean;

import com.example.ebanking.model.Account;
import com.example.ebanking.model.Client;
import com.example.ebanking.model.ClosedTermInvestment;
import com.example.ebanking.model.InvestmentAccount;
import com.example.ebanking.model.InvestmentPlan;
import com.example.ebanking.model.OpenTermInvestment;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;

/**
 *
 * @author PradeepSamuel
 */
@ManagedBean
@RequestScoped
public class InvestmentBean {

    /**
     * Creates a new instance of InvestmentBean
     */
    public InvestmentBean() {
    }

    private double returnPercentage;
    private int durationInDays;
    private double penaltyPercentage;
    private ArrayList<InvestmentPlan> investmentPlanList;
    private ArrayList<InvestmentPlan> openInvestmentPlanList;
    private ArrayList<InvestmentPlan> closedInvestmentPlanList;
    private InvestmentPlan selectedInvestmentPlan;
    private String clientAccountNumber;
    private Date startDate;
    private Date endDate;
    private ArrayList<String> investmentTypeList;
    private String investmentType;
    private ArrayList<String> clientAccountNumberList;

    public double getReturnPercentage() {
        return returnPercentage;
    }

    public void setReturnPercentage(double returnPercentage) {
        this.returnPercentage = returnPercentage;
    }

    public int getDurationInDays() {
        return durationInDays;
    }

    public void setDurationInDays(int durationInDays) {
        this.durationInDays = durationInDays;
    }

    public double getPenaltyPercentage() {
        return penaltyPercentage;
    }

    public void setPenaltyPercentage(double penaltyPercentage) {
        this.penaltyPercentage = penaltyPercentage;
    }

    public ArrayList<InvestmentPlan> getInvestmentPlanList() {
        investmentPlanList = InvestmentPlan.getInvestmentPlans();
        return investmentPlanList;
    }

    public void setInvestmentPlanList(ArrayList<InvestmentPlan> investmentPlanList) {
        this.investmentPlanList = investmentPlanList;
    }

    public InvestmentPlan getSelectedInvestmentPlan() {
        selectedInvestmentPlan = getInvestmentPlanFromSession();
        return selectedInvestmentPlan;
    }

    public void setSelectedInvestmentPlan(InvestmentPlan selectedInvestmentPlan) {
        this.selectedInvestmentPlan = selectedInvestmentPlan;
    }

    public String getClientAccountNumber() {
        return clientAccountNumber;
    }

    public void setClientAccountNumber(String clientAccountNumber) {
        this.clientAccountNumber = clientAccountNumber;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public ArrayList<String> getClientAccountNumberList() {
        clientAccountNumberList = getListOfClients();
        return clientAccountNumberList;
    }

    public void setClientAccountNumberList(ArrayList<String> clientAccountNumberList) {
        this.clientAccountNumberList = clientAccountNumberList;
    }

    public ArrayList<InvestmentPlan> getOpenInvestmentPlanList() {
        openInvestmentPlanList = (ArrayList) InvestmentPlan.getOpenTermInvestments(getInvestmentPlanList());
        return openInvestmentPlanList;
    }

    public void setOpenInvestmentPlanList(ArrayList<InvestmentPlan> openInvestmentPlanList) {
        this.openInvestmentPlanList = openInvestmentPlanList;
    }

    public ArrayList<InvestmentPlan> getClosedInvestmentPlanList() {
        closedInvestmentPlanList = (ArrayList) InvestmentPlan.getClosedTermInvestments(getInvestmentPlanList());
        return closedInvestmentPlanList;
    }

    public void setClosedInvestmentPlanList(ArrayList<InvestmentPlan> closedInvestmentPlanList) {
        this.closedInvestmentPlanList = closedInvestmentPlanList;
    }

    public ArrayList<String> getInvestmentTypeList() {
        ArrayList<String> tempList = new ArrayList();
        tempList.add("open");
        tempList.add("closed");
        investmentTypeList = tempList;
        return investmentTypeList;
    }

    public void setInvestmentTypeList(ArrayList<String> investmentTypeList) {
        this.investmentTypeList = investmentTypeList;
    }

    public String getInvestmentType() {
        return investmentType;
    }

    public void setInvestmentType(String investmentType) {
        this.investmentType = investmentType;
    }

    public String addInvestmentPlan() {
        if (investmentType.equalsIgnoreCase("open")) {
            OpenTermInvestment openInvestment = new OpenTermInvestment(penaltyPercentage, durationInDays, returnPercentage);
            openInvestment.saveInvestmentPlan();
        } else if (investmentType.equalsIgnoreCase("closed")) {
            ClosedTermInvestment closedInvestment = new ClosedTermInvestment(penaltyPercentage, durationInDays, returnPercentage);
            closedInvestment.saveInvestmentPlan();
        }
        return "createInvestmentPlan";
    }

    public String selectPlanforClient() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> parameters = context.getExternalContext().getRequestParameterMap();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        session.setAttribute("selectedInvestmentPlanId", parameters.get("selectedInvestmentPlanId"));
        return "addInvestment";
    }

    private InvestmentPlan getInvestmentPlanFromSession() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        String selectedInvestmentPlanId = (String) session.getAttribute("selectedInvestmentPlanId");
        return InvestmentPlan.getInvestmentPlanById(Long.parseLong(selectedInvestmentPlanId));
    }

    public String createInvestmentForClient() {
        InvestmentAccount investmentAccount = new InvestmentAccount(ConvertStartDateToDateTime(), ConvertEndDateToDateTime(), getSelectedInvestmentPlan());
        investmentAccount.setAccountNumber("Invest" + generateAccountNumber());
        investmentAccount.setClient(getSelectedClient());
        investmentAccount.setStatus(Account.AccountStatus.ACTIVE);
        investmentAccount.saveAccount();
        return "createInvestmentPlan";
    }

    private Client getSelectedClient() {
        Client client = Client.getClientByAccountNumber(clientAccountNumber);
        if (client != null) {
            return client;
        }
        return new Client();
    }

    private DateTime ConvertStartDateToDateTime() {
        MutableDateTime startDateMutable = new MutableDateTime(startDate);
        return startDateMutable.toDateTime();
    }

    private DateTime ConvertEndDateToDateTime() {
        MutableDateTime endDateMutable = new MutableDateTime(startDate);
        endDateMutable.addDays(durationInDays);
        return endDateMutable.toDateTime();
    }

    private double generateAccountNumber() {
        double Max = 100000;
        double Min = 1;
        return Math.random() * (Max - Min);
    }

    private ArrayList<String> getListOfClients() {
        ArrayList<Client> tempClientList = Client.getClients();
        ArrayList<String> accNumberList = new ArrayList();
        for (Client client : tempClientList) {
            accNumberList.add(client.getUserName());
        }
        return accNumberList;
    }
}
