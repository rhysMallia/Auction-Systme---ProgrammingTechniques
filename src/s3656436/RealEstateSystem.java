package s3656436;

import java.util.*;

import offerException.OfferException;

import java.io.*;

public class RealEstateSystem {

	private static ArrayList<Sale> saleArray = new ArrayList<Sale>();
	// the titles of both text files
	public static final String fileName = "Storage.txt";
	public static final String backupName = "Backup.txt";

	public static void main(String[] args) throws OfferException {
		startProgram();
	}

	// String set that prints the formated menu options
	public static String menu() {
		String zeroString = "------------------------------- \n";
		String firstString = "*** Real Estate System Menu *** \n";
		String secondString = String.format("%-25s %s\n", "Add New Sale", "A");
		String thirdString = String.format("%-25s %s\n", "Submit Offer", "B");
		String fourthString = String.format("%-25s %s\n", "Display Sales Summary", "C");
		String fifthString = String.format("%-25s %s\n", "Add new Auction", "D");
		String sixthString = String.format("%-25s %s\n", "Close Auction", "E");
		String seventhString = String.format("%-25s %s", "Exit Program", "X");

		return zeroString + firstString + secondString + thirdString + fourthString + fifthString + sixthString
				+ seventhString;
	}

	// Method to accept the users choice for menu options, gives a default
	// response and reloops if a non-choice option is picked
	public static void getMenu() throws OfferException {
		String c;

		System.out.println(menu());
		Scanner s = new Scanner(System.in);
		c = s.next();
		System.out.println("-------------------------------");
		switch (c.toLowerCase()) {
		case "a":
			addNewSale();
			break;
		case "b":
			submitOffer();
			break;
		case "c":
			getSalesDetails();
			break;
		case "d":
			addNewAuction();
			break;
		case "e":
			closeAuction();
			break;
		case "x":
			exitProgram();
			break;
		default:
			System.out.println("Error! - Illegal character input, please try again.");
			getMenu();
		}
		if(c.isEmpty()){
			isEmpty();
		}
		s.close();

	}

	// Method to prompt and accept information to create a new sale object and
	// add it to array
	public static void addNewSale() throws OfferException {

		Scanner a = new Scanner(System.in);
		System.out.printf("%-44s %s ", "Enter Sale ID for new Property Sale:", "");
		String tempID = a.nextLine();

		// loop to run through array and check if the sale ID already exists
		for (int i = 0; i < saleArray.size(); i++) {
			if (tempID.equals(saleArray.get(i).getSaleID())) {
				System.out.printf("%-45s %s\n", "Error! Sale ID already exists.", "");
				getMenu();
			}
		}
		System.out.printf("%-45s %s", "Enter Property Address for new Property Sale:", "");
		String tempProperty = a.nextLine();

		System.out.printf("%-45s %s", "Enter Reserve Price for new Property Sale:", "");
		int tempPrice = a.nextInt();

		// creating the object and adding it to the array
		Sale newSale = new Sale(tempID, tempProperty, tempPrice);
		saleArray.add(newSale);
		System.out.println('\n' + "New Property Sale added successfully for property at " + tempProperty + "!" + '\n');

		// looping back to the menu and closing the scanner
		getMenu();
		a.close();
	}

	// similar method that creates an auction object using user input
	public static void addNewAuction() throws OfferException {
		Scanner a = new Scanner(System.in);

		System.out.printf("%-45s %s", "Enter Sale ID for new Auction Sale:", "");
		String tempID = a.nextLine();
		if(tempID.isEmpty()){
			isEmpty();
		}

		for (int i = 0; i < saleArray.size(); i++) {
			if (tempID.equals(saleArray.get(i).getSaleID())) {
				System.out.printf("%-45s %s\n", "Error! Sale ID already exists.", "");
				getMenu();
			}
		}

		System.out.printf("%-45s %s", "Enter Property Address for new Auction Sale:", "");
		String tempProperty = a.nextLine();

		System.out.printf("%-45s %s", "Enter Reserve Price for new Auction Sale:", "");
		int tempPrice = a.nextInt();

		Auction newSale = new Auction(tempID, tempProperty, tempPrice);
		saleArray.add(newSale);
		System.out.println("New Auction added!");
		getMenu();
		a.close();
	}

