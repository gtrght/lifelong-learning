__author__ = 'vvlasov'


for i in range(1, 35):
    if 2 ** i % 35 == 1:
        print 2 ** i, i
        break