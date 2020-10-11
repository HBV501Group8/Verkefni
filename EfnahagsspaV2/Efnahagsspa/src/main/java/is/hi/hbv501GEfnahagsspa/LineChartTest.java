package is.hi.hbv501GEfnahagsspa;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;

public class LineChartTest {

    public static void main(String[] args) throws Exception {
        DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();

        DefaultCategoryDataset result = new DefaultCategoryDataset();
        for (int i = 0; i < 8; i++) {
            int rnd = getRandomNumber(0,800);
            String rndtext = rnd+"";
            result.addValue( rnd, "schools", rndtext);
        }
        JFreeChart lineChartObject = ChartFactory.createLineChart(
                "Efnahasspá","Árssfjórðunugur",
                "Vísitala",
                 result,PlotOrientation.VERTICAL,
                true,true,false);

        int width = 640;    /* Width of the image */
        int height = 480;   /* Height of the image */
        File lineChart = new File( "C:/sk/lineChart.jpeg" );
        ChartUtilities.saveChartAsJPEG(lineChart ,lineChartObject, width ,height);
    }
    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    public static String readFileAsString(String file)throws Exception
    {
        return new String(Files.readAllBytes(Paths.get(file)));
    }
}