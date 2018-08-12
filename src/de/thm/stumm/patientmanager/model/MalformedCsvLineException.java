package de.thm.stumm.patientmanager.model;

/**
 * Exception that gets thrown, when a line in the CSV-File is malformed.
 */
public class MalformedCsvLineException extends Exception {
    /**
     * Initializes the exception object.
     *
     * @param message Message to display to the user.
     */
    MalformedCsvLineException(String message) {
        super(message);
    }
}
