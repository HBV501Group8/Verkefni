package is.hi.hbv501GEfnahagsspa.Entities;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;


@Table(name = "ForecastResult")
@Entity
public class ForecastResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String frequency; // m monthly, q quarterly, y yearly
    private String unit;

    @ElementCollection
    @OrderColumn
    private double[] series;

    @ElementCollection
    @OrderColumn
    private double[] lower;

    @ElementCollection
    @OrderColumn
    private double[] upper;

    @ElementCollection
    @OrderColumn
    @Column(name = "resultTime", columnDefinition = "DATE")
    @DateTimeFormat(pattern = "yyyy-MM")
    private LocalDate[] time;

    private String forecastModel;

    @Column(columnDefinition="longtext")
    private String forecastDescription;


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

    public String getUnit() { return unit; }

    public void setUnit(String unit) { this.unit = unit; }

    public double[] getSeries() {
        return series;
    }

    public void setSeries(double[] resultSeries) {
        this.series = resultSeries;
    }

    public double[] getLower() {
        return lower;
    }

    public void setLower(double[] lower) {
        this.lower = lower;
    }

    public double[] getUpper() {
        return upper;
    }

    public void setUpper(double[] upper) {
        this.upper = upper;
    }

    public LocalDate[] getTime() {
        return time;
    }

    public void setTime(LocalDate[] resultTime) {
        this.time = resultTime.clone();
    }

    public String getForecastModel() {
        return forecastModel;
    }

    public void setForecastModel(String forecastModel) {
        this.forecastModel = forecastModel;
    }

    public String getForecastDescription() {
        return forecastDescription;
    }

    public void setForecastDescription(String forecastDescription) {
        this.forecastDescription = forecastDescription;
    }
}
