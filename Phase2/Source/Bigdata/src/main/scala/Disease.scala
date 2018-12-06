
import org.apache.spark.{SparkConf, SparkContext}

// For implicit conversions from RDDs to DataFrames

object Disease {
  def main(args: Array[String]) {

    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")

    val sc = new SparkContext(sparkConf)

    // Contains SQLContext which is necessary to execute SQL queries
    val sqlContext = new org.apache.spark.sql.SQLContext(sc)

    // Reads json file and stores in a variable
    val tweet = sqlContext.read.json("/home/koushik/Desktop/Teju/Disease_Tweets.json")

    //To register tweets data as a table
    tweet.createOrReplaceTempView("tweets")

    val disCat = sqlContext.sql("SELECT user.name as UserName,user.location as loc,text,created_at," +
      "CASE WHEN text like '%heartattack%' THEN 'HEART STROKE'" +
      "WHEN text like '%cancer%' THEN 'CANCER'" +
      "WHEN text like '%hiv%' THEN 'HIV'" +
      "WHEN text like '%aids%' THEN 'AIDS'" +
      "WHEN text LIKE '%diabetes%' THEN 'DIABETES'" +
      "WHEN text like '%tuberculosis%' THEN 'TUBERCULOSIS'" +
      "WHEN text like '%braintumour%' THEN 'BRAIN TUMOUR'" +
      "WHEN text like '%malaria%' THEN 'MALARIA'" +
      "WHEN text like '%dengue%' THEN 'DENGUE'" +
      "WHEN text like '%asthma%' THEN 'ASTHMA'" +
      "WHEN text like '%chickenpox%' THEN 'CHICKENPOX'" +
      "END AS diseaseType from tweets where text is not null")

    disCat.createOrReplaceTempView("disCat2")

    println("Enter any one of the following query to get data")
    println("1.Query-1:Which disease has more tweets")
    println("2.Query-2:Which user tweeted more on which disease")
    println("3.Query-3:Which state tweeted more on which disease")
    println("4.Query-4:On which day more tweets are done")
    println("5.Query-5:Compare disease hashtags with blackboard tags")
    println("6.Query-6:Users with most sensitive tweets")
    println("7.Query-7:Popular languages used for tweeting tweets about Diseases")
    println("8.Query-8:Account verification Tweets")
    println("9.Query-9:Top Tweet text and Retweet count")
    println("10.Query-10:Users created per year")
    println("Enter any one of the following query to get data:")
    val count = scala.io.StdIn.readLine()
    count match {
      case "1" =>
        /*--------------------Query 1: This query fetches the diseases and its popularity based on tweets-----------------------*/
        val textFile = sc.textFile("/home/koushik/Desktop/Teju/Disease_Tweets.json")
        val heartattack = (textFile.filter(line => line.contains("#heartattack")).count())
        val cancer = (textFile.filter(line => line.contains("#cancer")).count())
        val hiv = (textFile.filter(line => line.contains("#hiv")).count())
        val aids = (textFile.filter(line => line.contains("#aids")).count())
        val diabetes = (textFile.filter(line => line.contains("#diabetes")).count())
        val tuberculosis = (textFile.filter(line => line.contains("#tuberculosis")).count())
        val braintumour = (textFile.filter(line => line.contains("#braintumour")).count())
        val malaria = (textFile.filter(line => line.contains("#malaria")).count())
        val dengue = (textFile.filter(line => line.contains("#dengue")).count())
        val asthma = (textFile.filter(line => line.contains("#asthma")).count())
        val chickenpox = (textFile.filter(line => line.contains("#chickenpox")).count())

        println("********************************************")
        println("Number of users tweeted on different disease")
        println("********************************************")
        println("Heart Attack : %s".format(heartattack))
        println("Cancer : %s".format(cancer))
        println("HIV : %s".format(hiv))
        println("AIDS : %s".format(aids))
        println("DIABETES : %s".format(diabetes))
        println("TUBERCULOSIS : %s".format(tuberculosis))
        println("BRAIN TUMOUR : %s".format(braintumour))
        println("MALARIA : %s".format(malaria))
        println("DENGUE : %s".format(dengue))
        println("ASTHMA : %s".format(asthma))
        println("CHICKENPOX : %s".format(chickenpox))

      /*-----------------------------Query 2:  User who tweeted most on which Disease--------------------------------------------*/
      case "2" =>

        val r1 = sqlContext.sql("SELECT UserName,'HEART STROKE' as diseaseType,count(*) as count FROM disCat2 WHERE diseaseType='HEART STROKE' " +
          "group by UserName order by count desc limit 1")
        val r2 = sqlContext.sql("SELECT UserName,'CANCER' as diseaseType,count(*) as count FROM disCat2 WHERE diseaseType='CANCER' " +
          "group by UserName order by count desc limit 1 ")
        val r3 = sqlContext.sql("SELECT UserName,'HIV' as diseaseType,count(*) as count FROM disCat2 WHERE diseaseType='HIV' " +
          "group by UserName order by count desc limit 1 ")
        val r4 = sqlContext.sql("SELECT UserName,'AIDS' as diseaseType,count(*) as count FROM disCat2 WHERE diseaseType='AIDS' " +
          "group by UserName order by count desc limit 1 ")
        val r5 = sqlContext.sql("SELECT UserName,'DIABETES' as diseaseType,count(*) as count FROM disCat2 WHERE diseaseType='DIABETES' " +
          "group by UserName order by count desc limit 1 ")
        val r6 = sqlContext.sql("SELECT UserName,'TUBERCULOSIS' as diseaseType,count(*) as count FROM disCat2 WHERE diseaseType='TUBERCULOSIS' " +
          "group by UserName order by count desc limit 1 ")
        val r7 = sqlContext.sql("SELECT UserName,'BRAIN TUMOUR' as diseaseType,count(*) as count FROM disCat2 WHERE diseaseType='BRAIN TUMOUR' " +
          "group by UserName order by count desc limit 1 ")
        val r8 = sqlContext.sql("SELECT UserName,'MALARIA' as diseaseType,count(*) as count FROM disCat2 WHERE diseaseType='MALARIA' " +
          "group by UserName order by count desc limit 1 ")
        val r9 = sqlContext.sql("SELECT UserName,'DENGUE' as diseaseType,count(*) as count FROM disCat2 WHERE diseaseType='DENGUE' " +
          "group by UserName order by count desc limit 1")
        val r10 = sqlContext.sql("SELECT UserName,'ASTHMA' as diseaseType,count(*) as count FROM disCat2 WHERE diseaseType='ASTHMA' " +
          "group by UserName order by count desc limit 1")
        val r11 = sqlContext.sql("SELECT UserName,'CHICKENPOX' as diseaseType,count(*) as count FROM disCat2 WHERE diseaseType='CHICKENPOX' " +
          "group by UserName order by count desc limit 1 ")

        val rdd1 = r1.union(r2).union(r3).union(r4).union(r5).union(r6).union(r7).union(r8).union(r9) union (r10).union(r11)

        println("****************************************")
        println("Which user tweeted more on which Disease")
        println("****************************************")
        rdd1.show()

      //TopTweets.collect().foreach(println)
      //TopTweets.write.format("com.databricks.spark.csv").option("header", "true").save("C:\\Users\\nikky\\Desktop\\pbproject\\TopTweetsBySports.csv")

      /*-----------------------------------Query 3: US states with more popular Diseases-------------------------------------*/
      case "3" =>
        val stateWiseCnt = sqlContext.sql(
          """ SELECT Case
            |when user.location LIKE '%USA%' then 'United States'
            |when user.location LIKE '%India%' then 'India'
            |when user.location LIKE '%Germany%' then 'Germany'
            |when user.location LIKE '%Pakistan%' then 'Pakistan'
            |when user.location LIKE '%Australia%' then 'Australia'
            |when user.location LIKE '%France%' then 'France'
            |when user.location LIKE '%United Kingdom%' then 'United Kingdom'
            |when user.location LIKE '%Canada%' then 'Canada'
            |when user.location LIKE '%Spain%' then 'Spain'
            |when user.location LIKE '%Indonesia%' then 'Indonesia'
            |when user.location LIKE '%Mexico%' then 'Mexico'
            |when user.location LIKE '%Cameroon%' then 'Cameroon'
            |when user.location LIKE '%Argentina%' then 'Argentina'
            |when user.location LIKE '%South Africa%' then 'South Africa'
            |when user.location LIKE '%Nigeria%' then 'Nigeria'
            |when user.location LIKE '%Colombia%' then 'Colombia'
            |when user.location LIKE '%Malaysia%' then 'Malaysia'
            |when user.location LIKE '%Brazil%' then 'Brazil'
            |when user.location LIKE '%Philippines%' then 'Philippines'
            |when user.location LIKE '%Austria%' then 'Austria'
            |when user.location LIKE '%Venezuela%' then 'venezuela'
            |when user.location LIKE '%Netherlands%' then 'Netherlands'
            | end as US_State,text from tweets where text is not null""".stripMargin)
        stateWiseCnt.createOrReplaceTempView("stateWiseDataCnt")

        val stateWiseDataCnt = sqlContext.sql("select US_State, count(text) as State_Tweet_Count " +
            "from stateWiseDataCnt where US_State is not null " +
            "and text is not null group by US_State,text order by count(text) desc")

        println("*****************************************")
        println("Which country Tweeted More On Which Disease")
        println("*****************************************")
        stateWiseDataCnt.show();
      //stateSportType2.collect().foreach(println)
      //stateSportType2.write.format("com.databricks.spark.csv").option("header", "true").save("C:\\Users\\nikky\\Desktop\\pbproject\\SportsByState.csv")
      /*-------------------------------Query 4 : On which Day More Tweets are done-----------------------------------*/
      case "4" =>
        val day_data = sqlContext.sql("SELECT substring(user.created_at,1,3) as day from tweets where text is not null")

        day_data.createOrReplaceTempView("day_data")

        val days_final = sqlContext.sql(
          """ SELECT Case
            |when day LIKE '%Mon%' then 'MONDAY'
            |when day LIKE '%Tue%' then 'TUESDAY'
            |when day LIKE '%Wed%' then 'WEDNESDAY'
            |when day LIKE '%Thu%' then 'THURSDAY'
            |when day LIKE '%Fri%' then 'FRIDAY'
            |when day LIKE '%Sat%' then 'SATURDAY'
            |when day LIKE '%Sun%' then 'SUNDAY'
            | else
            | null
            | end as day1 from day_data where day is not null""".stripMargin)

        days_final.createOrReplaceTempView("days_final")

        val res = sqlContext.sql("SELECT day1 as Day,Count(*) as Day_Count from days_final where day1 is not null group by day1 order by count(*) desc")

        println ("**********************************")
        println("On Which Day More Tweets Were Done")
        println("**********************************")
        res.show()

      /*-------------------------------Query 5 : Blackboard Hash Tags Join -----------------------------------*/
      case "5" =>
        val hashtag = sqlContext.read.json(
          "/home/koushik/Desktop/Teju/Blackboard Tweets.txt")
        //To register tweets data as a table

        //hashtag.createOrReplaceTempView("hashtable")
        val hasdf = hashtag.toDF().withColumnRenamed("_Corrupt_Record", "name")
        hasdf.createOrReplaceTempView("hashtag")
        val query = sqlContext.sql(
          "SELECT t.text as Text,d.name as HashTag from tweets t JOIN hashtag d ON t.text like CONCAT('%', d.name, '%')")
        println("************************************************")
        println("Same Hash Tags from Tweets and Blackboard Tweets")
        println("************************************************")
        query.show()

        /*-----------------------Query 6: Users with most sensitive tweet numbers----------------------------------*/
      case "6" =>
        val df = sqlContext.read.json(
          "/home/koushik/Desktop/Teju/Disease_Tweets.json")
        df.createOrReplaceTempView("tweets")
        val query = sqlContext.sql(
          "select user.name,count(user.name) as no_of_sensitive_tweets from tweets where possibly_sensitive=true and user.lang='en' group by user.name order by no_of_sensitive_tweets desc limit 10")
        println("************************************************")
        println("Users with Most Sensitive Tweet Numbers")
        println("************************************************")
        query.show()

        /*---------------------Query 7: Popular languages used for tweeting tweets about Diseases--------------------------*/
      case "7" =>
        val langWstCount = sqlContext.sql("SELECT distinct id," +
          "CASE when user.lang LIKE '%en%' then 'English'"+
          "when user.lang LIKE '%ja%' then 'Japanese'"+
          "when user.lang LIKE '%es%' then 'Spanish'"+
          "when user.lang LIKE '%fr%' then 'French'"+
          "when user.lang LIKE '%it%' then 'Italian'"+
          "when user.lang LIKE '%ru%' then 'Russian'"+
          "when user.lang LIKE '%ar%' then 'Arabic'"+
          "when user.lang LIKE '%bn%' then 'Bengali'"+
          "when user.lang LIKE '%cs%' then 'Czech'"+
          "when user.lang LIKE '%da%' then 'Danish'"+
          "when user.lang LIKE '%de%' then 'German'"+
          "when user.lang LIKE '%el%' then 'Greek'"+
          "when user.lang LIKE '%fa%' then 'Persian'"+
          "when user.lang LIKE '%fi%' then 'Finnish'"+
          "when user.lang LIKE '%fil%' then 'Filipino'"+
          "when user.lang LIKE '%he%' then 'Hebrew'"+
          "when user.lang LIKE '%hi%' then 'Hindi'"+
          "when user.lang LIKE '%hu%' then 'Hungarian'"+
          "when user.lang LIKE '%id%' then 'Indonesian'"+
          "when user.lang LIKE '%ko%' then 'Korean'"+
          "when user.lang LIKE '%msa%' then 'Malay'"+
          "when user.lang LIKE '%nl%' then 'Dutch'"+
          "when user.lang LIKE '%no%' then 'Norwegian'"+
          "when user.lang LIKE '%pl%' then 'Polish'"+
          "when user.lang LIKE '%pt%' then 'Portuguese'"+
          "when user.lang LIKE '%ro%' then 'Romanian'"+
          "when user.lang LIKE '%sv%' then 'Swedish'"+
          "when user.lang LIKE '%th%' then 'Thai'"+
          "when user.lang LIKE '%tr%' then 'Turkish'"+
          "when user.lang LIKE '%uk%' then 'Ukrainian'"+
          "when user.lang LIKE '%ur%' then 'Urdu'"+
          "when user.lang LIKE '%vi%' then 'Vietnamese'"+
          "when user.lang LIKE '%zh-cn%' then 'Chinese (Simplified)'"+
          "when user.lang LIKE '%zh-tw%' then 'Chinese (Traditional)'"+
          "END AS language from tweets where text is not null")
        langWstCount.createOrReplaceTempView("langWstCount")
        var langWstDataCount=sqlContext.sql("SELECT language, Count(language) as Count from langWstCount where id is NOT NULL and language is not null group by language order by Count DESC")
        langWstDataCount.show()

        /*-----------------------------Query 8: Account verification Tweets-----------------------------------------------*/
      case "8" =>
        val acctVerify=sqlContext.sql("SELECT distinct id, " +
          "CASE when user.verified LIKE '%true%' THEN 'VERIFIED ACCOUNT'"+
          "when user.verified LIKE '%false%' THEN 'NON-VERIFIED ACCOUNT'"+
          "END AS Verified from tweets where text is not null")
        acctVerify.createOrReplaceTempView("acctVerify")
        var acctVerifydata=sqlContext.sql("SELECT  Verified, Count(Verified) as Count from acctVerify where id is NOT NULL and Verified is not null group by Verified order by Count DESC")

        println("************************************************")
        println("Account verification Tweets")
        println("************************************************")
        acctVerifydata.show()

        /*----------------------------Query 9: Top Tweet text and Retweet count----------------------------------------*/
      case "9" =>
        val SQLquery = sqlContext.sql("SELECT user.name as Name,retweeted_status.text AS Retweet_Text,retweeted_status.retweet_count AS Retweet_Count FROM tweets WHERE retweeted_status.retweet_count IS NOT NULL ORDER BY retweeted_status.retweet_count DESC")

        println("************************************************")
        println("Top Tweet text and Retweet count")
        println("************************************************")
        SQLquery.show()

      /*----------------------------Query 10: Users created per year----------------------------------------*/
      case "10" =>
        val SQLquery = sqlContext.sql("SELECT substring(user.created_at,27,4) as year,count(*) from tweets where user.created_at is not null group by substring(user.created_at,27,4) order by count(1) desc")

        println("************************************************")
        println("Users created per year")
        println("************************************************")
        SQLquery.show()
    }
  }
}

