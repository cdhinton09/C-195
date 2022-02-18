package Model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * The class contains all constructors and associated methods for the
 * ContactSchedule object.
 *
 * @author cdhin
 */
public class ContactSchedule {

    private int appointmentID, customerID;

    private String title, type, description;
    private LocalDateTime start, end;

    /**
     * This is the standard constructor for the ContactSchedule object.
     *
     * @param apptID
     * @param customerID
     * @param title
     * @param type
     * @param description
     * @param start
     * @param end
     */
    public ContactSchedule(int apptID, int customerID, String title, String type, String description, LocalDateTime start, LocalDateTime end) {
        this.appointmentID = apptID;
        this.customerID = customerID;
        this.title = title;
        this.type = type;
        this.description = description;
        this.start = start;
        this.end = end;
    }

    /**
     * Getting for AppointmentID.
     *
     * @return
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * Setter for AppointmentID.
     *
     * @param appointmentID
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * Getter for CustomerID.
     *
     * @return
     */
    public int getCustomerID() {
        return customerID;
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
     * Getter for Title.
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for Title.
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for Type.
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * Setter for Type.
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter for Description.
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for Description.
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for Start.
     *
     * @return
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Setter for Start.
     *
     * @param start
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Getter for End.
     *
     * @return
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Setter for End.
     *
     * @param end
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

}
