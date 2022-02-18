package softwaretwo;

import Database.ConnectDB;
import Database.MyTime.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is the main class for the application.
 */
public class SoftwareTwo extends Application {

    /**
     * This will open the Login Screen and initiate the application. It will 
     * also change the GUI based on the system's default and query to database
     * for User information.
     * language.
     *
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        ResourceBundle rb = ResourceBundle.getBundle("I18N/Lang", Locale.getDefault());

        ConnectDB.collectUsers();
        Parent root = FXMLLoader.load(getClass().getResource("/View/Login.fxml"), rb);
        Scene scene = new Scene(root);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This is the main method.  It will initiate the application.
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        launch(args);
    }

    /**
     * This will change the language of text depending on the system default locale.
     * @param string
     * @return 
     */
    public static String languageCheck(String string) {
        ResourceBundle rb = ResourceBundle.getBundle("I18N/Lang", Locale.getDefault());
        String frString = "";
        if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
            frString = rb.getString(string);

        }
        return frString;
    }

}
