package is.hi.hbv501GEfnahagsspa;

import is.hi.hbv501GEfnahagsspa.Services.Implementation.ForecastGeneratorService;

import javax.script.ScriptException;
import java.io.IOException;

public class forecastPrufa {

    public static void main(String[] args) throws IOException, ScriptException {

        ForecastGeneratorService prufa =
                new ForecastGeneratorService("prufa", 8, "var",
                "VLF", "Mannfjoldi_is", "Atvinnul_land", "Vara_ut");

        double[] gamla = prufa.getForecastInputs().get(0).getSeries();
        double[] frcst = prufa.getForecastResults().get(0).getResultSeries();

        System.out.println("Gamla - VLF");
        for (double value : gamla) {
            System.out.println(value);
        }
        System.out.println("Forecast VLF");
        for (double v : frcst) {
            System.out.println(v);
        }

        prufa = new ForecastGeneratorService("prufa", 8, "arima", "Atvinnul_land", "Mannfjoldi_is", "VLF", "Vara_ut");

        gamla = prufa.getForecastInputs().get(0).getSeries();
        frcst = prufa.getForecastResults().get(0).getResultSeries();

        System.out.println("Gamla - Atvinnuleys landsbyggð");
        for (double value : gamla) {
            System.out.println(value);
        }
        System.out.println("Forecast - Atvinnuleysi landsbyggð");
        for (double v : frcst) {
            System.out.println(v);
        }
    }
}
