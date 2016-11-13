package testing;

import java.util.GregorianCalendar;
import java.util.List;

import org.junit.*;
import org.junit.Test;

import junit.framework.*;
import model.Auction;
import model.Calendar;
import model.Item;

public class AuctionTest extends TestCase {
	Calendar testCalendar;
	Auction testAuction;
	Item testItem1;
	Item testItem2;
	
	@Before
	public void setUp() {
		testCalendar = new Calendar();
		GregorianCalendar aDate = (GregorianCalendar)GregorianCalendar.getInstance();
		testAuction = new Auction(aDate, "test");
		testItem1 = new Item(testCalendar.getNextItemID() + "", "Football", "A football, wow!", 10.01, 2, "good");
		testItem2 = new Item(testCalendar.getNextItemID() + "", "Handball", "A handball, wow!", 10.51, 1, "good");
	}
	
	/**
	 * Tests to make sure multiple different items can be added to an Auction
	 */
	@Test
	public void testaddItemDifferentItem() {
		assertTrue(testAuction.addItem(testItem1));
		assertTrue(testAuction.addItem(testItem2));
	}
	
	/**
	 * Tests to make sure multiple of the same item cannot be added to an Auction
	 */
	@Test
	public void testaddItemSameItem() {
		assertTrue(testAuction.addItem(testItem1));
		assertFalse(testAuction.addItem(testItem1));
	}
}