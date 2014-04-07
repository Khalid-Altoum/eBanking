/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ebanking.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pr_danie
 */
@ManagedBean
@RequestScoped
public class LoginBean {

    private String accountNumber;
    private String password;
    private String userRole;

    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.login(this.accountNumber, this.password);
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage("Login failed."));
            return "loginError";
        } finally {
            if (this.accountNumber != null) {
                HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
                session.setAttribute("clientNumber", this.accountNumber);
                session.setAttribute("roleName", /* User.getRoleFromUserEmail(this.username) */ "");
                // userRole = User.getRoleFromUserEmail(this.username);
            }
        }
        return "accounts";
    }

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.logout();
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage("Logout failed."));
            return "loginError";
        }
        return "/index";
    }

}
