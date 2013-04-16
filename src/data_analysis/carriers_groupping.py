__author__ = 'vvlasov'
import pandas as pd


if __name__ == '__main__':
    mnames = ['phone', 'carrier']
    carriers = pd.read_table('phonedata/carriers.txt', sep=',', header=None, names=mnames)
    print carriers.groupby('carrier').size()[:25]
