package org.devcenter.quickchart.implementation;

import org.devcenter.quickchart.model.ColumnName;
import org.devcenter.quickchart.model.DatabaseConnection;
import org.devcenter.quickchart.model.Patient;
import org.devcenter.quickchart.model.SortOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class PatientRepositoryTest {
    @Mock
    private DatabaseConnection databaseConnection = Mockito.mock(DatabaseConnection.class);

    @Mock
    private Connection mockConnection = Mockito.mock(Connection.class);

    @Mock
    private PreparedStatement mockStatement = Mockito.mock(PreparedStatement.class);

    @Mock
    private ResultSet resultSet = Mockito.mock(ResultSet.class);

    PatientRepository patientRepo;
    Patient patient;

    @BeforeEach
    public void setUp() throws Exception {
        assertNotNull(databaseConnection);
        when(databaseConnection.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(resultSet);
        when(mockStatement.executeUpdate()).thenReturn(1);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.isLast()).thenReturn(true);
        patientRepo = new PatientRepository(databaseConnection);
    }

    @Test
    void addPatientIntoDB() throws SQLException {
        patient = new Patient();
        patient.setFirstname("Pankaj");
        patient.setLastname("Rawat");
        patient.setDateOfBirth("21-03-1986");
        patient.setGender("Male");
        assertTrue(patientRepo.addPatientIntoDB(patient));
        when(mockStatement.executeUpdate()).thenReturn(0);
        assertFalse(patientRepo.addPatientIntoDB(patient));
    }

    @Test
    void getPatientList() throws SQLException {
        List<Patient> patientList = new ArrayList<>();
        List<Patient> emptyPatientList = new ArrayList<>();
        patient = new Patient();
        patient.setPatientId(1);
        patient.setPatientName("Pankaj Rawat");
        LocalDate dob = LocalDate.parse("1994-05-19");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        patient.setDateOfBirth(formatter.format(dob));
        patient.setAge(patientRepo.getAge(dob));
        patient.setGender("Male");
        patientList.add(patient);

        when(resultSet.getInt(anyInt())).thenReturn(1);
        when(resultSet.getString(2)).thenReturn("Pankaj");
        when(resultSet.getString(3)).thenReturn("Rawat");
        when(resultSet.getString(4)).thenReturn("1994-05-19");
        when(resultSet.getString(5)).thenReturn("Male");

        assertFalse(patientRepo.getPatientList().isEmpty());
        assertEquals(patientList.size(),patientRepo.getPatientList().size());
        when(resultSet.next()).thenReturn(false);
        assertEquals(patientRepo.getPatientList(), emptyPatientList);
    }

    @Test
    void getSortedPatientList() throws SQLException {
        List<Patient> patientList = new ArrayList<>();
        List<Patient> emptyPatientList = new ArrayList<>();
        patient = new Patient();
        patient.setPatientId(1);
        patient.setPatientName("Pankaj Rawat");
        LocalDate dob = LocalDate.parse("1994-05-19");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        patient.setDateOfBirth(formatter.format(dob));
        patient.setAge(patientRepo.getAge(dob));
        patient.setGender("Male");
        patientList.add(patient);

        when(resultSet.getInt(anyInt())).thenReturn(1);
        when(resultSet.getString(2)).thenReturn("Pankaj");
        when(resultSet.getString(3)).thenReturn("Rawat");
        when(resultSet.getString(4)).thenReturn("1994-05-19");
        when(resultSet.getString(5)).thenReturn("Male");

        patientList.sort(Patient.idComparatorD);
        assertFalse(patientRepo.getSortedPatientList(ColumnName.patientId, SortOrder.descending).isEmpty());
        assertEquals(patientList.size(),patientRepo.getSortedPatientList(ColumnName.patientId, SortOrder.descending).size());
        when(resultSet.next()).thenReturn(false);
        assertEquals(patientRepo.getSortedPatientList(ColumnName.patientId, SortOrder.descending), emptyPatientList);
    }
}