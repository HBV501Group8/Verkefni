package is.hi.hbv501GEfnahagsspa;
import is.hi.hbv501GEfnahagsspa.forecastGenerator.ForecastBuilder;
import is.hi.hbv501GEfnahagsspa.forecastGenerator.ForecastInput;
import is.hi.hbv501GEfnahagsspa.forecastGenerator.ForecastResult;
import javax.persistence.*;
import javax.script.ScriptException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ForeCastTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long forecastID;
    private String forecastName;

    private ForecastResult forecastResult;

    @ElementCollection
    private ArrayList<ForecastInput> forecastInput;

    public ForeCastTest() {
    }
    public ForeCastTest(String forecastNme, int length, String model,
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

    public ArrayList<ForecastInput> getForecastInput() {
        return forecastInput;
    }

    public void setForecastInput(ArrayList<ForecastInput> forecastInput) {
        this.forecastInput = forecastInput;
    }
}
