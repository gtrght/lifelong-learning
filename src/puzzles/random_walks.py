__author__ = 'vvlasov'
import random as rd
import numpy as np


def generate_plain(tries, nsteps):
    walks = []
    for j in xrange(tries):
        walk = []
        walks.append(walk)
        position = 0
        for i in xrange(nsteps):
            step = 1 if position == 0 or rd.randint(0, 1) else -1
            position += step
            walk.append(position)

    return np.asarray(walks)


if __name__ == '__main__':
    nsteps = 2000
    walks = generate_plain(1000, nsteps)
    for crossingPoint in range(2, 21, 2):
        crossing_times = (walks >= crossingPoint).argmax(1)
        crossing_times = np.where(crossing_times > 0, crossing_times, nsteps)
        print "Reaching {} in {} steps average, where squared(n) = {}" \
            .format(crossingPoint, crossing_times.mean(), crossingPoint ** 2)





