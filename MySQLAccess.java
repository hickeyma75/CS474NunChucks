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
 * @author Hickey, Burkey Handles all of the mySQL interfacing from the CS474Project3Driver.java program.
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
  final private String database = "ManuscriptTesting";

  /**
   * connectToDB Creates the conenction to the database with the host, user, passwd,
   * database @author, Hickey
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
  public ArrayList<String> getChantIDs(String location, String date, String event) throws Exception {
    try {
      statement = connect.createStatement();
      ArrayList<String> chantIDArray = new ArrayList<>();
      
      //SELECT DISTINCT chantID, provenanceID FROM Feast JOIN Chant ON Chant.feastID = Feast.FeastID JOIN Section ON Chant.libSiglum=Section.libSiglum AND Chant.msSiglum=Section.msSiglum AND Chant.sectionID=Section.sectionID WHERE feastDate="Dec.25" AND chantID IS NOT NULL AND provenanceID = "Rome";
      
      if (location != "" && event != "" && date != "") {resultSet = statement.executeQuery("SELECT DISTINCT chantID, provenanceID "
        		+ "FROM " + database +".Feast JOIN Chant ON " + database + ".Chant.feastID = " + database 
          		+ ".Feast.FeastID JOIN " + database + ".Section "
          		+ "ON " + database + ".Chant.libSiglum=" + database + ".Section.libSiglum AND "
          		+ database + ".Chant.msSiglum=Section.msSiglum AND "
          		+ database + ".Chant.sectionID=Section.sectionID WHERE feastDate LIKE '%" + date + "%'"
          		+ "AND chantID IS NOT NULL AND provenanceID LIKE '%" + location + "%' AND feastDescription LIKE '%" + event + "%';");}

      else if (location == "" && event == "") {resultSet = statement.executeQuery("SELECT DISTINCT chantID, provenanceID "
        		+ "FROM " + database +".Feast JOIN Chant ON " + database + ".Chant.feastID = " + database 
          		+ ".Feast.FeastID JOIN " + database + ".Section "
          		+ "ON " + database + ".Chant.libSiglum=" + database + ".Section.libSiglum AND "
          		+ database + ".Chant.msSiglum=Section.msSiglum AND "
          		+ database + ".Chant.sectionID=Section.sectionID WHERE feastDate LIKE '%" + date + "%'"
          		+ "AND chantID IS NOT NULL AND provenanceID IS NOT NULL;");}
    
      else if (location == "" && date == "") {resultSet = statement.executeQuery("SELECT DISTINCT chantID, provenanceID "
        		+ "FROM " + database + ".Feast JOIN Chant ON " + database + ".Chant.feastID = " + database 
          		+ ".Feast.FeastID JOIN " + database + ".Section "
          		+ "ON " + database + ".Chant.libSiglum=" + database + ".Section.libSiglum AND "
          		+ database + ".Chant.msSiglum=Section.msSiglum AND "
          		+ database + ".Chant.sectionID=Section.sectionID WHERE feastDate IS NOT NULL "
          		+ "AND chantID IS NOT NULL AND provenanceID IS NOT NULL AND feastDescription LIKE '%" + event + "%';");}
      
      else if (location == "") {resultSet = statement.executeQuery("SELECT DISTINCT chantID, provenanceID "
        		+ "FROM " + database +".Feast JOIN Chant ON " + database + ".Chant.feastID = " + database 
        		+ ".Feast.FeastID JOIN " + database + ".Section "
        		+ "ON " + database + ".Chant.libSiglum=" + database + ".Section.libSiglum AND "
        		+ database + ".Chant.msSiglum=Section.msSiglum AND "
        		+ database + ".Chant.sectionID=Section.sectionID WHERE feastDate LIKE '%" + date + "%'"
        		+ "AND chantID IS NOT NULL AND provenanceID IS NOT NULL AND feastDescription LIKE '%" + event + "%';");}
      
      
      else if (date == "" && event == "") {resultSet = statement.executeQuery("SELECT DISTINCT chantID, provenanceID "
        		+ "FROM " + database + ".Feast JOIN Chant ON " + database + ".Chant.feastID = " + database 
          		+ ".Feast.FeastID JOIN " + database + ".Section "
          		+ "ON " + database + ".Chant.libSiglum=" + database + ".Section.libSiglum AND "
          		+ database + ".Chant.msSiglum=Section.msSiglum AND "
          		+ database + ".Chant.sectionID=Section.sectionID WHERE feastDate IS NOT NULL "
          		+ "AND chantID IS NOT NULL AND provenanceID = \"" + location + "\";");}
      
      else if (date == "") {resultSet = statement.executeQuery("SELECT DISTINCT chantID, provenanceID "
        		+ "FROM " + database + ".Feast JOIN Chant ON " + database + ".Chant.feastID = " + database 
        		+ ".Feast.FeastID JOIN " + database + ".Section "
        		+ "ON " + database + ".Chant.libSiglum=" + database + ".Section.libSiglum AND "
        		+ database + ".Chant.msSiglum=Section.msSiglum AND "
        		+ database + ".Chant.sectionID=Section.sectionID WHERE feastDate IS NOT NULL "
        		+ "AND chantID IS NOT NULL AND provenanceID LIKE '%" + location + "%' AND feastDescription LIKE '%" + event + "%';");}
      
      else {resultSet = statement.executeQuery("SELECT DISTINCT chantID, provenanceID "
      		+ "FROM " + database +".Feast JOIN Chant ON " + database + ".Chant.feastID = " + database 
      		+ ".Feast.FeastID JOIN " + database + ".Section "
      		+ "ON " + database + ".Chant.libSiglum=" + database + ".Section.libSiglum AND "
      		+ database + ".Chant.msSiglum=Section.msSiglum AND "
      		+ database + ".Chant.sectionID=Section.sectionID WHERE feastDate LIKE '%" + date + "%' "
      		+ "AND chantID IS NOT NULL AND provenanceID LIKE '%" + location + "%';");}
      
      while (resultSet.next()) {
      	chantIDArray.add(resultSet.getString("chantID"));
      }

      System.out.println("Done");
      resultSet = null;
      //String insertQuery = "INSERT INTO " + database
      //    + ".Customer(custNum, name, city, credLimit, salesNum) VALUES('" + custNum + "', '" + name
      //    + "', '" + city + "', '" + credLimit + "', '" + salesNum + "');";

      //PreparedStatement pstmt = connect.prepareStatement(insertQuery);
      //pstmt.executeUpdate();
      for (int i = 0; i < chantIDArray.size(); i++) {
    	  System.out.println(chantIDArray.get(i));
      }
      
      return chantIDArray;
      } catch (Exception e) {
      throw e;
    }
  }

  public String getManuscriptInformation(String chantID) throws SQLException {
	  String formattedInfo = new String();
	  
	  resultSet = statement.executeQuery("SELECT DISTINCT chantID, provenanceID, "
	  		    + "feastName, feastDescription, Feast.feastID, feastDate, feastNotes, Chant.libSiglum, Chant.msSiglum, "
	  		    + "msFullText, provenanceDetail, liturgicalOccasion "
	      		+ "FROM " + database +".Feast JOIN Chant ON " + database + ".Chant.feastID = " + database 
	      		+ ".Feast.FeastID JOIN " + database + ".Section "
	      		+ "ON " + database + ".Chant.libSiglum=" + database + ".Section.libSiglum AND "
	      		+ database + ".Chant.msSiglum=Section.msSiglum AND "
	      		+ database + ".Chant.sectionID=Section.sectionID WHERE chantID = \""+ chantID +"\";");
	  
	  if(resultSet.getFetchSize() > 1){
		  formattedInfo = "Error.  More than one Chant found for " + chantID;
	  }
	  else {
		  resultSet.next();
		  formattedInfo = "Chant Information for Selected Chant\n\n"
		  		+ "Chant ID: " + resultSet.getString("chantID") +"\nManuscript Siglum: " + resultSet.getString("msSiglum") +""
		  		+ "\nLibrary Siglum: " + resultSet.getString("libSiglum") +"\nProvenance ID: "+ resultSet.getString("provenanceID") + ""
		  		+ "\nProvenance Name: " + resultSet.getString("provenanceDetail") + "\n\nLiturgical Occasion: " + resultSet.getString("liturgicalOccasion") + ""
		  		+ "\n\nFeast Information\nFeast ID: " + resultSet.getString("feastID") + "\nFeast Name: " + resultSet.getString("feastName") +""
		  		+ "\nFeast Notes: " + resultSet.getString("feastNotes") + "\n\nSnapshot of the Full Manuscript Text:\n " + resultSet.getString("msFullText").substring(0, 40) 
		  		+"...\n\n END OF RECORD";
	  }
	  resultSet = null;
	  return formattedInfo;
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


}
