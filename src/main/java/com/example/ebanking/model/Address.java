/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ebanking.model;


import com.example.ebanking.dao.ObjectDao;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="Address")
public class Address implements Serializable {

    @Id
    @GeneratedValue
    private long addressId;

  
    private String streetNumber;
    private String streetName;
    private String apartmentNumber;
    private String city;
    private String province;
    private String postalCode;
    private String country;

   

    public Address() {
    }

    public Address(String streetNumber, String streetName, String apartmentNumber, String city, String province, String postalCode, String country) {
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.apartmentNumber = apartmentNumber;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.country = country;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void saveAddress() {
        ObjectDao<Address> accountDao = new ObjectDao<Address>();
         accountDao.addObject(this);
    }

    public void updateAddress(){
        ObjectDao<Address> accountDao = new ObjectDao<Address>();
        accountDao.updateObject(this, this.getAddressId(), Address.class);
    }

    public void deleteAddress(){
        ObjectDao<Address> accountDao = new ObjectDao<Address>();
        accountDao.deleteObject(this, this.getAddressId(), Address.class);
    }

    public ArrayList<Address> getAllAddresses() {
        ObjectDao<Address> dao = new ObjectDao<Address>();
        return dao.getAllObjects(Address.class, "Address");
    }
}
