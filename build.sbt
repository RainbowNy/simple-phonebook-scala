name := "Phonebook"
 
version := "1.0" 
      
lazy val `phonebook` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"

resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"
      
scalaVersion := "2.12.2"

libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice )
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.9.0"

PlayKeys.devSettings := Seq("play.server.http.idleTimeout" -> "infinite")

      