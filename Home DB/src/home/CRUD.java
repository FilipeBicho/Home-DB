package home;

/* This class is used to Create Read Update and Delete from the database
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CRUD {

	//Elements are private to be access just in this class
	private String url;
	private String user;
	private String pass;
	
	//Variable to connect to database
	Connection connection = null;
	//Variable to create a statement
	static Statement statement = null;
	//Variable to create a prepared statement
	static PreparedStatement preparedStatement = null;
	//Variable to create CallableStatement statement
	static CallableStatement callableStatement = null;
	//Variable to read file
	FileInputStream inputFile = null;
	FileOutputStream outputFile = null;
	//Variable to get inputStream
	InputStream inputStream = null;
	
	//Read user input
	static Scanner input = new Scanner(System.in);
	
	//Constructor to initialize elements
	CRUD (String url, String user, String pass) throws ClassNotFoundException
	{
		//Word this is a reference for the current object
		this.url = url;
		this.user = user;
		this.pass = pass;	
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//1. Get a connection to database
			 connection = DriverManager.getConnection(url, user, pass);
			
			//2. Create a Statement
			statement = connection.createStatement();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Define getters and setters of the global variables
	public String getUrl() { return url; }
	public String getUser() { return user; }
	public String getPass() { return pass; }
	public void setUrl(String url) { this.url = url; }
	public void setUser(String user) { this.user = user; }
	public void setPass(String pass) { this.pass = pass; }

	
	// Method to insert values into Bedroom table
	public void insertIntoBedroom() throws SQLException
	{
		
		String name, color, store;
		int quantity;
		float price;
		
		// Get the values that go into table
		System.out.print("Insert name: ");
		name = input.nextLine();
		
		System.out.print("Insert store: ");
		store = input.nextLine();
		
		System.out.print("Insert price: ");
		price = input.nextFloat();
		
		System.out.print("Insert quantity: ");
		quantity = input.nextInt();
		
		System.out.print("Insert color: ");
		input.nextLine();
		color = input.nextLine();

		//Insert query
		String query = "insert into Bedroom" 
				+ "(name, store, price, quantity, color)"
				+ "values (?,?,?,?,?)";
		
		//Prepare statement
		preparedStatement = connection.prepareStatement(query);
		
		//Set parameters
		preparedStatement.setString(1, name);
		preparedStatement.setString(2, store);
		preparedStatement.setFloat(3, price);
		preparedStatement.setInt(4, quantity);
		preparedStatement.setString(5, color);
		
		//Execute query
		preparedStatement.executeUpdate();
		
		//Close preparedStatement
		if(preparedStatement != null)
		{
			preparedStatement.close();
		}
		
		System.out.println();
		System.out.println("Values inserted to table Bedroom with sucess!");			
	}
	
	// Method to insert values into Bedroom table
	public void insertInto(Bedroom item) throws SQLException
	{

		//Insert query
		String query = "insert into Bedroom" 
				+ "(name, store, price, quantity, color)"
				+ "values (?,?,?,?,?)";
		
		//Prepare statement
		preparedStatement = connection.prepareStatement(query);
		
		//Set parameters
		preparedStatement.setString(1, item.getName());
		preparedStatement.setString(2, item.getStore());
		preparedStatement.setFloat(3, item.getPrice());
		preparedStatement.setInt(4, item.getQuantity());
		preparedStatement.setString(5, item.getColor());
		
		//Execute query
		preparedStatement.executeUpdate();
		
		//Close preparedStatement
		if(preparedStatement != null)
		{
			preparedStatement.close();
		}
		
		System.out.println();
		System.out.println("Values inserted to table Bedroom with sucess!");			
	}

	//Method to show all elements of bedReoom table
	public void readBedroom() throws SQLException
	{
		// Select all columns from bedroom table
		String query = "Select * from Bedroom";
		
		// The ResultSet object maintains a cursor pointing to its current row of data
		ResultSet result = statement.executeQuery(query);
		
		// Process the result of the query
		System.out.println("id	name	quantity"
				+ "	price	color	store	priority\n");
		while(result.next())
		{
			System.out.println(result.getString("id") + "	" +
					result.getString("name") + "	" +
					result.getString("quantity") + "		" + 
					result.getString("price") + "	" +
					result.getString("color") + "	" +
					result.getString("store") + "	" +
					result.getString("priority") + "	");
		}
	}
	
	//Method to update a element in the row and table given by the user
	public void updateValue() throws SQLException
	{
		//Get table name
		System.out.print("Insert table to update: ");
		String table = input.nextLine();
		
		System.out.println();
		//Show table vaues
		if(table.equals("Bedroom"))
			readBedroom();
		System.out.println();

		//Get table line
		System.out.print("Insert line to update: ");
		String id = input.nextLine();
		
		//Get table column
		System.out.print("Insert column to update: ");
		String column = input.nextLine();
		
		//Set the new value
		System.out.print("Insert new value: ");
		String value = input.nextLine();
		
		//Set which row to update
		String query = "Update " + table + " set " + column + "='" + value + "' "
				+ "where id=" + id;
		
		//Update table
		statement.executeUpdate(query);
		
		//Close preparedStatement
		if(preparedStatement != null)
		{
			preparedStatement.close();
		}
		
		System.out.println("Update complete!");
	}
	
	//Method to update a element with preparedstatement
	public void updateValuePrepared() throws SQLException
	{
		//Get table name
		System.out.print("Insert table to update: ");
		String table = input.nextLine();
		
		System.out.println();
		//Show table values
		if(table.equals("Bedroom"))
			readBedroom();
		System.out.println();

		//Get table line
		System.out.print("Insert line to update: ");
		int id = input.nextInt();
		
		//Get table column
		System.out.print("Insert column to update: ");
		input.nextLine();
		String column = input.nextLine();
		
		//Set the new value
		System.out.print("Insert new value: ");
		String value = input.nextLine();
		
		//Set update query
		String query = "Update " + table + " set " + column + " = ? where id= ?";
		
		//Prepare statement
		preparedStatement = connection.prepareStatement(query);
		
		//Set parameters
		preparedStatement.setString(1, value);
		preparedStatement.setInt(2, id);
		
		//Close preparedStatement
		if(preparedStatement != null)
		{
			preparedStatement.close();
		}
		
		//Execute query
		preparedStatement.executeUpdate();
		
		//Close preparedStatement
		if(preparedStatement != null)
		{
			preparedStatement.close();
		}
		
	}
	
	//Method to delete a row from table where the id is given by the user
	public void deleteRow() throws SQLException
	{
		//Get table name
		System.out.print("Insert table where to delete: ");
		String table = input.nextLine();
		
		System.out.println();
		//Show table vaues
		if(table.equals("Bedroom"))
			readBedroom();
		System.out.println();

		//Get table line
		System.out.print("Insert line to delete: ");
		String id = input.nextLine();
		
		//Set which row to delete
		String query = "Delete from " + table
				+ " where id=" + id;
		
		//Execute deletion
		statement.executeUpdate(query);
		
		//Close preparedStatement
		if(statement != null)
		{
			statement.close();
		}
		
		System.out.println("Row was deleted successfuly");
	}
	
	//Method to call a callablestatement
	public void increasePrice() throws SQLException
	{
		//Initialize where to increase and how much
		String name = "bed";
		float increase = 100;
		
		//Prepare the stored procedure call
		callableStatement = connection.prepareCall("{call increment_price(?,?)}");
		
		//Set parameters
		callableStatement.setString(1, name);
		callableStatement.setFloat(2, increase);
		
		//Execute stored procedure
		callableStatement.execute();
		
		//Close preparedStatement
		if(callableStatement != null)
		{
			callableStatement.close();
		}
		
	}

	//Method to count the number of beds
	public void count() throws SQLException 
	{
		callableStatement = null;
		
		//Initialize where to increase and how much
		String name = "bed";
		
		//Prepare the stored procedure call
		callableStatement = connection.prepareCall("{call count(?,?)}");
		
		//Set parameters
		callableStatement.setString(1, name);
		callableStatement.registerOutParameter(2, Types.INTEGER);
		
		//Execute stored procedure
		callableStatement.execute();
		
		//Close preparedStatement
		if(callableStatement != null)
		{
			callableStatement.close();
		}
		
		System.out.println(callableStatement.getInt(2));
	}
	
	//Method to create a transaction 
	public void transaction() throws SQLException 
	{
		//Turn off auto commit
		connection.setAutoCommit(false);
		
		//Transaction step 1
		// Delete all beds that the price is higher then 0
		statement = connection.createStatement();
		statement.executeUpdate("delete from bedroom where price > 0 and name='bed'");
		
		//Transaction step 2
		// Update the color of the lamp to white
		statement.executeUpdate("update bedroom set color='white' where name='lamp'");
		
		//Ask user if is ok to commit
		System.out.println("It is ok to commit? (1: yes, 2: no)");
		int okToCommit = input.nextInt();
		
		if(okToCommit == 1)
		{
			//Store in the database
			connection.commit();
			System.out.println("Transaction commited");
		}
		else
		{
			//discard
			connection.rollback();
			System.out.println("Transaction aborted");
		}
		
		//Close preparedStatement
		if(connection != null)
		{
			connection.close();
		}
	}
	
	//Read pdf and write as a blob to the database
	public void readFileAndSave() throws SQLException, FileNotFoundException
	{
		//Prepare a statement 
		String query = "update Bedroom set file=? where name='bed'";
		preparedStatement = connection.prepareStatement(query);
		
		//set parameter for pdf file name
		File file = new File("home.pdf");
		inputFile = new FileInputStream(file);
		preparedStatement.setBinaryStream(1, inputFile);
		
		System.out.println("Reading file " + file.getAbsolutePath());
		
		//Execute statement
		preparedStatement.executeUpdate();
		
		System.out.println("Completed!");
	}
	
	//Read blob from database
	public void readBlob () throws SQLException, IOException
	{
		//Prepare a statement 
		statement = connection.createStatement();
		String query = "select * from bedroom where name='bed'";
		ResultSet result = statement.executeQuery(query);
		
		//set parameter for pdf file name
		File file = new File("home.pdf");
		outputFile = new FileOutputStream(file);
		
		if(result.next())
		{
			inputStream = result.getBinaryStream("file");
			
			byte[] buffer = new byte[1024];
			while(inputStream.read(buffer)> 0)
			{
				outputFile.write(buffer);
			}
		}
	}
	
	//Insert a list of object into the table bedroom
	public void insertObjectIntoBedroom(ArrayList<Bedroom> itens)
	{
		if(itens != null)
		{
			for(Bedroom item : itens)
			{
				System.out.println("name: " + item.getName());
				System.out.println("store: " + item.getStore());
				System.out.println("price: " + item.getPrice());
				System.out.println("quantity: " + item.getQuantity());
				System.out.println("color: " + item.getColor());
			}
			
			for(Bedroom item : itens)
			{
				preparedStatement = null;
				
				String query = "insert into bedroom "
						+ "(name, store, price, quantity, color) "
						+ "values (?,?,?,?,?)";
				
				try {
					//Prepare statement
					preparedStatement = connection.prepareStatement(query);
					
					preparedStatement.setString(1, item.getName());
					preparedStatement.setString(2, item.getStore());
					preparedStatement.setFloat(3, item.getPrice());
					preparedStatement.setInt(4, item.getQuantity());
					preparedStatement.setString(5, item.getColor());
					
					preparedStatement.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			}	
			if(preparedStatement != null )
			{
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else
		{
			System.out.println("Bedroom object is empty!");
		}
		
	}
	
	//Insert elements from database into object
	@SuppressWarnings("null")
	public ArrayList<Bedroom> insertTableIntoObj()
	{
		ArrayList<Bedroom> temp = new ArrayList<Bedroom>();
		String name, store,color;
		int quantity;
		float price;
		
		// Select all columns from bedroom table
		String query = "Select * from Bedroom";
		//Create a Statement
		try {
			statement = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			
			// The ResultSet object maintains a cursor pointing to its current row of data
			ResultSet result = statement.executeQuery(query);

			while(result.next())
			{
				temp.add(new Bedroom(
						result.getString("name"),
						result.getString("store"), 
						result.getFloat("price"),
						result.getInt("quantity"),
						result.getString("color")
						));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return temp;
	}
}
