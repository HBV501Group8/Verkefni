package is.hi.hbv501GEfnahagsspa.forecastGenerator;

import org.springframework.data.annotation.Id;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDate;
import java.util.HashMap;



public class ForecastResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long forecastID;

    private String name;
    private String frequency; // m monthly, q quarterly, y yearly

    @ElementCollection
    //private HashMap<String, double[]> series;
    private HashMap<String, double[]> series;

    @ElementCollection
    private HashMap<String, double[]> lower;

    @ElementCollection
    private HashMap<String, double[]> upper;

    @ElementCollection
    private LocalDate[] time;

    private String forecastModel;

    @ElementCollection
    private HashMap<String, String> forecastDescription;
    @javax.persistence.Id
    private String id;

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

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
