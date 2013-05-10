from collections import defaultdict
import json
import re
import sys


def hw():
    print 'Hello, world!'


scores = {}
estimation = defaultdict(list)


def load_scores(fp):
    global scores
    scores = dict((line.split('\t')[0], int(line.split('\t')[1])) for line in fp.readlines())


def estimate_term_sentiment(text):
    global scores, estimation
    words = filter(lambda x: len(x) > 0, re.split("[^@A-Za-z,]", text))

    result = sum(map(lambda x: scores.get(x, 0), words))

    if result != 0:
        for word in words:
            estimation[word].append(float(result) / len(words))


def main():
    with open(sys.argv[1]) as afinn_file, open(sys.argv[2]) as tweet_file:
        load_scores(afinn_file)
        # print "Loaded dictionary of sentiment scores:", len(scores)

        tweets = [json.loads(line.strip()) for line in tweet_file.readlines()]

        for tweet in tweets:
            estimate_term_sentiment(tweet.get('text', ''))

        for term in estimation.keys():
            print term, '%0.2f' % (sum(estimation[term])/len(estimation[term]))


if __name__ == '__main__':
    main()
