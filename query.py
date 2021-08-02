import pandas as pd

def queryfun():
    df = pd.read_csv('query.csv')
    mylist = df['QUERY'].tolist()

    return mylist