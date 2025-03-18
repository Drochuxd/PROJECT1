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
    private HashMap<Integer, Cashier> cashiers = new HashMap<>(); //Key id
    private HashMap<String, Supplier> suppliers = new HashMap<>(); //Key supplierName
    private HashMap<String, Product> inventory = new HashMap<>();
    private ArrayList<String> inventoryPriceOrder = new ArrayList<>(); //keys for prices in ascending (least-greatest)
    private ArrayList<String> inventoryStockOrder = new ArrayList<>(); //keys for stock percentage in ascending
    private ArrayList<String> inventoryAlphabetOrder = new ArrayList<>(); //keys for alphabetical order
    private HashMap<Integer, Customer> customers = new HashMap<>(); //Key customerId
    private ArrayList<Sale> sales = new ArrayList<>();
    
    public Manager (int currentMonth, int currentDay, int currentYear) {
        this.currentMonth = currentMonth;
        this.currentDay = currentDay;
        this.currentYear = currentYear;
    }
    
    private int getMaxDay() { //helper method that returns the highest day in the month
        if (currentMonth == 2 && ((currentYear % 4 == 0) && (currentYear % 100 != 0 || currentYear % 400 == 0))) //account for leap year
            return 29;
		else if (currentMonth == 2)
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
	public String currentDateToStr() { //helper method that returns the date in form mm/dd/yyyy
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

    //adds a shipment
    public void addShipment(int id, int arrivalMonth, int arrivalDay, int arrivalYear, String supplierName) {
        if (suppliers.get(supplierName) != null) {
           suppliers.get(supplierName).addShipment(id, arrivalMonth, arrivalDay, arrivalYear);
       }else {
			   System.out.println(supplierName + " does not exist");
       }
    }
    
    //removes a shipment
    public void removeShipment(int id, String supplierName) {
        suppliers.get(supplierName).removeShipment(id);
    }
    
    //update a shipment
    public void updateShipment(int id, int arrivalMonth, int arrivalDay, int arrivalYear, String supplierName) {
        suppliers.get(supplierName).updateShipment(id, arrivalMonth, arrivalDay, arrivalYear);
    }
    
    //returns upcoming shipments within a certain timeframe
    public String getUpcomingShipments(String timeframe) {
        String result = "";
		if (timeframe.equals("day")) {
			for (Supplier supplier : suppliers.values()) {
				result += String.format("%-20s ", supplier.getSupplierName()) + ": \n";
				for (Shipment ship : supplier.getAllShipments().values()) {
					if (ship.getArrivalDay() == currentDay && ship.getArrivalMonth() == currentMonth && ship.getArrivalYear() == currentYear)
						result += "Shipment ID " + ship.getIdNumber() + " arrives at " + ship.arrivalDateToStr() + "\n";
				}
				result += "----------------------------------\n";
			}
			return result;
		}
		else if (timeframe.equals("month")) {
			for (Supplier supplier : suppliers.values()) {
				result += String.format("%-20s ", supplier.getSupplierName()) + ": \n";
				for (Shipment ship : supplier.getAllShipments().values()) {
					if (ship.getArrivalMonth() == currentMonth && ship.getArrivalYear() == currentYear)
						result += "Shipment ID " + ship.getIdNumber() + " arrives at " + ship.arrivalDateToStr() + "\n";
				}
				result += "----------------------------------\n";
			}
			return result;
		}
		else if (timeframe.equals("year")) {
			for (Supplier supplier : suppliers.values()) {
				result += String.format("%-20s ", supplier.getSupplierName()) + ": \n";
				for (Shipment ship : supplier.getAllShipments().values()) {
					if (ship.getArrivalYear() == currentYear)
						result += "Shipment ID " + ship.getIdNumber() + " arrives at " + ship.arrivalDateToStr() + "\n";
				}
				result += "----------------------------------\n";
			}
			return result;
		}
		else 
			return "invalid timeframe";
    }
    //returns all upcoming shipments
    public String getUpcomingShipments() {
        String result = "";
		for (Supplier supplier : suppliers.values()) {
			result += String.format("%-20s ", supplier.getSupplierName()) + ": \n";
			for (Shipment ship : supplier.getAllShipments().values()) {
				result += "Shipment ID " + ship.getIdNumber() + " arrives at " + ship.arrivalDateToStr() + "\n";
			}
			result += "----------------------------------";
		}
		return result; 
    }
    public String receiveShipment(String supplierName, int id) {
        Supplier supplier = suppliers.get(supplierName);
		String output = "";
		if (supplier != null) {
			Shipment ship = supplier.getShipment(id);
			if (ship != null) {
				HashMap<String, Product> items = ship.getItems(); //all products in shipment
				Product existingProduct; //the product info that is already in inventory
				for (Product item : items.values()) {
					if (inventory.get(item.getName()) != null) { //already exists in inventory
						existingProduct = inventory.get(item); 
						existingProduct.setCurrentStock(existingProduct.getCurrentStock() + item.getCurrentStock());
						inventory.put (item.getName(), existingProduct);
					}
					else { //new product not yet in inventory
						inventory.put (item.getName(), item);
					}
					output += "adding product " + item.getName() + "... new stock is now " + inventory.get(item.getName()).getCurrentStock() + "\n";
				}
				return output;
			}
			else {
				return "shipment with id " + id + " does not exist";
			}
		}
		else {
			return "supplier with name " + supplierName + " not found";
		}
    }
    //peek at an upcoming shipment from supplier with matching id, return string displaying info about it
    public String peekShipment(String supplierName, int id) {
        if (suppliers.get(supplierName) != null) {
			Shipment shipment = suppliers.get(supplierName).getShipment(id);
			if (shipment == null) {
				return "shipment id " + id + " for supplier " + supplierName + " does not exist";
			}
			else {
				return "Shipment ID: " + id + "\nArrival Date: " + shipment.arrivalDateToStr() + "\nProduct Info: \n" + shipment.getProductInfo();
			}
		}
		else {
			return supplierName + " does not exist";
		}
	}
	//peek at all shipments from a specified supplier
	public String peekShipments(String supplierName) {
        String result = ""; 
		if (suppliers.get(supplierName) != null) {
			Supplier supplier = suppliers.get(supplierName);
			if (supplier.getAllShipments().size() == 0) {
				return "supplier " + supplierName + " has no shipments";
			}
			else {
				for (Shipment shipment: supplier.getAllShipments().values()) {
					result += "Shipment ID: " + shipment.getIdNumber() + "\nArrival Date: " + shipment.arrivalDateToStr() + "\nProduct Info: \n" + shipment.getProductInfo() + "\n";
				}
				return result;
			}
		}
		else {
			return supplierName + " does not exist";
		}
	}
    public Product searchInventory(String productName) {
        return inventory.get(productName); 
    }
    //1 for alphabetical, 2 for price descending, 3 for price ascending
    //4 for percentage stock descending, 5 for percentage stock ascending
    public void displayInventory(int criteria) {
		Product curProduct;
        switch (criteria) {
			case 1: //alphabetical order
				for (int i = 0; i < inventoryAlphabetOrder.size(); i++) {
					curProduct = inventory.get(inventoryAlphabetOrder.get(i));
					System.out.printf("%-15s %.2f %d/%d", curProduct.getName(), curProduct.getCost(), curProduct.getCurrentStock(), curProduct.getMaxStock());
				}
				break;
			case 2: //price descending
				for (int i = inventoryPriceOrder.size()-1; i >= 0; i--) {
					curProduct = inventory.get(inventoryPriceOrder.get(i));
					System.out.printf("%-15s %.2f %d/%d", curProduct.getName(), curProduct.getCost(), curProduct.getCurrentStock(), curProduct.getMaxStock());
				}
				break;
			case 3: //price ascending
				for (int i = 0; i < inventoryPriceOrder.size(); i++) {
					curProduct = inventory.get(inventoryPriceOrder.get(i));
					System.out.printf("%-15s %.2f %d/%d", curProduct.getName(), curProduct.getCost(), curProduct.getCurrentStock(), curProduct.getMaxStock());
				}
				break;
			case 4: //percentage stock descending
				for (int i = inventoryStockOrder.size()-1; i >= 0; i--) {
					curProduct = inventory.get(inventoryStockOrder.get(i));
					System.out.printf("%-15s %.2f %d/%d", curProduct.getName(), curProduct.getCost(), curProduct.getCurrentStock(), curProduct.getMaxStock());
				}
				break;
			case 5: //percentage stock ascending
				for (int i = 0; i < inventoryStockOrder.size(); i++) {
					curProduct = inventory.get(inventoryStockOrder.get(i));
					System.out.printf("%-15s %.2f %d/%d", curProduct.getName(), curProduct.getCost(), curProduct.getCurrentStock(), curProduct.getMaxStock());
				}
				break;
			default:
				System.out.println("invalid criteria input to displayInventory method");
				return;
		}
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
				if (sale.getMonthOfSale() != currentMonth || sale.getYearOfSale() != currentYear) //stop counting
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
				if (sale.getDayOfSale() != currentDay || sale.getMonthOfSale() != currentMonth || sale.getYearOfSale() != currentYear) //stop counting
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
				if (sale.getMonthOfSale() != currentMonth || sale.getYearOfSale() != currentYear) //stop counting
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
				if (sale.getDayOfSale() != currentDay || sale.getMonthOfSale() != currentMonth || sale.getYearOfSale() != currentYear) //stop counting
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
		for (int loopId : cashiers.keySet()) {
			Cashier currentCashier = cashiers.get(loopId);
            if (currentCashier.getFirstName().equals(firstName) && currentCashier.getLastName().equals(lastName)) {
				isInDatabase = true;
				break;
			}
		}
        if (isInDatabase) { //Cashier is already in the database
            System.out.println("Unable to add Cashier " + firstName + " " + lastName + ", already in system.");
		}
        else {
            cashiers.put(id, newCashier);
            System.out.println("Successfully added Cashier " + firstName + " " + lastName);
		}
    }
	
    public void removeCashier(int id) {
        try {
            cashiers.remove(id);
            System.out.println("Succesfully removed Cashier with id " + id);
        }
        catch(Exception e) {
            System.out.println("Unable to remove Cashier with id " + id);
        }
    }

    public ArrayList<Cashier> searchCashierByName(String first, String last) {
        ArrayList<Cashier> cashiersFound = new ArrayList<>();
        for (int id : cashiers.keySet()) {
            if ( (cashiers.get(id).getFirstName().equals(first)) && (cashiers.get(id).getLastName().equals(last)) )
                cashiersFound.add(cashiers.get(id));
        }
        return cashiersFound;
    }

    public ArrayList<Cashier> searchCashierByName(String first) {
        ArrayList<Cashier> cashiersFound = new ArrayList<>();
        for (int id : cashiers.keySet()) {
            if (cashiers.get(id).getFirstName().equals(first))
                cashiersFound.add(cashiers.get(id));
        }
        return cashiersFound;
    }

	public Cashier searchCashierById (int id) {
		Cashier currentCashier; 
        for (int loopId : cashiers.keySet()) {
            currentCashier = cashiers.get(loopId);
            if ( currentCashier.getId() == id )
                return currentCashier;
        }
        System.out.println("Unable to find Customer with Id " + id); //Customer cannot be found, returning null
        return null;
	}
	
    public void displayCashiers() {
        System.out.println();
        System.out.println("Cashiers:");
        System.out.println("First Name      Last Name        ID        Salary");
		Cashier currentCashier;
        for (int id : cashiers.keySet()) {
            currentCashier = cashiers.get(id);
            System.out.printf("%-15s %-16s %-9d %.2f", currentCashier.getFirstName(), currentCashier.getLastName(), currentCashier.getId(), currentCashier.getSalary());
        }
    }
    
    public void addCustomer(String firstName, String lastName, int id, String phoneNumber) {
        Customer newCustomer = new Customer(firstName, lastName, id, phoneNumber);
		boolean isInDatabase = false; 
		for (int loopId : customers.keySet()) {
			Customer currentCustomer = customers.get(loopId);
            if (currentCustomer.getFirstName().equals(firstName) && currentCustomer.getLastName().equals(lastName)) {
				isInDatabase = true;
				break;
			}
		}
        if (isInDatabase) //Customer is already in the database
            System.out.print("Unable to add Customer " + firstName + " " + lastName + ", already in system." );
        else {
            customers.put(id, newCustomer);
            System.out.print("Successfully added Customer " + firstName + " " + lastName);
		}
    }

    public void removeCustomer(int id) {
        try {
            customers.remove(id);
            System.out.println("Succesfully removed Customer with id " + id);
        }
        catch(Exception e) {
            System.out.println("Unable to remove Customer with id " + id);
        }
    }

    public ArrayList<Customer> searchCustomerByName(String first, String last) {
		ArrayList<Customer> customersFound = new ArrayList<>();
        Customer currentCustomer; 
        for (int id : customers.keySet()) {
            currentCustomer = customers.get(id);
            if ( (currentCustomer.getFirstName().equals(first)) && (currentCustomer.getLastName().equals(last)) )
                customersFound.add(currentCustomer);
        }
        return customersFound;
    }
	
	public ArrayList<Customer> searchCustomerByName(String first) {
		ArrayList<Customer> customersFound = new ArrayList<>();
        Customer currentCustomer; 
        for (int id : customers.keySet()) {
            currentCustomer = customers.get(id);
            if ( (currentCustomer.getFirstName().equals(first)) )
                customersFound.add(currentCustomer);
        }
        return customersFound;
	}
	
	public Customer searchCustomerById (int id) {
        Customer currentCustomer; 
        for (int loopId : customers.keySet()) {
            currentCustomer = customers.get(loopId);
            if ( currentCustomer.getCustomerId() == id )
                return currentCustomer;
        }
        System.out.println("Unable to find Customer with Id " + id); //Customer cannot be found, returning null
        return null;
	}
	
    public void displayCustomers() {
        System.out.println();
        System.out.println("Customers:");
        System.out.println("First Name      Last Name        ID        Phone Number");
        for (int id : customers.keySet()) {
            Customer currentCustomer = customers.get(id);
            System.out.printf("%-15s %-16s %-9d %s", currentCustomer.getFirstName(), currentCustomer.getLastName(), currentCustomer.getCustomerId(), currentCustomer.getPhoneNumber());
			System.out.println();
        }
    }
    
	public void addSupplier(String name) {
		if (suppliers.get(name) == null) {
			suppliers.put(name, new Supplier(name));
		}
		else {
			System.out.print("this name already exists, would you like to overwrite? **doing so will erase all data associated with this supplier** [y/n]: ");
			String input = scan.nextLine();
			if (input.equals("y")) {
				suppliers.put(name, new Supplier(name));
			}
			else {
				System.out.println("addition of supplier was not successful"); 
				System.out.println("if you would like to modify this supplier, please use the modify option");
			}
		}
	}
	
	public void modifySupplier (String oldName, String newName) {
		if (suppliers.get(oldName) != null) {
			Supplier supplier = suppliers.get(oldName);
			suppliers.remove(oldName);
			supplier.setSupplierName(newName);
			suppliers.put(newName, supplier); 
		}
		else {
			System.out.println(oldName + " was not found");
		}
	}
	
	public void removeSupplier(String name) {
		if (suppliers.get(name) != null) {
			suppliers.remove(name);
		}
		else {
			System.out.println(name + " was not found");
		}
	}
	
	public Supplier searchSupplierByName(String name) {
		if (suppliers.get(name) != null) {
			return suppliers.get(name);
		}
		else {
			System.out.println("Supplier with name " + name + " does not exist");
         return null;
		}
	}
	
	public void displaySuppliers() {
		System.out.printf("%-20s %s", "Supplier Names", "Number of Shipments");
		for (Supplier supplier : suppliers.values()) {
			System.out.printf("-20s %d", supplier.getSupplierName(), supplier.getAllShipments().size());
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
        Scanner scanner = new Scanner(System.in);
              Manager manager = new Manager(-1, -1, -1);
              System.out.print("enter month: ");
              int month = scanner.nextInt();
              while(month>12 || month<1){
                  System.out.print("enter valid month: ");
                  month = scanner.nextInt();
                  manager.setCurrentMonth(month);
               }
              System.out.print("enter day: ");
              int day = scanner.nextInt();
              while(day>manager.getMaxDay()||day<1){
                  System.out.print("enter valid day: ");
                  day = scanner.nextInt();
                  manager.setCurrentDay(day);
              }
              System.out.print("enter year: ");
              int year = scanner.nextInt();
              manager.setCurrentYear(year);
      
              while (true) {
                  try {
                      System.out.println("\n Management Menu ");
                      System.out.println("1. Date");
                      System.out.println("2. EndDay");
                      System.out.println("3. Shipment");
                      System.out.println("4. Customer");
                      System.out.println("5. Cashier");
                      System.out.println("6. Product");
                      System.out.println("7. Sale");
                      System.out.println("8. Supplier");
                      System.out.println("9. Exit");
                      System.out.print("choose an option: ");
      
                      int choice = scanner.nextInt();
                      scanner.nextLine(); // Consume newline
      
                      switch (choice) {
                          case 1: // Date  
                          System.out.print(manager.currentDateToStr());
                              break;
                          case 2: // End Day Menu
                              System.out.println("\nEnd Day menu ");
                              System.out.println("Are you sure");
                              System.out.println("Enter y/n:");
                              String input = scanner.next();
                              if (input.equals("y")){
                                 manager.incrementDay();
                                 System.out.println("date is " + manager.currentDateToStr() + "\n");
                                 break;}
                              System.out.println("date is " + manager.currentDateToStr() + "\n");
                              break;
                          case 3: // Shipment Menu
                              while (true) {
                                  System.out.println("\n shipment menu ");
                                  System.out.println("1. view upcoming shipments");
                                  System.out.println("2. receive shipment");
                                  System.out.println("3. add shipment");
                                  System.out.println("4. remove shipment");
                                  System.out.println("5. update shipment");
                                  System.out.println("6. back to main menu");
                                  System.out.print("choose an option: ");
      
                                  int shipmentChoice = scanner.nextInt();
                                  scanner.nextLine();
      
                                  if (shipmentChoice == 1) {
                                     manager.getUpcomingShipments();
                                  } else if (shipmentChoice == 2) {
                                      System.out.print("enter supplier name: ");
                                      String supplierName = scanner.nextLine();
                                      System.out.print("enter shipment ID: ");
                                      int id = scanner.nextInt();
                                      scanner.nextLine();
                                      System.out.println(manager.receiveShipment(supplierName, id));
                                  } else if (shipmentChoice == 3) {
                                      //add shipment
                                      System.out.print("enter id: ");
                                      int id = scanner.nextInt();
                                      System.out.print("enter arrivalMonth: ");
                                      int arrivalMonth = scanner.nextInt();
                                      System.out.print("enter arrivalDay: ");
                                      int arrivalDay = scanner.nextInt();
                                      System.out.print("enter arrivalYear: ");
                                      int arrivalYear = scanner.nextInt();
                                      System.out.print("enter supplierName: ");
                                      String supplierName = scanner.nextLine();

                                      manager.addShipment(id, arrivalMonth, arrivalDay, arrivalYear, supplierName);
                                  }else if (shipmentChoice == 4) {
                                      //remove shipment
                                      System.out.print("enter id: ");
                                      int id = scanner.nextInt();
                                      System.out.print("enter supplierName: ");
                                      String supplierName = scanner.nextLine();
                                      manager.removeShipment(id, supplierName);
                                  }else if (shipmentChoice == 5) {
                                      System.out.print("enter id: ");
                                      int id = scanner.nextInt();
                                      System.out.print("enter arrivalMonth: ");
                                      int arrivalMonth = scanner.nextInt();
                                      System.out.print("enter arrivalDay: ");
                                      int arrivalDay = scanner.nextInt();
                                      System.out.print("enter arrivalYear: ");
                                      int arrivalYear = scanner.nextInt();
                                      System.out.print("enter supplierName: ");
                                      String supplierName = scanner.nextLine();
                                      manager.updateShipment(id, arrivalMonth, arrivalDay, arrivalYear, supplierName );
                                  }else if (shipmentChoice == 6) {
                                      break;
                                 
                                  } else {
                                      System.out.println("invalid option.");
                                  }
                              }
                              break;
      
                          case 4: // Customer Menu
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
      
                          case 5: // Cashier Menu
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
      
                          case 6: // Product Menu
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
                                      String productName = scanner.nextLine();
                                      System.out.print("enter cost: ");
                                      double cost = scanner.nextDouble();
                                      System.out.print("enter currentStock: ");
                                      int currentStock = scanner.nextInt();
                                      System.out.print("enter maxStock: ");
                                      int maxStock = scanner.nextInt();
                                      System.out.print("enter lowPercentage: ");
                                      int lowPercentage = scanner.nextInt();
                                      manager.addProduct(productName, cost, currentStock, maxStock, lowPercentage);
                                  }else if (productChoice == 5) {
                                      //remove shipment
                                      System.out.print("enter product name: ");
                                      String productName = scanner.nextLine();
                                      manager.removeProduct(productName);
                                  }else if (productChoice == 6){
                                      break;
                                  }else {
                                      System.out.println("invalid option.");
                                  }
                              }
                              break;
      
                          case 7: // Sale Menu
                              while (true) {
                                  System.out.println("\n sale menu ");
                                  System.out.println("1. add sold item");
                                  System.out.println("2. most sold item");
                                  System.out.println("3. number of sales");
                                  System.out.println("4. display sales");
                                  System.out.println("5. back to main menu");
                                  System.out.print("Choose an option: ");
      
                                  int saleChoice = scanner.nextInt();
                                  scanner.nextLine();
      
                                  if (saleChoice == 1) {
                                      System.out.print("enter dayOfSale: ");
                                      int dayOfSale = scanner.nextInt();
                                      System.out.print("enter monthOfSale: ");
                                      int monthOfSale = scanner.nextInt();
                                      System.out.print("enter yearOfSale: ");
                                      int yearOfSale = scanner.nextInt();
                                      System.out.print("enter cashier first: ");
                                      String cashierFirst = scanner.nextLine();
                                      System.out.print("enter cashier last: ");
                                      String cashierLast = scanner.nextLine();
                                      System.out.print("enter Customer first: ");
                                      String customerFirst = scanner.nextLine();
                                      System.out.print("enter Customer last: ");
                                      String customerLast = scanner.nextLine();
                                      Cashier managingSale = manager.searchCashierByName(cashierFirst, cashierLast).get(0);
                                      Customer makingSale = manager.searchCustomerByName(customerFirst, customerLast).get(0);
                                      manager.addSale(dayOfSale, monthOfSale, yearOfSale, managingSale, makingSale);
                                 } if (saleChoice == 2) {
                                      System.out.print("enter timeframe day month year: ");
                                      String timeframe = scanner.nextLine();
                                      System.out.println("most S=sold item: " + manager.mostSoldItem(timeframe));
                                  } else if (saleChoice == 3) {
                                      System.out.print("enter timeframe day month year: ");
                                      String timeframe = scanner.nextLine();
                                      System.out.println("number of sales: " + manager.numSales(timeframe));
                                  } else if (saleChoice == 4) {
                                      manager.displaySales();
                                  } else if (saleChoice == 5) {
                                      break;
                                  } else {
                                      System.out.println("invalid option.");
                                  }
                              }
                              break;
                          case 8: // Supplier Menu
                              while (true) {
                                  System.out.println("\n supplier menu ");
                                  System.out.println("1. add supplier");
                                  System.out.println("2. remove supplier");
                                  System.out.println("3. modify supplier");
                                  System.out.println("4. search supplier");
                                  System.out.println("5. display Suppliers");
                                  System.out.println("6. back to main menu");
                                  System.out.print("Choose an option: ");
      
                                  int saleChoice = scanner.nextInt();
                                  scanner.nextLine();
      
                                  if (saleChoice == 1) {
                                      System.out.print("enter supplier name: ");
                                      String name = scanner.nextLine();
                                      manager.addSupplier(name);
                                  } else if (saleChoice == 2) {
                                      System.out.print("enter supplier name: ");
                                      String name = scanner.nextLine();
                                      manager.removeSupplier(name);
                                  } else if (saleChoice == 3) {
                                      System.out.print("enter supplier old name: ");
                                      String oldName = scanner.nextLine();
                                      System.out.print("enter supplier new name: ");
                                      String newName = scanner.nextLine();
                                      manager.modifySupplier(oldName, newName);
                                  }else if (saleChoice == 4) {
                                      System.out.print("enter supplier name: ");
                                      String name = scanner.nextLine();
                                      manager.searchSupplierByName(name);
                                  } else if (saleChoice == 5) {
                                      manager.displaySuppliers();
                                  }else if (saleChoice == 6) {
                                      break;
                                  } else {
                                      System.out.println("invalid option.");
                                  }
                              }
                              break;
      
                          case 9: // Exit
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
          }
}