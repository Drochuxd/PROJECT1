//all other classes as of this commit are implemented
//if the word test is passed when this file is ran a pregenerated series of tests will run to test methods (not implemented yet)

import management.*;
import java.util.*;

public class Manager {
    private int currentMonth;
    private int currentDay;
    private int currentYear;
    private String latestLowStockReport = "report not generated yet"; //most recently generated lowStockReport
    private ArrayList<Cashier> cashiers = new ArrayList<>();
    private ArrayList<Supplier> suppliers = new ArrayList<>(); 
    private ArrayList<Product> inventory = new ArrayList<>();
    private ArrayList<Integer> inventoryPriceOrder = new ArrayList<>();
    private ArrayList<Integer> inventoryStockOrder = new ArrayList<>();
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
            latestLowStockReport = "No low products; 
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
    public Product searchInventory(String productName) {
        for (String nameOfProduct, Product product : inventory) {
            if productName.equals(nameOfProduct) {
                return product;
            }
        }
        return null; //Product wasn't found
    }
    public void displayInventory(int criteria, boolean toFile) {
        throw new UnsupportedOperationException("not implemented yet");
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
    public void addProduct(String productName, double cost, int currentStock, int maxStock, int lowPercentage) {
        throw new UnsupportedOperationException("not implemented yet");
    }
    public void removeProduct(String productName) {
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
        throw new UnsupportedOperationException("not implemented yet");
    }
    public void addSale(int dayOfSale, int monthOfSale, int yearOfSale, Cashier managingSale) {
        throw new UnsupportedOperationException("not implemented yet");
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
    
    public static void main (String[] args) {
    	if (args[0] != null && args[0].toLowerCase().equals("test")) {
            throw new UnsupportedOperationException("pregenerated tests not implemented");
        }
        else {
            throw new UnsupportedOperationException("user-side menu not implemented");
    }
}
