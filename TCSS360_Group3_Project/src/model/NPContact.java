package model;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
* This class is used to handle/hold methods and
* fields specifically for AuctionCentral non-profit
* contact persons.
*
* @author Vlad Kaganyuk
* @version 12 Nov 2016
*
**/
public class NPContact extends User {
	private Calendar myCalendar;
	
	private List<Auction> myAuctions;
	
	private static final long serialVersionUID = 7526433795622776147L;

	/**
	* This constructor sets the fields equal to the user-provided values,
	* and sets the User type to 2.
	* @param theUsername The String that contains the User's username.
	* @param theCalendar The Calendar object to call methods on.
    */
	public NPContact(String theUsername, Calendar theCalendar) {
		super(theUsername, theCalendar, 2);
		myCalendar = theCalendar;
		myAuctions = new ArrayList<Auction>();
	}
	
	public void addItem(Auction theAuctionToAddTo, Item theItemToAdd) {
		//checkIfItemExists in Auction Yet.
		theAuctionToAddTo.addItem(theItemToAdd);
	}
	
	public void addAuction(Auction theAuction) {
		myAuctions.add(theAuction);
	}
	
	/**
	 * Checks if the non-profit had an auction less than a year ago and if they have an upcoming auction.
	 */
	public boolean hasAuctionUpcomingOrLastYear() {
		GregorianCalendar oneYearAgo = (GregorianCalendar)GregorianCalendar.getInstance();
		oneYearAgo.add(GregorianCalendar.YEAR, -1);
		oneYearAgo.add(GregorianCalendar.DAY_OF_YEAR, -365);
		for (int i = 0; i < myAuctions.size(); i++) {
			//check if had an auction less than a year ago or have an upcoming auction already.
			if (myAuctions.get(i).getDate().after(oneYearAgo)) {
				return false;
			}
		}
		return true;
	}
}
