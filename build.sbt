val commonSettings = androidBuildAar ++ Seq(
  platformTarget in Android := "android-21",
  typedResources := false,

  organization := "org.ocular",
  version := "1.0.0",
  licenses += ("Apache-2.0", url("http://opensource.org/licenses/Apache-2.0")),

  scalaVersion := "2.10.5",
  crossScalaVersions := Seq("2.10.5", "2.11.7"),
  javacOptions ++= Seq("-source", "1.7", "-target", "1.7"),
  scalacOptions ++= Seq("-feature", "-deprecation", "-target:jvm-1.7"),

  libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)

lazy val core = (project in file("core")).
  settings(commonSettings: _*).
  settings(
    name := "ocular-core",
    description := "Functional Reactive Gui for Android",
    homepage := Some(url("https://github.com/dant3/ocular")),
    libraryDependencies ++= Seq(
      "com.android.support" % "support-v4" % "21.0.3"
    )
  )

lazy val root = (project in file(".")).aggregate(core).
  settings(
    name := "ocular"
  )
