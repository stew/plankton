lazy val phyto = project
  .settings(buildSettings)
  .settings(publishSettings)
  .settings(predefSettings)

val catsVersion = "0.9.1-SNAPSHOT"

lazy val zoo = project
  .settings(buildSettings)
  .settings(publishSettings)
  .settings(predefSettings)
  .settings(libraryDependencies += "org.typelevel" %% "cats" % catsVersion)

lazy val docs = project
  .settings(buildSettings)
  .settings(noPublishSettings)

lazy val buildSettings = Seq(
  scalaOrganization in ThisBuild := "org.typelevel",
  organization in Global := "io.github.stew",
  scalaVersion in ThisBuild := "2.12.1"
)

lazy val commonScalacOptions = Seq(
  "-feature",
  "-deprecation",
  "-encoding", "utf8",
  "-language:postfixOps",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked",
  "-Xcheckinit",
  "-Xfuture",
  "-Xlint",
  "-Xfatal-warnings",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-value-discard",
  "-Xfuture"
)

lazy val publishSettings = Seq(
  publishMavenStyle := true,
  bintrayOrganization := None,
  bintrayRepository := "plankton",
  bintrayVcsUrl := Some("git@github.com:stew/plankton.git"),
  licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))
)

lazy val noPublishSettings = Seq(
  publish := (),
  publishLocal := (),
  publishArtifact := false
)

lazy val plankton = project.in(file("."))
  .settings(noPublishSettings)
  .aggregate(phyto, zoo, docs)


lazy val predefSettings = Seq(
  scalacOptions in Compile ++= (commonScalacOptions :+ "-Yno-predef" :+ "-Yno-imports"),
  scalacOptions in (Test, console) ++= (commonScalacOptions ++ Seq("-Ysysdef", "-", "-Ypredef", "plankton.Phyto."))
)



