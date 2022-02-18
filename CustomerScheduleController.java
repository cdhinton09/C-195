/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reports;

import Database.ConnectDB;
import Model.Customer;
import Model.CustomerSchedule;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * This class acts as the controller for the Customer Schedule Screen. It
 * contains all methods associated with the Customer Schedule.
 */
public class CustomerScheduleController implements Initializable {

    @FXML
    private ComboBox<Customer> customerCB;

    @FXML
    private TableView<CustomerSchedule> customerTV;

    @FXML
    private TableColumn<CustomerSchedule, Integer> apptIDTC;

    @FXML
    private TableColumn<CustomerSchedule, String> titleTC;

    @FXML
    private TableColumn<CustomerSchedule, String> typeTC;

    @FXML
    private TableColumn<CustomerSchedule, String> descriptionTC;

    @FXML
    private TableColumn<CustomerSchedule, LocalDateTime> startTC;

    @FXML
    private TableColumn<CustomerSchedule, LocalDateTime> endTC;

    @FXML
    private TableColumn<CustomerSchedule, Integer> contactIDTC;

    @FXML
    private Button backButton;

    @FXML
    private Button getScheduleButton;

    ObservableList<Customer> customerList = FXCollections.observableArrayList();
    ObservableList<String> customerStrings = FXCollections.observableArrayList();

    /**
     * Event handler for the back button. It will direct the user to the Home
     * Screen.
     *
     * @param event
     * @throws IOException
     * @throws InterruptedException
     */
    @FXML
    public void backButtonAction(ActionEvent event) throws IOException, InterruptedException {
        Parent homeScreen = FXMLLoader.load(getClass().getResource("/View/HomeScreen.fxml"));
        Scene scene = new Scene(homeScreen);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Home");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method sets the table view items, table column cells value
     * factories, and customer combo box, cell factory and button cell.
     */
    public void gettingStarted() {
        try {
            customerTV.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            customerTV.setItems(ConnectDB.customerSchedules(3));
            apptIDTC.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            titleTC.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionTC.setCellValueFactory(new PropertyValueFactory<>("description"));
            typeTC.setCellValueFactory(new PropertyValueFactory<>("type"));
            startTC.setCellValueFactory(new PropertyValueFactory<>("start"));
            endTC.setCellValueFactory(new PropertyValueFactory<>("end"));
            contactIDTC.setCellValueFactory(new PropertyValueFactory<>("contactID"));

            /**
             * This Lambda allows me to use a ComboBox with Customer type, but
             * show the String CustomerName. This simplifies the method, as I do
             * not have to have a utility method that takes in a string, then
             * converts it to a Customer for future work with the selection.
             */
            Callback<ListView<Customer>, ListCell<Customer>> factory = l
                    -> new ListCell<Customer>() {
                @Override
                protected void updateItem(Customer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : (item.getCustomerName()));
                }
            };

            /**
             * This Lambda allows me to change the string shown in the ComboBox,
             * after the user has made a selection. This makes the GUI easier to
             * interact with for the user.
             */
            Callback<ListView<Customer>, ListCell<Customer>> factoryUsed = l
                    -> new ListCell<Customer>() {
                @Override
                protected void updateItem(Customer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : ("You Selected : " + item.getCustomerName()));
                }
            };

            customerList = ConnectDB.collectCustomers();
            // customerCB.setItems(fillContactCB());
            customerCB.setItems(customerList);
            customerCB.setCellFactory(factory);
            customerCB.setButtonCell(factoryUsed.call(null));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This takes in the customerID and queries the database. It returns a list
     * and set the table view with that list.
     *
     * @param event
     */
    @FXML
    public void getScheduleAction(ActionEvent event) {

        customerTV.setItems(ConnectDB.customerSchedules(customerCB.getValue().getCustomerID()));
    }

    /**
     * This initializes the Customer Schedule Report. It also sets the table
     * view resize policy.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gettingStarted();
        customerTV.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

}
