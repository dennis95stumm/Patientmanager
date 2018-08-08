package de.thm.stumm.patientmanager.data;

import java.util.Date;

public class Patient {
    private String id;
    private String firstName;
    private String lastName;
    private int age;
    private Date admissionDate;
    private Date dischargeDate;
    private String ICD;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
