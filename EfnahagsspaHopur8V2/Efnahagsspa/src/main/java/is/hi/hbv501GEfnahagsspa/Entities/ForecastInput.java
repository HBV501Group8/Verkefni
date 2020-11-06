package is.hi.hbv501GEfnahagsspa.Entities;

import is.hi.hbv501GEfnahagsspa.Entities.Forecast;
import javax.persistence.Id;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class ForecastInput {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @ManyToOne
    @JoinColumn(name="forecastId")
    private Forecast forecast;

    private String name;
    private String frequency; // m monthly, q quarterly, y yearly

    @ElementCollection
    @OrderColumn
    private double[] series;

    @ElementCollection
    @OrderColumn
    private LocalDate[] time;


    public ForecastInput() {
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

    public double[] getSeries() {
        return series;
    }

    public void setSeries(double[] series) {
        this.series = series;
    }

    public LocalDate[] getTime() {
        return time;
    }

    public void setTime(LocalDate[] time) {
        this.time = time;
    }

}
