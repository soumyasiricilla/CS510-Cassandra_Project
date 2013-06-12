package com.cass.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransitiveClosure {
	
    public static void transClosure() throws SQLException, IOException {
    	List<UUID> Result;
    	
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));          
       	System.out.println("Enter the node name for which you want to find Transitive Closure");     
       	String userNode = br.readLine();       	

       	UUID node_id = Queries.getNodeId(userNode);
     
    	Result = TransClosure(node_id, null);
    	
    	System.out.println("\nTransitive Closure of " + userNode);
    	Display.print_NodeNames(Result);
    }

    public static List<UUID> TransClosure(UUID srcNode, UUID dstNode) throws SQLException
    {
    	List <UUID> nodeFriends = null ;
    	List <UUID> Result = new ArrayList<UUID>();
    	List <UUID> temp = null;
    	
    	nodeFriends = FriendClass.get_friends(srcNode);    	
    	Result.add(srcNode);    	
    	
    	if ( dstNode != null)
    	   	if (nodeFriends.contains(dstNode))
    	   		nodeFriends.remove(dstNode);
    	
    	while(!nodeFriends.isEmpty())
    	{
    		temp = FriendClass.get_friends(nodeFriends.get(0));
    		
    		for ( UUID s:temp)
    		{
    			if ((!nodeFriends.contains(s)) && ( !Result.contains(s)))
    			{
    				nodeFriends.add(s);
    			}
    		}
    		
    		Result.add(nodeFriends.get(0));
    		nodeFriends.remove(0);
    	}
    	    	
    	Result.remove(srcNode);
    	return Result;
    }
}
