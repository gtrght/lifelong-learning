from collections import defaultdict
import json
import re
import sys


def main():
    with open(sys.argv[1]) as tweet_file:
        tweets = [json.loads(line.strip()) for line in tweet_file.readlines()]

        hashtags = defaultdict(list)

        for tweet in tweets:
            if tweet.get('entities', None) is not None:
                tags = tweet['entities']['hashtags']
                for tag in tags:
                    hashtags[tag['text']].append(1)

        hashtags_items = map(lambda x: (x[0], sum(x[1])), hashtags.items())

        hashtags_items = sorted(hashtags_items, key=lambda x: -x[1])

        for tag in hashtags_items[:10]:
            print tag[0], float(tag[1])


if __name__ == '__main__':
    main()
