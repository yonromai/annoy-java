# Deploying Instructions

These instructions are based on the [instructions](http://central.sonatype.org/pages/ossrh-guide.html)
for deploying to the Central Repository using [Maven](http://central.sonatype.org/pages/apache-maven.html).
Note that this is for Spotify internal use only.


## Deploying Snapshots to mvn central

You need a Sonatype username and password.

You can refer to `/foss/manual/blob/master/manual.md#releasing-java-projects-to-maven-central` on 
Spotify's internal github on how to get them.

You can set your environment variables:
```bash
export SONATYPE_USERNAME=foo
export SONATYPE_PASSWORD=bar
```
(if you put them in your shell rc, don't check them in...)

Then, you can add the following server config to your maven settings (`~/.m2/settings.xml`):
```xml
<settings>
  <servers>
    <server>
      <id>ossrh</id>
      <username>${env.SONATYPE_USERNAME}</username>
      <password>${env.SONATYPE_PASSWORD}</password>
    </server>
  </servers>
</settings>
```
This should enable you to push a snapshot to maven central:
```
# deploy snapshot version
mvn clean deploy
```

If you get:

`[ERROR] Failed to execute goal org.apache.maven.plugins:maven-deploy-plugin:2.7:deploy [...] ReasonPhrase: Unauthorized`,
 you've probably not set your env variables (or `settings.xml`) correctly. 

## Deploying releases to mvn central


You need GPG key set up on the machine you're deploying from.
Getting a new key is easy, [here are some instructions](http://central.sonatype.org/pages/working-with-pgp-signatures.html).

Once you've got that in place, you should be able to do deploy new releases:

```
# make and deploy a relase
mvn -Prelease release:clean release:prepare
mvn -Prelease release:perform
```