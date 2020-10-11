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

        String file = "c:/sk/json/jsontest4.json";
        String json = readFileAsString(file);
        // Debug

        Dimension dim = gson.fromJson(json, Dimension.class);

        //Debug
        Dataset test = gson.fromJson(json, Dataset.class);
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
        int teljari=0;
        for (int i = 0; i < jsonArray.length(); i++) {
            Double val = arrli.get(i);
            String str = arryearQ.get(i);
            result.addValue( val, "schools",ar+"q" + teljari + "");
            teljari++;
            if(teljari==4) {
                ar++;
                teljari=0;
            }

        }

        // Búa til graf

        JFreeChart lineChartObject = ChartFactory.createLineChart(
                "Efnahasspá","Árssfjórðunugur",
                "Vísitala",
                result,PlotOrientation.VERTICAL,
                true,true,false);

        // Laga font

        CategoryPlot p = lineChartObject.getCategoryPlot();
        CategoryAxis axis = p.getDomainAxis();
        Font font = new Font("Dialog", Font.PLAIN, 6);
        axis.setTickLabelFont(font);

        int width = 1400;    /* Width of the image */
        int height = 800;   /* Height of the image */
        File lineChart = new File( "C:/sk/lineChart.jpeg" );
        ChartUtilities.saveChartAsJPEG(lineChart ,lineChartObject, width ,height);


    }

    // Les inn skrá

    public static String readFileAsString(String file)throws Exception
    {
        return new String(Files.readAllBytes(Paths.get(file)));
    }


    public static class Application {
        String DatasetObject;


        // Getter Methods

        public String getDataset() {
            return "test";
        }

        // Setter Methods

        public void setDataset(String datasetObject ) {
            this.DatasetObject = datasetObject;
        }
    }
    public static class Dataset {
        Dimension DimensionObject;
        private String label;
        private String source = "test";
        private String updated;
        ArrayList<Object> value = new ArrayList<Object>();


        // Getter Methods

        public Dimension getDimension() {
            return DimensionObject;
        }

        public String getLabel() {
            return label;
        }

        public String getSource() {
            return source;
        }

        public String getUpdated() {
            return updated;
        }

        // Setter Methods

        public void setDimension( Dimension dimensionObject ) {
            this.DimensionObject = dimensionObject;
        }

        public void setLabel( String label ) {
            this.label = "Mælikvarði";
        }

        public void setSource( String source ) {
            this.source = source;
        }

        public void setUpdated( String updated ) {
            this.updated = updated;
        }
    }
    public static class Dimension {
        Mælikvarði MælikvarðiObject;
        Skipting SkiptingObject;
        Ársfjórðungur ÁrsfjórðungurObject;
        ArrayList<Object> id = new ArrayList<Object>();
        ArrayList<Object> size = new ArrayList<Object>();
        Role RoleObject;


        // Getter Methods

        public Mælikvarði getMælikvarði() {
            return MælikvarðiObject;
        }

        public Skipting getSkipting() {
            return SkiptingObject;
        }

        public Ársfjórðungur getÁrsfjórðungur() {
            return ÁrsfjórðungurObject;
        }

        public Role getRole() {
            return RoleObject;
        }

        // Setter Methods

        public void setMælikvarði( Mælikvarði MælikvarðiObject ) {
            this.MælikvarðiObject = MælikvarðiObject;
        }

        public void setSkipting( Skipting SkiptingObject ) {
            this.SkiptingObject = SkiptingObject;
        }

        public void setÁrsfjórðungur( Ársfjórðungur ÁrsfjórðungurObject ) {
            this.ÁrsfjórðungurObject = ÁrsfjórðungurObject;
        }

        public void setRole( Role roleObject ) {
            this.RoleObject = roleObject;
        }
    }
    public class Role {
        ArrayList<Object> time = new ArrayList<Object>();


        // Getter Methods



        // Setter Methods


    }
    public class Ársfjórðungur {
        private String label;
        Category CategoryObject;


        // Getter Methods

        public String getLabel() {
            return label;
        }

        public Category getCategory() {
            return CategoryObject;
        }

        // Setter Methods

        public void setLabel( String label ) {
            this.label = label;
        }

        public void setCategory( Category categoryObject ) {
            this.CategoryObject = categoryObject;
        }
    }
    public class Category {
        Index IndexObject;
        Label LabelObject;


        // Getter Methods

        public Index getIndex() {
            return IndexObject;
        }

        public Label getLabel() {
            return LabelObject;
        }

        // Setter Methods

        public void setIndex( Index indexObject ) {
            this.IndexObject = indexObject;
        }

        public void setLabel( Label labelObject ) {
            this.LabelObject = labelObject;
        }
    }
    public class Label {
        private String _1995Q1;
        private String _1995Q2;
        private String _1995Q3;
        private String _1995Q4;
        private String _1996Q1;
        private String _1996Q2;
        private String _1996Q3;
        private String _1996Q4;
        private String _1997Q1;
        private String _1997Q2;
        private String _1997Q3;
        private String _1997Q4;
        private String _1998Q1;
        private String _1998Q2;
        private String v1998Q3;
        private String _1998Q4;
        private String _1999Q1;
        private String _1999Q2;
        private String _1999Q3;
        private String _1999Q4;
        private String _2000Q1;
        private String _2000Q2;
        private String _2000Q3;
        private String _2000Q4;
        private String _2001Q1;
        private String _2001Q2;
        private String _2001Q3;
        private String _2001Q4;
        private String _2002Q1;
        private String _2002Q2;
        private String _2002Q3;
        private String _2002Q4;
        private String _2003Q1;
        private String _2003Q2;
        private String _2003Q3;
        private String _2003Q4;
        private String _2004Q1;
        private String _2004Q2;
        private String _2004Q3;
        private String _2004Q4;
        private String _2005Q1;
        private String _2005Q2;
        private String _2005Q3;
        private String _2005Q4;
        private String _2006Q1;
        private String _2006Q2;
        private String _2006Q3;
        private String _2006Q4;
        private String _2007Q1;
        private String _2007Q2;
        private String _2007Q3;
        private String _2007Q4;
        private String _2008Q1;
        private String _2008Q2;
        private String _2008Q3;
        private String _2008Q4;
        private String _v2009Q1;
        private String _2009Q2;
        private String _2009Q3;
        private String _2009Q4;
        private String _2010Q1;
        private String _2010Q2;
        private String _2010Q3;
        private String _2010Q4;
        private String _2011Q1;
        private String _2011Q2;
        private String _2011Q3;
        private String _2011Q4;
        private String _2012Q1;
        private String _2012Q2;
        private String _2012Q3;
        private String _2012Q4;
        private String _2013Q1;
        private String _2013Q2;
        private String _2013Q3;
        private String _2013Q4;
        private String _2014Q1;
        private String _2014Q2;
        private String _2014Q3;
        private String _2014Q4;
        private String _2015Q1;
        private String _2015Q2;
        private String _2015Q3;
        private String _2015Q4;
        private String _2016Q1;
        private String _2016Q2;
        private String _2016Q3;
        private String _2016Q4;
        private String _2017Q1;
        private String _2017Q2;
        private String _2017Q3;
        private String _2017Q4;
        private String _2018Q1;
        private String __2018Q2;
        private String _2018Q3;
        private String _2018Q4;
        private String _2019Q1;
        private String _2019Q2;
        private String _2019Q3;
        private String _2019Q4;
        private String _2020Q1;
        private String _2020Q2;


        // Getter Methods

        public String get1995Q1() {
            return _1995Q1;
        }

        public String get1995Q2() {
            return _1995Q2;
        }

        public String get1995Q3() {
            return _1995Q3;
        }

        public String get1995Q4() {
            return _1995Q4;
        }

        public String get1996Q1() {
            return _1996Q1;
        }

        public String get1996Q2() {
            return _1996Q2;
        }

        public String get1996Q3() {
            return _1996Q3;
        }

        public String get1996Q4() {
            return _1996Q4;
        }

        public String get1997Q1() {
            return _1997Q1;
        }

        public String get1997Q2() {
            return _1997Q2;
        }

        public String get1997Q3() {
            return _1997Q3;
        }

        public String get1997Q4() {
            return _1997Q4;
        }

        public String get1998Q1() {
            return _1998Q1;
        }

        public String get1998Q2() {
            return _1998Q2;
        }

        public String get1998Q3() {
            //return _1998Q3;
            return "";
        }

        public String get1998Q4() {
            return _1998Q4;
        }

        public String get1999Q1() {
            return _1999Q1;
        }

        public String get1999Q2() {
            return _1999Q2;
        }

        public String get1999Q3() {
            return _1999Q3;
        }

        public String get1999Q4() {
            return _1999Q4;
        }

        public String get2000Q1() {
            return _2000Q1;
        }

        public String get2000Q2() {
            return _2000Q2;
        }

        public String get2000Q3() {
            return _2000Q3;
        }

        public String get2000Q4() {
            return _2000Q4;
        }

        public String get2001Q1() {
            return _2001Q1;
        }

        public String get2001Q2() {
            return _2001Q2;
        }

        public String get2001Q3() {
            return _2001Q3;
        }

        public String get2001Q4() {
            return _2001Q4;
        }

        public String get2002Q1() {
            return _2002Q1;
        }

        public String get2002Q2() {
            return _2002Q2;
        }

        public String get2002Q3() {
            return _2002Q3;
        }

        public String get2002Q4() {
            return _2002Q4;
        }

        public String get2003Q1() {
            return _2003Q1;
        }

        public String get2003Q2() {
            return _2003Q2;
        }

        public String get2003Q3() {
            return _2003Q3;
        }

        public String get2003Q4() {
            return _2003Q4;
        }

        public String get2004Q1() {
            return _2004Q1;
        }

        public String get2004Q2() {
            return _2004Q2;
        }

        public String get2004Q3() {
            return _2004Q3;
        }

        public String get2004Q4() {
            return _2004Q4;
        }

        public String get2005Q1() {
            return _2005Q1;
        }

        public String get2005Q2() {
            return _2005Q2;
        }

        public String get2005Q3() {
            return _2005Q3;
        }

        public String get2005Q4() {
            return _2005Q4;
        }

        public String get2006Q1() {
            return _2006Q1;
        }

        public String get2006Q2() {
            return _2006Q2;
        }

        public String get2006Q3() {
            return _2006Q3;
        }

        public String get2006Q4() {
            return _2006Q4;
        }

        public String get2007Q1() {
            return _2007Q1;
        }

        public String get2007Q2() {
            return _2007Q2;
        }

        public String get2007Q3() {
            return _2007Q3;
        }

        public String get2007Q4() {
            return _2007Q4;
        }

        public String get2008Q1() {
            return _2008Q1;
        }

        public String get2008Q2() {
            return _2008Q2;
        }

        public String get2008Q3() {
            return _2008Q3;
        }

        public String get2008Q4() {
            return _2008Q4;
        }

        public String get2009Q1() {
         //   return _2009Q1;
            return  "";
        }

        public String get2009Q2() {
            return _2009Q2;
        }

        public String get2009Q3() {
            return _2009Q3;
        }

        public String get2009Q4() {
            return _2009Q4;
        }

        public String get2010Q1() {
            return _2010Q1;
        }

        public String get2010Q2() {
            return _2010Q2;
        }

        public String get2010Q3() {
            return _2010Q3;
        }

        public String get2010Q4() {
            return _2010Q4;
        }

        public String get2011Q1() {
            return _2011Q1;
        }

        public String get2011Q2() {
            return _2011Q2;
        }

        public String get2011Q3() {
            return _2011Q3;
        }

        public String get2011Q4() {
            return _2011Q4;
        }

        public String get2012Q1() {
            return _2012Q1;
        }

        public String get2012Q2() {
            return _2012Q2;
        }

        public String get2012Q3() {
            return _2012Q3;
        }

        public String get2012Q4() {
            return _2012Q4;
        }

        public String get2013Q1() {
            return _2013Q1;
        }

        public String get2013Q2() {
            return _2013Q2;
        }

        public String get2013Q3() {
            return _2013Q3;
        }

        public String get2013Q4() {
            return _2013Q4;
        }

        public String get2014Q1() {
            return _2014Q1;
        }

        public String get2014Q2() {
            return _2014Q2;
        }

        public String get2014Q3() {
            return _2014Q3;
        }

        public String get2014Q4() {
            return _2014Q4;
        }

        public String get2015Q1() {
            return _2015Q1;
        }

        public String get2015Q2() {
            return _2015Q2;
        }

        public String get2015Q3() {
            return _2015Q3;
        }

        public String get2015Q4() {
            return _2015Q4;
        }

        public String get2016Q1() {
            return _2016Q1;
        }

        public String get2016Q2() {
            return _2016Q2;
        }

        public String get2016Q3() {
            return _2016Q3;
        }

        public String get2016Q4() {
            return _2016Q4;
        }

        public String get2017Q1() {
            return _2017Q1;
        }

        public String get2017Q2() {
            return _2017Q2;
        }

        public String get2017Q3() {
            return _2017Q3;
        }

        public String get2017Q4() {
            return _2017Q4;
        }

        public String get2018Q1() {
            return _2018Q1;
        }

        public String get2018Q2() {
            //return _2018Q2;
            return "";
        }

        public String get2018Q3() {
            return _2018Q3;
        }

        public String get2018Q4() {
            return _2018Q4;
        }

        public String get2019Q1() {
            return _2019Q1;
        }

        public String get2019Q2() {
            return _2019Q2;
        }

        public String get2019Q3() {
            return _2019Q3;
        }

        public String get2019Q4() {
            return _2019Q4;
        }

        public String get2020Q1() {
            return _2020Q1;
        }

        public String get2020Q2() {
            return _2020Q2;
        }


/*
        // Setter Methods

        public void set1995Q1( String 1995Q1 ) {
            this._1995Q1 = _1995Q1;
        }

        public void set1995Q2( String 1995Q2 ) {
            this._1995Q2 = _1995Q2;
        }

        public void set1995Q3( String 1995Q3 ) {
            this._1995Q3 = _1995Q3;
        }

        public void set1995Q4( String 1995Q4 ) {
            this._1995Q4 = _1995Q4;
        }

        public void set1996Q1( String 1996Q1 ) {
            this._1996Q1 = _1996Q1;
        }

        public void set1996Q2( String 1996Q2 ) {
            this._1996Q2 = _1996Q2;
        }

        public void set1996Q3( String 1996Q3 ) {
            this._1996Q3 = _1996Q3;
        }

        public void set1996Q4( String 1996Q4 ) {
            this._1996Q4 = _1996Q4;
        }

        public void set1997Q1( String 1997Q1 ) {
            this._1997Q1 = _1997Q1;
        }

        public void set1997Q2( String 1997Q2 ) {
            this._1997Q2 = _1997Q2;
        }

        public void set1997Q3( String 1997Q3 ) {
            this._1997Q3 = _1997Q3;
        }

        public void set1997Q4( String 1997Q4 ) {
            this._1997Q4 = _1997Q4;
        }

        public void set1998Q1( String 1998Q1 ) {
            this._1998Q1 = _1998Q1;
        }

        public void set1998Q2( String 1998Q2 ) {
            this._1998Q2 = _1998Q2;
        }

        public void set1998Q3( String 1998Q3 ) {
            //this._1998Q3 = _1998Q3;
        }

        public void set1998Q4( String 1998Q4 ) {
            this._1998Q4 = _1998Q4;
        }

        public void set1999Q1( String 1999Q1 ) {
            this._1999Q1 = _1999Q1;
        }

        public void set1999Q2( String 1999Q2 ) {
            this._1999Q2 = _1999Q2;
        }

        public void set1999Q3( String 1999Q3 ) {
            this._1999Q3 = _1999Q3;
        }

        public void set1999Q4( String 1999Q4 ) {
            this._1999Q4 = _1999Q4;
        }

        public void set2000Q1( String 2000Q1 ) {
            this._2000Q1 = _2000Q1;
        }

        public void set2000Q2( String 2000Q2 ) {
        }

        public void set2000Q3( String 2000Q3 ) {
            this.2000Q3 = 2000Q3;
        }

        public void set2000Q4( String 2000Q4 ) {
            this.2000Q4 = 2000Q4;
        }

        public void set2001Q1( String 2001Q1 ) {
            this.2001Q1 = 2001Q1;
        }

        public void set2001Q2( String 2001Q2 ) {
            this.2001Q2 = 2001Q2;
        }

        public void set2001Q3( String 2001Q3 ) {
            this.2001Q3 = 2001Q3;
        }

        public void set2001Q4( String 2001Q4 ) {
            this.2001Q4 = 2001Q4;
        }

        public void set2002Q1( String 2002Q1 ) {
            this.2002Q1 = 2002Q1;
        }

        public void set2002Q2( String 2002Q2 ) {
            this.2002Q2 = 2002Q2;
        }

        public void set2002Q3( String 2002Q3 ) {
            this.2002Q3 = 2002Q3;
        }

        public void set2002Q4( String 2002Q4 ) {
            this.2002Q4 = 2002Q4;
        }

        public void set2003Q1( String 2003Q1 ) {
            this.2003Q1 = 2003Q1;
        }

        public void set2003Q2( String 2003Q2 ) {
            this.2003Q2 = 2003Q2;
        }

        public void set2003Q3( String 2003Q3 ) {
            this.2003Q3 = 2003Q3;
        }

        public void set2003Q4( String 2003Q4 ) {
            this.2003Q4 = 2003Q4;
        }

        public void set2004Q1( String 2004Q1 ) {
            this.2004Q1 = 2004Q1;
        }

        public void set2004Q2( String 2004Q2 ) {
            this.2004Q2 = 2004Q2;
        }

        public void set2004Q3( String 2004Q3 ) {
            this.2004Q3 = 2004Q3;
        }

        public void set2004Q4( String 2004Q4 ) {
            this.2004Q4 = 2004Q4;
        }

        public void set2005Q1( String 2005Q1 ) {
            this.2005Q1 = 2005Q1;
        }

        public void set2005Q2( String 2005Q2 ) {
            this.2005Q2 = 2005Q2;
        }

        public void set2005Q3( String 2005Q3 ) {
            this.2005Q3 = 2005Q3;
        }

        public void set2005Q4( String 2005Q4 ) {
            this.2005Q4 = 2005Q4;
        }

        public void set2006Q1( String 2006Q1 ) {
            this.2006Q1 = 2006Q1;
        }

        public void set2006Q2( String 2006Q2 ) {
            this.2006Q2 = 2006Q2;
        }

        public void set2006Q3( String 2006Q3 ) {
            this.2006Q3 = 2006Q3;
        }

        public void set2006Q4( String 2006Q4 ) {
            this.2006Q4 = 2006Q4;
        }

        public void set2007Q1( String 2007Q1 ) {
            this.2007Q1 = 2007Q1;
        }

        public void set2007Q2( String 2007Q2 ) {
            this.2007Q2 = 2007Q2;
        }

        public void set2007Q3( String 2007Q3 ) {
            this.2007Q3 = 2007Q3;
        }

        public void set2007Q4( String 2007Q4 ) {
            this.2007Q4 = 2007Q4;
        }

        public void set2008Q1( String 2008Q1 ) {
            this.2008Q1 = 2008Q1;
        }

        public void set2008Q2( String 2008Q2 ) {
            this.2008Q2 = 2008Q2;
        }

        public void set2008Q3( String 2008Q3 ) {
            this.2008Q3 = 2008Q3;
        }

        public void set2008Q4( String 2008Q4 ) {
            this.2008Q4 = 2008Q4;
        }

        public void set2009Q1( String 2009Q1 ) {
            this.2009Q1 = 2009Q1;
        }

        public void set2009Q2( String 2009Q2 ) {
            this.2009Q2 = 2009Q2;
        }

        public void set2009Q3( String 2009Q3 ) {
            this.2009Q3 = 2009Q3;
        }

        public void set2009Q4( String 2009Q4 ) {
            this.2009Q4 = 2009Q4;
        }

        public void set2010Q1( String 2010Q1 ) {
            this.2010Q1 = 2010Q1;
        }

        public void set2010Q2( String 2010Q2 ) {
            this.2010Q2 = 2010Q2;
        }

        public void set2010Q3( String 2010Q3 ) {
            this.2010Q3 = 2010Q3;
        }

        public void set2010Q4( String 2010Q4 ) {
            this.2010Q4 = 2010Q4;
        }

        public void set2011Q1( String 2011Q1 ) {
            this.2011Q1 = 2011Q1;
        }

        public void set2011Q2( String 2011Q2 ) {
            this.2011Q2 = 2011Q2;
        }

        public void set2011Q3( String 2011Q3 ) {
            this.2011Q3 = 2011Q3;
        }

        public void set2011Q4( String 2011Q4 ) {
            this.2011Q4 = 2011Q4;
        }

        public void set2012Q1( String 2012Q1 ) {
            this.2012Q1 = 2012Q1;
        }

        public void set2012Q2( String 2012Q2 ) {
            this.2012Q2 = 2012Q2;
        }

        public void set2012Q3( String 2012Q3 ) {
            this.2012Q3 = 2012Q3;
        }

        public void set2012Q4( String 2012Q4 ) {
            this.2012Q4 = 2012Q4;
        }

        public void set2013Q1( String 2013Q1 ) {
            this.2013Q1 = 2013Q1;
        }

        public void set2013Q2( String 2013Q2 ) {
            this.2013Q2 = 2013Q2;
        }

        public void set2013Q3( String 2013Q3 ) {
            this.2013Q3 = 2013Q3;
        }

        public void set2013Q4( String 2013Q4 ) {
            this.2013Q4 = 2013Q4;
        }

        public void set2014Q1( String 2014Q1 ) {
            this.2014Q1 = 2014Q1;
        }

        public void set2014Q2( String 2014Q2 ) {
            this.2014Q2 = 2014Q2;
        }

        public void set2014Q3( String 2014Q3 ) {
            this.2014Q3 = 2014Q3;
        }

        public void set2014Q4( String 2014Q4 ) {
            this.2014Q4 = 2014Q4;
        }

        public void set2015Q1( String 2015Q1 ) {
            this.2015Q1 = 2015Q1;
        }

        public void set2015Q2( String 2015Q2 ) {
            this.2015Q2 = 2015Q2;
        }

        public void set2015Q3( String 2015Q3 ) {
            this.2015Q3 = 2015Q3;
        }

        public void set2015Q4( String 2015Q4 ) {
            this.2015Q4 = 2015Q4;
        }

        public void set2016Q1( String 2016Q1 ) {
            this.2016Q1 = 2016Q1;
        }

        public void set2016Q2( String 2016Q2 ) {
            this.2016Q2 = 2016Q2;
        }

        public void set2016Q3( String 2016Q3 ) {
            this.2016Q3 = 2016Q3;
        }

        public void set2016Q4( String 2016Q4 ) {
            this.2016Q4 = 2016Q4;
        }

        public void set2017Q1( String 2017Q1 ) {
            this.2017Q1 = 2017Q1;
        }

        public void set2017Q2( String 2017Q2 ) {
            this.2017Q2 = 2017Q2;
        }

        public void set2017Q3( String 2017Q3 ) {
            this.2017Q3 = 2017Q3;
        }

        public void set2017Q4( String 2017Q4 ) {
            this.2017Q4 = 2017Q4;
        }

        public void set2018Q1( String 2018Q1 ) {
            this.2018Q1 = 2018Q1;
        }

        public void set2018Q2( String 2018Q2 ) {
            this.2018Q2 = 2018Q2;
        }

        public void set2018Q3( String 2018Q3 ) {
            this.2018Q3 = 2018Q3;
        }

        public void set2018Q4( String 2018Q4 ) {
            this.2018Q4 = 2018Q4;
        }

        public void set2019Q1( String 2019Q1 ) {
            this.2019Q1 = 2019Q1;
        }

        public void set2019Q2( String 2019Q2 ) {
            this.2019Q2 = 2019Q2;
        }

        public void set2019Q3( String 2019Q3 ) {
            this.2019Q3 = 2019Q3;
        }

        public void set2019Q4( String 2019Q4 ) {
            this.2019Q4 = 2019Q4;
        }

        public void set2020Q1( String 2020Q1 ) {
            this.2020Q1 = 2020Q1;
        }

        public void set2020Q2( String 2020Q2 ) {
            this.2020Q2 = 2020Q2;
        }
 */
    }


    public class Index { /*
        private float 1995Q1;
        private float 1995Q2;
        private float 1995Q3;
        private float 1995Q4;
        private float 1996Q1;
        private float 1996Q2;
        private float 1996Q3;
        private float 1996Q4;
        private float 1997Q1;
        private float 1997Q2;
        private float 1997Q3;
        private float 1997Q4;
        private float 1998Q1;
        private float 1998Q2;
        private float 1998Q3;
        private float 1998Q4;
        private float 1999Q1;
        private float 1999Q2;
        private float 1999Q3;
        private float 1999Q4;
        private float 2000Q1;
        private float 2000Q2;
        private float 2000Q3;
        private float 2000Q4;
        private float 2001Q1;
        private float 2001Q2;
        private float 2001Q3;
        private float 2001Q4;
        private float 2002Q1;
        private float 2002Q2;
        private float 2002Q3;
        private float 2002Q4;
        private float 2003Q1;
        private float 2003Q2;
        private float 2003Q3;
        private float 2003Q4;
        private float 2004Q1;
        private float 2004Q2;
        private float 2004Q3;
        private float 2004Q4;
        private float 2005Q1;
        private float 2005Q2;
        private float 2005Q3;
        private float 2005Q4;
        private float 2006Q1;
        private float 2006Q2;
        private float 2006Q3;
        private float 2006Q4;
        private float 2007Q1;
        private float 2007Q2;
        private float 2007Q3;
        private float 2007Q4;
        private float 2008Q1;
        private float 2008Q2;
        private float 2008Q3;
        private float 2008Q4;
        private float 2009Q1;
        private float 2009Q2;
        private float 2009Q3;
        private float 2009Q4;
        private float 2010Q1;
        private float 2010Q2;
        private float 2010Q3;
        private float 2010Q4;
        private float 2011Q1;
        private float 2011Q2;
        private float 2011Q3;
        private float 2011Q4;
        private float 2012Q1;
        private float 2012Q2;
        private float 2012Q3;
        private float 2012Q4;
        private float 2013Q1;
        private float 2013Q2;
        private float 2013Q3;
        private float 2013Q4;
        private float 2014Q1;
        private float 2014Q2;
        private float 2014Q3;
        private float 2014Q4;
        private float 2015Q1;
        private float 2015Q2;
        private float 2015Q3;
        private float 2015Q4;
        private float 2016Q1;
        private float 2016Q2;
        private float 2016Q3;
        private float 2016Q4;
        private float 2017Q1;
        private float 2017Q2;
        private float 2017Q3;
        private float 2017Q4;
        private float 2018Q1;
        private float 2018Q2;
        private float 2018Q3;
        private float 2018Q4;
        private float 2019Q1;
        private float 2019Q2;
        private float 2019Q3;
        private float 2019Q4;
        private float 2020Q1;
        private float 2020Q2;


        // Getter Methods

        public float get1995Q1() {
            return 1995Q1;
        }

        public float get1995Q2() {
            return 1995Q2;
        }

        public float get1995Q3() {
            return 1995Q3;
        }

        public float get1995Q4() {
            return 1995Q4;
        }

        public float get1996Q1() {
            return 1996Q1;
        }

        public float get1996Q2() {
            return 1996Q2;
        }

        public float get1996Q3() {
            return 1996Q3;
        }

        public float get1996Q4() {
            return 1996Q4;
        }

        public float get1997Q1() {
            return 1997Q1;
        }

        public float get1997Q2() {
            return 1997Q2;
        }

        public float get1997Q3() {
            return 1997Q3;
        }

        public float get1997Q4() {
            return 1997Q4;
        }

        public float get1998Q1() {
            return 1998Q1;
        }

        public float get1998Q2() {
            return 1998Q2;
        }

        public float get1998Q3() {
            return 1998Q3;
        }

        public float get1998Q4() {
            return 1998Q4;
        }

        public float get1999Q1() {
            return 1999Q1;
        }

        public float get1999Q2() {
            return 1999Q2;
        }

        public float get1999Q3() {
            return 1999Q3;
        }

        public float get1999Q4() {
            return 1999Q4;
        }

        public float get2000Q1() {
            return 2000Q1;
        }

        public float get2000Q2() {
            return 2000Q2;
        }

        public float get2000Q3() {
            return 2000Q3;
        }

        public float get2000Q4() {
            return 2000Q4;
        }

        public float get2001Q1() {
            return 2001Q1;
        }

        public float get2001Q2() {
            return 2001Q2;
        }

        public float get2001Q3() {
            return 2001Q3;
        }

        public float get2001Q4() {
            return 2001Q4;
        }

        public float get2002Q1() {
            return 2002Q1;
        }

        public float get2002Q2() {
            return 2002Q2;
        }

        public float get2002Q3() {
            return 2002Q3;
        }

        public float get2002Q4() {
            return 2002Q4;
        }

        public float get2003Q1() {
            return 2003Q1;
        }

        public float get2003Q2() {
            return 2003Q2;
        }

        public float get2003Q3() {
            return 2003Q3;
        }

        public float get2003Q4() {
            return 2003Q4;
        }

        public float get2004Q1() {
            return 2004Q1;
        }

        public float get2004Q2() {
            return 2004Q2;
        }

        public float get2004Q3() {
            return 2004Q3;
        }

        public float get2004Q4() {
            return 2004Q4;
        }

        public float get2005Q1() {
            return 2005Q1;
        }

        public float get2005Q2() {
            return 2005Q2;
        }

        public float get2005Q3() {
            return 2005Q3;
        }

        public float get2005Q4() {
            return 2005Q4;
        }

        public float get2006Q1() {
            return 2006Q1;
        }

        public float get2006Q2() {
            return 2006Q2;
        }

        public float get2006Q3() {
            return 2006Q3;
        }

        public float get2006Q4() {
            return 2006Q4;
        }

        public float get2007Q1() {
            return 2007Q1;
        }

        public float get2007Q2() {
            return 2007Q2;
        }

        public float get2007Q3() {
            return 2007Q3;
        }

        public float get2007Q4() {
            return 2007Q4;
        }

        public float get2008Q1() {
            return 2008Q1;
        }

        public float get2008Q2() {
            return 2008Q2;
        }

        public float get2008Q3() {
            return 2008Q3;
        }

        public float get2008Q4() {
            return 2008Q4;
        }

        public float get2009Q1() {
            return 2009Q1;
        }

        public float get2009Q2() {
            return 2009Q2;
        }

        public float get2009Q3() {
            return 2009Q3;
        }

        public float get2009Q4() {
            return 2009Q4;
        }

        public float get2010Q1() {
            return 2010Q1;
        }

        public float get2010Q2() {
            return 2010Q2;
        }

        public float get2010Q3() {
            return 2010Q3;
        }

        public float get2010Q4() {
            return 2010Q4;
        }

        public float get2011Q1() {
            return 2011Q1;
        }

        public float get2011Q2() {
            return 2011Q2;
        }

        public float get2011Q3() {
            return 2011Q3;
        }

        public float get2011Q4() {
            return 2011Q4;
        }

        public float get2012Q1() {
            return 2012Q1;
        }

        public float get2012Q2() {
            return 2012Q2;
        }

        public float get2012Q3() {
            return 2012Q3;
        }

        public float get2012Q4() {
            return 2012Q4;
        }

        public float get2013Q1() {
            return 2013Q1;
        }

        public float get2013Q2() {
            return 2013Q2;
        }

        public float get2013Q3() {
            return 2013Q3;
        }

        public float get2013Q4() {
            return 2013Q4;
        }

        public float get2014Q1() {
            return 2014Q1;
        }

        public float get2014Q2() {
            return 2014Q2;
        }

        public float get2014Q3() {
            return 2014Q3;
        }

        public float get2014Q4() {
            return 2014Q4;
        }

        public float get2015Q1() {
            return 2015Q1;
        }

        public float get2015Q2() {
            return 2015Q2;
        }

        public float get2015Q3() {
            return 2015Q3;
        }

        public float get2015Q4() {
            return 2015Q4;
        }

        public float get2016Q1() {
            return 2016Q1;
        }

        public float get2016Q2() {
            return 2016Q2;
        }

        public float get2016Q3() {
            return 2016Q3;
        }

        public float get2016Q4() {
            return 2016Q4;
        }

        public float get2017Q1() {
            return 2017Q1;
        }

        public float get2017Q2() {
            return 2017Q2;
        }

        public float get2017Q3() {
            return 2017Q3;
        }

        public float get2017Q4() {
            return 2017Q4;
        }

        public float get2018Q1() {
            return 2018Q1;
        }

        public float get2018Q2() {
            return 2018Q2;
        }

        public float get2018Q3() {
            return 2018Q3;
        }

        public float get2018Q4() {
            return 2018Q4;
        }

        public float get2019Q1() {
            return 2019Q1;
        }

        public float get2019Q2() {
            return 2019Q2;
        }

        public float get2019Q3() {
            return 2019Q3;
        }

        public float get2019Q4() {
            return 2019Q4;
        }

        public float get2020Q1() {
            return 2020Q1;
        }

        public float get2020Q2() {
            return 2020Q2;
        }

        // Setter Methods

        public void set1995Q1( float 1995Q1 ) {
            this.1995Q1 = 1995Q1;
        }

        public void set1995Q2( float 1995Q2 ) {
            this.1995Q2 = 1995Q2;
        }

        public void set1995Q3( float 1995Q3 ) {
            this.1995Q3 = 1995Q3;
        }

        public void set1995Q4( float 1995Q4 ) {
            this.1995Q4 = 1995Q4;
        }

        public void set1996Q1( float 1996Q1 ) {
            this.1996Q1 = 1996Q1;
        }

        public void set1996Q2( float 1996Q2 ) {
            this.1996Q2 = 1996Q2;
        }

        public void set1996Q3( float 1996Q3 ) {
            this.1996Q3 = 1996Q3;
        }

        public void set1996Q4( float 1996Q4 ) {
            this.1996Q4 = 1996Q4;
        }

        public void set1997Q1( float 1997Q1 ) {
            this.1997Q1 = 1997Q1;
        }

        public void set1997Q2( float 1997Q2 ) {
            this.1997Q2 = 1997Q2;
        }

        public void set1997Q3( float 1997Q3 ) {
            this.1997Q3 = 1997Q3;
        }

        public void set1997Q4( float 1997Q4 ) {
            this.1997Q4 = 1997Q4;
        }

        public void set1998Q1( float 1998Q1 ) {
            this.1998Q1 = 1998Q1;
        }

        public void set1998Q2( float 1998Q2 ) {
            this.1998Q2 = 1998Q2;
        }

        public void set1998Q3( float 1998Q3 ) {
            this.1998Q3 = 1998Q3;
        }

        public void set1998Q4( float 1998Q4 ) {
            this.1998Q4 = 1998Q4;
        }

        public void set1999Q1( float 1999Q1 ) {
            this.1999Q1 = 1999Q1;
        }

        public void set1999Q2( float 1999Q2 ) {
            this.1999Q2 = 1999Q2;
        }

        public void set1999Q3( float 1999Q3 ) {
            this.1999Q3 = 1999Q3;
        }

        public void set1999Q4( float 1999Q4 ) {
            this.1999Q4 = 1999Q4;
        }

        public void set2000Q1( float 2000Q1 ) {
            this.2000Q1 = 2000Q1;
        }

        public void set2000Q2( float 2000Q2 ) {
            this.2000Q2 = 2000Q2;
        }

        public void set2000Q3( float 2000Q3 ) {
            this.2000Q3 = 2000Q3;
        }

        public void set2000Q4( float 2000Q4 ) {
            this.2000Q4 = 2000Q4;
        }

        public void set2001Q1( float 2001Q1 ) {
            this.2001Q1 = 2001Q1;
        }

        public void set2001Q2( float 2001Q2 ) {
            this.2001Q2 = 2001Q2;
        }

        public void set2001Q3( float 2001Q3 ) {
            this.2001Q3 = 2001Q3;
        }

        public void set2001Q4( float 2001Q4 ) {
            this.2001Q4 = 2001Q4;
        }

        public void set2002Q1( float 2002Q1 ) {
            this.2002Q1 = 2002Q1;
        }

        public void set2002Q2( float 2002Q2 ) {
            this.2002Q2 = 2002Q2;
        }

        public void set2002Q3( float 2002Q3 ) {
            this.2002Q3 = 2002Q3;
        }

        public void set2002Q4( float 2002Q4 ) {
            this.2002Q4 = 2002Q4;
        }

        public void set2003Q1( float 2003Q1 ) {
            this.2003Q1 = 2003Q1;
        }

        public void set2003Q2( float 2003Q2 ) {
            this.2003Q2 = 2003Q2;
        }

        public void set2003Q3( float 2003Q3 ) {
            this.2003Q3 = 2003Q3;
        }

        public void set2003Q4( float 2003Q4 ) {
            this.2003Q4 = 2003Q4;
        }

        public void set2004Q1( float 2004Q1 ) {
            this.2004Q1 = 2004Q1;
        }

        public void set2004Q2( float 2004Q2 ) {
            this.2004Q2 = 2004Q2;
        }

        public void set2004Q3( float 2004Q3 ) {
            this.2004Q3 = 2004Q3;
        }

        public void set2004Q4( float 2004Q4 ) {
            this.2004Q4 = 2004Q4;
        }

        public void set2005Q1( float 2005Q1 ) {
            this.2005Q1 = 2005Q1;
        }

        public void set2005Q2( float 2005Q2 ) {
            this.2005Q2 = 2005Q2;
        }

        public void set2005Q3( float 2005Q3 ) {
            this.2005Q3 = 2005Q3;
        }

        public void set2005Q4( float 2005Q4 ) {
            this.2005Q4 = 2005Q4;
        }

        public void set2006Q1( float 2006Q1 ) {
            this.2006Q1 = 2006Q1;
        }

        public void set2006Q2( float 2006Q2 ) {
            this.2006Q2 = 2006Q2;
        }

        public void set2006Q3( float 2006Q3 ) {
            this.2006Q3 = 2006Q3;
        }

        public void set2006Q4( float 2006Q4 ) {
            this.2006Q4 = 2006Q4;
        }

        public void set2007Q1( float 2007Q1 ) {
            this.2007Q1 = 2007Q1;
        }

        public void set2007Q2( float 2007Q2 ) {
            this.2007Q2 = 2007Q2;
        }

        public void set2007Q3( float 2007Q3 ) {
            this.2007Q3 = 2007Q3;
        }

        public void set2007Q4( float 2007Q4 ) {
            this.2007Q4 = 2007Q4;
        }

        public void set2008Q1( float 2008Q1 ) {
            this.2008Q1 = 2008Q1;
        }

        public void set2008Q2( float 2008Q2 ) {
            this.2008Q2 = 2008Q2;
        }

        public void set2008Q3( float 2008Q3 ) {
            this.2008Q3 = 2008Q3;
        }

        public void set2008Q4( float 2008Q4 ) {
            this.2008Q4 = 2008Q4;
        }

        public void set2009Q1( float 2009Q1 ) {
            this.2009Q1 = 2009Q1;
        }

        public void set2009Q2( float 2009Q2 ) {
            this.2009Q2 = 2009Q2;
        }

        public void set2009Q3( float 2009Q3 ) {
            this.2009Q3 = 2009Q3;
        }

        public void set2009Q4( float 2009Q4 ) {
            this.2009Q4 = 2009Q4;
        }

        public void set2010Q1( float 2010Q1 ) {
            this.2010Q1 = 2010Q1;
        }

        public void set2010Q2( float 2010Q2 ) {
            this.2010Q2 = 2010Q2;
        }

        public void set2010Q3( float 2010Q3 ) {
            this.2010Q3 = 2010Q3;
        }

        public void set2010Q4( float 2010Q4 ) {
            this.2010Q4 = 2010Q4;
        }

        public void set2011Q1( float 2011Q1 ) {
            this.2011Q1 = 2011Q1;
        }

        public void set2011Q2( float 2011Q2 ) {
            this.2011Q2 = 2011Q2;
        }

        public void set2011Q3( float 2011Q3 ) {
            this.2011Q3 = 2011Q3;
        }

        public void set2011Q4( float 2011Q4 ) {
            this.2011Q4 = 2011Q4;
        }

        public void set2012Q1( float 2012Q1 ) {
            this.2012Q1 = 2012Q1;
        }

        public void set2012Q2( float 2012Q2 ) {
            this.2012Q2 = 2012Q2;
        }

        public void set2012Q3( float 2012Q3 ) {
            this.2012Q3 = 2012Q3;
        }

        public void set2012Q4( float 2012Q4 ) {
            this.2012Q4 = 2012Q4;
        }

        public void set2013Q1( float 2013Q1 ) {
            this.2013Q1 = 2013Q1;
        }

        public void set2013Q2( float 2013Q2 ) {
            this.2013Q2 = 2013Q2;
        }

        public void set2013Q3( float 2013Q3 ) {
            this.2013Q3 = 2013Q3;
        }

        public void set2013Q4( float 2013Q4 ) {
            this.2013Q4 = 2013Q4;
        }

        public void set2014Q1( float 2014Q1 ) {
            this.2014Q1 = 2014Q1;
        }

        public void set2014Q2( float 2014Q2 ) {
            this.2014Q2 = 2014Q2;
        }

        public void set2014Q3( float 2014Q3 ) {
            this.2014Q3 = 2014Q3;
        }

        public void set2014Q4( float 2014Q4 ) {
            this.2014Q4 = 2014Q4;
        }

        public void set2015Q1( float 2015Q1 ) {
            this.2015Q1 = 2015Q1;
        }

        public void set2015Q2( float 2015Q2 ) {
            this.2015Q2 = 2015Q2;
        }

        public void set2015Q3( float 2015Q3 ) {
            this.2015Q3 = 2015Q3;
        }

        public void set2015Q4( float 2015Q4 ) {
            this.2015Q4 = 2015Q4;
        }

        public void set2016Q1( float 2016Q1 ) {
            this.2016Q1 = 2016Q1;
        }

        public void set2016Q2( float 2016Q2 ) {
            this.2016Q2 = 2016Q2;
        }

        public void set2016Q3( float 2016Q3 ) {
            this.2016Q3 = 2016Q3;
        }

        public void set2016Q4( float 2016Q4 ) {
            this.2016Q4 = 2016Q4;
        }

        public void set2017Q1( float 2017Q1 ) {
            this.2017Q1 = 2017Q1;
        }

        public void set2017Q2( float 2017Q2 ) {
            this.2017Q2 = 2017Q2;
        }

        public void set2017Q3( float 2017Q3 ) {
            this.2017Q3 = 2017Q3;
        }

        public void set2017Q4( float 2017Q4 ) {
            this.2017Q4 = 2017Q4;
        }

        public void set2018Q1( float 2018Q1 ) {
            this.2018Q1 = 2018Q1;
        }

        public void set2018Q2( float 2018Q2 ) {
            this.2018Q2 = 2018Q2;
        }

        public void set2018Q3( float 2018Q3 ) {
            this.2018Q3 = 2018Q3;
        }

        public void set2018Q4( float 2018Q4 ) {
            this.2018Q4 = 2018Q4;
        }

        public void set2019Q1( float 2019Q1 ) {
            this.2019Q1 = 2019Q1;
        }

        public void set2019Q2( float 2019Q2 ) {
            this.2019Q2 = 2019Q2;
        }

        public void set2019Q3( float 2019Q3 ) {
            this.2019Q3 = 2019Q3;
        }

        public void set2019Q4( float 2019Q4 ) {
            this.2019Q4 = 2019Q4;
        }

        public void set2020Q1( float 2020Q1 ) {
            this.2020Q1 = 2020Q1;
        }

        public void set2020Q2( float 2020Q2 ) {
            this.2020Q2 = 2020Q2;
        }
    */
    }
    public class Skipting {
        private String label;
        Category CategoryObject;


        // Getter Methods

        public String getLabel() {
            return label;
        }

        public Category getCategory() {
            return CategoryObject;
        }

        // Setter Methods

        public void setLabel( String label ) {
            this.label = label;
        }

        public void setCategory( Category categoryObject ) {
            this.CategoryObject = categoryObject;
        }
    }
    public class Category2 {
        Index IndexObject;
        Label LabelObject;


        // Getter Methods

        public Index getIndex() {
            return IndexObject;
        }

        public Label getLabel() {
            return LabelObject;
        }

        // Setter Methods

        public void setIndex( Index indexObject ) {
            this.IndexObject = indexObject;
        }

        public void setLabel( Label labelObject ) {
            this.LabelObject = labelObject;
        }
    }
    public class Label3 {
        private String _14;


        // Getter Methods

        public String get14() {
            return _14;
        }

        // Setter Methods

        public void set14( String _14 ) {
//            this._14 = 14;
        }
    }
    public class Index3 {
        private float _14;


        // Getter Methods

        public float get14() {
            return 14;
        }

        // Setter Methods

        public void set14( float _14 ) {
            this._14 = 14;
        }
    }
    public class Mælikvarði {
        private String label;
        Category CategoryObject;


        // Getter Methods

        public String getLabel() {
            return label;
        }

        public Category getCategory() {
            return CategoryObject;
        }

        // Setter Methods

        public void setLabel( String label ) {
            this.label = label;
        }

        public void setCategory( Category categoryObject ) {
            this.CategoryObject = categoryObject;
        }
    }
    public class Category3 {
        Index IndexObject;
        Label LabelObject;


        // Getter Methods

        public Index getIndex() {
            return IndexObject;
        }

        public Label getLabel() {
            return LabelObject;
        }

        // Setter Methods

        public void setIndex( Index indexObject ) {
            this.IndexObject = indexObject;
        }

        public void setLabel( Label labelObject ) {
            this.LabelObject = labelObject;
        }
    }
    public class Label4 {
        private String _7;


        // Getter Methods

        public String get7() {
            return _7;
        }

        // Setter Methods

        public void set7( String _7 ) {
            this._7 = _7;
        }
    }
    public class Index4 {
        private float _7;


        // Getter Methods

        public float get7() {
            return 7;
        }

        // Setter Methods

        public void set7( float _7 ) {
            this._7 = 7;
        }
    }
}
