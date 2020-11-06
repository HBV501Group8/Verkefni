package is.hi.hbv501GEfnahagsspa.Controllers;


import is.hi.hbv501GEfnahagsspa.Entities.Forecast;
import is.hi.hbv501GEfnahagsspa.Entities.ForecastInput;
import is.hi.hbv501GEfnahagsspa.Services.ForecastService;
import is.hi.hbv501GEfnahagsspa.Services.Implementation.ForecastGeneratorService;
import is.hi.hbv501GEfnahagsspa.repositories.ForecastRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.script.ScriptException;
import java.io.IOException;
import java.util.ArrayList;

@Controller
public class ForecastController {

    @Autowired
    private ForecastService forecastService;


    @RequestMapping(value = "/forecast/{id}", method = RequestMethod.GET)
    public Forecast getForecast(@PathVariable("id") String id){
        return forecastService.findById(Integer.parseInt(id));
    }

    @RequestMapping(value = "/forecast/generate/{name}/{length}/{model}/{seriesName}",
            method = RequestMethod.GET)
    public void generateForecast(@PathVariable("name") String name, @PathVariable("length") int length,
                                 @PathVariable("model") String forecastModel, @PathVariable("seriesName") String seriesName)
                                throws IOException, ScriptException {
        // Generator service called to build Forecast object
        ForecastGeneratorService generatedForecast =
                            new ForecastGeneratorService(name, length, forecastModel, seriesName);
        // Forecast entity created and values from ForecastGenerator assigned to it
        Forecast forecast = new Forecast();
        forecast.setForecastName(generatedForecast.getForecastName());
        forecast.setForecastInputs(generatedForecast.getForecastInputs());
        forecast.setForecastResults(generatedForecast.getForecastResults());
        //TODO
        // Vista strax, tökum svo líklega út.. Viljum líklega vista með sér klasa
        // er bara hérna núna til að geta sent gögn inn i gagnagrunn og prófað
        forecastService.save(forecast);
    }

    @RequestMapping(value = "/dummy", method = RequestMethod.GET)
    public void generateDummy(Model model) throws IOException, ScriptException {
        // Random name
        String[] names = {"Flott spa", "Betri spa", "Rosa spa", "Vond spa"};
        String name = names[(int)(Math.random()*names.length)];

        // Random length
        int length = (int)(Math.random()*10);

        // Picks 1 random time series and then three of the same
        String[] series = {"Mannfjoldi_is", "Mannfjoldi_erl", "Atvinnul_rvk", "Atvinnul_land",
                            "Einkaneysla", "Samneysla", "Fjarmunamyndun", "Vara_ut", "Vara_inn"};
        String input_1 = series[(int)(Math.random()*series.length +1)];
        String input_2 = "Thjonusta_ut";
        String input_3 = "Thjonusta_inn";
        String input_4 = "VLF";
        // Picks random model
        String forecastModel;
        int rand = (int)(Math.random()*2);
        if(rand == 1) {
            forecastModel = "var";
        } else {
            forecastModel = "arima";
        }
        Forecast forecast = new Forecast(name, length, forecastModel, input_1,
                                        input_2, input_3, input_4);
        forecastService.save(forecast);
/*
        // Generates forecast
        ForecastGeneratorService generatedForecast =
                new ForecastGeneratorService(name, length, forecastModel, input_1,
                                             input_2, input_3, input_4);
        // Forecast entity created and values from ForecastGenerator assigned to it
        Forecast forecast = new Forecast();
        forecast.setForecastName(generatedForecast.getForecastName());
        forecast.setForecastInputs(generatedForecast.getForecastInputs());
        forecast.setForecastResults(generatedForecast.getForecastResults());
        forecastService.save(forecast);
        */
    }


    @RequestMapping(value = "/graph", method = RequestMethod.GET)
    public String Graph(Model model) {

        //model.addAttribute("movies", movieService.findAll() );
        return "ShowImage";
    }



}
