package Model;

import java.time.LocalDateTime;

/**
 * This Class contains the constructors and all methods associated with the
 * CustomerSchedule object.
 */
public class CustomerSchedule {

    private int appointmentID, contactID;
    private String title, type, description;
    private LocalDateTime start, end;

    /**
     * This is the standard constructor for this class.
     *
     * @param appointmentID
     * @param contactID
     * @param title
     * @param type
     * @param description
     * @param start
     * @param end
     */
    public CustomerSchedule(int appointmentID, int contactID, String title, String type, String description, LocalDateTime start, LocalDateTime end) {
        this.appointmentID = appointmentID;
        this.contactID = contactID;
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
     * Getter for ContactID.
     *
     * @return
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Setter for ContactID.
     *
     * @param contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
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
