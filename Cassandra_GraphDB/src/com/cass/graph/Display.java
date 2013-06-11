package com.cass.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Display {
	 public static void printNodeDetails() throws SQLException,IOException{
		 //get user input
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		 System.out.println("Enter node name for which you want to print the attributes:");
		 String userNode = br.readLine();
		 
		 ResultSet rs = Queries.getNodeAttr(userNode);
		 UUID node_id = (UUID) rs.getObject("node_id");

		 List<UUID> outlist = new ArrayList<UUID>();
		 outlist = FriendClass.get_friends(node_id);
		
		 List<UUID> inlist = new ArrayList<UUID>();
		 inlist = FriendClass.get_infriends(node_id);           
		 
		 
		 printNodeAttr(rs);
		 System.out.println("\nHas outgoing Edges to: ");
		 print_NodeNames(outlist);
		 System.out.println("\nHas incoming Edges from: ");
		 print_NodeNames(inlist);
	 }

	 public static void printNodeAttr(ResultSet rs) throws SQLException {
	     while(rs.next())
	     {
	    	 for(int j=2;j<rs.getMetaData().getColumnCount()+1;j++)
	    		 System.out.println(rs.getMetaData().getColumnName(j) +" : "+
	    				 rs.getString(rs.getMetaData().getColumnName(j)));
	     }
	 }

	 public static void print_NodeNames(List<UUID> Result) throws SQLException {
		 Statement st1 = MainMenu.con.createStatement();
		 if (Result.isEmpty())
			 System.out.println("None");
		 else
		 {
			 for(int i =0; i < Result.size(); i++) {
				 String node_st = "SELECT name FROM Nodes where node_id ="+ Result.get(i) +";";
				 ResultSet rs1 = st1.executeQuery(node_st);
				 String node_name = ((String) rs1.getObject(1));
				 System.out.println(node_name);
			 }
		 }
	}
}