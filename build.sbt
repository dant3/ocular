val commonSettings = Seq(
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
  settings(androidBuildAar: _*).
  settings(commonSettings: _*).
  settings(
    name := "ocular-core",
    description := "Functional Reactive Gui for Android",
    homepage := Some(url("https://github.com/dant3/ocular")),
    exportJars := true,
    libraryDependencies ++= Seq(
      "com.lihaoyi" %% "scalarx" % "0.2.8",
      "com.typesafe.akka" %% "akka-actor" % "2.3.14",
      "com.android.support" % "support-v4" % "21.0.3",
      "com.android.support" % "support-annotations" % "22.2.0"
    )
  )

lazy val examples = (project in file("example")).
  androidBuildWith(core).
  settings(commonSettings: _*).
  settings(
    libraryDependencies ++= Seq(
      "com.android.support" % "support-v4" % "21.0.3",
      "com.android.support" % "appcompat-v7" % "21.0.3",
      "com.android.support" % "design" % "22.2.0"
    ),
    name := "ocular-example",
    proguardConfig in Android ++= Seq(
      "-ignorewarnings",
      "-keep class scala.Dynamic",
      "-keepattributes Signature",
      "-keepattributes InnerClasses",
      "-keep class akka.actor.Actor$class { *; }",
      "-keep class akka.actor.LightArrayRevolverScheduler { *; }",
      "-keep class akka.actor.LocalActorRefProvider { *; }",
      "-keep class akka.actor.CreatorFunctionConsumer { *; }",
      "-keep class akka.actor.TypedCreatorFunctionConsumer { *; }",
      "-keep class akka.dispatch.BoundedDequeBasedMessageQueueSemantics { *; }",
      "-keep class akka.dispatch.UnboundedMessageQueueSemantics { *; }",
      "-keep class akka.dispatch.UnboundedDequeBasedMessageQueueSemantics { *; }",
      "-keep class akka.dispatch.DequeBasedMessageQueueSemantics { *; }",
      "-keep class akka.dispatch.MultipleConsumerSemantics { *; }",
      "-keep class akka.actor.LocalActorRefProvider$Guardian { *; }",
      "-keep class akka.actor.LocalActorRefProvider$SystemGuardian { *; }",
      "-keep class akka.dispatch.UnboundedMailbox { *; }",
      "-keep class akka.actor.DefaultSupervisorStrategy { *; }",
      "-keep class akka.event.Logging$LogExt { *; }"
    ),
    dependencyClasspath in Compile ~= { _ filterNot (_.data.getName startsWith "android-support-v4") }
  )

lazy val root = (project in file(".")).aggregate(core, examples).
  settings(
    name := "ocular"
  )
