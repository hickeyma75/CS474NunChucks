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
  public ArrayList<String> getChantIDs(String location, String date, String event, String msSiglum) throws Exception {
    try {
      statement = connect.createStatement();
      ArrayList<String> chantIDArray = new ArrayList<>();
      
      //SELECT DISTINCT chantID, provenanceID FROM Feast JOIN Chant ON Chant.feastID = Feast.FeastID JOIN Section ON Chant.libSiglum=Section.libSiglum AND Chant.msSiglum=Section.msSiglum AND Chant.sectionID=Section.sectionID WHERE feastDate="Dec.25" AND chantID IS NOT NULL AND provenanceID = "Rome";
      
      if (!location.equals("") && !event.equals("") && !date.equals("")) {resultSet = statement.executeQuery("SELECT chantID, msIncipit "
        		+ "FROM " + database +".Feast JOIN Chant ON " + database + ".Chant.feastID = " + database 
          		+ ".Feast.FeastID JOIN " + database + ".Section "
          		+ "ON " + database + ".Chant.libSiglum=" + database + ".Section.libSiglum AND "
          		+ database + ".Chant.msSiglum=Section.msSiglum AND "
          		+ database + ".Chant.sectionID=Section.sectionID WHERE feastDate = \"" + date + "\""
          		+ "AND chantID IS NOT NULL AND provenanceID = \"" + location + "\" AND feastDescription LIKE '%" + event 
          		+ "%' AND Chant.msSiglum = \"" + msSiglum +"\";");}

      else if (location.equals("") && event.equals("")) {resultSet = statement.executeQuery("SELECT chantID "
        		+ "FROM " + database +".Feast JOIN Chant ON " + database + ".Chant.feastID = " + database 
          		+ ".Feast.FeastID JOIN " + database + ".Section "
          		+ "ON " + database + ".Chant.libSiglum=" + database + ".Section.libSiglum AND "
          		+ database + ".Chant.msSiglum=Section.msSiglum AND "
          		+ database + ".Chant.sectionID=Section.sectionID WHERE feastDate = \"" + date + "\""
          		+ "AND chantID IS NOT NULL AND provenanceID IS NOT NULL AND feastDescription LIKE '%" + event + "%'"
          		+ " AND Chant.msSiglum = \"" + msSiglum +"\";");}
    
      else if (location.equals("") && date.equals("")) {resultSet = statement.executeQuery("SELECT chantID "
        		+ "FROM " + database + ".Feast JOIN Chant ON " + database + ".Chant.feastID = " + database 
          		+ ".Feast.FeastID JOIN " + database + ".Section "
          		+ "ON " + database + ".Chant.libSiglum=" + database + ".Section.libSiglum AND "
          		+ database + ".Chant.msSiglum=Section.msSiglum AND "
          		+ database + ".Chant.sectionID=Section.sectionID WHERE feastDate IS NOT NULL "
          		+ "AND chantID IS NOT NULL AND provenanceID IS NOT NULL AND feastDescription LIKE '%" + event + "%' "
          		+ " AND Chant.msSiglum = \"" + msSiglum +"\";");}
      
      else if (location.equals("")) {resultSet = statement.executeQuery("SELECT chantID "
        		+ "FROM " + database +".Feast JOIN Chant ON " + database + ".Chant.feastID = " + database 
        		+ ".Feast.FeastID JOIN " + database + ".Section "
        		+ "ON " + database + ".Chant.libSiglum=" + database + ".Section.libSiglum AND "
        		+ database + ".Chant.msSiglum=Section.msSiglum AND "
        		+ database + ".Chant.sectionID=Section.sectionID WHERE feastDate = \"" + date + "\""
        		+ "AND chantID IS NOT NULL AND provenanceID IS NOT NULL AND feastDescription LIKE '%" + event + "%'"
          		+ " AND Chant.msSiglum = \"" + msSiglum +"\";");}
      
      
      else if (date.equals("") && event.equals("")) {resultSet = statement.executeQuery("SELECT chantID "
        		+ "FROM " + database + ".Feast JOIN Chant ON " + database + ".Chant.feastID = " + database 
          		+ ".Feast.FeastID JOIN " + database + ".Section "
          		+ "ON " + database + ".Chant.libSiglum=" + database + ".Section.libSiglum AND "
          		+ database + ".Chant.msSiglum=Section.msSiglum AND "
          		+ database + ".Chant.sectionID=Section.sectionID WHERE feastDate IS NOT NULL "
          		+ "AND chantID IS NOT NULL AND provenanceID = \"" + location + "\""
          		+ " AND Chant.msSiglum = \"" + msSiglum +"\";");}
      
      else if (date.equals("")) {resultSet = statement.executeQuery("SELECT chantID "
        		+ "FROM " + database + ".Feast JOIN Chant ON " + database + ".Chant.feastID = " + database 
        		+ ".Feast.FeastID JOIN " + database + ".Section "
        		+ "ON " + database + ".Chant.libSiglum=" + database + ".Section.libSiglum AND "
        		+ database + ".Chant.msSiglum=Section.msSiglum AND "
        		+ database + ".Chant.sectionID=Section.sectionID WHERE feastDate IS NOT NULL "
        		+ "AND chantID IS NOT NULL AND provenanceID = \"" + location + "\" AND feastDescription LIKE '%" + event + "%'"
          		+ " AND Chant.msSiglum = \"" + msSiglum +"\";");}
      
      else {resultSet = statement.executeQuery("SELECT chantID "
      		+ "FROM " + database +".Feast JOIN Chant ON " + database + ".Chant.feastID = " + database 
      		+ ".Feast.FeastID JOIN " + database + ".Section "
      		+ "ON " + database + ".Chant.libSiglum=" + database + ".Section.libSiglum AND "
      		+ database + ".Chant.msSiglum=Section.msSiglum AND "
      		+ database + ".Chant.sectionID=Section.sectionID WHERE feastDate = \"" + date + "\" "
      		+ "AND chantID IS NOT NULL AND provenanceID = \"" + location + "\" AND feastDescription LIKE '%" + event + "%'"
      		+ " AND Chant.msSiglum = \"" + msSiglum +"\";");}
      
      while (resultSet.next()) {
      	chantIDArray.add(resultSet.getString("msIncipit") + "(" + resultSet.getString("chantID") + ")");
      }
      if (location.equals("") && date.equals("")) {
    	  System.out.println("Done");
      }
      resultSet = null;
      for (int i = 0; i < chantIDArray.size(); i++) {
    	  System.out.println(chantIDArray.get(i));
      }
      
      return chantIDArray;
      } catch (Exception e) {
      throw e;
    }
  }

  public ArrayList<String> getMSSiglumIDs(String location, String date, String event) throws Exception {
	    try {
	      statement = connect.createStatement();
	      ArrayList<String> msSiglumArray = new ArrayList<>();
	      
	      //SELECT DISTINCT chantID, provenanceID FROM Feast JOIN Chant ON Chant.feastID = Feast.FeastID JOIN Section ON Chant.libSiglum=Section.libSiglum AND Chant.msSiglum=Section.msSiglum AND Chant.sectionID=Section.sectionID WHERE feastDate="Dec.25" AND chantID IS NOT NULL AND provenanceID = "Rome";
	      if (location.equals("")) {
	    	  System.out.print("Success");
	      }
	      if (!location.equals("") && !event.equals("") && !date.equals("")) {resultSet = statement.executeQuery("SELECT DISTINCT Chant.msSiglum "
	        		+ "FROM " + database +".Feast JOIN Chant ON " + database + ".Chant.feastID = " + database 
	          		+ ".Feast.FeastID JOIN " + database + ".Section "
	          		+ "ON " + database + ".Chant.libSiglum=" + database + ".Section.libSiglum AND "
	          		+ database + ".Chant.msSiglum=Section.msSiglum AND "
	          		+ database + ".Chant.sectionID=Section.sectionID WHERE feastDate LIKE '%" + date + "%'"
	          		+ "AND chantID IS NOT NULL AND provenanceID LIKE '%" + location + "%' AND feastDescription LIKE '%" + event + "%';");}

	      else if (location.equals("") && event.equals("")) {resultSet = statement.executeQuery("SELECT DISTINCT Chant.msSiglum "
	        		+ "FROM " + database +".Feast JOIN Chant ON " + database + ".Chant.feastID = " + database 
	          		+ ".Feast.FeastID JOIN " + database + ".Section "
	          		+ "ON " + database + ".Chant.libSiglum=" + database + ".Section.libSiglum AND "
	          		+ database + ".Chant.msSiglum=Section.msSiglum AND "
	          		+ database + ".Chant.sectionID=Section.sectionID WHERE feastDate LIKE '%" + date + "%'"
	          		+ "AND chantID IS NOT NULL AND provenanceID IS NOT NULL;");}
	    
	      else if (location.equals("") && date.equals("")) {resultSet = statement.executeQuery("SELECT DISTINCT Chant.msSiglum "
	        		+ "FROM " + database + ".Feast JOIN Chant ON " + database + ".Chant.feastID = " + database 
	          		+ ".Feast.FeastID JOIN " + database + ".Section "
	          		+ "ON " + database + ".Chant.libSiglum=" + database + ".Section.libSiglum AND "
	          		+ database + ".Chant.msSiglum=Section.msSiglum AND "
	          		+ database + ".Chant.sectionID=Section.sectionID WHERE feastDate IS NOT NULL "
	          		+ "AND chantID IS NOT NULL AND provenanceID IS NOT NULL AND feastDescription LIKE '%" + event + "%';");}
	      
	      else if (location.equals("")) {resultSet = statement.executeQuery("SELECT DISTINCT Chant.msSiglum "
	        		+ "FROM " + database +".Feast JOIN Chant ON " + database + ".Chant.feastID = " + database 
	        		+ ".Feast.FeastID JOIN " + database + ".Section "
	        		+ "ON " + database + ".Chant.libSiglum=" + database + ".Section.libSiglum AND "
	        		+ database + ".Chant.msSiglum=Section.msSiglum AND "
	        		+ database + ".Chant.sectionID=Section.sectionID WHERE feastDate LIKE '%" + date + "%'"
	        		+ "AND chantID IS NOT NULL AND provenanceID IS NOT NULL AND feastDescription LIKE '%" + event + "%';");}
	      
	      
	      else if (date.equals("") && event.equals("")) {resultSet = statement.executeQuery("SELECT DISTINCT Chant.msSiglum "
	        		+ "FROM " + database + ".Feast JOIN Chant ON " + database + ".Chant.feastID = " + database 
	          		+ ".Feast.FeastID JOIN " + database + ".Section "
	          		+ "ON " + database + ".Chant.libSiglum=" + database + ".Section.libSiglum AND "
	          		+ database + ".Chant.msSiglum=Section.msSiglum AND "
	          		+ database + ".Chant.sectionID=Section.sectionID WHERE feastDate IS NOT NULL "
	          		+ "AND chantID IS NOT NULL AND provenanceID = \"" + location + "\";");}
	      
	      else if (date.equals("")) {resultSet = statement.executeQuery("SELECT DISTINCT Chant.msSiglum "
	        		+ "FROM " + database + ".Feast JOIN Chant ON " + database + ".Chant.feastID = " + database 
	        		+ ".Feast.FeastID JOIN " + database + ".Section "
	        		+ "ON " + database + ".Chant.libSiglum=" + database + ".Section.libSiglum AND "
	        		+ database + ".Chant.msSiglum=Section.msSiglum AND "
	        		+ database + ".Chant.sectionID=Section.sectionID WHERE feastDate IS NOT NULL "
	        		+ "AND chantID IS NOT NULL AND provenanceID LIKE '%" + location + "%' AND feastDescription LIKE '%" + event + "%';");}
	      
	      else {resultSet = statement.executeQuery("SELECT DISTINCT Chant.msSiglum "
	      		+ "FROM " + database +".Feast JOIN Chant ON " + database + ".Chant.feastID = " + database 
	      		+ ".Feast.FeastID JOIN " + database + ".Section "
	      		+ "ON " + database + ".Chant.libSiglum=" + database + ".Section.libSiglum AND "
	      		+ database + ".Chant.msSiglum=Section.msSiglum AND "
	      		+ database + ".Chant.sectionID=Section.sectionID WHERE feastDate = \"" + date + "\" "
	        	+ "AND chantID IS NOT NULL AND provenanceID = \"" + location + "\" AND "
	        	+ "feastDescription LIKE '%" + event + "%';");}
	      
	      while (resultSet.next()) {
	      	msSiglumArray.add(resultSet.getString("msSiglum"));
	      }

	      System.out.println("Done");
	      resultSet = null;
	      for (int i = 0; i < msSiglumArray.size(); i++) {
	    	  System.out.println(msSiglumArray.get(i));
	      }
	      
	      return msSiglumArray;
	      } catch (Exception e) {
	      throw e;
	    }
	  }
  public String getManuscriptInformation(String chantID, String location, String date, String event) throws SQLException {
	  String formattedInfo = new String();
	  
      if (!location.equals("") && !event.equals("") && !date.equals("")) {resultSet = statement.executeQuery("SELECT DISTINCT chantID, provenanceID, "
    		    + "feastName, feastDescription, Feast.feastID, feastDate, feastNotes, Chant.libSiglum, Chant.msSiglum, "
      		    + "msFullText, provenanceDetail, liturgicalOccasion "
          		+ "FROM " + database +".Feast JOIN Chant ON " + database + ".Chant.feastID = " + database 
          		+ ".Feast.FeastID JOIN " + database + ".Section "
          		+ "ON " + database + ".Chant.libSiglum=" + database + ".Section.libSiglum AND "
          		+ database + ".Chant.msSiglum=Section.msSiglum AND "
          		+ database + ".Chant.sectionID=Section.sectionID WHERE chantID = \""+ chantID +"\" AND feastDate = \"" + date + "\" "
          		+ "AND chantID IS NOT NULL AND provenanceID = \"" + location + "\" AND feastDescription LIKE '%" + event + "%';");}

    else if (location.equals("") && event.equals("")) {resultSet = statement.executeQuery("SELECT DISTINCT chantID, provenanceID, "
  		    + "feastName, feastDescription, Feast.feastID, feastDate, feastNotes, Chant.libSiglum, Chant.msSiglum, "
  		    + "msFullText, provenanceDetail, liturgicalOccasion "
      		+ "FROM " + database +".Feast JOIN Chant ON " + database + ".Chant.feastID = " + database 
      		+ ".Feast.FeastID JOIN " + database + ".Section "
      		+ "ON " + database + ".Chant.libSiglum=" + database + ".Section.libSiglum AND "
      		+ database + ".Chant.msSiglum=Section.msSiglum AND "
      		+ database + ".Chant.sectionID=Section.sectionID WHERE chantID = \""+ chantID +"\" AND feastDate = \"" + date + "\" "
      		+ "AND chantID IS NOT NULL AND provenanceID IS NOT NULL AND feastDescription IS NOT NULL;");}
  
    else if (location.equals("") && date.equals("")) {resultSet = statement.executeQuery("SELECT DISTINCT chantID, provenanceID, "
  		    + "feastName, feastDescription, Feast.feastID, feastDate, feastNotes, Chant.libSiglum, Chant.msSiglum, "
  		    + "msFullText, provenanceDetail, liturgicalOccasion "
      		+ "FROM " + database +".Feast JOIN Chant ON " + database + ".Chant.feastID = " + database 
      		+ ".Feast.FeastID JOIN " + database + ".Section "
      		+ "ON " + database + ".Chant.libSiglum=" + database + ".Section.libSiglum AND "
      		+ database + ".Chant.msSiglum=Section.msSiglum AND "
      		+ database + ".Chant.sectionID=Section.sectionID WHERE chantID = \""+ chantID +"\" AND feastDate IS NOT NULL "
      		+ "AND chantID IS NOT NULL AND provenanceID IS NOT NULL AND feastDescription LIKE '%" + event + "%';");}
    
    else if (location.equals("")) {resultSet = statement.executeQuery("SELECT DISTINCT chantID, provenanceID, "
  		    + "feastName, feastDescription, Feast.feastID, feastDate, feastNotes, Chant.libSiglum, Chant.msSiglum, "
  		    + "msFullText, provenanceDetail, liturgicalOccasion "
      		+ "FROM " + database +".Feast JOIN Chant ON " + database + ".Chant.feastID = " + database 
      		+ ".Feast.FeastID JOIN " + database + ".Section "
      		+ "ON " + database + ".Chant.libSiglum=" + database + ".Section.libSiglum AND "
      		+ database + ".Chant.msSiglum=Section.msSiglum AND "
      		+ database + ".Chant.sectionID=Section.sectionID WHERE chantID = \""+ chantID +"\" AND feastDate = \"" + date + "\" "
      		+ "AND chantID IS NOT NULL AND provenanceID IS NOT NULL AND feastDescription LIKE '%" + event + "%';");}
    
    
    else if (date.equals("") && event.equals("")) {resultSet = statement.executeQuery("SELECT DISTINCT chantID, provenanceID, "
  		    + "feastName, feastDescription, Feast.feastID, feastDate, feastNotes, Chant.libSiglum, Chant.msSiglum, "
  		    + "msFullText, provenanceDetail, liturgicalOccasion "
      		+ "FROM " + database +".Feast JOIN Chant ON " + database + ".Chant.feastID = " + database 
      		+ ".Feast.FeastID JOIN " + database + ".Section "
      		+ "ON " + database + ".Chant.libSiglum=" + database + ".Section.libSiglum AND "
      		+ database + ".Chant.msSiglum=Section.msSiglum AND "
      		+ database + ".Chant.sectionID=Section.sectionID WHERE chantID = \""+ chantID +"\" AND feastDate IS NOT NULL "
      		+ "AND chantID IS NOT NULL AND provenanceID = \"" + location + "\" AND feastDescription IS NOT NULL;");}
    
    else if (date.equals("")) {resultSet = statement.executeQuery("SELECT DISTINCT chantID, provenanceID, "
  		    + "feastName, feastDescription, Feast.feastID, feastDate, feastNotes, Chant.libSiglum, Chant.msSiglum, "
  		    + "msFullText, provenanceDetail, liturgicalOccasion "
      		+ "FROM " + database +".Feast JOIN Chant ON " + database + ".Chant.feastID = " + database 
      		+ ".Feast.FeastID JOIN " + database + ".Section "
      		+ "ON " + database + ".Chant.libSiglum=" + database + ".Section.libSiglum AND "
      		+ database + ".Chant.msSiglum=Section.msSiglum AND "
      		+ database + ".Chant.sectionID=Section.sectionID WHERE chantID = \""+ chantID +"\" AND feastDate IS NOT NULL "
      		+ "AND chantID IS NOT NULL AND provenanceID = \"" + location + "\" AND feastDescription LIKE '%" + event + "%';");}
    
    else {resultSet = statement.executeQuery("SELECT DISTINCT chantID, provenanceID, "
  		    + "feastName, feastDescription, Feast.feastID, feastDate, feastNotes, Chant.libSiglum, Chant.msSiglum, "
  		    + "msFullText, provenanceDetail, liturgicalOccasion "
      		+ "FROM " + database +".Feast JOIN Chant ON " + database + ".Chant.feastID = " + database 
      		+ ".Feast.FeastID JOIN " + database + ".Section "
      		+ "ON " + database + ".Chant.libSiglum=" + database + ".Section.libSiglum AND "
      		+ database + ".Chant.msSiglum=Section.msSiglum AND "
      		+ database + ".Chant.sectionID=Section.sectionID WHERE chantID = \""+ chantID +"\" AND feastDate = \"" + date + "\" "
      		+ "AND chantID IS NOT NULL AND provenanceID = \"" + location + "\" AND feastDescription LIKE '%" + event + "%';");}
	  ///////////////////////////////////////////////////
  
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
		  		+ "\nFeast Notes: " + resultSet.getString("feastNotes")  + "\nFeast Date: "+ resultSet.getString("feastDate") +"\n\n END OF RECORD";
		  
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