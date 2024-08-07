package org.devcenter.quickchart.implementation;

import org.devcenter.quickchart.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class PatientChartRepositoryTest {

    @Mock
    private DatabaseConnection databaseConnection = Mockito.mock(DatabaseConnection.class);

    @Mock
    private Connection mockConnection = Mockito.mock(Connection.class);

    @Mock
    private PreparedStatement mockStatement = Mockito.mock(PreparedStatement.class);

    @Mock
    private ResultSet resultSet = Mockito.mock(ResultSet.class);

    @Mock
    private PatientChartRepository patientChartRepository = Mockito.mock(PatientChartRepository.class);

    PatientChartRepository patientChartRepo;
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
        patientChartRepo = new PatientChartRepository(databaseConnection);
    }

    @Test
    void addPatientChartIntoDB() throws SQLException {
        patientChart = new PatientChart();
        patientChart.setPatientId(3);
        patientChart.setChartName("Vital Chart");
        patientChart.setDateTime("27/12/2021, 10:49:51 am");
        patientChart.setBloodPressureSP("117");
        patientChart.setBloodPressureDP("78");
        patientChart.setRespiration("16");
        patientChart.setPulse("72");
        patientChart.setTemperature("98.6");
        assertTrue(patientChartRepo.addPatientChartIntoDB(patientChart));
        when(mockStatement.executeUpdate()).thenReturn(0);
        assertFalse(patientChartRepo.addPatientChartIntoDB(patientChart));
    }

    @Test
    void getPatientChartList() throws SQLException {
        List<PatientChart> patientChartList = new ArrayList<>();
        List<PatientChart> emptyPatientChartList = new ArrayList<>();
        patientChart = new PatientChart();
        patientChart.setPatientId(1);
        patientChart.setChartId(1);
        patientChart.setChartName("Vital Chart");
        patientChart.setDateTime("27/12/2021, 10:49:51 am");
        patientChartList.add(patientChart);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(anyInt())).thenReturn(1);
        when(resultSet.getString(2)).thenReturn("Vital Chart");
        when(resultSet.getString(3)).thenReturn("27/12/2021, 10:49:51 am");

        assertFalse(patientChartRepo.getPatientChartList(anyInt()).isEmpty());
        assertEquals(patientChartList.size(),patientChartRepo.getPatientChartList(anyInt()).size());
        when(resultSet.next()).thenReturn(false);
        assertEquals(patientChartRepo.getPatientChartList(anyInt()), emptyPatientChartList);
    }

    @Test
    void getPatientChart() throws SQLException {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(anyInt())).thenReturn(1);
        when(resultSet.getString(anyInt())).thenReturn("Dummy");
        assertFalse(patientChartRepo.getPatientChart(1).isEmpty());
        when(resultSet.next()).thenReturn(false);
        assertTrue(patientChartRepository.getPatientChart(1).isEmpty());
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
        assertTrue(patientChartRepo.updatePatientChart(patientChart));
        when(mockStatement.executeUpdate()).thenReturn(0);
        assertFalse(patientChartRepo.updatePatientChart(patientChart));
    }

    @Test
    void getSortedChartList() throws SQLException {
        List<PatientChart> patientChartList = new ArrayList<>();
        List<PatientChart> emptyPatientChartList = new ArrayList<>();
        patientChart = new PatientChart();
        patientChart.setPatientId(1);
        patientChart.setChartId(1);
        patientChart.setChartName("Vital Chart");
        patientChart.setDateTime("27/12/2021, 10:49:51 am");
        patientChartList.add(patientChart);
        patientChart = new PatientChart();
        patientChart.setPatientId(1);
        patientChart.setChartId(2);
        patientChart.setChartName("Vital Chart");
        patientChart.setDateTime("02/01/2022, 01:33:45 am");
        patientChartList.add(patientChart);

        when(patientChartRepository.getSortedChartList(1,ColumnName.dateTime,SortOrder.ascending)).thenReturn(patientChartList);
        assertEquals(patientChartRepository.getSortedChartList(1,ColumnName.dateTime,SortOrder.ascending), patientChartList);
        when(resultSet.next()).thenReturn(false);
        assertEquals(patientChartRepo.getSortedChartList(1,ColumnName.dateTime,SortOrder.ascending), emptyPatientChartList);
    }
}