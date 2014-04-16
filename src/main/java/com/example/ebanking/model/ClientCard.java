/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ebanking.model;

import com.example.ebanking.dao.ObjectDao;
import com.example.ebanking.utils.JodaDateTimeConverter;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import javax.persistence.*;
import org.joda.time.DateTime;
@Entity
@Table
public class ClientCard implements Serializable {

    @Id
    @GeneratedValue
    protected Long clientCardId;
    private String cardNumber;

    @Convert(converter= JodaDateTimeConverter.class)
    private DateTime expiryDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private Client relatedClient;

    public ClientCard() {
    }

    public ClientCard(String cardNumber, DateTime expiryDate, Client client) {
        
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.relatedClient = client;
    }

    public Long getClientCardId() {
        return clientCardId;
    }

    public void setClientCardId(Long clientCardId) {
        this.clientCardId = clientCardId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public DateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(DateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Client getRelatedClient() {
        return relatedClient;
    }

    public void setRelatedClient(Client relatedClient) {
        this.relatedClient = relatedClient;
    }

    public void saveClientCard() throws IllegalAccessException, InvocationTargetException {
        ObjectDao<ClientCard> accountDao = new ObjectDao<ClientCard>();
        accountDao.addObject(this);
        
        updateRelatedClientUserName(this.relatedClient);
      }

    private void updateRelatedClientUserName(Client relatedClient)  {
        
        relatedClient.setUserName(this.cardNumber);
        relatedClient.updateUser();
    }

    public void updateClientCard() throws IllegalAccessException, InvocationTargetException {
        ObjectDao<ClientCard> accountDao = new ObjectDao<ClientCard>();
        accountDao.updateObject(this, this.getClientCardId(), ClientCard.class);
        
        updateRelatedClientUserName(this.relatedClient);
    }

    public void deleteClientCard() throws IllegalAccessException, InvocationTargetException {
        ObjectDao<ClientCard> accountDao = new ObjectDao<ClientCard>();
        accountDao.deleteObject(this, this.getClientCardId(), ClientCard.class);
    }

    public static ClientCard getClientCardById(long id) {
         ObjectDao<ClientCard> dao = new ObjectDao<ClientCard>();
        return dao.getObjectById(id, ClientCard.class);
    }
    
    public static ArrayList<ClientCard> getClientCards() {
         ObjectDao<ClientCard> dao = new ObjectDao<ClientCard>();
        return dao.getAllObjects(ClientCard.class, "ClientCard");
    }
}
