import json

__author__ = 'vvlasov'

import unittest
import cky


class TestSequenceFunctions(unittest.TestCase):
    def setUp(self):
        self.prepared = cky.prepare('h2/parse_train.counts.out')

    def test_empty(self):
        original = 'What is witch hazel?'
        result = json.dumps(cky.cky(original.split(' '), 'SBARQ', self.prepared[0], self.prepared[1]))
        print result  # Yeah, a side effect however I'd like to see this in input.
        self.assertEqual(result,
                         '["SBARQ", ["WHNP+PRON", "What"], ["SQ+VP", ["VERB", "is"], ["NP", ["NOUN", "_RARE_"], '
                         '["NOUN", "_RARE_"]]]]')



