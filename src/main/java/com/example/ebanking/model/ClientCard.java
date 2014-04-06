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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table
public class ClientCard implements Serializable {

    @Id
    @GeneratedValue
    protected Long clientCardId;

    @Column
    private String cardNumber;

    @Column
    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    private DateTime expiryDate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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

    public long saveClientCard() throws IllegalAccessException, InvocationTargetException {
        ObjectDao<ClientCard> accountDao = new ObjectDao<ClientCard>();

        long cardId= accountDao.addObject(this);
        updateRelatedClientUserName(this.relatedClient,cardId);
        return cardId;

    }

    private void updateRelatedClientUserName(Client relatedClient,long cardId) throws IllegalAccessException, InvocationTargetException {
        
        relatedClient.setUserName(this.cardNumber);
        ArrayList<ClientCard> cards = new ArrayList<ClientCard>();
        cards.add(ClientCard.getClientCardById(cardId));
        relatedClient.setClientCards(cards);
        relatedClient.updateUser();
    }

    public void updateClientCard() throws IllegalAccessException, InvocationTargetException {
        ObjectDao<ClientCard> accountDao = new ObjectDao<ClientCard>();
        accountDao.updateObject(this, this.getClientCardId(), ClientCard.class);
        updateRelatedClientUserName(this.relatedClient,this.getClientCardId());
    }

    public void deleteClientCard() throws IllegalAccessException, InvocationTargetException {
        ObjectDao<ClientCard> accountDao = new ObjectDao<ClientCard>();
        accountDao.deleteObject(this, this.getClientCardId(), ClientCard.class);
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
