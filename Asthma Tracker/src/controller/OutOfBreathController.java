//Author: Anna
//Description: This is the controller for the out of breath view
//Problems: none
//Comments: No way to auto add user into database yet, run sql query until that is resolved.
//Query: INSERT INTO `asthmatrackerdb`.`clicktracker` (`userNameFK`) VALUES ('theUser');
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.DBConfig;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Account;


public class OutOfBreathController
{

	//FXML
    @FXML
    private Label breathCountLBL;
    @FXML
    private Button outBreathBTN;
    @FXML
    private Button backMainBTN;
    @FXML
    private Button resetBTN;

    //For loading new scenes
    Stage stage;
    Scene scene;
    Parent root;

    //counter for breath count
    private int breathCount;

    //tester database user
   // String testUser = "ab6789";

    Account activeUser = AsthmaController.curUser;


  //connect your controller to the launcher- for testing using the specialized launcher
    /*private BreathCountLauncher main;
  	public void setMain(BreathCountLauncher mainIn)
  	{
  		main=mainIn;
  	}
  	*/

  	private Main main;
  	public void setMain(Main mainIn)
  	{
  		main=mainIn;
  	}


  	@FXML
  	public void initialize() throws SQLException
  	{
  		System.out.println("error check: initialize ran");
  		getBreath();
  	}

  	//gets breath from database so if user moves pages, current breath is not lost. Reset during log in or log out
  	public int getBreath() throws SQLException
  	{
  		//System.out.println("error check: line 77");
  		String SQLQuery = "SELECT * FROM `clicktracker` WHERE clicktracker.userNameFK=" + "'"+activeUser.getuserName()+"'";
  		//String SQLQuery = "SELECT * FROM `clicktracker` WHERE clicktracker.userNameFK=" + "'"+testUser+"'";-for testing

		ResultSet rs = null;

		//System.out.println ("error check line");

		try(

				Connection conn = DBConfig.getConnection();
				PreparedStatement dbBreathCount = conn.prepareStatement(SQLQuery);
		){
			//displayAccountInfo.setInt(1, voterId);
			rs = dbBreathCount.executeQuery();

			// check to see if receiving any data
			if (rs.next())
			{
		  		//System.out.println("error check line 96");

	        	breathCount = rs.getInt("clicks");
	        	breathCountLBL.setText(Integer.toString(breathCount));

	        	System.out.println("error check: getBreath ran, breath count " + breathCount);
	        	//System.out.println ("error check line");
	        	return breathCount;

			}//if
			else
			{
			}
		}catch(SQLException ex)//try
		{
			DBConfig.displayException(ex);

		}finally //catch
		{
			if(rs != null)
			{
				rs.close();
			}
		}//finally
		return breathCount;
  	}

    //send the out of breath experiences to the DB
    @FXML
    void countOutOfBreath(ActionEvent event) throws SQLException
    {

    	//System.out.println("Testing user:" + activeUser.getfirstName());
    	//breathCount = getBreath();
  		System.out.println("error check: countOutOfBreath, current breath: " +breathCount);

    	breathCount ++;

    	//System.out.println("error check breathCount" + breathCount);
    	//System.out.println("error check user" + testUser);

    	//query for database
    	String updateQuery = "update clicktracker set clicks = ? where userNameFK = ?";

    	//attempt to connect to database
		try (Connection conn = DBConfig.getConnection();
				PreparedStatement updateBreath = conn.prepareStatement(updateQuery);
			)
		{
			//the 1,2,3 in the setString correspond to the ?,?,? in the query
			updateBreath.setInt(1, breathCount);
			updateBreath.setString(2, activeUser.getuserName());
			//updateBreath.setString(2, testUser);

			//execute update
			updateBreath.executeUpdate();

			System.out.println("error check: success! breath updated! user:  " + activeUser.getuserName() + " breath count: " + breathCount);
        	breathCountLBL.setText(Integer.toString(breathCount));


		} catch (Exception e) {

			System.out.println("Error: " + e);
			//statusLabel.setText("Status: operation failed due to: " + e.getMessage());
		}


    }


    //resets breath count
    @FXML
    void resetOutOfBreath(ActionEvent event)
    {
    	breathCount = 0;

    	//System.out.println("error check breathCount" + breathCount);
    	//System.out.println("error check user" + testUser);

    	//query for database
    	String query = "update clicktracker set clicks = ? where userNameFK = ?";

    	//attempt to connect to database
		try (Connection conn = DBConfig.getConnection();
				PreparedStatement resetBreath = conn.prepareStatement(query);
			)
		{
			//the 1,2,3 in the setString correspond to the ?,?,? in the query
			resetBreath.setInt(1, breathCount);
			resetBreath.setString(2, activeUser.getuserName());
			//resetBreath.setString(2, testUser);

			//execute update
			resetBreath.executeUpdate();


			System.out.println("error check: success! breath reset! user:  " + activeUser.getuserName() + " breath count: " + breathCount);
        	breathCountLBL.setText(Integer.toString(breathCount));


		} catch (Exception e) {

			System.out.println("Error: " + e);
			//statusLabel.setText("Status: operation failed due to: " + e.getMessage());
		}
    }//end reset



    //TODO need the fxml for main menu and the controller
    @FXML
    void returnToMain(ActionEvent event)
    {
    	/*
    	//get the stage the button was hit in
    	stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

    	//load the new fxml file
    	//TODO update the fxml to the main menu
    	root = FXMLLoader.load(getClass().getResource(""));

    	//create a new controller
		MainAccountController con1 = new MainAccountController();

		//link the controller to the main
		con1.setMain(main);

		//sets fxml file as a scene
		scene = new Scene(root);

		//loads the scene on top of whatever stage the button is in
		stage.setScene(scene);
		*/

    }



}