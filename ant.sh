#!/bin/sh
export ANT_OPTS="-Xms200m -Xmx200m"
export ANT_HOME=./ant
$ANT_HOME/bin/ant $*