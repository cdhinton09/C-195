package Reports;

import Database.ConnectDB;
import static Database.ConnectDB.appointmentTotals;
import Model.Appointment;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * This class contains all the methods associated with the Appointment Totals
 * Report.
 *
 * @author cdhin
 */
public class CustomerAppointmentTotalsController implements Initializable {

    @FXML
    private ComboBox<String> monthCB;

    @FXML
    private ComboBox<String> typeCB;

    @FXML
    private Label totalLabel;

    @FXML
    private Label total;

    @FXML
    private Button backButton;

    public DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM");

    LocalDateTime jan = LocalDateTime.of(2021, 1, 01, 0, 0);
    LocalDateTime feb = LocalDateTime.of(2021, 2, 01, 0, 0);
    LocalDateTime mar = LocalDateTime.of(2021, 3, 01, 0, 0);
    LocalDateTime apr = LocalDateTime.of(2021, 4, 01, 0, 0);
    LocalDateTime may = LocalDateTime.of(2021, 5, 01, 0, 0);
    LocalDateTime jun = LocalDateTime.of(2021, 6, 01, 0, 0);
    LocalDateTime jul = LocalDateTime.of(2021, 7, 01, 0, 0);
    LocalDateTime aug = LocalDateTime.of(2021, 8, 01, 0, 0);
    LocalDateTime sep = LocalDateTime.of(2021, 9, 01, 0, 0);
    LocalDateTime oct = LocalDateTime.of(2021, 10, 01, 0, 0);
    LocalDateTime nov = LocalDateTime.of(2021, 11, 01, 0, 0);
    LocalDateTime dec = LocalDateTime.of(2021, 12, 01, 0, 0);
    ObservableList<LocalDateTime> monthsLDT = FXCollections.observableArrayList();

    ObservableList<String> months = FXCollections.observableArrayList();

    private static String source = "jdbc:mysql://wgudb.ucertify.com/WJ077GX";
    private static String user = "U077GX";
    private static String pass = "53688956344";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            setMonthCB();
            setTypeCB();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method creates a list of the months of the year.
     *
     * @throws SQLException
     */
    public void setMonthCB() throws SQLException {

        months.add(jan.getMonth().toString());
        months.add(feb.getMonth().toString());
        months.add(mar.getMonth().toString());
        months.add(apr.getMonth().toString());
        months.add(may.getMonth().toString());
        months.add(jun.getMonth().toString());
        months.add(jul.getMonth().toString());
        months.add(aug.getMonth().toString());
        months.add(sep.getMonth().toString());
        months.add(oct.getMonth().toString());
        months.add(nov.getMonth().toString());
        months.add(dec.getMonth().toString());

        monthCB.setItems(months);

    }

    /**
     * This method sets the items of the Type ComboBox with all supports
     * appointment types.
     */
    private void setTypeCB() {
        typeCB.setItems(Appointment.appointmentTypes());
    }

    /**
     * Event Handler for the Get Total button. Takes the selected month and
     * type, then passes them into a query. The query will return a integer,
     * signifying the number of appointments in the given month, of the given
     * type.
     *
     * @param event
     */
    @FXML
    public void getTotalAction(ActionEvent event) {
        int count = -1;
        String selectedMonth = monthCB.getValue();
        LocalDateTime selectedLDT = null;
        monthsLDT.add(jan);
        monthsLDT.add(feb);
        monthsLDT.add(mar);
        monthsLDT.add(apr);
        monthsLDT.add(may);
        monthsLDT.add(jun);
        monthsLDT.add(jul);
        monthsLDT.add(aug);
        monthsLDT.add(sep);
        monthsLDT.add(oct);
        monthsLDT.add(nov);
        monthsLDT.add(dec);
        try {
            for (LocalDateTime m : monthsLDT) {

                if (selectedMonth == m.getMonth().toString()) {
                    selectedLDT = m;

                }

            }
            count = appointmentTotals(selectedLDT.getMonthValue(), typeCB.getValue());
        } catch (NullPointerException ee) {
            ee.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        total.setText(String.valueOf(count));
    }

    /**
     * Event Handler for the back button. This will direct the user to the Home
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
}
