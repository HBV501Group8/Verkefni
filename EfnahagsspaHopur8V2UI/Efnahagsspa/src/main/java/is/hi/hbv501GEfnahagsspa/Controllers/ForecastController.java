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

import javax.script.ScriptException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ForecastController {

    @Autowired
    private ForecastService forecastService;
    @Autowired
    private UserService userService;

    /**
     * Grípur fyrirspurn þegar notandinn nær í spá
     * @param id er lykill spár í gagnagrunni
     * @return Skilar Forecast efrtir ID
     */

    @RequestMapping(value = "/forecast/{id}", method = RequestMethod.GET)
    public Forecast getForecast(@PathVariable("id") String id){
        return forecastService.findById(Integer.parseInt(id));
    }

    /**
     * Grípur fyrirspurn til að búa til spá.
     * @param name er nafn spár
     * @param length er :
     * @param model er af tagi Model
     * @param seriesName er :
     * @return Skilar Forecast efrtir ID
     */


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
    /**
     * Grípur fyrirspurn til að búa til spá inn í töflu
     * @param model hlutur af taginu Model sem geymir key-value pör sem hægt er að nota í html template-unum
     * @return ekkert
     */
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
    /**
     * Grípur fyrirspurn til að sýna mynd
     * @param model hlutur af taginu Model sem geymir key-value pör sem hægt er að nota í html template-unum
     * @return sendur notandai á spálíkan mynd
     */

    @RequestMapping(value = "/graph", method = RequestMethod.GET)
    public String Graph(Model model) {

        //model.addAttribute("movies", movieService.findAll() );
        return "ShowImage";
    }

    /**
     * Grípur fyrirspurn þegar kemur á rót vefsins
     * @param model hlutur af taginu Model sem geymir key-value pör sem hægt er að nota í html template-unum
     * @param user hlutur af taginu User sem geymir email og lykilorð sem var slegið inn
     * @return endursendum notandann aftur á rót vefs
     */

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String makeData(User user, Model model) throws IOException, ScriptException {
        
        List<Forecast> list = new ArrayList<>();
        list = forecastService.findAll();
        if (list.isEmpty()) {
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


        return "testLogin";
    }

}
