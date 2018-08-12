package de.thm.stumm.patientmanager.view;

/**
 * Main class for all views in this application containing some basically functionality, that is necessary in all views.
 */
public class View {
    /**
     * Prints an error to the console.
     *
     * @param message Error message to print to the console.
     */
    public void printError(String message) {
        printTitle("Fehler");
        System.out.println(message);
    }

    /**
     * Prints a title to the console.
     *
     * @param title Title to print to the console.
     */
    void printTitle(String title) {
        System.out.println("************ " + title + " ************");
    }
}
