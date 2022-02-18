package Model;

import Database.ConnectDB;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *This class contains the constructor and all methods associated with the Contact object.
 * @author cdhinton09
 */
public class Contact {
    private int contactID;
    private String contactName;
    private String email;
    
    /**
     * This is the standard constructor for the COntact object.
     * @param contactID
     * @param contactName
     * @param email 
     */
    public Contact(int contactID, String contactName, String email) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }
    
    //Getters and Setters
    /**
     * Getter for ContactID.
     * @return 
     */
    public int getContactID() {
        return contactID;
    }
/**
 * Setter for ContactID.
 * @param contactID 
 */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }
/**
 * Getter for COntact Name.
 * @return 
 */
    public String getContactName() {
        return contactName;
    }
/**
 * Setter for Contact Name.
 * @param contactName 
 */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
/**
 * Getter for email.
 * @return 
 */
    public String getEmail() {
        return email;
    }
/**
 * Setter for email.
 * @param email 
 */
    public void setEmail(String email) {
        this.email = email;
    }
    
    private static ObservableList<Contact> allContacts = FXCollections.observableArrayList();
    /**
     * Returns the observableList allContacts
     * @return 
     */
    public static ObservableList<Contact> getAllContacts(){
        return allContacts;
    }
    /**
     * Adds a contact to the allContacts list.
     * @param contact 
     */
    
    public static void addContact(Contact contact){
        allContacts.add(contact);
    }
    /**
     * Removes a contact from the allContacts.
     * @param contact 
     */
    public static void removeContact(String tableName, String primaryKey, 
            String keyValue) throws SQLException{
        ConnectDB.deleteContact(tableName, primaryKey, keyValue);
    }
    
}
