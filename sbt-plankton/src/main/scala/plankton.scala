package plankton

import sbt._, Keys._
import scala.collection.immutable.List

trait Kingdom
case object Phyto extends Kingdom
case object Zoo extends Kingdom

object PlanktonPlugin extends AutoPlugin {

  object autoImport {
    val planktonFlavor = SettingKey[Kingdom]("plankton-flavor")
  }

  import autoImport._

  val commonFlags = List(
    "-deprecation",
    "-encoding", "UTF-8",
    "-unchecked",
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
    "-Xfuture",
    "-feature",
    "-Ywarn-adapted-args",
    "-Ywarn-inaccessible",
    "-Xmax-classfile-name", (255 - 15).toString
  )


  override def projectSettings = {
    val commonSysdef = "scala.annotation.{tailrec,implicitNotFound},scala.{deprecated,inline,transient,unchecked,volatile,Any,AnyRef,AnyVal,BigInt,BigDecimal,Boolean,Byte,Char,Double,Float,Int,Long,Nothing,PartialFunction,Product,Serializable,Short,Unit,StringContext,Option,Either,Left,Right,Some,None},java.lang.String,scala.collection.immutable._"

    val catsSysdef = ", cats._, cats.data._, cats.arrow._, cats.functor._"
    List(
      planktonFlavor := Phyto,
      resolvers += Resolver.bintrayRepo("stew", "plankton"),
      libraryDependencies ++= (planktonFlavor.value match {
        case Phyto => List("io.github.stew" %% "phyto"     % "0.0.4-SNAPSHOT")
        case Zoo =>   List("io.github.stew" %% "zoo"       % "0.0.4-SNAPSHOT",
                           "org.typelevel"  %% "cats-core" % "0.9.0")
      }),
      scalacOptions in Compile ++= {
        planktonFlavor.value match {
          case Phyto =>
            List("-Ysysdef", commonSysdef, "-Ypredef", "plankton.Phyto._")
          case Zoo =>
            List("-Ysysdef", commonSysdef + catsSysdef, "-Ypredef", "plankton.Zoo._")
        }
      }  ++ List(
        "-Xlint:adapted-args",
        "-Xlint:nullary-unit",
        "-Xlint:inaccessible",
        "-Xlint:nullary-override",
        "-Xlint:missing-interpolator",
        "-Xlint:doc-detached",
        "-Xlint:private-shadow",
        "-Xlint:type-parameter-shadow",
        "-Xlint:poly-implicit-overload",
        "-Xlint:option-implicit",
        "-Xlint:delayedinit-select",
        "-Xlint:by-name-right-associative",
        "-Xlint:package-object-classes",
        "-Xlint:unsound-match",
        "-Xlint:stars-align"
      )
    )
  }

  sourceGenerators in Compile += Def.task {
    val file = (sourceManaged in Compile).value / "demo" / "Test.scala"
    IO.write(file, """object Test extends App { println("Hi") }""")
    List(file)
  }.taskValue
}
