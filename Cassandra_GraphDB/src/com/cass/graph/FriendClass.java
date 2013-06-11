package com.cass.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class FriendClass {
	
	public static void friendOfFriend() throws SQLException,IOException{
		
    	List<UUID> immediate_friends  = new ArrayList<UUID>();
    	List<UUID> friends_of_friends  = new ArrayList<UUID>();
    	
    	//take user input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));          
        System.out.println("Enter node name for which you want to find friends of friend");
        String userNode = br.readLine();        
      	UUID node_id = Queries.getNodeId(userNode);
      	
        //finding immediate friends 
        immediate_friends = get_friends(node_id);
        
        //finding friends of friends
        System.out.println("\nFinding friends of friends:");
        friends_of_friends = friendoffriend(immediate_friends,node_id);
        Display.print_NodeNames(friends_of_friends);
        }

	public static List<UUID> friendoffriend(List<UUID> nodes_list, UUID node_id) throws SQLException {
		List<UUID> friends_of_friends  = new ArrayList<UUID>();
		List<UUID> mutualfriends  = new ArrayList<UUID>();
		UUID dest_nodeid;  	    
		for(UUID element: nodes_list)
		{
			if (element!=null)
			{
      	 		ResultSet rs = Queries.getDestNode(element);
      	 		
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
      	 /* for printing mutual friends
      	 if(mutualfriends.isEmpty())
      		System.out.println("No Mutual Friends.\n");
      	 else
      	 {
      		System.out.println("Mutual Friends are:");
      		MainMenu.print_NodeNames(mutualfriends);
      	 } 
      	 */
      	friends_of_friends = remove_duplicate(friends_of_friends);
      	
      	//remove itself(node) if it comes in a cycle
      	if (friends_of_friends.contains(node_id))
      		friends_of_friends.remove(node_id);
      	return (friends_of_friends);
       }
	
	public static List<UUID> friendoffriend(List<UUID> nodes_list) throws SQLException {
		List<UUID> friends_of_friends  = new ArrayList<UUID>();
		List<UUID> mutualfriends  = new ArrayList<UUID>();
		UUID dest_nodeid;  	    
		for(UUID element: nodes_list)
		{
			if (element!=null)
			{
				//friends_of_friends.add(element);
      	 		ResultSet rs = Queries.getDestNode(element);
      	 		
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
	
   	public static List<UUID> remove_duplicate(List<UUID> nodes_list) throws SQLException {
       	HashSet<UUID> hs = new HashSet<UUID>();
       	hs.addAll(nodes_list);
       	nodes_list.clear();
       	nodes_list.addAll(hs);
       	return(nodes_list);
       	}
	
   	public static List<UUID> get_infriends(UUID node_id) throws SQLException {
   		List<UUID> infriends  = new ArrayList<UUID>();
        if (node_id!=null) {
        	ResultSet rs = Queries.getSourceNode(node_id);
        	
   	 		while(rs.next())
      			 infriends.add((UUID) rs.getObject(1));
   	 		
   	 		infriends = remove_duplicate(infriends);
   	 		}
        return (infriends);
        }
}