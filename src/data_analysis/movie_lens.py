__author__ = 'vvlasov'

from pandas import DataFrame, Series
import pandas as pd


unames = ['user_id', 'gender', 'age', 'occupation', 'zip']
users = pd.read_table('ml-1m/users.dat', sep='::', header=None, names=unames)

rnames = ['user_id', 'movie_id', 'rating', 'timestamp']
ratings = pd.read_table('ml-1m/ratings.dat', sep='::', header=None, names=rnames)

mnames = ['movie_id', 'title', 'genres']
movies = pd.read_table('ml-1m/movies.dat', sep='::', header=None, names=mnames)

merged_data = DataFrame(pd.merge(pd.merge(users, ratings), movies))


#getting aggregation by rating
print merged_data.groupby('rating').size()[:5]

#15 titles sorted by name
print sorted(merged_data.title.unique())[:15]

#mean rating by age and gender
print merged_data.pivot_table('rating', rows='age', cols='gender', aggfunc='mean')



