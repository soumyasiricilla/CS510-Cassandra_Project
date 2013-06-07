package com.cass.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.sql.Connection;
import java.sql.SQLException;


public class Bridge{
	

public static boolean isBridge(UUID srcNode, UUID dstNode, Connection con) throws SQLException
	    {
		 boolean isbridgebool=false;
		 List <UUID> nodeFriends = new ArrayList<UUID>();
		 List <UUID> temp =  new ArrayList<UUID>();
		 nodeFriends = FriendClass.get_friends(srcNode, con);
		 if(nodeFriends.contains(dstNode)) //checking if there is an edge b/w srcNOde and dstNode
		 {
			 temp=TransitiveClosure.TransClosure(srcNode, dstNode, con);
			 if(temp.contains(dstNode))
			 {
			//	 System.out.println("not bridge");
				 isbridgebool=false;
			 }
			 else
			//	 System.out.println("is bridge");
				 isbridgebool = true;
		 }
		 else //if there is no edge
			 System.out.println("There is no edge b/w given nodes.");		 
		 
		 return isbridgebool;
	    }
}