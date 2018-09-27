package s3656436;

import java.util.Scanner;

import offerException.OfferException;

public class Auction extends Sale {

	private String highestBidder;

	// constructor for the auction object, it automatically sets highest bidder
	// placed as it is the default state
	public Auction(String saleID, String propertyAddress, int reservePrice) {
		super(saleID, propertyAddress, reservePrice);
		highestBidder = "NO BIDS PLACED";
	}

	public String getHighestBidder() {
		return highestBidder;
	}

	public void setHighestBidder(String a) {
		this.highestBidder = a;
	}

	// returns relevant string depending on the objects getAcceptingOffers
	// variable
	public String getPropertyStatus() {
		if (super.getAcceptingOffers())
			return "ACCEPTING BIDS";

		else if (super.getReservePrice() <= super.getCurrentOffer())
			return "SOLD";

		else
			// the default state
			return "PASSED IN";
	}

	// closes the auction by setting the acceptingOffers method for the object
	// to false
	public boolean closeAuction() {
		if (super.getAcceptingOffers()) {
			super.setAcceptingOffers(false);
			return true;
		}

		return false;
	}

	// this method updates the currentoffer of an object
	public void makeOffer(int offerPrice) throws OfferException {
		// returns user to menu if the object is not currently accepting offers
		if (!getAcceptingOffers()) {
			throw new OfferException(
					"Error - SaleID: " + getSaleID() + " is not accepting offers!" + '\n' + "Offer rejected.");
			// checks if the entered price is less than the current set offer
		} else if (offerPrice <= getCurrentOffer()) {
			throw new OfferException("Error - New offer must be higher than current offer." + '\n' + "Offer rejected!");
		}
		// prompts and saves the highest bidder for an auction object
		System.out.printf("%-10s %s", "Enter Bidder name:", "");
		Scanner b = new Scanner(System.in);
		String tempBidder = b.nextLine();
		setHighestBidder(tempBidder);
		System.out.printf("\n" + "%-25s %s\n", "Offer accepted! - Offer below reserve price.", "");
		setCurrentOffer(offerPrice);
		b.close();
		// checks if the offer price is greater or equal to the reserve price,
		// and then congratulates the user on a new house!
		if (getCurrentOffer() >= getReservePrice()) {
			setAcceptingOffers(false);
			throw new OfferException("Congratulations! - Offer greater than or equal to the reserve price: "
					+ getReservePrice() + '\n' + "Sale Closed! Enjoy your new home " + getHighestBidder() + ".");

		}

	}

	// this method overloads the Sale object method and adds the exra highest
	// bidder element
	public String getSalesDetails() {
		String seventhLine = String.format("%-20s %s\n", "Current Highest Bidder:", getHighestBidder());
		return super.getSalesDetails() + seventhLine;
	}

}
