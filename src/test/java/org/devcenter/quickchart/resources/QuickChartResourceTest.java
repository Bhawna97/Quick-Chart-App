package org.devcenter.quickchart.resources;

import org.devcenter.quickchart.implementation.PatientChartRepository;
import org.devcenter.quickchart.implementation.PatientRepository;
import org.devcenter.quickchart.implementation.UserRepository;
import org.devcenter.quickchart.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;

@RunWith(PowerMockRunner.class)
class QuickChartResourceTest {

    @Mock
    private DatabaseConnection databaseConnection = Mockito.mock(DatabaseConnection.class);

    @Mock
    private Connection mockConnection = Mockito.mock(Connection.class);

    @Mock
    private PreparedStatement mockStatement = Mockito.mock(PreparedStatement.class);

    @Mock
    private ResultSet resultSet = Mockito.mock(ResultSet.class);

    UserRepository userRepo;
    PatientRepository patientRepo;
    PatientChartRepository patientChartRepo;
    QuickChartResource quickChartResource;
    User user;
    Patient patient;
    PatientChart patientChart;

    @BeforeEach
    public void setUp() throws Exception {
        assertNotNull(databaseConnection);
        when(databaseConnection.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(resultSet);
        when(mockStatement.executeUpdate()).thenReturn(1);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.isLast()).thenReturn(true);

        userRepo = new UserRepository(databaseConnection);
        patientRepo = new PatientRepository(databaseConnection);
        patientChartRepo = new PatientChartRepository(databaseConnection);
        quickChartResource = new QuickChartResource(userRepo, patientRepo, patientChartRepo);
    }

    @Test
    void addUser() throws SQLException {
        user = new User();
        user.setFirstname("Bhawna");
        user.setLastname("Badlani");
        user.setUsername("Bhawna56");
        user.setPassword("Bhawna@56");
        assertEquals(quickChartResource.addUser(user).getStatus(), 200);
        when(mockStatement.executeUpdate()).thenReturn(0);
        assertEquals(quickChartResource.addUser(user).getStatus(), 304);
    }

    @Test
    void UsernameTaken() throws SQLException {
        user = new User();
        user.setFirstname("Bhawna");
        user.setLastname("Badlani");
        user.setUsername("Bhawna56");
        user.setPassword("Bhawna@56");
        when(mockStatement.executeUpdate()).thenThrow(SQLIntegrityConstraintViolationException.class);
        assertEquals(quickChartResource.addUser(user).getStatus(), 406);
    }

    @Test
    void validateUser() throws Exception {
        user = new User();
        user.setUsername("Bhawna56");
        user.setPassword("Bhawna@56");
        when(resultSet.getInt(anyInt())).thenReturn(1);
        assertEquals(quickChartResource.validateUser(user).getStatus(), 200);
        when(resultSet.getInt(anyInt())).thenReturn(0);
        assertEquals(quickChartResource.validateUser(user).getStatus(), 401);
    }

    @Test
    void checkUsername() throws Exception {
        user = new User();
        user.setFirstname("Bhawna");
        user.setLastname("Badlani");
        when(resultSet.getInt(anyInt())).thenReturn(0);
        assertEquals(quickChartResource.checkUsername(user).getStatus(), 200);
    }

    @Test
    void addPatient() throws SQLException {
        patient = new Patient();
        patient.setFirstname("Pankaj");
        patient.setLastname("Rawat");
        patient.setDateOfBirth("21-03-1986");
        patient.setGender("Male");
        assertEquals(quickChartResource.addPatient(patient).getStatus(), 200);
        when(mockStatement.executeUpdate()).thenReturn(0);
        assertEquals(quickChartResource.addPatient(patient).getStatus(), 304);
    }

