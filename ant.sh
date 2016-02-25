#!/bin/sh
export ANT_OPTS="-Xms75m -Xmx75m"
export ANT_HOME=./ant
$ANT_HOME/bin/ant $*
