#!/bin/sh

#Generating java docs
mvn clean javadoc:javadoc

if [ "$1" == "debug" ]; then
  mvndebug clean install spring-boot:run -f pom.xml
else
  mvn clean install spring-boot:run -f pom.xml
fi