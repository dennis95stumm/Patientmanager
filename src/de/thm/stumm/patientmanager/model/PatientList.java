package de.thm.stumm.patientmanager.model;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * Singleton that contains all existing patients in the system.
 *
 * @author Dennis Stumm
 */
public class PatientList extends List<Patient> {
    /**
     * Instance of the PatientList.
     */
    private static PatientList instance;

    /**
     * The highest id that a patient of this list has.
     */
    private int maxId;

    /**
     * Initializes the PatientList object.
     *
     * @throws MalformedCsvLineException If some of the lines in the CSV-File contains errors.
     * @throws IOException               If an error gets thrown while reading the CSV-File.
     */
    private PatientList() throws MalformedCsvLineException, IOException {
        super();
    }

    /**
     * Returns the instance of the singleton object and initializes this object if necessary.
     *
     * @return The instance of this singleton.
     * @throws MalformedCsvLineException If some of the lines in the CSV-File contains errors.
     * @throws IOException               If an error gets thrown while reading the CSV-File.
     */
    public static PatientList getInstance() throws MalformedCsvLineException, IOException {
        if (instance == null) {
            instance = new PatientList();
        }

        return instance;
    }

    /**
     * Adds a new patient to this list, that has the passed properties set.
     * <p>
     * The id of the patients gets managed automatically by this list class and mustn't be passed.
     *
     * @param firstName The first name of the new patient.
     * @param lastName  The last name of the new patient.
     * @param age       The age of the new patient.
     * @param icd       The icd of the new patient.
     */
    public void add(String firstName, String lastName, int age, String icd) {
        this.add(new Patient(this.maxId + 1, firstName, lastName, age, icd));
    }

    /**
     * Adds the passed patient to this list.
     *
     * @param patient Patient that should be added to this list.
     */
    @Override
    public void add(Patient patient) {
        super.add(patient);
        if (patient.getId() > this.maxId) {
            this.maxId = patient.getId();
        }
    }

    /**
     * Removes the passed patient from this list.
     *
     * @param patient Patient that should be removed from this list.
     */
    @Override
    public void remove(Patient patient) {
        super.remove(patient);
        if (patient.getId() == this.maxId) {
            this.maxId = 0;
            for (Patient currentPatient : this) {
                if (currentPatient.getId() > this.maxId) {
                    this.maxId = currentPatient.getId();
                }
            }
        }
    }

    /**
     * Adds a new patient object with the information parsed from the passed String in CSV-Format to this PatientList.
     *
     * @param csvLine The CSV-Formatted string to get the values for the new object from.
     * @throws MalformedCsvLineException If the passed csvLine contains errors.
     */
    @Override
    protected void add(String csvLine) throws MalformedCsvLineException {
        String[] values = csvLine.split(";", -1);

        if (values.length != 7) {
            throw new MalformedCsvLineException("Die Zeile (" + csvLine + ") enthält zu wenig bzw. zu viel spalten!");
        }

        try {
            int id = Integer.parseInt(values[0]);
            String firstName = values[1];
            String lastName = values[2];
            int age = Integer.parseInt(values[3]);
            Date admissionDate = values[4].equals("") ? null : DateFormat.getDateInstance().parse(values[4]);
            Date dischargeDate = values[5].equals("") ? null : DateFormat.getDateInstance().parse(values[5]);
            String icd = values[6];
            this.add(new Patient(id, firstName, lastName, age, icd, admissionDate, dischargeDate));
        } catch (ParseException exception) {
            throw new MalformedCsvLineException("Beim Analysieren eines Datums in der Zeile (" + csvLine + ") ist ein Fehler aufgetreten (" + exception.getLocalizedMessage() + ")!");
        } catch (NumberFormatException exception) {
            throw new MalformedCsvLineException("Beim Analysieren einer Zahl in der Zeile (" + csvLine + ") ist ein Fehler aufgetreten (" + exception.getLocalizedMessage() + "!!");
        }
    }

    /**
     * @return Path to the CSV-File where the patients should be persisted.
     */
    @Override
    protected Path getFilePath() {
        return Paths.get("./data/patients.csv");
    }

    /**
     * Returns a string containing the line for a CSV-File for the patient object at the passed index.
     *
     * @param index The index where to get the item, that should be persisted.
     * @return String in CSV-Format representing this patient object.
     */
    @Override
    protected String getCsvLine(int index) {
        Patient patient = this.get(index);
        String line = patient.getId() + ";";
        line += patient.getFirstName() + ";";
        line += patient.getLastName() + ";";
        line += patient.getAge() + ";";

        Date admissionDate = patient.getAdmissionDate();
        line += (admissionDate == null ? "" : DateFormat.getDateInstance().format(admissionDate)) + ";";

        Date dischargeDate = patient.getDischargeDate();
        line += (dischargeDate == null ? "" : DateFormat.getDateInstance().format(dischargeDate)) + ";";

        line += patient.getIcd() == null ? "" : patient.getIcd();

        return line;
    }
}
