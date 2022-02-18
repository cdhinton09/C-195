package Database;

import Model.Appointment;
import Model.Contact;
import Model.ContactSchedule;
import Model.Country;
import Model.Customer;
import Model.CustomerSchedule;
import Model.FirstLevelDivision;
import Model.Messages;
import Model.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The class is used by other classes to query the database.
 */
public class ConnectDB {

    public static String appointmentStatement = "SELECT * FROM appointments";
    public static String contactsStatement = "SELECT * FROM contacts";
    public static String countriesStatement = "SELECT * FROM countries";
    public static String customerStatement = "SELECT * FROM customers";
    public static String firstLevelDivision = "SELECT * FROM first_level_divisions";
    public static String usersStatement = "SELECT * FROM users";

    //Prepared statementS
    //This prepared statement should allow the user to call up specific data from the database.
    public static String selectPrepStatement = "SELECT ? FROM ?";
    //This prepared statement should allow the user to add data to a specific table in the database.
    public static String insertPrepStatement = "INSERT INTO ? (?) VALUES (?)";
    //This prepared statement should allow the user to remove data from the database.
    public static String deletePrepStatement = "DELETE FROW ? WHERE ? = ?";
    //This prepared statement should allow the usre to update a specific data row from in the database.
    public static String updatePrepStatement = "UPDATE ? SET (?) = (?) WHERE ? = ?";

    private static String source = "jdbc:mysql://wgudb.ucertify.com/WJ077GX";
    private static String user = "U077GX";
    private static String pass = "53688956344";

