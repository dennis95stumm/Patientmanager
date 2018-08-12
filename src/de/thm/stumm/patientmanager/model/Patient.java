package de.thm.stumm.patientmanager.model;

import java.text.DateFormat;
import java.util.Date;

/**
 * Class holding the information of a patient and providing necessary functionality.
 *
 * @author Dennis Stumm
 */
public class Patient {
    /**
     * The unique id of the patient.
     */
    private int id;

    /**
     * The first name of the patient.
     */
    private String firstName;

    /**
     * The last name of the patient.
     */
    private String lastName;

    /**
     * The age of the patient.
     */
    private int age;

    /**
     * The date of admission of the patient.
     */
    private Date admissionDate;

    /**
     * The date of discharge of the patient.
     */
    private Date dischargeDate;

    /**
     * The ICD of the patient.
     */
    private String icd;

    /**
     * Initializes the patient object by setting the passed values as the values of the object properties.
     *
     * @param id            Id of the patient.
     * @param firstName     First name of the patient.
     * @param lastName      Last name of the patient.
     * @param age           Age of the patient.
     * @param icd           ICD of the patient.
     * @param admissionDate Admission date of the patient.
     * @param dischargeDate Discharge date of the patient.
     */
    public Patient(int id, String firstName, String lastName, int age, String icd, Date admissionDate, Date dischargeDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.icd = icd;
        this.admissionDate = admissionDate;
        this.dischargeDate = dischargeDate;
    }

    /**
     * Initializes the patient object by setting the passed values as the values of the object properties.
     * <p>
     * The dischargeDate will be left empty and the admissionDate gets set to today.
     *
     * @param id        Id of the patient.
     * @param firstName First name of the patient.
     * @param lastName  Last name of the patient.
     * @param age       Age of the patient.
     * @param icd       ICD of the patient.
     */
    public Patient(int id, String firstName, String lastName, int age, String icd) {
        this(id, firstName, lastName, age, icd, new Date(), null);
    }

    /**
     * @return The admission date of the patient.
     */
    Date getAdmissionDate() {
        return admissionDate;
    }

    /**
     * @return The age of the patient.
     */
    int getAge() {
        return age;
    }

    /**
     * @return The discharge date of the patient.
     */
    public Date getDischargeDate() {
        return dischargeDate;
    }

    /**
     * Sets the discharge date of the patient to the passed value.
     *
     * @param dischargeDate Date of discharge to be set.
     */
    public void setDischargeDate(Date dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    /**
     * @return The first name of the patient.
     */
    String getFirstName() {
        return firstName;
    }

    /**
     * @return The id of the patient.
     */
    public int getId() {
        return id;
    }

    /**
     * @return The last name of the patinet.
     */
    String getLastName() {
        return this.lastName;
    }

    /**
     * @return The ICD of the patient.
     */
    public String getIcd() {
        return this.icd;
    }

    /**
     * Sets the ICD of the patient to the passed value.
     *
     * @param icd ICD to be set.
     */
    public void setIcd(String icd) {
        this.icd = icd;
    }

    /**
     * Returns a boolean value that indicates whether the passed object is equal to this patient object.
     * <p>
     * If the passed object is a instance of the class Patient the two patient objects gets compared. Otherwise
     * the object is guessed as not equal.
     *
     * @param obj The object to compare the patient object with.
     * @return Boolean value that indicates, whether the passed object is equal to this object or not.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Patient) {
            return this.equals((Patient) obj);
        }
        return false;
    }

    /**
     * @return String representation of the patient object.
     */
    @Override
    public String toString() {
        String result = "**************************************************\n";
        result += "ID:\t\t\t\t\t" + this.id + "\n";
        result += "Vorname:\t\t\t" + this.firstName + "\n";
        result += "Name:\t\t\t\t" + this.lastName + "\n";
        result += "Alter:\t\t\t\t" + this.age + "\n";
        result += "Aufnahmedatum:\t\t" + (this.admissionDate == null ? "-" : DateFormat.getDateInstance().format(this.admissionDate)) + "\n";
        result += "Entlassungsdatum:\t" + (this.dischargeDate == null ? "-" : DateFormat.getDateInstance().format(this.dischargeDate)) + "\n";
        result += "ICD:\t\t\t\t" + this.icd + "\n";
        result += "**************************************************";

        return result;
    }

    /**
     * Compares this patient object with the passed patient object by the unique id.
     * <p>
     * If the unique id is identical, than the two patient objects are equal.
     *
     * @param patient The patient object to compare this object with.
     * @return Boolean that indicates whether the two patient objects are equal.
     */
    private boolean equals(Patient patient) {
        return this.id == patient.getId();
    }
}
