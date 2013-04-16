import math

__author__ = 'vvlasov'


n = 123.4

print (n % 100 - n % 10) / 10
print ((n - n % 10) % 100) / 10
print (n % 10) / 10


print (map(lambda x: -5 * x ** 5 + 69 * x ** 2 - 47, [0, 1, 2, 3]))


def future_value(present_value, annual_rate, periods_per_year, years):
    rate_per_period = annual_rate / periods_per_year
    periods = periods_per_year * years

    return present_value * (1 + rate_per_period) ** periods


print "$1000 at 2% compounded daily for 3 years yields $", future_value(500, .04, 10, 10)
print "$1000 at 2% compounded daily for 3 years yields $", future_value(1000, .02, 365, 3)


def area_polygon(number, size):
    return 0.25 * number * (size ** 2) / math.tan(math.pi / float(number))

print area_polygon(7, 3)


def max_of_2(a, b):
    if a > b:
        return a
    else:
        return b


def max_of_3(a, b, c):
    return max_of_2(a, max_of_2(b, c))

print max_of_3(1, 2, 3)


def project_to_distance(point_x, point_y, distance):
    dist_to_origin = math.sqrt(point_x ** 2 + point_y ** 2)
    scale = distance / dist_to_origin
    return point_x * scale, point_y * scale

print project_to_distance(2, 7, 4)