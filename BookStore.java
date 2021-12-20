import java.sql.*;
import java.util.*;

public class BookStore
{
	// Session
	static ArrayList<String> cart = new ArrayList<String>();
	static String user_id = "";
	static String role = "";
	
    public static void main(String[] args) {
    	int user_id = 0; // Will be set >= 1 after successful login
    	
        while (true) {
            Scanner inpObj = new Scanner(System.in);
            System.out.println();
            System.out.println("0) Exit");
            System.out.println("1) Register new user");
            System.out.println("2) Log in");
            System.out.println("3) Search books");
            System.out.println("4) Checkout");
            System.out.println("5) Add new book");
            System.out.println("6) Remove book");
            System.out.println("7) View reports");
            String inp = inpObj.nextLine();
            
            if (inp.equals("0")) {
	        	// Exit
	        	System.exit(0);
        	} else if (inp.equals("1")) {
            	// Register
                registerUser();
                continue;
                
            } else if (inp.equals("2")) {
            	// Login
                login();
                continue;
            	
            } else if (inp.equals("3")) {
            	// Search
            	
            	System.out.println();
                System.out.println("0) Back");
                System.out.println("1) Search by title");
                System.out.println("2) Search by author");
                System.out.println("3) Search by genre");
                System.out.println("4) Search by publisher");
                System.out.println("5) Show all books");
                inp = inpObj.nextLine();
                
               
                while (true) {
					 if (inp.equals("0")) {
						// Back
					 	break;
					 } else if (inp.equals("1")) {
					 	// Search by title
					     search(1);
					     break;
					 } else if (inp.equals("2")) {
					  	// Search by author
						 search(2);
					     break;
					 } else if (inp.equals("3")) {
					  	// Search by genre
						 search(3);
					     break;
					 } else if (inp.equals("4")) {
					   	// Search by publisher
						 search(4);
					     break;
					 } else if (inp.equals("5")) {
					 	 // Show all
						 search(5);
						 break;
					 } else {
					     System.out.println("not an option");
					     continue;
					 }
				}
						
            } else if (inp.equals("4")) {
            	// Checkout
            	checkOut();
            	
            } else if (inp.equals("5")) {
            	// Add new book
            	addBook();
            	
            } else if (inp.equals("6")) {
            	// Remove book
            	removeBook();
            	
			} else {
			    System.out.println("not an option");
			    continue;
			}
        }
    } 
    
