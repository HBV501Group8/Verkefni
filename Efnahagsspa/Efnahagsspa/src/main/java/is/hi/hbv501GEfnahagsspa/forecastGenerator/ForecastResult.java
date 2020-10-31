package is.hi.hbv501GEfnahagsspa.forecastGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class ForecastResult {

    //TODO Bæta við nafni eða einhverjum identifyer - ef þarf til tengja við Forecast object
    private String name;
    private String frequency; // m monthly, q quarterly, y yearly
    private HashMap<String, double[]> series;
    private HashMap<String, double[]> lower;
    private HashMap<String, double[]> upper;
    private LocalDate[] time;
    private String forecastModel;
    private HashMap<String, String> forecastDescription;

    public ForecastResult() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public HashMap<String, double[]> getSeries() {
        return series;
    }

    public void setSeries(HashMap<String, double[]> series) {
        this.series = series;
    }

    public HashMap<String, double[]> getLower() {
        return lower;
    }

    public void setLower(HashMap<String, double[]> lower) {
        this.lower = lower;
    }

    public HashMap<String, double[]> getUpper() {
        return upper;
    }

    public void setUpper(HashMap<String, double[]> upper) {
        this.upper = upper;
    }

    public LocalDate[] getTime() {
        return time;
    }

    public void setTime(LocalDate[] time) {
        this.time = time;
    }

    public String getForecastModel() {
        return forecastModel;
    }

    public void setForecastModel(String forecastModel) {
        this.forecastModel = forecastModel;
    }

    public HashMap<String, String> getForecastDescription() {
        return forecastDescription;
    }

    public void setForecastDescription(HashMap<String, String> forecastDescription) {
        this.forecastDescription = forecastDescription;
    }
}

