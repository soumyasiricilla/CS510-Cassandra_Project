package com.cass.graph;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class MainMenu {
    public static java.sql.Connection con = null;
    public static Scanner sc = new Scanner(System.in);
    public static String NodesTable = "Nodes";
    public static String OutEdgesTable = "OutEdges";
    public static String InEdgesTable = "InEdges";
    
    //private Cluster cluster;
    
    public static void main(String[] a){
        try {
            Class.forName("org.apache.cassandra.cql.jdbc.CassandraDriver");
            con=DriverManager.getConnection("jdbc:cassandra://192.168.1.11:9160");

            String query = "Use GraphDB;";
            Statement st = con.createStatement();
            st.executeUpdate(query);
            
            int choice = 0;
            
            while (choice != 9) {
            	System.out.println("\n");
                System.out.println("MAIN MENU");            	
                System.out.println("1. Insert a node");
                System.out.println("2. Insert a relationship");
                System.out.println("3. Delete a node");
                System.out.println("4. Delete a relationship");
                System.out.println("5. Display node details");
                System.out.println("6. Friend of friend");
                System.out.println("7. Transitive Closure");
                System.out.println("8. Bridge");
                System.out.println("9. Exit");
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
                case 5: Display.printNodeDetails();
        				break;                		
                case 6: FriendClass.friendOfFriend();
                		break;
                case 7: TransitiveClosure.transClosure();
        				break;
                case 8: Bridge.isBridge();
        				break;
                case 9: exit();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void exit() throws SQLException {
    	System.out.println("Good bye!");
    	sc.close();
    	con.close();
    }
}