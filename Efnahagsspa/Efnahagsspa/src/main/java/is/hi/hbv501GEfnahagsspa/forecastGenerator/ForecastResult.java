package is.hi.hbv501GEfnahagsspa.forecastGenerator;

import java.util.Date;

public class ForecastResult {

    //TODO Bæta við nafni eða einhverjum identifyer - ef þarf til tengja við Forecast object
    private Date start;
    private String frequency; // m monthly, q quarterly, y yearly
    private double[] Series;
    private String forecastModel;
    private String forecastDescription;

    public ForecastResult() {
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public double[] getSeries() {
        return Series;
    }

    public void setSeries(double[] series) {
        Series = series;
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
