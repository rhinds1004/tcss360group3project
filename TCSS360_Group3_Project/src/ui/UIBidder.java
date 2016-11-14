package ui;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import model.Auction;
import model.Bidder;
import model.Calendar;
import model.Item;


/**
 * Handles all the bidder interaction.
 * 
 * @author David Nowlin
 * @version 11/11/2016
 *
 */
public class UIBidder {
	private static Scanner myScanner = new Scanner(System.in);
	private static String myHeadline = null;

	/**
	 * this is what start all the Bidder UI classes.
	 * dose all the bidder need to do.
	 * @param theBidder the user that is in the UI.
	 * @param theCalendar of auction center.
	 * @return
	 */
	public static Calendar beginBidderUI(Bidder theBidder, Calendar theCalendar) {
		int choose = 0;
		myHeadline = "AuctionCentral: the auctioneer for non-profit organization.\n" + theBidder.getUsername()
				+ " logged in as Bidder.\n";
		System.out.println(myHeadline);
		do {
			System.out.print("1) Views List Of Acution.\n" + "2) Exit.\n" + "Enter your Selection from 1 to 2: ");
			choose = myScanner.nextInt();
		} while (choose != 1 && choose != 2);
		if (choose == 1) {
			pickAuction(theBidder, theCalendar);
		}
		return theCalendar;
	}

	/*
	 * this will show all the auction that you can pick from.
	 */
	private static Bidder pickAuction(Bidder theBidder, Calendar theCalendar) {
		int choose = 0;
		boolean exit = false;
		while (!exit) {
			System.out.println(myHeadline);
			List<Auction> desiredAcctions = theCalendar.getAuctions((GregorianCalendar) GregorianCalendar.getInstance()); //TODO: getting a zero and added need to talk to Justin
			for (int i = 0; i < (theCalendar.getUpcomingAuctionsNumber() + 1); i++) {
				System.out.println((i+1) + ") " + desiredAcctions.get((i)).getAuctionName());
//						+ desiredAcctions.get(i).getDate().DATE + "   ");
			}
			System.out.println((theCalendar.getUpcomingAuctionsNumber() + 2) + ") Exit");
			do {
				System.out.print("Enter your Selection from 1 to " + (theCalendar.getUpcomingAuctionsNumber() + 2) + ": ");
				choose = myScanner.nextInt();
			} while (choose < 1 || choose > (theCalendar.getUpcomingAuctionsNumber() + 2));
			if ((theCalendar.getUpcomingAuctionsNumber() + 2) == choose) {
				return theBidder;
			}
			exit = viewItem(theBidder, desiredAcctions.get((choose - 1)));
		}
		return theBidder;
	}

	/*
	 * view all the item in a select auction let you choose to go back or choose to bid.
	 */
	private static boolean viewItem(Bidder theBidder, Auction theAuction) {
		int choose = 0;
		boolean exit = false;
		boolean goBack = false;
		while (!exit && !goBack) {
			System.out.println(myHeadline);
			String AuctionInfo = theAuction.getAuctionName() + ", " + theAuction.getDate().DAY_OF_MONTH + ", "
					+ theAuction.getDate().DATE + ", " + theAuction.getDate().YEAR; // TODO:need to fix to display the date and with date.
			System.out.println(AuctionInfo);
			List<Item> itemlist = theAuction.getItems();
			do {
				System.out.println("Items Offered For Sale:\n" + "ID\tItem Name\t\t\tCondition\tMin Bid\tMy Bid");
				for (int i = 0; i < itemlist.size(); i++) {
					System.out.print(itemlist.get(i).getID() + "\t" + itemlist.get(i).getName() + "\t\t\t"
							+ itemlist.get(i).getCondition() + "\t" + itemlist.get(i).getMinBid() + "\t");//TODO: fix the format of the string.
				if(itemlist.get(i).getBidOf(theBidder.getUsername()) != null) {
					System.out.print(itemlist.get(i).getBidOf(theBidder.getUsername()).getBidAmount());
				}
				System.out.print("\n");
				}
				System.out.println("1) Bid On An Item.\n2) Go Back.\n3) Exit AuctionCentral.");// TODO: add a way to exit
				choose = myScanner.nextInt();
			} while (choose < 1 || choose > 3);
			if (choose == 1) {
				selectItem(theBidder, theAuction);
			} else if (choose == 2) {
				goBack = true;
				System.out.println(goBack);
			} else if (choose == 3){
				exit = true;//TODO: find a way to exit program with out throwing error.
			}
		}
		return exit;
	}
	
	/*
	 * this will allow you to select which item to bid on.
	 */
	private static void selectItem(Bidder theBidder, Auction theAuction) {
		int choose = 0;
		String AuctionInfo = theAuction.getAuctionName() + ", " + theAuction.getDate().DAY_OF_MONTH + ", "
				+ theAuction.getDate().DATE + ", " + theAuction.getDate().YEAR; // TODO:need to fix to display the date and with date.
		System.out.println(AuctionInfo);
		List<Item> itemlist = theAuction.getItems();
		
		do {
			System.out.println("Items Offered For Sale:\n" + "ID\tItem Name\t\t\tCondition\tMin Bid\tMy Bid");
			for (int i = 0; i < itemlist.size(); i++) {
				System.out.print(itemlist.get(i).getID() + "\t" + itemlist.get(i).getName() + "\t\t\t"
						+ itemlist.get(i).getCondition() + "\t" + itemlist.get(i).getMinBid() + "\t");
				if(itemlist.get(i).getBidOf(theBidder.getUsername()) != null) {
					System.out.print(itemlist.get(i).getBidOf(theBidder.getUsername()).getBidAmount());
				}
				System.out.print("\n");
			}
			System.out.print("\nType Item ID to get more information and bid on the item :");
			choose = myScanner.nextInt();
		} while (choose < 1 || (itemlist.size()) < choose);
		placeBid(theBidder, itemlist.get((choose-1)));
	}
	
	/*
	 * this is will you place your bid.
	 */
	private static void placeBid(Bidder theBidder, Item theItem) {
		double bid = 0;
		int choose = 0;
		do {
			System.out.println(theItem.getName() + "\t" + theItem.getCondition() + " condition " + theItem.getMinBid());
			System.out.println(theItem.getDescription() + "\n\n"
					+ "What would you like to do?\n"
					+ "1) Place bid on this item.\n"
					+ "2) Go back\n");
			choose = myScanner.nextInt();
		} while (choose < 1 || choose > 2);
		if(choose == 1){
			bid = 0;
			do {
				System.out.println("Enter bid of least $" + theItem.getMinBid() + "(no sollar sign or period after dollar amount");
				bid = myScanner.nextDouble();
			} while (bid < theItem.getMinBid());
			System.out.println("You have placed a bid of $" + bid + " on " + theItem.getName() + ",\n"
					+ "AuctionCentral will notify you after auction to let you know if\n"
					+ "yours is the winning bid.\n");
		}
		theBidder.placeBid(theItem, bid);
	}
}
