from flask import Flask, render_template, request
from searchQ import searchQuery

from query import queryfun

app = Flask(__name__)

results = {}

@app.route('/')
def home():
    return render_template('index.html', results={})

@app.route('/srch', methods = ['POST','GET'])
def srch():
    global results
    if request.method == 'POST':
        data = request.form
    # print(data)
    query = data['query']
    # print(query)
    #searchQuery(query)
    results = searchQuery(query)
    # print(results)
    newres= {}
    c = 10
    for k,v in results.items():
        if (not c) : break
        newres[k]=v 
        c-=1       
            # print(results)
    return render_template('index.html', results=newres)

@app.route('/srch/<num>', methods = ['POST','GET'])
def next(num):
    newres={}
    num = int(num)
    print("num ",num)
    c = 0
    for k,v in results.items():
        if c>=(num-1)*10 and c<(num*10):
            newres[k]=v
        elif c >= (num * 10) : break
        c += 1
    # newres = results[(num - 1)*10:(num*10)]
    print(newres)
    print(results)
    return render_template('index.html', results=newres)

@app.route('/team')
def team():
    return render_template('team.html')

@app.route('/query')
def query():
    querylist = queryfun()
    return render_template('query.html', querylist=querylist)

if __name__ == "__main__":
    app.run(debug=True, port=3000)