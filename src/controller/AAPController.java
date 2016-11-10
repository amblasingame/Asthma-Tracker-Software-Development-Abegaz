//Author: Anna
//Description: This is the controller for the AAP
//Problems: none
//Comments: this controller grabs the info from database, populates textfields, and lets the user submit new information
//I've set activeUser in the methods with the username ab6789 for testing purposes
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.DBConfig;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.AAP;
import model.Account;


public class AAPController {

    @FXML
    private TextField mildMedTF;
    @FXML
    private TextField mildAmtTF;
    @FXML
    private TextField mildFreqTF;

    @FXML
    private TextField modMedTF;
    @FXML
    private TextField modAmtTF;
    @FXML
    private TextField modFreqTF;

    @FXML
    private TextField sevMedTF;
    @FXML
    private TextField sevAmtTF;
    @FXML
    private TextField sevFreqTF;

    @FXML
    private Button backBTN;
    @FXML
    private Button resetBTN;
    @FXML
    private Button updateBTN;


    @FXML
    private TextField drNameTF;
    @FXML
    private TextField drPhoneTF;
    @FXML
    private TextField drCityTF;

  //For loading new scenes
    Stage stage;
    Scene scene;
    Parent root;

    //Danni<start>-----------------------------------------------------------------------------------------------------
    @FXML private Label allFieldsErr, drNameErr, drCityErr, drPhoneErr, sevMedErr, sevAmtErr, sevFreqErr, modMedErr,
    	modAmtErr, modFreqErr, mildMedErr, mildAmtErr, mildFreqErr;
    //Danni<end>-------------------------------------------------------------------------------------------------------

    //current user
    Account activeUser = AsthmaController.curUser;




	//for integration

    private Main main;
  	public void setMain(Main mainIn)
  	{
  		main = mainIn;
  	}


  	//runs when initialized, grabs info from database and populates the textfields

	@FXML
  	public void initialize() throws SQLException
  	{
  		System.out.println("error check: initialize ran");
  		//pass the object from getAAPInfo method
  		AAP userPlan = getAAPInfo(activeUser.getuserName());
  		//set the textfields with the content of the database
  		//mildMed

	    mildMedTF.setText(userPlan.getMildMed());
	    mildAmtTF.setText(userPlan.getMildAmt());
	    mildFreqTF.setText(userPlan.getMildFreq());
	    //modMed
	    modMedTF.setText(userPlan.getModMed());
	    modAmtTF.setText(userPlan.getModAmt());
	    modFreqTF.setText(userPlan.getModFreq());
	    //sevMed
	    sevMedTF.setText(userPlan.getSevMed());
	    sevAmtTF.setText(userPlan.getSevAmt());
	    sevFreqTF.setText(userPlan.getSevFreq());
	    //dr info
	    drNameTF.setText(userPlan.getDrName());
	    drPhoneTF.setText(userPlan.getDrPhone());
	    drCityTF.setText(userPlan.getDrCity());

  	}//end method

	//TODO need the fxml for main menu and the controller
  	//right now this is integrated personally
    @FXML
    void backToMain(ActionEvent event) throws Exception
    {

    	//get the stage the button was hit in
    	stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

    	try {
			root = FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));

