package is.hi.hbv501GEfnahagsspa.Entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "Forecast")
@Entity
public class Forecast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String forecastName;

    @OneToMany(mappedBy = "forecast", cascade = CascadeType.ALL)
    private List<ForecastResult> forecastResults = new ArrayList<>();

    @OneToMany(mappedBy = "forecast", cascade = CascadeType.ALL)
    private List<ForecastInput> forecastInputs = new ArrayList<>();


    public Forecast() {
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
