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

  val commonFlags = Seq(
    "-deprecation",                      // Emit warning and location for usages of deprecated APIs.
    "-encoding", "utf-8",                // Specify character encoding used by source files.
    "-explaintypes",                     // Explain type errors in more detail.
    "-feature",                          // Emit warning and location for usages of features that should be imported explicitly.
    "-language:existentials",            // Existential types (besides wildcard types) can be written and inferred
    "-language:experimental.macros",     // Allow macro definition (besides implementation and application)
    "-language:higherKinds",             // Allow higher-kinded types
    "-language:implicitConversions",     // Allow definition of implicit functions called views
    "-unchecked",                        // Enable additional warnings where generated code depends on assumptions.
    "-Xcheckinit",                       // Wrap field accessors to throw an exception on uninitialized access.
    "-Xfatal-warnings",                  // Fail the compilation if there are any warnings.
    "-Xfuture",                          // Turn on future language features.
    "-Xlint:adapted-args",               // Warn if an argument list is modified to match the receiver.
    "-Xlint:by-name-right-associative",  // By-name parameter of right associative operator.
    "-Xlint:constant",                   // Evaluation of a constant arithmetic expression results in an error.
    "-Xlint:delayedinit-select",         // Selecting member of DelayedInit.
    "-Xlint:doc-detached",               // A Scaladoc comment appears to be detached from its element.
    "-Xlint:inaccessible",               // Warn about inaccessible types in method signatures.
    "-Xlint:infer-any",                  // Warn when a type argument is inferred to be `Any`.
    "-Xlint:missing-interpolator",       // A string literal appears to be missing an interpolator id.
    "-Xlint:nullary-override",           // Warn when non-nullary `def f()' overrides nullary `def f'.
    "-Xlint:nullary-unit",               // Warn when nullary methods return Unit.
    "-Xlint:option-implicit",            // Option.apply used implicit view.
    "-Xlint:package-object-classes",     // Class or object defined in package object.
    "-Xlint:poly-implicit-overload",     // Parameterized overloaded implicit methods are not visible as view bounds.
    "-Xlint:private-shadow",             // A private field (or class parameter) shadows a superclass field.
    "-Xlint:stars-align",                // Pattern sequence wildcard must align with sequence component.
    "-Xlint:type-parameter-shadow",      // A local type parameter shadows a type already in scope.
    "-Xlint:unsound-match",              // Pattern match may not be typesafe.
    "-Yno-adapted-args",                 // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.
    "-Ypartial-unification",             // Enable partial unification in type constructor inference
    "-Ywarn-dead-code",                  // Warn when dead code is identified.
    "-Ywarn-inaccessible",               // Warn about inaccessible types in method signatures.
    "-Ywarn-infer-any",                  // Warn when a type argument is inferred to be `Any`.
    "-Ywarn-nullary-override",           // Warn when non-nullary `def f()' overrides nullary `def f'.
    "-Ywarn-nullary-unit",               // Warn when nullary methods return Unit.
    "-Ywarn-numeric-widen",              // Warn when numerics are widened.
    "-Ywarn-value-discard",              // Warn when non-Unit expression results are unused.
    "-Xmax-classfile-name", (255 - 15).toString // Leave some room for codecov and friends
    )

  val lightbendOnlyFlags = Seq(
    "-Ywarn-extra-implicit",             // Warn when more than one implicit parameter section is defined.
    "-Ywarn-unused:implicits",           // Warn if an implicit parameter is unused.
    "-Ywarn-unused:imports",             // Warn if an import selector is not referenced.
    "-Ywarn-unused:locals",              // Warn if a local definition is unused.
    "-Ywarn-unused:params",              // Warn if a value parameter is unused.
    "-Ywarn-unused:patvars",             // Warn if a variable bound in a pattern is unused.
    "-Ywarn-unused:privates"            // Warn if a private member is unused.
    )

  val typelevelOnlyFlags = Seq(
    // see https://github.com/typelevel/scala/blob/typelevel-readme/notes/2.12.1.md

    "-Yinduction-heuristics",            // speedd up the compilation of inductive implicit resolution
    "-Ykind-polymorphism",               // https://github.com/typelevel/scala/blob/typelevel-readme/notes/2.12.1.md#minimal-kind-polymorphism-pull5538-mandubian
    "-Yliteral-types",                   // Literals can now appear in type position (!!)
    "-Xstrict-patmat-analysis",          // providemore accurate reporting of failures of match exhaustivity for patterns with guards or extractors

    "-Ywarn-unused-import"               // Until TLS gets the above 'warn-unused:whatever' flags from the LBS section above
  )

  override def projectSettings = {
    val commonSysdef = "scala.annotation.{tailrec,implicitNotFound},scala.{deprecated,inline,transient,unchecked,volatile,Any,AnyRef,AnyVal,BigInt,BigDecimal,Boolean,Byte,Char,Double,Float,Int,Long,Nothing,PartialFunction,Product,Serializable,Short,Unit,StringContext,Option,Either,Left,Right,Some,None},java.lang.String,scala.collection.immutable._"

    val catsSysdef = ", cats._, cats.data._, cats.arrow._, cats.functor._, cats.implicits._"
    List(
      planktonFlavor := Phyto,
      resolvers += Resolver.bintrayRepo("stew", "plankton"),
      libraryDependencies ++= (planktonFlavor.value match {
        case Phyto => List("io.github.stew" %% "phyto"     % "0.0.5-SNAPSHOT")
        case Zoo =>   List("io.github.stew" %% "zoo"       % "0.0.5-SNAPSHOT",
                           "org.typelevel"  %% "cats-core" % "0.9.0")
      }),
      scalacOptions in Compile := (scalaOrganization.value match {
        case "org.typelevel" => commonFlags ++ typelevelOnlyFlags
        case _               => commonFlags ++ lightbendOnlyFlags
      }) ++ ({
        planktonFlavor.value match {
          case Phyto =>
            List("-Ysysdef", commonSysdef, "-Ypredef", "plankton.Phyto._")
          case Zoo =>
            List("-Ysysdef", commonSysdef + catsSysdef, "-Ypredef", "plankton.Zoo._")
        }
      }),

      scalacOptions in (Compile, console) ~= (
        _.filterNot(Set("-Ywarn-unused:imports",
                        "-Xfatal-warnings"))
      )
      

    )
  }

  sourceGenerators in Compile += Def.task {
    val file = (sourceManaged in Compile).value / "demo" / "Test.scala"
    IO.write(file, """object Test extends App { println("Hi") }""")
    List(file)
  }.taskValue
}
