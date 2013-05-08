
import unittest
import mini_project_3 as mp


class TestHelperFunctions(unittest.TestCase):
    def setUp(self):
        pass

    def test_format(self):
        self.assertEqual(mp.format(0), "0:00.0")
        self.assertEqual(mp.format(11), "0:01.1")
        self.assertEqual(mp.format(321), "0:32.1")
        self.assertEqual(mp.format(631), "1:03.1")

    def test_ticks(self):
        mp.start()
        mp.tick()
        self.assertEqual(mp.time, 1)
        mp.tick()
        mp.tick()
        mp.tick()
        self.assertEqual(mp.time, 4)

    def test_reset(self):
        mp.start()
        mp.tick()
        mp.tick()
        mp.tick()
        self.assertEqual(mp.time, 3)
        mp.total = 10
        mp.wins = 5

        mp.reset()

        self.assertEqual(mp.time, 0)
        self.assertEqual(mp.total, 0)
        self.assertEqual(mp.wins, 0)





