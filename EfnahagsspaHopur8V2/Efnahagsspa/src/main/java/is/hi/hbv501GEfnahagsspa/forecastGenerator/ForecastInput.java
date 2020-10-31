package is.hi.hbv501GEfnahagsspa.forecastGenerator;

import org.springframework.data.annotation.Id;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDate;


public class ForecastInput {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long forecastID;

    private String name;
    private String frequency; // m monthly, q quarterly, y yearly
    @ElementCollection
    private double[] series;
    @ElementCollection
    private LocalDate[] time;
    @javax.persistence.Id
    private String id;

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

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
