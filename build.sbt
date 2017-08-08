organization in ThisBuild := "io.github.stew"

lazy val phyto = project
  .settings(buildSettings)
  .settings(publishSettings)
  .settings(List(scalacOptions ++= List("-Ysysdef", "scala.annotation.{tailrec,implicitNotFound},scala.{deprecated,inline,transient,unchecked,volatile,Any,AnyRef,AnyVal,BigInt,BigDecimal,Boolean,Byte,Char,Double,Float,Int,Long,Nothing,PartialFunction,Product,Serializable,Short,Unit,StringContext,Option,Either,Left,Right,Some,None},java.lang._")))

val catsVersion = "1.0.0-MF"

lazy val zoo = project
  .settings(buildSettings)
  .settings(publishSettings)
  .settings(List(
              scalacOptions ++= List("-Ysysdef", "scala.annotation.{tailrec,implicitNotFound},scala.{deprecated,inline,transient,unchecked,volatile,Any,AnyRef,AnyVal,BigInt,BigDecimal,Boolean,Byte,Char,Double,Float,Int,Long,Nothing,PartialFunction,Product,Serializable,Short,Unit,StringContext,Option,Either,Left,Right,Some,None},java.lang._"),
              libraryDependencies += "org.typelevel" %% "cats-core" % catsVersion))

lazy val `sbt-plankton` = project
  .settings(buildSettings)
  .settings(publishSettings)
  .settings(List(sbtPlugin := true,
                 scalaOrganization := "org.scala-lang",
                 scalaVersion := "2.10.6",
                 crossScalaVersions := Seq("2.10.6")))
  .enablePlugins(BuildInfoPlugin)
  .settings(
  buildInfoKeys := Seq[BuildInfoKey](version),
  buildInfoPackage := "plankton"
)



lazy val docs = project
  .settings(buildSettings)
  .settings(noPublishSettings)

lazy val buildSettings = Seq(
  scalaOrganization := "org.typelevel",
  scalaVersion := "2.12.3-bin-typelevel-4",
  crossScalaVersions := Seq("2.12.3-bin-typelevel-4", "2.11.11-bin-typelevel-4")

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

import ReleaseTransformations._


lazy val publishSettings = Seq(
  publishMavenStyle := false,
  bintrayOrganization := None,
  bintrayRepository := "plankton",
  bintrayVcsUrl := Some("git@github.com:stew/plankton.git"),
  licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html")),

  releaseCrossBuild := false,
  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    runClean,
    releaseStepCommandAndRemaining("so test"),
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    releaseStepCommandAndRemaining("so publish"),
    setNextVersion,
    commitNextVersion,
    pushChanges
  )

)



lazy val noPublishSettings = Seq(
  publish := (),
  publishLocal := (),
  publishArtifact := false
)

lazy val plankton = project.in(file("."))
  .settings(noPublishSettings)
  .aggregate(phyto, zoo, `sbt-plankton`, docs)
