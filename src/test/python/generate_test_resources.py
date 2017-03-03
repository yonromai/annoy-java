#!/usr/bin/env python
"""
Generate a tree using python annoy,
useful to make sure that annoy and annoy-java produce the same nearest neighbors
"""
import os
import random
from annoy import AnnoyIndex

random.seed = 42

this_dir = os.path.dirname(os.path.realpath(__file__))
test_resources_dir = this_dir + '/../resources'

dim = 20
n_vecs = 10000
n_trees = 10

t = AnnoyIndex(dim)

for i in xrange(n_vecs):
    v = [random.random() for z in xrange(dim)]
    t.add_item(i, v)

t.build(n_trees)
t.save(test_resources_dir + '/test_%d.ann' % dim)

seed = 42
nn_cnt = 100

with open('%s/%d_nns_of_%d.txt' % (test_resources_dir, nn_cnt, seed), 'w') as f:
    for nn in t.get_nns_by_item(seed, nn_cnt):
        f.write(str(nn) + '\n')

k = 2
with open('%s/%d_nns_of_%d_k%d.txt' % (test_resources_dir, nn_cnt, seed, k), 'w') as f:
    for nn in t.get_nns_by_item(seed, nn_cnt, k):
        f.write(str(nn) + '\n')
