# BigDataProject

# PLAN

# Server
  #Connect Server
   Use TeamViewer to connect to server
   Server will be turn off after 11:00 pm
  Beacause my InternetyIP does not public IP, I need to use TeamViewer.
  The TeamViewer ID and Password change each day.
  Call me(Kaiwen Zhu) for the ID and Password to connect to the server, if you need to deploy your code and test it.
  TeamViewer ServerID:
  TeamViewer ServerPassWord:

  Ubuntu 18.04
  Operation System User: kevin
  Operation System Password : 19950314

  #Config Server
  #Install Oracle Java8
  sudo add-apt-repository ppa:webupd8team /java // we need to run this command for install java.
  sudo apt-get update // using this command all dependency will be updated
  sudo apt-get install oracle-java8-installer // now using this command java will be installed
  sudo apt-get update // using this command all dependency will be updated

  #Intall Neo4j
  wget -O - https://debian.neo4j.org/neotechnology.gpg.key | sudo apt-key add -
  echo 'deb http://debian.neo4j.org/repo stable/' >/tmp/neo4j.list
  sudo mv /tmp/neo4j.list /etc/apt/sources.list.d
  sudo apt-get update // using this command all dependency will be updated

  #Use Neo4j
  Ctrl+Alt+t calls the Terminal
  Enter "service neo4j start"
  Enter “http://localhost:7474/browser/” in firefox browser for using the neo4j
  "service neo4j status"//check status
  "service neo4j restart"//restart
  "sudo su"//run command as root
