//all other classes as of this commit are implemented
//if the word test is passed when this file is ran a pregenerated series of tests will run to test methods (not implemented yet)

import management.*;
import java.util.*;

public class Manager {
    private Scanner scan = new Scanner (System.in); //scanner for user input when things go wrong in methods
    private int currentMonth;
    private int currentDay;
    private int currentYear;
    private String latestLowStockReport = "report not generated yet"; //most recently generated lowStockReport
	private String latestLowStockReportDate = ""; //date of most recently generated lowStockReport
    private ArrayList<Cashier> cashiers = new ArrayList<>();
    private ArrayList<Supplier> suppliers = new ArrayList<>(); 
    private HashMap<String, Product> inventory = new HashMap<>();
    private ArrayList<String> inventoryPriceOrder = new ArrayList<>(); //keys for prices in ascending (least-greatest)
    private ArrayList<String> inventoryStockOrder = new ArrayList<>(); //keys for stock percentage in ascending
    private ArrayList<String> inventoryAlphabetOrder = new ArrayList<>(); //keys for alphabetical order
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<Sale> sales = new ArrayList<>();
    
    public Manager (int currentMonth, int currentDay, int currentYear) {
        this.currentMonth = currentMonth;
        this.currentDay = currentDay;
        this.currentYear = currentYear;
    }
    
