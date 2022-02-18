package Model;

import java.sql.Time;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The class contains all constructors and associated methods for the Country
 * object.
 *
 * @author cdhin
 */
public class Country {

    //Vars
    public int countryID;
    public String country;
    public String createDate;
    public String createdBy;
    public String lastUpdate;
    public String lastUpdatedBy;

    /**
     * This is the standard constructor for this class.
     *
     * @param countryID
     * @param country
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     */
    public Country(int countryID, String country, String createDate, String createdBy, String lastUpdate, String lastUpdatedBy) {
        this.countryID = countryID;
        this.country = country;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Getter for CountryID.
     *
     * @return
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Setter for CountryID
     *
     * @param countryID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * Getter for Country name.
     *
     * @return
     */
    public String getCountry() {
        return country;
    }

    /**
     * Setter for Country name.
     *
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Getter for CreateDate.
     *
     * @return
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * Setter for CreateDate.
     *
     * @param createDate
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
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
     * Getter for LastUpdate.
     *
     * @return
     */
    public String getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Setter for LastUpdate.
     *
     * @param lastUpdate
     */
    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
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
    /**
     * ObservableList to hold all countries for use throughout application.
     */
    private static ObservableList<Country> allCountries = FXCollections.observableArrayList();

    /**
     * Should return allCountries list.
     *
     * @return
     */
    public static ObservableList<Country> getAllCountries() {
        return allCountries;
    }

    /**
     * Should add a country to the allCountries list.
     *
     * @param country
     */
    public static void addCountry(Country country) {
        allCountries.add(country);
    }

    /**
     * Should remove a country for the allCountries list.
     *
     * @param country
     */
    public static void removeCountry(Country country) {
        allCountries.remove(country.countryID);
    }

}
