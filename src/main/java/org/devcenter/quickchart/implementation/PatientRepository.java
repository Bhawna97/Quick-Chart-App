package org.devcenter.quickchart.implementation;

import org.devcenter.quickchart.model.ColumnName;
import org.devcenter.quickchart.model.DatabaseConnection;
import org.devcenter.quickchart.model.Patient;
import org.devcenter.quickchart.model.SortOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Class to perform the actions for the new Patient or existing Patients
 */
public class PatientRepository {
    public Logger logger = Logger.getAnonymousLogger();
    Connection connectDB;
    List<Patient> patientList;

    public PatientRepository(DatabaseConnection databaseConnection) {
        this.connectDB = databaseConnection.getConnection();
    }

    /**
     * Function to add new patient details into DB
     * @param patient the object of Patient class to access the properties
     * @return 1 if new patient details gets entered successfully in DB else if any sql exception occurs return 0
     */
    public boolean addPatientIntoDB(Patient patient){
        try{
            PreparedStatement patientUpdate = connectDB.prepareStatement("INSERT INTO patient_list(firstname, lastname, dateofbirth, gender) VALUES (?,?,?,?)");
            patientUpdate.setString(1, patient.getFirstname());
            patientUpdate.setString(2, patient.getLastname());
            patientUpdate.setString(3, patient.getDateOfBirth());
            patientUpdate.setString(4, patient.getGender());
            int executeUpdate = patientUpdate.executeUpdate();
            patientUpdate.close();
            return executeUpdate == 1;
        }catch (SQLException e){
            logger.log(Level.INFO, "An SQLException occurred while adding the patient details.", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public String getAge(LocalDate dob) {
        String age;
        LocalDate curDate = LocalDate.now();
        Period period = Period.between(dob, curDate);
        if(period.getYears()!=0 && period.getMonths()!=0 && period.getDays()!=0){
            age = period.getYears()+" yr "+period.getMonths()+" mo "+period.getDays()+" d";
        } else if (period.getYears()==0 && period.getMonths()!=0 && period.getDays()!=0){
            age = period.getMonths()+" mo "+period.getDays()+" d";
        } else if (period.getYears()!=0 && period.getMonths()==0 && period.getDays()!=0){
            age = period.getYears()+" yr "+period.getDays()+" d";
        } else if (period.getYears() != 0 && period.getMonths() != 0){
            age = period.getYears()+" yr "+period.getMonths()+" mo";
        } else if (period.getYears() != 0){
            age = period.getYears()+" yr";
        } else if (period.getMonths() != 0){
            age = period.getMonths()+" mo";
        } else if (period.getDays() != 0){
            age = period.getDays()+" d";
        } else {
            age = "1 d";
        }
        return age;
    }

    /**
     * Function to get patients List from DB
     * @return the list of Patients with all the details
     */
    public List<Patient> getPatientList(){
        try {
            patientList = new ArrayList<>();
            PreparedStatement patientListQuery = connectDB.prepareStatement("Select * from patient_list");
            ResultSet queryResult = patientListQuery.executeQuery();
            while (queryResult.next()) {
                Patient patient = new Patient();
                patient.setPatientId(queryResult.getInt(1));
                patient.setPatientName(queryResult.getString(2)+" "+queryResult.getString(3));
                LocalDate dob = LocalDate.parse(queryResult.getString(4));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                patient.setDateOfBirth(formatter.format(dob));
                patient.setAge(getAge(dob));
                patient.setGender(queryResult.getString(5));
                patientList.add(patient);
                if (queryResult.isLast()){
                    break;
                }
            }
            patientListQuery.close();
            queryResult.close();
        }catch (SQLException e){
            logger.log(Level.INFO, "An SQLException occurred while fetching the patient list.", e.getMessage());
            e.printStackTrace();
        }catch (DateTimeException e){
            logger.log(Level.INFO, "An Exception occurred while parsing or formatting the date of birth.", e.getMessage());
            e.printStackTrace();
        }
        return patientList;
    }

    /**
     * Function to get sorted patients List from DB
     * @param columnName string to identify by which value want to sort the list
     * @param order chart to identify in which order want to sort the list
     * @return the sorted patient list
     */
    public List<Patient> getSortedPatientList(ColumnName columnName, SortOrder order) {
        try {
            patientList = getPatientList();
            switch (columnName) {
                case patientId:
                    if (order == SortOrder.ascending) {
                        patientList.sort(Patient.idComparatorA);
                    } else if (order == SortOrder.descending) {
                        patientList.sort(Patient.idComparatorD);
                    }
                    break;
                case age:
                    if (order == SortOrder.ascending) {
                        patientList.sort(Patient.dobComparatorD);
                    } else if (order == SortOrder.descending) {
                        patientList.sort(Patient.dobComparatorA);
                    }
                    break;
                case patientName:
                    if (order == SortOrder.ascending) {
                        patientList.sort(Patient.nameComparatorA);
                    } else if (order == SortOrder.descending) {
                        patientList.sort(Patient.nameComparatorD);
                    }
                    break;
            }
            return patientList;
        } catch (Exception e) {
            logger.log(Level.INFO, "An Exception occurred while sorting the patient list.", e.getMessage());
            e.printStackTrace();
        }
        return patientList;
    }
}
