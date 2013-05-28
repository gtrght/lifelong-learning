import MapReduce
import sys

# Join in the Simple Python MapReduce Framework

mr = MapReduce.MapReduce()


def mapper(record):
    mr.emit_intermediate(record[1], record)


def reducer(key, list_of_values):
    order = None
    for element in list_of_values:
        if element[0] == 'order':
            order = element
            break

    list_of_values.remove(order)

    for element in list_of_values:
        mr.emit(order + element)


# Do not modify below this line
# =============================
if __name__ == '__main__':
    inputdata = open(sys.argv[1])
    mr.execute(inputdata, mapper, reducer)
