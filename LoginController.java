/**
 * This class is the controller for the Login Screen.  It handles the user validation.
 * 
 */
package Controller;

import Database.ConnectDB;
import Model.Messages;
import Model.User;
import Reports.LoginActivityLog;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField userNameTF;

    @FXML
    private TextField passwordTF;

    @FXML
    private Label loginLocation;

    @FXML
    private Button loginButton;

    @FXML
    private Button exitButton;

    public String user = null;

    public String pass = null;

    @FXML
    void onExit(ActionEvent event) {
        Model.Messages.exitConfirmation();
    }
    
/**
 * This method accepts username and password input, if access if granted it will
 * direct the user to the Home Screen.
 * 
 */
    @FXML
    public void onLogin(ActionEvent event) throws SQLException, IOException {
        LocalDateTime dt = LocalDateTime.now();
        File file = new File("login_activity.txt");
        if (userNameTF.getText().trim().isEmpty() || passwordTF.getText().trim().isEmpty()) {
            Messages.failedLogin();
        } else {
            user = userNameTF.getText();
            pass = passwordTF.getText();
            if (validateLogin() == true) {
                try {
                    ConnectDB.collectAllData();

                    User.setCurrentUserName(user);
                    Parent root = FXMLLoader.load(getClass().getResource("/View/HomeScreen.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setTitle("Home Screen");
                    stage.setScene(scene);
                    stage.show();
                    Messages.welcome(user);
                } catch (IOException e) {
                    Messages.invalidInput();
                    System.err.print(e);
                    e.getStackTrace();
                }

                LoginActivityLog.appendLoginActivity(file, user, true, dt);

            } else {
                LoginActivityLog.appendLoginActivity(file, user, false, dt);
                Messages.invalidInput();
                
            }
        }
    }
/**
 * This method check to see if the user has a valid username and password.
 */
    public boolean validateLogin() {
        boolean login = false;

        for (User user : User.allUsers) {
            if (user.userName.equals(userNameTF.getText())) {
                if (user.password.equals(passwordTF.getText())) {
                    login = true;
                    System.out.println("Login Successful");
                }
            }
        }
        return login;
    }
    
  

    @FXML
    void initialize() throws SQLException  {
        //Locale.setDefault(new Locale("fr"));
        loginLocation.setText(ZoneId.systemDefault().getId());
        

    }
}
