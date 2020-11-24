package is.hi.hbv501GEfnahagsspa.Controllers;


import is.hi.hbv501GEfnahagsspa.Entities.Forecast;
import is.hi.hbv501GEfnahagsspa.Entities.User;
import is.hi.hbv501GEfnahagsspa.Services.ForecastService;
import is.hi.hbv501GEfnahagsspa.Services.ForecastGeneratorService;
import is.hi.hbv501GEfnahagsspa.Services.UserService;
import org.apache.tools.ant.taskdefs.condition.Http;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.script.ScriptException;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
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

    @RequestMapping(value = "/listforecasts", method = RequestMethod.GET)
    public String forecastsList(Model model, HttpSession session){
        User user = (User) session.getAttribute("activeUser");
        model.addAttribute("forecasts", forecastService.findAllByUser(user));;
        String userlogged = (String) session.getAttribute("loggedInUser");
        model.addAttribute("userlogged", userlogged);
        return "listforecasts";

    }

    /**
     * Grípur fyrirspurnir um spásmið view.
     * @return Skilar forecastgeneration view
     */
    @RequestMapping(value = "forecastgeneration", method = RequestMethod.GET)
    public String forecastForm(Model model){
        return "forecastgeneration";
    }

    /**
     * Grípur fyrirspurnir um það að fá að sjá ákveðna spá
     * @return Skilar forecastview með umbeðinni spá
     */
    @RequestMapping(value = "/getforecast/{id}", method = RequestMethod.GET)
    public String getForecast(@PathVariable(value = "id") long id,
                              HttpSession session,
                              Model model){
        // Gets forecast from forecast Id and adds it to session as the activeForecast
        Forecast forecast = (Forecast) forecastService.findById(id);

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

        // Gets active user from session
        User user = (User) session.getAttribute("activeUser");
        model.addAttribute("userlogged", user.getUserName());

        return "viewforecast";
    }

    @RequestMapping(value = "/deleteforecast/{id}", method = RequestMethod.GET)
    public String deleteForecast(@PathVariable(value = "id") long id,
                              HttpSession session,
                              Model model){
        // Gets forecast from forecast Id and adds it to session as the activeForecast
        Forecast forecast = (Forecast) forecastService.findById(id);
        forecastService.delete(forecast);

        // Gets active user from session
        User user = (User) session.getAttribute("activeUser");
        model.addAttribute("forecasts", forecastService.findAllByUser(user));;

        return "listforecasts";
    }

    @RequestMapping(value = "updateforecast", method = RequestMethod.GET)
    public String updateForecast(HttpSession session, Model model) throws IOException, ScriptException{

        // Load old forecast and retrieve attributes
        Forecast oldForecast = (Forecast) session.getAttribute("activeForecast");
        String name = oldForecast.getForecastName();
        int length = oldForecast.getForecastResults().get(0).getSeries().length;
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
        User user = (User) session.getAttribute("activeUser");
        newForecast.setUser(user);

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
        model.addAttribute("userlogged", user.getUserName());
        return "viewforecast";

    }

    @RequestMapping(value = "updateforecast/{id}", method = RequestMethod.GET)
    public String updateForecastById(@PathVariable(value = "id") long id,
                                     HttpSession session,
                                     Model model) throws IOException, ScriptException{

        // Load old forecast and retrieve attributes
        Forecast oldForecast = forecastService.findById(id);
        String name = oldForecast.getForecastName();
        int length = oldForecast.getForecastResults().get(0).getSeries().length;
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
        User user = (User) session.getAttribute("activeUser");
        newForecast.setUser(user);

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
        model.addAttribute("userlogged", user.getUserName());

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

        User user = (User) session.getAttribute("activeUser");
        forecast.setUser(user);

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
        model.addAttribute("userlogged", user.getUserName());

        return "viewforecast";
    }


    @RequestMapping(value = "forecastresult/{seriesNumber}", method = RequestMethod.GET)
    public String forecastResult(@PathVariable int seriesNumber,
                                 Model model,
                                 HttpSession session) throws IOException {

        Forecast forecast = (Forecast) session.getAttribute("activeForecast");

        // Draw forecast chart
        JFreeChart chart = forecast.drawForecast(forecast.getForecastResults().get(seriesNumber).getName());

        // Convert chart to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(chart.createBufferedImage(1000, 600),"png", baos);
        baos.flush();
        byte[] chartInBytes = baos.toByteArray();
        baos.close();

        // Convert byte array to Base64 and add to model in order to display
        model.addAttribute("chartImage", Base64.getEncoder().encodeToString(chartInBytes));


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
}
