/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Model.Messages;
import Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import static java.time.ZoneOffset.UTC;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *This class is used to manipulate time in the rest of the application.
 */
public class MyTime {

    /**
     * This DateTimeFormatter is the primary format used throughout the application.
     */
    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * This method should convert a local time to UTC time.  It takes a LocalDateTime
     * as a parameter and returns a LocalDateTime.
     */ 
    public static LocalDateTime localToUTC(LocalDateTime date) {
        LocalDateTime originLDT;
        LocalDateTime targetLDT;
        ZonedDateTime originZDT;
        ZonedDateTime targetZDT;

        //Start with LocalDateTime
        originLDT = date;
        //convert to ZonedDateTime at origin ZoneId
        originZDT = originLDT.atZone(ZoneId.systemDefault());
        //convert that to a ZonedDateTime at the target ZoneId
        targetZDT = originZDT.withZoneSameInstant(ZoneId.of("UTC"));
        //Convert that tot the changed LocalDateTime
        targetLDT = targetZDT.toLocalDateTime();
        //format targetLDT
        LocalDateTime formattedLDT = LocalDateTime.parse(targetLDT.format(dtf), MyTime.dtf);

        return targetLDT;
    }

    /**
     * This method should convert a UTC time to local time.  It takes a LocalDateTime
     * as a parameter and returns a LocalDateTime.
     */
    public static LocalDateTime utcToLocal(Timestamp utcTime) {
        LocalDateTime originLDT;
        LocalDateTime targetLDT;
        ZonedDateTime originZDT;
        ZonedDateTime targetZDT;
        ZoneId utc = UTC;
        ZoneId local = ZoneId.systemDefault();

        //take in Timestamp in UTC
        //convert Timestamp to LDT in UTC
        originLDT = utcTime.toLocalDateTime();
        //convert LDT to ZDT in UTC
        originZDT = originLDT.atZone(utc);
        //convert ZDT in UTC to ZDT in local
        targetZDT = originZDT.withZoneSameLocal(local);
        //Convert ZDT in local to LDT in local 
        targetLDT = targetZDT.toLocalDateTime();

        LocalDateTime formattedLDT = LocalDateTime.parse(targetLDT.format(dtf), MyTime.dtf);

        return targetLDT;
    }


    /**
     * This method should convert a local time to EST time.  It takes a LocalDateTime
     * as a parameter and returns a LocalDateTime.
     */
    public static LocalDateTime timeToEST(LocalDateTime date) {
        LocalDateTime originLDT;
        LocalDateTime targetLDT;
        ZonedDateTime originZDT;
        ZonedDateTime targetZDT;
        //Start with LocalDateTime
        originLDT = date;
        //convert to ZonedDateTime at origin ZoneId
        originZDT = originLDT.atZone(ZoneId.systemDefault());
        //convert that to a ZonedDateTime at the target ZoneId
        targetZDT = originZDT.withZoneSameInstant(ZoneId.of("US/Eastern"));
        //Convert that tot the changed LocalDateTime
        targetLDT = targetZDT.toLocalDateTime();

        LocalDateTime formattedLDT = LocalDateTime.parse(targetLDT.format(dtf), MyTime.dtf);

        return targetLDT;
    }

