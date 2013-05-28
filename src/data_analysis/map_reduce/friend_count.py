import MapReduce
import sys

# Friends count in the Simple Python MapReduce Framework

mr = MapReduce.MapReduce()


def mapper(record):
    mr.emit_intermediate(record[0], 1)


def reducer(key, list_of_values):
    result = sum(list_of_values)
    mr.emit((key, result))


# Do not modify below this line
# =============================
if __name__ == '__main__':
    inputdata = open(sys.argv[1])
    mr.execute(inputdata, mapper, reducer)
