from collections import defaultdict
import functools
import sys
import marshal
import numpy as np

__author__ = 'vvlasov'


def initialize_model1(pairs, q_map, t_map):
    counts = defaultdict(int)
    dict = defaultdict(set)

    for pair in pairs:
        eng_words = (pair[0]).split(" ")
        es_words = pair[1].split(" ")

        for es_word in es_words:
            dict['_NULL_'].add(es_word)
            for eng_word in eng_words:
                dict[eng_word].add(es_word)

    for word in dict:
        translations = dict[word]

        for translation in translations:
            len_of_trans = len(translations)
            t_map[(translation, word)] = float(1) / len_of_trans

            # for key in t_map:
            # print key, t_map[key]
            # print t_map[('de', 'the')]
            # print t_map[('de', '_NULL_')]

            # print t_map['de', 'budget'], t_map['de', 'the']


def estimation_maximization(iterations, pairs, delta, q_map, t_map):
    for iteration in range(iterations):
        print  "Running iteration", iteration
        c_map = defaultdict(float)
        qc_map = defaultdict(float)
        # print 'New iteration, ', iteration, c_map
        for k in range(len(pairs)):
            pair = pairs[k]
            cache = {}
            translation = pair[1].split(" ")
            original = pair[0].split(" ")
            original.insert(0, "_NULL_")

            l = len(original)
            m = len(translation)

            for i in range(m):
                for j in range(l):
                    d = delta(pair[0], pair[1], original, translation, c_map, t_map, q_map, cache, i, j)
                    # print 'delta[{}][{}][{}] = '.format(k, i, j), d
                    c_map[(translation[i], original[j])] += d
                    c_map[original[j]] += d
                    qc_map[(j, i, l, m)] += d
                    qc_map[(i, l, m)] += d

        for fe_pair in t_map:
            # print fe_pair, c_map[fe_pair], c_map[fe_pair[1]]
            t_map[fe_pair] = c_map[fe_pair] / c_map[fe_pair[1]]

        for item in qc_map:
            if isinstance(item, tuple) and len(item) == 4:
                q_map[item] = qc_map[item] / qc_map[(item[1], item[2], item[3])]


def delta_model_1(original_str, translation_str, original, translation, c_map, t_map, q_map, cache, i, j):
    target = translation[i]
    cached = cache.get((target, original_str), -1)

    source = original[j]
    if cached != -1:
        return t_map[(target, source)] / cached

    total = 0
    for source in original:
        total += t_map[(target, source)]

    cache[(target, original_str)] = total
    return t_map[(target, original[j])] / total


def delta_model_2(original_str, translation_str, original, translation, c_map, t_map, q_map, cache, i, j):
    target = translation[i]
    cached = cache.get((target, original_str), -1)
    l = len(original)
    m = len(translation)

    q1 = q_map.get((j, i, l, m), float(1) / l)

    source = original[j]
    if cached != -1:
        return (q1 * t_map[(target, source)]) / cached

    total = 0
    for j in xrange(len(original)):
        total += t_map[(target, original[j])] * q_map.get((j, i, l, m), float(1) / l)

    cache[(target, original_str)] = total
    return (q1 * t_map[(target, original[j])]) / total


def recover_alignment_for_pair(originals, translations, estimation):
    m = len(translations)
    l = len(originals)
    result = np.zeros(dtype=int, shape=m)  # replace with empty
    for i in range(m):
        translation = translations[i]
        max = -1
        for j in xrange(l):
            original = originals[j]
            candidate = estimation(translation, original, i, j, m, l)

            if max < candidate:
                max = candidate
                result[i] = j

    return result


def recover_alignments(pairs, output, estimation):
    for k in range(len(pairs)):
        if k % 100 == 0:
            print 'Recovering: ', k
        pair = pairs[k]
        translations = pair[1].split(" ")
        originals = pair[0].split(" ")
        originals.insert(0, "_NULL_")

        result = recover_alignment_for_pair(originals, translations, estimation)
        for index in xrange(len(result)):
            if result[index] != 0:
                output.write("{} {} {}".format(k + 1, result[index], index + 1))
                output.write("\n")


with open('h3/corpus.en') as en_input, open('h3/corpus.es') as es_input:
    training = zip([line.strip() for line in en_input.readlines()],
                   [line.strip() for line in es_input.readlines()])

    print sys.argv  # printing parameters

    def estimation(q_map, t_map, translation, original, i, j, m, l):
        return q_map.get((j, i, l, m), 1) * t_map.get((translation, original), 0)

    if 'ibm_1' in sys.argv:
        if 'estimate' in sys.argv:
            t_map = {}
            initialize_model1(training, t_map)
            estimation_maximization(5, training, delta_model_1, {}, t_map)
            marshal.dump(t_map, open('h3/tmap.ms', 'wb'))

        t_map = {}
        print "Loading t_map from file",
        t_map = marshal.load(open('h3/tmap.ms', 'rb'))

        print "Starting alignment recovery with t_map ", len(t_map)
        recover_alignments(zip([line.strip() for line in open('h3/test.en', 'rb').readlines()],
                               [line.strip() for line in open('h3/test.es', 'rb').readlines()]),
                           open('h3/test.out', 'wb'),
                           functools.partial(estimation, {}, t_map))

    if 'ibm_2' in sys.argv:
        if 'estimate' in sys.argv:
            print "Loading t_map"
            t_map = marshal.load(open('h3/tmap.ms', 'rb'))
            q_map = {}

            print "Estimating parameters q and t"
            estimation_maximization(5, training, delta_model_2, q_map, t_map)
            marshal.dump(t_map, open('h3/tmap2.ms', 'wb'))
            marshal.dump(q_map, open('h3/qmap2.ms', 'wb'))

        print "Loading parameters for IBM2..."
        t_map = marshal.load(open('h3/tmap2.ms', 'rb'))
        q_map = marshal.load(open('h3/qmap2.ms', 'rb'))

        print "Recovering alignments"
        recover_alignments(zip([line.strip() for line in open('h3/test.en', 'rb').readlines()],
                               [line.strip() for line in open('h3/test.es', 'rb').readlines()]),
                           open('h3/alignment_test.p2.out', 'wb'),
                           functools.partial(estimation, q_map, t_map))