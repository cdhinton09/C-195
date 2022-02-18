
package Controller;

import Database.ConnectDB;
import Model.Country;
import Model.Customer;
import Model.FirstLevelDivision;
import Model.Messages;
import Model.User;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * This Class is the Controller for the Update Customer Screen. 
 */
public class UpdateCustomerScreenController implements Initializable {

    @FXML
    private TextField customerIDTF;

    @FXML
    private TextField customerNameTF;

    @FXML
    private TextField addressTF;

    @FXML
    private TextField postalCodeTF;

    @FXML
    private TextField phoneCodeTF;

    @FXML
    private TextField phoneNumberTF;

    @FXML
    private TextField createdByTF;

    @FXML
    private Label countryID;

    @FXML
    private ComboBox<String> countryIDCB;

    @FXML
    private ComboBox<String> firstLevelDivisionIDCB;

    Customer selectedCustomer = CustomerScreenController.selectedCustomer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gettingStarted();

    }

    /**
     * This method will set all TextFields and ComboBoxes will information from
     * the customer selected on the Customer Screen.  
     */
    public void gettingStarted() throws NullPointerException {
        customerIDTF.setText(String.valueOf(selectedCustomer.getCustomerID()));
        customerIDTF.setEditable(false);
        customerNameTF.setText(selectedCustomer.getCustomerName());
        addressTF.setText(selectedCustomer.getAddress());
        postalCodeTF.setText(selectedCustomer.getPostalCode());
        phoneCodeTF.setText(selectedCustomer.getPhoneCode(selectedCustomer.getPhone()));
        phoneNumberTF.setText(selectedCustomer.getPhoneNumber(selectedCustomer.getPhone()));
        createdByTF.setText(selectedCustomer.getCreatedBy());
        createdByTF.setEditable(false);
        countryIDCB.setValue(Customer.getCountryName(selectedCustomer.getDivisionID()));
        firstLevelDivisionIDCB.setValue(Customer.getDivisionName(selectedCustomer.getDivisionID()));
        
        
        for (Country country : Model.Country.getAllCountries()) {
            countryIDCB.getItems().add(country.country);
        }

        for (FirstLevelDivision division : Model.FirstLevelDivision.getAllDivisions()) {
            firstLevelDivisionIDCB.getItems().add(division.division);
        }

    }


 /**
  * This method is an Event Handler for the update button. It will perform an
  * UPDATE query to the database using input from the TxtFields and ComboBoxes.
  */
    @FXML
    public void updateCustomerButtonAction(ActionEvent event) throws IOException {
        Timestamp current = new Timestamp(System.currentTimeMillis());
        FirstLevelDivision currentDivision = null;
        for (FirstLevelDivision division : FirstLevelDivision.getAllDivisions()) {
            if (division.division == firstLevelDivisionIDCB.getValue()) {
                currentDivision = division;
            }
        }
        
        String phone = Customer.buildPhoneNum(phoneCodeTF.getText() + phoneNumberTF.getText());
        try {
           
                Connection con = ConnectDB.connection();
                PreparedStatement stmt = con.prepareStatement("UPDATE  WJ077GX.customers SET "
                        + "Customer_Name = ?, "
                        + "Address = ?, "
                        + "Postal_Code = ?, "
                        + "Phone = ?, "
                        + "Division_ID = ?, "
                        + "Last_Update = ?, "
                        + "Last_Updated_By = ? "
                        + "WHERE Customer_ID = ?");
                stmt.setString(1, customerNameTF.getText());
                stmt.setString(2, addressTF.getText());
                stmt.setString(3, postalCodeTF.getText());
                stmt.setString(4, phone);
                stmt.setInt(5, currentDivision.divisionID);
                stmt.setString(6, current.toString());
                stmt.setString(7, User.getCurrentUserName());
                
                stmt.setString(8, customerIDTF.getText());
                
                int result = stmt.executeUpdate();
                System.out.println(result);

                

                Messages.customerUpdate(customerNameTF.getText());
           
        } catch (NullPointerException e) {
            Messages.invalidInput();
        }catch(SQLException ee){
            ee.printStackTrace();
        }
        Parent backToCustomerScreen = FXMLLoader.load(getClass().getResource("/View/CustomerScreen.fxml"));
        Scene scene = new Scene(backToCustomerScreen);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Customer");
        stage.show();

    }
/**
 * This method is the Event Handler for the cancel button.  It will direct the 
 * user to the Customer Screen.
 */
    @FXML
    public void cancelUpdateButtonAction(ActionEvent event) throws IOException {

        boolean answer = Messages.cancelConfirmation();
        if (answer == true) {
            Parent cancelButton = FXMLLoader.load(getClass().getResource("/View/CustomerScreen.fxml"));
            Scene scene = new Scene(cancelButton);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Customer");
            stage.show();
        } else {
        }
    }

}
