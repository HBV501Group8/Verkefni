package is.hi.hbv501GEfnahagsspa.Controllers;


import is.hi.hbv501GEfnahagsspa.Entities.Forecast;
import is.hi.hbv501GEfnahagsspa.Entities.User;
import is.hi.hbv501GEfnahagsspa.Services.ForecastService;
import is.hi.hbv501GEfnahagsspa.Services.Implementation.ForecastGeneratorService;
import is.hi.hbv501GEfnahagsspa.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import sun.text.resources.cldr.ext.FormatData_ewo;

import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
public class ForecastController {

    @Autowired
    private ForecastService forecastService;
    @Autowired
    private UserService userService;


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

    }


    @RequestMapping(value = "/forecastsearch", method = RequestMethod.GET)
    public String forecastSearch(Model model) {

        //model.addAttribute("movies", movieService.findAll() );
        return "forecastSearch";
    }
    @RequestMapping(value = "/forecastList", method = RequestMethod.GET)
    public String forecastsList(HttpServletRequest request, Model model, Forecast forecast){
        String forecastName = request.getParameter("forecastName");
        model.addAttribute("forecasts", forecastService.findByForecastNameContaining(forecastName));
        return "forecast";
    }



    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String forecastview(Model model) {

        //model.addAttribute("movies", movieService.findAll() );
        return "forecastform";
    }
    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public String makeData(User user,Model model) throws IOException, ScriptException {


        user.setName("Sigurjón Ólafsson");
        user.setUserName("Sigurjon");
        user.setUserPassword("test");
        user.setEmail("sigurjon@textor.is");
        user.setEnabled(true);
        user.setAdmin(false);
        userService.save(user);
        User user2 = new User();
        user2.setName("Sigurjón Ólafsson2");
        user2.setUserName("admin");
        user2.setUserPassword("admintest");
        user2.setEmail("sigurjon@textor.is");
        user2.setEnabled(true);
        user2.setAdmin(true);
        userService.save(user2);
        generateDummy(model);
        generateDummy(model);
        return "testLogin";
    }
}
