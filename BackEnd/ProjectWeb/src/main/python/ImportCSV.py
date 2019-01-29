import csv
from py2neo import neo4j, authenticate, Graph, Node, cypher, rel, Relationship 

#Type your own account name and password at here
graph = Graph("localhost:7474", username="kevin", password"19950314")
##graph_db.delete_all()

with open('UserActivity.csv", "rb" as file:
data = csv.reader(file, delimiter = ";")
data.next()

## Add Node
graph_db.create(Node('User', name = 'A'))

## Add Edge
graph.create(Relationship(NodeA, 'relation', NodeB))