    private int getMaxDay() { //helper method that returns the highest day in the month
        if (currentMonth == 2)
            return 28;
        else if (currentMonth == 4 || currentMonth == 6 || currentMonth == 9 || currentMonth == 11)
            return 30;
        else
            return 31;
    }
    public void incrementDay() {
        if (currentDay + 1 > this.getMaxDay()) {
            if (currentMonth == 12) {
                this.currentMonth = 1;
                this.currentYear += 1;
            }
            else
                this.currentMonth += 1;
            this.currentDay = 1;
        }
        else
            this.currentDay += 1;
    }
	private String currentDateToStr() { //helper method that returns the date in form mm/dd/yyyy
        if (currentMonth < 10) { 
            if (currentDay < 10) 
                return "0" + currentMonth + "/0" + currentDay + "/" + currentYear;
            else //just the month needs zero
                return "0" + currentMonth + "/" + currentDay + "/" + currentYear;
        }
        else if (currentDay < 10)
            return currentMonth + "/0" + currentDay + "/" + currentYear;
        else
            return currentMonth + "/" + currentDay + "/" + currentYear;
    }
    public String generateLowStockReport() {
        String lowProducts = "";
		latestLowStockReportDate = this.currentDateToStr();
        for (Product item : inventory.values()) {
            if (item.getStockPercentage() <= item.getLowPercentage())
               lowProducts += item.getName() + ","; //adds comma to separate
        }
        if (lowProducts.equals("")) {
            latestLowStockReport = "No low products"; 
            return "No low products";
        }
        else {
			lowProducts = lowProducts.substring(0,lowProducts.length()-1); //strip the last comma
            latestLowStockReport = lowProducts;
            return lowProducts;
        }
    }
	//prints low stock report to terminal
	public void displayLowStockReport() {
		if (latestLowStockReportDate.equals("")) {
			System.out.println("report not generated yet");
		}
		else {
			String[] lowItems = latestLowStockReport.split(",");
			System.out.println("Date of Report: " + latestLowStockReportDate);
			for (int i = 1; i <= lowItems.length; i++) {
				System.out.println(i + ": " + lowItems[i-1] + "| stock: " + 
				inventory.get(lowItems[i-1]).getCurrentStock() + "/" + inventory.get(lowItems[i-1]).getMaxStock());
			}
		}
	}
    //returns upcoming shipments within a certain timeframe
    public String[][] getUpcomingShipments(String timeframe) {
        throw new UnsupportedOperationException("not implemented yet");
    }
    //returns all upcoming shipments
    public String[][] getUpcomingShipments() {
        throw new UnsupportedOperationException("not implemented yet");
    }
    public String receiveShipment(Supplier supplier, int id) {
        throw new UnsupportedOperationException("not implemented yet");
    }
    //peek at an upcoming shipment from supplier with matching id, return string displaying info about it
    public String peekShipment(Supplier supplier, int id) {
        throw new UnsupportedOperationException("not implemented yet");
    }
    public Product searchInventory(String productName) {
        return inventory.get(productName); 
    }
    //0 for chronological, 1 for alphabetical, 2 for price descending, 3 for price ascending
    //4 for percentage stock descending, 5 for percentage stock ascending
    //make a text file if toFile is true, "Inventory-<MM>-<DD>-<YYYY>.txt"
    public void displayInventory(int criteria, boolean toFile) {
        throw new UnsupportedOperationException("not implemented yet");
    }
    public void addProduct(String productName, double cost, int currentStock, int maxStock, int lowPercentage) {
        Product newProduct = new Product (productName, cost, currentStock, maxStock, lowPercentage);
        if (inventory.get(productName) != null)  { //key already exists
            System.out.print("This product already exists, continuing would overwrite this item; continue? [y/n]: ");
            String response = this.scan.next();
            if (response.toLowerCase().equals("n")) {
               System.out.println("Product not successfully added");
               return;
            }
            else { //overwriting an existing item
               inventory.put(productName, newProduct);
               //Shift the position of the key in sorted lists to maintain order
               inventoryPriceOrder.remove(productName);
               inventoryStockOrder.remove(productName);
               inventoryAlphabetOrder.remove(productName);
               int index = 0; 
               for (int i = 0; i < inventoryPriceOrder.size()-1; i++) { //price order
                  if (cost > inventory.get(inventoryPriceOrder.get(i)).getCost())
                     index++;
                  else
                     break;
               }
               inventoryPriceOrder.add(index, productName);
               index = 0;
               for (int i = 0; i < inventoryStockOrder.size() - 1; i++) { //stock percentage order
                  if (newProduct.getStockPercentage() > inventory.get(inventoryStockOrder.get(i)).getStockPercentage())
                     index++;
                  else
                     break;
               }
               inventoryStockOrder.add(index, productName); 
               index = 0;
               for (int i = 0; i < inventoryAlphabetOrder.size() - 1; i++) { //alphabetical order
                  if (productName.compareToIgnoreCase(inventory.get(inventoryAlphabetOrder.get(i)).getName()) > 0)
                     index++;
                  else
                     break;
               }
            }
        }
        else {
            inventory.put(productName, newProduct);
            //add new productName to sorted lists in correct spot
            int index = 0; 
            for (int i = 0; i < inventoryPriceOrder.size()-1; i++) { //price order
               if (cost > inventory.get(inventoryPriceOrder.get(i)).getCost())
                  index++;
               else
                  break;
            }
            inventoryPriceOrder.add(index, productName);
            index = 0;
            for (int i = 0; i < inventoryStockOrder.size() - 1; i++) { //stock percentage order
               if (newProduct.getStockPercentage() > inventory.get(inventoryStockOrder.get(i)).getStockPercentage())
                  index++;
               else
                  break;
            }
            inventoryStockOrder.add(index, productName); 
            index = 0;
            for (int i = 0; i < inventoryAlphabetOrder.size() - 1; i++) { //alphabetical order
               if (productName.compareToIgnoreCase(inventory.get(inventoryAlphabetOrder.get(i)).getName()) > 0)
                  index++;
               else
                  break;
            }
        }
    }
    public void removeProduct(String productName) {
        if (inventory.get(productName) != null) {
            inventory.remove(productName);
            System.out.println(productName + " was removed");
			inventoryPriceOrder.remove(productName);
			inventoryStockOrder.remove(productName);
			inventoryAlphabetOrder.remove(productName);
        }
        else {
            System.out.println(productName + " does not exist"); 
        }
    }
    
