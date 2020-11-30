package is.hi.hbv501GEfnahagsspa.Entities;

import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class ForecastInput {
/*
    ForecastInput.java
    Object used to store forecast Inputs.

    List of methods
        - Basic Getters and Setters
*/

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
    @Column(name="InputTime", columnDefinition = "DATE")
    @DateTimeFormat(pattern = "yyyy-MM")
    private LocalDate[] time;

    // Constructor is empty since construction is handled by the ForeastGeneratorService class
    public ForecastInput() { }

    // Getters and setters

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

    public void setSeries(double[] series) {
        this.series = series;
    }

    public LocalDate[] getTime() {
        return time;
    }

    public void setTime(LocalDate[] time) {
        this.time = time.clone();
    }

}
