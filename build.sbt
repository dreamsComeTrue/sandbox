name := "scala-sandbox"

version := "0.1"

scalaVersion := "2.12.8"

//libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.3.1"
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.19"
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.19"
libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.1.1"

libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.5.19" % Test
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0-SNAP10"

libraryDependencies += "org.typelevel" %% "cats-core" % "1.6.0"

scalacOptions ++= Seq(
  "-Xfatal-warnings",
  "-Ypartial-unification"
)