package is.hi.hbv501GEfnahagsspa.Entities;


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

    @ManyToOne
    //@JoinColumn(name="forecastId")
    private Forecast forecast;

    @ElementCollection
    @OrderColumn
    private double[] resultSeries;

    @ElementCollection
    @OrderColumn
    private double[] lower;

    @ElementCollection
    @OrderColumn
    private double[] upper;

    @ElementCollection
    @OrderColumn
    @Column(name = "resultTime")
    private LocalDate[] resultTime;

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

    public Forecast getForecast() {
        return forecast;
    }

    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }

    public double[] getResultSeries() {
        return resultSeries;
    }

    public void setSeries(double[] resultSeries) {
        this.resultSeries = resultSeries;
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

    public LocalDate[] getResultTime() {
        return resultTime;
    }

    public void setTime(LocalDate[] resultTime) {
        this.resultTime = resultTime;
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
