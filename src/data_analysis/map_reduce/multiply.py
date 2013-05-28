__author__ = 'vvlasov'

import MapReduce
import sys

# Matrix multiplication in the Simple Python MapReduce Framework

mr = MapReduce.MapReduce()


def mapper(record):
    if record[0] == 'a':
        for k in range(5):
            mr.emit_intermediate((record[1], k), tuple(record))
    else:
        for i in range(5):
            mr.emit_intermediate((i, record[2]), tuple(record))


def reducer(key, list_of_values):
    result = 0
    multipliers = {}
    for value in list_of_values:
        if value[0] == 'a':
            multipliers[value[2]] = value[3]

    for value in list_of_values:
        if value[0] == 'b':
            result += multipliers.get(value[1], 0) * value[3]

    mr.emit((key[0], key[1], result))

# Do not modify below this line
# =============================
if __name__ == '__main__':
    inputdata = open(sys.argv[1])
    mr.execute(inputdata, mapper, reducer)
