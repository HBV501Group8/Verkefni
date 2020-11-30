package is.hi.hbv501GEfnahagsspa.Services;

import is.hi.hbv501GEfnahagsspa.Entities.ForecastInput;
import is.hi.hbv501GEfnahagsspa.Entities.ForecastResult;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.renjin.script.RenjinScriptEngineFactory;
import org.renjin.sexp.DoubleVector;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForecastGeneratorService {
/*
    ForecastGeneratorService.java
    Handles all aspects of forecast generation. Retrieves input series' from Hagstofan using POST
    requests. Then modifies inputs to make them legal inputs for models. Finally runs inputs through
    models using a JVM implementation of R (renjin) in order to produce forecasts.
    This class is used as the constructor for Forecast objects. First the required parameters should
    be passed to the ForecastGeneratorService constructor. Then the attributes from the
    ForecastGeneratorService object should be passed to a new Forecast object using setters and getters.

    Note: Group 2 found that the methods in this class were too long and hard to follow.
          Their recommendation was that the code should be split into smaller methods.
          Having given this some consideration it was decided to keep the structure of this
          class unchanged. The methods here may be long but this is not because they are
          doing anything complicated but rather because they are doing simple but code heavy
          tasks. Furthermore, they follow a clearly defined linear progression:
            (download all data -> parse data -> use data to produce forecasts)
          Splitting them any further would probably just reduce the readability of the code.
          However, should more model options be added in the future (beyond VAR and ARIMA)
          it would probably be beneficial to split the R parts of the generateForecast method
          into further methods, one for each type of model.

    List of methods
        - ForecastGeneratorService: Constructor, uses passed parameters to construct lists of
                                    ForecastInput and ForecastResult objects using calls to the
                                    downloadInputData and generateForecast methods.
        - downloadInputData: Uses name of input series to download data from Hagstofan. Then parses
                             the http response and returns the data as a forecastInput object.
        - generateForecast: Uses a list of forecastInput objects to create a list of forecastResult
                            objects by passing the required parameters from the forecastInput objects
                            to the renjin R engine, running the required R functions and then passing
                            objects back from the R engine to Java. Then returns the list of
                            forecastResult objects.
*/

    private String forecastName;
    private ArrayList<ForecastResult> forecastResults;
    private ArrayList<ForecastInput> forecastInputs = new ArrayList<>();


/*
    ForecastGeneratorService
        Use: new ForecastGeneratorService(forecastName, length, model, seriesName)
        Parameters: forecastName, name of forecast to be generated
                    length, length of forecast to be generated
                    model, model to be used for forecast generation
                    seriesName, Name of series to be used in forecast generation.
                                Should be passed either as a list of Strings or one
                                or more strings seperated by commas.
        Returns: Assigns forecastName to forecastName attribute.
                 Assigns list of ForecastInput objects to forecastInputs attribute
                 Assigns list of FOrecastResult objecst to forecastResults attribute
*/
    public ForecastGeneratorService(String forecastName, int length, String model,
                                    String ... seriesName) throws IOException, ScriptException{

        // Assign name to forecastName attribute
        this.forecastName = forecastName;

        // Retrieve required data from Statistics Iceland by calling downloadInputData
        // for each input series. Inputs are added to forecastInputs attribute
        for(String name:seriesName) {
            forecastInputs.add(downloadInputData(name));
        }

        // MinMax and MaxMin dates found (the earliest data common to all series and
        // the latest date common to all series)
        LocalDate minMax = LocalDate.of(3000, 1, 1);
        LocalDate maxMin = LocalDate.of(1000, 1, 1);

        // Input time periods equalized using minMax and maxMin
        // This is required in order to make input legal for R script functions
        for(ForecastInput input:forecastInputs) {
            LocalDate max = input.getTime()[input.getTime().length-1];
            LocalDate min = input.getTime()[0];
            if(max.compareTo(minMax) < 0) minMax = max;
            if(min.compareTo(maxMin) > 0) maxMin = min;
        }

        for(ForecastInput input:forecastInputs) {
            LocalDate[] temp_time = input.getTime();
            double[] temp_series = input.getSeries();

            int max = temp_time.length-1;
            int min = 0;

            // Iterate through array from both sides to find indices
            // between which data will be kept.
            while(temp_time[min].compareTo(maxMin) < 0) min++;
            while(temp_time[max].compareTo(minMax) > 0) max--;

            input.setTime(Arrays.copyOfRange(temp_time, min, max+1));
            input.setSeries(Arrays.copyOfRange(temp_series,min, max+1));
        }

        // Get frequency of inputs (currently all inputs are quarterly so this is not
        // strictly required. However, if the list of inputs were to be expanded to
        // monthly and yearly inputs this would be required since inputs must all
        // be of the same frequency in order to be legal). There were plans to increase
        // the number of inputs but they did not come to fruition due to lack of time.
        String freq = this.forecastInputs.get(0).getFrequency();

        // Generate list of forecastResult objecst by calling generateForecast and assign
        // result to forecastResults attribute
        this.forecastResults = generateForecast(this.forecastInputs, length, freq, model,
                minMax, maxMin);
    }


    // Lookup table with information on input data from Hagstofan
    // This is required due to the rather limited design of the API used
    private static final Map<String, String[][]> inputLookup = new HashMap<String, String[][]>();
    {
        inputLookup.put("Mannfjoldi_is",
                new String[][] {{"Ibuar/mannfjoldi/1_yfirlit/arsfjordungstolur/MAN10001.px"}, {"q"},
                        {"Fjöldi"}, {"Sveitarfélag","-"},{"Kyn og ríkisfang", "3"}});

        inputLookup.put("Mannfjoldi_erl",
                new String[][] {{"Ibuar/mannfjoldi/1_yfirlit/arsfjordungstolur/MAN10001.px"}, {"q"},
                        {"Fjöldi"}, {"Sveitarfélag","-"},{"Kyn og ríkisfang", "4"}});

        inputLookup.put("Atvinnul_rvk",
                new String[][] {{"Samfelag/vinnumarkadur/vinnumarkadsrannsokn/2_arsfjordungstolur/VIN00920.px"}, {"q"},
                        {"%"},{"Landsvæði","1"},{"Kyn","0"}, {"Aldur","0"}, {"Eining", "0"}});

        inputLookup.put("Atvinnul_land",
                new String[][]  {{"Samfelag/vinnumarkadur/vinnumarkadsrannsokn/2_arsfjordungstolur/VIN00920.px"}, {"q"},
                        {"%"},{"Landsvæði","2"},{"Kyn","0"}, {"Aldur","0"}, {"Eining", "0"}});

        inputLookup.put("Einkaneysla",
                new String[][]  {{"Efnahagur/thjodhagsreikningar/landsframl/2_landsframleidsla_arsfj/THJ01601.px"}, {"q"},
                        {"Milljónir kr."}, {"Mælikvarði","0"},{"Skipting","0"}});

        inputLookup.put("Samneysla",
                new String[][]  {{"Efnahagur/thjodhagsreikningar/landsframl/2_landsframleidsla_arsfj/THJ01601.px"}, {"q"},
                        {"Milljónir kr."},{"Mælikvarði","0"},{"Skipting","1"}});

        inputLookup.put("Fjarmunamyndun",
                new String[][]  {{"Efnahagur/thjodhagsreikningar/landsframl/2_landsframleidsla_arsfj/THJ01601.px"}, {"q"},
                        {"Milljónir kr."}, {"Mælikvarði","0"},{"Skipting","2"}});

        inputLookup.put("Vara_ut",
                new String[][]  {{"Efnahagur/thjodhagsreikningar/landsframl/2_landsframleidsla_arsfj/THJ01601.px"}, {"q"},
                        {"Milljónir kr."}, {"Mælikvarði","0"},{"Skipting","9"}});

        inputLookup.put("Vara_inn",
                new String[][]  {{"Efnahagur/thjodhagsreikningar/landsframl/2_landsframleidsla_arsfj/THJ01601.px"}, {"q"},
                        {"Milljónir kr."}, {"Mælikvarði","0"},{"Skipting","12"}});

        inputLookup.put("Thjonusta_ut",
                new String[][]  {{"Efnahagur/utanrikisverslun/2_thjonusta/thjonusta/UTA04005.px"}, {"q"}, {"Milljónir kr."},
                        {"Þjónustuviðskipti", "0"}, {"Flokkur", "0"}});

        inputLookup.put("Thjonusta_inn",
                new String[][]  {{"Efnahagur/utanrikisverslun/2_thjonusta/thjonusta/UTA04005.px"}, {"q"}, {"Milljónir kr."},
                        {"Þjónustuviðskipti", "1"}, {"Flokkur", "0"}});

        inputLookup.put("VLF",
                new String[][]  {{"Efnahagur/thjodhagsreikningar/landsframl/2_landsframleidsla_arsfj/THJ01601.px"}, {"q"},
                        {"Milljónir kr."}, {"Mælikvarði","0"},{"Skipting","14"}});
    }


/*
    downloadInputData
        Use: downloadInputData(url, name)
        Parameters: name, name of input which should be downloaded
        Returns: ForecastInput object containing the requested data.
*/
    private ForecastInput downloadInputData(String name) throws IOException {

        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        // Retrives url and query items for time series by name from lookup table
        String[][] queryParameters = inputLookup.get(name);
        String url = "https://px.hagstofa.is:443/pxis/api/v1/is/" + queryParameters[0][0];

        // Start of JSON query
        StringBuilder jsonQuery = new StringBuilder("{\"query\": [");

        // Middle of JSON query, regexp: ({code: X, selection: Y, filter: Z, values: R})*
        for (String[] parameter:queryParameters) {
            if(parameter.length < 2) continue;
            jsonQuery.append("{\"code\": \"" + parameter[0] +
                    "\", \"selection\": { \"filter\": \"item\"," +
                    " \"values\": [\"" + parameter[1] + "\"]}},");
        }
        // End of JSON query, always asks Hagstofan for JSON formated response
        jsonQuery.setLength(jsonQuery.length() - 1); // delete extra comma at end
        jsonQuery.append("],\"response\": { \"format\": \"json\"}}");

        // POST request sent to url
        RequestBody body = RequestBody.create(jsonQuery.toString(), JSON);
        Request request = new Request.Builder().url(url).post(body).build();

        Call call = client.newCall(request);
        Response response = call.execute();

        //JSON response parsed and assigned to ForecastInput object
        JSONObject jo = new JSONObject(response.body().string());
        JSONArray jsonArray = jo.getJSONArray("data");

        ForecastInput input = new ForecastInput();
        input.setName(name);
        input.setFrequency(queryParameters[1][0]);
        input.setUnit(queryParameters[2][0]);

        double[] series = new double[jsonArray.length()];
        LocalDate[] time = new LocalDate[jsonArray.length()];

        // Note: this was ultimately not implemented due to time constraints
        // Data can be monthly, quarterly or yearly and need to be parsed accordingly

        // Set time format based on frequency of data
        DateTimeFormatter format;
        Pattern quarter;
        if(input.getFrequency().equals("m")) {
            quarter = Pattern.compile("[0-9][0-9][0-9][0-9]M[0-1][0-9]");
            format = new DateTimeFormatterBuilder().appendPattern("yyyy'M'MM")
                    .parseDefaulting(ChronoField.DAY_OF_MONTH, 28)
                    .toFormatter();
        }else if(input.getFrequency().equals("q")) {
            quarter = Pattern.compile("[0-9][0-9][0-9][0-9]Q[1-4]");
            format = new DateTimeFormatterBuilder().appendPattern("yyyyMM")
                    .parseDefaulting(ChronoField.DAY_OF_MONTH, 30)
                    .toFormatter();
        }else {
            quarter = Pattern.compile("[0-9][0-9][0-9][0-9]");
            format = new DateTimeFormatterBuilder().appendPattern("yyyy")
                    .parseDefaulting(ChronoField.MONTH_OF_YEAR, 12)
                    .parseDefaulting(ChronoField.DAY_OF_MONTH, 31)
                    .toFormatter();
        }

        // Parse dates using assigned time format
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject temp = jsonArray.getJSONObject(i);
            series[i] = Double.parseDouble(temp.get("values").toString()
                    .replaceAll("[\\[\"\\]]", ""));

            Matcher matcher = quarter.matcher(temp.get("key").toString());
            if (matcher.find()) {
                try {
                    String date = matcher.group(0);
                    if(input.getFrequency().equals("q")) {
                        String[] temp_string = date.split("Q");
                        int quart = Integer.parseInt(temp_string[1]);
                        quart *= 3;
                        if(quart == 12) {
                            date = temp_string[0] + quart;
                        }else {
                            date = temp_string[0] + 0 + quart;
                        }
                    }

                    time[i] = LocalDate.parse(date, format);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // Assign series and time as attributes of ForecastInput object
        input.setSeries(series);
        input.setTime(time);

        // Return forecastInput object
        return input;

    }

/*
    generateForecast
        Use: generateForecast(forecastInput, length, frequency, model, minMax, maxMin)
        Parameters: forecastInput, List of ForecastInputs objects which are to be used
                                   to generate forecast.
                    length, length of forecast to be generated
                    frequency, frequency of forecastInputs
                    model, model to be used for forecast generation
                    minMax, the latest date common to all series
                    maxMin, the earliest date common to all series
        Returns: List of ForecastResult objects containing the generated forecasts.
*/
    private ArrayList<ForecastResult> generateForecast(ArrayList<ForecastInput> forecastInput, int length,
                                           String frequency, String model, LocalDate minMax, LocalDate maxMin
                                      )throws ScriptException {

        // Define ForecastResult object to return
        ArrayList<ForecastResult> forecastResults = new ArrayList<>();

        // Define general variables needed in R script freq, month and year are needed in ts()
        // inputLen is needed to define data.frame length is needed to define length of forecast in R
        int month = maxMin.getMonthValue();
        int year = maxMin.getYear();
        int inputLen = forecastInput.get(0).getSeries().length;

        // Get freq parameter to pass to R engine and compute LocalDate array for forecast time period
        int freq;
        LocalDate[] time = new LocalDate[length];
        if(frequency.equals("m")) {
            freq = 12;
            for(int i = 0; i < length; i++) {
                time[i] = minMax.plusMonths(i+1);
            }
        }else if(frequency.equals("q")){
            freq = 4;
            month = month/4;
            for(int i = 0; i < length; i++) {
                time[i] = minMax.plusMonths((i+1)*3);
            }
        }else {
            freq = 1;
            for(int i = 0; i < length; i++) {
                time[i] = minMax.plusYears(i+1);
            }
        }


        // Start R JVM engine
        RenjinScriptEngineFactory factory = new RenjinScriptEngineFactory();
        ScriptEngine engine = factory.getScriptEngine();

        // Pass parameters to R engine
        engine.put("inputLen", inputLen);
        engine.put("freq", freq);
        engine.put("len", length);

        // Pass input to R engine, convert to time series objects
        // and then combine into data.frame.
        engine.eval("library('tseries')");

        engine.eval("input = data.frame()[1:inputLen, ]");
        for(ForecastInput input:forecastInput) {
            String name = input.getName();
            engine.put(name,input.getSeries());
            engine.eval(name + "_ts = ts("+ name +", start = c(" + year + "," + month + "), frequency = freq)");
            engine.eval("input[\"" + name + "\"] = " + name + "_ts");
        }

        // Generate forecast values
        if(model.equals("var")){
            // Load required R library
            engine.eval("library('vars')");

            // VAR estimated
            // p selected using minimum AIC
            // no unit root test performed - since this is for forecasting only
            // the lag length p is always 4 (a preferable method would have been to estimate
            // the optimal p - the vars library includes a function for this called VARselect.
            // However, it currently does not run on the R JVM engine.
            engine.eval("var = VAR(input, p = 4, type = \"both\")");

            // VAR used to forecast
            engine.eval("forecast = predict(var, n.ahead = "+ length +", ci = 0.95)");

            // Extracting model description
            StringWriter descrWriter = new StringWriter();
            engine.getContext().setWriter(descrWriter);
            engine.eval("print(var)");
            String descr = descrWriter.toString();

            // Creating ForecastResult objects using VAR output from R engine
            for(ForecastInput input:forecastInput) {

                // Extract name of input in order to assign to forecastResult
                String name = input.getName();
                String unit = input.getUnit();

                // Define ForecastResult object to store model forecast for input time series
                ForecastResult forecastResult = new ForecastResult();

                // Set forecastResult attributes already within to Java scope
                forecastResult.setName(name);
                forecastResult.setUnit(unit);
                forecastResult.setForecastModel(model);
                forecastResult.setFrequency(frequency);
                forecastResult.setTime(time);
                forecastResult.setForecastDescription(descr);

                // Set forecastResult attributes which need to be extracted from R engine
                DoubleVector forecastSeries = (DoubleVector) engine.eval("forecast$fcst$"+ name +"[,\"fcst\"]");
                DoubleVector forecastUpper = (DoubleVector) engine.eval("forecast$fcst$"+ name +"[,\"upper\"]");
                DoubleVector forecastLower = (DoubleVector) engine.eval("forecast$fcst$"+ name +"[,\"lower\"]");
                forecastResult.setSeries(forecastSeries.toDoubleArray());
                forecastResult.setUpper(forecastUpper.toDoubleArray());
                forecastResult.setLower(forecastLower.toDoubleArray());

                // Add forecastResult to forecastResults list
                forecastResults.add(forecastResult);
            }
        } else {
            // If model is not equal to var then ARIMA model is used to
            // generate forecast

            // Load required R library
            engine.eval("library('crayon')");
            engine.eval("library('forecast')");

            // Generate forecast values for each input and extract results from R engine
            for(ForecastInput input:forecastInput) {

                // Extract name of input in order to assign to forecastResult
                String name = input.getName();
                String unit = input.getUnit();

                // Define ForecastResult object to store model forecast for input time series
                ForecastResult forecastResult = new ForecastResult();

                // Set forecastResult attributes already within to Java scope
                forecastResult.setName(name);
                forecastResult.setUnit(unit);
                forecastResult.setForecastModel(model);
                forecastResult.setFrequency(frequency);
                forecastResult.setTime(time);

                // Run ARIMA forecast estimation in R engine
                engine.eval("frcst = forecast(auto.arima(input[\""+ name +"\"]), h ="+ length +")");

                // Extract forcasted values to Java
                DoubleVector forecastSeries = (DoubleVector)  engine.eval("as.numeric(frcst$mean[])");
                DoubleVector forecastUpper = (DoubleVector) engine.eval("as.numeric(frcst$upper[,2])");
                DoubleVector forecastLower = (DoubleVector) engine.eval("as.numeric(frcst$lower[,2])");

                // Extract description for each estimated equation to Java
                StringWriter descrWriter = new StringWriter();
                engine.getContext().setWriter(descrWriter);
                engine.eval("print(frcst$model)");
                String descr = descrWriter.toString();

                // Assign values from R engine to forecastResult object
                forecastResult.setSeries(forecastSeries.toDoubleArray());
                forecastResult.setUpper(forecastUpper.toDoubleArray());
                forecastResult.setLower(forecastLower.toDoubleArray());
                forecastResult.setForecastDescription(descr);

                // Add forecastResult to forecastResults list
                forecastResults.add(forecastResult);
            }
        }

        // Return list of forecastResult objects
        return forecastResults;
    }

    // Getters

    public String getForecastName() {
        return forecastName;
    }

    public List<ForecastResult> getForecastResults() {
        return forecastResults;
    }

    public List<ForecastInput> getForecastInputs() {
        return forecastInputs;
    }

}
