package is.hi.hbv501GEfnahagsspa.Entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "Forecast")
@Entity
public class Forecast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private long id;

    private String forecastName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="Forecast_ID", nullable = false)
    private List<ForecastResult> forecastResults = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="Forecast_ID")
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
