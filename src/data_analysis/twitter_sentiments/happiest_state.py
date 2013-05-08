from collections import defaultdict
import json
import re
import sys

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

        states_score = defaultdict(list)

        for tweet in tweets:
            if tweet.get('place', None) is not None and tweet['place']['country_code'] == 'US':
                states_score[tweet['place']['full_name'][-2:]].append(estimate_sentiment(tweet.get('text', '')))

        happiest = ['NA', 0]


        for state in states_score:
            score = sum(states_score[state])
            if score > happiest[1]:
                happiest = [state, score]


        print happiest[0]


if __name__ == '__main__':
    main()
