package is.hi.hbv501GEfnahagsspa.Entities;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.DeviationRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.xy.YIntervalSeries;
import org.jfree.data.xy.YIntervalSeriesCollection;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ForecastAndroid {
/*
    Forecast.java
    Object used to store forecasts.
    JPA:
        - Many-to-One relationship with User
        - One-to-Many relationship with ForecastInput
        - One-to-Many relationship with ForecastResult

    List of methods
        - drawForecast: Draws a chart of a specific series
        - Basic Getters and Setters
*/



    public long id;

    private String forecastName;

    private String generatedTime;

    private List<ForecastResult> forecastResults = new ArrayList<>();

    private List<ForecastInput> forecastInputs = new ArrayList<>();

    public ForecastAndroid(Forecast forecast) {
        id = forecast.getId();
        forecastName = forecast.getForecastName();
        forecastResults = forecast.getForecastResults();
        forecastInputs = forecast.getForecastInputs();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        generatedTime = forecast.getGeneratedTime().format(formatter);
    }


    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getForecastName() { return forecastName; }

    public void setForecastName(String forecastName) {
        this.forecastName = forecastName;
    }

    public String getGeneratedTime() { return generatedTime; }

    public void setGeneratedTime(String generatedTime) { this.generatedTime = generatedTime; }

    public List<ForecastResult> getForecastResults() {
        return forecastResults;
    }

    public void setForecastResults(List<ForecastResult> forecastResults) {
        this.forecastResults = forecastResults;
    }

    public List<ForecastInput> getForecastInputs() {
        return forecastInputs;
    }

    public void setForecastInputs(List<ForecastInput> forecastInputs) {
        this.forecastInputs = forecastInputs;
    }

}
