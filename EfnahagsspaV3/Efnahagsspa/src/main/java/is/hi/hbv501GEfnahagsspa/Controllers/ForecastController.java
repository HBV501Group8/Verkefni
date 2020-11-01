package is.hi.hbv501GEfnahagsspa.Controllers;


//import is.hi.hbv501GEfnahagsspa.Entities.Forecast;
import is.hi.hbv501GEfnahagsspa.Entities.Forecast;
import is.hi.hbv501GEfnahagsspa.repositories.ForecastRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.script.ScriptException;
import java.io.IOException;
import java.util.Optional;

@Controller
public class ForecastController {

    private ForecastRepository forecastService;

    @RequestMapping(value = "/forecast/{id}", method = RequestMethod.GET)
    public Optional<Forecast> getForecast(@PathVariable("id") String id){
        return forecastService.findById(Integer.parseInt(id));

    }
/*
    @RequestMapping(value = "/forecast/generate/{name}/{length}/{model}/{seriesName}",
            method = RequestMethod.GET)
    public void generateForecast(@PathVariable("name") String name, @PathVariable("length") int length,
                                 @PathVariable("model") String model, @PathVariable("seriesName") String seriesName)
            throws IOException, ScriptException {
        Forecast forecast = new Forecast(name, length, model, seriesName);
        forecastService.save(forecast);
    }
*/
    @RequestMapping(value = "/graph", method = RequestMethod.GET)
    public String Graph(Model model) {

        //model.addAttribute("movies", movieService.findAll() );
        return "ShowImage";
    }


}
