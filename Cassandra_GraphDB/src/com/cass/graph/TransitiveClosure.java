package com.cass.graph;

import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransitiveClosure {
	


    public static List<UUID> TransClosure(UUID srcNode, UUID dstNode, Connection con) throws SQLException
    {
    	
    	List <UUID> nodeFriends = null ;
    	List <UUID> Result = new ArrayList<UUID>();
    	List <UUID> temp = null;
    	
    	nodeFriends = FriendClass.get_friends(srcNode, con);    	
    	Result.add(srcNode);    	
    	
    	if ( dstNode != null)
    	   	if (nodeFriends.contains(dstNode))
    	   		nodeFriends.remove(dstNode);
    	
    	while(!nodeFriends.isEmpty())
    	{
    		temp = FriendClass.get_friends(nodeFriends.get(0), con);
    		
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
