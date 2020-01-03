# Release Guide

for Confidentify Client for Java

## Account setup

Make sure you have credentials with Sonatype and that you've been granted access to publish to the `com.confidentify` namespace of the central Maven repo.

## Sonatype distribution

Check out all the information on these pages:

 * https://central.sonatype.org/pages/ossrh-guide.html
 * https://central.sonatype.org/pages/apache-maven.html

## The release command

For now we enable the `release` profile by hand. It would be nice to improve this...

```
mvn release:prepare release:perform -Darguments=-Prelease
```