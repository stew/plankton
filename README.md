Plankton
========

This project was created to provide alternative options to
scala.Predef as a set of default imports.



Requirements
------------

You must be using a scalac which supports `-Ysysdef` and `-Ypredef`,
right now the only released compiler which does this is
the
[typelevel](http://typelevel.org) [compiler version 2.12.1 or greater](http://typelevel.org/scala/),
although there is a ticket open with the upstream compiler to add this
feature, 
[see this pull request](https://github.com/scala/scala/pull/5350).

So at least for now, you'll need this in your `build.sbt`:

	scalacOrganization := "org.typelevel"
	scalacVersion := "2.12.1"


Quick Start
-----------

The easiest way to use plankton is to use the sbt-plugin, which will
set your scalacOptions. Add or modify a file such as `project/plguins.sbt`:

	addSbtPlugin("io.github.stew" % "sbt-plankton" % "0.0.5")

Then in your project definition (usually in `build.sbt`), you enable the
plugin, and select a `planktonFlavor`:

	enablePlugins(PlanktonPlugin)
	planktonFlavor := plankton.Zoo

There are currently two flavors offered:

- Phyto - A minimal Predef with just a safe subset of the standard Predef
- Zoo - A Predef that also includes [Typelevel Cats](https://github.com/typelevel/cats)

This feature relies on the `-Ypredef` and `-Ysysdef` scalac compiler
flags added to [Typelevel scala](http://typelevel.org/scala/). See
 for more
information.

Compiler Flags
--------------
[We trust Rob](https://tpolecat.github.io/2017/04/25/scalac-flags.html),
