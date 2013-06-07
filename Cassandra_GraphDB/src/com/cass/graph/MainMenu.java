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
            
            while (choice != 9) {
            	System.out.println("\n");
                System.out.println("MAIN MENU");            	
                System.out.println("1. Insert a node");
                System.out.println("2. Insert a relationship");
                System.out.println("3. Delete a node");
                System.out.println("4. Delete a relationship");
                System.out.println("5. Print node attributes");
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
                case 5: printNodeAttr();
        				break;                		
                case 6: friendOfFriend();
                		break;
                case 7: transClosure();
        				break;
                case 8: isBridge();
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
        }
    }
    
	public static void printNodeAttr() throws SQLException,IOException{
		//get user input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));          
        System.out.println("Enter node name for which you want print attributes:\n");
        String userNode = br.readLine();
      
		PrintNodeAttr.getNodeAttr(userNode,con);
	}
    
    public static void friendOfFriend() throws SQLException,IOException{
    	
    	List<UUID> immediate_friends  = new ArrayList<UUID>();
    	List<UUID> friends_of_friends  = new ArrayList<UUID>();
    	
    	//take user input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));          
        System.out.println("Enter node name for which you want to find friends of friend\n");
        String userNode = br.readLine();        
      	UUID node_id = getNodeid(userNode);
      	
        //finding immediate friends 
        immediate_friends = FriendClass.get_friends(node_id, con);
        
        //finding friends of friends
        System.out.println("\nFinding friends of friends:");
        friends_of_friends = FriendClass.friendoffriend(immediate_friends, con);
        print_NodeNames(friends_of_friends);
    	
    }
    
    public static void transClosure() throws SQLException, IOException {
    	List<UUID> Result;
    	
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));          
       	System.out.println("Enter the node name for which you want to find Transitive Closure\n");     
       	String userNode = br.readLine();       	
      	UUID node_id = getNodeid(userNode);
     
    	Result = TransitiveClosure.TransClosure(node_id, null, con);
    	
    	System.out.println("Transitive Closure of " + userNode);
    	print_NodeNames(Result);
    	
    }
    
    public static void isBridge() throws IOException, SQLException {
    	
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));          
       	System.out.println("Enter the 1st node name: ");
       	String FirstNode = br.readLine();
       	
       	br = new BufferedReader(new InputStreamReader(System.in));          
       	System.out.println("Enter the 2nd node name: ");
       	String SecondNode = br.readLine();
       
      	UUID firstnode_id  = getNodeid(FirstNode);
      	UUID secondnode_id  = getNodeid(SecondNode);
      	
       	boolean check = Bridge.isBridge(firstnode_id, secondnode_id, con);
       	if ( check == true )
       		System.out.println("Given edge is a bridge.");
       	else
       		System.out.println("Given edge is not a bridge.");
    }
    
    public static void print_NodeNames(List<UUID> Result) throws SQLException
    {
    	int i = 0;
    	 while(i < Result.size()) 
         {
        	 String node_st = "SELECT name FROM GraphDB.Nodes where node_id ="+ Result.get(i) +";";
        	 Statement st1 = con.createStatement();
             ResultSet rs1 = st1.executeQuery(node_st);         
        	 //String node_name = rs1.getString((String) rs1.getObject("name"));
        	 String node_name = ((String) rs1.getObject(1));
        	 System.out.println(node_name);
        	 i++;
         }
    }
  
    public static UUID getNodeid(String Nodename) throws SQLException
    {
    	//find node id for a given node name
    	String first_st = "SELECT node_id FROM GraphDB.Nodes where name ='"+ Nodename +"';";
      	Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(first_st);         
      	UUID node_id = (UUID) rs.getObject("node_id");
      	return(node_id);
    }

    public static void exit() throws SQLException {
    	System.out.println("Good bye!");
    	sc.close();
    	con.close();
    }
}