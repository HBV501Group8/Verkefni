package is.hi.hbv501GEfnahagsspa.forecastGenerator;

import java.time.LocalDate;
import java.util.Date;

public class ForecastInput {

    //TODO Bæta við nafni eða einhverjum identifyer - ef þarf til tengja við Forecast object
    private String name;
    private String frequency; // m monthly, q quarterly, y yearly
    private double[] Series;
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
        return Series;
    }

    public void setSeries(double[] series) {
        Series = series;
    }

    public LocalDate[] getTime() {
        return time;
    }

    public void setTime(LocalDate[] time) {
        this.time = time;
    }
}
