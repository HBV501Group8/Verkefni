package is.hi.hbv501GEfnahagsspa.Entities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.DeviationRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.xy.YIntervalSeries;
import org.jfree.data.xy.YIntervalSeriesCollection;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "Forecast")
@Entity
public class Forecast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private long id;

    private String forecastName;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime generatedTime;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="Forecast_ID", nullable = false)
    private List<ForecastResult> forecastResults = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="Forecast_ID")
    private List<ForecastInput> forecastInputs = new ArrayList<>();


    public Forecast() {
    }


    public String getForecastName() {
        return forecastName;
    }

    public void setForecastName(String forecastName) {
        this.forecastName = forecastName;
    }

    public LocalDateTime getGeneratedTime() { return generatedTime; }

    public void setGeneratedTime(LocalDateTime generatedTime) { this.generatedTime = generatedTime; }

    public List<ForecastResult> getForecastResults() {
        return forecastResults;
    }

    public void setForecastResults(List<ForecastResult> forecastResults) {
        this.forecastResults = forecastResults;
    }

    public List<ForecastInput> getForecastInputs() {
        return forecastInputs;
    }

    public void setForecastInputs(List<ForecastInput> forecastInputs) {
        this.forecastInputs = forecastInputs;
    }

    public JFreeChart drawForecast(String seriesName){

        // Unit data for y-axis label
        String unit = "";

        // input data
        int inputLength = forecastInputs.get(0).getSeries().length;
        double[] inputSeries = new double[inputLength];
        LocalDate[] inputTime = new LocalDate[inputLength];

        // forecast data
        int forecastLength = forecastResults.get(0).getSeries().length;
        double[] forecastSeries = new double[forecastLength];;
        double[] forecastUpper = new double[forecastLength];;
        double[] forecastLower = new double[forecastLength];;
        LocalDate[] forecastTime = new LocalDate[forecastLength];

        for(ForecastInput input:this.forecastInputs) {
            if(input.getName() == seriesName){
                inputSeries = input.getSeries();
                inputTime = input.getTime();
                unit = input.getUnit();
            }
        }

        for(ForecastResult result:this.forecastResults) {
            if(result.getName() == seriesName) {
                forecastSeries = result.getSeries();
                forecastUpper = result.getUpper();
                forecastLower = result.getLower();
                forecastTime = result.getTime();
            }
        }

        // Variables needed for chart initialization
        Font font = JFreeChart.DEFAULT_TITLE_FONT;
        ArrayList<Color> colors = new ArrayList<Color>();
        ArrayList<Stroke> strokes = new ArrayList<Stroke>();
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        float[] dash = {5.0f};

        // Series for input data - simple line plot
        XYSeriesCollection datasetInput = new XYSeriesCollection();
        XYSeries input = new XYSeries("Raungögn");
        for(int i = 0; i < inputSeries.length; i++){
            Date date = Date.from(inputTime[i].atStartOfDay(ZoneId.systemDefault()).toInstant());
            input.add(new Day(date).getLastMillisecond(), inputSeries[i]);
        }
        datasetInput.addSeries(input);

        // Series for forecast data - deviation with 95% certainty interval
        YIntervalSeriesCollection datasetForecast = new YIntervalSeriesCollection();
        YIntervalSeries forecast = new YIntervalSeries("Spá");
        for(int i = 0; i < forecastSeries.length+1; i++){
            if(i == 0) {
                Date date = Date.from(inputTime[inputSeries.length-1].atStartOfDay(ZoneId.systemDefault()).toInstant());
                forecast.add(new Day(date).getLastMillisecond(), inputSeries[inputSeries.length-1],
                        inputSeries[inputSeries.length-1], inputSeries[inputSeries.length-1]);
            }else {
                Date date = Date.from(forecastTime[i-1].atStartOfDay(ZoneId.systemDefault()).toInstant());
                forecast.add(new Day(date).getLastMillisecond(), forecastSeries[i-1], forecastLower[i-1], forecastUpper[i-1]);
            }
        }
        datasetForecast.addSeries(forecast);

        // Initialize chart
        JFreeChart chart = ChartFactory.createXYLineChart(null, null,null,
                null, PlotOrientation.VERTICAL,true,false,false);
        XYPlot plot = chart.getXYPlot();

        // Initilize and set renderer for input series
        XYLineAndShapeRenderer inputRenderer = new XYLineAndShapeRenderer();
        inputRenderer.setSeriesPaint(0,Color.BLACK);
        inputRenderer.setSeriesStroke(0, new BasicStroke(3.0f));
        inputRenderer.setSeriesShapesVisible(0, false);

        // Initialize and set renderer for forecast series
        DeviationRenderer forecastRenderer = new DeviationRenderer();
        forecastRenderer.setSeriesStroke(0, new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                                                                10.0f, dash, 0.0f));
        forecastRenderer.setSeriesPaint(0, Color.BLACK);
        forecastRenderer.setSeriesFillPaint(0, Color.LIGHT_GRAY);
        forecastRenderer.setSeriesShapesVisible(0, false);

        // Add data and renderers to chart
        plot.setDataset(0, datasetInput);
        plot.setDataset(1, datasetForecast);

        plot.setRenderer(0, inputRenderer);
        plot.setRenderer(1, forecastRenderer);

        // Add backgroundcolor and title to chart
        plot.setBackgroundPaint(Color.WHITE);

        // Add label to y-axis
        chart.getXYPlot().getRangeAxis().setLabel(unit);

        // Add dates to x-axis
        plot.setDomainAxis(new DateAxis("Tími"));

        // Add x-axis grid
        chart.getXYPlot().setDomainGridlinesVisible(true);
        chart.getXYPlot().setDomainMinorGridlinesVisible(true);
        chart.getXYPlot().setDomainGridlinePaint(Color.GRAY);

        // Add y-axis grid
        chart.getXYPlot().setRangeGridlinesVisible(true);
        chart.getXYPlot().setRangeMinorGridlinesVisible(true);
        chart.getXYPlot().setRangeGridlinePaint(Color.GRAY);


        // Add legend
        LegendTitle legend = chart.getLegend();
        legend.setItemFont(font);
        legend.setBackgroundPaint(Color.WHITE);
        legend.setFrame(new BlockBorder(Color.BLACK));


        // Add correct fonts to chart
        font = new Font("Helvetica", Font.PLAIN, 15);
        chart.getXYPlot().getDomainAxis().setLabelFont(font);
        chart.getXYPlot().getDomainAxis().setTickLabelFont(font);
        chart.getXYPlot().getRangeAxis().setLabelFont(font);
        chart.getXYPlot().getRangeAxis().setTickLabelFont(font);
        legend.setItemFont(font);

        return chart;
    }
}
