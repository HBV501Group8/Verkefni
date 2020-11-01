package is.hi.hbv501GEfnahagsspa;

import is.hi.hbv501GEfnahagsspa.Entities.Forecast;

import javax.script.ScriptException;
import java.io.IOException;

public class forecastPrufa {

    public static void main(String[] args) throws IOException, ScriptException {

        Forecast prufa = new Forecast("prufa", 8, "var",
                "VLF", "Mannfjoldi_is", "Atvinnul_land", "Vara_ut");

        double[] gamla = prufa.getForecastInput().get(0).getSeries();
        double[] frcst = prufa.getForecastResult().getSeries().get("VLF");

        System.out.println("Gamla - VLF");
        for (double value : gamla) {
            System.out.println(value);
        }
        System.out.println("Forecast VLF");
        for (double v : frcst) {
            System.out.println(v);
        }

        prufa = new Forecast("prufa", 8, "arima", "Atvinnul_land", "Mannfjoldi_is", "VLF", "Vara_ut");

        gamla = prufa.getForecastInput().get(0).getSeries();
        frcst = prufa.getForecastResult().getSeries().get("Atvinnul_land");

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