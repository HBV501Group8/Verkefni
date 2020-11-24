package is.hi.hbv501GEfnahagsspa;

import is.hi.hbv501GEfnahagsspa.Entities.Forecast;
import is.hi.hbv501GEfnahagsspa.Services.ForecastGeneratorService;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;

public class forecastPrufa {

    public static void main(String[] args) throws IOException, ScriptException {

        ForecastGeneratorService prufa =
                new ForecastGeneratorService("prufa", 8, "var",
                "VLF", "Mannfjoldi_is", "Atvinnul_land", "Vara_ut");

        double[] gamla = prufa.getForecastInputs().get(0).getSeries();
        double[] frcst = prufa.getForecastResults().get(0).getSeries();

        System.out.println("Gamla - VLF");
        for (double value : gamla) {
            System.out.println(value);
        }
        System.out.println("Forecast VLF");
        for (double v : frcst) {
            System.out.println(v);
        }

        String[] arguments = {"Atvinnul_land", "Mannfjoldi_is", "VLF", "Vara_ut"};
        prufa = new ForecastGeneratorService("prufa", 8, "arima",
                                            arguments);

        gamla = prufa.getForecastInputs().get(0).getSeries();
        frcst = prufa.getForecastResults().get(0).getSeries();
        double[] frcstUpper = prufa.getForecastResults().get(0).getUpper();

        System.out.println("Gamla - Atvinnuleys landsbyggð");
        for (double value : gamla) {
            System.out.println(value);
        }
        System.out.println("Forecast - Atvinnuleysi landsbyggð");
        for (double v : frcst) {
            System.out.println(v);
        }
        for (double v: frcstUpper){
            System.out.println(v);
        }

        Forecast prufa2 = new Forecast();
        prufa2.setForecastName(prufa.getForecastName());
        prufa2.setForecastResults(prufa.getForecastResults());
        prufa2.setForecastInputs(prufa.getForecastInputs());
        JFreeChart chart = prufa2.drawForecast("Vara_ut");

        File file = new File("EfnahagsspaHopur8V2UI/Efnahagsspa/src/main/resources/templates/forecastcharts/test.jpeg");
        try {
            ChartUtilities.saveChartAsJPEG(file, chart,1400, 800);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
