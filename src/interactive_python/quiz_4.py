from math import sqrt


def closest_distance(point, circle):
    return sqrt((circle[0] - point[0]) ** 2 + (circle[1] - point[1]) ** 2) - circle[2]


print closest_distance((4, 7), (2, 9, 2))

print sum([1, 2, 3, 5])

l1 = range(1, 5)
l2 = l1[:4]

l2[0] = 2
print l1


l3 = list(l1)
l3[0] = 10
print l1


value = 5
for i in xrange(12):
    value *= 2
    value -= 3

print value


a = [49, 27, 101, -10]
b = a
c = list(a)
d = c
a[3] = 68
c[2] = a[1]
b = a[1 : 3]
b[1] = c[2]

print b