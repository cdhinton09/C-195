/**
 This Class is used to control the Customer Screen.  From here the user will 
  be able to visualize the current list of Customers, delete customers, and go to 
  the Add Customer and Update Customer screens.
 */
package Controller;

import Database.ConnectDB;
import Model.Customer;
import Model.Messages;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author cdhinton09
 */
public class CustomerScreenController implements Initializable {

    @FXML
    private TableView<Customer> currentCustomerTV;
    @FXML
    private TableColumn<Customer, Integer> customerIDTC;
    @FXML
    private TableColumn<Customer, String> customerNameTC;
    @FXML
    private TableColumn<Customer, String> customerPhoneNumberTC;
    @FXML
    private TableColumn<Customer, String> customerPostCodeTC;
    @FXML
    private TableColumn<Customer, Integer> customerFirstLevelDivisionTC;

    public static Customer selectedCustomer;
    //private ObservableList<Customer>;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentCustomerTV.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        try {
            gettingStarted();
        } catch (SQLException ex) {
            Messages.connectionError();
        }

    }
/**
 This method is used to gather customer information from the database and set it
 in the TableView.  It also sets the CellValueFactorys for each TableColumn.
 */
    public void gettingStarted() throws SQLException {
        currentCustomerTV.setItems(ConnectDB.collectCustomers());//setCustomerList());

        customerIDTC.setCellValueFactory(new PropertyValueFactory("customerID"));
        customerNameTC.setCellValueFactory(new PropertyValueFactory("customerName"));
        customerPhoneNumberTC.setCellValueFactory(new PropertyValueFactory("phone"));
        customerPostCodeTC.setCellValueFactory(new PropertyValueFactory("postalCode"));
        customerFirstLevelDivisionTC.setCellValueFactory(new PropertyValueFactory("divisionID"));

    }
/**
 This method is an Event Handler that directs the user to the Add Customer Screen.
 */
    @FXML
    public void addCustomerAction(ActionEvent event) throws IOException {
        Parent addCustomerScreen = FXMLLoader.load(getClass().getResource("/View/AddCustomerScreen.fxml"));
        Scene scene = new Scene(addCustomerScreen);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    /**
 This method is an Event Handler that directs the user to the Update Customer Screen.
 */
    @FXML
    public void updateCustomerAction(ActionEvent event) throws NullPointerException, IOException {
        try {
            selectedCustomer = currentCustomerTV.getSelectionModel().getSelectedItem();

            if (selectedCustomer.toString().trim().isEmpty()) {
                Messages.makeSelection();
            } else {

                Parent updateCustomerScreen = FXMLLoader.load(getClass().getResource("/View/UpdateCustomerScreen.fxml"));
                Scene scene = new Scene(updateCustomerScreen);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Update Customer");
                stage.setScene(scene);
                stage.show();
            }
        } catch (NullPointerException e) {
            Messages.makeSelection();
        }
    }

    /**
     Once a customer is selected, this method will delete the customer and their appointments
     from the database.
     */
    @FXML
    public void deleteCustomerAction(ActionEvent event) throws SQLException {
        selectedCustomer = currentCustomerTV.getSelectionModel().getSelectedItem();

        Messages.customerDelete(selectedCustomer);

        gettingStarted();
    }
/**
This method will direct the user back to the Home Screen. 
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

}