    public String mostSoldItem(String timeframe) {
        HashMap<Product, Integer> products = new HashMap<>(); //product object is the key, values will be sum total of that product sold
		HashMap<Product, Integer> curProductsSold = new HashMap<>();
		
		if (timeframe.equals("month")) {
			for (Sale sale : sales) {
				if (sale.getMonthOfSale() != currentMonth) //stop counting
					break;
				curProductsSold = sale.getProductsSold();
				for (Product item : curProductsSold.keySet()) {
					if (products.get(item) == null) { //product hasn't been counted yet
						products.put(item, curProductsSold.get(item));
					}
					else { //product is in map already
						products.put(item, curProductsSold.get(item) + products.get(item));
					}
				}
			}
		}
		else if (timeframe.equals("day")) {
			for (Sale sale : sales) {
				if (sale.getMonthOfSale() != currentDay) //stop counting
					break;
				curProductsSold = sale.getProductsSold();
				for (Product item : curProductsSold.keySet()) {
					if (products.get(item) == null) { //product hasn't been counted yet
						products.put(item, curProductsSold.get(item));
					}
					else { //product is in map already
						products.put(item, curProductsSold.get(item) + products.get(item));
					}
				}
			}
		}
		else if (timeframe.equals("year")) {
			for (Sale sale : sales) {
				if (sale.getMonthOfSale() != currentYear) //stop counting
					break;
				curProductsSold = sale.getProductsSold();
				for (Product item : curProductsSold.keySet()) {
					if (products.get(item) == null) { //product hasn't been counted yet
						products.put(item, curProductsSold.get(item));
					}
					else { //product is in map already
						products.put(item, curProductsSold.get(item) + products.get(item));
					}
				}
			}
		}
		else { //timeframe wasn't entered correctly
			System.out.println("invalid timeframe");
			return "error";
		}
		if (products.keySet().isEmpty()) { //no sales were counted
			System.out.println("No products sold within timeframe");
			return ""; 
		}
		else { //normal case, at least one sale so products map is populated
			String mostSold = "";
			int largest = -1;
			for (Product item : products.keySet()) {
				if (products.get(item) > largest) {
					largest = products.get(item);
					mostSold = item.getName();
				}
			}
			return mostSold; 
		}
    }
    public int numSold(String productName, String timeframe) {
        int count = 0;
		HashMap<Product, Integer> curProductsSold = new HashMap<>();
		
		if (timeframe.equals("month")) {
			for (Sale sale : sales) {
				if (sale.getMonthOfSale() != currentMonth) //stop counting
					break;
				curProductsSold = sale.getProductsSold();
				for (Product item : curProductsSold.keySet()) {
					if (item.getName().equals(productName)) {
						count += curProductsSold.get(item);
						break;
					}
				}
			}
		}
		else if (timeframe.equals("day")) {
			for (Sale sale : sales) {
				if (sale.getDayOfSale() != currentDay) //stop counting
					break;
				curProductsSold = sale.getProductsSold();
				for (Product item : curProductsSold.keySet()) {
					if (item.getName().equals(productName)) {
						count += curProductsSold.get(item);
						break;
					}
				}
			}
		}
		else if (timeframe.equals("year")) {
			for (Sale sale : sales) {
				if (sale.getYearOfSale() != currentYear) //stop counting
					break;
				curProductsSold = sale.getProductsSold();
				for (Product item : curProductsSold.keySet()) {
					if (item.getName().equals(productName)) {
						count += curProductsSold.get(item);
						break;
					}
				}
			}
		}
		else {
			System.out.println("invalid timeframe");
			return -1;
		}
		return count;
    }
    public int numSales(String timeframe) {
		int count = 0;
		if (timeframe.equals("month")) {
			for (Sale sale : sales) {
				if (sale.getMonthOfSale() != currentMonth) //stop counting
					break;
				count++;
			}
		}
		else if (timeframe.equals("day")) {
			for (Sale sale : sales) {
				if (sale.getDayOfSale() != currentDay) //stop counting
					break;
				count++;
			}
		}
		else if (timeframe.equals("year")) {
			for (Sale sale : sales) {
				if (sale.getYearOfSale() != currentYear) //stop counting
					break;
				count++;
			}
		}
		else {
			System.out.println("invalid timeframe");
			return -1;
		}
		return count;
    }
	//timeframe is either "month", "day", or "year"
	//since sales are in reverse chronological order, count sales until the selected timeframe doesn't match respective current
	//ex. if the timeframe is "day" and the day is 21, then start from the beginning until the day is not 21
    public double averageSalePrice(String timeframe) {
        double sum = 0.0;
		int count = 0;
		if (timeframe.equals("month")) {
			for (Sale sale : sales) {
				if (sale.getMonthOfSale() != currentMonth) //stop counting
					break;
				sum += sale.totalOfSale();
				count++;
			}
		}
		else if (timeframe.equals("day")) {
			for (Sale sale : sales) {
				if (sale.getDayOfSale() != currentDay) //stop counting
					break;
				sum += sale.totalOfSale();
				count++;
			}
		}
		else if (timeframe.equals("year")) {
			for (Sale sale : sales) {
				if (sale.getYearOfSale() != currentYear) //stop counting
					break;
				sum += sale.totalOfSale();
				count++;
			}
		}
		else {
			System.out.println("invalid timeframe");
			return -1.0;
		}
		
		if (count == 0) {
			System.out.println("no sales in timeframe");
			return 0.0;
		}
		else {
			return (sum / (double)count);
		}
    }
    
