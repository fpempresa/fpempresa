#!/bin/sh
export ANT_OPTS="-Xms255m -Xmx255m"
export ANT_HOME=./ant
$ANT_HOME/bin/ant $*