    public static Connection connection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(source, user, pass);

        } catch (SQLException e) {
            Messages.connectionError();
        }
        return conn;
    }

    /**
     * This method it used to query the database and collect all appointment
     * information. It returns an ObservableList of type Appointment.
     */
    public static ObservableList<Appointment> collectAppointments() throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        Connection connection = DriverManager.getConnection(source, user, pass);
        Statement stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery(appointmentStatement);

        while (rs.next()) {
            int appointmentID = rs.getObject("Appointment_ID", Integer.class);
            int customerID = rs.getObject("Customer_ID", Integer.class);
            int userID = rs.getObject("User_ID", Integer.class);
            int contactID = rs.getObject("Contact_ID", Integer.class);
            String title = rs.getObject("Title", String.class);
            String description = rs.getObject("Description", String.class);
            String type = rs.getObject("Type", String.class);
            String createdBy = rs.getObject("Created_By", String.class);
            String lastUpdatedBy = rs.getObject("Last_Updated_By", String.class);
            Timestamp start = rs.getObject("Start", Timestamp.class);
            String s = MyTime.utcToLocal(start).format(MyTime.dtf);
            LocalDateTime startLDT = LocalDateTime.parse(s, MyTime.dtf);
            //MyTime.utcToLocal(start)

            Timestamp end = rs.getObject("End", Timestamp.class);
            Timestamp createDate = rs.getObject("Create_Date", Timestamp.class);
            Timestamp lastUpdate = rs.getObject("Last_Update", Timestamp.class);
            String location = rs.getObject("Location", String.class);

            Appointment appointment = new Appointment(appointmentID,
                    customerID, userID, contactID, title, description,
                    type, createdBy, lastUpdatedBy, startLDT, MyTime.utcToLocal(end), MyTime.utcToLocal(createDate),
                    MyTime.utcToLocal(lastUpdate), location);
            //Appointment.addAppointment(appointment);
            allAppointments.add(appointment);
        }
        return allAppointments;// Appointment.getAllAppointments();
    }

    /**
     * This method it used to query the database and collect all contact
     * information. It returns an ObservableList of type Contact.
     */
    public static ObservableList<Contact> collectContacts() throws SQLException {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();

        Connection connection = DriverManager.getConnection(source, user, pass);
        Statement stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery(contactsStatement);

        while (rs.next()) {
            int contactID = rs.getObject("Contact_ID", Integer.class);
            String contactName = rs.getObject("Contact_Name", String.class);
            String email = rs.getObject("Email", String.class);

            Contact contact = new Contact(contactID, contactName, email);

            allContacts.add(contact);
        }
        return allContacts;
    }

    /**
     * This method it used to query the database and collect all customers
     * information. It returns an ObservableList of type Customer.
     */
    public static ObservableList<Customer> collectCustomers() throws SQLException {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        //Connect to database
        Connection connection = DriverManager.getConnection(source, user, pass);
        Statement stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

        ResultSet rs = stmt.executeQuery(customerStatement);

        while (rs.next()) {
            
            String phone = rs.getObject("Phone", String.class); //phoneCode + phoneNumber;
            int customerID = rs.getObject("Customer_ID", Integer.class);
            int divisionID = rs.getObject("Division_ID", Integer.class);
            String customerName = rs.getObject("Customer_Name", String.class);
            String address = rs.getObject("Address", String.class);
            String postalCode = rs.getObject("Postal_Code", String.class);
            String createdBy = rs.getObject("Created_By", String.class);
            String lastUpdatedBy = rs.getObject("Last_Updated_By", String.class);

            LocalDateTime createDate = rs.getObject("Create_Date", LocalDateTime.class);
            LocalDateTime lastUpdate = rs.getObject("Last_Update", LocalDateTime.class);

            Customer customer = new Customer(customerID, divisionID, customerName,
                    address, postalCode, createdBy, lastUpdatedBy, phone,
                    createDate, lastUpdate);
            allCustomers.add(customer);
        }
        return allCustomers; 
    }

   /**
     * This method it used to query the database and collect all countries
     * information. It returns an ObservableList of type Country.
     */ 
    public static ObservableList<Country> collectCountries() throws SQLException {
        Connection connection = DriverManager.getConnection(source, user, pass);
        Statement stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery(countriesStatement);

        ObservableList<Country> list = FXCollections.observableArrayList();

        while (rs.next()) {
            int countryID = rs.getObject("Country_ID", Integer.class);
            String country = rs.getObject("Country", String.class);
            String createdBy = rs.getObject("Created_By", String.class);
            String lastUpdatedBy = rs.getObject("Last_Updated_By", String.class);
            String createDate = rs.getObject("Create_Date", String.class);
            String lastUpdate = rs.getObject("Last_Update", String.class);

            Country newCountry = new Country(countryID, country, createDate, createdBy, lastUpdate, lastUpdatedBy);

            Country.addCountry(newCountry);
            list.add(newCountry);
        }
        return list;
    }

    /**
     * This method it used to query the database and collect all first level divisions
     * information. It returns an ObservableList of type FirstLevelDivision.
     */
    public static ObservableList<FirstLevelDivision> collectFirstDivision() throws SQLException {
        Connection connection = DriverManager.getConnection(source, user, pass);
        Statement stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery(firstLevelDivision);

        ObservableList list = FXCollections.observableArrayList();
        while (rs.next()) {
            int divisionID = rs.getObject("Division_ID", Integer.class);
            String division = rs.getObject("Division", String.class);
            Time createdDate = rs.getObject("Create_Date", Time.class);
            String createdBy = rs.getObject("Created_By", String.class);
            Time lastUpdated = rs.getObject("Last_Update", Time.class);
            String lastUpdatedBy = rs.getObject("Last_Updated_By", String.class);
            int countryID = rs.getObject("COUNTRY_ID", Integer.class);

            FirstLevelDivision state = new FirstLevelDivision(divisionID,
                    division, createdDate, createdBy, lastUpdated, lastUpdatedBy,
                    countryID);

            FirstLevelDivision.addDivision(state);
            list.add(state);
        }
        return list;
    }

    /**
     * This method it used to query the database and collect all users
     * information. It returns an ObservableList of type User.
     */
    public static void collectUsers() throws SQLException {
        Connection connection = DriverManager.getConnection(source, user, pass);
        Statement stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery("SELECT * FROM users");

        while (rs.next()) {
            int userID = rs.getObject("User_ID", Integer.class);
            String userName = rs.getObject("User_Name", String.class);
            String password = rs.getObject("Password", String.class);
            String createDate = rs.getObject("Create_Date", String.class);
            String createdBy = rs.getObject("Created_By", String.class);
            String lastUpdate = rs.getObject("Last_Update", String.class);
            String lastUpdatedBy = rs.getObject("Last_Updated_By", String.class);

            User user = new User(userID, userName, password, createDate,
                    createdBy, lastUpdate, lastUpdatedBy);

            //userList.add(user);
            User.addUsers(user);
        }

    }
