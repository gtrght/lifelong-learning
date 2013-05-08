import json
import re
import sys


def hw():
    print 'Hello, world!'


scores = {}


def load_scores(fp):
    global scores
    scores = dict(map(lambda x: (x[0], int(x[1])), [line.split('\t') for line in fp.readlines()]))


def estimate_sentiment(text):
    global scores
    words = re.findall(r"[\w']+", text.lower())

    return sum(map(lambda x: scores.get(x, 0), words))


def main():
    with open(sys.argv[1]) as afinn_file, open(sys.argv[2]) as tweet_file:
        load_scores(afinn_file)
        # print "Loaded dictionary of sentiment scores:", len(scores)

        tweets = [json.loads(line.strip()) for line in tweet_file.readlines()]

        for tweet in tweets:
            print '%0.2f' % estimate_sentiment(tweet.get('text', ''))


if __name__ == '__main__':
    main()
