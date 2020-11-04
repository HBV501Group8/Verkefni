package is.hi.hbv501GEfnahagsspa.forecastGenerator;

import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


@Embeddable
public class ForecastResult {
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private long id;

    private String name;
    private String frequency; // m monthly, q quarterly, y yearly


    @ElementCollection
    //@CollectionTable(name = "series")
    //@MapKeyColumn(name = "timeSeriesName")
    //@Column(name = "series")
    private Map<String, double[]> series = new HashMap<String,double[]>();

    @ElementCollection
    //@CollectionTable(name = "lower")
    //@MapKeyColumn(name = "timeSeriesName")
    //@Column(name = "lower")
    private Map<String, double[]> lower = new HashMap<String,double[]>();

    @ElementCollection
    //@CollectionTable(name = "upper")
    //@MapKeyColumn(name = "timeSeriesName")
    //@Column(name = "upper")
    private Map<String, double[]> upper = new HashMap<String,double[]>();

    @ElementCollection
    @OrderColumn
    private LocalDate[] time;

    private String forecastModel;

    @ElementCollection
    private Map<String, String> forecastDescription = new HashMap<String, String>();


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

    public Map<String, double[]> getSeries() {
        return series;
    }

    public void setSeries(Map<String, double[]> series) {
        this.series = series;
    }

    public Map<String, double[]> getLower() {
        return lower;
    }

    public void setLower(Map<String, double[]> lower) {
        this.lower = lower;
    }

    public Map<String, double[]> getUpper() {
        return upper;
    }

    public void setUpper(Map<String, double[]> upper) {
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

    public Map<String, String> getForecastDescription() {
        return forecastDescription;
    }

    public void setForecastDescription(Map<String, String> forecastDescription) {
        this.forecastDescription = forecastDescription;
    }


}
