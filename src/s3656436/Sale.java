package s3656436;

import offerException.OfferException;

public class Sale {

	private String saleID, propertyAddress;
	private int currentOffer, reservePrice;
	private boolean acceptingOffers;

	// constructor for sale object, sets accepting offers to a default state of
	// true
	public Sale(String saleID, String propertyAddress, int reservePrice) {
		this.saleID = saleID;
		this.propertyAddress = propertyAddress;
		this.reservePrice = reservePrice;

		acceptingOffers = true;
	}

	public String getSaleID() {
		return saleID;
	}

	public String getPropertyAddress() {
		return propertyAddress;
	}

	public int getReservePrice() {
		return reservePrice;
	}

	public int getCurrentOffer() {
		return currentOffer;
	}

	public void setCurrentOffer(int c) {
		this.currentOffer = c;
	}

	public boolean getAcceptingOffers() {
		return acceptingOffers;
	}

	public void setAcceptingOffers(boolean a) {
		this.acceptingOffers = a;
	}

	public void makeOffer(int offerPrice) throws OfferException {
		// returns user to menu, and prints a message if the object is not
		// accepting sales
		if (!acceptingOffers) {
			throw new OfferException(
					"Error - SaleID: " + getSaleID() + " is not accepting offers!" + '\n' + "Offer rejected.");
			// rejects offer if it is less than the current offer
		} else if (offerPrice <= currentOffer) {
			throw new OfferException("Error - New offer must be higher than current offer!" + '\n' + "Offer rejected!");
			// updates offer price, and checks if it is equal to the reserve
			// price of the sale
		} else {
			setCurrentOffer(offerPrice);
			System.out.printf("\n" + "%-25s %s\n", "Offer accepted! - Offer lower than reserve price", "");
			if (currentOffer >= reservePrice) {
				acceptingOffers = false;
				throw new OfferException("Congratulations! - Offer greater than or equal to the reserve price: "
						+ getReservePrice() + '\n' + "Sale Closed!");
			}
		}

	}

	// returns a string depending on if it is currently accepting offers
	public String getPropertyStatus() {
		if (acceptingOffers)
			return "ON SALE";

		else if (!acceptingOffers)
			return "SOLD";

		else
			return "NOT AVAILABLE";
	}

	// returns a string set that prints all the objects information
	public String getSalesDetails() {
		String firstLine = String.format("%-20s %s\n", "Sale ID:", saleID);
		String secondLine = String.format("%-20s %s\n", "Property Address:", propertyAddress);
		String thirdLine = String.format("%-20s %s\n", "Current Offer", currentOffer);
		String fourthLine = String.format("%-20s %s\n", "Reserve Price", reservePrice);
		String fifthLine = String.format("%-20s %s\n", "Accepting offers:", acceptingOffers);
		String sixthLine = String.format("%-20s %s\n", "Sale Status", getPropertyStatus());
		return firstLine + secondLine + thirdLine + fourthLine + fifthLine + sixthLine;
	}

}