    /**
     * This method checks if a requested appointment is within company business hours (EST).
     * It takes in a LocalDateTime parameter and returns a boolean.
     */ 
    public static boolean checkOfficeHours(LocalDateTime date) {
        boolean allowAppt = true;
        LocalDateTime dateEST = timeToEST(date);
        LocalTime apptTime = dateEST.toLocalTime();
        LocalTime opening = LocalTime.of(8, 00);
        LocalTime closing = LocalTime.of(22, 00);
        System.out.println("open from: " + opening + " - " + closing);
        
        
        if (apptTime.isBefore(opening) || apptTime.isAfter(closing)) {
           
            //Allow the appointment to be created
            allowAppt = false;
            Model.Messages.outsideBusinessHours();
        }

        return allowAppt;
    }
/**
 * This method check to see if the user has any appointments within the next 15 minutes.
 * It queries the database for all of the users appointment.  Then compares the 
 * current time to the start times of all the users appointments.
 * @param user
 * @throws SQLException 
 */
    public static void check15MinuteWarning(User user) throws SQLException {
        int userID = user.getUserID();
        LocalDateTime loginDT = LocalDateTime.now();
        LocalTime loginTime = loginDT.toLocalTime();
        Connection con = ConnectDB.connection();
        PreparedStatement ps = con.prepareStatement("SELECT Start FROM appointments WHERE User_Id = ?");
        ps.setInt(1, userID);
        ResultSet rs = ps.executeQuery();
        //should compare all start appt times for the user to the current time
        //if an appt is within 15 min of the login time
        //create a list of start times
        ObservableList<LocalTime> startTimes = FXCollections.observableArrayList();
        //add all start times from the users schedule
        while (rs.next()) {
            LocalDateTime nextDT = rs.getObject("User_ID", LocalDateTime.class);
            LocalTime nextTime = nextDT.toLocalTime();
            startTimes.add(nextTime);
        }
        //compare the start times to the login time, if within 15 min, send out warning
        for (LocalTime localTime : startTimes) {
            if (localTime.equals(loginTime)) {
                Messages.apptWithin15();
            }
        }
    }

   
/**
 * This method check if the specified customer has a appointment with a start time
 * that will overlap with the requested appointment.
 * @param apptTimeStart
 * @param apptTimeEnd
 * @param customerID
 * @return 
 */
    public static boolean checkCustomerApptAdding(LocalDateTime apptTimeStart, LocalDateTime apptTimeEnd, int customerID) {
        boolean allowAppt = true;
        //convert requested time to EST
        LocalDateTime requestedDateTimeStart = timeToEST(apptTimeStart);
        LocalDateTime requestedDateTimeEnd = timeToEST(apptTimeEnd);
        //take in the requested appt time split into LocalTime
      //  LocalTime requestedTimeStart = requestedDateTimeStart.toLocalTime();
      //  LocalTime requestedTimeEnd = requestedDateTimeEnd.toLocalTime();
        //Create an observablelist of LocalTime appointment times
        ObservableList<LocalDateTime> customerStartApptList = ConnectDB.apptListStartTimeAddingAppt(customerID);
        ObservableList<LocalDateTime> customerEndApptList = ConnectDB.apptListEndTimeAddingAppt(customerID);
        //compare requested start time to the customers other appts

        //When requested start is in the window of another appt
        //RStart >= SStart && RStart < SEnd
        for (int i = 0; i < customerStartApptList.size(); i++) {
            System.out.println("this is the requested start time: " + requestedDateTimeStart
                    + ". This is the time being compared: " + customerStartApptList.get(i)
                    + ". This is the requested end time: " + requestedDateTimeEnd
                    + ". This is the time being compared: " + customerEndApptList.get(i));
            if ((requestedDateTimeStart.isAfter(customerStartApptList.get(i))
                    || requestedDateTimeStart.equals(customerStartApptList.get(i)))
                    && requestedDateTimeStart.isBefore(customerEndApptList.get(i))) {
                allowAppt = false;
                System.out.println("After the first check:" + allowAppt);
                Messages.apptTimeOverlap();
            } //When the requested end time is in the the window of another appt
            //REnd > SStart && REnd <= SEnd
            else if (requestedDateTimeEnd.isAfter(customerStartApptList.get(i))
                    && ((requestedDateTimeEnd.isBefore(customerEndApptList.get(i)))
                    || requestedDateTimeEnd.equals(customerEndApptList.get(i)))) {
                allowAppt = false;
                Messages.apptTimeOverlap();
                System.out.println("After the second check: " + allowAppt);
            } //When the requested start time is before the window start and the requested end in after the window end
            //RStart <= SStart && REnd >= SEnd
            else if (((requestedDateTimeStart.equals(customerStartApptList.get(i)))
                    || requestedDateTimeStart.isBefore(customerStartApptList.get(i)))
                    && ((requestedDateTimeEnd.equals(customerEndApptList.get(i)))
                    || requestedDateTimeEnd.isAfter(customerEndApptList.get(i)))) {
                allowAppt = false;
                Messages.apptTimeOverlap();
                System.out.println("After the third check: " + allowAppt);
            }
        }

        return allowAppt;
    }
/**
 * This method check if the specified customer has a appointment with a end time
 * that will overlap with the requested appointment.
 * @param apptTimeStart
 * @param apptTimeEnd
 * @param customerID
 * @param apptID
 * @return 
 */
    public static boolean checkCustomerApptUpdating(LocalDateTime apptTimeStart, LocalDateTime apptTimeEnd, int customerID, int apptID) {
        boolean allowAppt = true;
        LocalDateTime requestedDateTimeStart = timeToEST(apptTimeStart);
        LocalDateTime requestedDateTimeEnd = timeToEST(apptTimeEnd);
       
        ObservableList<LocalDateTime> customerStartApptList = ConnectDB.apptListStartTimeUpdatingAppt(customerID, apptID);
        ObservableList<LocalDateTime> customerEndApptList = ConnectDB.apptListEndTimeUpdatingAppt(customerID, apptID);
        
        for (int i = 0; i < customerStartApptList.size(); i++) {
            System.out.println("this is the requested start time: " + requestedDateTimeStart
                    + ". This is the time being compared: " + customerStartApptList.get(i)
                    + ". This is the requested end time: " + requestedDateTimeEnd
                    + ". This is the time being compared: " + customerEndApptList.get(i));
            if ((requestedDateTimeStart.isAfter(customerStartApptList.get(i))
                    || requestedDateTimeStart.equals(customerStartApptList.get(i)))
                    && requestedDateTimeStart.isBefore(customerEndApptList.get(i))) {
                allowAppt = false;
                System.out.println("After the first check:" + allowAppt);
                return allowAppt;
            } 
            else if (requestedDateTimeEnd.isAfter(customerStartApptList.get(i))
                    && ((requestedDateTimeEnd.isBefore(customerEndApptList.get(i)))
                    || requestedDateTimeEnd.equals(customerEndApptList.get(i)))) {
                allowAppt = false;
                System.out.println("After the second check: " + allowAppt);
                return allowAppt;
            } 
            else if (((requestedDateTimeStart.equals(customerStartApptList.get(i)))
                    || requestedDateTimeStart.isBefore(customerStartApptList.get(i)))
                    && ((requestedDateTimeEnd.equals(customerEndApptList.get(i)))
                    || requestedDateTimeEnd.isAfter(customerEndApptList.get(i)))) {
                allowAppt = false;
                System.out.println("After the third check: " + allowAppt);
                return allowAppt;
            }
        }

        return allowAppt;
    }
}
