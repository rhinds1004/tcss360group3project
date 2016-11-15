package ui;

import java.io.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

import model.Auction;
import model.Bidder;
import model.Calendar;
import model.Item;
import model.NPContact;
import model.Staff;
import model.User;

/**
 * this start the UI for the user of the program.
 * 
 * @author David Nowlin
 * @version 11/11/2016
 *
 */
public class UI {
	private static Scanner myScanner = new Scanner(System.in);
	private static Calendar myCalender	= new Calendar();
	private static User myUser = null;	
	//used to generate the current Date.
	private static Format myFormatter = new SimpleDateFormat("MMMM dd, yyyy"); 
	protected static Date   myTodayDate = GregorianCalendar.getInstance().getTime();
	protected static String myCurrentDate = myFormatter.format(myTodayDate);

	private static ArrayList<User> myListUser = null;


	/**
	 * this is the start of the UI.
	 */
	public static void beginUI() {

		readInData();
		User chooseUser = choosePreviousOrMakeUser();
		if(chooseUser != null) {
			myCalender = whichUserUI(chooseUser);
		}
		writeOutData();
	}
	
	
	/*
	 * login on a previously created user account or create a new user account.
	 * @return the select user.
	 */
	private static User choosePreviousOrMakeUser() {
		User myUser = null;
		int choose = 0;
		do {
			System.out.print("AuctionCentral: the auctioneer for non-profit organizations.\n"
					+ "1) Login\n"
					+ "2) Create acount\n"
					+ "3) Exit.\n"
					+ "Enter your Selection from 1 to 3: ");
			choose = myScanner.nextInt();
		} while (choose < 1 && choose > 3);
		if(choose == 1){
//			System.out.println("went in to login");
			myUser = chooseLogin();
		} else if (choose ==2){
//			System.out.println("\n\nwent in to create User");
			myUser = createUser();
			myListUser.add(myUser);
		}
		return myUser;
	}
	
	/*
	 * Determine which user type is login and goes to the right UI on type. 
	 * @param the login user
	 * @return the update calendar.
	 */
	private static Calendar whichUserUI(User theUser) {
		Calendar aCalendar = null;
		if(theUser.getType() == 1) {// the Staff
						//TODO: the Staff
			aCalendar = UIStaff.beginStaffUI((Staff)theUser, myCalender);
		} else if (theUser.getType() == 2) { // the NPContact
			//TODO: the NPContact UI
//			aCalendar = UINPContact.beginNPContactUI((NPContact)theUser, myCalender);
		} else { // the Bidder type
			aCalendar = UIBidder.beginBidderUI((Bidder)theUser, myCalender);
		}
		return aCalendar;
	}
	
	/*
	 * how you choose the user you are.
	 * @return the login user
	 */
	private static User chooseLogin() {
		int choose = 0;
		do {
			System.out.println("\n\nPick the user you are.\n"
					+ "type of user\n1 is Staff user\n2 is None Profit user\n3 is a bidder user\n\n"
					+ "index\t\tuserName\t\t typeuser");
			for (int i = 0; i < myListUser.size(); i++) {
				System.out.println((i+1) + ") \t\t" + myListUser.get(i).getUsername() + "\t\t  " + myListUser.get(i).getType());
			}
			System.out.print("\nEnter your Selection from 1 to " + myListUser.size() + ": ");
			choose = myScanner.nextInt();
		} while (choose < 1 || choose > myListUser.size());		
		return myListUser.get((choose - 1));
	}
	
	/*
	 * this make a new user that is enter in to the systems.
	 * @return the new user as login.
	 */
	private static User createUser() {
		int typeUser = 0;
		String userName = "";
		User aUser = null;
		do {
			System.out.print("\n\nCreate a New User.\n"
					+ "What Type Of User Are You.\n"
					+ "1) For Staff User.\n"
					+ "2) For Non For Profit Contact User.\n"
					+ "3) For Bidder User.\n"
					+ "Enter your Selection from 1 to 3: ");
			typeUser = myScanner.nextInt();
		} while (typeUser < 1 && typeUser > 3);
		System.out.println("\nYour User Name With No Space Please.");
		userName = myScanner.next();
		if(typeUser == 1) {
			aUser = new Staff(userName, myCalender);
		} else if (typeUser == 2) {
			aUser = new NPContact(userName, myCalender);
		} else {
			aUser = new Bidder(userName, myCalender);
		}
		return aUser;
	}
	
	/*
	 * this is used to read in the user list and the calendar for the as
	 * serialization object.
	 */
	private static void readInData() {
		myCalender = null;
		myListUser = null;
	    readCaledar();
	    readUser();
	}
	
	/*
	 * this read in the calendar object serialization.
	 */
	private static void readCaledar() {
		try
	      {
	         FileInputStream fileIn = new FileInputStream("testCalendar.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         myCalender = (Calendar) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("Calender class not found");
	         c.printStackTrace();
	         return;
	      }
	}
	
	/*
	 * this read in the list of user object serialization.
	 */
	private static void readUser() {
		try
	      {
	         FileInputStream fileIn = new FileInputStream("testUserList.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         myListUser = (ArrayList<User>)in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("Calender class not found");
	         c.printStackTrace();
	         return;
	      }
	}
	
	/*
	 * this will write out the update calendar and list of user object as serialization object.
	 */
	private static void writeOutData() {
		writeOutCalendar();
		writeOutUserList();
	}
	
	/*
	 * how we write out the calendar as a serialization object.
	 */
	private static void writeOutCalendar() {
//		Item football = new Item("1", "computer", "ASUS", "Small", 20.0, 1, "great");
//		Item baseball = new Item("2", "pie", "the nothing much", "mid", 20.0, 1, "Very Fine");
//		Auction testAuction = new Auction(new GregorianCalendar(2016, 10, 23), "testAcution3");
//		testAuction.addItem(football);
//		testAuction.addItem(baseball);
////		myCalender = new Calendar();
//		myCalender.addAuction(testAuction);
		try
	      {
	         FileOutputStream fileOut = new FileOutputStream("testCalendar.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(myCalender);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved in testCalendar.ser\n");
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
	
	/*
	 * this is how we write out the user list as a serialization object.
	 */
	private static void writeOutUserList() {
//		myListUser = new ArrayList<User>();
//		myListUser.add(new Bidder("davidTest", myCalender));
		try
	      {
	         FileOutputStream fileOut =new FileOutputStream("testUserList.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(myListUser);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved in testUserList.ser\n");
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
}
