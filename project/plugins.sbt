resolvers += "Era7 maven releases" at "https://s3-eu-west-1.amazonaws.com/releases.era7.com"
resolvers += "Jenkins repo" at "http://repo.jenkins-ci.org/public/"

addSbtPlugin("ohnosequences" % "sbt-github-release" % "0.4.0")
addSbtPlugin("me.lessis" % "bintray-sbt" % "0.3.0")
