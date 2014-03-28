/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ebanking.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table

public class Role implements Serializable{

    @Id
    @GeneratedValue
    private double roleId;
    
    @Column
    private String roleName;
    
    
//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private List<UserRole> userRoles;

    public Role() {
      //  this.userRoles = new ArrayList<UserRole>();
    }

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
