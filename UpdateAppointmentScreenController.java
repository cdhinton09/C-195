
package Controller;

import Database.ConnectDB;
import Database.MyTime;
import Model.Appointment;
import Model.Contact;
import Model.Messages;
import Model.User;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *This class is used to control the Update Appointment Screen.  Allowing the user
 * to update existing appointments.
 */
public class UpdateAppointmentScreenController implements Initializable {

    private Appointment selectedAppt = AppointmentController.selectedAppt;

    @FXML
    private TextField appointmentIDTF;

    @FXML
    private TextField titleTF;

    @FXML
    private TextField descriptionTF;

    @FXML
    private TextField locationTF;

    @FXML
    private TextField typeTF;
    
    @FXML
    private ComboBox<String> typeCB;

    @FXML
    private TextField startTF;

    @FXML
    private TextField endTF;

    @FXML
    private TextField createDateTF;

    @FXML
    private TextField createdByTF;

    @FXML
    private TextField customerIDTF;

    @FXML
    private TextField userIDTF;

    @FXML
    private ComboBox<String> contactIDCB;

    /**
     * This method initializes the Update Appointment Screen.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gettingStarted();
    }

    /**
     * This method populates all TextFields and ComboBoxs with information from
     * the appointment selected on the Appointment Screen.  It then fills in the
     * rest of the ComboBox information.
     */
    public void gettingStarted() {
        try {
            appointmentIDTF.setText(String.valueOf(selectedAppt.getAppointmentID()));
            appointmentIDTF.setEditable(false);
            titleTF.setText(selectedAppt.getTitle());
            descriptionTF.setText(selectedAppt.getDescription());
            locationTF.setText(selectedAppt.getLocation());
            //typeTF.setText(selectedAppt.getType());
            typeCB.setValue(selectedAppt.getType());
            System.out.println("The Appointment starts at: " + selectedAppt.getStart());

            startTF.setText(selectedAppt.getStart().format(MyTime.dtf).toString());
            System.out.println("The Appointment ends at: " + selectedAppt.getEnd());

            endTF.setText(selectedAppt.getEnd().format(MyTime.dtf).toString());
            customerIDTF.setText(String.valueOf(selectedAppt.getCustomerID()));
            userIDTF.setText(String.valueOf(selectedAppt.getUserID()));
            userIDTF.setEditable(false);
            createdByTF.setText(selectedAppt.getCreatedBy());
            createDateTF.setText(selectedAppt.getCreateDate().format(MyTime.dtf).toString());
            
            for(String s : Appointment.appointmentTypes()){
                typeCB.getItems().add(s);
            }

            try {
                //Fill the ComboBox
                for (Contact contact : ConnectDB.collectContacts()) {
                    contactIDCB.getItems().add(contact.getContactName());
                }
                for (Contact contact : ConnectDB.collectContacts()) {
                    if (contact.getContactID() == selectedAppt.getContactID()) {
                        contactIDCB.setValue(contact.getContactName());
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
/**
 * This method takes in the contact name from the ComboBox and returns the Contact's
 * ContactID number.  
 */
    public int getContactID(String contactName) throws SQLException {
        //contactList;
        ObservableList<Contact> contactList = ConnectDB.collectContacts();//ConnectDB.collectContacts(ConnectDB.contactsStatement);
        int id = 0;
        Contact currentContact = null;
        String name = contactName;

        for (Contact contact : contactList) {
            if (name.equals(contact.getContactName())) {
                //System.out.println("This is the Contacts name: " + contact.getContactName());
                currentContact = contact;
                id = contact.getContactID();
            }
        }
        return id;
    }

    /**
     * This is an Event Handler for the cancel button.  It will dump all unsaved 
     * data and direct the user to the Appointment Screen.
     */
    @FXML
   public void cancelAppointmentButtonAction(ActionEvent event) throws IOException {
        boolean answer = Messages.cancelConfirmation();
        if (answer == true) {
            Parent cancelButton = FXMLLoader.load(getClass().getResource("/View/AppointmentScreen.fxml"));
            Scene scene = new Scene(cancelButton);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Appointment");
            stage.setScene(scene);
            stage.show();
        }
    }

   /**
    * This is an Event Handler for the update button.  It will ensure the requested
    * appointment times are within office hours and not overlapping with an existing
    * appointment for that customer.  It will then send and UPDATE query to the 
    * database. 
    */
    @FXML
   public void updateAppointmentButtonAction(ActionEvent event) throws IOException {
        Timestamp current = new Timestamp(System.currentTimeMillis());
        LocalDateTime currentLDT = current.toLocalDateTime();

        try {
            String title = titleTF.getText();
            String description = descriptionTF.getText();
            String location = locationTF.getText();
            String type = typeCB.getValue();//typeTF.getText();
            String start = startTF.getText();
            LocalDateTime startLDT = LocalDateTime.parse(start, MyTime.dtf);
            Timestamp startTS = Timestamp.valueOf(startLDT);
            String end = endTF.getText();
            LocalDateTime endLDT = LocalDateTime.parse(end, MyTime.dtf);
            Timestamp endTS = Timestamp.valueOf(endLDT);
            String createDate = createDateTF.getText();
            LocalDateTime createDateLDT = LocalDateTime.parse(createDate, MyTime.dtf);
            Timestamp createDateTS = Timestamp.valueOf(createDateLDT);
            String createdBy = createdByTF.getText();
            int customerID = Integer.parseInt(customerIDTF.getText());
            int userID = Integer.parseInt(userIDTF.getText());
            int contactID = getContactID(contactIDCB.getValue());
            String lastUpdate = currentLDT.format(MyTime.dtf);
            LocalDateTime lastUpdateLDT = LocalDateTime.parse(lastUpdate, MyTime.dtf);
            Timestamp lastUpdateTS = Timestamp.valueOf(lastUpdateLDT);
            String lastUpdatedBy = User.getCurrentUserName();
            int appointmentID = selectedAppt.getAppointmentID();

            boolean officeOpen = MyTime.checkOfficeHours(startLDT);
            boolean apptConflict = MyTime.checkCustomerApptUpdating(startLDT, endLDT, customerID, appointmentID);

            try {

                if (officeOpen == true && apptConflict == true) {
                    try {

                        Connection con = ConnectDB.connection();
                        PreparedStatement stmt = con.prepareStatement("UPDATE WJ077GX.appointments SET "
                                + "Title = ?, "
                                + "Description = ?, "
                                + "Location = ?, "
                                + "Type = ?, "
                                + "Start = ?, "
                                + "End = ?, "
                                + "Create_Date = ?, "
                                + "Created_By = ?, "
                                + "Last_Update = ?, "
                                + "Last_Updated_By = ?, "
                                + "Customer_ID = ?,"
                                + "User_ID = ?, "
                                + "Contact_ID = ? "
                                + "WHERE Appointment_ID = ?");
                        stmt.setString(1, title);
                        stmt.setString(2, description);
                        stmt.setString(3, location);
                        stmt.setString(4, type);
                        stmt.setTimestamp(5, startTS);
                        stmt.setTimestamp(6, endTS);
                        stmt.setTimestamp(7, createDateTS);
                        stmt.setString(8, createdBy);
                        stmt.setTimestamp(9, lastUpdateTS);
                        stmt.setString(10, lastUpdatedBy);
                        stmt.setInt(11, customerID);
                        stmt.setInt(12, userID);
                        stmt.setInt(13, contactID);
                        stmt.setInt(14, appointmentID);
                        int result = stmt.executeUpdate();

                        String resultString = "";
                        if (result == 0) {
                            resultString = "The Appt was NOT updated";
                        } else {
                            resultString = "The Appt was updated";
                        }
                        System.out.println(resultString);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    Parent apptScreen = FXMLLoader.load(getClass().getResource("/View/AppointmentScreen.fxml"));
                    Scene scene = new Scene(apptScreen);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.setTitle("Appointment");
                    stage.show();
                } else {
                    Messages.apptTimeOverlap();
                }
                /*  } catch (SQLException e) {
                e.printStackTrace();*/
            } catch (NullPointerException ee) {
                ee.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Messages.connectionError();
        }
    }

}
