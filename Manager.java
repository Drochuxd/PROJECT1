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
    public String generateLowStockReport() {
        String lowProducts = "";
        for (Product item : inventory.values()) {
            if (item.getStockPercentage() <= item.getLowPercentage())
               lowProducts += item.getName(); 
        }
        if (lowProducts.equals("")) {
            latestLowStockReport = "No low products"; 
            return "No low products";
        }
        else {
            latestLowStockReport = lowProducts;
            return lowProducts;
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
        throw new UnsupportedOperationException("not implemented yet");
    }
    public void removeCashier(int id) {
        throw new UnsupportedOperationException("not implemented yet");
    }
    public Cashier searchCashierByName(String first, String last) {
        throw new UnsupportedOperationException("not implemented yet");
    }
    public ArrayList<Cashier> searchCashierByName(String first) {
        throw new UnsupportedOperationException("not implemented yet");
    }
    public void displayCashiers() {
        throw new UnsupportedOperationException("not implemented yet");
    }
    
    public void addCustomer(String firstName, String lastName, int id, String phoneNumber) {
        throw new UnsupportedOperationException("not implemented yet");
    }
    public void removeCustomer(int id) {
        throw new UnsupportedOperationException("not implemented yet");
    }
    public Customer searchCustomerByName(String first, String last) {
        throw new UnsupportedOperationException("not implemented yet");
    }
    public void displayCustomers() {
        throw new UnsupportedOperationException("not implemented yet");
    }
    
    public void addSale(int dayOfSale, int monthOfSale, int yearOfSale, Cashier managingSale, Customer makingSale) {
        Sale newSale = new Sale (dayOfSale, monthOfSale, yearOfSale, managingSale, makingSale);
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
    public void removeSale(int index) {
        throw new UnsupportedOperationException("not implemented yet");
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
