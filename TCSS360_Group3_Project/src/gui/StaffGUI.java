package gui;

import dataStorage.SerializeData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Calendar;
import model.User;


/**
* This class contains the GUI for staff members to update the maximum number of Auctions and view upcoming auctions.
*
* @author Justin Washburn
* @version 30 Nov 2016
*
*/

public class StaffGUI {
	

	/**
	 * Current user
	 */
	private static User myUser;
	
	/**
	 * Calendar and User Data
	 */
	private static SerializeData myData;
	

	/**
	 * Sets up the fields and calls the StaffGUI set up
	 * 
	 * @param theStage The current open window
	 * @param theUser The current user
	 * @param theData Calendar and User Data
	 */
	public static void startStaffGUI(Stage theStage, User theUser, SerializeData theData) {
		myUser = theUser;
		myData = theData;
		setUpStaffGUI(theStage);
	}
	
/**
 * Builds the Staff GUI and it's functionality
 * @param primaryStage The current open window
 */
	public static void setUpStaffGUI(Stage primaryStage) {
		primaryStage.setTitle("Auction Central - Staff");
		GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 550, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        Text scenetitle = new Text("Welcome " + myUser.getUsername() + ", Current Upcoming Auctions: " + myData.getCalendar().getUpcomingAuctionsNumber());
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label changeMaxAuction = new Label("Max auctions possible");
        grid.add(changeMaxAuction, 0, 4);
        
        ObservableList<Integer> options = 
 			    FXCollections.observableArrayList();
        for (int i = 1; i <= Calendar.MAX_POSSIBLE_AUCTIONS; i++) {
        	options.add(i);
        }
       
 	    final ComboBox<Integer> maxAuctionsBox = new ComboBox<Integer>(options);
 	    maxAuctionsBox.setValue(myData.getCalendar().getMaxAuctionsLimit());

 	    HBox maxAuctionsbx = new HBox(10);
 	    maxAuctionsbx.setAlignment(Pos.BOTTOM_RIGHT);
 	    maxAuctionsbx.getChildren().add(maxAuctionsBox);
 	    grid.add(maxAuctionsbx, 1, 4);
 	    
        
 	    
 	   Button submitMaxAuctions = new Button("Update");
       HBox submitMaxAuctionshbBtn = new HBox(10);
       submitMaxAuctionshbBtn.setAlignment(Pos.BOTTOM_RIGHT);
       submitMaxAuctionshbBtn.getChildren().add(submitMaxAuctions);
       grid.add(submitMaxAuctionshbBtn, 1, 5);
       
       final Text actiontarget = new Text();
       grid.add(actiontarget, 1, 6);
       
       /**
        * Attempts to set the new number of max auctions
        */
       submitMaxAuctions.setOnAction(new EventHandler<ActionEvent>() {
      	 /**
      	  * Attempts to set the new number of max auctions
      	  * 
      	  * @param e The button press that will try to submit the new number
      	  */
          @Override
          public void handle(ActionEvent e) {
        	  int newMaxAuctions = maxAuctionsBox.getValue();
        	  if (myData.getCalendar().setMaxAuctionsLimit(newMaxAuctions) == 0) {
        		  actiontarget.setFill(Color.GREEN);
                  actiontarget.setText("Succesfully updated");
                  //Serialize?
                  
                
        	  } else {
        		  actiontarget.setFill(Color.FIREBRICK);
                  actiontarget.setText("You already have " + myData.getCalendar().getUpcomingAuctionsNumber() + " schduled");
        	  }
          }
 
      });
       
       Button viewCalendarbtn = new Button("View Calendar");
       HBox viewCalendarhbx = new HBox(10);
       viewCalendarhbx.setAlignment(Pos.BOTTOM_RIGHT);
       viewCalendarhbx.getChildren().add(viewCalendarbtn);
       grid.add(viewCalendarhbx, 1, 7);
       
     /**
    	 * Chanegs the GUI to view upcoming auctions
      * 
      */
       
       viewCalendarbtn.setOnAction(new EventHandler<ActionEvent>() {
      	 /**
      	  * Chanegs the GUI to view upcoming auctions
      	  * 
      	  * @param e The button press that will try to submit the new number
      	  */
          @Override
          public void handle(ActionEvent e) {
        	  ViewCalendarGUI.viewCalendar(primaryStage, myData, myUser);
          }
 
      });

	
    Button backbtn = new Button("Log Out");
    HBox backhbBtn = new HBox(10);
    backhbBtn.setAlignment(Pos.TOP_LEFT);
    backhbBtn.getChildren().add(backbtn);
    grid.add(backhbBtn, 0, 7);
    
	 /**
	  * Goes back to the authenticate scene, essentially logs you out 
	  * 
	  */
    
    backbtn.setOnAction(new EventHandler<ActionEvent>() {
    	 /**
    	  * Goes back to the authenticate scene
    	  * 
    	  * @param e The button press that will send the user back to authenticate
    	  */
        @Override
        public void handle(ActionEvent e) {
     	   Authenticate.setupAuthenticate(primaryStage);
        }             
    });
	}
}
