/*
This Class is used to initialize the Home Screen.  It will direct the user to  
the appointment or customer screens or to the report of their choosing, based on
user input.
 */
package Controller;

import Database.ConnectDB;
import Model.Appointment;
import Model.Messages;
import Reports.CustomerAppointmentTotalsController;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import static javafx.application.Platform.exit;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class HomeScreenController implements Initializable {

    private ObservableList<Appointment> homeScreenTableView;

    @FXML
    private TableView<Appointment> homeScreenTV;
    @FXML
    private TableColumn<Appointment, Integer> appointmentIDTC;
    @FXML
    private TableColumn<Appointment, String> titleTC;
    @FXML
    private TableColumn<Appointment, String> descriptionTC;
    @FXML
    private TableColumn<Appointment, String> locationTC;
    @FXML
    private TableColumn<Appointment, Integer> contactIDTC;
    @FXML
    private TableColumn<Appointment, String> typeTC;
    @FXML
    private TableColumn<Appointment, LocalDateTime> startTC;
    @FXML
    private TableColumn<Appointment, LocalDateTime> endTC;
    @FXML
    private TableColumn<Appointment, Integer> customerIDTC;
    @FXML
    private TableColumn<Appointment, Integer> userIDTC;
    @FXML
    private ComboBox<String> reportsCB;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        homeScreenTV.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        try {
            gettingStarted();

        } catch (SQLException ex) {
            Messages.connectionError();
        }
    }

    /**
     * This method is used the gather appointment information from the database,
     * set TableColumn CellFactorys, and set the fill the Reports ComboBox.
     */
    public void gettingStarted() throws SQLException {
        homeScreenTV.setItems(ConnectDB.collectAppointments());
        appointmentIDTC.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleTC.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionTC.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationTC.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactIDTC.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        typeTC.setCellValueFactory(new PropertyValueFactory<>("type"));
        startTC.setCellValueFactory(new PropertyValueFactory<>("start"));
        endTC.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIDTC.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIDTC.setCellValueFactory(new PropertyValueFactory<>("userID"));

        reportsCB.getItems().add("Customer Appointment Totals");
        reportsCB.getItems().add("Contact Schedule");
        reportsCB.getItems().add("Customer Schedule");
    }

    /**
     * This method is an event handler for the Reports ComboBox. It will direct
     * the user to the correct report based on item selected.
     */
    @FXML
    public void reportAction(ActionEvent event) {
        if (reportsCB.getValue() == "Customer Appointment Totals") {
            try {

                Parent root = FXMLLoader.load(getClass().getResource("/Reports/CustomerAppointmentTotals.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Customer Appointment Totals");
                stage.setScene(scene);
                stage.show();
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (IOException ee) {
                ee.printStackTrace();
            }
        } else if (reportsCB.getValue() == "Contact Schedule") {
            try {

                Parent root = FXMLLoader.load(getClass().getResource("/Reports/ContactSchedule.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Contact Schedule");
                stage.setScene(scene);
                stage.show();
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (IOException ee) {
                ee.printStackTrace();
            }
        } else if (reportsCB.getValue() == "Customer Schedule") {
            try {

                Parent root = FXMLLoader.load(getClass().getResource("/Reports/CustomerSchedule.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Customer Schedule");
                stage.setScene(scene);
                stage.show();
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (IOException ee) {
                ee.printStackTrace();
            }
        }
    }

    /**
     * This method is an Event Handler for the Appointment button. It will
     * direct the user to the AppointmentScreen.
     */
    @FXML
    public void appointmentButtonAction(ActionEvent event) throws IOException, NullPointerException {
        try {
            Parent appointments = FXMLLoader.load(getClass().getResource("/View/AppointmentScreen.fxml"));
            Scene scene = new Scene(appointments);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Appointments");
            stage.setScene(scene);
            stage.show();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will complete exit the application and end all running tasks.
     */
    @FXML
    public void exitButtonAction(ActionEvent event) {
        Messages.exitConfirmation();
        exit();
    }

    /**
     * This method is an Event Handler for the Appointment button. It will
     * direct the user to the CustomerScreen.
     */
    @FXML
    public void customerRecordsButtonAction(ActionEvent event) throws IOException, SQLException {

        Parent customerRecords = FXMLLoader.load(getClass().getResource("/View/CustomerScreen.fxml"));
        Scene scene = new Scene(customerRecords);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Customer Records");
        stage.setScene(scene);
        stage.show();
    }

   

}
