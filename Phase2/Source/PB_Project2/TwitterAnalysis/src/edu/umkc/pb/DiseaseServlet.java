package edu.umkc.pb;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;

import scala.Tuple2;

/**
 * Servlet implementation class WorkoutServlet
 */
@WebServlet("/diseasecnt")
public class DiseaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DiseaseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String inputFile = "/home/koushik/Desktop/Teju/Disease_Tweets.json";
        SparkConf sparkConf = new SparkConf().setAppName("WorkoutCounts").setMaster("local").set("spark.driver.allowMultipleContexts", "true");
        JavaSparkContext ctx = new JavaSparkContext(sparkConf);
        SQLContext sc = new SQLContext(ctx);
        System.out.println(inputFile);
        @SuppressWarnings("deprecation")
		DataFrame d = sc.jsonFile(inputFile);
        d.registerTempTable("tweets");
        DataFrame data = sc.sql("SELECT text from tweets where text like '%heartattack%' "
        		+ "or text like '%cancer%' "
        		+ "or text like '%hiv%' "
        		+ "or text like '%aids%' "
        		+ "or text LIKE '%diabetes%'"
        		+ "or  text like '%tuberculosis%' "
        		+ "or text like '%braintumour%' "
        		+ "or text like '%malaria%'"
        		+ "or text like '%dengue%' "
        		+ "or text like '%asthma%' "
        		+ "or text like '%chickenpox%'");
        long total = data.count();
        System.out.println(total);
        JavaRDD<String> words = data.toJavaRDD().flatMap(
                new FlatMapFunction<Row, String>() {
                    @Override
                    public Iterable<String> call(Row row) throws Exception {
                        String s = "";
                        
                           if (row.getString(0).contains("heartattack"))
                                s = s+" " +"HeartStroke";
                            if (row.getString(0).contains("cancer"))
                            	s = s + " " + "Cancer";
                            if (row.getString(0).contains("hiv"))
                            	s = s + " " + "HIV";
                            if (row.getString(0).contains("aids"))
                                s = s + " " +"AIDS";
                            if (row.getString(0).contains("diabetes"))
                                s = s+" " +"Diabetes";
                            if (row.getString(0).contains("tuberculosis"))
                            	s = s + " " + "Tuberculosis";
                            if (row.getString(0).contains("malaria"))
                            	s = s + " " + "Malaria";
                            if (row.getString(0).contains("dengue"))
                                s = s + " " +"Dengue";
                            if (row.getString(0).contains("asthma"))
                            	s = s + " " + "Asthma";
                            if (row.getString(0).contains("chickenpox"))
                                s = s + " " +"ChickenPox";

                        s=s.trim();
                        return Arrays.asList(s.split(" "));
                    }
                }
        );
        JavaPairRDD<String, Integer> counts = words.mapToPair(
                new PairFunction<String, String, Integer>(){
                    public Tuple2<String, Integer> call(String s){
                        return new Tuple2(s, 1);
                    }
                } );

        // Java 7 and earlier: count the words
        JavaPairRDD<String, Integer> reducedCounts = counts.reduceByKey(
                new Function2<Integer, Integer, Integer>(){
                    public Integer call(Integer x, Integer y){ return x + y; }
                } );
        
        List<String> keys = reducedCounts.keys().toArray();
        List<Integer> values = reducedCounts.values().toArray();
        ctx.stop();
        request.setAttribute("total", total);
        request.setAttribute("keys", keys);
        request.setAttribute("values", values);
        RequestDispatcher rd = request.getRequestDispatcher("workout.jsp");
        rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
