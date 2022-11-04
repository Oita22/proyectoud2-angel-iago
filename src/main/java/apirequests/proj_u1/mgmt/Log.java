package apirequests.proj_u1.mgmt;

import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Log class.
 */
public class Log {
    /**
     * Write to a log file the Exception during file operations.
     *
     * @param err Error message
     * @param e Exception error occurred
     */
    public static void writeErr(String err, Exception e) {
        write(("src/main/resources/log/log_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + ".txt"),
                (err + '\n' + e.getMessage()));
    }

    /**
     * Write in a log the requests
     *
     * @param urlRequest URL request
     */
    public static void writeRequest(String urlRequest) {
        write("src/main/resources/log/logRequest.txt", ("Request:\t" + urlRequest));
    }

    /**
     * Writing operation.
     *
     * @param path File path to save
     * @param msg Error message
     */
    private static void write(String path, String msg) {
        try (FileWriter fileWriter = new FileWriter(path, true)) {
            fileWriter.write('[' + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss")) + "]\t:" + msg + "\n\n");
        } catch (Exception e) {
        }
    }
}