    public void addCashier(String firstName, String lastName, int id, double salary) {
        Cashier newCashier = new Cashier(id, firstName, lastName, salary);
		boolean isInDatabase = false;
		for (Cashier cashier : cashiers) {
			if (cashier.getFirstName().equals(firstName) && cashier.getLastName().equals(lastName)) {
				isInDatabase = true;
				break;
			}
		}
        if (isInDatabase) { //Cashier is already in the database
            System.out.println("Unable to add Cashier " + firstName + " " + lastName + ", already in system.");
		}
        else {
            cashiers.add(newCashier);
            System.out.println("Successfully added Cashier " + firstName + " " + lastName);
		}
    }
	
    public void removeCashier(int id) {
        boolean removedCashier = false;
        for (int currentPosition = 0; currentPosition < cashiers.size(); currentPosition++) {
            if (cashiers.get(currentPosition).getId() == id) {
                cashiers.remove(currentPosition);
                removedCashier = true;
                break;
			}
        }
        if (removedCashier) //Print statments to confirm if removeCashier was successful
            System.out.println("Succesfully removed Cashier with id " + id);
        else
            System.out.println("Unable to remove Cashier with id " + id);
    }

    public Cashier searchCashierByName(String first, String last) {
        for (int currentPosition = 0; currentPosition < cashiers.size(); currentPosition++) {
            if ( (cashiers.get(currentPosition).getFirstName().equals(first)) && (cashiers.get(currentPosition).getLastName().equals(last)) )
                return cashiers.get(currentPosition);
        }
        System.out.println("Unable to find Cashier " + first + " " + last); //If cashier is not found, null is returned instead
        return null;
    }

    public ArrayList<Cashier> searchCashierByName(String first) {
        ArrayList<Cashier> cashiersFound = new ArrayList<>();
        for (int currentPosition = 0; currentPosition < cashiers.size(); currentPosition++) {
            if (cashiers.get(currentPosition).getFirstName().equals(first))
                cashiersFound.add(cashiers.get(currentPosition));
        }
        return cashiersFound;
    }

