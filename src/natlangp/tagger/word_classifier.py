import collections
import re

__author__ = 'U0139096'


class Classifier:
    _uppercase = re.compile("^[A-Z]*$")
    _last_uppercase = re.compile(".*[A-Z]$")
    _digits = re.compile('\d')

    def classify(self, word):
        if bool(self._digits.search(word)):
            return "_DIGIT_"
        elif re.match(self._uppercase, word):
            return "_UPPERCASE_"
        elif bool(re.match(self._last_uppercase, word)):
            return "_LAST_UPPERCASE_"
        else:
            return "_RARE_"


class SimpleClassifier(Classifier):
    def classify(self, word):
        return "_RARE_"


class RareWordReplace:
    def __init__(self, classifier):
        self._classifier = classifier

    def replace(self, stat_file, in_file, out_file ):
        def statMap(word_stat):
            stat_split = word_stat.split(" ")
            return stat_split[3], int(stat_split[0])

        lines = [line.strip() for line in open(stat_file)]
        word_tags = [line for line in lines if line.find("WORDTAG") != -1]

        statistics = collections.defaultdict(list)

        for word_stat in word_tags:
            stat_map = statMap(word_stat)
            statistics[stat_map[0]].append(stat_map[1])

        lines = [line.strip() for line in open(in_file)]
        writer = open(out_file, "w")

        for line in lines:
            line_split = line.split(" ")
            if len(line_split) > 1:
                writer.write(
                    self._classifier.classify(line_split[0]) + " " + line_split[1] if sum(
                        statistics[line_split[0]]) < 5 else line)
            else:
                writer.write(line)
            writer.write(u"\n")
        writer.close()

#RareWordReplace(Classifier()).replace("gene.count.out", "gene.train", "gene.train.out")