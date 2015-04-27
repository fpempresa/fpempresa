#!/bin/sh
export ANT_OPTS=-Xms256m -Xmx300m
export ANT_HOME=./ant
$ANT_HOME/bin/ant $*