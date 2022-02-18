/*
This Class functions as a controller from the AddCustomerScreen.  It will allow
users to add customers to the database.
 */
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author cdhinton09
 */
public class AddCustomerScreenController implements Initializable {

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
    private ComboBox<String> countryIDCB;

    @FXML
    private ComboBox<String> firstLevelDivisionIDCB;

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
/**
 This method initiates the AddCustomerScreen.  It sets the ComboBox for firstLevelDivisionIDCB
 and countryIDCB with corresponding data from the database.  It sets the createdBy 
 field with the current user's name and sets the customerID with a distinct number.
 */
    private void gettingStarted() throws SQLException {
        getCountries();
       
        for (FirstLevelDivision division : Model.FirstLevelDivision.getAllDivisions()) {
            firstLevelDivisionIDCB.getItems().add(division.division);
        }
       // getDivision();

        createdByTF.setText(User.currentUserName);
        //customerIDTF.setEditable(false);
        customerIDTF.setText(String.valueOf(Customer.getCustomerIDCount()));
    }
/**
 This method gathers country data from the database and sets the ComboBox.
 */
    private void getCountries() {
        for (Country country : Model.Country.getAllCountries()) {
            countryIDCB.getItems().add(country.country);

        }
        getDivision();

    }
/**
 This method gathers first level division data from the database and sets the ComboBox.
 */
    private void getDivision() {
        String selectedCountryName = countryIDCB.getSelectionModel().getSelectedItem();
        ObservableList<FirstLevelDivision> selectedDivision = FXCollections.observableArrayList();
        
       
    }

/**
 This method is an event handler for the add customer button.  Once the button is
  pressed it will complete an INSERT SQL query on the database, uploading all
  gathered information to the database.
 
 */
    @FXML
    private void addCustomerButtonAction(ActionEvent event) throws IOException, SQLException, NullPointerException {
        Timestamp current = new Timestamp(System.currentTimeMillis());
        String divisionName = firstLevelDivisionIDCB.getSelectionModel().getSelectedItem();
        FirstLevelDivision collectedDivision = null;
        String statement = "insert into WJ077GX.customers (Customer_ID, Customer_Name, "
                + "Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, "
                + "Last_Updated_By, Division_ID) values (?,?,?,?,?,?,?,?,?,?);";

        try {
            if ( true /* customerIDTF.getText().trim().isEmpty()
                    || customerNameTF.getText().trim().isEmpty()
                    || addressTF.getText().trim().isEmpty()
                    || postalCodeTF.getText().trim().isEmpty()
                    || phoneCodeTF.getText().trim().isEmpty()
                    || phoneNumberTF.getText().trim().isEmpty()
                    || createdByTF.getText().trim().isEmpty()
                    || countryIDCB.getValue().trim().isEmpty()
                    || firstLevelDivisionIDCB.getValue().trim().isEmpty()*/) {

                for (FirstLevelDivision division : Model.FirstLevelDivision.getAllDivisions()) {
                    if (division.division == firstLevelDivisionIDCB.getSelectionModel().getSelectedItem()) {
                        collectedDivision = division;
                        System.out.println("This is the collected division: " + collectedDivision.getDivision());
                    }

                }

                int customerID = Customer.getCustomerIDCount();
                int divisionID = collectedDivision.getDivisionID();
                String customerName = customerNameTF.getText();
                String address = addressTF.getText();
                String postalCode = postalCodeTF.getText();
                String createdBy = User.currentUserName;//createdByTF.getText(); // ******Should auto populate with users name*****
                String lastUpdatedBy = User.currentUserName;//createdByTF.getText();
                String phoneNumber = Customer.splitPhoneToPhoneNumber(phoneNumberTF.getText());
                String phoneCode = Customer.splitPhoneToPhoneCode(phoneNumberTF.getText());
                String createDate = current.toString();
                String lastUpdate = current.toString();//***Should use the name of the last user to update file.
                String phone = Customer.buildPhoneNum(Customer.numericOnly(phoneCodeTF.getText() + phoneNumberTF.getText()));//phoneCode + phoneNumber;

                Connection con = ConnectDB.connection();
                PreparedStatement stmt = con.prepareStatement(statement);
                stmt.setInt(1,customerID);
                stmt.setString(2,customerName);
                stmt.setString(3,address);
                stmt.setString(4,postalCode);
                stmt.setString(5, phone);
                stmt.setString(6,createDate);
                stmt.setString(7,createdBy);
                stmt.setString(8,lastUpdate);
                stmt.setString(9,lastUpdatedBy);
                stmt.setInt(10,divisionID);  
                stmt.executeUpdate();
              
                //This should send the user back to the Customer Screen
                Parent customerScreen = FXMLLoader.load(getClass().getResource("/View/CustomerScreen.fxml"));
                Scene scene = new Scene(customerScreen);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Customer");
                stage.setScene(scene);
                
               

                // This should send the user back to the Customer Screen after adding the new customer.
                Messages.customerAdded(customerName);
            } else {
                System.out.println("TextField is empty");

            }

        } catch (NullPointerException ex) {
            System.out.println("NullPointerException has occured");
            ex.printStackTrace();
        }
    }
/**
 This method will cancel any actions performed on the AddCustomerScreen and return
 the user to the CustomerScreen.
 */
    @FXML
    private void cancelButtonAction(ActionEvent event) throws IOException {
        boolean answer = Messages.cancelConfirmation();
        if (answer == true) {
            Parent cancelButton = FXMLLoader.load(getClass().getResource("/View/CustomerScreen.fxml"));
            Scene scene = new Scene(cancelButton);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Customer");
            stage.setScene(scene);
            stage.show();
        } else {
        }
    }
}
