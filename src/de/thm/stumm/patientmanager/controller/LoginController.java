package de.thm.stumm.patientmanager.controller;

import de.thm.stumm.patientmanager.model.MalformedCsvLineException;
import de.thm.stumm.patientmanager.model.User;
import de.thm.stumm.patientmanager.model.UserList;
import de.thm.stumm.patientmanager.view.LoginView;

import java.io.IOException;

/**
 * Controller that handles the login for the application.
 *
 * @author Dennis Stumm
 */
public class LoginController {
    /**
     * UserList containing all existing users in the system.
     */
    private UserList users;

    /**
     * Gets the instance of the UserList, initializes and renders the LoginView.
     */
    public LoginController() {
        LoginView view = new LoginView(this);

        try {
            users = UserList.getInstance();
            view.render();
        } catch (MalformedCsvLineException exception) {
            view.printError("Beim Laden der Daten aus der CSV-Datei ist folgender Fehler aufgetreten:\n" + exception.getMessage());
        } catch (IOException exception) {
            view.printError("Beim Lesen der CSV-Datei ist folgender Fehler aufgetreten:\n" + exception.getLocalizedMessage());
        }
    }

    /**
     * Tries to find the passed user in the UserList.
     *
     * @param user User to search for in the UserList.
     * @return true if the user could be found in the UserList, false otherwise.
     */
    public boolean login(User user) {
        return users.find(user) != null;
    }

    /**
     * Initializes the next controller, whose view should be displayed after a successful login.
     */
    public void transition() {
        new PatientController();
    }
}
