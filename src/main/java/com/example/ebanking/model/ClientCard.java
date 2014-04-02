/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ebanking.model;

import com.example.ebanking.dao.ObjectDao;
import com.example.ebanking.persistence.HibernateUtil;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table
public class ClientCard implements Serializable {

    @Id
    @GeneratedValue
    protected Long cardId;

    @Column
    private String cardNumber;

    @Column
    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    private DateTime expiryDate;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Client relatedClient;
   
    public ClientCard() {
    }

    public ClientCard(String cardNumber, DateTime expiryDate) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
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

    public long saveClientCard() throws IllegalAccessException, InvocationTargetException {
        ObjectDao<ClientCard> accountDao = new ObjectDao<ClientCard>();
        updateRelatedClientUserName();
        
        return accountDao.addObject(this);
        
    }

    private void updateRelatedClientUserName() throws IllegalAccessException, InvocationTargetException {
        long clientId=this.relatedClient.getUserId();
        Client client=Client.getClientsById(clientId);
        client.setUserName(cardNumber);
        client.updateUser();
    }

    public void updateClientCard() throws IllegalAccessException, InvocationTargetException {
        ObjectDao<ClientCard> accountDao = new ObjectDao<ClientCard>();
        accountDao.updateObject(this, this.getCardId(), ClientCard.class);
        updateRelatedClientUserName();
    }

    public void deleteClientCard() throws IllegalAccessException, InvocationTargetException {
        ObjectDao<ClientCard> accountDao = new ObjectDao<ClientCard>();
        accountDao.deleteObject(this, this.getCardId(), ClientCard.class);
    }

    public static ClientCard getClientCardById(long id) {
        ClientCard cardHolder = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            cardHolder = (ClientCard) session.get(ClientCard.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return cardHolder;
    }
}
