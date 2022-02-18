package Model;

import Database.ConnectDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import javafx.scene.control.Alert;

import static javafx.application.Platform.exit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import softwaretwo.SoftwareTwo;

/**
 * This class contains all messages used throughout the application.
 */
public class Messages {

    public static Alert warning = new Alert(Alert.AlertType.WARNING);
    public static Alert error = new Alert(Alert.AlertType.ERROR);
    public static Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
    public static Alert information = new Alert(Alert.AlertType.INFORMATION);

    /**
     * Displays a message if the user enters an incorrect username or password.
     */
    public static void failedLogin() {
        warning.setTitle(SoftwareTwo.languageCheck("Failed_Login"));
        warning.setContentText(SoftwareTwo.languageCheck("You_have_entered_incorrect_username_or_password_Please_try_again"));
        warning.show();
    }

    /**
     * Displays a message if the user requests an appointment that is outside
     * the company's office hours.
     */
    public static void outsideBusinessHours() {
        information.setTitle("Appointment Outside Office Hours.");
        information.setContentText("The appointment time that you have "
                + "requested is outside of our offices normal business hours.  "
                + "Please choose a different time or contact our office for other "
                + "option.");
        information.show();
    }

    /**
     * Displays a message when the user attempts to exit the program. The
     * message ask for confirmation before exiting
     */
    public static void exitConfirmation() {
        confirmation.setTitle("Ready to exit?");
        confirmation.setContentText("Are you ready to exit the application?");
        confirmation.showAndWait();
        exit();
    }

    /**
     * Displays a message when the user attempts to cancel changes to an
     * appointment or customer record. It will wait for confirmation before
     * canceling.
     */
    public static boolean cancelConfirmation() {
        boolean answer = true;
        confirmation.setTitle("Cancel");
        confirmation.setContentText("By confirming you will lose all unsaved data.");

        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.get() == ButtonType.OK) {
            answer = true;
        } else {
            answer = false;
        }
        return answer;
    }

    /**
     * Displays a message if the application has trouble connecting to the
     * database.
     */
    public static void connectionError() {
        error.setTitle("Connection Error");
        error.setContentText("There has been an error while connecting to your database.  "
                + "Please contact support or try again later.");
        error.show();
    }

    /**
     * Displays a message when the user successfully logs in.
     */
    public static void welcome(String name) {
        information.setTitle("Welcome");
        information.setContentText("Welcome " + name + ", have a great day!");
        information.show();
    }

    /**
     * Displays a message when a new Customer is successfully added to the
     * database.
     */
    public static void customerAdded(String newCustomerName) {
        information.setTitle("Customer Added");
        information.setContentText(newCustomerName + " was successfully added!");
        information.show();
    }

    /**
     * Displays a message when a Customer is successfully update in the
     * database.
     */
    public static void customerUpdate(String customerUpdate) {
        confirmation.setTitle("Updating Customer Record");
        confirmation.setContentText("By confirming, you will be making "
                + "changes to the current customers record.  Do you wish to "
                + "accept changes?");
        confirmation.showAndWait();
    }

    /**
     * Displays a message when a Customer is successfully deleted from the
     * database. It will wait for confirmation. If confirmation is given, it
     * will delete the customer from the database.
     */
    public static void customerDelete(Customer customerBeingDeleted) throws SQLException {
        warning.setTitle("Delete Customer");
        warning.setContentText("You are about to delete " + customerBeingDeleted.getCustomerName()
                + "'s record.  Do you wish to delete " + customerBeingDeleted.getCustomerName()
                + " and all of their appointments?");

        Optional<ButtonType> result = warning.showAndWait();
        if (result.get() == ButtonType.OK) {
            ObservableList<Appointment> list = ConnectDB.collectAppointments();
            for (int i = 0; i < list.size(); i++) {
                //if the customerBeingDeleted.customerID == list.get(i).getCustomerID
                if (list.get(i).getCustomerID() == customerBeingDeleted.getCustomerID()) {
                    //delete the appointment
                    Appointment toRemove = list.get(i);
                    Appointment.removeAppointment(toRemove);
                }

            }
            Customer.removeCustomer(customerBeingDeleted);
        }

    }

    /**
     * Displays a message when a Appointment is successfully deleted from the
     * database. It will wait for confirmation. If confirmation is given, it
     * will delete the appointment from the database.
     */
    public static void appointmentDelete(Appointment apptBeingDeleted) throws SQLException {
        try {
            warning.setTitle("Delete Customer");
            warning.setContentText("You are about to delete Appointment ID: " + apptBeingDeleted.getAppointmentID()
                    + " scheduled on " + apptBeingDeleted.getStart()
                    + ".  Please confirm you wish to delete this appointment.");

            Optional<ButtonType> result = warning.showAndWait();
            if (result.get() == ButtonType.OK) {
                Appointment.removeAppointment(apptBeingDeleted);
            } else {

            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays a message when a Appointment is successfully deleted from the
     * database. It will wait for confirmation. If confirmation is given, it
     * will delete the appointment from the database.
     */
    public static void appointmentUpdate(Appointment appointment) {
        confirmation.setTitle("Update Appointment");
        confirmation.setContentText("You are about to make a permanent "
                + "change to the ");
    }

    /**
     * Displays a message when the user enters invalid information.
     */
    public static void invalidInput() {
        error.setTitle(SoftwareTwo.languageCheck("Invalid_Input"));
        error.setContentText(SoftwareTwo.languageCheck("You_input_is_invalid._Please_enter_valid_input."));
        error.show();
    }

    /**
     * Displays a message when the user attempts to update or delete without
     * making a selection.
     */
    public static void makeSelection() {
        error.setTitle("Failed Selection");
        error.setContentText("You have failed to select a file to update.  "
                + "Please make a selection.");
        error.show();
    }

    /**
     * Displays a message when the user successfully adds an appointment.
     */
    public static void appointmentAdded(String start) {
        information.setTitle("Appointment Added");
        information.setContentText("Your new appointment for " + start
                + " has been added to the system.");
        information.show();
    }

    /**
     * Displays a message when the user does not have an appointment during the
     * specified time period.
     */
    public static void noAppts(String userName) {
        information.setTitle("No Appointments");
        information.setContentText(userName + ", you have no appointements during this time period.");
        information.show();
    }

    /**
     * Displays a message when the user has an appointment within 15 minutes.
     */
    public static void apptWithin15() {
        information.setTitle("Appointment Within 15 Mintutes");
        information.setContentText("You have an appointment scheduled "
                + "to start within the next 15 minutes.");
        information.showAndWait();
    }

    /**
     * Displays a message when the user attempts to update or add an appointment
     * that would overlap with an existing appointment.
     */
    public static void apptTimeOverlap() {
        error.setTitle("Time Overlap");
        error.setContentText("The requested time you have entered overlaps "
                + "another appointment for this user.  Please change the requested times.");
        error.showAndWait();
    }

}
