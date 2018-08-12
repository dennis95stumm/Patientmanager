package de.thm.stumm.patientmanager.controller;

import de.thm.stumm.patientmanager.model.*;
import de.thm.stumm.patientmanager.view.PatientView;

import java.io.IOException;
import java.util.Arrays;

/**
 * Controller that handles the actions from the PatientView including the management of patients and users.
 *
 * @author Dennis Stumm
 */
public class PatientController {
    /**
     * PatientList containing all existing patients in the system.
     */
    private PatientList patients;

    /**
     * UserList containing all existing users in the system.
     */
    private UserList users;

    /**
     * Gets the instance of the UserList and PatientList, initializes and renders the PatientView.
     */
    public PatientController() {
        PatientView view = new PatientView(this);

        try {
            patients = PatientList.getInstance();
            users = UserList.getInstance();
            view.render();
        } catch (MalformedCsvLineException exception) {
            view.printError("Beim Laden der Daten aus der CSV-Datei ist folgender Fehler aufgetreten:\n" + exception.getMessage());
        } catch (IOException exception) {
            view.printError("Beim Lesen der CSV-Datei ist folgender Fehler aufgetreten:\n" + exception.getLocalizedMessage());
        }
    }

    /**
     * Adds a new patient to the application with the passed properties.
     * <p>
     * The admission date of the user will be set automatically to the current date.
     *
     * @param firstName First name of the patient.
     * @param lastName  Last name of the patient.
     * @param age       Age of the patient.
     * @param icd       ICD of the patient.
     */
    public void createPatient(String firstName, String lastName, int age, String icd) {
        patients.add(firstName, lastName, age, icd);
    }

    /**
     * Adds a new user to the application with the passed username and SHA1 hashed password.
     *
     * @param username Name of the user that should be created.
     * @param password SHA1 hashed password of the user that should be created.
     */
    public void createUser(String username, String password) {
        users.add(new User(username, password));
    }

    /**
     * Deletes the patient with the passed id from the system.
     *
     * @param id Id of the patient to delete the patient with.
     * @return Boolean value, that indicates whether the patient was deleted successfully or not.
     */
    public boolean deletePatient(int id) {
        Patient patient = searchPatient("id", id);

        if (patient != null) {
            this.patients.remove(patient);
            return true;
        }

        return false;
    }

    /**
     * Deletes the user with the passed username from the system.
     *
     * @param username Name of the user to delete the user with.
     * @return Boolean value, that indicates whether the user was deleted successfully or not.
     */
    public boolean deleteUser(String username) {
        User user = this.users.find("username", username);

        if (user != null) {
            this.users.remove(user);
            return true;
        }

        return false;
    }

    /**
     * @return PatientList containing all patients of the application.
     */
    public PatientList getPatients() {
        return patients;
    }

    /**
     * Calls the method to persist the users to the CSV-File.
     *
     * @throws IOException If an error while persisting the patients occurs.
     */
    public void persistPatients() throws IOException {
        this.patients.persist();
    }

    /**
     * Calls the method to persist the patients to the CSV-File.
     *
     * @throws IOException If an error while persisting the users occurs.
     */
    public void persistUsers() throws IOException {
        this.users.persist();
    }

    /**
     * Searches for the first occurrence, where the patient has for the passed property the passed value.
     *
     * @param property Name of property on the object to check the value on.
     * @param value    Value of the property to search the patient with.
     * @return The first matching patient.
     */
    public Patient searchPatient(String property, Object value) {
        return patients.find(property, value);
    }

    /**
     * Searches for all patients, where the passed property has the passed value.
     *
     * @param property Name of property on the object to check the value on.
     * @param value    Value of the property to search the patients with.
     * @return Array containing all matching patients.
     */
    public Patient[] searchPatients(String property, Object value) {
        Object[] foundPatients = patients.findAll(property, value);
        return Arrays.copyOf(foundPatients, foundPatients.length, Patient[].class);
    }
}
