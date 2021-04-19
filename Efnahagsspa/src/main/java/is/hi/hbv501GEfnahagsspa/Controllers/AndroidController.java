package is.hi.hbv501GEfnahagsspa.Controllers;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import is.hi.hbv501GEfnahagsspa.Entities.Forecast;
import is.hi.hbv501GEfnahagsspa.Entities.ForecastAndroid;
import is.hi.hbv501GEfnahagsspa.Entities.User;
import is.hi.hbv501GEfnahagsspa.Services.ForecastGeneratorService;
import is.hi.hbv501GEfnahagsspa.Services.ForecastService;
import is.hi.hbv501GEfnahagsspa.Services.Implementation.UserServiceImplementation;
import is.hi.hbv501GEfnahagsspa.Services.UserService;
import org.jfree.chart.JFreeChart;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
public class AndroidController {
/*
    ForecastController.java
    Controller for all interactions and requests related to the Forecast entity.
    Handles requests to view forecasts from database, deleting forecasts, generating
    new forecasts and updating older ones.

    List of methods
        - forecastList: Directs to listforecasts.html which displays forecasts previously
                        generated by user
        - getForecast: Displays specific forecast in viewforecast.html when requested
                       by user in listforecasts.html
        - deleteForecast: Deletes specific forecast when deletion requested by user
                          in listforecasts.html
        - updateForecast: Updates forecast being displayed in view using the most
                          recent data available.
        - updateForecastById: Updates specific forecast when update requested by user
                              in listforecasts.html
        - generateForecast: Generates new forecast using parameters submitted by user
        - forecastResult: Displays specific series from forecast in tabs when viewing
                          forecasts in viewforecast.html
*/

    @Autowired
    private ForecastService forecastService;
    @Autowired
    private UserService userService;

    // Lookup table with information on Icelandic series names.
    // This is needed since the R engine won't accept variables with Icelandic names
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



    /*
        forecastList
            Use: Catches url query "./listforecasts"
            Parameters: model, Model object
                        session, HttpSession object
            Returns: Return template listforecasts.html populated with key/value pairs from model.
                     model key value pairs are:
                        -"forecasts": List of all forecasts previously generated by logged in user
                        -"username": Username of logged in user
    */

    @RequestMapping(value = "android/listforecasts/{username}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ArrayList<String[]> forecastsList(@PathVariable(value = "username") String username,
                                             HttpSession session){
        User user = (User) userService.findByUsername(username);
        List<Forecast> forecasts = forecastService.findAllByUser(user);
        ArrayList<String[]> listForecasts = new ArrayList<String[]>();
        for (Forecast forecast : forecasts) {
            // Extracts forecast id, name and time of creation
            String [] forecastAttributes = new String[3];
            forecastAttributes[0] = String.valueOf(forecast.getId());
            forecastAttributes[1] = forecast.getForecastName();
            // Parses time of creation
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            forecastAttributes[2] = forecast.getGeneratedTime().format(formatter);
            listForecasts.add(forecastAttributes);
        }
        return listForecasts;
    }


    /*
        getForecast
            Use: Catches url query "./getforecast/{id}"
            Parameters: id, unique id of forecast in database
                        model, Model object
                        session, HttpSession object
            Returns: Template viewforecast.html populated with key/value pairs from model.
                     model key value pairs are:
                        -"forecastName": Name of forecast to be displayed
                        -"forecastTime": The time when forecast to be displayed was originally generated
                        -"seriesNames": List with names of series in forecast
                        -"username": Username of logged in user.
    */
    @RequestMapping(value = "android/getforecast/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ForecastAndroid getForecast(@PathVariable(value = "id") long id,
                              HttpSession session){
        Forecast forecast = (Forecast) forecastService.findById(id);
        return new ForecastAndroid(forecast);
    }

    /*
        deleteForecast
            Use: Catches url query "./deleteforecast/{id}"
            Parameters: id, unique id of forecast in database
                        model, Model object
                        session, HttpSession object
            Returns: Forecast with id equal to id parameter is deleted from database.
                     Template listforecast.html populated with key/value pairs from model.
                     model key value pairs are:
                        -"forecasts": List of all forecasts previously generated by logged in user
                        -"username": Username of logged in user
    */
    @RequestMapping(value = "android/deleteforecast/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String deleteForecast(@PathVariable(value = "id") long id,
                                 Model model,
                                 HttpSession session){
        // Gets forecast from forecast Id and adds it to session as the activeForecast
        Forecast forecast = (Forecast) forecastService.findById(id);
        if(forecast != null){
            forecastService.delete(forecast);
            return "{delete: success}";
        }else{
            return "{delete: failed}";
        }
    }

