import math
import numpy as np
import pandas as pd
from sklearn.ensemble import RandomForestClassifier


__author__ = 'vvlasov'

frame = pd.DataFrame(pd.read_table('../csv/train.csv', sep=','))
mean_age = frame['age'].mean()


def entropy(count1, count2, total):
    p1 = float(count1) / total
    p2 = float(count2) / total
    return - p1 * math.log(p1, 2) - p2 * math.log(p2, 2)


def tune_young_to_survived(age):
    frame['young'] = np.where(frame['age'] < age, 1, 0)
    young_to_survived = frame.groupby(['young', 'survived']).size()
    print age, young_to_survived


def tune_young_to_survived(age):
    frame['young'] = np.where(frame['age'] < age, 1, 0)
    young_to_survived = frame.groupby(['young', 'survived']).size()
    print age, young_to_survived

# tuning the survived out of age to split in two classes, 9 is just fine
# for i in range(30):
#     tune_young_to_survived(i)

# Estimating the pivot point
# for price in range(200, 300, 20):
#     frame['cheap'] = np.where(frame['fare'] < price, 1, 0)
#     print price, frame.groupby(['cheap', 'survived']).size()


def prepare_frame(frame):
    frame['age'] = np.where(frame['age'] > 0, frame['age'], mean_age)
    frame['sex'] = np.where(frame['sex'] == 'female', 1, 0)
    frame['young'] = np.where(frame['age'] <= 2, 1, 0)
    frame['cheap'] = np.where(frame['fare'] < 280, 1, 0)
    frame['class'] = np.where(frame['pclass'] == 1, 1, 0)


prepare_frame(frame)
# print frame[:2][['survived', 'pclass', 'age', 'sex', 'young']]


forest = RandomForestClassifier(n_estimators=100)
forest = forest.fit(frame[['sex', 'young', 'cheap', 'class']], frame['survived'])

frame = pd.DataFrame(pd.read_table('../csv/test.csv', sep=','))

prepare_frame(frame)

prediction = forest.predict(frame[['sex', 'young', 'cheap', 'class']])

with open("../csv/titanic.csv", "wb") as out_file:
    for row in prediction:
        out_file.write(str(row))
        out_file.write('\n')







