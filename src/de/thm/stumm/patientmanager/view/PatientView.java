package de.thm.stumm.patientmanager.view;

import de.thm.Sha1;
import de.thm.stumm.patientmanager.controller.PatientController;
import de.thm.stumm.patientmanager.model.Patient;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * View containing methods for managing patients and users of the application.
 *
 * @author Dennis Stumm
 */
public class PatientView extends View {
    /**
     * Nested string array with the actions of this view.
     * <p>
     * Each element of the array is an array of strings containing as the first element a description and as the second
     * element the name of the method to be called when the user selects the corresponding action.
     */
    private final String[][] actions = {
            {"einen Patienten anzulegen", "createPatient"},
            {"einen Patienten anhand seiner ID zu finden", "searchPatientById"},
            {"Patienten anhand des Namens zu finden", "searchPatientsByName"},
            {"einen Patienten zu löschen", "deletePatient"},
            {"einen Patienten zu entlassen", "dischargePatient"},
            {"einem Patienten eine Diagnose hinzuzufügen", "editIcdOfPatient"},
            {"alle Patienten auszugeben", "printPatients"},
            {"einen Benutzer hinzuzufügen", "createUser"},
            {"einen Benutzer zu löschen", "deleteUser"},
            {"das Programm zu beenden", "exit"}
    };

    /**
     * Controller that handles the user interactions and communicates with the models.
     */
    private PatientController controller;

    /**
     * Scanner object that is used to read user input from the console.
     */
    private Scanner scanner;

    /**
     * Initializes the PatientView.
     *
     * @param controller Controller that handles the user interactions and communicates with the models.
     */
    public PatientView(PatientController controller) {
        this.controller = controller;
        scanner = new Scanner(System.in);
    }

    /**
     * Renders the main menu for the patient view on the console.
     */
    public void render() {
        this.printTitle("Willkommen");
        this.showActionSelect();
    }

    /**
     * Renders the view for creating a new patient, reads the inputs and calls the necessary method on the controller.
     */
    @SuppressWarnings("unused")
    private void createPatient() {
        printTitle("Patienten anlegen");
        String firstName = readString("Vorname: ", false);
        String lastName = readString("Name: ", false);
        int age = readInt("Alter (in Jahren): ", 0, 150);
        String icd = readString("ICD: ", true);
        controller.createPatient(firstName, lastName, age, icd);
        returnToMainMenu("Der Patient wurde erfolgreich angelegt!");
    }

    /**
     * Renders the view for creating a new user, reads the inputs and calls the necessary method on the controller.
     */
    @SuppressWarnings("unused")
    private void createUser() {
        printTitle("Benutzer anlegen");
        String username = readString("Benutzername: ", false);
        String password = readString("Passwort: ", false);
        password = new Sha1().getHash(password);
        controller.createUser(username, password);
        returnToMainMenu("Der Benutzer wurde erfolgreich angelegt!");
    }

    /**
     * Renders the view for deleting a patient, reads the inputs and calls the necessary method on the controller.
     */
    @SuppressWarnings("unused")
    private void deletePatient() {
        printTitle("Patienten löschen");
        int id = readInt("ID: ");
        boolean success = controller.deletePatient(id);
        String message = "Der Patient wurde erfolgreich gelöscht!";
        message = success ? message : "Der Patient mit der ID '" + id + "' konnte nicht gelöscht werden!";
        returnToMainMenu(message);
    }

    /**
     * Renders the view for deleting a user, reads the inputs and calls the necessary method on the controller.
     */
    @SuppressWarnings("unused")
    private void deleteUser() {
        printTitle("Benutzer löschen");
        String username = readString("Benutzername: ", false);
        boolean success = controller.deleteUser(username);
        String message = "Der Benutzer wurde erfolgreich gelöscht!";
        message = success ? message : "Der Benutzer mit dem Benutzernamen '" + username + "' konnte nicht gelöscht werden!";
        returnToMainMenu(message);
    }

    /**
     * Renders the view for discharging a patient, reads the inputs and calls the necessary method on the controller.
     */
    @SuppressWarnings("unused")
    private void dischargePatient() {
        printTitle("Patienten entlassen");
        Patient patient = getPatient();
        if (patient.getDischargeDate() != null) {
            returnToMainMenu("Der Patient wurde bereits entlassen!");
        } else {
            patient.setDischargeDate(new Date());
            returnToMainMenu("Der Patient wurde mit dem heutigen Datum erfolgreich entlassen!");
        }
    }

    /**
     * Renders the view for editing the icd of a patient, reads the inputs and calls the necessary method on the
     * controller.
     */
    @SuppressWarnings("unused")
    private void editIcdOfPatient() {
        printTitle("Patienten Diagnose hinzufügen");
        Patient patient = getPatient();
        System.out.println();
        System.out.println("Alter Wert: " + patient.getIcd());
        patient.setIcd(readString("Neuer Wert: ", true));
        returnToMainMenu("Der ICD des Patienten wurde erfolgreich aktualisiert!");
    }

