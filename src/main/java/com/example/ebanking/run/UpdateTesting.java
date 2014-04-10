/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.ebanking.run;

import com.example.ebanking.model.*;


public class UpdateTesting {
    public static void main(String[] args) {
        SavingAccount sa = SavingAccount.getSavingAccountById(1);
        ChequingAccount ca = ChequingAccount.getCheckingAccountById(2);
        
        Account.transfer(ca, sa, 100, "Paying my load");
    }
}
