package is.hi.hbv501GEfnahagsspa.forecastGenerator;

import is.hi.hbv501GEfnahagsspa.EfnahagsspaApplication;
import okhttp3.*;
import org.springframework.boot.SpringApplication;

import javax.persistence.ElementCollection;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


public class ForecastBuilder {


    private String forecastName;
    private Map<String, ForecastResult> forecastResult = new HashMap<>();
    private Map<String, ForecastInput> forecastInput = new HashMap<>();

    public ForecastBuilder() {
    }

    //TODO klára fyrst föllin hér að neðan, útfæra svo þennan með köllum á þau
    public ForecastBuilder(String forecastName, int length, String frequency, String model, String ... seriesName){
        this.forecastName = forecastName;

        /*
        for each series name:
            ForecastInput.put(name, thisdownloadInputData(url, name));
         */

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
    private static Map<String, String[][]> inputLookup = new HashMap<String, String[][]>();
    {
        inputLookup.put("Mannfjoldi-is",
                new String[][] {{"Ibuar/mannfjoldi/1_yfirlit/arsfjordungstolur/MAN10001.px"},
                                {"Sveitarfélag","-"},{"Kyn og ríkisfang", "3"}});

        inputLookup.put("Mannfjoldi-erl",
                new String[][] {{"Ibuar/mannfjoldi/1_yfirlit/arsfjordungstolur/MAN10001.px"},
                                {"Sveitarfélag","-"},{"Kyn og ríkisfang", "4"}});

        inputLookup.put("Atvinnul-rvk",
                new String[][] {{"Samfelag/vinnumarkadur/vinnumarkadsrannsokn/2_arsfjordungstolur/VIN00920.px"},
                                {"Landsvæði","1"},{"Kyn","0"}, {"Aldur","0"}, {"Eining", "0"}});

        inputLookup.put("Atvinnul-land",
                new String[][]  {{"Samfelag/vinnumarkadur/vinnumarkadsrannsokn/2_arsfjordungstolur/VIN00920.px"},
                                {"Landsvæði","2"},{"Kyn","0"}, {"Aldur","0"}, {"Eining", "0"}});

        inputLookup.put("Einkaneysla",
                new String[][]  {{"Efnahagur/thjodhagsreikningar/landsframl/2_landsframleidsla_arsfj/THJ01601.px"},
                                {"Mælikvarði","0"},{"Skipting","0"}});

        inputLookup.put("Samneysla",
                new String[][]  {{"Efnahagur/thjodhagsreikningar/landsframl/2_landsframleidsla_arsfj/THJ01601.px"},
                        {"Mælikvarði","0"},{"Skipting","1"}});

        inputLookup.put("Fjarmunamyndun",
                new String[][]  {{"Efnahagur/thjodhagsreikningar/landsframl/2_landsframleidsla_arsfj/THJ01601.px"},
                        {"Mælikvarði","0"},{"Skipting","2"}});

        inputLookup.put("Vara-ut",
                new String[][]  {{"Efnahagur/thjodhagsreikningar/landsframl/2_landsframleidsla_arsfj/THJ01601.px"},
                        {"Mælikvarði","0"},{"Skipting","9"}});

        inputLookup.put("Vara-inn",
                new String[][]  {{"Efnahagur/thjodhagsreikningar/landsframl/2_landsframleidsla_arsfj/THJ01601.px"},
                        {"Mælikvarði","0"},{"Skipting","12"}});

        inputLookup.put("Thjonusta-ut",
                new String[][]  {{"Efnahagur/utanrikisverslun/2_thjonusta/thjonusta/UTA04005.px"},
                        {"Þjónustuviðskipti", "0"}, {"Flokkur", "0"}});

        inputLookup.put("Thjonusta-inn",
                new String[][]  {{"Efnahagur/utanrikisverslun/2_thjonusta/thjonusta/UTA04005.px"},
                        {"Þjónustuviðskipti", "1"}, {"Flokkur", "0"}});

        inputLookup.put("VLF",
                new String[][]  {{"Efnahagur/thjodhagsreikningar/landsframl/2_landsframleidsla_arsfj/THJ01601.px"},
                        {"Mælikvarði","0"},{"Skipting","14"}});
    };


    // Use: downloadInputData(url, name)
    // parameters: String url, A url string to send post request for data to Statistics Iceland
    //             String name, name of the time series
    // returns: forecastInput results, object containing requested time series
    public Response downloadInputData(String name) throws IOException {

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

        System.out.println(jsonQuery.toString());

        RequestBody body = RequestBody.create(jsonQuery.toString(), JSON);

        Request request = new Request.Builder().url(url).post(body).build();

        Call call = client.newCall(request);
        Response response = call.execute();
        return response;

    }

    public static void main() throws IOException{
        ForecastBuilder prufa = new ForecastBuilder();
        Response response = prufa.downloadInputData("Vara-ut");
        System.out.println(response.body().string());
    }

}
