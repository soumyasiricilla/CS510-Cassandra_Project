package com.cass.graph;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class Delete {

    public static void deleteNode() {
    	System.out.println("DELETE NODE:");
    	System.out.print("Enter name: ");
    	int nodeId = Insert.validate_node(MainMenu.sc.next());
    	if (nodeId == 0) {
    		System.out.println ("Invalid node name");
    		return;
    	}

    	try {
            deleteNode(nodeId);
    	} catch (Exception e) {
    		System.err.println(e.getMessage());
    	}
    }

    public static void deleteEdge() {
    	System.out.println("DELETE EDGE: ");
    	
    	System.out.print("Enter source node ID/ name: ");
    	int sourceNode = Insert.validate_node(MainMenu.sc.next());
    	if (sourceNode == 0) {
    		System.out.println ("Invalid source node");
    		return;
    	}
    	
    	System.out.print("Enter destination node ID/ name: ");
    	int destNode = Insert.validate_node(MainMenu.sc.next());
    	if (destNode == 0) {
    		System.out.println ("Invalid destination node");
    		return;
    	}

    	try {
            deleteEdge(sourceNode,destNode);
    	} catch (Exception e) {
    		System.err.println(e.getMessage());
    	}
    }
    
    public static void deleteNode(int nodeID) throws SQLException {
    	String query = "DELETE FROM Nodes WHERE node_id = " + nodeID + ";";
    	System.out.println(query);
    	Statement st = MainMenu.con.createStatement();
    	st.executeUpdate(query);
    }
    
    public static void deleteEdge(int sourceNode, int destNode ) throws SQLException {
    	
    	String query = "SELECT * FROM Out WHERE source_node = " + sourceNode + ";";
    	System.out.println(query);
    	Statement st = MainMenu.con.createStatement();
    	ResultSet result = st.executeQuery(query);
    	
    	while (result.next())
    	{
    		if (result.getInt("dest_node") == destNode)
    		{
    			String relType = result.getString("rel_type");
    			UUID relID = (UUID) result.getObject("rel_id");
    			query = "DELETE FROM Out WHERE source_node = " + sourceNode + 
    					" AND rel_type = '" + relType + "'" +
    					" AND rel_id = " + relID + 
    					";";
    			System.out.println(query);
    			st.executeUpdate(query);
    			return;
    		}
    	}
    	
    	System.out.println("The relationship does not exist");
    }    
}
