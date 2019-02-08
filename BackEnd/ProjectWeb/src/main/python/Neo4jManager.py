import csv
from py2neo import Graph, Node, cypher, Relationship, NodeMatcher

class Neo4jManager:
	server = "http://34.227.104.129:7474/"
	user_name = "neo4j"
	password = "19950314"
	
	def __init__(self):
		graph_db = Graph(this.server, username = this.user_name, password = this.password)
	
	def __init__(self, server, username, password):
		this.server = server
		this.username = username
		this.password = password
	
	def setServer(server):
		this.server = server
	
	def setUser_Name(user_name):
		this.user_name = user_name
	
	def setPassWord(password):
		this.password = password
		
	# batch
	def saveActivityDatas(datas):
		# {"user_name", "from_source", "content", "link", "time", "map_location","type"}
		for i in range(len(datas)):
			this.saveActivityData(datas[i])
	
	# single
	def saveActivityData(self, data):
		# {"user_name", "from_source", "content", "link", "time", "map_location","type"}
		#find the node has same user name
		matcher = NodeMatcher(this.graph_db)
		
		user_query = "MATCH (user:User) Where user.name = '"+ data["user_name"] +"' Return user"
		user_data = graph_db.run(user_query).data()
	
		if len(user_data) == 0:
			user_Node = Node('User', name = data["user_name"])##user_name
			graph_db.create(user_Node)
		else:
			user_Node = matcher.match('User', name= data["user_name"]).first()
	
		#check whether there is same node
		service_query = "MATCH (service:Service) Where service.name = '"+ data["from_source"] +"' Return service"
		service_data = graph_db.run(service_query).data()
	
		if len(service_data) == 0:
			service_Node = Node('Service', name = data["from_source"])##from_source
			graph_db.create(service_Node)
			#Create relation
			user_relation_service = Relationship(user_Node, "Visit", service_Node)
			graph_db.create(user_relation_service)
		else:
			service_Node = matcher.match('Service', name = data["from_source"]).first()
		
		## [id,content,from_source,link,map_location,time,type,user_name]
		## [0 ,1      ,2          ,3   ,4           ,5   ,6   ,7       ]
		# {"user_name", "from_source", "content", "link", "time", "map_location","type"}
		#find detaile
		## [id,content,from_source,link,map_location,time,type,username]
		record_query = "MATCH (record:Record) Where record.time_stamp = '"+data["time"]+"' Return record"
		record_data = graph_db.run(record_query).data()
	
		if len(record_data) == 0:
			record_Node =Node('Record', content = data["content"], link = data["link"], map_location = data["map_location"], time_stamp = data["time"], type = data["type"])
			graph_db.create(record_Node)
			#Create relation
			service_relation_detail = Relationship(service_Node, "Visit", record_Node)
			graph_db.create(service_relation_detail)
	
	#Save the data from cvs file
	def saveActivityDataFromFile(file_link):
		
		## [id,content,from_source,link,map_location,time,type,user_name]
		## [0 ,1      ,2          ,3   ,4           ,5   ,6   ,7       ]
		with open(file_link, "rb") as file:
			data = csv.reader(file, delimiter = ";")
			rows = [row for row in data]
			
		matcher = NodeMatcher(graph_db)

		for i in range(1,len(rows)):

			#find the node has same user name
			user_query = "MATCH (user:User) Where user.name = '"+ rows[i][-1] +"' Return user"
			user_data = graph_db.run(user_query).data()
	
			if len(user_data) == 0:
				user_Node = Node('User', name = rows[i][-1])##user_name
				graph_db.create(user_Node)
			else:
				user_Node = matcher.match('User', name= rows[i][-1]).first()
	
			#check whether there is same node
			service_query = "MATCH (service:Service) Where service.name = '"+ rows[i][2] +"' Return service"
			service_data = graph_db.run(service_query).data()
		
			if len(service_data) == 0:
				service_Node = Node('Service', name = rows[i][2])##from_source
				graph_db.create(service_Node)
				#Create relation
				user_relation_service = Relationship(user_Node, "Visit", service_Node)
				graph_db.create(user_relation_service)
			else:
				service_Node = matcher.match('Service', name = rows[i][2]).first()
		
		
			#find detaile
			## [id,content,from_source,link,map_location,time,type,username]
			##record_query = "MATCH (record:Record) Where record.id = '"+rows[i][0]+"' And record.content = '"+rows[i][1]+"' And record.link = '"+rows[i][3]+"' And record.map_location = '"+rows[i][4]+"' And record.time_stamp = '"+rows[i][5]+"' And record.type = '"+rows[i][6]+"' Return record"
			record_query = "MATCH (record:Record) Where record.time_stamp = '"+rows[i][5]+"' Return record"
			record_data = graph_db.run(record_query).data()
	
			if len(record_data) == 0:
				record_Node =Node('Record', id = rows[i][0], content = rows[i][1], link = rows[i][3], map_location = rows[i][4], time_stamp = rows[i][5], type = rows[i][6])
				graph_db.create(record_Node)
				#Create relation
				service_relation_detail = Relationship(service_Node, "Visit", record_Node)
				graph_db.create(service_relation_detail)
		

if __name__ = '__main__':





