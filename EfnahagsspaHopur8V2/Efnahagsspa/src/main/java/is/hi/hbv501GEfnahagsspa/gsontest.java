package is.hi.hbv501GEfnahagsspa;

import com.google.gson.Gson;
import java.awt.*;
import java.io.File;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class gsontest {

    public static void main(String[] args) throws Exception {

        // Nota GSON

        Gson gson = new Gson();

        //JSON með ártali frá 2010

        String file = "jsontest5.json";
        String json = readFileAsString(file);
        // Debug

//        Dimension dim = gson.fromJson(json, Dimension.class);

        //Debug
  //      Dataset test = gson.fromJson(json, Dataset.class);
        String jsonDataString=json;

        // Ná í VAlue fylkið

        JSONObject jo = new JSONObject(json);
        JSONObject joParams = jo.getJSONObject("dataset");
        JSONArray jsonArray = joParams.getJSONArray("value");

        // Ítra inn niðurstöðum

        ArrayList<Double> arrli = new ArrayList<Double>(150);
        ArrayList<String> arryearQ = new ArrayList<String>(150);

        // legend á grafi

        for(int i = 0 ; i < jsonArray.length(); i++) {
            arrli.add((Double) jsonArray.get(i));
            arryearQ.add(i+"");
        }

        // Búa til dataset

        DefaultCategoryDataset result = new DefaultCategoryDataset();
        int ar=2010;
        int teljari=1;
        for (int i = 0; i < jsonArray.length(); i++) {
            Double val = arrli.get(i);
            String str = arryearQ.get(i);
            result.addValue( val, "schools",ar+"Q" + teljari + "");
            teljari++;
            if(teljari>4) {
                ar++;
                teljari=1;
            }

        }

        // Búa til graf

        JFreeChart lineChartObject = ChartFactory.createLineChart(
                "Efnahagsspá","Ársfjórðunugur",
                "Upphæð",
                result,PlotOrientation.VERTICAL,
                true,true,false);

        // Laga font

        CategoryPlot p = lineChartObject.getCategoryPlot();
        CategoryAxis axis = p.getDomainAxis();
        Font font = new Font("Dialog", Font.BOLD, 9);
        axis.setTickLabelFont(font);

        int width = 1400;    /* Width of the image */
        int height = 800;   /* Height of the image */
        File lineChart = new File( "Efnahagsspa/images/lineChart.jpeg" );
        ChartUtilities.saveChartAsJPEG(lineChart ,lineChartObject, width ,height);


    }

    // Les inn skrá

    public static String readFileAsString(String file)throws Exception
    {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

}
