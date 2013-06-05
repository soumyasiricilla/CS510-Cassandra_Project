package com.cass.graph;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.UUID;

public class MainMenu {
    public static java.sql.Connection con = null;
    public static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] a){
        try {
            Class.forName("org.apache.cassandra.cql.jdbc.CassandraDriver");
            con=DriverManager.getConnection("jdbc:cassandra://localhost:9160");

            String query = "Use GraphDB;";
            Statement st = con.createStatement();
            st.executeUpdate(query);
            
            int choice = 0;
            
            while (choice != 8) {
            	System.out.println("\n");
                System.out.println("MAIN MENU");            	
                System.out.println("1. Insert a node");
                System.out.println("2. Insert a relationship");
                System.out.println("3. Delete a node");
                System.out.println("4. Delete a relationship");
                System.out.println("5. Friend of friend");
                System.out.println("6. Transitive Closure");
                System.out.println("7. Bridge");
                System.out.println("8. Exit");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                
                switch(choice) {
                case 1: Insert.insertNode();
                		break;
                case 2: Insert.insertEdge();
                		break;
                case 3: Delete.deleteNode();
                		break;
                case 4: Delete.deleteEdge();
                		break;
                case 5: friendOfFriend();
                		break;
                case 6: transClosure();
        		break;
                case 7: isBridge();
        		break;
                case 8: exit();
                		break;
                default: System.out.println("Invalid choice");
                		break;
                }
            }
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public static void friendOfFriend() {
    	System.out.println("To be implemented - friendOfFriend");
    }
    
    public static void transClosure() {
    	System.out.println("To be implemented - transClosure");
    }

    public static void isBridge() {
    	System.out.println("To be implemented - isBridge");
    }

    public static void exit() throws SQLException {
    	System.out.println("Good bye!");
    	sc.close();
    	con.close();
    }
}