    public void displayCashiers() {
        System.out.println();
        System.out.println("Cashiers:");
        System.out.println("First Name      Last Name        ID        Salary");
		Cashier currentCashier;
        for (int currentPosition = 0; currentPosition < cashiers.size(); currentPosition++) {
            currentCashier = cashiers.get(currentPosition);
            System.out.printf("%-15s %-16s %-9d %.2f", currentCashier.getFirstName(), currentCashier.getLastName(), currentCashier.getId(), currentCashier.getSalary());
        }
    }
    
    public void addCustomer(String firstName, String lastName, int id, String phoneNumber) {
        Customer newCustomer = new Customer(firstName, lastName, id, phoneNumber);
		boolean isInDatabase = false; 
		for (Customer customer : customers) {
			if (customer.getFirstName().equals(firstName) && customer.getLastName().equals(lastName)) {
				isInDatabase = true;
				break;
			}
		}
        if (isInDatabase) //Customer is already in the database
            System.out.print("Unable to add Customer " + firstName + " " + lastName + ", already in system." );
        else {
            customers.add(newCustomer);
            System.out.print("Successfully added Customer " + firstName + " " + lastName);
		}
    }

    public void removeCustomer(int id) {
        boolean removedCustomer = false;
        for (int currentPosition = 0; currentPosition < customers.size(); currentPosition++) {
            if (customers.get(currentPosition).getCustomerId() == id) {
                customers.remove(currentPosition);
                removedCustomer = true;
                break;
			}
        }
        if (removedCustomer) //Print statments to confirm if removeCustomer was successful
            System.out.println("Succesfully removed Customer with id " + id);
        else
            System.out.println("Unable to remove Customer with id " + id);
    }

    public Customer searchCustomerByName(String first, String last) {
		Customer currentCustomer; 
        for (int currentPosition = 0; currentPosition < customers.size(); currentPosition++) {
            currentCustomer = customers.get(currentPosition);
            if ( (currentCustomer.getFirstName().equals(first)) && (currentCustomer.getLastName().equals(last)) )
                return currentCustomer;
        }
        System.out.println("Unable to find Customer " + first + " " + last); //If cashier is not found, null is returned instead
        return null;
    }

    public void displayCustomers() {
        System.out.println();
        System.out.println("Customers:");
        System.out.println("First Name      Last Name        ID        Phone Number");
        for (int currentPosition = 0; currentPosition < customers.size(); currentPosition++) {
            Customer currentCustomer = customers.get(currentPosition);
            System.out.printf("%-15s %-16s %-9d %s", currentCustomer.getFirstName(), currentCustomer.getLastName(), currentCustomer.getCustomerId(), currentCustomer.getPhoneNumber());
			System.out.println();
        }
    }
    
