package de.thm.stumm.patientmanager.view;

import de.thm.Sha1;
import de.thm.stumm.patientmanager.controller.LoginController;
import de.thm.stumm.patientmanager.model.User;

import java.util.Scanner;

/**
 * View for the login to the application.
 *
 * @author Dennis Stumm
 */
public class LoginView {
    /**
     * The LoginController handling and providing the data the user inputs.
     */
    private LoginController controller;

    /**
     * Initializes the LoginView.
     *
     * @param controller The LoginController handling and providing the data the user inputs.
     */
    public LoginView(LoginController controller) {
        this.controller = controller;
    }

    /**
     * Renders the login view on the console.
     */
    public void render() {
        System.out.println("************ Login ************");
        this.showLogin();
    }

    /**
     * Reads the login data from console input and tries to login into the application with the inputted data.
     * <p>
     * If the login was incorrect the user gets informed and the user will forced again to login by calling this
     * method recursively. Otherwise the next view gets displayed by calling the transition method of the controller.
     */
    private void showLogin() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Benutzername: ");
        String username = sc.nextLine();
        System.out.print("Passwort: ");
        String password = sc.nextLine();
        password = new Sha1().getHash(password);

        if (!this.controller.login(new User(username, password))) {
            System.out.println("Der eingegebene Benutzername oder das Passwort ist falsch!");
            System.out.println("Drücken Sie Enter um den Login erneut zu versuchen!");
            sc.nextLine();
            this.showLogin();
        } else {
            controller.transition();
        }
    }
}
