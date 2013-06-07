package com.cass.graph;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PrintNodeAttr {

	 public static void getNodeAttr(String n, Connection con) throws SQLException {
         String t = "SELECT * FROM Nodes WHERE name = '" + n + "'";          
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery(t);
         while(rs.next())
         {
        	// System.out.println(rs.getString("name"));
        	 for(int j=2;j<rs.getMetaData().getColumnCount()+1;j++)
        		 System.out.println(rs.getMetaData().getColumnName(j) +" : "+rs.getString(rs.getMetaData().getColumnName(j)));
         }
      }
}