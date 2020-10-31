package is.hi.hbv501GEfnahagsspa.entities;

import is.hi.hbv501GEfnahagsspa.forecastGenerator.ForecastInput;
import is.hi.hbv501GEfnahagsspa.forecastGenerator.ForecastResult;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;

@Entity
public class Forecast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long forecastID;
    private String forecastName;

    private ForecastResult forecastResult;
    @ElementCollection
    private ArrayList<ForecastInput> forecastInput;



}
