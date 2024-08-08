package org.devcenter.quickchart.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class PatientChart {
    private int patientId;
    private int chartId;
    private String chartName;
    private String dateTime;
    private String bloodPressureSP;
    private String systolicPressureUnit;
    private String bloodPressureDP;
    private String diastolicPressureUnit;
    private String respiration;
    private String respirationRateUnit;
    private String pulse;
    private String pulseRateUnit;
    private String temperature;
    private String temperatureUnit;


    public int getPatientId() {return patientId;}

    public int getChartId() {return chartId;}

    public String getChartName() {return chartName;}

    public String getRespiration() {return respiration;}

    public String getRespirationRateUnit() {return respirationRateUnit;}

    public String getPulse() {return pulse;}

    public String getPulseRateUnit() {return pulseRateUnit;}

    public String getTemperature() {return temperature;}

    public String getTemperatureUnit() {return temperatureUnit;}

    public String getDateTime() {return dateTime;}

    public String getBloodPressureSP() {return bloodPressureSP;}

    public String getSystolicPressureUnit() {return systolicPressureUnit;}

    public String getBloodPressureDP() {return bloodPressureDP;}

    public String getDiastolicPressureUnit() {return diastolicPressureUnit;}

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public void setChartId(int chartId) {
        this.chartId = chartId;
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

    public void setRespiration(String respiration) {this.respiration = respiration;}

    public void setRespirationRateUnit(String respirationRateUnit) {
        this.respirationRateUnit = respirationRateUnit;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    public void setPulseRateUnit(String pulseRateUnit) {
        this.pulseRateUnit = pulseRateUnit;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setTemperatureUnit(String temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setBloodPressureSP(String bloodPressureSP) {this.bloodPressureSP = bloodPressureSP;}

    public void setSystolicPressureUnit(String systolicPressureUnit) {
        this.systolicPressureUnit = systolicPressureUnit;
    }

    public void setBloodPressureDP(String bloodPressureDP) {this.bloodPressureDP = bloodPressureDP;}

    public void setDiastolicPressureUnit(String diastolicPressureUnit) {
        this.diastolicPressureUnit = diastolicPressureUnit;
    }

    /**
     * Comparator for sorting the patient chart list by datetime in ascending and descending order
     */
    public static Comparator<PatientChart> datetimeComparatorA = (patientChart1, patientChart2) -> {
        LocalDateTime dateTime1;
        LocalDateTime dateTime2;
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy, hh:mm:ss a");
        String datetime1 = patientChart1.getDateTime();
        if (datetime1.endsWith("am")){
            datetime1 = datetime1.replace("am","AM");
        } else if (datetime1.endsWith("pm")){
            datetime1 = datetime1.replace("pm","PM");
        }
        dateTime1 = LocalDateTime.parse(datetime1, format);
        String datetime2 = patientChart2.getDateTime();
        if (datetime2.endsWith("am")){
            datetime2 = datetime2.replace("am","AM");
        } else if (datetime2.endsWith("pm")){
            datetime2 = datetime2.replace("pm","PM");
        }
        dateTime2 = LocalDateTime.parse(datetime2, format);
        return dateTime1.compareTo(
                dateTime2);
    };
    public static Comparator<PatientChart> datetimeComparatorD = (patientChart1, patientChart2) -> {
        LocalDateTime dateTime1;
        LocalDateTime dateTime2;
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy, hh:mm:ss a");
        String datetime1 = patientChart1.getDateTime();
        if (datetime1.endsWith("am")){
            datetime1 = datetime1.replace("am","AM");
        } else if (datetime1.endsWith("pm")) {
            datetime1 = datetime1.replace("pm","PM");
        }
        dateTime1 = LocalDateTime.parse(datetime1, format);
        String datetime2 = patientChart2.getDateTime();
        if (datetime2.endsWith("am")){
            datetime2 = datetime2.replace("am","AM");
        } else if (datetime2.endsWith("pm")){
            datetime2 = datetime2.replace("pm","PM");
        }
        dateTime2 = LocalDateTime.parse(datetime2, format);
        return dateTime2.compareTo(
                dateTime1);
    };

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
