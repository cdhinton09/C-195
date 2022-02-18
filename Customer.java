package Model;

import Database.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Should contain the constructor and all methods associated with the Customer
 * object.
 */
public class Customer {

    private int customerID, divisionID;
    private String customerName, address, postalCode, createdBy, lastUpdatedBy, phone;// phoneNumber, phoneNumber;
    private LocalDateTime createDate, lastUpdate;

    private static String source = "jdbc:mysql://wgudb.ucertify.com/WJ077GX";
    private static String user = "U077GX";
    private static String pass = "53688956344";
    private Connection conn = null;// DriverManager.getConnection(source, user, pass);
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    /**
     * The standard constructor for this class.
     *
     * @param customerID
     * @param divisionID
     * @param customerName
     * @param address
     * @param postalCode
     * @param createdBy
     * @param lastUpdatedBy
     * @param phone
     * @param createDate
     * @param lastUpdate
     */
    public Customer(int customerID, int divisionID, String customerName,
            String address, String postalCode, String createdBy, String lastUpdatedBy,
            String phone, LocalDateTime createDate, LocalDateTime lastUpdate) {
        this.customerID = customerID;
        this.divisionID = divisionID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
        this.phone = phone;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
    }

    /**
     * Getter for PhoneNumber.
     *
     * @param phone
     * @return
     */
    public String getPhoneNumber(String phone) {
        return Customer.splitPhoneToPhoneNumber(phone);
    }

    /**
     * Getter for PhoneCode.
     *
     * @param phone
     * @return
     */
    public String getPhoneCode(String phone) {
        return Customer.splitPhoneToPhoneCode(phone);
    }

    /**
     * Setter for Phone.
     *
     * @param phone
     * @param customerID
     * @throws SQLException
     */
    public void setPhone(String phone, String customerID) throws SQLException {
        this.phone = phone;
    }

    /**
     * Getter for Phone.
     *
     * @return
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * Getter for CustomerID.
     *
     * @return
     */
    public int getCustomerID() {
        return this.customerID;
    }

    /**
     * Setter for CustomerID
     *
     * @param customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Getter for DivisionID.
     *
     * @return
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Setter for DivisionID.
     *
     * @param divisionID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * Getter for CustomerName.
     *
     * @return
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Setter for CustomerName
     *
     * @param customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Getter for Address.
     *
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter for Address.
     *
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter for Postal Code.
     *
     * @return
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Setter for PostalCode.
     *
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Getter for CreateDate.
     *
     * @return
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Setter for CreateDate.
     *
     * @param createDate
     */
    public void setCreateDate(LocalDateTime createDate) {
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
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Setter for LastUpdate.
     *
     * @param lastUpdate
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
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
     * Should take in a divisionID and return the country name associated with
     * that division.
     *
     * @param divisionID
     * @return
     */
    public static String getCountryName(int divisionID) {

        int countryID = 0;
        String countryName = "";
        for (FirstLevelDivision division : FirstLevelDivision.getAllDivisions()) {
            if (division.divisionID == divisionID) {
                countryID = division.countryID;
                for (Country country : Country.getAllCountries()) {
                    if (country.countryID == countryID) {
                        countryName = country.getCountry();
                    } else {

                    }
                }
            }
        }
        return countryName;
    }

    /**
     * Should take in a divisionID and return the divisionName associated with
     * it.
     *
     * @param divisionID
     * @return
     */
    public static String getDivisionName(int divisionID) {
        String divName = "";
        for (FirstLevelDivision division : FirstLevelDivision.getAllDivisions()) {
            if (division.divisionID == divisionID) {
                divName = division.division;
            }
        }
        return divName;
    }

    private static ObservableList allCustomers = FXCollections.observableArrayList();

    /**
     * Should return the allCustomers list.
     *
     * @return
     */
    public static ObservableList<Customer> getAllCustomers() {

        return allCustomers;
    }

    /**
     * Should remove a Customer from the database.
     *
     * @param customer
     * @throws SQLException
     */
    public static void removeCustomer(Customer customer) throws SQLException {
        //allCustomers.remove(customer);
        //ConnectDB.deleteContact("customers", "Customer_ID", String.valueOf(customer.customerID));
        int customerID = customer.getCustomerID();
        Connection con = ConnectDB.connection();
        PreparedStatement stmt = con.prepareCall("DELETE FROM customers WHERE Customer_ID = ?;");
        stmt.setInt(1, customerID);
        stmt.executeUpdate();
    }
    private static int highest;
    private static int num;
    private static int customerIDCount;

    /**
     * Should return the highest CustomerID and add 1.
     */
    public static int getCustomerIDCount() throws SQLException {

        ResultSet rs = ConnectDB.prepStatement("SELECT MAX(Customer_ID) AS nextIDNum FROM customers");
        int count = 0;
        while (rs.next()) {
            count = rs.getObject("nextIDNum", Integer.class);

        }
        return count + 1;
    }

    /**
     * Should split phone into phoneNumber
     */
    public static String splitPhoneToPhoneNumber(String phone) {
        String phoneNum = phone.replace("-", "");
        String phoneNumber = "";

        if (phoneNum.length() < 10) {
            phoneNumber = "Input error, please input a phone number.";
        } else if (phoneNum.length() == 10) {
            phoneNumber = phoneNum;
        } else if (phoneNum.length() > 10) {
            phoneNumber = phoneNum.substring(phoneNum.length() - 10, phoneNum.length());
        }

        return phoneNumber;

    }

    /**
     * Should split phone into phoneCode
     */
    public static String splitPhoneToPhoneCode(String phone) {
        String phoneNum = phone.replace("-", "");
        String phoneCode = "";

        if (phoneNum.length() < 10) {
            phoneCode = "Input error, please input a phone number.";
        } else if (phoneNum.length() == 10) {
            phoneCode = "1";
        } else {
            phoneCode = phoneNum.substring(0, phoneNum.length() - 10);
        }
        return phoneCode;

    }

    /**
     * Should give format the given phone number.
     *
     * @param phone
     * @return
     */
    public static String buildPhoneNum(String phone) {

        String str = phone;
        char ch = '-';
        int pos1;
        int pos2;
        int pos3;
        StringBuilder sb = new StringBuilder(str);
        String tooBeAdded;
        //finds positions if the num is greater than 10
        if (str.length() > 10) {
            pos1 = str.length() - 10;
            pos2 = pos1 + 4;
            pos3 = pos2 + 4;

            sb.insert(pos1, ch);
            sb.insert(pos2, ch);
            sb.insert(pos3, ch);
        } else if (str.length() == 10) {
            pos1 = 3;
            pos2 = 7;

            //StringBuilder sb = new StringBuilder(str);
            sb.insert(pos1, ch);
            sb.insert(pos2, ch);
        } else { // throws error
            return "Phone Numbers must be 10 digits in length, please add more digits.";

        }

        return sb.toString();

    }

    /**
     * Should clean the data entered and return only numeric digits.
     *
     * @param value
     * @return
     */
    public static String numericOnly(String value) {
        return value.replaceAll("[^0-9]", "");
    }

}
