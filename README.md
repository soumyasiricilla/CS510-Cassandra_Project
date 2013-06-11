Cs510 Data Management in the Cloud Course Work Project

Project Description: 
Goal of this project is to realize a small graph management system in Cassandra NoSQL database. 
There are a many social netwroking applications present today that use similar graph management systems. While developing this application we kept those features in mind.
This project is a part of the course "CS 410/510-Data Management in the Cloud" in Portland State University.


Softwares Needed:

1. apache-cassandra-1.2.5-bin.tar.gz
http://cassandra.apache.org/download/

2. cassandra-jdbc driver(cassandra-jdbc-1.2.5.jar)
https://code.google.com/a/apache-extras.org/p/cassandra-jdbc/downloads/list

3. Java 6/JDK (recommended)
http://www.java.com/en/download/manual.jsp

4. Eclipse (any version)
http://www.eclipse.org/downloads/

5. Python 2.7.5
http://www.python.org/getit/


Configuration Steps:

1. Setup  Cassandra and cassandra-jdbc

   We are using cassandra-jdbc, which is a JDBC driver for connectivity between Cassandra/CQL and Java.
   Java is used as a front end language for developing the application and connecting to the database stored in Cassandra.

   Download cassandra-jdbc driver(cassandra-jdbc-1.2.5.jar) from above given link in lib folder in CASSANDRA home (\apache-cassandra-1.2.5\lib).

   Make sure below librabraries are also present in lib folder in CASSANDRA home (\apache-cassandra-1.2.5\lib):
   apache-cassandra-thrift-1.2.5.jar, 
   apache-cassandra-clientutil-1.2.5.jar, 
   libthrift-0.7.0.jar, 
   apache-cassandra-1.2.5.jar, 
   guava-13.0.1.jar, 
   cassandra-jdbc-1.2.5.jar, 
   log4j-1.2.16.jar, 
   slf4j-api-1.7.2.jar, 
   slf4j-log4j12-1.7.2.jar

   Follow below link for running Cassandra Server and Client:

   http://wiki.apache.org/cassandra/GettingStarted

2. Setup Cassandra cluster and keyspace
   
   The Cassandra database can be setup in multinode cluster for replicating data among nodes. Below are the parameters that need to be modified in the cassandra.yaml file.
   
   > listen_addr: Address to bind to and tell other Cassandra nodes to connect to.

   > rpc_addr: The address other clients connect to.

   > Seeds: IP addr of machines that contain information about the ring

   > Murmur3Partitioner: make sure this is the partitioning method used
   
   > Create a keyspace with name "GraphDB" using below cmd:
   
      CREATE KEYSPACE GraphDB
      WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 3 }; 

3. Download the github project and import it in Eclipse.

http://help.eclipse.org/helios/index.jsp?topic=%2Forg.eclipse.platform.doc.user%2Ftasks%2Ftasks-importproject.htm


File manifest(class files and script files included):

   > CQl_Commands.txt
   
   > MainMenu.java

   > Bridge.java

   > Delete.java

   > Display.java

   > FriendClass.java

   > Insert.java

   > Queries.java

   > TransitiveClosure.java


Developers:

Dang Le, Neena Maldikar, Norah Alballa, Pratibha Natani, Soumya Siricilla

Portland State University (CS 410/510-Data Management in the Cloud  - Spring 2013 class)

Credits and acknowledgments:

We want to thank Professor Kristin Tufte for her guidance in Cloud Management Systems, which truely helped us while development of this project.

References:

http://www.developerstation.org/2011/08/simple-readwrite-example-using.html

http://www.datastax.com/docs/1.2/cql_cli/index

http://cassandra.apache.org/doc/cql/CQL.html

http://www.datastax.com/docs/1.1/dml/using_cql
