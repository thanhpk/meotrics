name := """meotrics"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

scalacOptions += "-feature"

libraryDependencies ++= Seq(
	jdbc,
	cache,
	ws,
	filters,
	"postgresql" % "postgresql" % "9.1-901-1.jdbc4",
	"com.typesafe.play" %% "anorm" % "2.4.0",
	"org.yaml" % "snakeyaml" % "1.8",
	"commons-collections" % "commons-collections" % "3.2.1",
	"org.codemonkey.simplejavamail" % "simple-java-mail" % "2.1",
	specs2 % Test
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator


fork in run := false

