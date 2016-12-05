package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Auction;
import model.Calendar;
import model.NPContact;

public class NPContactGUI implements Initializable {
	
	/** The "Submit auction request" button. */
	@FXML
	private Button mySubmitAuctionRequestBtn;
	
	/** The "Submit auction request" button. */
	@FXML
	private Button myViewAuctionsBtn;
	
	/** The "Item inventory" button. */
	@FXML
	private Button myItemInvBtn;
	
	/** The "Log out" button. Pressing this should return the user to the login screen. */
	@FXML
	private Button myLogoutBtn;
	
	@FXML
	private Label myUsernameLabel;
	
	@FXML
	private ImageView myLogoImageView;
	
	private Calendar myCalendar;
	
	private Stage myStage;
	
	private NPContact myNPContact;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		assert mySubmitAuctionRequestBtn != null : "fx:id=\"mySubmitAuctionRequestBtn\" was not injected: check your FXML file 'NPContactAuctionRequestForm.fxml'.";
		assert myViewAuctionsBtn != null : "fx:id=\"myViewAuctionsBtn\" was not injected: check your FXML file 'NPContactAuctionRequestForm.fxml'.";
		assert myItemInvBtn != null : "fx:id=\"myItemInvBtn\" was not injected: check your FXML file 'NPContactAuctionRequestForm.fxml'.";
		assert myLogoutBtn != null : "fx:id=\"myLogoutBtn\" was not injected: check your FXML file 'NPContactAuctionRequestForm.fxml'.";
		assert myUsernameLabel != null : "fx:id=\"myUsernameLabel\" was not injected: check your FXML file 'NPContactAuctionRequestForm.fxml'.";
		assert myLogoImageView != null : "fx:id=\"myLogoImageView\" was not injected: check your FXML file 'NPContactAuctionRequestForm.fxml'.";

		Image logo = new Image("file:logo2_v3.png");
		myLogoImageView.setImage(logo);
	}
	
	//My constructor...
	public void sendData(Stage theStage, NPContact theNPContact, Calendar theCalendar) {
		myStage = theStage;
		myNPContact = theNPContact;
		myCalendar = theCalendar;
		
		if (myNPContact.hasAuctionUpcomingOrLastYear()) {
			mySubmitAuctionRequestBtn.setDisable(true);
		} else {
			myItemInvBtn.setDisable(true);
		}
		
		myUsernameLabel.setText("Logged in as: " + myNPContact.getUsername());
		initalizeBtns();
	}
	
	private void initalizeBtns() {
		mySubmitAuctionRequestBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				final Stage auctionRequestStage = new Stage();
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NPContactAuctionRequestForm.fxml"));
					Parent root = (Parent)fxmlLoader.load();
					NPContactAuctionRequestFormGUI ctrlAuctionRequestFormGUI = fxmlLoader.<NPContactAuctionRequestFormGUI>getController();

					ctrlAuctionRequestFormGUI.initVariables(myStage, auctionRequestStage, myCalendar, myNPContact, mySubmitAuctionRequestBtn , myItemInvBtn);

					Scene scene = new Scene(root);
					auctionRequestStage.setScene(scene);
					myStage.hide();
					auctionRequestStage.show();
				} catch(Exception anException) {
					anException.printStackTrace();
				}
			}
		});
		
		myViewAuctionsBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				final Stage viewAuctionsStage = new Stage();
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NPContactViewAuctionsGUI.fxml"));
					Parent root = (Parent)fxmlLoader.load();
					NPContactViewAuctionsGUI ctrlViewAuctionsGUI = fxmlLoader.<NPContactViewAuctionsGUI>getController();

					ctrlViewAuctionsGUI.initVariables(myStage, viewAuctionsStage, myCalendar, myNPContact, myItemInvBtn);

					Scene scene = new Scene(root);
					viewAuctionsStage.setScene(scene);
					myStage.hide();
					viewAuctionsStage.show();
				} catch(Exception anException) {
					anException.printStackTrace();
				}
			}
		});
		
		myItemInvBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				final Stage itemInventoryStage = new Stage();
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NPContactItemInventoryGUI.fxml"));
					Parent root = (Parent)fxmlLoader.load();
					NPContactItemInventoryGUI ctrlItemInventoryGUI = fxmlLoader.<NPContactItemInventoryGUI>getController();

					ctrlItemInventoryGUI.initVariables(myStage, itemInventoryStage, myCalendar, myNPContact);

					Scene scene = new Scene(root);
					itemInventoryStage.setScene(scene);
					myStage.hide();
					itemInventoryStage.show();
				} catch(Exception anException) {
					anException.printStackTrace();
				}
			}
		});
		
		myLogoutBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Authenticate.setupAuthenticate(myStage);
			}
		});

	}
}
