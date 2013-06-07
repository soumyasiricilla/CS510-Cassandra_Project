package com.cass.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class Delete {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));          
    
    public static void deleteEdge() throws IOException {
    	System.out.println("DELETE EDGE: ");
    	
    	System.out.print("Enter source node name: ");
    	UUID sourceNode = Queries.getNodeId(br.readLine().trim());
    	if (sourceNode == null) {
    		System.out.println ("Invalid source node");
    		return;
    	}
    	
    	System.out.print("Enter destination node name: ");
    	UUID destNode = Queries.getNodeId(br.readLine().trim());
    	if (destNode == null) {
    		System.out.println ("Invalid destination node");
    		return;
    	}

    	System.out.print("Enter relationship type: ");
    	String relType = br.readLine().trim();

    	try {
            deleteEdge("Out", sourceNode,destNode, relType);
            deleteEdge("InEdge",sourceNode,destNode, relType);
    	} catch (Exception e) {
    		System.err.println(e.getMessage());
    	}
    }

    /* This function first deletes all the edges associated with the given node and
     * then deletes the node. 
     */
    public static void deleteNode() throws IOException {
    	System.out.println("DELETE NODE:");
    	System.out.print("Enter name: ");
    	UUID nodeId = Queries.getNodeId(br.readLine().trim());
    	if (nodeId == null) {
    		System.out.println ("Invalid node name");
    		return;
    	}

    	try {
        	/* Delete all the edges from Out table 
        	 * where given node appears as a destination node 
        	 */    	
        	deleteOutEdges(nodeId);
        	
        	/* Delete all the edges from InEdges table 
        	 * where given node appears as a source node
        	 */   	
        	deleteInEdges(nodeId);
        	
        	/* Delete a row from Out table
        	 * where given node is a source node (row key)
        	 */
        	deleteAllEdges("Out", nodeId);
        	
        	/* Delete a row from InEdges table
        	 * where given node is a destination node (row key)
        	 */
        	deleteAllEdges("InEdge", nodeId);

        	deleteNode(nodeId);
    	} catch (Exception e) {
    		System.err.println(e.getMessage());
    	}
    }
    
    /* This function deletes all the edges from Out table where the given node appears 
     * as a destination node.  
     * e.g. If there are edges from 1->2, 2->3 and we are trying to delete node 2. Then 
     * we have following entries in Out and InEdges tables.
     * Out: 1 -> 2, 2 -> 3
     * In Edges: 2 <- 1, 3 <- 2
     * This function will delete edge 1->2 from Out table.
     */
    public static void deleteOutEdges(UUID nodeID) throws SQLException {
    	/* InEdges table can give us all the edges where the given node is a 
    	 * destination node.
    	 */
    	ResultSet result = Queries.getInEdges(nodeID);
    	while (result.next())
    	{
			String relType = result.getString("rel_type");
			UUID sourceNode = (UUID) result.getObject("source_node");
			deleteEdge("Out", sourceNode, nodeID, relType);
    	}
    }

    /* This function deletes all the edges from InEdges table where the given node appears 
     * as a source node.  
     * e.g. If there are edges from 1->2, 2->3 and we are trying to delete node 2. Then 
     * we have following entries in Out and InEdges tables.
     * Out: 1 -> 2, 2 -> 3
     * In Edges: 2 <- 1, 3 <- 2
     * This function will delete edge 3 <- 2 from InEdges table.
     */
    public static void deleteInEdges(UUID nodeID) throws SQLException {
    	/* Out table can give us all the edges where the given node is a 
    	 * source node.
    	 */
    	ResultSet result = Queries.getOutEdges(nodeID);
    	while (result.next())
    	{
			String relType = result.getString("rel_type");
			UUID destNode = (UUID) result.getObject("dest_node");
			deleteEdge("InEdge", nodeID, destNode, relType);
    	}
    }

    public static void deleteAllEdges(String table, UUID nodeId) throws SQLException {
    	Statement st = MainMenu.con.createStatement();
    	String query;
		if (table.equals("Out")) {
			query = "DELETE FROM " + table + 
					" WHERE source_node = " + nodeId + 
					";";				
		} else {
			query = "DELETE FROM " + table + 
					" WHERE dest_node = " + nodeId +
					";";				
			
		}
		System.out.println(query);
		st.executeUpdate(query);
    }

    public static void deleteEdge(String table, UUID sourceNode, UUID destNode, String relType ) throws SQLException {
    	Statement st = MainMenu.con.createStatement();
		String query;
		ResultSet result = Queries.getRelId(table, sourceNode, destNode, relType);

    	while (result.next())
    	{
			UUID relID = (UUID) result.getObject("rel_id");
			if (table.equals("Out")) {
				query = "DELETE FROM " + table + 
						" WHERE source_node = " + sourceNode + 
						" AND rel_type = '" + relType + "'" +
						" AND rel_id = " + relID + 
						";";				
			} else {
				query = "DELETE FROM " + table + 
						" WHERE dest_node = " + destNode +
						" AND rel_type = '" + relType + "'" +
						" AND rel_id = " + relID + 
						";";				
				
			}
			System.out.println(query);
			st.executeUpdate(query);
			return;
    	}
    	
    	System.out.println("The relationship does not exist");
    }

    public static void deleteNode(UUID nodeID) throws SQLException {

    	String query = "DELETE FROM Nodes WHERE node_id = " + nodeID + ";";
    	System.out.println(query);
    	Statement st = MainMenu.con.createStatement();
    	st.executeUpdate(query);
    }
}
