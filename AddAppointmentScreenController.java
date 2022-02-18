
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
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
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
 * This class will be used to controller the functionality of the "Add Appointment" GUI.
It should allow the user to add appointment information and save it to the database.
 */
public class AddAppointmentScreenController implements Initializable {

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
    private TextField startTimeTF;
    @FXML
    private TextField endTimeTF;
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
    
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    

    Timestamp current = new Timestamp(System.currentTimeMillis());
    LocalDateTime currentTime = current.toLocalDateTime();
    

    ObservableList<Contact> contactList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            gettingStarted();
        } catch (SQLException e) {
            Messages.connectionError();
        }
    }
    
/**This method will add a appointment to the database once the add button is pressed in the GUI.
 Before the appointment is saved the method will ensure that the appointment is within office hours
  and that the customer does not have an overlapping appointment.   
 */
    @FXML
    private void addAppointmentButtonAction(ActionEvent event) throws SQLException, IOException {
        //add the appointment to the database
        // LocalDateTime startTime = LocalDateTime.parse(MyTime.timeToUTC(), DateTimeFormatter.ISO_DATE);
        
        int appointmentID = Appointment.appointmentIDCounter();//Integer.parseInt(appointmentIDTF.getText());
        String title = titleTF.getText();
        String description = descriptionTF.getText();
        String location = locationTF.getText();
        String type = typeCB.getValue();
        String start = startTimeTF.getText();
        LocalDateTime startLDT = LocalDateTime.parse(start, dtf);
        Timestamp startTS = Timestamp.valueOf(startLDT);
        String end = endTimeTF.getText();
        LocalDateTime endLDT = LocalDateTime.parse(end, dtf);
        Timestamp endTS = Timestamp.valueOf(endLDT);
        LocalDateTime createDate = currentTime; //createDateTF.getText();
        Timestamp createDateTS = Timestamp.valueOf(createDate);
        String createdBy = User.currentUserName;//createdByTF.getText();
        int customerID = Integer.parseInt(customerIDTF.getText());
        int userID = 1;//User.getCurrentUserID();
        int contactID = getContactID(contactIDCB.getValue());
        LocalDateTime lastUpdate = currentTime;
        LocalDateTime lastUpdateLDT = currentTime;
        Timestamp lastUpdateTS  = Timestamp.valueOf(lastUpdateLDT);
        String lastUpdatedBy = User.currentUserName;

        //check if times are valid
        boolean officeOpen = MyTime.checkOfficeHours(startLDT);
        boolean apptConflict  = MyTime.checkCustomerApptAdding(startLDT, endLDT, customerID);
        if (officeOpen == true && apptConflict == true) {

            try {

                Connection con = ConnectDB.connection();
                PreparedStatement stmt = con.prepareStatement("INSERT INTO WJ077GX.appointments "
                        + "(Appointment_ID, "
                        + "Title, "
                        + "Description, "
                        + "Location, "
                        + "Type, "
                        + "Start, "
                        + "End, "
                        + "Create_Date, "
                        + "Created_By, "
                        + "Last_Update, "
                        + "Last_Updated_By, "
                        + "Customer_ID, "
                        + "User_ID, "
                        + "Contact_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                stmt.setInt(1, appointmentID);
                stmt.setString(2, title);
                stmt.setString(3, description);
                stmt.setString(4, location);
                stmt.setString(5, type);
                stmt.setTimestamp(6, startTS);
                stmt.setTimestamp(7, endTS);
                stmt.setTimestamp(8, createDateTS);
                stmt.setString(9, createdBy);
                stmt.setTimestamp(10, lastUpdateTS);
                stmt.setString(11, lastUpdatedBy);
                stmt.setInt(12, customerID);
                stmt.setInt(13, userID);
                stmt.setInt(14, contactID);

                //Send confirmation message
                Messages.appointmentAdded(start);
                //redirect UI back to appointmentScreen
                Parent appointments = FXMLLoader.load(getClass().getResource("/View/AppointmentScreen.fxml"));
                Scene scene = new Scene(appointments);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Appointments");
                stage.setScene(scene);
                stage.show();
            } catch (SQLException e) {
                e.printStackTrace();
                Messages.connectionError();
            } catch (IOException ee) {
                ee.printStackTrace();
            }
        } else {
            
        }

    }
/** 
 This method should collect all the contacts from the database, then match the 
 name gathered from the contactIDCB ComboBox to the contact in contactList.  Then
 it will return the contactID for the same contact.
 */
    private int getContactID(String contactName) throws SQLException {
        ObservableList<Contact> contactList = ConnectDB.collectContacts();
        int id = 0;
        Contact currentContact = null;
        String name = contactName;

        for (Contact contact : contactList) {
            if (name.equals(contact.getContactName())) {
                currentContact = contact;
                id = contact.getContactID();
            }
        }
        return id;
    }
/**
 This method should cancel the Appointment creation process and take the user 
 back AppointmentScreen.
 
 */
    @FXML
    private void cancelAppointmentButtonAction(ActionEvent event) throws IOException {
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
     This method is used to initiate AddAppointmentScreen.  It collects and sets
     ComboBox for contact ID and types.  It sets the appointmentID, createDate,
     and createdBy fields and sets editable to false for those fields.
     
     */
    private void gettingStarted() throws SQLException {

        ObservableList<Contact> contactList = ConnectDB.collectContacts();

        for (Contact contact : contactList) {
            contactIDCB.getItems().add(contact.getContactName());
        }
        for(String type : Appointment.appointmentTypes()){
            typeCB.getItems().add(type);
        }
        appointmentIDTF.setText(String.valueOf(Appointment.appointmentIDCounter()));
        appointmentIDTF.setEditable(false);
        createDateTF.setText(currentTime.format(dtf));
        createDateTF.setEditable(false);
        createdByTF.setText(User.currentUserName);
        createdByTF.setEditable(false);
    }
}
