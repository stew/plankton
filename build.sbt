lazy val phyto = project
  .settings(buildSettings)
  .settings(publishSettings)
  .settings(List(scalacOptions ++= List("-Ysysdef", "scala.annotation.{tailrec,implicitNotFound},scala.{deprecated,inline,transient,unchecked,volatile,Any,AnyRef,AnyVal,BigInt,BigDecimal,Boolean,Byte,Char,Double,Float,Int,Long,Nothing,PartialFunction,Product,Serializable,Short,Unit,StringContext,Option,Either,Left,Right,Some,None},java.lang._")))

val catsVersion = "0.9.1-SNAPSHOT"

lazy val zoo = project
  .settings(buildSettings)
  .settings(publishSettings)
  .settings(List(
              scalacOptions ++= List("-Ysysdef", "scala.annotation.{tailrec,implicitNotFound},scala.{deprecated,inline,transient,unchecked,volatile,Any,AnyRef,AnyVal,BigInt,BigDecimal,Boolean,Byte,Char,Double,Float,Int,Long,Nothing,PartialFunction,Product,Serializable,Short,Unit,StringContext,Option,Either,Left,Right,Some,None},java.lang._"),
              libraryDependencies += "org.typelevel" %% "cats" % catsVersion))

lazy val `sbt-plankton` = project
  .settings(buildSettings)
  .settings(publishSettings)
  .settings(List(sbtPlugin := true,
                 scalaOrganization := "org.scala-lang",
                 scalaVersion := "2.10.6"))

lazy val docs = project
  .settings(buildSettings)
  .settings(noPublishSettings)

lazy val buildSettings = Seq(
  scalaOrganization := "org.typelevel",
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
  .aggregate(phyto, zoo, `sbt-plankton`, docs)