/**
     * This method it used to query the database and collect all appointment,
     * contact, country, customer, and first level division information. 
     */
    public static void collectAllData() throws SQLException {

        collectAppointments();
        collectContacts();
        collectCountries();
        collectCustomers();
        collectFirstDivision();
        //collectUsers(usersStatement);

    }

  
/**
 * This method is used to delete a Contact from the database.
 */
    public static void deleteContact(String tableName, String key, String value) throws SQLException {
        Connection connection = DriverManager.getConnection(source, user, pass);
        PreparedStatement stmt = connection.prepareStatement(deletePrepStatement);
        stmt.setString(1, tableName);
        stmt.setString(2, key);
        stmt.setString(3, value);

        stmt.executeUpdate();
    }

    /**
     * This is a utility method used to query the database.  Any prepared statement 
     * can be passed as a parameter and a ResultSet will be returned.
     */
    public static ResultSet prepStatement(String statement) throws SQLException {
        Connection con = DriverManager.getConnection(source, user, pass);
        Statement stmt = con.createStatement(ResultSet.FETCH_FORWARD, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stmt.executeQuery(statement);

        return rs;
    }

    /**
     * This method will query the database and return any Appointment set to occur
     * within the next month.  It will return an ObservableList of type Appointment.
     */
    public static ObservableList<Appointment> monthlyAppts() {
        ObservableList<Appointment> monthlyAppts = FXCollections.observableArrayList();

        try {
            Connection con = DriverManager.getConnection(source, user, pass);
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM  "
                    + "WJ077GX.appointments WHERE Start BETWEEN NOW() AND NOW() + INTERVAL 1 MONTH");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int appointmentID = rs.getObject("Appointment_ID", Integer.class);
                int customerID = rs.getObject("Customer_ID", Integer.class);
                int userID = rs.getObject("User_ID", Integer.class);
                int contactID = rs.getObject("Contact_ID", Integer.class);
                String title = rs.getObject("Title", String.class);
                String description = rs.getObject("Description", String.class);
                String type = rs.getObject("Type", String.class);
                String createdBy = rs.getObject("Created_By", String.class);
                String lastUpdatedBy = rs.getObject("Last_Updated_By", String.class);
                Timestamp start = rs.getObject("Start", Timestamp.class);
                Timestamp end = rs.getObject("End", Timestamp.class);
                Timestamp createDate = rs.getObject("Create_Date", Timestamp.class);
                Timestamp lastUpdate = rs.getObject("Last_Update", Timestamp.class);
                String location = rs.getObject("Location", String.class);

                Appointment appointment = new Appointment(appointmentID,
                        customerID, userID, contactID, title, description,
                        type, createdBy, lastUpdatedBy, MyTime.utcToLocal(start), MyTime.utcToLocal(end), MyTime.utcToLocal(createDate),
                        MyTime.utcToLocal(lastUpdate), location);
                //Appointment.addAppointment(appointment);
                monthlyAppts.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return monthlyAppts;
    }

   /**
     * This method will query the database and return any Appointment set to occur
     * within the next week.  It will return an ObservableList of type Appointment.
     */
    public static ObservableList<Appointment> weeklyAppts() {
        ObservableList<Appointment> weeklyAppts = FXCollections.observableArrayList();

        try {
            Connection con = DriverManager.getConnection(source, user, pass);
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM  "
                    + "WJ077GX.appointments WHERE Start BETWEEN NOW() AND NOW() + INTERVAL 1 WEEK");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int appointmentID = rs.getObject("Appointment_ID", Integer.class);
                int customerID = rs.getObject("Customer_ID", Integer.class);
                int userID = rs.getObject("User_ID", Integer.class);
                int contactID = rs.getObject("Contact_ID", Integer.class);
                String title = rs.getObject("Title", String.class);
                String description = rs.getObject("Description", String.class);
                String type = rs.getObject("Type", String.class);
                String createdBy = rs.getObject("Created_By", String.class);
                String lastUpdatedBy = rs.getObject("Last_Updated_By", String.class);
                Timestamp start = rs.getObject("Start", Timestamp.class);
                Timestamp end = rs.getObject("End", Timestamp.class);
                Timestamp createDate = rs.getObject("Create_Date", Timestamp.class);
                Timestamp lastUpdate = rs.getObject("Last_Update", Timestamp.class);
                String location = rs.getObject("Location", String.class);

                Appointment appointment = new Appointment(appointmentID,
                        customerID, userID, contactID, title, description,
                        type, createdBy, lastUpdatedBy, MyTime.utcToLocal(start), MyTime.utcToLocal(end), MyTime.utcToLocal(createDate),
                        MyTime.utcToLocal(lastUpdate), location);
                //Appointment.addAppointment(appointment);
                weeklyAppts.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return weeklyAppts;
    }

   /**
    * This method queries the database for the start time of all existing appointment 
    * for a specific customer.  It take a parameter of CustomerID and returns an 
    * ObservableList of type LocalDateTime.
    */ 
    public static ObservableList<LocalDateTime> apptListStartTimeAddingAppt(int customerID) {
        ObservableList<LocalDateTime> list = FXCollections.observableArrayList();
        
        try {
            Connection con = DriverManager.getConnection(source, user, pass);

            PreparedStatement stmt = con.prepareStatement("SELECT Start FROM  "
                    + "WJ077GX.appointments WHERE Customer_ID = ?;");
            stmt.setInt(1, customerID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Timestamp startTS = rs.getObject("Start", Timestamp.class);
                LocalDateTime startEST = startTS.toLocalDateTime();
                //Convert to EST
                startEST = MyTime.timeToEST(startEST);
                //    LocalTime startLT = startEST.toLocalTime();
                list.add(startEST);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;

    }

   /**
    * This method queries the database for the end time of all existing appointment 
    * for a specific customer.  It take a parameter of CustomerID and returns an 
    * ObservableList of type LocalDateTime.
    */
    public static ObservableList<LocalDateTime> apptListEndTimeAddingAppt(int customerID) {
        ObservableList<LocalDateTime> list = FXCollections.observableArrayList();

        try {
            Connection con = DriverManager.getConnection(source, user, pass);
            PreparedStatement stmt = con.prepareStatement("SELECT End FROM  "
                    + "WJ077GX.appointments WHERE Customer_ID = ?");
            stmt.setInt(1, customerID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                /*      Timestamp endTS = rs.getObject("End", Timestamp.class);
                LocalDateTime endLDT = endTS.toLocalDateTime();     */
                LocalDateTime end = rs.getObject("End", Timestamp.class).toLocalDateTime();
                LocalDateTime endEST = MyTime.timeToEST(end);
                //    LocalTime endLT = endEST.toLocalTime();
                list.add(endEST);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    /**
    * This method queries the database for the start time of all existing appointment 
    * for a specific customer.  It take a parameter of CustomerID and AppointmentID
    * then returns an ObservableList of type LocalDateTime.
    */
    public static ObservableList<LocalDateTime> apptListStartTimeUpdatingAppt(int customerID, int apptID) {
        ObservableList<LocalDateTime> list = FXCollections.observableArrayList();
        //should retrieve start and end times from appoint DB
        //convert the timestamps to LocalDateTime
        //convert origin LDT to origin EST LDT
        //split LDT to LocalTime
        //return a observable list of LocalTime
        try {
            Connection con = DriverManager.getConnection(source, user, pass);

            PreparedStatement stmt = con.prepareStatement("SELECT Start FROM  "
                    + "WJ077GX.appointments WHERE Customer_ID = ? AND Appointment_ID != ?;");
            stmt.setInt(1, customerID);
            stmt.setInt(2, apptID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Timestamp startTS = rs.getObject("Start", Timestamp.class);
                LocalDateTime startEST = startTS.toLocalDateTime();
                //Convert to EST
                startEST = MyTime.timeToEST(startEST);
                //LocalTime startLT = startEST.toLocalTime();
                list.add(startEST);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("The problem is here");
        }
        return list;

    }

    /**
    * This method queries the database for the end time of all existing appointment 
    * for a specific customer.  It take a parameter of CustomerID and AppointmentID,
    * then returns an ObservableList of type LocalDateTime.
    */
    public static ObservableList<LocalDateTime> apptListEndTimeUpdatingAppt(int customerID, int apptID) {
        ObservableList<LocalDateTime> list = FXCollections.observableArrayList();

        try {
            Connection con = DriverManager.getConnection(source, user, pass);
            PreparedStatement stmt = con.prepareStatement("SELECT End FROM  "
                    + "WJ077GX.appointments WHERE Customer_ID = ? AND Appointment_ID != ?");
            stmt.setInt(1, customerID);
            stmt.setInt(2, apptID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                LocalDateTime end = rs.getObject("End", Timestamp.class).toLocalDateTime();
                LocalDateTime endEST = MyTime.timeToEST(end);
                //LocalTime endLT = endEST.toLocalTime();
                list.add(endEST);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

/**
 * This method queries the database for Appointment associated with a specific Contact.
 * It take a parameter of int type and returns an ObservableList of ContactSchedule type.
 */
    public static ObservableList<ContactSchedule> contactSchedules(int contactNum) {
        ObservableList<ContactSchedule> list = FXCollections.observableArrayList();
        try {
            Connection con = DriverManager.getConnection(source, user, pass);
            PreparedStatement stmt = con.prepareCall("SELECT Appointment_ID, Title,"
                    + "Type, Description, Start, End, Customer_ID "
                    + "FROM WJ077GX.appointments\n"
                    + "WHERE Contact_ID = ?;");
            stmt.setInt(1, contactNum);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int apptID = rs.getObject("Appointment_ID", Integer.class);
                String title = rs.getObject("Title", String.class);
                String type = rs.getObject("Type", String.class);
                String description = rs.getObject("Description", String.class);
                Timestamp startTS = rs.getObject("Start", Timestamp.class);
                LocalDateTime start = startTS.toLocalDateTime();
                Timestamp endTS = rs.getObject("End", Timestamp.class);
                LocalDateTime end = endTS.toLocalDateTime();
                int customerID = rs.getObject("Customer_ID", Integer.class);

                ContactSchedule next = new ContactSchedule(apptID, customerID, title, type, description, start, end);
                list.add(next);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
/**
 * This method queries the database for all Appointments during a specific month
 * that are of a specific type.  It takes two parameters, one String and one int.  
 * It returns the number of appointments that match both parameters.
 * @param month
 * @param type
 * @return
 * @throws SQLException 
 */
    public static int appointmentTotals(int month, String type) throws SQLException {
        int count = -1;

        Connection con = DriverManager.getConnection(source, user, pass);
        //prepared statement asking for count of types in a month
        PreparedStatement stmt = con.prepareStatement(
                "SELECT COUNT(*) as 'Count' FROM WJ077GX.appointments WHERE type = ?  AND MONTH(start) = ?;");
        stmt.setString(1, type);
        stmt.setInt(2, month);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            count = rs.getObject("Count", Integer.class);
        }

        return count;

    }
/**
 * This method queries the database for Appointment associated with a specific Customer.
 * It take a parameter of int type and returns an ObservableList of CustomerSchedule type.
 * @param customerNum
 * @return 
 */
    public static ObservableList<CustomerSchedule> customerSchedules(int customerNum) {
        ObservableList<CustomerSchedule> list = FXCollections.observableArrayList();
        try {
            Connection con = DriverManager.getConnection(source, user, pass);
            PreparedStatement stmt = con.prepareCall("SELECT Appointment_ID, Title,"
                    + "Type, Description, Start, End, Contact_ID "
                    + "FROM WJ077GX.appointments\n"
                    + "WHERE Customer_ID = ?;");
            stmt.setInt(1, customerNum);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int apptID = rs.getObject("Appointment_ID", Integer.class);
                String title = rs.getObject("Title", String.class);
                String type = rs.getObject("Type", String.class);
                String description = rs.getObject("Description", String.class);
                Timestamp startTS = rs.getObject("Start", Timestamp.class);
                LocalDateTime start = startTS.toLocalDateTime();
                Timestamp endTS = rs.getObject("End", Timestamp.class);
                LocalDateTime end = endTS.toLocalDateTime();
                int contactID = rs.getObject("Contact_ID", Integer.class);

                CustomerSchedule next = new CustomerSchedule(apptID, contactID, title, type, description, start, end);
                list.add(next);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
