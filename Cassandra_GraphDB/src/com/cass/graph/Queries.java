package com.cass.graph;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class Queries {

    public static UUID getNodeId(String name) {
    	String query="SELECT node_id FROM " + MainMenu.NodesTable + " WHERE name = '" + name + "';";
        try {
            Statement st = MainMenu.con.createStatement();
            ResultSet rs = st.executeQuery(query);
            return((UUID) rs.getObject(1));
        } catch (SQLException e) {
    		System.err.println(e.getMessage());
        }
        return null;
    }
    
    public static ResultSet getNodeAttr(String name) throws SQLException {
    	String query = "SELECT * FROM " + MainMenu.NodesTable + " WHERE name = '" + name + "'";
    	Statement st = MainMenu.con.createStatement();
    	ResultSet rs = st.executeQuery(query);
    	return rs;
      }

    public static ResultSet getDestNode(UUID sourceNode) throws SQLException {
		String t = "SELECT dest_node FROM " + MainMenu.OutEdgesTable + " WHERE source_node=" + sourceNode +";";       
		Statement st = MainMenu.con.createStatement();
		ResultSet rs = st.executeQuery(t);
		return rs;
	}
    
    public static ResultSet getSourceNode(UUID destNode) throws SQLException {
		String t = "SELECT source_node FROM " + MainMenu.OutEdgesTable + " WHERE dest_node=" + destNode +";";       
		Statement st = MainMenu.con.createStatement();
		ResultSet rs = st.executeQuery(t);
		return rs;
	}
    
    public static ResultSet getOutEdges(UUID sourceNode) throws SQLException {
    	Statement st = MainMenu.con.createStatement();
    	String query = "SELECT * FROM " + MainMenu.OutEdgesTable + " WHERE source_node = " + sourceNode + ";";
    	ResultSet result = st.executeQuery(query);
		return result;
	}
    
	public static ResultSet getInEdges(UUID destNode) throws SQLException {
		Statement st = MainMenu.con.createStatement();
    	String query = "SELECT * FROM " + MainMenu.InEdgesTable + " WHERE dest_node = " + destNode + ";";
    	ResultSet result = st.executeQuery(query);
		return result;
	}

	public static ResultSet getRelId(String table, UUID sourceNode,
			UUID destNode, String relType) throws SQLException {
    	Statement st = MainMenu.con.createStatement();		
		String query = "SELECT rel_id FROM " + table + 
				" WHERE source_node = " + sourceNode + 
				" AND rel_type = '" + relType + "'" +
				" AND dest_node = " + destNode + ";";
    	ResultSet result = st.executeQuery(query);
		return result;
	}
}
