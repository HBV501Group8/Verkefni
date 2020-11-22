package is.hi.hbv501GEfnahagsspa.Controllers;


import is.hi.hbv501GEfnahagsspa.Entities.Forecast;
import is.hi.hbv501GEfnahagsspa.Entities.User;
import is.hi.hbv501GEfnahagsspa.Services.ForecastService;
import is.hi.hbv501GEfnahagsspa.Services.Implementation.ForecastGeneratorService;
import is.hi.hbv501GEfnahagsspa.Services.UserService;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.script.ScriptException;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class ForecastController {

    @Autowired
    private ForecastService forecastService;
    @Autowired
    private UserService userService;

    // Lookup table with information on Icelandic series names
    // this is needed since the R engine won't accept variables with icelandic names
    private static final Map<String, String> seriesNameLookup = new HashMap<String, String>();
    {
        seriesNameLookup.put("Mannfjoldi_is","Fjöldi íslenskra ríkisborgara");
        seriesNameLookup.put("Mannfjoldi_erl","Fjöldi erlendra ríkisborgara");
        seriesNameLookup.put("Atvinnul_rvk","Atvinnuleysi í Reykjavík");
        seriesNameLookup.put("Atvinnul_land","Atvinnuleysi");
        seriesNameLookup.put("Einkaneysla","Einkaneysla");
        seriesNameLookup.put("Samneysla","Samneysla");
        seriesNameLookup.put("Fjarmunamyndun","Fjármunamyndun");
        seriesNameLookup.put("Vara_ut","Útflutningur vara");
        seriesNameLookup.put("Vara_inn","Innflutningur vara");
        seriesNameLookup.put("Thjonusta_ut","Útflutningur þjónustu");
        seriesNameLookup.put("Thjonusta_inn","Innflutningur þjónustu");
        seriesNameLookup.put("VLF","Verg landsframleiðsla");
    }
    /**
     * Grípur fyrirspurn þegar notandinn nær í spá
     * @param id er lykill spár í gagnagrunni
     * @return Skilar Forecast efrtir ID
     */
/*
    @RequestMapping(value = "/forecast/{id}", method = RequestMethod.GET)
    public Forecast getForecast(@PathVariable("id") String id){
        return forecastService.findById(Integer.parseInt(id));
    }
*/
    /*@RequestMapping(value = "forecastupdate", method = RequestMethod.GET)
    public String updateForecast(Model model){

    }*/


    /**
     * Grípur fyrirspurnir um spásmið view.
     * @return Skilar forecastgeneration view
     */
    @RequestMapping(value = "forecastgeneration", method = RequestMethod.GET)
    public String forecastForm(Model model){

        return "forecastgeneration";
    }

    @RequestMapping(value = "updateforecast", method = RequestMethod.GET)
    public String forecastForm(HttpSession session, Model model) throws IOException, ScriptException{

        // Load old forecast and retrieve attributes
        Forecast oldForecast = (Forecast) session.getAttribute("activeForecast");
        String name = oldForecast.getForecastName();
        int length = oldForecast.getForecastInputs().get(0).getSeries().length;
        String forecastModel = oldForecast.getForecastResults().get(0).getForecastModel();

        String[] seriesNames = new String[oldForecast.getForecastResults().size()];
        for(int i = 0; i < seriesNames.length; i++) {
            seriesNames[i] = oldForecast.getForecastInputs().get(i).getName();
        }

        // Generate new forecast with same attributes
        ForecastGeneratorService generatedForecast =
                new ForecastGeneratorService(name, length, forecastModel, seriesNames);

        Forecast newForecast = new Forecast();
        newForecast.setForecastName(generatedForecast.getForecastName());
        newForecast.setForecastInputs(generatedForecast.getForecastInputs());
        newForecast.setForecastResults(generatedForecast.getForecastResults());
        newForecast.setGeneratedTime(LocalDateTime.now());

        // Delete old forecast, save new forecast
        forecastService.delete(oldForecast);
        forecastService.save(newForecast);

        // Display new forecast to forecast view

        // Keeps track of forecast being viewed in session
        session.setAttribute("activeForecast", newForecast);

        // Adds forecast name and time of generation to model in order to display in view
        model.addAttribute("forecastName", newForecast.getForecastName());
        model.addAttribute("forecastTime",newForecast.getGeneratedTime());

        // Adds seriesNames to model in order to generate tabs
        for(int i = 0; i < seriesNames.length; i++) {
            seriesNames[i] = seriesNameLookup.get(newForecast.getForecastResults().get(i).getName());
        }
        model.addAttribute("seriesNames", seriesNames);
        String userlogged = (String) session.getAttribute("loggedInUser");
        System.out.println("user " + userlogged);
        model.addAttribute("userlogged", userlogged);
        return "viewforecast";

    }

    /**
     * Grípur fyrirspurn til að búa til spá.
     * @param name er nafn spár
     * @param length er lengt spár
     * @param model er spálíkan sem notað er við gerð spár
     * @param seriesNames eru tímaraðir sem gera á spá fyrir
     * @return Skilar forecastview með forcast sem var búið til
     */
    // TODO Setja inn exception ef ólöglegt val og þá bara return redirect:/ eða módel og uppfylling
    // með einhverjum hints (mega ekki vera xxx....)
    @RequestMapping(value = "forecastgeneration", method = RequestMethod.POST)
    public String generateForecast(@RequestParam(value = "forecastname") String name,
                                 @RequestParam(value = "length") int length,
                                 @RequestParam(value = "forecastmodel") String forecastModel,
                                 @RequestParam(value = "seriesNames") String[] seriesNames,
                                 HttpSession session,
                                 Model model)
            throws IOException, ScriptException {
        // Generator service called to build Forecast object
        ForecastGeneratorService generatedForecast =
                new ForecastGeneratorService(name, length, forecastModel, seriesNames);
        // Forecast entity created and values from ForecastGenerator assigned to it
        Forecast forecast = new Forecast();
        forecast.setForecastName(generatedForecast.getForecastName());
        forecast.setForecastInputs(generatedForecast.getForecastInputs());
        forecast.setForecastResults(generatedForecast.getForecastResults());
        forecast.setGeneratedTime(LocalDateTime.now());
        // Save forecast to database
        forecastService.save(forecast);

        // Keeps track of forecast being viewed in session
        session.setAttribute("activeForecast", forecast);

        // Adds forecast name and time of generation to model in order to display in view
        model.addAttribute("forecastName", forecast.getForecastName());
        model.addAttribute("forecastTime",forecast.getGeneratedTime());

        // Adds seriesNames to model in order to generate tabs
        String[] names = new String[forecast.getForecastResults().size()];
        for(int i = 0; i < names.length; i++) {
            names[i] = seriesNameLookup.get(forecast.getForecastResults().get(i).getName());
        }
        model.addAttribute("seriesNames", names);
        String userlogged = (String) session.getAttribute("loggedInUser");
        System.out.println("user " + userlogged);
        model.addAttribute("userlogged", userlogged);

        return "viewforecast";
    }

    @RequestMapping(value = "forecastresult/{seriesNumber}", method = RequestMethod.GET)
    public String forecastResult(@PathVariable int seriesNumber,
                                 Model model,
                                 HttpSession session){

        Forecast forecast = (Forecast) session.getAttribute("activeForecast");

        model.addAttribute("forecastName", forecast.getForecastResults().get(seriesNumber).getName() +".jpeg");

        // Draw forecast chart
        JFreeChart chart = forecast.drawForecast(forecast.getForecastResults().get(seriesNumber).getName());

        //TODO breyta þessu þannig að geymist sem user name ekki name á series

        // Save chart for display on next view
        File file = new File("Efnahagsspa/target/classes/static/images/"
                + forecast.getForecastResults().get(seriesNumber).getName() + ".jpeg");
        try {
            ChartUtilities.saveChartAsJPEG(file, chart,700, 400);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add forecast result and input to model in order to display as table
        // in next view
        double[] forecastResultSeries = forecast.getForecastResults().get(seriesNumber).getSeries();
        LocalDate[] forecastResultTime = forecast.getForecastResults().get(seriesNumber).getTime();
        double[] forecastInputSeries =  forecast.getForecastInputs().get(seriesNumber).getSeries();
        LocalDate[] forecastInputTime = forecast.getForecastInputs().get(seriesNumber).getTime();
        model.addAttribute("forecastResultSeries", forecastResultSeries);
        model.addAttribute("forecastResultTime", forecastResultTime);
        model.addAttribute("forecastInputSeries", forecastInputSeries);
        model.addAttribute("forecastInputTime", forecastInputTime);
        return "forecastresult";
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
        
        List<User> list = new ArrayList<>();
        list = userService.findAll();
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
          //  generateDummy(model);
          //  generateDummy(model);
            return "testLogin";

        }


        return "testLogin";
    }
    /**
     * Grípur fyrirspurnir um spásmið view.
     * @return Skilar forecastgeneration view
     */
    @RequestMapping(value = "/getforecast", method = RequestMethod.GET)
    public String getforecast(Model model){

        return "viewforecast";
    }

}
