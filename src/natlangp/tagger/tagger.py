import itertools as itools
from io import open
import collections
import word_classifier

__author__ = 'Vasily Vlasov'


class TrigramSimpleTagger:
    def __init__(self, filename, classifier):
        self.filename = filename
        self.words_count = self.buildWordTags()
        self.rules_map = self.buildRulesMap()
        self._classifier = classifier

    words_count = []
    rules_map = []

    def buildWordTags(self):
        def wordMap(x):
            split = x.split(' ')
            return split[3], (split[2], float(split[0]))

        lines = [line.strip() for line in open(self.filename)]
        filtered = [line for line in lines if line.find("WORDTAG") != -1]
        tmp = map(wordMap, filtered)
        mapped = collections.defaultdict(list)
        for tuple in tmp:
            mapped[tuple[0]].append(tuple[1])
        return mapped

    def buildRulesMap(self):
        def wordMap(x):
            split = x.split(' ')
            return (split[2], split[3] if len(split) > 3 else "-", split[4] if len(split) > 4 else "-"), float(split[0])

        lines = [line.strip() for line in open(self.filename)]
        filtered = [line for line in lines if line.find("-GRAM") != -1]
        return dict(map(wordMap, filtered))


    start_tags = ['*']
    general_tags = ["O", "I-GENE"]

    def tags(self, index, word_len=-1):
        if index <= 0:
            return self.start_tags
        elif 0 < word_len < index:
            return ["STOP"]
        else:
            return self.general_tags

    q_list = {}

    def q(self, v, w, u):
        """Implementing a model with caching"""
        score = self.q_list.get((v, w, u), -1)
        if score is -1:
            tripleRuleCount = self.rules_map.get((w, u, v), 0)
            doubleRuleCount = self.rules_map.get((w, u, "-"), 0)
            score = 0 if tripleRuleCount == 0 else tripleRuleCount / doubleRuleCount
            self.q_list[(v, w, u)] = score

        return score

    def countTags(self, tag):
        """Implementing a model with caching"""
        return self.rules_map.get((tag, "-", "-"), 0)

    def e(self, word, tag):
        tagList = self.words_count.get(word, [])
        if len(tagList) == 0:
            word = self._classifier.classify(word)
            tagList = self.words_count.get(word)

        count = next((x[1] for x in tagList if tag == x[0]), 0)

        total = self.countTags(tag)
        result = count / total if count != 0 else 0
        return result

    def tagSentence(self, words):
        word_len = len(words)
        p = [{} for k in xrange(word_len + 2)]
        p[0][("*", "*")] = (1, "*")
        for k in range(1, len(p)):
            for u in self.tags(k - 1):
                for v in self.tags(k, word_len):
                    p[k][u, v] = (0, '*')
                    for w in self.tags(k - 2):
                        score = p[k - 1].get((w, u), (0, ""))[0] * self.q(v, w, u) * \
                                (self.e(words[k - 1], v) if v != "STOP" else 1)
                        if score > p[k][u, v][0]:
                            p[k][u, v] = (score, w)

        current_tuple = reduce(lambda x, y: x if p[len(p) - 1][x][0] > p[len(p) - 1][y][0] else y, p[len(p) - 1])
        tags = []
        for k in range(len(p) - 2, 0, -1):
            tags.insert(0, current_tuple[0])
            try:
                current_tuple = p[k + 1][current_tuple][1], current_tuple[0]
            except:
                pass

        return tags


tagger = TrigramSimpleTagger("gene.count.out", word_classifier.SimpleClassifier())
sentences = open("small_test.txt").read().split("\n\n")
writer = open("out.txt", "w")
for sentence in sentences:
    words = sentence.split("\n")
    for t in zip(words, tagger.tagSentence(words)):
        writer.write(t[0] + " " + t[1] + "\n")
    writer.write(u"\n")

writer.close()



