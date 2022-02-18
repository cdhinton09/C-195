package Model;

import Database.ConnectDB;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class contains the constructor and all methods associated with the User
 * object.
 */
public class User {

    public int userID;
    public String userName;
    public String password;
    public String createDate;
    public String createdBy;
    public String lastUpdate;
    public String lastUpdatedBy;

    /**
     * This is the standard constructor for this class.
     */
    public User(int userID, String userName, String password, String createDate,
            String createdBy, String lastUpdate, String lastUpdatedBy) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Getter for UserID.
     *
     * @return
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Setter for UserID.
     *
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Getter for UserName.
     *
     * @return
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter for UserName.
     *
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter for Password.
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for Password.
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for CreatedDate.
     *
     * @return
     */
    public String getCreatedDate() {
        return createDate;
    }

    /**
     * Setter for CreatedDate.
     *
     * @param createdDate
     */
    public void setCreatedDate(String createdDate) {
        this.createDate = createdDate;
    }

    /**
     * Getter for CreatedBy.
     *
     * @return
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Setter for CreatedBy.
     *
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Getter for LastUpdated.
     *
     * @return
     */
    public String getLastUpdated() {
        return lastUpdate;
    }

    /**
     * Setter for LastUpdated.
     *
     * @param lastUpdated
     */
    public void setLastUpdated(String lastUpdated) {
        this.lastUpdate = lastUpdated;
    }

    /**
     * Getter for LastUpdatedBy.
     *
     * @return
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Setter for LastUpdatedBy.
     *
     * @param lastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public static ObservableList<User> allUsers = FXCollections.observableArrayList();

    /**
     * This should show all Users.
     */
    public static ObservableList<User> getAllUsers() throws SQLException {

        return allUsers;
    }

    /**
     * This should add a Users.
     */
    public static void addUsers(User user) {
        allUsers.add(user);
    }

    /**
     * This should remove Users.
     */
    public static void removeAppointment(User user) {
        allUsers.remove(user.userID);
    }

    public static User currentUser;
    public static String currentUserName;

    public static int currentUserID;

    /**
     * Setter for currentUser.
     *
     * @param name
     */
    public static void setCurrentUserName(String name) {
        currentUserName = name;
        for (User user : allUsers) {
            if (name == user.getUserName()) {
                currentUser = user;
            }
        }
    }

    /**
     * Getter for currentUser
     *
     * @return
     */
    public static String getCurrentUserName() {
        return currentUserName;
    }

}
