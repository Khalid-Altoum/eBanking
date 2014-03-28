/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ebanking.model;

import java.io.Serializable;
import javax.persistence.Id;

/**
 *
 * @author HMD
 */
public class Role implements Serializable{

    @Id
    private double roleId;
    private String roleName;

    public double getRoleId() {
        return roleId;
    }

    public void setRoleId(double roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
