package is.hi.hbv501GEfnahagsspa.Entities;
import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "ForecastInput")
@Entity
public class ForecastInput {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @ManyToOne
    //@JoinColumn(name="forecastId")
    private Forecast forecast;

    private String name;
    private String frequency; // m monthly, q quarterly, y yearly

    @ElementCollection
    @OrderColumn
    private double[] inputSeries;

    @ElementCollection
    @OrderColumn
    @Column(name = "inputTime")
    private LocalDate[] inputTime;


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
        return inputSeries;
    }

    public void setSeries(double[] series) {
        this.inputSeries = series;
    }

    public LocalDate[] getTime() {
        return inputTime;
    }

    public void setTime(LocalDate[] time) {
        this.inputTime = time;
    }

}
