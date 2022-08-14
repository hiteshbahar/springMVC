#!/bin/sh

if [ "$1" == "debug" ]; then
  mvndebug clean install tomcat7:run-war -f pom.xml
else
  mvn clean install tomcat7:run-war -f pom.xml
fi