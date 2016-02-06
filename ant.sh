#!/bin/sh
export ANT_OPTS="-Xms150m -Xmx150m"
export ANT_HOME=./ant
$ANT_HOME/bin/ant $*