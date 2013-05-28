import MapReduce
import sys

# Assymetric freinds count (doubled) in the Simple Python MapReduce Framework

mr = MapReduce.MapReduce()


def mapper(record):
    mr.emit_intermediate((record[0], record[1]), 1)
    mr.emit_intermediate((record[1], record[0]), -1)


def reducer(key, list_of_values):
    if sum(list_of_values) != 0:
        mr.emit(key)


# Do not modify below this line
# =============================
if __name__ == '__main__':
    inputdata = open(sys.argv[1])
    mr.execute(inputdata, mapper, reducer)
