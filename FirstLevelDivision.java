package Model;

import java.sql.Time;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class contains all constructors and other methods associated with the FirstLevelDivision object.
 */
public class FirstLevelDivision {

    //Vars
    public int divisionID;
    public String division;
    public Time createdDate;
    public String createdBy;
    public Time lastUpdated;
    public String lastUpdatedBy;
    public int countryID;

    /**
     * This is the standard constructor for this class.
     * @param divisionID
     * @param division
     * @param createdDate
     * @param createdBy
     * @param lastUpdated
     * @param lastUpdatedBy
     * @param countryID 
     */
    public FirstLevelDivision(int divisionID, String division, Time createdDate, String createdBy, Time lastUpdated, String lastUpdatedBy, int countryID) {
        this.divisionID = divisionID;
        this.division = division;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryID = countryID;
    }

    /**
     * Getter for DivisionID.
     * @return 
     */
    public int getDivisionID() {
        return divisionID;
    }
/**
 * Setter for DivisionID.
 * @param divisionID 
 */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
/**
 * Getter for Division name.
 * @return 
 */
    public String getDivision() {
        return division;
    }
/**
 * Setter for Division name.
 * @param division 
 */
    public void setDivision(String division) {
        this.division = division;
    }
/**
 * Getter for CreatedDate.
 * @return 
 */
    public Time getCreatedDate() {
        return createdDate;
    }
/**
 * Setter for CreatedDate.
 * @param createdDate 
 */
    public void setCreatedDate(Time createdDate) {
        this.createdDate = createdDate;
    }
/**
 * Getter for CreatedBy.
 * @return 
 */
    public String getCreatedBy() {
        return createdBy;
    }
/**
 * Setter for CreatedBy.
 * @param createdBy 
 */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
/**
 * Getter for LastUpdated.
 * @return 
 */
    public Time getLastUpdated() {
        return lastUpdated;
    }
/**
 * Setter for LastUpdated.
 * @param lastUpdated 
 */
    public void setLastUpdated(Time lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
/**
 * Getter for LastUpdatedBy.
 * @return 
 */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
/**
 * Setter for LastUpdatedBy.
 * @param lastUpdatedBy 
 */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
/**
 * Getter for CountryID.
 * @return 
 */
    public int getCountryID() {
        return countryID;
    }
/**
 * Setter for CountryID.
 * @param countryID 
 */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    private static ObservableList<FirstLevelDivision> allDivisions = FXCollections.observableArrayList();
/**
 * Retrieves the ObservableList allDivisions.
 * @return 
 */
    public static ObservableList<FirstLevelDivision> getAllDivisions() {
        return allDivisions;
    }
/**
 * Adds a firstLevelDivision object to allDivisions list.
 * @param division 
 */
    public static void addDivision(FirstLevelDivision division) {
        allDivisions.add(division);
    }
/**
 * Removes a firstLevelDivision object to allDivisions list.
 * @param division 
 */
    public static void removeDivision(FirstLevelDivision division) {
        allDivisions.remove(division.divisionID);
    }

}
