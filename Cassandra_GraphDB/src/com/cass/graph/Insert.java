package com.cass.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class Insert {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
	public static void insertNode() throws IOException{
    	System.out.println("INSERT NODE:");
    	System.out.print("Enter name: ");
    	String name = br.readLine().trim();
    	System.out.print("Enter gender: ");
    	String gender = br.readLine().trim();
    	System.out.print("Enter occupation: ");
    	String occupation = br.readLine().trim();
    	try {
    		insertNode(name, gender, occupation);
    	} catch (Exception e) {
    		System.err.println(e.getMessage());
    	}
    }
    
    public static void insertEdge() throws IOException {
    	System.out.println("INSERT EDGE: ");
    	
    	System.out.print("Enter source node name: ");
    	UUID sourceNode = Queries.getNodeId(br.readLine().trim());
    	System.out.println(sourceNode);
    	if (sourceNode == null) {
    		System.out.println ("Invalid source node");
    		return;
    	}
    	
    	System.out.print("Enter destination node ID/ name: ");
    	UUID destNode = Queries.getNodeId(br.readLine().trim());
    	if (destNode == null) {
    		System.out.println ("Invalid destination node");
    		return;
    	}

    	System.out.print("Enter relationship type: ");
    	String relType = br.readLine().trim();

    	System.out.print("Enter weight: ");
    	int weight = MainMenu.sc.nextInt();

    	try {
            insertEdge(MainMenu.OutEdgesTable, sourceNode, relType, destNode, weight);
            insertEdge(MainMenu.InEdgesTable, sourceNode, relType, destNode, weight);
    	} catch (Exception e) {
    		System.err.println(e.getMessage());
    	}
    }

    public static void insertNode(String name, String gender,String occupation) 
    		throws SQLException {
    	UUID nodeID = UUID.randomUUID();
    			
    	String query="INSERT INTO " + MainMenu.NodesTable + 
					 " (node_id, name, gender, occupation) " +
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
