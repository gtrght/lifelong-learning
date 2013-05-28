__author__ = 'vvlasov'


import MapReduce
import sys

# Unique DNA trims in the Simple Python MapReduce Framework

mr = MapReduce.MapReduce()


def mapper(record):
    key = record[0]
    value = record[1][:-10]
    mr.emit_intermediate(value, 1)


def reducer(key, list_of_values):
    mr.emit(key)

# Do not modify below this line
# =============================
if __name__ == '__main__':
    inputdata = open(sys.argv[1])
    mr.execute(inputdata, mapper, reducer)