    static void checkOut() {
    	if (!role.equals("Customer")) {
    		System.out.println(role);
    		System.out.println("You need to be logged in as a customer");
    		return;
    	}
    	
    	Scanner inpObj = new Scanner(System.in);
        System.out.println("Enter first name");
        String fname = inpObj.nextLine();
        System.out.println("Enter last name");
        String lname = inpObj.nextLine();
        System.out.println("Enter credit card number");
        String card = inpObj.nextLine();
        System.out.println("Enter billing address street");
        String bstreet = inpObj.nextLine();
        System.out.println("Enter billing address city");
        String bcity = inpObj.nextLine();
        System.out.println("Enter billing address province");
        String bprovince = inpObj.nextLine();
        System.out.println("Enter billing address postal code");
        String bpostal = inpObj.nextLine();
        System.out.println("Enter shipping address street");
        String sstreet = inpObj.nextLine();
        System.out.println("Enter shipping address city");
        String scity = inpObj.nextLine();
        System.out.println("Enter shiping address province");
        String sprovince = inpObj.nextLine();
        System.out.println("Enter shipping address postal code");
        String spostal = inpObj.nextLine();

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/project", "postgres", "postgres" )){
            // Query
            PreparedStatement pStmt = connection.prepareStatement("insert into placed_order (user_id, first_name, last_name, billing_street, billing_city, billing_province, billing_postal, shipping_street, shipping_city, shipping_province, shipping_postal, date_placed) values(?,?,?,?,?,?,?,?,?,?,?,?)");
            pStmt.setString(1, user_id);
            pStmt.setString(2, fname);
            pStmt.setString(3, lname);
            pStmt.setString(4, bstreet);
            pStmt.setString(5, bcity);
            pStmt.setString(6, bprovince);
            pStmt.setString(7, bpostal);
            pStmt.setString(8, sstreet);
            pStmt.setString(9, scity);
            pStmt.setString(10, sprovince);
            pStmt.setString(11, spostal);
            pStmt.setString(12, "2021-12-20");
            pStmt.executeUpdate();
            
        } catch (Exception sqle) {
            System.out.println(sqle);
        }
    }
    
    static void removeBook() {
    	if (!role.equals("Manager")) {
    		System.out.println(role);
    		System.out.println("You need to be logged in as a manager");
    		return;
    	}
    	
    	Scanner inpObj = new Scanner(System.in);
        System.out.println("Enter ISBN");
        String ISBN = inpObj.nextLine();
        
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/project", "postgres", "postgres" )){
            // Query
        	PreparedStatement pStmt = connection.prepareStatement("delete from book_author where isbn=?::NUMERIC");
            pStmt.setString(1, ISBN);
            pStmt.executeUpdate();
        	
            pStmt = connection.prepareStatement("delete from book where isbn=?::NUMERIC");
            pStmt.setString(1, ISBN);
            pStmt.executeUpdate();
            
        } catch (Exception sqle) {
            System.out.println(sqle);
        }
    }
    
    static void addBook() {
    	if (!role.equals("Manager")) {
    		System.out.println(role);
    		System.out.println("You need to be logged in as a manager");
    		return;
    	}
    	
    	Scanner inpObj = new Scanner(System.in);
        System.out.println("Enter ISBN");
        String ISBN = inpObj.nextLine();
        System.out.println("Enter title");
        String title = inpObj.nextLine();
        System.out.println("Enter author's first name");
        String fname = inpObj.nextLine();
        System.out.println("Enter author's last name");
        String lname = inpObj.nextLine();
        System.out.println("Enter the genre");
        String genre = inpObj.nextLine();
        System.out.println("Enter number of pages");
        String numPages = inpObj.nextLine();
        System.out.println("Enter price");
        String price = inpObj.nextLine();
        System.out.println("Enter publisher name");
        String pname = inpObj.nextLine();
        System.out.println("Enter publisher sales percentage");
        String salesPercent = inpObj.nextLine();
        System.out.println("Enter the stock of this book");
        String stock = inpObj.nextLine();

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/project", "postgres", "postgres" )){
            // Query
            PreparedStatement pStmt = connection.prepareStatement("insert into book (isbn, publisher_name, title, genre, num_pages, price, publisher_sales_percent, stock) values(?::NUMERIC,?,?,?,?::NUMERIC,?::NUMERIC,?::NUMERIC,?::NUMERIC)");
            pStmt.setString(1, ISBN);
            pStmt.setString(2, pname);
            pStmt.setString(3, title);
            pStmt.setString(4, genre);
            pStmt.setString(5, numPages);
            pStmt.setString(6, price);
            pStmt.setString(7, salesPercent);
            pStmt.setString(8, stock);
            pStmt.executeUpdate();
            
            pStmt = connection.prepareStatement("insert into book_author (ISBN, first_name, last_name) values(?,?,?)");
            pStmt.setString(1, ISBN);
            pStmt.setString(2, fname);
            pStmt.setString(3, lname);
            pStmt.executeUpdate();
            
        } catch (Exception sqle) {
            System.out.println(sqle);
        }
    	
    }
    
    static void search(int i) {         
    	Scanner inpObj = new Scanner(System.in);
    	
		 try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/project", "postgres", "postgres" )){
	         
			 // Query
			 PreparedStatement pStmt = connection.prepareStatement("");
			 String inp = "";
			 switch(i) {
			 case 0:
				 return;
			 case 1:
				 pStmt = connection.prepareStatement("select * from book natural join book_author where title=?");
				 System.out.println();
		         System.out.println("Type the title of the book");
		         inp = inpObj.nextLine();
		         pStmt.setString(1, inp);
				 break;
			 case 2:
				 pStmt = connection.prepareStatement("select * from book natural join book_author where last_name=?");
				 System.out.println();
		         System.out.println("Type the last name of the author");
		         inp = inpObj.nextLine();
		         pStmt.setString(1, inp);
				 break;
			 case 3:
				 pStmt = connection.prepareStatement("select * from book natural join book_author where genre=?");
				 System.out.println();
		         System.out.println("Type the genre");
		         inp = inpObj.nextLine();
		         pStmt.setString(1, inp);
				 break;
			 case 4:
				 pStmt = connection.prepareStatement("select * from book natural join book_author where publisher_name=?");
				 System.out.println();
		         System.out.println("Type the name of the publisher");
		         inp = inpObj.nextLine();
		         pStmt.setString(1, inp);
				 break;
			 case 5:
				// Show all
				 pStmt = connection.prepareStatement("select * from book natural join book_author");
				 break;
			 }
	         ResultSet resultSet = pStmt.executeQuery();
	         
	         int k = 0;
	         ArrayList<String> books = new ArrayList<String>();
	         while (resultSet.next()) {
	        	 k++;
	        	 System.out.printf(""+k+") ");
	             books.add(resultSet.getString("ISBN"));
	             System.out.printf("Title: ");
	             System.out.printf(resultSet.getString("title"));
	             System.out.printf("  Author: ");
	             System.out.printf(resultSet.getString("first_name"));
	             System.out.printf(" ");
	             System.out.printf(resultSet.getString("last_name"));
	             System.out.printf("  Genre: ");
	             System.out.printf(resultSet.getString("genre"));
	             System.out.printf("  Publisher: ");
	             System.out.println(resultSet.getString("publisher_name"));
	         }
	         
	         while (true) {
	             System.out.println();
		         System.out.println("Type the number of a book to add to cart");
		         System.out.println("0) Done");
		         inp = inpObj.nextLine();
		         
		         if (inp.equals("0")) {
		        	 break;
		         } else {
			         pStmt = connection.prepareStatement("select isbn from book natural join book_author where ISBN=?::NUMERIC");
			         pStmt.setString(1, books.get(Integer.parseInt(inp)-1));
			         resultSet = pStmt.executeQuery();
			         
			         while (resultSet.next()) {
			        	 cart.add(resultSet.getString("ISBN"));
			        	 System.out.println(cart);
			        	 continue;
			         }
		         }
	         }
	         
	     } catch (Exception sqle) {
	         System.out.println(sqle);
	     }
	 }

    static void registerUser() {
        Scanner inpObj = new Scanner(System.in);
        System.out.println("Enter username");
        String username = inpObj.nextLine();
        System.out.println("Enter password");
        String password = inpObj.nextLine();
        System.out.println("Enter first name");
        String fname = inpObj.nextLine();
        System.out.println("Enter last name");
        String lname = inpObj.nextLine();
        System.out.println("Enter role (\"Customer\" or \"Manager\")");
        String role = inpObj.nextLine();

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/project", "postgres", "postgres" )){
            // Query
            PreparedStatement pStmt = connection.prepareStatement("insert into program_user (username, password, first_name, last_name, role) values(?,?,?,?,?)");
            pStmt.setString(1, username);
            pStmt.setString(2, password);
            pStmt.setString(3, fname);
            pStmt.setString(4, lname);
            pStmt.setString(5, role);
            pStmt.executeUpdate();
        } catch (Exception sqle) {
            System.out.println(sqle);
        }
    }
    
    static void login() {
    	Scanner inpObj = new Scanner(System.in);
        System.out.println("Enter username");
        String username = inpObj.nextLine();
        System.out.println("Enter password");
        String password = inpObj.nextLine();
        
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/project", "postgres", "postgres" )){
            // Query
            PreparedStatement pStmt = connection.prepareStatement("select * from program_user where username=? and password=?");
            pStmt.setString(1, username);
            pStmt.setString(2, password);
            
            ResultSet resultSet = pStmt.executeQuery();
            while (resultSet.next()) {
                System.out.println("Successfully logged in");
                user_id = resultSet.getString("user_id"); 
                role = resultSet.getString("role");
                System.out.println(role);
                System.out.println(user_id);
                return;
            }
            System.out.println("Login failed");
        } catch (Exception sqle) {
            System.out.println(sqle);
        }
    }
}