    public void addSale(int dayOfSale, int monthOfSale, int yearOfSale, Cashier managingSale, Customer makingSale) {
		makingSale.incrementPurchase();
		System.out.print("enter id number for new sale: ");
		int idNumber = scan.nextInt();
		Sale newSale = new Sale (idNumber, dayOfSale, monthOfSale, yearOfSale, managingSale, makingSale);
        String input = "";
        System.out.print("enter product name w/ number sold in the form name:number (or q to quit): ");
        input = scan.nextLine();
        String curProduct = "oh no";
        String curNumber = "nonono";
        boolean success = false;
        while (!(input.toLowerCase().equals("q"))) {
            curProduct = input.split(":")[0].strip();
            curNumber = input.split(":")[1].strip();
            success = this.sellItem(curProduct, Integer.parseInt(curNumber)); //subtracts item from inventory
            if (success) {
               newSale.addItem(inventory.get(curProduct));
            }
            else {
               System.out.println("product addition failed: not enough in stock");
            }
            System.out.print("enter product name w/ number sold in the form name:number (or q to quit): ");
            input = scan.nextLine();
        }
        sales.add(0,newSale);
    }
    public void addSale(int dayOfSale, int monthOfSale, int yearOfSale, Cashier managingSale) {
        System.out.print("enter id number for new sale: ");
		int idNumber = scan.nextInt();
		Sale newSale = new Sale (idNumber, dayOfSale, monthOfSale, yearOfSale, managingSale);
        String input = "";
        System.out.print("enter product name w/ number sold in the form name:number (or q to quit): ");
        input = scan.nextLine();
        String curProduct = "oh no";
        String curNumber = "nonono";
        boolean success = false;
        while (!(input.toLowerCase().equals("q"))) {
            curProduct = input.split(":")[0].strip();
            curNumber = input.split(":")[1].strip();
            success = this.sellItem(curProduct, Integer.parseInt(curNumber)); //subtracts item from inventory
            if (success) {
               newSale.addItem(inventory.get(curProduct));
            }
            else {
               System.out.println("product addition failed: not enough in stock");
            }
            System.out.print("enter product name w/ number sold in the form name:number (or q to quit): ");
            input = scan.nextLine();
        }
        sales.add(0,newSale);
    }
    //helper for the addSale function, responsible for decrementing products sold from inventory
	//this method needs to update the inventoryStockOrder arrayList because it decrements stock of an item
    private boolean sellItem(String productName, int numSold) {
        int productInventory = inventory.get(productName).getCurrentStock();
        if (productInventory - numSold < 0)
            return false;
        else {
            Product update = inventory.get(productName);
            update.setCurrentStock(productInventory - numSold);
            inventory.put(productName, update);
			inventoryStockOrder.remove(productName); //removing old placement in stock order
			boolean placed = false;
			for (int i = 0; i < inventoryStockOrder.size(); i++) {
				if (inventory.get(inventoryStockOrder.get(i)).getStockPercentage() >= update.getStockPercentage()) { //place at index i
					inventoryStockOrder.add(i, productName);
					placed = true;
				}
			}
			if (!placed)
				inventoryStockOrder.add(productName); //if the product is the largest value
            return true;
        }
    }
    public void removeSale(int id) {
		boolean isRemoved = false;
        for (int i = 0; i < sales.size(); i++) {
			if (sales.get(i).getId() == id) {
				sales.remove(i);
				isRemoved = true;
				break;
			}
		}
		if (isRemoved)
			System.out.println("Sale with id number " + id + " succesfully removed");
		else
			System.out.println("Failed to remove sale with id number " + id);
    }
    public void displaySales() {
        throw new UnsupportedOperationException("not implemented yet");
    }
    
    public int getCurrentMonth() {
        return currentMonth; 
    }
    public int getCurrentDay() {
        return currentDay;
    }
    public int getCurrentYear() {
        return currentYear; 
    }
    public String getLatestLowStockReport() {
        return latestLowStockReport;
    }
    
