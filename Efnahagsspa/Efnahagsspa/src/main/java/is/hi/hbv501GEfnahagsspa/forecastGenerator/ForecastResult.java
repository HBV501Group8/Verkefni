package is.hi.hbv501GEfnahagsspa.forecastGenerator;

import java.time.LocalDate;
import java.util.Date;

public class ForecastResult {

    //TODO Bæta við nafni eða einhverjum identifyer - ef þarf til tengja við Forecast object
    private String frequency; // m monthly, q quarterly, y yearly
    private double[] Series;
    private double[] lower;
    private double[] upper;
    private LocalDate[] time;
    private String forecastModel;
    private String forecastDescription;

    public ForecastResult() {
    }


    public double[] getSeries() {
        return Series;
    }

    public void setSeries(double[] series) {
        Series = series;
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

    public void setTime(LocalDate[] time) {
        this.time = time;
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

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

}
