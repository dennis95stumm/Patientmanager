package de.thm.stumm.patientmanager;

import de.thm.stumm.patientmanager.controller.LoginController;

/**
 * The main class of the application containing the main method.
 *
 * @author Dennis Stumm
 */
public class Patientmanager {
    /**
     * Initializes the LoginController, which initializes the view to force the user to login to the application.
     *
     * @param args
     */
    public static void main(String[] args) {
        new LoginController();
    }
}
