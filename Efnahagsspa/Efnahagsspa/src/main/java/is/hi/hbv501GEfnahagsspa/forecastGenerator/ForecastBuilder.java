package is.hi.hbv501GEfnahagsspa.forecastGenerator;

import antlr.StringUtils;
import is.hi.hbv501GEfnahagsspa.EfnahagsspaApplication;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.helper.StringUtil;
import org.renjin.script.RenjinScriptEngineFactory;
import org.renjin.sexp.Vector;
import org.springframework.boot.SpringApplication;

import javax.persistence.ElementCollection;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ForecastBuilder {


    private String forecastName;
    private Map<String, ForecastResult> forecastResult = new HashMap<>();
    private Map<String, ForecastInput> forecastInput = new HashMap<>();

    public ForecastBuilder() {
    }

    //TODO klára fyrst föllin hér að neðan, útfæra svo þennan með köllum á þau
    public ForecastBuilder(String forecastName, int length,
                           String model, String ... seriesName) throws IOException{

        this.forecastName = forecastName;

        // Load required data from Statistics Iceland
        for(String name:seriesName) {
            forecastInput.put(name, downloadInputData(name));
        }

        //TODO EF bætt við tímaröðum með aðra tíðni en ársfjórðunglega þarf hér að
        // þræða í gegnum þau input sem ekki eru á sömu tíðni og færa upp ef hægt.

        // Input data time period equalized
        // MinMax and MaxMin dates found
        LocalDate minMax = LocalDate.of(3000, 1, 1);
        LocalDate maxMin = LocalDate.of(1000, 1, 1);

        for(ForecastInput input:forecastInput.values()) {
            LocalDate max = input.getTime()[input.getTime().length-1];
            LocalDate min = input.getTime()[0];
            if(max.compareTo(minMax) < 0) minMax = max;
            if(min.compareTo(maxMin) > 0) maxMin = min;
        }


        // Data newer than MinMax and older than MaxMin thrown out
        // length of inputs stored for future use
        int inputLen = 0;
        for(ForecastInput input:forecastInput.values()) {
            LocalDate[] temp_time = input.getTime();
            double[] temp_series = input.getSeries();

            int max = temp_time.length-1;
            int min = 0;

            // Iterate through array from both sides to find indices
            // between which data will be kept.
            while(temp_time[min].compareTo(maxMin) < 0) min++;
            while(temp_time[max].compareTo(minMax) > 0) max--;

            input.setTime(Arrays.copyOfRange(temp_time, min, max));
            input.setSeries(Arrays.copyOfRange(temp_series,min, max));
            inputLen = max-min+1;
        }


        /*
        Keyra forecast creation fall - output á að vera forecastResult object
        Setja það se m
         */
    }

    //TODO laga þetta implementation - ekki viss um að það sé best að hafa þetta
    // í Hashmap. Hugsanlega ætti lookup table frekar að vera global og skilgreint í main
    // í EfnahagsspaAppilcation klasa. Sparar að það sé alltaf keyrt upp aftur i hvert
    // skipti sem ForecastBuilder object er búið til. Mætti líka breyta nöfnunum á breytum.

    //TODO Mætti fjölga breytum, byrja með þessar 12 til þess að prófa og útfæra virkni
    // Allar ársfjórðungslegar, mætti fjölga og bæta við öðrum tímabilum. Þarf þá líka
    // að útfæra fall þannig að allar breytur séu færðar á sömu tíðni og sú sem
    // sem hefur lægsta tíðni af þeim sem voru valdar. Þarf líka lookup values þar
    // þar sem ekki er hægt að fara allar breytur yfir á lægri tíðni á sama máta (sumar
    // þarf að summa yfir tímabilið, aðrar ekki.

    // Lookup table with information on input data from Statistics Iceland
    private static final Map<String, String[][]> inputLookup = new HashMap<String, String[][]>();
    {
        inputLookup.put("Mannfjoldi-is",
                new String[][] {{"Ibuar/mannfjoldi/1_yfirlit/arsfjordungstolur/MAN10001.px"}, {"q"},
                                {"Sveitarfélag","-"},{"Kyn og ríkisfang", "3"}});

        inputLookup.put("Mannfjoldi-erl",
                new String[][] {{"Ibuar/mannfjoldi/1_yfirlit/arsfjordungstolur/MAN10001.px"}, {"q"},
                                {"Sveitarfélag","-"},{"Kyn og ríkisfang", "4"}});

        inputLookup.put("Atvinnul-rvk",
                new String[][] {{"Samfelag/vinnumarkadur/vinnumarkadsrannsokn/2_arsfjordungstolur/VIN00920.px"}, {"q"},
                                {"Landsvæði","1"},{"Kyn","0"}, {"Aldur","0"}, {"Eining", "0"}});

        inputLookup.put("Atvinnul-land",
                new String[][]  {{"Samfelag/vinnumarkadur/vinnumarkadsrannsokn/2_arsfjordungstolur/VIN00920.px"}, {"q"},
                                {"Landsvæði","2"},{"Kyn","0"}, {"Aldur","0"}, {"Eining", "0"}});

        inputLookup.put("Einkaneysla",
                new String[][]  {{"Efnahagur/thjodhagsreikningar/landsframl/2_landsframleidsla_arsfj/THJ01601.px"}, {"q"},
                                {"Mælikvarði","0"},{"Skipting","0"}});

        inputLookup.put("Samneysla",
                new String[][]  {{"Efnahagur/thjodhagsreikningar/landsframl/2_landsframleidsla_arsfj/THJ01601.px"}, {"q"},
                        {"Mælikvarði","0"},{"Skipting","1"}});

        inputLookup.put("Fjarmunamyndun",
                new String[][]  {{"Efnahagur/thjodhagsreikningar/landsframl/2_landsframleidsla_arsfj/THJ01601.px"}, {"q"},
                        {"Mælikvarði","0"},{"Skipting","2"}});

        inputLookup.put("Vara-ut",
                new String[][]  {{"Efnahagur/thjodhagsreikningar/landsframl/2_landsframleidsla_arsfj/THJ01601.px"}, {"q"},
                        {"Mælikvarði","0"},{"Skipting","9"}});

        inputLookup.put("Vara-inn",
                new String[][]  {{"Efnahagur/thjodhagsreikningar/landsframl/2_landsframleidsla_arsfj/THJ01601.px"}, {"q"},
                        {"Mælikvarði","0"},{"Skipting","12"}});

        inputLookup.put("Thjonusta-ut",
                new String[][]  {{"Efnahagur/utanrikisverslun/2_thjonusta/thjonusta/UTA04005.px"}, {"q"},
                        {"Þjónustuviðskipti", "0"}, {"Flokkur", "0"}});

        inputLookup.put("Thjonusta-inn",
                new String[][]  {{"Efnahagur/utanrikisverslun/2_thjonusta/thjonusta/UTA04005.px"}, {"q"},
                        {"Þjónustuviðskipti", "1"}, {"Flokkur", "0"}});

        inputLookup.put("VLF",
                new String[][]  {{"Efnahagur/thjodhagsreikningar/landsframl/2_landsframleidsla_arsfj/THJ01601.px"}, {"q"},
                        {"Mælikvarði","0"},{"Skipting","14"}});
    }


    // Use: downloadInputData(url, name)
    // parameters: String url, A url string to send post request for data to Statistics Iceland
    //             String name, name of the time series
    // returns: forecastInput results, object containing requested time series
    public ForecastInput downloadInputData(String name) throws IOException {

        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        // Retrives url and query items for time series by name from lookup table
        String[][] queryParameters = inputLookup.get(name);
        String url = "https://px.hagstofa.is:443/pxis/api/v1/is/" + queryParameters[0][0];

        // Start of json query
        StringBuilder jsonQuery = new StringBuilder("{\"query\": [");

        // Middle of json query, regexp: ({code: X, selection: Y, filter: Z, values: R})*
        for (String[] parameter:queryParameters) {
            if(parameter.length < 2) continue;
            jsonQuery.append("{\"code\": \"" + parameter[0] +
                    "\", \"selection\": { \"filter\": \"item\"," +
                    " \"values\": [\"" + parameter[1] + "\"]}},");
        }
        // End of json query, always asks for json formated response
        jsonQuery.setLength(jsonQuery.length() - 1); // delete extra comma at end
        jsonQuery.append("],\"response\": { \"format\": \"json\"}}");

        //TODO
        // Setja inn try hér ef skil eru 404 og ekki json response.

        // post request sent to url
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

        double[] series = new double[jsonArray.length()];
        LocalDate[] time = new LocalDate[jsonArray.length()];


        // Data can be monthly, quarterly or yearly
        // dates need to be parsed accordingly
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

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject temp = jsonArray.getJSONObject(i);
            series[i] = Double.parseDouble(temp.get("values").toString()
                    .replaceAll("[\\[\"\\]]", ""));

            //System.out.println(LocalDate.parse("2020Q2", format));
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
                    //TODO Hér þarf að bæta við betri meðhöndlun á þessu
                    time[i] = LocalDate.of(1900, 1, 1);

                }
            }
        }

        input.setSeries(series);
        input.setTime(time);

        return input;

    }

    public ForecastResult generateForecast(HashMap<String, ForecastInput> forecastInput, int length,
                                           String frequency, String model, LocalDate maxMin,
                                           LocalDate minMax, int inputLen) throws ScriptException {

        ForecastResult forecastResult = new ForecastResult();
        forecastResult.setForecastModel(model);
        forecastResult.setFrequency(frequency);

        RenjinScriptEngineFactory factory = new RenjinScriptEngineFactory();
        ScriptEngine engine = factory.getScriptEngine();

        //TODO má bæta við fleiri líkönum hér - nice to have
        // færa þá allt VAR specific inn í þessa if-setningu
        //if(model.equals("var")){
        //}

        // load required R libraries
        engine.eval("library('vars')");


        // Define general variables needed in R script
        // freq, month and year are needed in ts()
        // inputLen is needed to define data.frame
        // length is needed to define length of forecast in R
        int month = maxMin.getMonthValue();
        int year = maxMin.getYear();
        int freq;
        if(frequency.equals("m")) {
            freq = 12;
        }else if(frequency.equals("q")){
            freq = 4;
            month = month/4;
        }else {
            freq = 1;
        }
        engine.put("inputLen", inputLen);
        engine.put("freq", freq);
        engine.put("len", length);

        // feed input into R process
        engine.eval("input = data.frame()[1:inputLen, ]");
        for (Map.Entry<String, ForecastInput> entry : forecastInput.entrySet()) {
            String name = entry.getKey();
            ForecastInput input = entry.getValue();

            engine.put(name,input.getSeries());
            engine.eval(name + "_ts = ts(name, start = c(" + year + "," + month + "), frequency = freq)");
            engine.eval("input[\"" + name + "\"] = " + name + "_ts");
        }

        // var estimated
        // p selected using minimum AIC
        engine.eval("p_optimum = VARselect(y, lag.max = 5, type = \"both\")$selection[0]");
        engine.eval("var = VAR(input, p = p_optimum, type = \"both\"");

        //var used to forecast
        engine.eval("forecast = predict(var, n.ahead = "+ length +", ci = 0.95)");

        // forecast moved into java and assigned to forecastResult





        return forecastResult;

    }




    public static void main() throws IOException{

    }

}
