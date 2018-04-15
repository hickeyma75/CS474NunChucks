package project3Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * MySQLAccess.java
 * 
 * @author Hickey, Burkey Handles all of the mySQL interfacing from the Orders.java program.
 *  
 * Group Name: Nunchuck Secret Agents
 */
public class MySQLAccess {

  private Connection connect = null;
  private Statement statement = null;
  private ResultSet resultSet = null;

  // This is where the mysql user information can be changed.
  final private String host = "mysql.cs.jmu.edu";
  final private String user = "hickeyma";
  final private String passwd = "cs474";
  final private String database = "Orders";

  /**
   * connectToDB Creates the conenction to the database with the host, user, passwd,
   * database @author, Hickey, Burkey
   * 
   * @throws Exception
   */
  public void connectToDB() throws Exception {
    try {
      // This will load the MySQL driver, each DB has its own driver
      Class.forName("com.mysql.jdbc.Driver");

      // Setup the connection with the DB
      connect = DriverManager.getConnection(
          "jdbc:mysql://" + host + "/" + database + "?" + "user=" + user + "&password=" + passwd);
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * insertCustomer inserts a customer into the mysql database from the Orders UI.
   * 
   * @author Hickey, Burkey
   * 
   * @param custNum customer number from the UI
   * @param name name from the UI
   * @param city city from the UI
   * @param credLimit credit limit from the UI
   * @param salesNum sales person number from the UI
   * @throws Exception
   */
  public void insertCustomer(String custNum, String name, String city, String credLimit,
      String salesNum) throws Exception {
    try {
      statement = connect.createStatement();
      ArrayList<String> custNumArray = new ArrayList<>();
      ArrayList<String> salesNumArray = new ArrayList<>();

      resultSet = statement.executeQuery("SELECT DISTINCT custNum FROM " + database + ".Customer");

      while (resultSet.next()) {
        custNumArray.add(resultSet.getString("custNum"));
      }

      resultSet =
          statement.executeQuery("SELECT DISTINCT salesNum FROM " + database + ".Salesperson");
      while (resultSet.next()) {
        salesNumArray.add(resultSet.getString("salesNum"));
      }

      if (custNumArray.contains(custNum)) {
        System.out.println("Customer number already exists in table. Insert Failed.");
        return;
      }

      if (!salesNumArray.contains(salesNum)) {
        System.out.println("No salesperson with that sales number. Insert Failed.");
        return;
      }

      String insertQuery = "INSERT INTO " + database
          + ".Customer(custNum, name, city, credLimit, salesNum) VALUES('" + custNum + "', '" + name
          + "', '" + city + "', '" + credLimit + "', '" + salesNum + "');";

      PreparedStatement pstmt = connect.prepareStatement(insertQuery);
      pstmt.executeUpdate();

      System.out.println("Insert Sucessful!");
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * readCustomers reads all of the customers and the total of their amounts from all of their
   * purchases.
   * 
   * @author Hickey, Burkey
   * @param begin Which customer number to start on
   * @param end Which customer number to end on
   * @throws Exception
   */
  public void readCustomers(String begin, String end) throws Exception {
    try {
      Scanner scanner = new Scanner(System.in);
      statement = connect.createStatement();

      System.out.println(
          "Customer Information Table\n Order table by(custNum, name, credLimit, salesNum:");
      String orderBy = scanner.nextLine();

      if (orderBy.equals("credLimit")) {
        orderBy = orderBy + " DESC";
      }

      resultSet = statement.executeQuery(
          "SELECT c.*, SUM(amount) AS amount FROM " + database + ".Customer c JOIN " + database
              + ".CustOrder co ON c.custNum = co.custNum GROUP BY custNum ORDER BY " + orderBy);

      if (begin.equals("") && end.equals("")) {
        printTable(resultSet, 0000, 9999);
      } else if (begin.equals("") && !end.equals("")) {
        printTable(resultSet, 0000, Integer.parseInt(end));
      } else if (!begin.equals("") && end.equals("")) {
        printTable(resultSet, Integer.parseInt(begin), 9999);
      } else {
        printTable(resultSet, Integer.parseInt(begin), Integer.parseInt(end));
      }
      scanner.close();
    } catch (Exception e) {
      throw e;
    }

  }

  /**
   * Close Closes the result sets and the database connection.
   * 
   * @author Hickey, Burkey
   */
  public void close() {
    try {
      if (resultSet != null) {
        resultSet.close();
      }

      if (statement != null) {
        statement.close();
      }

      if (connect != null) {
        connect.close();
      }
    } catch (Exception e) {

    }
  }

  // Prints the table for printing all cusotomer information.
  private void printTable(ResultSet resultSet, int begin, int end) throws SQLException {
    System.out.println("custNum\t\tname\t\tcity\t\tCredit Limit\t\tSalesNumber\t\tTotal Amount"
        + "\n________________________________________________________________"
        + "_______________________________________________________");

    while (resultSet.next()) {
      String custNum = resultSet.getString("custNum");
      String name = resultSet.getString("name");
      String city = resultSet.getString("city");
      String credLimit = Double.toString(resultSet.getDouble("credLimit"));
      String salesNum = resultSet.getString("salesNum");
      String totalAmount = Double.toString(resultSet.getDouble("amount"));
      if (Integer.parseInt(custNum) >= begin && Integer.parseInt(custNum) <= end) {
        System.out.println(String.format(
            "custNum:%s\t|Name: %s\t|City: %s\t|Credit Limit: %s\t|Sales Number: %s\t|Total Amount: %s\t|",
            custNum, name, city, credLimit, salesNum, totalAmount));
        System.out.print("________________________________________________________________"
            + "_______________________________________________________\n");
      }
    }
    System.out.println();
  }

}
