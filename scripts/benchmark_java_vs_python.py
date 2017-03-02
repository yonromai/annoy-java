#!/usr/bin/env python
"""
Benchmark speed of annoy-java vs annoy python
"""
import numpy as np
import os
import random
import shutil
import subprocess
import time
from annoy import AnnoyIndex

random.seed = 42

this_dir = os.path.dirname(os.path.realpath(__file__))
benchmark_dir = this_dir + '/../src/test/resources/benchmark'
if not os.path.exists(benchmark_dir):
    os.makedirs(benchmark_dir)


# Tree generation
dim = 40
n_vecs = 500000
n_trees = 10

print ">>> Building annoy tree..."
t = AnnoyIndex(dim)
for i in xrange(n_vecs):
    v = [random.random() for z in xrange(dim)]
    t.add_item(i, v)

t.build(n_trees)
tree_file = benchmark_dir + '/test_%d.ann' % dim
t.save(tree_file)


# Queries generation
print ">>> Generating queries..."
n_queries = 50000
query_file = '%s/%d_queries.txt' % (benchmark_dir, n_queries)
queries = random.sample(range(n_vecs), n_queries)
with open(query_file, 'w') as f:
    for query in queries:
        f.write(str(query) + '\n')


# Java perf test
print ">>> Compiling Java benchmark..."
wd = this_dir + '/..'
p = subprocess.Popen('mvn clean test', cwd=wd, stdout=subprocess.PIPE, shell=True)
p.wait()
# for stdout_line in iter(p.stdout.readline, ""):
#     print stdout_line.strip()


print ">>> Running Java benchmark..."
nns_count = 200
cmd = ('mvn exec:java -q' +
       ' -Dexec.mainClass="com.spotify.annoy.Benchmark"' +
       ' -Djava.library.path="%s/target/classes/jni"' % wd +
       ' -Dexec.classpathScope="test"' +
       ' -Dexec.args="%s"' % ' '.join([tree_file, str(dim), query_file, str(nns_count)]))
p = subprocess.Popen(cmd, cwd=wd, stdout=subprocess.PIPE, shell=True)
for stdout_line in iter(p.stdout.readline, ""):
    print stdout_line.strip()


# Python Perf
print ">>> Running Python benchmark..."

times = []
for query in queries:
    now = time.clock()
    t.get_nns_by_item(query, nns_count)
    times.append(time.clock() - now)

print "Total time:  %.5fs" % np.sum(times)
print "Mean time:   %.5fs" % np.mean(times)
print "Median time: %.5fs" % np.median(times)
print "Stddev time: %.5fs" % np.std(times)
print "Min time:    %.5fs" % np.min(times)
print "Max time:    %.5fs" % np.max(times)


# Clean up
shutil.rmtree(benchmark_dir)
