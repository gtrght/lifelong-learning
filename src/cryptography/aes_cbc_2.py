__author__ = 'vvlasov'
import functools as fc
from Crypto.Cipher import AES


def cbc_encode(message, key, vi):
    crypt = AES.new(key, AES.MODE_CBC, vi)
    return crypt.encrypt(message)


def cbc_decode(message, key, iv, mode):
    if mode == AES.MODE_CBC:
        crypt = AES.new(key, mode, IV=iv)
        return crypt.decrypt(message)
    else:
        counter = int(iv, 16)
        result = ""
        for i in (range(len(message) / 16 + 1)):
            result += (AES.new(key, mode, counter=lambda: hex(counter).lstrip('0x').rstrip('L').decode('hex')).decrypt(message[i * 16: (i + 1) * 16]))
            counter += 1
        return result


cbc_messages = [
    (
        "4ca00ff4c898d61e1edbf1800618fb2828a226d160dad07883d04e008a7897ee2e4b7465d5290d0c0e6c6822236e1daafb94ffe0c5da05d9476be028ad7c1d81",
        "140b41b22a29beb4061bda66b6747e14", AES.MODE_CBC),
    (
        "5b68629feb8606f9a6667670b75b38a5b4832d0f26e1ab7da33249de7d4afc48e713ac646ace36e872ad5fb8a512428a6e21364b0c374df45503473c5242a253",
        "140b41b22a29beb4061bda66b6747e14", AES.MODE_CBC),
    (
        "69dda8455c7dd4254bf353b773304eec0ec7702330098ce7f7520d1cbbb20fc388d1b0adb5054dbd7370849dbf0b88d393f252e764f1f5f7ad97ef79d59ce29f5f51eeca32eabedd9afa9329",
        "36f18357be4dbd77f050515c73fcf9f2", AES.MODE_CTR),
    (
        "770b80259ec33beb2561358a9f2dc617e46218c0a53cbeca695ae45faa8952aa0e311bde9d4e01726d3184c34451",
        "36f18357be4dbd77f050515c73fcf9f2", AES.MODE_CTR),
]


for message in cbc_messages:
    key = message[1].decode('hex')
    str_message = message[0].decode('hex')

    decode = cbc_decode(str_message[16:], key, str_message[:16] if message[2] == AES.MODE_CBC else message[0][0:32],
                        message[2])
    print decode, '\n\n'






