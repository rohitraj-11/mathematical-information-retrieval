import pandas as pd

def queryfun():
    df = pd.read_csv('utils\\query.csv')
    mylist = df['QUERY'].tolist()

    return mylist