# intern/Scripts/activate
# set FLASK_ENV=development

import numpy as np
import pandas as pd
from sentence_transformers import SentenceTransformer

sbert_model = SentenceTransformer('bert-base-nli-mean-tokens')

def searchQuery(query):

    df=pd.read_csv("C:\\Users\\Dell\\Desktop\\Intern\\src\\Output\\Formula.csv",encoding='iso-8859-1') # input file
    df.head()

    reader1=df['Filename']
    reader2=df['Formula']

    def cosine(u, v):
        return np.dot(u, v) / (np.linalg.norm(u) * np.linalg.norm(v))

    sentence_embeddings = np.load('utils\\formula_embeddings.npy')

    # query = r"-0.026838601\ldots"

    query_vec = sbert_model.encode([query])[0]

    sim_score=[]
    for sent in sentence_embeddings:
        sim = cosine(query_vec, sent)
        sim_score.append(sim)

    rows=zip(reader1,sim_score) 
    df=pd.DataFrame(rows,columns=['DOCNO','Similarity'])
    # df.to_csv("output.csv",index=True) #output file

    # df = pd.read_csv("output.csv")
    sorted_df = df.sort_values(by=["Similarity"], ascending=False) 
    sorted_df.drop_duplicates(subset='DOCNO',keep='first', inplace = True)
    df1=sorted_df.head(100)
    # df1.to_csv("outputsorted.csv", index=False)

    results = df1.to_dict('index')
    
    return results

