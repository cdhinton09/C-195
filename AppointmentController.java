/*
This Class is the main hub for all actions involving appointments.  It will have 
methods that all users to add, delete, and update appointments.  I will allow
users to see all appointments, current week appointments, and current month appointments.
 */
package Controller;

import Database.ConnectDB;
import Model.Appointment;
import Model.Messages;
import Model.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AppointmentController implements Initializable {

    @FXML
    private Button addApptButton;

    @FXML
    private Button updateApptButton;

    @FXML
    private Button deleteApptButton;

    @FXML
    private Button backApptButton;

    @FXML
    private TableView<Appointment> appointmentListTV;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIDTC;

    @FXML
    private TableColumn<Appointment, String> titleTC;

    @FXML
    private TableColumn<Appointment, String> descriptionTC;

    @FXML
    private TableColumn<Appointment, String> locationTC;

    @FXML
    private TableColumn<Appointment, String> contactTC;

    @FXML
    private TableColumn<Appointment, String> typeTC;

    @FXML
    private TableColumn<Appointment, LocalDateTime> startTimeTC;

    @FXML
    private TableColumn<Appointment, LocalDateTime> endTimeTC;

    @FXML
    private TableColumn<Appointment, Integer> customerIDTC;

    @FXML
    private TableColumn<Appointment, Integer> userIDTC;
    
     // Create Radio Button Group
    private ToggleGroup apptGroup = new ToggleGroup();
    
    @FXML
    private RadioButton allApptRB;

    @FXML
    private RadioButton weeklyApptRB;

    @FXML
    private RadioButton monthlyApptRB;
    
    ObservableList appointmentList = FXCollections.observableArrayList();
    
    public static Appointment selectedAppt ;
    
    
    
/** 
 This method initializes the AppointmentScreen.  
 */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        appointmentListTV.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        allApptRB.setToggleGroup(apptGroup);
        allApptRB.setSelected(true);
        weeklyApptRB.setToggleGroup(apptGroup);
        monthlyApptRB.setToggleGroup(apptGroup);
        try {
            gettingStarted();
            

        } catch (SQLException e) {
            Messages.connectionError();
        }

    }

    /*
     This method will gather data from the database and fill the allAppointment 
     TableView.  It also sets all CellFactory for the TableColumns. 
     
     
     
     */
    public void gettingStarted() throws SQLException {
        
        apptGroup.selectToggle(allApptRB);
        appointmentList = ConnectDB.collectAppointments();//.selectPS("*", "appointments");//Appointment.buildAllAppointments();
        appointmentListTV.setItems(appointmentList);
        appointmentIDTC.setCellValueFactory(new PropertyValueFactory<>("AppointmentID"));
        titleTC.setCellValueFactory(new PropertyValueFactory<>("Title"));
        descriptionTC.setCellValueFactory(new PropertyValueFactory<>("Description"));
        locationTC.setCellValueFactory(new PropertyValueFactory<>("Location"));
        contactTC.setCellValueFactory(new PropertyValueFactory<>("ContactID"));
        typeTC.setCellValueFactory(new PropertyValueFactory<>("Type"));
        startTimeTC.setCellValueFactory(new PropertyValueFactory<>("Start"));
        endTimeTC.setCellValueFactory(new PropertyValueFactory<>("End"));
        customerIDTC.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        userIDTC.setCellValueFactory(new PropertyValueFactory<>("UserID"));
    }

    /**
     This method is an Event Handler that sends the user to the Add Appointment Screen.  
     */
    @FXML
    public void addButtonAction(ActionEvent event) throws IOException {

        Parent addButton = FXMLLoader.load(getClass().getResource("/View/AddAppointmentScreen.fxml"));
        Scene scene = new Scene(addButton);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.show();
    }

    /**
     This method is an Event Handler that sends the user to the Update Appointment Screen.  
     */
    @FXML
    public void updateButtonAction(ActionEvent event) throws IOException {
        try {
            selectedAppt = appointmentListTV.getSelectionModel().getSelectedItem();

            if (selectedAppt.toString().trim().isEmpty()) {
                Messages.makeSelection();
            } else {
                Parent updateButton = FXMLLoader.load(getClass().getResource("/View/UpdateAppointmentScreen.fxml"));
                Scene scene = new Scene(updateButton);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Update Appointment");
                stage.setScene(scene);
                stage.show();
            }
        } catch (NullPointerException e) {
            Messages.makeSelection();
        }
    }

    /**
     This method is an Event Handler that will delete a selected appointment from the database.
     
     */
    @FXML
    public void deleteButtonAction(ActionEvent event) {
        try {
            selectedAppt = appointmentListTV.getSelectionModel().getSelectedItem();
            Messages.appointmentDelete(selectedAppt);
            gettingStarted();
        } catch (SQLException ex) {
            Messages.makeSelection();
        }

    }

    
    /** 
     This method is an Event Handler that will send the user back to the Home Screen.
     */
    @FXML
    public void backButtonAction(ActionEvent event) throws IOException, InterruptedException {
       //Messages.backConfirmation();
        Parent backButton = FXMLLoader.load(getClass().getResource("/View/HomeScreen.fxml"));
        Scene scene = new Scene(backButton);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Home");
        stage.setScene(scene);
        stage.show();

    }

    /** 
     This method is an Event Handler that will connect to the database and gather 
     appointment information for all appointments.
     Then it will set the CellFactorys for the table view.
     * 
     */
    @FXML
    public void showAllApptTV(ActionEvent event) {
        try {
            appointmentList = ConnectDB.collectAppointments();
            //load all appointments
            appointmentListTV.setItems(appointmentList);
            appointmentIDTC.setCellValueFactory(new PropertyValueFactory<>("AppointmentID"));
            titleTC.setCellValueFactory(new PropertyValueFactory<>("Title"));
            descriptionTC.setCellValueFactory(new PropertyValueFactory<>("Description"));
            locationTC.setCellValueFactory(new PropertyValueFactory<>("Location"));
            contactTC.setCellValueFactory(new PropertyValueFactory<>("ContactID"));
            typeTC.setCellValueFactory(new PropertyValueFactory<>("Type"));
            startTimeTC.setCellValueFactory(new PropertyValueFactory<>("Start"));
            endTimeTC.setCellValueFactory(new PropertyValueFactory<>("End"));
            customerIDTC.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
            userIDTC.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** 
     This method is an Event Handler that will connect to the database and gather 
     appointment information for all appointments occurring the next month.
     Then it will set the CellFactorys for the table view.
     * 
     */
    @FXML
    public void showMonthlyTV(ActionEvent event) {

        appointmentList = ConnectDB.monthlyAppts();//.selectPS("*", "appointments");//Appointment.buildAllAppointments();
        if (appointmentList.isEmpty()) {
            Messages.noAppts(User.getCurrentUserName());
        } else {
            appointmentListTV.setItems(appointmentList);
            appointmentIDTC.setCellValueFactory(new PropertyValueFactory<>("AppointmentID"));
            titleTC.setCellValueFactory(new PropertyValueFactory<>("Title"));
            descriptionTC.setCellValueFactory(new PropertyValueFactory<>("Description"));
            locationTC.setCellValueFactory(new PropertyValueFactory<>("Location"));
            contactTC.setCellValueFactory(new PropertyValueFactory<>("ContactID"));
            typeTC.setCellValueFactory(new PropertyValueFactory<>("Type"));
            startTimeTC.setCellValueFactory(new PropertyValueFactory<>("Start"));
            endTimeTC.setCellValueFactory(new PropertyValueFactory<>("End"));
            customerIDTC.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
            userIDTC.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        }
    }

    /** 
     This method is an Event Handler that will connect to the database and gather 
     appointment information for all appointments occurring in the next week.
     Then it will set the CellFactorys for the table view.
     * 
     */
    @FXML
    public void showWeeklyTV(ActionEvent event) {
        if (appointmentList.isEmpty()) {
            Messages.noAppts(User.getCurrentUserName());
        } else {
            appointmentList = ConnectDB.weeklyAppts();//.selectPS("*", "appointments");//Appointment.buildAllAppointments();
            appointmentListTV.setItems(appointmentList);
            appointmentIDTC.setCellValueFactory(new PropertyValueFactory<>("AppointmentID"));
            titleTC.setCellValueFactory(new PropertyValueFactory<>("Title"));
            descriptionTC.setCellValueFactory(new PropertyValueFactory<>("Description"));
            locationTC.setCellValueFactory(new PropertyValueFactory<>("Location"));
            contactTC.setCellValueFactory(new PropertyValueFactory<>("ContactID"));
            typeTC.setCellValueFactory(new PropertyValueFactory<>("Type"));
            startTimeTC.setCellValueFactory(new PropertyValueFactory<>("Start"));
            endTimeTC.setCellValueFactory(new PropertyValueFactory<>("End"));
            customerIDTC.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
            userIDTC.setCellValueFactory(new PropertyValueFactory<>("UserID"));

        }
    }
 
}
