Plankton
========

This project was created to provide alternative options to
scala.Predef as a set of default imports.


Quick Start
===========

This feature relies on the `-Ypredef` and `-Ysysdef` scalac compiler
flags added to [Typelevel scala](http://typelevel.org/scala/). See [this pull request](https://github.com/scala/scala/pull/5350) for more information.

You will need specify in your `build.sbt` that you want the typelevel
compiler instead of the lightbend compiler:

	scalaOrganization in ThisBuild := "org.typelevel",
	scalaVersion in ThisBuild := "2.12.1",

You will need to add plankton as a dependency:

	libraryDependencies += "org.typelevel" %% "plankton" % "0.0.1-SNAPSHOT"
	
And you'll need to add the approriate `-Ypredef` and `-Ysysdef` options to scalac:

	scalacOptions ++= Seq("-Ysysdef", "_",
                          "-Ypredef", "plankton.Phyto._")
