package Reports;

import Database.MyTime;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * This class exports login data to a text file.
 */
public class LoginActivityLog {

    /**
     * This will write a statement to the login text file. It will either say
     * that the login was successful or not.
     *
     * @param file
     * @param username
     * @param success
     * @param currentTime
     */
    public static void appendLoginActivity(File file, String username, boolean success, LocalDateTime currentTime) {
        String content = "";
        if (success == true) {
            content = "User " + username + " successfully logged in at " + currentTime.format(MyTime.dtf);
        } else {
            content = "User " + username + " unsuccessfully logged in at " + currentTime.format(MyTime.dtf);
        }

        try (FileWriter fw = new FileWriter(file, true);
                BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(content);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
