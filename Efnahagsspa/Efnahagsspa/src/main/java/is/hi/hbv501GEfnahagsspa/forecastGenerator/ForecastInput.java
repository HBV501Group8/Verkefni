package is.hi.hbv501GEfnahagsspa.forecastGenerator;

import java.util.Date;

public class ForecastInput {

    //TODO Bæta við nafni eða einhverjum identifyer - ef þarf til tengja við Forecast object
    private Date start;
    private String frequency; // m monthly, q quarterly, y yearly
    private double[] Series;

    public ForecastInput() {
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
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
}
