package org.devcenter.quickchart.implementation;

import org.devcenter.quickchart.model.ColumnName;
import org.devcenter.quickchart.model.DatabaseConnection;
import org.devcenter.quickchart.model.PatientChart;
import org.devcenter.quickchart.model.SortOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PatientChartRepository {
    public Logger logger = Logger.getAnonymousLogger();
    Connection connectDB;
    List<PatientChart> patientChartList;
    List<PatientChart> patientChartData;

    public PatientChartRepository(DatabaseConnection databaseConnection) {
        this.connectDB = databaseConnection.getConnection();
    }

    /**
     * function to add patient chart details in DB
     * @param patientChart object of PatientChart class to access the properties of patient chart
     * @return 1 if patient chart details gets entered successfully
     */
    public boolean addPatientChartIntoDB(PatientChart patientChart){
        try{
            PreparedStatement patientChartUpdate = connectDB.prepareStatement("INSERT INTO patient_charted_data(chartname,datetime,cpatientid) VALUES (?,?,?)");
            patientChartUpdate.setString(1, patientChart.getChartName());
            patientChartUpdate.setString(2, patientChart.getDateTime());
            patientChartUpdate.setInt(3, patientChart.getPatientId());
            int executeChartUpdate = patientChartUpdate.executeUpdate();

            PreparedStatement chartIdQuery = connectDB.prepareStatement("select chartid from patient_charted_data where datetime = ?");
            chartIdQuery.setString(1, patientChart.getDateTime());
            ResultSet queryResult = chartIdQuery.executeQuery();
            while (queryResult.next()){
                patientChart.setChartId(queryResult.getInt(1));
                if (queryResult.isLast()){
                    break;
                }
            }

            PreparedStatement patientChartValues = connectDB.prepareStatement("INSERT INTO chart_values(chartvalue, celementid, chartid) VALUES (?,?,?)");
            patientChartValues.setString(1,patientChart.getBloodPressureSP());
            patientChartValues.setInt(2,1);
            patientChartValues.setInt(3,patientChart.getChartId());
            int executeValueUpdate = patientChartValues.executeUpdate();

            patientChartValues.setString(1,patientChart.getBloodPressureDP());
            patientChartValues.setInt(2,2);
            patientChartValues.setInt(3,patientChart.getChartId());
            executeValueUpdate += patientChartValues.executeUpdate();

            patientChartValues.setString(1,patientChart.getRespiration());
            patientChartValues.setInt(2,3);
            patientChartValues.setInt(3,patientChart.getChartId());
            executeValueUpdate += patientChartValues.executeUpdate();

            patientChartValues.setString(1,patientChart.getPulse());
            patientChartValues.setInt(2,4);
            patientChartValues.setInt(3,patientChart.getChartId());
            executeValueUpdate += patientChartValues.executeUpdate();

            patientChartValues.setString(1, patientChart.getTemperature());
            patientChartValues.setInt(2,5);
            patientChartValues.setInt(3,patientChart.getChartId());
            executeValueUpdate += patientChartValues.executeUpdate();

            patientChartUpdate.close();
            queryResult.close();
            patientChartValues.close();

            return executeChartUpdate == 1 && executeValueUpdate == 5;
        } catch (SQLException e){
            logger.log(Level.INFO, "An SQLException occurred while adding the patient chart details.", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Function to get patient chart list from DB
     * @param patientId of a particular patient for which want to load the chart list
     * @return the list of chart for a particular patient
     */
    public List<PatientChart> getPatientChartList(int patientId){
        try {
            patientChartList = new ArrayList<>();
            PreparedStatement patientChartListQuery = connectDB.prepareStatement("select * from patient_charted_data where cpatientid = ?");
            patientChartListQuery.setInt(1,patientId);
            ResultSet queryResult = patientChartListQuery.executeQuery();
            while (queryResult.next()) {
                PatientChart patientChart = new PatientChart();
                patientChart.setChartId(queryResult.getInt(1));
                patientChart.setChartName(queryResult.getString(2));
                patientChart.setDateTime(queryResult.getString(3));
                patientChart.setPatientId(queryResult.getInt(4));
                patientChartList.add(patientChart);
                if (queryResult.isLast()){
                    break;
                }
            }
            patientChartListQuery.close();
            queryResult.close();
        }catch (Exception e){
            logger.log(Level.INFO, "An SQLException occurred while fetching the patient chart list.", e.getMessage());
            e.printStackTrace();
        }
        return patientChartList;
    }

    /**
     * Function to get patient chart values from DB
     * @param chartId of a particular chart for which want to get the values
     * @return the values of a particular chart
     */
    public List<PatientChart> getPatientChart(int chartId){
        try{
            patientChartData = new ArrayList<>();
            PatientChart patientChart = new PatientChart();
            PreparedStatement patientChartQuery = connectDB.prepareStatement("SELECT * FROM patient_charted_data WHERE chartid = ?");
            patientChartQuery.setInt(1,chartId);
            ResultSet chartQueryResult = patientChartQuery.executeQuery();
            while (chartQueryResult.next()) {
                patientChart.setPatientId(chartQueryResult.getInt(4));
                patientChart.setChartId(chartQueryResult.getInt(1));
                patientChart.setChartName(chartQueryResult.getString(2));
                patientChart.setDateTime(chartQueryResult.getString(3));
                if (chartQueryResult.isLast()){
                    break;
                }
            }
            PreparedStatement patientChartValueQuery = connectDB.prepareStatement("SELECT * FROM chart_values WHERE chartid = ?");
            patientChartValueQuery.setInt(1,chartId);
            ResultSet valueQueryResult = patientChartValueQuery.executeQuery();
            while (valueQueryResult.next()) {
                if (valueQueryResult.getInt(2) == 1) {
                    patientChart.setBloodPressureSP(valueQueryResult.getString(1));
                } else if (valueQueryResult.getInt(2) == 2) {
                    patientChart.setBloodPressureDP(valueQueryResult.getString(1));
                } else if (valueQueryResult.getInt(2) == 3) {
                    patientChart.setRespiration(valueQueryResult.getString(1));
                } else if (valueQueryResult.getInt(2) == 4) {
                    patientChart.setPulse(valueQueryResult.getString(1));
                } else if (valueQueryResult.getInt(2) == 5) {
                    patientChart.setTemperature(valueQueryResult.getString(1));
                }
                if (valueQueryResult.isLast()){
                    break;
                }
            }
            PreparedStatement chartValueUnitQuery = connectDB.prepareStatement("select * from chart_elements");
            ResultSet unitQueryResult = chartValueUnitQuery.executeQuery();
            while (unitQueryResult.next()) {
                if (unitQueryResult.getInt(1) == 1) {
                    patientChart.setSystolicPressureUnit(unitQueryResult.getString(3));
                } else if (unitQueryResult.getInt(1) == 2) {
                    patientChart.setDiastolicPressureUnit(unitQueryResult.getString(3));
                } else if (unitQueryResult.getInt(1) == 3) {
                    patientChart.setRespirationRateUnit(unitQueryResult.getString(3));
                } else if (unitQueryResult.getInt(1) == 4) {
                    patientChart.setPulseRateUnit(unitQueryResult.getString(3));
                } else if (unitQueryResult.getInt(1) == 5) {
                    patientChart.setTemperatureUnit(unitQueryResult.getString(3));
                }
                if (unitQueryResult.isLast()){
                    break;
                }
            }
            if(patientChart.getChartId() == chartId){
                patientChartData.add(patientChart);
            }
            patientChartQuery.close();
            chartQueryResult.close();
            patientChartValueQuery.close();
            valueQueryResult.close();
            chartValueUnitQuery.close();
            unitQueryResult.close();
        } catch (SQLException e){
            logger.log(Level.INFO, "An SQLException occurred while fetching the patient chart details.", e.getMessage());
            e.printStackTrace();
            e.getCause();
        }
        return patientChartData;
    }

    /**
     * Function to update patient chart values
     * @param patientChart object of PatientChart class to access the properties from request
     * @return 1 if patient chart values get updated successfully
     */
    public boolean updatePatientChart(PatientChart patientChart) {
        try{
            PreparedStatement patientChartUpdate = connectDB.prepareStatement("UPDATE patient_charted_data SET datetime = ? WHERE chartid = ? and cpatientid = ?");
            patientChartUpdate.setString(1, patientChart.getDateTime());
            patientChartUpdate.setInt(2, patientChart.getChartId());
            patientChartUpdate.setInt(3, patientChart.getPatientId());
            int executeChartUpdate = patientChartUpdate.executeUpdate();

            PreparedStatement patientChartValues = connectDB.prepareStatement("UPDATE chart_values SET chartvalue = ? WHERE celementid = ? and chartid = ?");
            patientChartValues.setString(1,patientChart.getBloodPressureSP());
            patientChartValues.setInt(2,1);
            patientChartValues.setInt(3,patientChart.getChartId());
            int executeValueUpdate = patientChartValues.executeUpdate();

            patientChartValues.setString(1,patientChart.getBloodPressureDP());
            patientChartValues.setInt(2,2);
            patientChartValues.setInt(3,patientChart.getChartId());
            executeValueUpdate += patientChartValues.executeUpdate();

            patientChartValues.setString(1,patientChart.getRespiration());
            patientChartValues.setInt(2,3);
            patientChartValues.setInt(3,patientChart.getChartId());
            executeValueUpdate += patientChartValues.executeUpdate();

            patientChartValues.setString(1,patientChart.getPulse());
            patientChartValues.setInt(2,4);
            patientChartValues.setInt(3,patientChart.getChartId());
            executeValueUpdate += patientChartValues.executeUpdate();

            patientChartValues.setString(1,patientChart.getTemperature());
            patientChartValues.setInt(2,5);
            patientChartValues.setInt(3,patientChart.getChartId());
            executeValueUpdate += patientChartValues.executeUpdate();

            return executeChartUpdate == 1 && executeValueUpdate == 5;
        } catch (SQLException e){
            logger.log(Level.INFO, "An SQLException occurred while updating the patient chart details.", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Function to get sorted patients List from DB
     * @param columnName string to identify by which value want to sort the list
     * @param order chart to identify in which order want to sort the list
     * @return the sorted patient list
     */
    public List<PatientChart> getSortedChartList(int patientId, ColumnName columnName, SortOrder order) {
        try {
            patientChartList = getPatientChartList(patientId);
            if (columnName == ColumnName.dateTime) {
                if (order == SortOrder.ascending) {
                    patientChartList.sort(PatientChart.datetimeComparatorA);
                } else if (order == SortOrder.descending) {
                    patientChartList.sort(PatientChart.datetimeComparatorD);
                }
            }
            return patientChartList;
        } catch (Exception e) {
            logger.log(Level.INFO, "An Exception occurred while sorting the patient chart list.", e.getMessage());
            e.printStackTrace();
        }
        return patientChartList;
    }
}
