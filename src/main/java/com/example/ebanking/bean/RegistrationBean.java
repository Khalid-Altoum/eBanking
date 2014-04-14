/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ebanking.bean;

import com.example.ebanking.model.Address;
import com.example.ebanking.model.Client;
import com.example.ebanking.model.ClientCard;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.joda.time.DateTime;

/**
 *
 * @author PradeepSamuel
 */
@ManagedBean
@RequestScoped
public class RegistrationBean {

    private String firstName;
    private String lastName;
    private String password;
    private String emailAddress;
    private String phoneNumber;
    private ArrayList<String> genderList;
    private String gender;
    private String aptNumber;
    private String streetName;
    private String streetNumber;
    private String city;
    private String postalCode;
    private String province;
    private String country;
    private String clientIdentifier;
    private ArrayList<String> clientIdentifierList;
    private String clientCardNumber;

    /**
     * Creates a new instance of RegistrationBean
     */
    public RegistrationBean() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAptNumber() {
        return aptNumber;
    }

    public void setAptNumber(String aptNumber) {
        this.aptNumber = aptNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ArrayList<String> getGenderList() {
        genderList = new ArrayList();
        genderList.add("Male");
        genderList.add("Female");
        return genderList;
    }

    public void setGenderList(ArrayList<String> genderList) {
        this.genderList = genderList;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getClientIdentifier() {
        return clientIdentifier;
    }

    public void setClientIdentifier(String clientIdentifier) {
        this.clientIdentifier = clientIdentifier;
    }

    public ArrayList<String> getClientIdentifierList() {
        clientIdentifierList = getListOfClientIdentifiers();
        return clientIdentifierList;
    }

    public void setClientIdentifierList(ArrayList<String> clientIdentifierList) {
        this.clientIdentifierList = clientIdentifierList;
    }

    public String getClientCardNumber() {
        return clientCardNumber;
    }

    public void setClientCardNumber(String clientCardNumber) {
        this.clientCardNumber = clientCardNumber;
    }

    public String registerClient() {
        Address clientAddress = new Address();

        clientAddress.setApartmentNumber(aptNumber);
        clientAddress.setCity(city);
        clientAddress.setCountry(country);
        clientAddress.setPostalCode(postalCode);
        clientAddress.setProvince(province);
        clientAddress.setStreetName(streetName);
        clientAddress.setStreetNumber(streetNumber);
        clientAddress.saveAddress();

        Client client = new Client();
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setGender(gender);
        client.setPassword(encryptPassword(password));
        client.setPhoneNumber(phoneNumber);
        client.setEmail(emailAddress);
        client.setUserAddress(clientAddress);
        client.saveUser();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("displayusersave", new FacesMessage("User Registered Successfully"));
        return "registration";
    }

    private static String encryptPassword(String password) {
        String sha1 = "";
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sha1;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private ArrayList<String> getListOfClientIdentifiers() {
        ArrayList<Client> tempClientList = Client.getClients();
        ArrayList<String> accIdList = new ArrayList();
        for (Client client : tempClientList) {
            accIdList.add(client.getUserId() + "-" + client.getFirstName() + "-" + client.getLastName() + "-" + client.getEmail());
        }
        return accIdList;
    }

    public String registerClientCard() throws IllegalAccessException, InvocationTargetException {
        Client clientSelected = getClientFromClientIdentifier();
        ClientCard clientCard = new ClientCard(getClientCardNumber(), DateTime.now(), clientSelected);
        clientCard.saveClientCard();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("displayccardsave", new FacesMessage("Client Card Saved Successfully"));
        return "createClientCard";
    }

    private Client getClientFromClientIdentifier() {
        String delimiter = "-";
        String[] temp;
        temp = clientIdentifier.split(delimiter);
        return Client.getClientsById(Long.parseLong(temp[0]));
    }
}
