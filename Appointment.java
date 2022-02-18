/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Database.ConnectDB;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.*;

/**
 *
 * This class defines the Appointment object type and its methods.
 */
public class Appointment {

    private int appointmentID, customerID, userID, contactID;
    private String title, description, type, createdBy, lastUpdatedBy, location;
    private LocalDateTime start, end, createDate, lastUpdate;
    String getStatement = "SELECT ? FROM customers WHERE AppointmentID = ?";

    /**
     * This is the standard constructor for appointments.
     */
    public Appointment(int appointmentID, int customerID, int userID,
            int contactID, String title, String description, String type,
            String createdBy, String lastUpdatedBy, LocalDateTime start, LocalDateTime end,
            LocalDateTime createDate, LocalDateTime lastUpdate, String location) {
        this.appointmentID = appointmentID;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        this.title = title;
        this.description = description;
        this.type = type;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
        this.location = location;
    }

    /**
     * Getter method for AppointmentID
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * Setter method for AppointmentID
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * Getter method for CustomerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Setter method for CustomerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Getter method for UserID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Setter method for UserID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Getter method for CcontactID
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Setter method for CcontactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Getter method for Title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter method for Title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter method for Description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter method for Description
     */
    public void setDescription(String decsription) {
        this.description = decsription;
    }

    /**
     * Getter method for Type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter method for Type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter method for CreatedBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Setter method for CreatedBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Getter method for LastUpdatedBy
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Setter method for LastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Getter method for Start
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Setter method for Start
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Getter method for End
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Setter method for End
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * Getter method for CreateDate
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Setter method for CreateDate
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Getter method for LastUpdated
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Setter method for LastUpdated
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Getter method for Location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Setter method for Location
     */
    public void setLocation(String location) {
        this.location = location;
    }
    /**
     * An ObservableList designed to hold all Appointments.
     */
    public static ObservableList allAppointments = FXCollections.observableArrayList();
    //private static ObservableList<Appointment> allAppointments;

    /**
     * This should return allAppointments.
     *
     * @return
     */
    public static ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }

    /**
     * This should add an appointment
     *
     * @param appointment
     */
    public static void addAppointment(Appointment appointment) {
        allAppointments.add(appointment);

    }

    /**
     * This should remove an appointment
     *
     * @param appointment
     * @throws SQLException
     */
    public static void removeAppointment(Appointment appointment) throws SQLException {
        //allAppointments.remove(appointment.appointmentID);
        Connection con = ConnectDB.connection();
        PreparedStatement stmt = con.prepareStatement("DELETE from WJ077GX.appointments WHERE Appointment_ID = ?");
        stmt.setInt(1, appointment.getAppointmentID());
        int result = stmt.executeUpdate();

        if (result == 1) {
            System.out.println("The deletion was successful");
        } else {
            System.out.println("The deletion has failed");
        }
    }

    /**
     * The method should keep track of the highest appointmentID by querying the
     * database.
     *
     * @return
     */
    public static int appointmentIDCounter() {
        int count = 0;
        try {
            Connection con = ConnectDB.connection();
            PreparedStatement stmt = con.prepareCall("SELECT MAX(Appointment_ID) AS nextIDNum FROM appointments");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                count = rs.getObject("nextIDNum", Integer.class);
                count += 1;
            }

        } catch (SQLException e) {
            Messages.connectionError();
        }
        return count;
    }

    /**
     * This method creates a list of appointment types to be used throughout the
     * application.
     *
     * @return
     */
    public static ObservableList<String> appointmentTypes() {
        ObservableList<String> list = FXCollections.observableArrayList();

        list.add("Briefing");
        list.add("Planning");
        list.add("Review");
        list.add("Status Update");
        list.add("Performance Report");
        list.add("Other");
        return list;
    }
}
