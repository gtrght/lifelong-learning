from collections import defaultdict
import os

__author__ = 'vvlasov'
import json as js
import functools as ft

'''Preparing a traning set by replacing rare words with _RARE_'''


def traverse(func, tree):
    if not isinstance(tree, list):
        return

    if len(tree) == 2 and isinstance(tree[0], basestring) and isinstance(tree[1], basestring):
        func(tree, tree[1])
    else:
        for part in tree:
            traverse(func, part)


def count_values(count_dict, tree, part):
    count_dict[part] += 1


def replace_with_rare(count_dict, tree, part):
    if count_dict[part] < 5:
        tree[1] = "_RARE_"


if __name__ == '__main__':
    with open('h2/parse_train.dat', 'rb') as in_file, open('h2/parse_train.rare.dat', 'wb') as out_file:
        trees = [js.loads(line) for line in in_file.readlines()]

        counter = defaultdict(int)
        for tree in trees:
            traverse(ft.partial(count_values, counter), tree)

        for tree in trees:
            traverse(ft.partial(replace_with_rare, counter), tree)
            js.dump(tree, out_file)
            out_file.write(u'\n')

    os.system("python h2/count_cfg_freq.py h2/parse_train.rare.dat > h2/parse_train.counts.out")




