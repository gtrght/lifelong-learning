import math

__author__ = 'vvlasov'

#number of AES decimals
aes_decimals = math.log10(2) * 128
print aes_decimals

#trillion of dollars for $200 (brute-forcing 10^9),
# 4 * (10 ** 12) * (10 ** 9) / 200   = 2 * (10 ** 19)

performance = (4 * (10 ** 12) * (10 ** 9) / 200)
print 10 ** (aes_decimals - math.log10(performance)) / (365 * 24 * 3600)  # More than a billion (109) years


##
#On input 064 the output is "e86d2de2 e1387ae9".    On input 132032 the output is "1792d21d b645c008".
#On input 064 the output is "2d1cfa42 c0b1d266".    On input 132032 the output is "eea6e3dd b2146dd0".
#On input 064 the output is "9d1a4f78 cb28d863".    On input 132032 the output is "75e5e3ea 773ec3e6".
#On input 064 the output is "5f67abaf 5210722b".    On input 132032 the output is "bbe033c0 0bc9330e".
print int("e86d2de2", 16) ^ 0, int("1792d21d", 16) ^ int("11111111111111111111111111111111", 2)
print int("2d1cfa42", 16) ^ 0, int("eea6e3dd", 16) ^ int("11111111111111111111111111111111", 2)
print int("9d1a4f78", 16) ^ 0, int("75e5e3ea", 16) ^ int("11111111111111111111111111111111", 2)
print int("5f67abaf", 16) ^ 0, int("bbe033c0", 16) ^ int("11111111111111111111111111111111", 2)  #  The last one

print "{0:b}".format(6 ^ 5 ^ 14)
print "{0:b}".format(3 ^ 10 ^ 6)