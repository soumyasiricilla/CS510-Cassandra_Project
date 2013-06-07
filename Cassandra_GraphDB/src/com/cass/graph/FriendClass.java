package com.cass.graph;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.UUID;

public class FriendClass {
    
	public static List<UUID> remove_duplicate(List<UUID> nodes_list) throws SQLException {
       	HashSet<UUID> hs = new HashSet<UUID>();
       	hs.addAll(nodes_list);
       	nodes_list.clear();
       	nodes_list.addAll(hs);
       	return(nodes_list);
       }
	
	public static List<UUID> get_friends(UUID node_id, Connection con) throws SQLException {
 	
        List<UUID> immediate_friends  = new ArrayList<UUID>();
	
      	 if (node_id!=null)
      	 {
      		 String dest_st = "select dest_node from GraphDB.Out where source_node="+ node_id +";";
      		 Statement st = con.createStatement();
      		 ResultSet rs = st.executeQuery(dest_st);
      		 while(rs.next())
      			 immediate_friends.add((UUID) rs.getObject(1));
      		 
             immediate_friends = remove_duplicate(immediate_friends);
      	 }
      	 	return (immediate_friends);
       }
       
	
       public static List<UUID> friendoffriend(List<UUID> nodes_list, Connection con) throws SQLException {
      	 	
    	    List<UUID> friends_of_friends  = new ArrayList<UUID>();
    	    List<UUID> mutualfriends  = new ArrayList<UUID>();
    	    UUID dest_nodeid;
    	    
      	 	for(UUID element: nodes_list)
      	 	{
      	 	 if (element!=null)
      	   	 {
				//friends_of_friends.add(element);
      	 		String t = "select dest_node from GraphDB.Out where source_node="+ element +";";       
      	 		Statement st = con.createStatement();
      	 		ResultSet rs = st.executeQuery(t);
      	 		
      	 		while(rs.next())
      	 		{
      	 			dest_nodeid = (UUID) rs.getObject("dest_node");
      	 			//check if this resulting node is not already an adjacent node
      	 			if(!(dest_nodeid.equals(element)))
      	 				friends_of_friends.add((UUID) rs.getObject(1));
      	 			else
      	 				mutualfriends.add((UUID) rs.getObject(1));
      	 		}
      	   	 }
      	 	}
      	 	/*
      	 if(mutualfriends.isEmpty())
      		System.out.println("No Mutual Friends.\n");
      	 else
      	 {
      		System.out.println("Mutual Friends are:");
      		MainMenu.print_NodeNames(mutualfriends);
      	 } 
      	 */
      	 friends_of_friends = remove_duplicate(friends_of_friends);
      	 return (friends_of_friends);
       }
}