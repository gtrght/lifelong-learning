__author__ = 'vvlasov'


def exchange(x, y):
    """Exchanges values with no additional variables"""
    x = x ^ y
    y ^= x
    x ^= y
    #return a tuple in a preserved order, values should be exchanged
    return x, y


def palindrome(x):
    """Checks if the integer is a palindrome in binary representation, ex: 5 = 101 - true, 8 = 1010 - false"""
    original = x
    reverse = 0
    while x > 0:
        reverse <<= 1
        reverse += x % 2
        x >>= 1

    return original & reverse == original


def add(x, y):
    """Adds x to y using only bitwise operations """

    if x < 0 or y < 0:
        raise ValueError("Arguments must be non-negative, provided {}, {}".format(x, y))
    if y == 0:
        return x

    summ = x ^ y  # Summing all the positions, zeroing overlapping 1-s, example 3 ^ 1 = 2
    carry = (x & y) << 1  # Finding a curry, for example 3, 1 => 3 & 1 = 1, so shift curry one bit left curry = 2,
    return add(summ, carry)


print palindrome(5), palindrome(1), palindrome(11), palindrome(7), palindrome(8), palindrome(0), palindrome(-1)
print exchange(5, 6), exchange(0, 1), exchange(-1, 100), exchange(5, 5)
print add(0, 1), add(3, 1), add(6, 5)


