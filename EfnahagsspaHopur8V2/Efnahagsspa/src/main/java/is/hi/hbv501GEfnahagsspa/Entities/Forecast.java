package is.hi.hbv501GEfnahagsspa.Entities;

import javax.persistence.*;
import javax.script.ScriptException;

import is.hi.hbv501GEfnahagsspa.forecastGenerator.ForecastBuilder;
import is.hi.hbv501GEfnahagsspa.forecastGenerator.ForecastInput;
import is.hi.hbv501GEfnahagsspa.forecastGenerator.ForecastResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

@Table(name = "Forecast")
@Entity
public class Forecast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String forecastName;

    @Embedded
    public ForecastResult forecastResult;

    @OneToMany(mappedBy = "forecast", cascade = CascadeType.ALL)
    private List<ForecastInput> forecastInput;

    public Forecast() {

    }
    public Forecast(String forecastNme, int length, String model,
                    String ... seriesName) throws IOException, ScriptException {
        ForecastBuilder forecastBuilder = new ForecastBuilder(forecastName, length, model,
                seriesName);
        this.forecastName = forecastBuilder.getForecastName();
        this.forecastResult = forecastBuilder.getForecastResult();
        this.forecastInput = (ArrayList<ForecastInput>) forecastBuilder.getForecastInput();
    }
    public String getForecastName() {
        return forecastName;
    }

    public void setForecastName(String forecastName) {
        this.forecastName = forecastName;
    }

    public ForecastResult getForecastResult() {
        return forecastResult;
    }

    public void setForecastResult(ForecastResult forecastResult) {
        this.forecastResult = forecastResult;
    }

    public List<ForecastInput> getForecastInput() {
        return forecastInput;
    }

    public void setForecastInput(List<ForecastInput> forecastInput) {
        this.forecastInput = forecastInput;
    }
}
