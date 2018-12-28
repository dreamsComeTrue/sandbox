package app

import org.apache.spark.sql.SparkSession

object EntryPoint {
  def main(args: Array[String]): Unit = {
    val logFile = "demo_file"
    val spark = SparkSession.builder.appName("Simple Application").getOrCreate()
    val logData = spark.read.textFile(logFile).cache()

    val numAs = logData.filter(line => line.contains("a")).count()
    val numBs = logData.filter(line => line.contains("b")).count()

    println(s"Lines with a: $numAs, Lines with b: $numBs")
    println(s"Lines with a: $numAs, Lines with b: $numBs")

    val set = Set ("asdasd", "asdasd")
    
    spark.stop()

    val list = List (1, 2, 3)
    list.map (a => a * 2).foreach (x => println (x))
  }
}
