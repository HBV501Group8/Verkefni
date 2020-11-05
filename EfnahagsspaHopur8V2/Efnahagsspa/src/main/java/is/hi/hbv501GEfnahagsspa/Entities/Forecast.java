package is.hi.hbv501GEfnahagsspa.Entities;

import javax.persistence.*;
import javax.script.ScriptException;

import is.hi.hbv501GEfnahagsspa.Services.Implementation.ForecastGeneratorService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

@Table(name = "Forecast")
@Entity
public class Forecast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long forecastId;

    private String forecastName;

    @OneToMany(mappedBy = "forecast", cascade = CascadeType.ALL)
    private List<ForecastResult> forecastResults;

    @OneToMany(mappedBy = "forecast", cascade = CascadeType.ALL)
    private List<ForecastInput> forecastInputs;


    public Forecast() {
    }

    //TODO færa í controller!
    public Forecast(String forecastNme, int length, String model,
                    String ... seriesName) throws IOException, ScriptException {
        ForecastGeneratorService forecastGeneratorService = new ForecastGeneratorService(forecastName, length, model,
                seriesName);
        this.forecastName = forecastGeneratorService.getForecastName();
        this.forecastResults = forecastGeneratorService.getForecastResults();
        this.forecastInputs = (ArrayList<ForecastInput>) forecastGeneratorService.getForecastInputs();
    }

    public long getForecastId() {
        return forecastId;
    }

    public void setForecastId(long forecastId) {
        this.forecastId = forecastId;
    }

    public String getForecastName() {
        return forecastName;
    }

    public void setForecastName(String forecastName) {
        this.forecastName = forecastName;
    }

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
