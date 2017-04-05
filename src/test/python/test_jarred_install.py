import os
import subprocess
import sys

this_dir = os.path.dirname(os.path.realpath(__file__))

# Compile Java code
print ">>> Compiling annoy-java..."
wd = this_dir + '/../../..'
p = subprocess.Popen('mvn package', cwd=wd, stdout=subprocess.PIPE, shell=True)
p.wait()
if p.returncode != 0:
    for stdout_line in iter(p.stdout.readline, ""):
        print stdout_line.strip()
    print ">>> Java compilation failed, aborting benchmark."
    sys.exit(p.returncode)

p = subprocess.Popen('javac src/test/java/com/spotify/annoy/jni/base/JarredTest.java -cp target/annoy-java-*.jar', cwd=wd, stdout=subprocess.PIPE, shell=True)
p.wait()

# Testing install
print ">>> Testing Jarred Annoy..."
cmd = ('java  -cp "src/test/java:target/annoy-java-mac-0.3.0-SNAPSHOT.jar" com.spotify.annoy.jni.base.JarredTest')
p = subprocess.Popen(cmd, cwd=wd, stdout=subprocess.PIPE, shell=True)
for stdout_line in iter(p.stdout.readline, ""):
    print stdout_line.strip()
