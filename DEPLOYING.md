# Deploying Instructions

These instructions are based on the [instructions](http://central.sonatype.org/pages/ossrh-guide.html)
for deploying to the Central Repository using [Maven](http://central.sonatype.org/pages/apache-maven.html).
Note that this is for Spotify internal use only.

You will need the following:
- Sonatype username and password. 

You can refer to `/foss/manual/blob/master/manual.md#releasing-java-projects-to-maven-central` on 
Spotify's internal github on how to get them.

Then, you can add the following server config to your maven settings (`~/.m2/settings.xml`):
```xml
<settings>
  <servers>
    <server>
      <id>ossrh</id>
      <username>USERNAME</username>
      <password>PASSWORD</password>
    </server>
  </servers>
</settings>
```
- [GPG set up on the machine you're deploying from](http://central.sonatype.org/pages/working-with-pgp-signatures.html)

Once you've got that in place, you should be able to do deployment using the following commands:

```
# deploy snapshot version
mvn clean deploy

# make and deploy a relase
mvn -Prelease release:clean release:prepare
mvn -Prelease release:perform
```