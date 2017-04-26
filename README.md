Plankton
========

This project was created to provide alternative options to
scala.Predef as a set of default imports.


Quick Start
===========

The easiest way to use plankton is to use the sbt-plugin, which will
set your scalacOptions. Add or modify a file such as `project/plguins.sbt`:

	addSbtPlugin("io.github.stew" % "sbt-plankton" % "0.0.4")

Then in your project definition (usually in build.sbt), you enable the
plugin, and select a `planktonFlavor`:

	enablePlugins(PlanktonPlugin)
	planktonFlavor := plankton.Zoo

There are currently two flavors offered:

- Phyto - A minimal Predef with just a safe subset of the standard Predef
- Zoo - A Predef that also includes [Typelevel Cats](https://github.com/typelevel/cats)

This feature relies on the `-Ypredef` and `-Ysysdef` scalac compiler
flags added to [Typelevel scala](http://typelevel.org/scala/). See
[this pull request](https://github.com/scala/scala/pull/5350) for more
information.

