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
    private ArrayList<String> inventoryStockOrder = new ArrayList<>(); //keys for stock in ascending
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
        throw new UnsupportedOperationException("not implemented yet");
    }
    public int numSold(Product product, String timeframe) {
        throw new UnsupportedOperationException("not implemented yet");
    }
    public int numSales(String timeframe) {
        throw new UnsupportedOperationException("not implemented yet");
    }
    public double averageSalePrice(String timeframe) {
        throw new UnsupportedOperationException("not implemented yet");
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
        Sale newSale = new Sale (dayOfSale, monthOfSale, yearOfSale, managingSale, makingSale);
		makingSale.incrementPurchase();
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
        Sale newSale = new Sale (dayOfSale, monthOfSale, yearOfSale, managingSale);
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
    	if (args.length != 0 && args[0].toLowerCase().equals("test")) {
            throw new UnsupportedOperationException("pregenerated tests not implemented");
        }
      else {
            throw new UnsupportedOperationException("user-side menu not implemented"); 
        }
    }
}
