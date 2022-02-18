package Reports;

import Database.ConnectDB;
import Model.Appointment;
import Model.Contact;
import Model.ContactSchedule;
import Model.Customer;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * This class contains the constructor and all methods associated with the
 * ContactSchedule object.
 */
public class ContactScheduleController implements Initializable {

    @FXML
    private ComboBox<Contact> contactCB;

    @FXML
    private TableView<ContactSchedule> contactTV;

    @FXML
    private TableColumn<ContactSchedule, Integer> apptIDTC;

    @FXML
    private TableColumn<ContactSchedule, String> titleTC;

    @FXML
    private TableColumn<ContactSchedule, String> typeTC;

    @FXML
    private TableColumn<ContactSchedule, String> descriptionTC;

    @FXML
    private TableColumn<ContactSchedule, LocalDateTime> startTC;

    @FXML
    private TableColumn<ContactSchedule, LocalDateTime> endTC;

    @FXML
    private TableColumn<ContactSchedule, Integer> customerIDTC;

    @FXML
    private Button backButton;

    @FXML
    private Button getScheduleButton;

    ObservableList<Contact> contactList = FXCollections.observableArrayList();
    ObservableList<String> contactStrings = FXCollections.observableArrayList();

    /**
     * This is the Event Handler the the back button. It will direct the user to
     * the Home Screen.
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
     * This method will set contact table view, cell factory for all table
     * columns, and fill the contact combo box.
     */
    public void gettingStarted() {
        try {

            contactTV.setItems(ConnectDB.contactSchedules(3));
            apptIDTC.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            titleTC.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionTC.setCellValueFactory(new PropertyValueFactory<>("description"));
            typeTC.setCellValueFactory(new PropertyValueFactory<>("type"));
            startTC.setCellValueFactory(new PropertyValueFactory<>("start"));
            endTC.setCellValueFactory(new PropertyValueFactory<>("end"));
            customerIDTC.setCellValueFactory(new PropertyValueFactory<>("customerID"));

            /**
             * This Lambda allows me to use a ComboBox with Contact type, but
             * show the String ContactName. This simplifies the method, as I do
             * not have to have a method that takes in a string, then converts
             * it to a Contact for future work with the selection.
             */
            Callback<ListView<Contact>, ListCell<Contact>> factory = l
                    -> new ListCell<Contact>() {
                @Override
                protected void updateItem(Contact item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : (item.getContactName()));
                }
            };

            /**
             * This Lambda allows me to change the string shown in the ComboBox,
             * after the user has made a selection. This makes the GUI easier to
             * interact with for the user.
             */
            Callback<ListView<Contact>, ListCell<Contact>> factoryUsed = l
                    -> new ListCell<Contact>() {
                @Override
                protected void updateItem(Contact item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : ("You Selected : " + item.getContactName()));
                }
            };

            contactList = ConnectDB.collectContacts();
            contactCB.setItems(ConnectDB.collectContacts());
            contactCB.setCellFactory(factory);
            contactCB.setButtonCell(factoryUsed.call(null));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This is an Event Handler for the Get Schedule button. It will query the
     * database for the specified Contacts appointments.
     *
     * @param event
     */
    @FXML
    public void getScheduleAction(ActionEvent event) {

        contactTV.setItems(ConnectDB.contactSchedules(contactCB.getValue().getContactID()));
    }

    /**
     * This method initializes the Contact Schedule Report. It also adds a
     * resizing policy for the contact table view.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gettingStarted();
        contactTV.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

}
