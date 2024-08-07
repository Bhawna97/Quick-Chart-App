package org.devcenter.quickchart.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;

/**
 *Class to set and get the properties of Patient
 */
public class Patient {
    private int patientId;
    private String firstname;
    private String lastname;
    private String patientName;
    private String dateOfBirth;
    private String age;
    private String gender;

    public Patient(){}

    public int getPatientId() {
        return patientId;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {return lastname;}

    public String getPatientName() {return patientName;}

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAge(LocalDate dob) {
        return age;
    }

    public String getGender() {return gender;}

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {this.lastname = lastname;}

    public void setPatientName(String patientName) {this.patientName = patientName;}

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setGender(String gender) {this.gender = gender;}
    /**
     * Comparator for sorting the patient list by Name in ascending and descending order
     */
    public static Comparator<Patient> nameComparatorA = (patient1, patient2) -> {
        String patientName1 = patient1.getPatientName().toUpperCase();
        String patientName2 = patient2.getPatientName().toUpperCase();
        return patientName1.compareTo(
                patientName2);
    };
    public static Comparator<Patient> nameComparatorD = (patient1, patient2) -> {
        String patientName1 = patient1.getPatientName().toUpperCase();
        String patientName2 = patient2.getPatientName().toUpperCase();
        return patientName2.compareTo(
                patientName1);
    };
    /**
     * Comparator for sorting the patient list by Age in ascending and descending order
     */
    public static Comparator<Patient> dobComparatorA = (patient1, patient2) -> {
        String dob1 = patient1.getDateOfBirth();
        String dob2 = patient2.getDateOfBirth();
        Date dateDOB1 = null;
        Date dateDOB2 = null;
        try {
            dateDOB1 = new SimpleDateFormat("dd-MM-yyyy").parse(dob1);
            dateDOB2 = new SimpleDateFormat("dd-MM-yyyy").parse(dob2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert dateDOB1 != null;
        return dateDOB1.compareTo(dateDOB2);
    };
    public static Comparator<Patient> dobComparatorD = (patient1, patient2) -> {
        String dob1 = patient1.getDateOfBirth();
        String dob2 = patient2.getDateOfBirth();
        Date dateDOB1 = null;
        Date dateDOB2 = null;
        try {
            dateDOB1 = new SimpleDateFormat("dd-MM-yyyy").parse(dob1);
            dateDOB2 = new SimpleDateFormat("dd-MM-yyyy").parse(dob2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert dateDOB2 != null;
        return dateDOB2.compareTo(dateDOB1);
    };
    /**
     * Comparator for sorting the patient list by id in ascending and descending order
     */
    public static Comparator<Patient> idComparatorA = (patient1, patient2) -> {
        int patientId1 = patient1.getPatientId();
        int patientId2 = patient2.getPatientId();
        return patientId1 - patientId2;
    };
    public static Comparator<Patient> idComparatorD = (patient1, patient2) -> {
        int patientId1 = patient1.getPatientId();
        int patientId2 = patient2.getPatientId();
        return patientId2 - patientId1;
    };

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
