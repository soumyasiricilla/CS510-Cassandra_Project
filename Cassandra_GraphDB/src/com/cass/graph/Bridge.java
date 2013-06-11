package com.cass.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Bridge{
    public static void isBridge() throws IOException, SQLException {
    	
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));          
       	System.out.println("Enter the 1st node name: ");
       	String FirstNode = br.readLine();
       	
       	br = new BufferedReader(new InputStreamReader(System.in));          
       	System.out.println("Enter the 2nd node name: ");
       	String SecondNode = br.readLine();
       
      	UUID firstnode_id  = Queries.getNodeId(FirstNode);
      	UUID secondnode_id  = Queries.getNodeId(SecondNode);
      	
       	boolean check = Bridge.isBridge(firstnode_id, secondnode_id);
       	if ( check == true )
       		System.out.println("Given edge is a bridge.");
       	else
       		System.out.println("Given edge is NOT a bridge.");
    }

    public static boolean isBridge(UUID srcNode, UUID dstNode) throws SQLException {
		 boolean isbridgebool=false;
		 List <UUID> nodeFriends = new ArrayList<UUID>();
		 List <UUID> temp =  new ArrayList<UUID>();
		 nodeFriends = FriendClass.get_friends(srcNode);
		 if(nodeFriends.contains(dstNode)) //checking if there is an edge b/w srcNOde and dstNode
		 {
			 temp=TransitiveClosure.TransClosure(srcNode, dstNode);
			 if(temp.contains(dstNode))
			 {
				 isbridgebool=false;
			 }
			 else
				 isbridgebool = true;
		 }
		 else //if there is no edge
			 System.out.println("There is no edge between given nodes.");		 
		 
		 return isbridgebool;
    }
}