    public void setCurrentMonth(int currentMonth) {
        this.currentMonth = currentMonth; 
    }
    public void setCurrentDay(int currentDay) {
        this.currentDay = currentDay;
    }
    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }
    
    //run this method on manager object before terminating program
    public void terminateScanner() {
       scan.close();
    }
    
    public static void main (String[] args) {
    ArrayList<Shipment> shipments =  new ArrayList<Shipment>();
    ArrayList<Product> products =  new ArrayList<Product>();
        Scanner scanner = new Scanner(System.in);
              Manager manager = new Manager(10, 20, 1930);
      
              while (true) {
                  try {
                      System.out.println("\n Management Menu ");
                      System.out.println("1. Shipment");
                      System.out.println("2. Customer");
                      System.out.println("3. Cashier");
                      System.out.println("4. Product");
                      System.out.println("5. Sale");
                      System.out.println("6. Exit");
                      System.out.print("choose an option: ");
      
                      int choice = scanner.nextInt();
                      scanner.nextLine(); // Consume newline
      
                      switch (choice) {
                          case 1: // Shipment Menu
                              while (true) {
                                  System.out.println("\n shipment menu ");
                                  System.out.println("1. view upcoming shipments");
                                  System.out.println("2. receive shipment");
                                  System.out.println("3. add shipment");
                                  System.out.println("4. remove shipment");
                                  System.out.println("5. back to main menu");
                                  System.out.print("choose an option: ");
      
                                  int shipmentChoice = scanner.nextInt();
                                  scanner.nextLine();
      
                                  if (shipmentChoice == 1) {
                                      manager.getUpcomingShipments();
                                  } else if (shipmentChoice == 2) {
                                      System.out.print("enter supplier name: ");
                                      String supplierName = scanner.nextLine();
                                      System.out.print("enter shipment ID: ");
                                      int shipmentId = scanner.nextInt();
                                      scanner.nextLine();
                                      System.out.println(manager.receiveShipment(new Supplier(supplierName), shipmentId));
                                  } else if (shipmentChoice == 3) {
                                      break;
                                  }else if (shipmentChoice == 4) {
                                      //add shipment
                                      System.out.print("enter id: ");
                                      int id = scanner.nextInt();
                                      System.out.print("enter arrivalMonth: ");
                                      int arrivalMonth = scanner.nextInt();
                                      System.out.print("enter arrivalDay name: ");
                                      int arrivalDay = scanner.nextInt();
                                      System.out.print("enter arrivalYear name: ");
                                      int arrivalYear = scanner.nextInt();

                                      shipments.add(new Shipment(id, arrivalMonth, arrivalDay, arrivalYear));
                                  }else if (shipmentChoice == 5) {
                                      //remove shipment
                                      System.out.print("enter id: ");
                                      int id = scanner.nextInt();
                                      for(int i=0; i < shipments.size(); i++)
                                      if (id == (shipments.get(i).getIdNumber())) shipments.remove(i);
                                  }else if (shipmentChoice == 6) {
                                      break;
                                 
                                  } else {
                                      System.out.println("invalid option.");
                                  }
                              }
                              break;
      
                          case 2: // Customer Menu
                              while (true) {
                                  System.out.println("\n customer menu ");
                                  System.out.println("1. add customer");
                                  System.out.println("2. remove customer");
                                  System.out.println("3. display customers");
                                  System.out.println("4. back to main menu");
                                  System.out.print("choose an option: ");
      
                                  int customerChoice = scanner.nextInt();
                                  scanner.nextLine();
      
                                  if (customerChoice == 1) {
                                      System.out.print("enter first name: ");
                                      String first = scanner.nextLine();
                                      System.out.print("enter last name: ");
                                      String last = scanner.nextLine();
                                      System.out.print("enter rewards number: ");
                                      int rewards = scanner.nextInt();
                                      scanner.nextLine();
                                      System.out.print("enter phone number: ");
                                      String phone = scanner.nextLine();
                                      manager.addCustomer(first, last, rewards, phone);
                                  } else if (customerChoice == 2) {
                                      System.out.print("enter rewards number: ");
                                      int rewards = scanner.nextInt();
                                      scanner.nextLine();
                                      manager.removeCustomer(rewards);
                                  } else if (customerChoice == 3) {
                                      manager.displayCustomers();
                                  } else if (customerChoice == 4) {
                                      break;
                                  } else {
                                      System.out.println("invalid option.");
                                  }
                              }
                              break;
      
                          case 3: // Cashier Menu
                              while (true) {
                                  System.out.println("\n cashier menu ");
                                  System.out.println("1. add cashier");
                                  System.out.println("2. remove cashier");
                                  System.out.println("3. display cashiers");
                                  System.out.println("4. back to main menu");
                                  System.out.print("choose an option: ");
      
                                  int cashierChoice = scanner.nextInt();
                                  scanner.nextLine();
      
                                  if (cashierChoice == 1) {
                                      System.out.print("enter first name: ");
                                      String first = scanner.nextLine();
                                      System.out.print("enter last name: ");
                                      String last = scanner.nextLine();
                                      System.out.print("enter ID: ");
                                      int id = scanner.nextInt();
                                      System.out.print("enter salary: ");
                                      double salary = scanner.nextDouble();
                                      scanner.nextLine();
                                      manager.addCashier(first, last, id, salary);
                                  } else if (cashierChoice == 2) {
                                      System.out.print("enter cashier ID: ");
                                      int id = scanner.nextInt();
                                      scanner.nextLine();
                                      manager.removeCashier(id);
                                  } else if (cashierChoice == 3) {
                                      manager.displayCashiers();
                                  } else if (cashierChoice == 4) {
                                      break;
                                  } else {
                                      System.out.println("invalid option.");
                                  }
                              }
                              break;
      
                          case 4: // Product Menu
                              while (true) {
                                  System.out.println("\n product menu ");
                                  System.out.println("1. generate low stock report");
                                  System.out.println("2. search inventory");
                                  System.out.println("3. display inventory");
                                  System.out.println("4. add inventory");
                                  System.out.println("5. remove inventory");
                                  System.out.println("6. back to main menu");
                                  System.out.print("choose an option: ");
      
                                  int productChoice = scanner.nextInt();
                                  scanner.nextLine();
      
                                  if (productChoice == 1) {
                                      manager.generateLowStockReport();
                                  } else if (productChoice == 2) {
                                      System.out.print("enter product name: ");
                                      String productName = scanner.nextLine();
                                      manager.searchInventory(productName);
                                  } /*else if (productChoice == 3) {
                                      manager.displayInventory();
                                      
                                 } */else if (productChoice == 4) {
                                      //add product
                                      System.out.print("enter name: ");
                                      String name = scanner.nextLine();
                                      System.out.print("enter cost: ");
                                      double cost = scanner.nextDouble();
                                      System.out.print("enter currentStock: ");
                                      int currentStock = scanner.nextInt();
                                      System.out.print("enter maxStock: ");
                                      int maxStock = scanner.nextInt();
                                      System.out.print("enter lowPercentage: ");
                                      int lowPercentage = scanner.nextInt();

                                      products.add(new Product(name, cost, currentStock, maxStock, lowPercentage));
                                  }else if (productChoice == 5) {
                                      //remove shipment
                                      System.out.print("enter id: ");
                                      String name = scanner.nextLine();
                                      for(int i=0; i < products.size(); i++)
                                      if (name.equals(products.get(i).getName())) products.remove(i);
                                  }else {
                                      System.out.println("invalid option.");
                                  }
                              }
                              break;
      
                          case 5: // Sale Menu
                              while (true) {
                                  System.out.println("\n sale menu ");
                                  System.out.println("1. most sold item");
                                  System.out.println("2. number of sales");
                                  System.out.println("3. display sales");
                                  System.out.println("4. back to main menu");
                                  System.out.print("Choose an option: ");
      
                                  int saleChoice = scanner.nextInt();
                                  scanner.nextLine();
      
                                  if (saleChoice == 1) {
                                      System.out.print("enter timeframe day month year: ");
                                      String timeframe = scanner.nextLine();
                                      System.out.println("most S=sold item: " + manager.mostSoldItem(timeframe));
                                  } else if (saleChoice == 2) {
                                      System.out.print("enter timeframe day month year: ");
                                      String timeframe = scanner.nextLine();
                                      System.out.println("number of sales: " + manager.numSales(timeframe));
                                  } else if (saleChoice == 3) {
                                      manager.displaySales();
                                  } else if (saleChoice == 4) {
                                      break;
                                  } else {
                                      System.out.println("invalid option.");
                                  }
                              }
                              break;
      
                          case 6: // Exit
                              System.out.println("done");
                              scanner.close();
                              manager.terminateScanner();
                              return;
      
                          default:
                              System.out.println("Invalid option. Please enter a number between 1 and 6.");
                      }
                  } catch (Exception e) {
                      System.out.println("Invalid input! Please enter a valid number.");
                      scanner.nextLine(); // Clear invalid input
                  }
              }
          }}