    /*
        updateForecastById
            Use: Catches url query "./updateforecast/{id}"
            Parameters: id, unique id of forecast in database
                        model, Model object
                        session, HttpSession object
            Returns: If data is successfully retrieved from Hagstofan then forecast currently being
                     viewed in viewforecast.html template is updated with most recent data. Then
                     template viewforecast.html is returned populated with key/value pairs from model.
                     model key value pairs are:
                        -"forecastName": Name of forecast to be displayed
                        -"forecastTime": The time when forecast to be displayed was originally generated
                        -"seriesNames": List with names of series in forecast
                        -"username": Username of logged in user.
                     Otherwise, if data can not be successfully retrieved, then forecast is unchanged and
                     template listforecasts.html returned populated with key/value pairs from model.
                     model key value pairs are:
                        -"forecasts": List of all forecasts previously generated by logged in user
                        -"username": Username of logged in user
                        -"errormsg": Error message to be displayed to user
    */
    @RequestMapping(value = "android/updateforecast/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String updateForecastById(@PathVariable(value = "id") long id,
                                     Model model,
                                     HttpSession session)
    {
        // Load old forecast and retrieve attributes
        Forecast oldForecast = forecastService.findById(id);
        String name = oldForecast.getForecastName();
        int length = oldForecast.getForecastResults().get(0).getSeries().length;
        String forecastModel = oldForecast.getForecastResults().get(0).getForecastModel();

        String[] seriesNames = new String[oldForecast.getForecastResults().size()];
        for(int i = 0; i < seriesNames.length; i++) {
            seriesNames[i] = oldForecast.getForecastInputs().get(i).getName();
        }

        // Try to update with new data from Hagstofan. If no answer from Hagstofan then
        // return same page plus error message
        try {
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
            long newId = forecastService.findById(newForecast.getId()).getId();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String time = newForecast.getGeneratedTime().format(formatter);

            return "{generatedID:" + String.valueOf(newId) + ", time:" + time + "}";

        } catch(Exception e) {
            return "{generatedID:failed}";
        }
    }


