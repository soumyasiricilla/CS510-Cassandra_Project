package com.cass.graph;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class Insert {
	public static void insertNode() {
    	System.out.println("INSERT NODE:");
    	System.out.print("Enter name: ");
    	String name = MainMenu.sc.next();
    	System.out.print("Enter gender: ");
    	String gender = MainMenu.sc.next();
    	System.out.print("Enter occupation: ");
    	String occupation = MainMenu.sc.next();
    	System.out.print("Enter weight: ");
    	int weight = MainMenu.sc.nextInt();
    	try {
    		insertNode(name.trim(), gender.trim(), occupation.trim(), weight);
    	} catch (Exception e) {
    		System.err.println(e.getMessage());
    	}
    }
    
    public static void insertEdge() {
    	System.out.println("INSERT EDGE: ");
    	
    	System.out.print("Enter source node name: ");
    	UUID sourceNode = validate_node(MainMenu.sc.next());
    	System.out.println(sourceNode);
    	if (sourceNode == null) {
    		System.out.println ("Invalid source node");
    		return;
    	}
    	
    	System.out.print("Enter destination node ID/ name: ");
    	UUID destNode = validate_node(MainMenu.sc.next());
    	if (destNode == null) {
    		System.out.println ("Invalid destination node");
    		return;
    	}

    	System.out.print("Enter relationship type: ");
    	String relType = MainMenu.sc.next();
    	try {
            insertEdge("Out", sourceNode, relType, destNode);
            insertEdge("InEdge", destNode, relType, sourceNode);
    	} catch (Exception e) {
    		System.err.println(e.getMessage());
    	}
    }

    public static UUID validate_node(String name) {
    	String query="SELECT node_id FROM Nodes WHERE name = '" + name + "';";
        System.out.println(query);
        try {
            Statement st = MainMenu.con.createStatement();
            ResultSet rs = st.executeQuery(query);
            return((UUID) rs.getObject(1));
        } catch (SQLException e) {
    		System.err.println(e.getMessage());
        }
        
        return null;
    }
    
    public static void insertNode(String name, String gender,String occupation, int weight) throws SQLException {
    	UUID nodeID = UUID.randomUUID();
    			
    	String query="INSERT INTO Nodes (node_id, name, gender, " +
    									 "occupation, weight) " +
         		"VALUES (" + nodeID  + ", '" + name + "', '" + gender +
         				 "','" + occupation + "'," + weight + ");";
        Statement st = MainMenu.con.createStatement();
    	System.out.println(query);
        st.executeUpdate(query);
    	System.out.println("Inserted node: " + nodeID);
    }

    public static void insertEdge(String table, UUID sourceNode, String relType, UUID destNode) 
    		throws SQLException {
    	UUID relID = UUID.randomUUID();
        String query="INSERT INTO " + table + "(source_node, rel_id, " +
        										"rel_type, dest_node) " +
        			 "VALUES (" + sourceNode + "," + relID + "," + "'" + 
        						relType + "', " + destNode +");";
        System.out.println(query);
        Statement st = MainMenu.con.createStatement();
        st.executeUpdate(query);
    }
}
