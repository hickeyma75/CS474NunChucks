/**
 * Orders Program that interacts with the database in order to insert new customers and to get all
 * of the customer information
 * 
 * @author Vega, Raasio
 * Group Name: Nunchuck Secret Agents
 *
 */
public class CS474Project3Driver {

  /**
   * main presents the UI for the program and then interacts with the MySQLAccess file to get info
   * from the DB
   * 
   * @author Vega, Raasio
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    MySQLAccess dao = new MySQLAccess();
    dao.connectToDB();
    dao.getChantIDs("Rome", null, "Christmas");
    // Scanner to pull information from the user to give to the program

  }



}