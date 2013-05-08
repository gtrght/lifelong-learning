from collections import defaultdict
import json
import re
import sys

estimation = defaultdict(list)


def count_term_frequency(text):
    global estimation
    words = filter(lambda x: len(x) > 0, re.split("[^A-Za-z,]", text))
    for word in words:
        estimation[word].append(1)

    return len(words)


def main():
    with open(sys.argv[1]) as tweet_file:
        tweets = [json.loads(line.strip()) for line in tweet_file.readlines()]

        total = 0
        for tweet in tweets:
            total += count_term_frequency(tweet.get('text', ''))

        for term in estimation.keys():
            print term, float(sum(estimation[term])) / total


if __name__ == '__main__':
    main()
