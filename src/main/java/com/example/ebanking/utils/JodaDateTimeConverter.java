/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ebanking.utils;

import java.sql.Timestamp;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.joda.time.DateTime;

@Converter(autoApply=true)
public class JodaDateTimeConverter implements AttributeConverter<DateTime, Timestamp>{
    private static final long serialVersionUID = 1L;

    @Override
    public Timestamp convertToDatabaseColumn(DateTime attribute) {  
        return attribute == null ? null : new Timestamp(((DateTime) attribute).getMillis());    
    }

    @Override
    public DateTime convertToEntityAttribute(Timestamp dbData) {
        return dbData == null ? null : new DateTime((Timestamp) dbData);
    }


}
