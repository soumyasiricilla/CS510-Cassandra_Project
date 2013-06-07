package com.cass.graph;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class Insert {
	public static void insertNode() {
    	System.out.println("INSERT NODE:");
    	System.out.print("Enter name: ");
    	String name = MainMenu.sc.next().trim();
    	System.out.print("Enter gender: ");
    	String gender = MainMenu.sc.next().trim();
    	System.out.print("Enter occupation: ");
    	String occupation = MainMenu.sc.next().trim();
    	try {
    		insertNode(name, gender, occupation);
    	} catch (Exception e) {
    		System.err.println(e.getMessage());
    	}
    }
    
    public static void insertEdge() {
    	System.out.println("INSERT EDGE: ");
    	
    	System.out.print("Enter source node name: ");
    	UUID sourceNode = Queries.getNodeId(MainMenu.sc.next().trim());
    	System.out.println(sourceNode);
    	if (sourceNode == null) {
    		System.out.println ("Invalid source node");
    		return;
    	}
    	
    	System.out.print("Enter destination node ID/ name: ");
    	UUID destNode = Queries.getNodeId(MainMenu.sc.next().trim());
    	if (destNode == null) {
    		System.out.println ("Invalid destination node");
    		return;
    	}

    	System.out.print("Enter relationship type: ");
    	String relType = MainMenu.sc.next().trim();

    	System.out.print("Enter weight: ");
    	int weight = MainMenu.sc.nextInt();

    	try {
            insertEdge("Out", sourceNode, relType, destNode, weight);
            insertEdge("InEdge", sourceNode, relType, destNode, weight);
    	} catch (Exception e) {
    		System.err.println(e.getMessage());
    	}
    }

    public static void insertNode(String name, String gender,String occupation) 
    		throws SQLException {
    	UUID nodeID = UUID.randomUUID();
    			
    	String query="INSERT INTO Nodes (node_id, name, gender, occupation) " +
         		"VALUES (" + nodeID  + ", '" + name + "', '" + gender +
         				 "','" + occupation + "');";
        Statement st = MainMenu.con.createStatement();
    	System.out.println(query);
        st.executeUpdate(query);
    	System.out.println("Inserted node: " + nodeID);
    }

    public static void insertEdge(String table, UUID sourceNode, String relType, 
    		UUID destNode, int weight) throws SQLException {
    	UUID relID = UUID.randomUUID();
        String query="INSERT INTO " + table + "(source_node, rel_id, " +
        										"rel_type, dest_node, weight) " +
        			 "VALUES (" + sourceNode + "," + relID + "," + "'" + 
        						relType + "', " + destNode +"," + weight + ");";
        System.out.println(query);
        Statement st = MainMenu.con.createStatement();
        st.executeUpdate(query);
    }
}
