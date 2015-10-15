name := "akka-json-serializer"

organization := "org.notmandatory"

scalaVersion := "2.11.7"

scalacOptions := Seq("-feature", "-unchecked", "-deprecation", "-encoding", "utf8")

val akkaVersion = "2.4.0"

// akka jars
val akka = "com.typesafe.akka" %% "akka-actor" % akkaVersion
val akkaPersistence = "com.typesafe.akka" %% "akka-persistence" % akkaVersion
val akkaTestKit = "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test"

// leveldb jars
val leveldb = "org.iq80.leveldb" % "leveldb" % "0.7"
val leveldbjni = "org.fusesource.leveldbjni"   % "leveldbjni-all"   % "1.8"

// spray jar
val spray = "io.spray" %% "spray-json" % "1.3.2"

// test jars
val scalatest = "org.scalatest" %% "scalatest" % "2.2.1" % "test"
val scalacheck = "org.scalacheck" %% "scalacheck" % "1.12.2" % "test"

libraryDependencies ++= Seq(akka, akkaPersistence, akkaTestKit, leveldb, leveldbjni, spray, scalatest, scalacheck)
