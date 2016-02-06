#!/bin/sh
export ANT_OPTS="-Xms80m -Xmx80m"
export ANT_HOME=./ant
$ANT_HOME/bin/ant $*