		MainMenuController conX=new MainMenuController();
		conX.setMain(main);
		scene = new Scene(root);
		stage.setScene(scene);
    	} catch (Exception e){
			e.printStackTrace();
		}


    }



    //used to reset fields in AAP
    @FXML
    void resetAAP(ActionEvent event) {
    	//mildMed
	    mildMedTF.setText(null);
	    mildAmtTF.setText(null);
	    mildFreqTF.setText(null);

	    //modMed
	    modMedTF.setText(null);
	    modAmtTF.setText(null);
	    modFreqTF.setText(null);

	    //sevMed
	    sevMedTF.setText(null);
	    sevAmtTF.setText(null);
	    sevFreqTF.setText(null);

	    //dr info
	    drNameTF.setText(null);
	    drPhoneTF.setText(null);
	    drCityTF.setText(null);

	    //clear error labels---Danni<start>----------------------------------------------------------------------------
	    //all fields
	    allFieldsErr.setText(null);
	    //dr info
	    drNameErr.setText(null);
	    drCityErr.setText(null);
	    drPhoneErr.setText(null);
	    //sev
	    sevMedErr.setText(null);
	    sevAmtErr.setText(null);
	    sevFreqErr.setText(null);
	    //mod
	    modMedErr.setText(null);
    	modAmtErr.setText(null);
    	modFreqErr.setText(null);
    	//mild
    	mildMedErr.setText(null);
    	mildAmtErr.setText(null);
    	mildFreqErr.setText(null);
	    //Danni<end>---------------------------------------------------------------------------------------------------

	    System.out.println("error check: current user reset " + activeUser.getuserName());
    }//end method

    //Danni<start>-----------------------------------------------------------------------------------------------------
    //capitalizes first letter in name
  	public String capitalizeName(String name){
  			return name.substring(0,1).toUpperCase() + name.substring(1);//.toLowerCase();
  	}
    //Danni<end>-------------------------------------------------------------------------------------------------------

    //use to submit AAP to database
    @FXML
    void submitAAP(ActionEvent event) throws SQLException {

    	//get the information from the textfields

    	//mildMed
	    String mildMed = mildMedTF.getText();
	    String mildAmt = mildAmtTF.getText();
		String mildFreq = mildFreqTF.getText();

	    //modMed
	    String modMed = modMedTF.getText();
	    String modAmt = modAmtTF.getText();
		String modFreq = modFreqTF.getText();

	    //sevMed
	    String sevMed = sevMedTF.getText();
	    String sevAmt = sevAmtTF.getText();
		String sevFreq = sevFreqTF.getText();

	    //dr info
	    String drName = drNameTF.getText();
	    String drPhone = drPhoneTF.getText();
	    String drCity = drCityTF.getText();
	    //Danni<start>-------------------------------------------------------------------------------------------------
	    //check to see if all fields of one of the tabs is filled in
	    boolean test = false;
	    String mia, mif, moa, mof, sea, sef, drphon;
	    mia = mildAmtTF.getText();
	    mif = mildFreqTF.getText();
	    moa = modAmtTF.getText();
	    mof = modFreqTF.getText();
	    sea = sevAmtTF.getText();
	    sef = sevFreqTF.getText();
	    drphon = drPhoneTF.getText();

	    allFieldsErr.setText(null);
	    //checks for blank fields
    	if(!mildMed.equals("") || !mia.equals("") || !mif.equals("")){
    		mildMed = capitalizeName(mildMed);
    		test = true;
    	}else if(!modMed.equals("") && !moa.equals("") && !mof.equals("")){
    		modMed = capitalizeName(modMed);
    		test = true;
    	}else if(!sevMed.equals("") && !sea.equals("") && !sef.equals("")){
    		sevMed = capitalizeName(sevMed);
    		test = true;
    	}else{
    		allFieldsErr.setText("Please fill in all fields for severity info.");
    		test = false;
    	}
    	if(drName.equals("") || drphon.equals("") || drCity.equals("")){
    		allFieldsErr.setText("Please fill in all fields for doctor info.");
    		test = false;
    	}else{
    		//capitalizes names
    		drName = capitalizeName(drName);
    		drCity = capitalizeName(drCity);
    	}

    	mildMedErr.setText(null);
    	modMedErr.setText(null);
    	sevMedErr.setText(null);
    	//checks for special characters and numbers in mildMed, modMed, and sevMed
    	Pattern p3 = Pattern.compile("[^a-zA-Z-\\.\\s]");
		Matcher mim = p3.matcher(mildMed);
		Matcher mom = p3.matcher(modMed);
		Matcher sem = p3.matcher(sevMed);
		boolean mi1 = mim.find();
		boolean mo1 = mom.find();
		boolean se1 = sem.find();
		if(mi1){
			mildMedErr.setText("Invalid medicine type. Cannot contain special characters or numbers.");
			test = false;
		}
		if(mo1){
			modMedErr.setText("Invalid medicine type. Cannot contain special characters or numbers.");
			test = false;
		}
		if(se1){
			sevMedErr.setText("Invalid medicine type. Cannot contain special characters or numbers.");
			test = false;
		}

		mildAmtErr.setText(null);
		mildFreqErr.setText(null);
		modAmtErr.setText(null);
		modFreqErr.setText(null);
		sevAmtErr.setText(null);
		sevFreqErr.setText(null);
		//checks for anything that isn't a number in
    	Pattern p4 = Pattern.compile("[^0-9]");
    	//mild
    	Matcher mia2 = p4.matcher(mia);
    	Matcher mif2 = p4.matcher(mif);
    	boolean mi2 = mia2.find();
    	boolean mi3 = mif2.find();
    	if(mi2){
    		mildAmtErr.setText("Amount must consist of only numbers.");
    		test = false;
    	}
    	if(mi3){
    		mildFreqErr.setText("Frequency must consist of only numbers.");
    		test = false;
    	}
    	//moderate
    	Matcher moa2 = p4.matcher(moa);
    	Matcher mof2 = p4.matcher(mof);
    	boolean mo2 = moa2.find();
    	boolean mo3 = mof2.find();
    	if(mo2){
    		modAmtErr.setText("Amount must consist of only numbers.");
    		test = false;
    	}
    	if(mo3){
    		modFreqErr.setText("Frequency must consist of only numbers.");
    		test = false;
    	}
    	//severe
    	Matcher sea2 = p4.matcher(sea);
    	Matcher sef2 = p4.matcher(sef);
    	boolean se2 = sea2.find();
    	boolean se3 = sef2.find();
    	if(se2){
    		sevAmtErr.setText("Amount must consist of only numbers.");
    		test = false;
    	}
    	if(se3){
    		sevFreqErr.setText("Frequency must consist of only numbers.");
    		test = false;
    	}

    	drNameErr.setText(null);
    	drCityErr.setText(null);
    	//checks for special characters and number in drName and drCity
    	Pattern p = Pattern.compile("[^a-zA-Z-\\.\\s]");
		Matcher dName = p.matcher(drName);
		Matcher dCity = p.matcher(drCity);
		boolean dn = dName.find();
		boolean dc = dCity.find();
		if(dn){
			drNameErr.setText("Invalid full name. Cannot use special characters or numbers.");
			test = false;
		}
		if(dc){
			drCityErr.setText("Invalid city name. Cannot use special characters or numbers.");
			test = false;
		}

		drPhoneErr.setText(null);
		//checks to make sure phone number only consists of numbers and -
		Pattern p2 = Pattern.compile("[^0-9-]");
		Matcher phon = p2.matcher(drphon);
		boolean pho = phon.find();
		if(pho){
			drPhoneErr.setText("Invalid phone number. Phone number must consist of numbers only.");
			test = false;
		}
		if(drphon.length() != 12 && !drphon.equals("")){
			drPhoneErr.setText("Incorrect phone number length.");
			test = false;
		}else if(drphon.charAt(3) != '-' || drphon.charAt(7) != '-' && !drphon.equals("") ){
			drPhoneErr.setText("Incorrect phone number format.");
			test = false;
		}
    	//Danni<end>---------------------------------------------------------------------------------------------------

		//create an instance of your model and set the values into it
		if(test == true){//Danni---------------------------------------------------------------------------------------
			AAP newPlan = new AAP();

	    	//mild
	    	newPlan.setMildMed(mildMed);
	    	newPlan.setMildAmt(mildAmt);
	    	newPlan.setMildFreq(mildFreq);

	    	//mod
	    	newPlan.setModMed(modMed);
	    	newPlan.setModAmt(modAmt);
	    	newPlan.setModFreq(modFreq);

	    	//sev
	    	newPlan.setSevMed(sevMed);
	    	newPlan.setSevAmt(sevAmt);
	    	newPlan.setSevFreq(sevFreq);

	    	//dr info
	    	newPlan.setDrName(drName);
	    	newPlan.setDrPhone(drPhone);
	    	newPlan.setDrCity(drCity);


	    	//create a query
	    	String AAP = "UPDATE aap SET mildMed = ?, mildAmt = ?, mildFreq = ?, modMed = ?, modAmt = ?, "
	    			+ "modFreq = ?, sevMed = ?, sevAmt = ?, sevFreq = ?, drName = ?, drPhone = ?, drCity = ?"
					+ "WHERE uNameFK = ?";


			//attempt to connect to database
			try (Connection conn = DBConfig.getConnection();
					PreparedStatement insertAAP = conn.prepareStatement(AAP);)
			{

				//mild
				insertAAP.setString(1, newPlan.getMildMed());
				insertAAP.setString(2, newPlan.getMildAmt());
				insertAAP.setString(3, newPlan.getMildFreq());

				//mod
				insertAAP.setString(4, newPlan.getModMed());
				insertAAP.setString(5, newPlan.getModAmt());
				insertAAP.setString(6, newPlan.getModFreq());

				//severe
				insertAAP.setString(7, newPlan.getSevMed());
				insertAAP.setString(8, newPlan.getSevAmt());
				insertAAP.setString(9, newPlan.getSevFreq());

				//doctor info
				insertAAP.setString(10, newPlan.getDrName());
				insertAAP.setString(11, newPlan.getDrPhone());
				insertAAP.setString(12, newPlan.getDrCity());

				insertAAP.setString(13, activeUser.getuserName());



				//execute the update
				insertAAP.executeUpdate();

				System.out.println("error check: current user updated " + activeUser.getuserName());
				System.out.println("error check: success! account updated " + newPlan);

			}catch(SQLException ex)//try
			{
				DBConfig.displayException(ex);
			}
			allFieldsErr.setText("Your AAP information has been updated.");
		}//if--Danni---------------------------------------------------------------------------------------------------
    }//end method


    //gets info from the database
    private AAP getAAPInfo (String userName) throws SQLException
    {

    	String AAPInfo = "select * from aap where uNameFK =" + "'"+userName+"'";
		ResultSet rs = null;

		try(
				Connection conn = DBConfig.getConnection();
				PreparedStatement displayAAPInfo = conn.prepareStatement(AAPInfo);
		){
			//displayAccountInfo.setInt(1, voterId);
			rs = displayAAPInfo.executeQuery();

			// check to see if receiving any data
			if (rs.next())
			{
				//create instance of model
				AAP displayPlan = new AAP();

				//add info from db to model

		    	//mild
				displayPlan.setMildMed(rs.getString("mildMed"));
				displayPlan.setMildAmt(rs.getString("mildAmt"));
				displayPlan.setMildFreq(rs.getString("mildFreq"));

		    	//mod
				displayPlan.setModMed(rs.getString("modMed"));
				displayPlan.setModAmt(rs.getString("modAmt"));
				displayPlan.setModFreq(rs.getString("modFreq"));

		    	//sev
				displayPlan.setSevMed(rs.getString("sevMed"));
				displayPlan.setSevAmt(rs.getString("sevAmt"));
				displayPlan.setSevFreq(rs.getString("sevFreq"));

		    	//dr info
				displayPlan.setDrName(rs.getString("drName"));
				displayPlan.setDrPhone(rs.getString("drPhone"));
				displayPlan.setDrCity(rs.getString("drCity"));


		        System.out.println("gotten aap " + displayPlan);

		        //return the object
		        return displayPlan;
			}//if
			else
			{
				return null;
			}
		}catch(SQLException ex)//try
		{
			DBConfig.displayException(ex);
			return null;
		}finally //catch
		{
			if(rs != null)
			{
				rs.close();
			}
		}//finally
    }//end method

}//end program