    /*
        generateForecast
            Use: Catches url query "./forecastgeneration"
            Parameters: name, name of forecast to be generated
                        length, integer in [1..12] specifying length of forecast
                        forecastModel, string describing which model should be used for generation
                        seriesNames, list of inputs from Hagstofan to be used for generation
                        model, Model object
                        session, HttpSession object
            Returns: If data is successfully retrieved from Hagstofan a new forecast will be
                     generated and saved to database. Then the viewforecast.html template is is returned
                     populated with key/value pairs from model.
                     model key value pairs are:
                        -"forecastName": Name of forecast to be displayed
                        -"forecastTime": The time when forecast to be displayed was originally generated
                        -"seriesNames": List with names of series in forecast
                        -"username": Username of logged in user.
                     Otherwise, if data can not be successfully retrieved, then no forecast is generated
                     and user redirected back to forecastgeneration.html and an error message displayed.
                     model key value pairs are:
                        -"forecasts": List of all forecasts previously generated by logged in user
                        -"username": Username of logged in user.
                        -"errormsg": Error message to be displayed to user.
    */
    @RequestMapping(value = "android/forecastgeneration", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String generateForecast(@RequestBody String string) {

        // Extract parameters from request
        JSONObject json = new JSONObject(string);

        String username = json.get("username").toString();
        String name = json.get("name").toString();
        int length = Integer.parseInt(json.get("length").toString());
        String forecastModel = json.get("forecastModel").toString();


        JSONArray jsonArray = (JSONArray) json.get("seriesNames");
        String[] seriesNames =new String[jsonArray.length()];
        for(int i = 0; i < seriesNames.length; i++) {
            seriesNames[i] = jsonArray.optString(i);
        }


        // Try to generate forecast with data from Hagstofan. If no answer from Hagstofan then
        // return same page plus error message
        try {
            // Generator service called to build Forecast object
            ForecastGeneratorService generatedForecast =
                    new ForecastGeneratorService(name, length, forecastModel, seriesNames);
            // Forecast entity created and values from ForecastGenerator assigned to it
            Forecast forecast = new Forecast();
            forecast.setForecastName(generatedForecast.getForecastName());
            forecast.setForecastInputs(generatedForecast.getForecastInputs());
            forecast.setForecastResults(generatedForecast.getForecastResults());
            forecast.setGeneratedTime(LocalDateTime.now());

            User user = (User) userService.findByUsername(username);
            forecast.setUser(user);

            // Save forecast to database
            forecastService.save(forecast);

            long id = forecastService.findById(forecast.getId()).getId();
            return "{generatedID: " + String.valueOf(id) + "}";

        } catch(Exception e) {
            e.printStackTrace();
            return "{generatedID: failed}";
        }
    }

    /*
        forecastResult
            Use: Catches url query "./forecastResult/{seriesNumber}"
            Parameters: seriesNumber, index number of series from arrays forecastResults
                                      and forecastInputs in Forecast object.
                        model, Model object
                        session, HttpSession object
            Returns: Template forecastresult.html template populated with key/value pairs from model.
                     model key value pairs are:
                        -"chartImage": Forecast line chart with confidence intervals
                        -"forecastResultSeries": Forecasted values
                        -"forecastResultTime": Forecasted value dates
                        -"forecastInputSeries": Input values
                        -"forecastInputTime": Input value dates

    @RequestMapping(value = "android/forecastresult/{seriesNumber}", method = RequestMethod.GET)
    public String forecastResult(@PathVariable int seriesNumber,
                                 Model model,
                                 HttpSession session) throws IOException {
        // Get active forecast
        Forecast forecast = (Forecast) session.getAttribute("activeForecast");

        // Draw forecast chart using draw method in Forecast object.
        JFreeChart chart = forecast.drawForecast(forecast.getForecastResults().get(seriesNumber).getName());

        // Convert chart to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(chart.createBufferedImage(1000, 600),"png", baos);
        baos.flush();
        byte[] chartInBytes = baos.toByteArray();
        baos.close();

        // Convert byte array to Base64 and add to model
        model.addAttribute("chartImage", Base64.getEncoder().encodeToString(chartInBytes));

        // Add forecast result and input to model to display in table
        double[] forecastResultSeries = forecast.getForecastResults().get(seriesNumber).getSeries();
        LocalDate[] forecastResultTime = forecast.getForecastResults().get(seriesNumber).getTime();
        double[] forecastInputSeries =  forecast.getForecastInputs().get(seriesNumber).getSeries();
        LocalDate[] forecastInputTime = forecast.getForecastInputs().get(seriesNumber).getTime();
        model.addAttribute("forecastResultSeries", forecastResultSeries);
        model.addAttribute("forecastResultTime", forecastResultTime);
        model.addAttribute("forecastInputSeries", forecastInputSeries);
        model.addAttribute("forecastInputTime", forecastInputTime);

        return "forecastresult";

     */

    @RequestMapping(value = "android/loginsubmit", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String userLogin(@RequestBody String string, HttpServletRequest request, HttpSession session) {

        // Extract parameters from request
        JSONObject json = new JSONObject(string);
        String username = json.get("username").toString();
        String userPassword = json.get("password").toString();


        // Retrive user from database
        User user =  userService.findByUsername(username);

        // If User with typed user name does exist and that User's password from database
        // matches password typed by user then log user in as either admin or normal
        // user depending.
        // Otherwise direct back to login and display error
        if((user!=null) && (userPassword.equals(user.getUserPassword())))  {
            // Check if User account is admin account or not and redirect
            // accordingly
            if(!user.getAdmin()) {
                // Mark signed in user as activeUser
                session.setAttribute("activeUser", user);
                return "{login: success}";
            }
            else {
                // Mark signed in user as activeUser
                session.setAttribute("activeUser", user);

                return "{login: success}";
            }
        }
        else {
            return "{login: failed}";
        }
    }

    @RequestMapping(value = "android/registeruser", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String userRegister(@RequestBody String string, HttpServletRequest request, HttpSession session) {

        // Extract parameters from request
        JSONObject json = new JSONObject(string);
        String name = json.get("name").toString();
        String email = json.get("email").toString();
        String username = json.get("username").toString();
        String password = json.get("password").toString();

        User user = new User();

        // Check if user with chosen username already exists in database.
        // If not then register user, otherwise return error message
        if(userService.findByUsername(username) == null) {
            user.setName(name);
            user.setUsername(username);
            user.setUserPassword(password);
            user.setEmail(email);
            user.setEnabled(true);
            user.setAdmin(false);

            userService.save(user);

            return "{register: success}";
        } else {
            return "{register: exists}";
        }
    }
}

