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
    	try {
    		insertNode(name, gender, occupation);
    	} catch (Exception e) {
    		System.err.println(e.getMessage());
    	}
    }
    
    public static void insertEdge() {
    	System.out.println("INSERT EDGE: ");
    	
    	System.out.print("Enter source node ID/ name: ");
    	int sourceNode = validate_node(MainMenu.sc.next());
    	if (sourceNode == 0) {
    		System.out.println ("Invalid source node");
    		return;
    	}
    	
    	System.out.print("Enter destination node ID/ name: ");
    	int destNode = validate_node(MainMenu.sc.next());
    	if (destNode == 0) {
    		System.out.println ("Invalid destination node");
    		return;
    	}

    	System.out.print("Enter relationship type: ");
    	String relType = MainMenu.sc.next();
    	
    	try {
            insertEdge(sourceNode, relType, destNode);
    	} catch (Exception e) {
    		System.err.println(e.getMessage());
    	}
    }

    public static int validate_node(String IdOrName) {
    	String query;
    	if (isInteger(IdOrName)){
            query="SELECT node_id FROM Nodes WHERE node_id =" + IdOrName + ";";
    	} else {    		
            query="SELECT node_id FROM Nodes WHERE name = '" + IdOrName + "';";
    	}
    	
        System.out.println(query);
        try {
            Statement st = MainMenu.con.createStatement();
            ResultSet rs = st.executeQuery(query);
            return(rs.getInt(1));
        } catch (SQLException e) {
    		System.err.println(e.getMessage());
        }
        
        return 0;
    }
    
    public static boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
            return false; 
        }
        // only got here if we didn't return false
        return true;
    }

    public static void insertNode(String name, String gender,String occupation) throws SQLException {
    	String query = "SELECT COUNT(*) FROM Nodes;";
        Statement st = MainMenu.con.createStatement();
        ResultSet rs = st.executeQuery(query);
        int nodeID = rs.getInt(1) + 1;
    			
    	query="INSERT INTO Nodes (node_id, name, gender, occupation) " +
         		"VALUES (" + nodeID  + ", '" + name + "', '" + gender + "','" + occupation + "');";
    	System.out.println(query);
        st.executeUpdate(query);
    	System.out.println("Inserted node: " + nodeID);
    }

    public static void insertEdge(int sourceNode, String relType, int destNode) throws SQLException {
    	UUID relID = UUID.randomUUID();
        String query="INSERT INTO Out (source_node, rel_id, rel_type, dest_node) " +
        		"VALUES (" + sourceNode + "," + relID + "," + "'" + relType + "', " + destNode +");";
        System.out.println(query);
        Statement st = MainMenu.con.createStatement();
        st.executeUpdate(query);
    }
}
