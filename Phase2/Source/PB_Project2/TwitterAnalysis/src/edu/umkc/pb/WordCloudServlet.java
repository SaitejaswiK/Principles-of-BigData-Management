package edu.umkc.pb;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
 * Servlet implementation class WordCloudServlet
 */
@WebServlet("/wordcloud")
public class WordCloudServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WordCloudServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String inputFile = "/home/koushik/Desktop/Teju/Disease_Tweets.json";
		String outputFile = getServletContext().getRealPath("/") + "/word.csv";
		File f = new File(outputFile);
		BufferedWriter bw;
		FileWriter fw = null;
		SparkConf sparkConf = new SparkConf().setAppName("CuisineCounts").setMaster("local")
				.set("spark.driver.allowMultipleContexts", "true");
		JavaSparkContext ctx = new JavaSparkContext(sparkConf);
		SQLContext sc = new SQLContext(ctx);

		DataFrame d = sc.jsonFile(inputFile);
		d.registerTempTable("tweets");
		DataFrame data, data1;
		data = sc.sql("select text from tweets where text is not null");
		JavaRDD<String> words = data.toJavaRDD().flatMap(new FlatMapFunction<Row, String>() {
			@Override
			public Iterable<String> call(Row row) throws Exception {
				String s = "";
				if ((row.getString(0).contains("food") || row.getString(0).contains("Food"))) {
					s = s + " " + "Food";
				}
				if (row.getString(0).contains("health") || row.getString(0).contains("Health"))
					s = s + " " + "Health";
				if (row.getString(0).contains("positive") || row.getString(0).contains("Positive"))
					s = s + " " + "Positive";
				if (row.getString(0).contains("virus") || row.getString(0).contains("Virus"))
					s = s + " " + "Virus";
				if (row.getString(0).contains("bed") || row.getString(0).contains("Bed"))
					s = s + " " + "Bed";
				if (row.getString(0).contains("clinic") || row.getString(0).contains("Clinic"))
					s = s + " " + "Clinic";
				if (row.getString(0).contains("awarness") || row.getString(0).contains("Awarness"))
					s = s + " " + "Awarness";
				if (row.getString(0).contains("trust") || row.getString(0).contains("Trust"))
					s = s + " " + "Trust";
				if (row.getString(0).contains("patient") || row.getString(0).contains("Patient"))
					s = s + " " + "Patient";
				if (row.getString(0).contains("healthy") || row.getString(0).contains("Healthy"))
					s = s + " " + "Healthy";
				if (row.getString(0).contains("water") || row.getString(0).contains("Water"))
					s = s + " " + "Water";
				if (row.getString(0).contains("accident") || row.getString(0).contains("Accident"))
					s = s + " " + "Accident";
				if (row.getString(0).contains("ambulance") || row.getString(0).contains("Ambulance"))
					s = s + " " + "Ambulance";
				if (row.getString(0).contains("ICU") || row.getString(0).contains("icu"))
					s = s + " " + "ICU";
				if (row.getString(0).contains("insurance") || row.getString(0).contains("Insurance"))
					s = s + " " + "Insurance";
				if (row.getString(0).contains("care") || row.getString(0).contains("Care"))
					s = s + " " + "Care";
				if (row.getString(0).contains("research") || row.getString(0).contains("Research"))
					s = s + " " + "Research";
				if (row.getString(0).contains("obama") || row.getString(0).contains("Obama"))
					s = s + " " + "Obama";
				if (row.getString(0).contains("heartattack"))
                    s = s+" " +"HeartStroke";
                if (row.getString(0).contains("cancer"))
                	s = s + " " + "Cancer";
                if (row.getString(0).contains("hiv"))
                	s = s + " " + "HIV";
                if (row.getString(0).contains("aids"))
                    s = s + " " +"AIDS";
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
                if (row.getString(0).contains("breastcancer"))
                	s = s + " " + "Breast Cancer";
                if (row.getString(0).contains("lungcancer"))
                    s = s + " " +"LungCancer";
                if (row.getString(0).contains("leukemia"))
                	s = s + " " + "Leukemia";
                if (row.getString(0).contains("livercancer"))
                    s = s + " " +"LiverCancer";
                if (row.getString(0).contains("hospital"))
                    s = s + " " +"Hospital";
                if (row.getString(0).contains("heart") || row.getString(0).contains("Heart"))
                    s = s + " " +"Heart";
                if (row.getString(0).contains("doctor") || row.getString(0).contains("Doctor"))
                    s = s + " " +"Doctor";
                if (row.getString(0).contains("treatment") || row.getString(0).contains("Treatment"))
                    s = s + " " +"Treatment";
                if (row.getString(0).contains("medicines") || row.getString(0).contains("Medicines") || row.getString(0).contains("Medicine") || row.getString(0).contains("medicine"))
                    s = s + " " +"Medicines";
                if (row.getString(0).contains("Nurse") || row.getString(0).contains("nurse"))
					s = s + " " + "Nurse";
				if (row.getString(0).contains("Fruits") || row.getString(0).contains("fruits"))
					s = s + " " + "Fruits";
				if (row.getString(0).contains("Gym") || row.getString(0).contains("gym"))
					s = s + " " + "gym";
				if (row.getString(0).contains("Yoga") || row.getString(0).contains("yoga"))
					s = s + " " + "Yoga";
				if (row.getString(0).contains("Diet") || row.getString(0).contains("diet"))
					s = s + " " + "Diet";
				if (row.getString(0).contains("Calories") || row.getString(0).contains("calories"))
					s = s + " " + "Calories";
				if (row.getString(0).contains("Protein") || row.getString(0).contains("protein"))
					s = s + " " + "Protein";
				if (row.getString(0).contains("Work Out") || row.getString(0).contains("work out")
						|| row.getString(0).contains("workout") || row.getString(0).contains("work"))
					s = s + " " + "Workout";
				if (row.getString(0).contains("Nutrition") || row.getString(0).contains("nutrition"))
					s = s + " " + "Nutrition";
				if (row.getString(0).contains("Diabetes") || row.getString(0).contains("diabetes"))
					s = s + " " + "Diabetes";
				if (row.getString(0).contains("Exercise") || row.getString(0).contains("exercise"))
					s = s + " " + "Exercise";
				if (row.getString(0).contains("Fitness") || row.getString(0).contains("fitness"))
					s = s + " " + "Fitness";
				s.trim();
				return Arrays.asList(s.split(" "));
			}
		});
		JavaPairRDD<String, Integer> counts = words.mapToPair(new PairFunction<String, String, Integer>() {
			public Tuple2<String, Integer> call(String s) {
				return new Tuple2(s, 1);
			}
		});

		// Java 7 and earlier: count the words
		JavaPairRDD<String, Integer> reducedCounts = counts.reduceByKey(new Function2<Integer, Integer, Integer>() {
			public Integer call(Integer x, Integer y) {
				return x + y;
			}
		});

		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			fw = new FileWriter(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bw = new BufferedWriter(fw);
		bw.append("word,count");
		List<String> keys = reducedCounts.keys().toArray();
		List<Integer> values = reducedCounts.values().toArray();
		for (int i = 0; i < keys.size() - 1; i++) {
			bw.newLine();
			bw.append(keys.get(i) + "," + values.get(i));
			System.out.println(keys.get(i) + "," + values.get(i));
		}
		bw.flush();
		bw.close();
		ctx.stop();
		// request.setAttribute("total", total);
		RequestDispatcher rd = request.getRequestDispatcher("wordcloud.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}