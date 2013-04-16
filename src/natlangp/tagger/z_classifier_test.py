__author__ = 'Vasily Vlasov'

import unittest
import word_classifier


class TestSequenceFunctions(unittest.TestCase):

    def setUp(self):
        self.classifier = word_classifier.Classifier()

    def test_classify_alphanumeric(self):
        classifier_classify = self.classifier.classify("qwe123")
        self.assertEqual(classifier_classify, "_DIGIT_")

    def test_classify_upper(self):
        classifier_classify = self.classifier.classify("QWERTY")
        self.assertEqual(classifier_classify, "_UPPERCASE_")

    def test_classify_last_upper(self):
        classifier_classify = self.classifier.classify("qweA")
        self.assertEqual(classifier_classify, "_LAST_UPPERCASE_")

    def test_classify_rare(self):
        classifier_classify = self.classifier.classify("qweqweqw")
        self.assertEqual(classifier_classify, "_RARE_")