    @Test
    void getPatientList() throws Exception {
        when(resultSet.getInt(anyInt())).thenReturn(1);
        when(resultSet.getString(2)).thenReturn("Pankaj");
        when(resultSet.getString(3)).thenReturn("Rawat");
        when(resultSet.getString(4)).thenReturn("1986-03-21");
        when(resultSet.getString(5)).thenReturn("Male");
        assertEquals(quickChartResource.getPatientList().getStatus(), 200);
        when(resultSet.next()).thenReturn(false);
        assertEquals(quickChartResource.getPatientList().getStatus(), 204);
    }

    @Test
    void getSortedPatientList() throws Exception {
        when(resultSet.getInt(anyInt())).thenReturn(1);
        when(resultSet.getString(2)).thenReturn("Pankaj");
        when(resultSet.getString(3)).thenReturn("Rawat");
        when(resultSet.getString(4)).thenReturn("1986-03-21");
        when(resultSet.getString(5)).thenReturn("Male");
        assertEquals(quickChartResource.getSortedPatientList(ColumnName.patientId,SortOrder.ascending).getStatus(), 200);
        when(resultSet.next()).thenReturn(false);
        assertEquals(quickChartResource.getSortedPatientList(ColumnName.patientId, SortOrder.ascending).getStatus(), 204);
    }

    @Test
    void addPatientChart() throws SQLException {
        patientChart = new PatientChart();
        patientChart.setPatientId(3);
        patientChart.setChartName("Vital Chart");
        patientChart.setDateTime("27/12/2021, 10:49:51 am");
        patientChart.setBloodPressureSP("117");
        patientChart.setBloodPressureDP("78");
        patientChart.setRespiration("16");
        patientChart.setPulse("72");
        patientChart.setTemperature("98.6");
        assertEquals(quickChartResource.addPatientChart(patientChart).getStatus(), 200);
        when(mockStatement.executeUpdate()).thenReturn(0);
        assertEquals(quickChartResource.addPatientChart(patientChart).getStatus(), 304);
    }

    @Test
    void getPatientChartList() throws Exception {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(anyInt())).thenReturn(1);
        when(resultSet.getString(2)).thenReturn("Vital Chart");
        when(resultSet.getString(3)).thenReturn("27/12/2021, 10:49:51 am");
        assertEquals(quickChartResource.getPatientChartList(anyInt()).getStatus(), 200);
        when(resultSet.next()).thenReturn(false);
        assertEquals(quickChartResource.getPatientChartList(anyInt()).getStatus(), 204);
    }

    @Test
    void getPatientChart() throws Exception {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(anyInt())).thenReturn(1);
        when(resultSet.getString(anyInt())).thenReturn("Dummy");
        assertEquals(quickChartResource.getPatientChart(1).getStatus(), 200);
        when(resultSet.next()).thenReturn(false);
        assertEquals(quickChartResource.getPatientChart(1).getStatus(), 204);
    }

    @Test
    void updatePatientChart() throws SQLException {
        patientChart = new PatientChart();
        patientChart.setPatientId(3);
        patientChart.setChartId(5);
        patientChart.setChartName("Vital Chart");
        patientChart.setDateTime("27/12/2021, 10:49:51 am");
        patientChart.setBloodPressureSP("117");
        patientChart.setBloodPressureDP("78");
        patientChart.setRespiration("16");
        patientChart.setPulse("72");
        patientChart.setTemperature("98.6");
        assertEquals(quickChartResource.updatePatientChart(patientChart).getStatus(), 200);
        when(mockStatement.executeUpdate()).thenReturn(0);
        assertEquals(quickChartResource.updatePatientChart(patientChart).getStatus(), 304);
    }

    @Test
    void getSortedChartList() throws Exception {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(anyInt())).thenReturn(1);
        when(resultSet.getString(2)).thenReturn("Vital Chart");
        when(resultSet.getString(3)).thenReturn("27/12/2021, 10:49:51 am");
        assertEquals(quickChartResource.getSortedChartList(anyInt(),ColumnName.dateTime,SortOrder.ascending).getStatus(), 200);
        when(resultSet.next()).thenReturn(false);
        assertEquals(quickChartResource.getSortedChartList(anyInt(), ColumnName.dateTime, SortOrder.ascending).getStatus(), 204);
    }
}