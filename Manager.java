//supplier is not implemented yet, compilation will fail
//all other classes as of this commit are implemented
//if the word test is passed when this file is ran a pregenerated series of tests will run to test methods (not implemented yet)

import management.*;
import java.util.*;

public class Manager {
    private int currentMonth;
    private int currentDay;
    private int currentYear;
    private String latestLowStockReport; //most recently generated lowStockReport
    private ArrayList<Cashier> cashiers = new ArrayList<>();
    private ArrayList<Supplier> suppliers = new ArrayList<>(); 
    private HashMap<String, Product> inventory = new HashMap<>();
    private ArrayList<Integer> inventoryPrice = new ArrayList<>();
    private ArrayList<Integer> inventoryStock = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<Sale> sales = new ArrayList<>();
    
    public Manager (int currentMonth, int currentDay, int currentYear) {
        this.currentMonth = currentMonth;
        this.currentDay = currentDay;
        this.currentYear = currentYear;
    }
    
    public void incrementDay() {
        throw new UnsupportedOperationException("not implemented yet");
    }
    public String generateLowStockReport() {
        throw new UnsupportedOperationException("not implemented yet");
    }
    public String[][] getUpcomingShipments(String timeframe) {
        throw new UnsupportedOperationException("not implemented yet");
    }
    public String[][] getUpcomingShipments() {
        throw new UnsupportedOperationException("not implemented yet");
    }
    public String receiveShipment(Supplier supplier, int id) {
        throw new UnsupportedOperationException("not implemented yet");
    }
    public Product searchInventory(String productName) {
        throw new UnsupportedOperationException("not implemented yet");
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