	// method that submits an offer to a selected object(Auction or Sale)
	public static void submitOffer() throws OfferException {
		// Try loop to catch any errors
		try {
			// This boolean is used as a check if the sale ID is found in the
			// array
			boolean checkIf = false;
			Scanner a = new Scanner(System.in);
			System.out.printf("\n" + "%-10s %s", "Enter Sale ID:", "");
			String tempSaleID = a.nextLine();

			// loop that uses the saleID entered by the user and checks if it is
			// currently accepting offers
			for (int i = 0; i < saleArray.size(); i++) {
				if (tempSaleID.equalsIgnoreCase(saleArray.get(i).getSaleID())) {
					checkIf = true;
					if (!saleArray.get(i).getAcceptingOffers()) {
						System.out.printf("\n" + "%-25s %s\n", "Error! Sale ID: " + saleArray.get(i).getSaleID()
								+ " is closed and cannot accept any offers.", "");
					}

					System.out.printf("%-10s %s\n", "Current offer: $", saleArray.get(i).getCurrentOffer());
					System.out.printf("%-10s %s", "Enter new offer:", "");
					int tempOffer = a.nextInt();

					// Invokes the makeOffer method using the offer entered by
					// the user
					saleArray.get(i).makeOffer(tempOffer);

				}

			}
			// if the ID is not found in the array, then a message is displayed
			// and the user is resent to menu
			if (!checkIf) {
				System.out.printf("\n" + "%-10s %s\n", "Error! ID not found.", "");
				getMenu();
				a.close();
			}
		} catch (OfferException e) {
			System.out.println(e.getMessage());
			getMenu();
		}
		// error exception that prints a message and resends user to menu if
		// they enter anything but a int
		catch (InputMismatchException e) {
			System.out.println("Error! - You have not entered a numeral");
			getMenu();
		}
	}

	// method that prints the sales details of the selected object
	public static void getSalesDetails() throws OfferException {
		for (int v = 0; v < saleArray.size(); v++) {
			System.out.println(saleArray.get(v).getSalesDetails());
		}
		getMenu();
	}

	// Method to set the status of an auction object
	public static void closeAuction() throws OfferException {
		// returns message and sends user back to menu if the current array is
		// empty
		if (saleArray.isEmpty()) {
			System.out.printf("%-25s %s\n", "No Auction found. Returning to menu!", "");
			getMenu();
		}
		// boolean and loop that runs through array to find a auction object
		boolean found = false;
		for (int x = 0; x < saleArray.size(); x++) {

			if (saleArray.get(x) instanceof Auction) {
				found = true;
			}

		}
		// if no object is found, then it prints a message and returns user to
		// menu
		if (!found) {
			System.out.printf("%-25s %s\n", "No Auction found. Returning to menu!", "");
			getMenu();
		}
		System.out.printf("\n" + "%-25s %s", "Please enter ID of auction to be closed:", "");
		Scanner a = new Scanner(System.in);
		String tempID = a.nextLine();

		// loop runs through array using the entered ID, checks if the object is
		// a auction
		for (int x = 0; x < saleArray.size(); x++) {
			if (tempID.equalsIgnoreCase(saleArray.get(x).getSaleID())) {
				if (saleArray.get(x) instanceof Auction) {
					// sets the object to be closed via the close auction method
					if (((Auction) saleArray.get(x)).closeAuction()) {
						System.out.printf(
								"\n" + "%-25s %s\n", "Auction " + saleArray.get(x).getSaleID()
										+ " has ended - property has been: " + saleArray.get(x).getPropertyStatus(),
								"");
						getMenu();
					}
				}
				// this else prints a message if the object found was a Sale
				// object and not a Auction
				else {
					System.out.printf("\n" + "%-25s %s\n", "Property Sale ID " + tempID + " is not an auction!", "");
					getMenu();
				}
			}
		}
		// returns user to menu and prints message if no object with the entered
		// ID is found
		System.out.printf("\n" + "%-25s %s\n", "Error - property " + tempID + " not found!", "");
		getMenu();
		a.close();

	}

	// This method prints all the entered objects to two text files to be
	// restored later on
	public static void exitProgram() throws OfferException {
		Scanner accept = new Scanner(System.in);
		String answer = null;
		try {
			// Confirming that the user wants to completely exit program and
			// begin the writing
			System.out.printf("\n" + "%-25s %s\n", "Are you sure you'd like to exit?", "Y to confirm, else to cancel.");
			answer = accept.next();

			// switch statement that continues the code if a Y is entered, else
			// it just loops back to menu
			switch (answer.toUpperCase()) {
			case "Y":
				// creation of the two print writers that saves the objects
				PrintWriter output = new PrintWriter(new FileOutputStream(fileName));
				PrintWriter backup = new PrintWriter(new FileOutputStream(backupName));
				// loop that checks what instance the object is, and saves the
				// relevent information in a
				// bruteforce style method for both print writers
				for (int x = 0; x < saleArray.size(); x++) {
					if (saleArray.get(x) instanceof Auction) {
						// this first line is the identifer that tells the
						// scanner what kind of object it is
						output.println("~Auction");
						output.println(((Auction) saleArray.get(x)).getHighestBidder());
						output.println(saleArray.get(x).getSaleID());
						output.println(saleArray.get(x).getPropertyAddress());
						output.println(saleArray.get(x).getReservePrice());
						output.println(saleArray.get(x).getCurrentOffer());
						output.println(saleArray.get(x).getAcceptingOffers());
						backup.println("~Auction");
						backup.println(((Auction) saleArray.get(x)).getHighestBidder());
						backup.println(saleArray.get(x).getSaleID());
						backup.println(saleArray.get(x).getPropertyAddress());
						backup.println(saleArray.get(x).getReservePrice());
						backup.println(saleArray.get(x).getCurrentOffer());
						backup.println(saleArray.get(x).getAcceptingOffers());
					}

					else if (saleArray.get(x) instanceof Sale) {
						// identifer
						output.println("~Sale");
						output.println(saleArray.get(x).getSaleID());
						output.println(saleArray.get(x).getPropertyAddress());
						output.println(saleArray.get(x).getReservePrice());
						output.println(saleArray.get(x).getCurrentOffer());
						output.println(saleArray.get(x).getAcceptingOffers());
						backup.println("~Sale");
						backup.println(saleArray.get(x).getSaleID());
						backup.println(saleArray.get(x).getPropertyAddress());
						backup.println(saleArray.get(x).getReservePrice());
						backup.println(saleArray.get(x).getCurrentOffer());
						backup.println(saleArray.get(x).getAcceptingOffers());
					}

				}
				output.close();
				backup.close();
				System.out.printf("\n" + "%-25s %s\n", "Save complete! - Exiting...", "");
				break;

			default:
				getMenu();
				break;
			}
			accept.close();
		}
		// this catches an error if no text folders are present, it prints a
		// message and loops to menu
		catch (FileNotFoundException e) {
			e.getMessage();
			System.out.print("Error! - Sales / Auctions could not be saved, please try again and pray.");
			getMenu();
		} catch (NoSuchElementException e) {
			e.getMessage();
		}
	}