    /**
     * Calls the methods to persisting the data and finishes the program.
     */
    @SuppressWarnings("unused")
    private void exit() {
        try {
            System.out.print("Benutzer werden gespeichert...");
            controller.persistUsers();
            System.out.println("fertig");
        } catch (IOException e) {
            System.out.println("Fehler (" + e.getLocalizedMessage() + ")");
        }

        try {
            System.out.print("Patienten werden gespeichert...");
            controller.persistPatients();
            System.out.println("fertig");
        } catch (IOException e) {
            System.out.println("Fehler (" + e.getLocalizedMessage() + ")");
        }

        System.out.println("Auf wiedersehen!");
    }

    /**
     * Reads the id of a patient from the user input and searches the patient with the given id.
     * <p>
     * When the patient for the given id was found, the patient will be returned. Otherwise the user is forced to input
     * an id as long as no patient is found for the inputted id.
     *
     * @return The patient that was found for the inputted id.
     */
    private Patient getPatient() {
        int id = readInt("ID: ");
        Patient patient = controller.searchPatient("id", id);

        if (patient == null) {
            System.out.println("Es existiert kein Patient mit der eingegebenen ID!");
            System.out.println();
            return getPatient();
        }

        return patient;
    }

    /**
     * Reads the input from the console and determines which method should be called or which action should be executed.
     * After determining the necessary method it gets called dynamically.
     */
    private void handleActionSelection() {
        int selection = readInt("", 1, 10);
        String method = this.actions[selection - 1][1];
        System.out.println();
        try {
            this.getClass().getDeclaredMethod(method).invoke(this);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints out to the console all patients that are available in this application.
     */
    @SuppressWarnings("unused")
    private void printPatients() {
        printTitle("Patienten");
        for (Patient patient : controller.getPatients()) {
            System.out.println(patient);
            System.out.println();
        }
        returnToMainMenu("");
    }

    /**
     * Forces the user to input an integer and returns the inputted integer.
     *
     * @param message The message, that should be print out to the user before forcing input.
     * @return The read integer from the user input.
     */
    private int readInt(String message) {
        System.out.print(message);

        try {
            int val = scanner.nextInt();
            scanner.nextLine();
            return val;
        } catch (InputMismatchException exception) {
            System.out.println("Bitte geben Sie eine Zahl ein!");
            scanner.nextLine();
            return readInt(message);
        }
    }

    /**
     * Forces the user to input an integer in the passed range and returns the inputted integer.
     *
     * @param message The message, that should be print out to the user before forcing input.
     * @param min     The minimum value for the inputted integer.
     * @param max     The maximum value for the inputted integer.
     * @return The read integer from the user input.
     */
    private int readInt(String message, int min, int max) {
        int number = readInt(message);

        if (number < min || number > max) {
            System.out.println("Bitte geben Sie eine Zahl zwischen " + min + " und " + max + " ein!");
            System.out.println();
            return readInt(message, min, max);
        }

        return number;
    }

    /**
     * Forces the user to input a string and returns the inputted string.
     *
     * @param message    The message, that should be print out to the user before forcing input.
     * @param allowEmpty Boolean value, that indicates whether the string can be empty or not.
     * @return The read string from the user input.
     */
    private String readString(String message, boolean allowEmpty) {
        System.out.print(message);
        String value = scanner.nextLine();

        if (value.equals("") && !allowEmpty) {
            System.out.println("Die Eingabe darf nicht leer sein!");
            System.out.println();
            return readString(message, allowEmpty);
        }

        return value;
    }

    /**
     * Returns to the main menu after printing out the passed message and forcing the user to press enter.
     *
     * @param message The message that should be shown before transitioning.
     */
    private void returnToMainMenu(String message) {
        System.out.println(message);
        System.out.println("Drücken Sie Enter um zum Hauptmenü zurückzukehren!");
        scanner.nextLine();
        render();
    }

    /**
     * Renders the view for searching a patient by the id, reads the inputs and calls the necessary method on the
     * controller.
     */
    @SuppressWarnings("unused")
    private void searchPatientById() {
        printTitle("Patienten suchen");
        int id = readInt("ID: ");
        Patient patient = controller.searchPatient("id", id);
        if (patient != null) {
            System.out.println(patient);
        } else {
            System.out.println("Patient mit dieser ID exisiert nicht!");
        }
        returnToMainMenu("");
    }

    /**
     * Renders the view for searching patients by last name, reads the inputs and calls the necessary method on the
     * controller.
     */
    @SuppressWarnings("unused")
    private void searchPatientsByName() {
        printTitle("Patienten suchen");
        String name = readString("Name: ", false);
        Patient[] patients = controller.searchPatients("lastName", name);

        if (patients.length == 0) {
            System.out.println("Es wurde kein Patient gefunden!");
        }

        for (Patient patient : patients) {
            System.out.println(patient);
            System.out.println();
        }

        returnToMainMenu("");
    }

    /**
     * Renders the main menu on the console.
     */
    private void showActionSelect() {
        for (int i = 0; i < this.actions.length; i++) {
            System.out.println("Drücken Sie die " + (i + 1) + " um " + this.actions[i][0] + "!");
        }
        this.handleActionSelection();
    }
}
