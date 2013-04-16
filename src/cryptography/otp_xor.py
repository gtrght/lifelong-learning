# Encoding ONE-TIME-PAD with a given raw message and encryption result
# The goal is to get encoded(attack at dusk)

__author__ = 'vvlasov'

import string


def to_ascii_array(value):
    return [(ord(c)) for c in value]


def to_hex_array(value):
    return [('0' if c < 16 else '') + hex(c)[2:4] for c in value]


def xor(message, encrypted):
    return [i[0] ^ i[1] for i in zip(message, encrypted)]


message1 = "attack at dawn"
encrypted = "6c73d5240a948c86981bc294814d"
encArray = [int('0x' + encrypted[2 * i:2 * i + 2], 16) for i in range(len(encrypted) / 2)]

key = xor(to_ascii_array(message1), encArray)

l = xor(to_ascii_array("attack at dusk"), key)
print string.join(to_hex_array(l), '')

