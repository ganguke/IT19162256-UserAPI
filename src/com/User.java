package com;

import java.sql.*;

public class User {
	
	private Connection connect() 
	 { 
	 Connection con = null; 
	 try
	 { 
	 Class.forName("com.mysql.jdbc.Driver"); 
	 
	 //Provide the correct details: DBServer/DBName, username, password 
	 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/users", "root", ""); 
	 } 
	 catch (Exception e) 
	 {e.printStackTrace();} 
	 return con; 
	 } 
	
	
	public String insertUser( String userName, String email, String password, String address) 
	 { 
	 String output = ""; 
	 try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 {return "Error while connecting to the database for inserting."; } 
	 // create a prepared statement
	 String query = " insert into user (`userID`,`userName`,`email`,`password`,`address`)"
	 + " values (?, ?, ?, ?, ?)"; 
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 // binding values
	 preparedStmt.setInt(1, 0); 
	 preparedStmt.setString(2, userName); 
	 preparedStmt.setString(3, email); 
	 preparedStmt.setString(4, password); 
	 preparedStmt.setString(5, address); 
 
	// execute the statement
	 preparedStmt.execute(); 
	 con.close();
	 
	 String newUsers = readUser();
	 output = "{\"status\":\"success\", \"data\": \"" + 
			 newUsers + "\"}";
	 } 
	 catch (Exception e) 
	 { 
	 output = "{\"status\":\"error\", \"data\": \"Error while inserting the User.\"}";  
	 System.err.println(e.getMessage()); 
	 } 
	 return output; 
	 }
	
	
	public String readUser() 
	 { 
	 String output = ""; 
	 try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 {return "Error while connecting to the database for reading."; } 
	 // Prepare the html table to be displayed
	 output = "<table border='1'><tr>" +
	 "<th>userName</th>" + 
	 "<th>email</th>" +
     "<th>password</th>"+
      "<th>address</th>"+
	 "<th>Update</th><th>Remove</th></tr>"; 
	 
	 String query = "select * from user"; 
	 Statement stmt = con.createStatement(); 
	 ResultSet rs = stmt.executeQuery(query); 
	 // iterate through the rows in the result set
	 while (rs.next()) 
	 { 
	 String userID = Integer.toString(rs.getInt("userID"));  
	 String userName = rs.getString("userName"); 
	 String email = rs.getString("email"); 
	 String password = rs.getString("password"); 
	 String address = rs.getString("address"); 
	 
	 
	 // Add into the html table
	 output += "<tr><td><input id='hidUserIDUpdate' name='hidUserIDUpdate' type='hidden' value='" + userID
			 + "'>" + userName + "</td>"; 
			 output += "<td>" + email + "</td>"; 
			 output += "<td>" + password + "</td>"; 
			 output += "<td>" + address + "</td>"; 
     
     
	 // buttons
	 output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
				+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-userid='"
				+ userID + "'>" + "</td></tr>"; 
	 
	 } 
	 con.close(); 
	 // Complete the html table
	 output += "</table>"; 
	 } 
	 catch (Exception e) 
	 { 
	 output = "Error while reading the user."; 
	 System.err.println(e.getMessage()); 
	 } 
	 return output; 
	 } 
	
	
	public String updateUser(String userID, String userName, String email, String password, String address)
	{ 
		 String output = ""; 
		 try
		 { 
		 Connection con = connect(); 
		 if (con == null) 
		 {return "Error while connecting to the database for updating."; } 
		 // create a prepared statement
		 String query = "UPDATE user SET userName=?,email=?,password=?,address=? WHERE userID=?"; 
		 PreparedStatement preparedStmt = con.prepareStatement(query); 
		 // binding values
		 preparedStmt.setString(1, userName); 
		 preparedStmt.setString(2, email); 
		 preparedStmt.setString(3, password); 
		 preparedStmt.setString(4, address);
		 preparedStmt.setInt(5, Integer.parseInt(userID)); 
		 
		 // execute the statement
		 preparedStmt.execute(); 
		 con.close(); 
		 
		 String newUsers = readUser();
		 output = "{\"status\":\"success\", \"data\": \"" + 
				 newUsers + "\"}";
		 } 
		 catch (Exception e) 
		 { 
		 output = "{\"status\":\"error\", \"data\": \"Error while updating the user.\"}"; 
		 System.err.println(e.getMessage()); 
		 } 
		 return output; 
		 } 
	
	
	
	public String deleteUser(String userID) 
	 { 
	 String output = ""; 
	 try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 {return "Error while connecting to the database for deleting."; } 
	 // create a prepared statement
	 String query = "delete from user where userID=?"; 
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 // binding values
	 preparedStmt.setInt(1, Integer.parseInt(userID));
	 // execute the statement
	 preparedStmt.execute(); 
	 con.close(); 
	 
	 String newUsers = readUser();
	 output = "{\"status\":\"success\", \"data\": \"" + 
			 newUsers + "\"}"; 
	 } 
	 catch (Exception e) 
	 { 
		 output = "{\"status\":\"error\", \"data\": \"Error while deleting the user.\"}";
	 System.err.println(e.getMessage()); 
	 } 
	 return output; 
	 } 

}
