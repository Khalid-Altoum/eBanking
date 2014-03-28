/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ebanking.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author HMD
 */
@Entity
@Table
@PrimaryKeyJoinColumn(name = "userId")
public class Client extends User implements Serializable{
    
    @Column
    private long age;

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }
    
    
    

}