	// this method grabs all the information from the text files and restores
	// the objects back into the array
	public static void startProgram() throws OfferException {
		try {
			// creating the scanner that runs through the files
			Scanner input = new Scanner(new File(fileName));
			// loop that continues as long as the file has another line present
			while (input.hasNextLine()) {
				// This string captures the indentifier and then does a relevent
				// if statement to recreate the object
				String catagory = input.nextLine();
				// this loop does the relevent creation of the object and its
				// extra information via
				if (catagory.equals("~Auction")) {
					String tempBidder = input.nextLine();
					String tempID = input.nextLine();
					String tempAddress = input.nextLine();
					int tempPrice = input.nextInt();
					int tempOffer = input.nextInt();
					boolean tempAcceptingOffers = input.nextBoolean();
					// creating the object, and applying information like
					// highestbidder, currentoffer and acceptingoffers
					Auction newAuction = new Auction(tempID, tempAddress, tempPrice);
					saleArray.add(newAuction);
					newAuction.setHighestBidder(tempBidder);
					newAuction.setCurrentOffer(tempOffer);
					newAuction.setAcceptingOffers(tempAcceptingOffers);

				}

				else if (catagory.equals("~Sale")) {
					String tempID = input.nextLine();
					String tempAddress = input.nextLine();
					int tempPrice = input.nextInt();
					int tempOffer = input.nextInt();
					boolean tempAcceptingOffers = input.nextBoolean();
					Sale newSale = new Sale(tempID, tempAddress, tempPrice);
					saleArray.add(newSale);
					newSale.setCurrentOffer(tempOffer);
					newSale.setAcceptingOffers(tempAcceptingOffers);

				}
			}
			System.out.println("Save File successfully restored!");
			getMenu();
			input.close();
		} catch (FileNotFoundException e) {
			// this is a bruteforce method that uses the backup file, its only
			// used if the first file is not found
			// it's placed into its own try loop to allow it to also be checked
			// for errors
			try {
				Scanner backput = new Scanner(new File(backupName));
				while (backput.hasNextLine()) {
					String catagory = backput.nextLine();
					if (catagory.equals("~Auction")) {
						String tempBidder = backput.nextLine();
						String tempID = backput.nextLine();
						String tempAddress = backput.nextLine();
						int tempPrice = backput.nextInt();
						int tempOffer = backput.nextInt();
						boolean tempAcceptingOffers = backput.nextBoolean();
						Auction newAuction = new Auction(tempID, tempAddress, tempPrice);
						saleArray.add(newAuction);
						newAuction.setHighestBidder(tempBidder);
						newAuction.setCurrentOffer(tempOffer);
						newAuction.setAcceptingOffers(tempAcceptingOffers);

					}

					else if (catagory.equals("~Sale")) {
						String tempID = backput.nextLine();
						String tempAddress = backput.nextLine();
						int tempPrice = backput.nextInt();
						int tempOffer = backput.nextInt();
						boolean tempAcceptingOffers = backput.nextBoolean();
						Sale newSale = new Sale(tempID, tempAddress, tempPrice);
						saleArray.add(newSale);
						newSale.setCurrentOffer(tempOffer);
						newSale.setAcceptingOffers(tempAcceptingOffers);

					}
				}
				System.out.println("Backup Save File successfully restored!");
				getMenu();
				backput.close();
			} catch (FileNotFoundException e1) {
				// This is used if either file is not found
				System.out.println("Error! - No files found, no data ");
				getMenu();
			}
		} catch (InputMismatchException e) {
			e.getMessage();
		} catch (NoSuchElementException e) {
			e.getMessage();
		}
	}

	// this message runs if an entered string is found to be empty
	// it reloops the user to the menu and prints a message
	public static void isEmpty() throws OfferException {
		System.out.println("Error! - Nothing has been entered, please try again.");
		getMenu();
	}
}
