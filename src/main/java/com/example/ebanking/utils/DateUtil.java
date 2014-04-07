/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ebanking.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.mail.internet.ParseException;
import org.joda.time.DateTime;
import org.joda.time.Days;

/**
 *
 * @author Khalid
 */
public class DateUtil {

    public static int DateDifference(DateTime fromDate, DateTime toDate) {
        int days = Days.daysBetween(fromDate, toDate).getDays();
        return days;

    }

    public static DateTime addDays(DateTime aDate, int days) {
        DateTime newDate = null;
        try {
            newDate = aDate.plusDays(days);
        } catch (Exception e) {
            return null;
        }
        return newDate;

    }
}
