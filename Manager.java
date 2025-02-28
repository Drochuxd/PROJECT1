import Cashier.*; 
import Supplier.*; //supplier doesn't exist yet
import Shipment.*; 
import Customer.*;
import Product.*; 
import Sale.*;
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
        throw UnsupportedOperationException("not implemented yet");
    }
    public generateLowStockReport() {
        throw UnsupportedOperationException("not implemented yet");
    }
    public String[][] getUpcomingShipments(String timeframe) {
        throw UnsupportedOperationException("not implemented yet");
    }
    public String[][] getUpcomingShipments() {
        throw UnsupportedOperationException("not implemented yet");
    }
    public String receiveShipment(Supplier supplier, int id) {
        throw UnsupportedOperationException("not implemented yet");
    }
    public Product searchInventory(String productName) {
        throw UnsupportedOperationException("not implemented yet");
    }
    public void displayInventory(int criteria, boolean toFile) {
        throw UnsupportedOperationException("not implemented yet");
    }
    public String mostSoldItem(String timeframe) {
        throw UnsupportedOperationException("not implemented yet");
    }
    public int numSold(Product product, String timeframe) {
        throw UnsupportedOperationException("not implemented yet");
    }
    public int numSales(String timeframe) {
        throw UnsupportedOperationException("not implemented yet");
    }
    public double averageSalePrice(String timeframe) {
        throw UnsupportedOperationException("not implemented yet");
    }
    public void addProduct(String productName, double cost, int currentStock, int maxStock, int lowPercentage) {
        throw UnsupportedOperationException("not implemented yet");
    }
    public void removeProduct(String productName) {
        throw UnsupportedOperationException("not implemented yet");
    }
    public void addCashier(String firstName, String lastName, int id, double salary) {
        throw UnsupportedOperationException("not implemented yet");
    }
    public void removeCashier(int id) {
        throw UnsupportedOperationException("not implemented yet");
    }
    public Cashier searchCashierByName(String first, String last) {
        throw UnsupportedOperationException("not implemented yet");
    }
    public ArrayList<Cashier> searchCashierByName(String first) {
        throw UnsupportedOperationException("not implemented yet");
    }
    public void displayCashiers() {
        throw UnsupportedOperationException("not implemented yet");
    }
    public void addCustomer(String firstName, String lastName, int id, String phoneNumber) {
        throw UnsupportedOperationException("not implemented yet");
    }
    public void removeCustomer(int id) {
        throw UnsupportedOperationException("not implemented yet");
    }
    public Customer searchCustomerByName(String first, String last) {
        throw UnsupportedOperationException("not implemented yet");
    }
    public void displayCustomers() {
        throw UnsupportedOperationException("not implemented yet");
    }
    public void addSale(int dayOfSale, int monthOfSale, int yearOfSale, Cashier managingSale, Customer makingSale) {
        throw UnsupportedOperationException("not implemented yet");
    }
    public void addSale(int dayOfSale, int monthOfSale, int yearOfSale, Cashier managingSale) {
        throw UnsupportedOperationException("not implemented yet");
    }
    public void removeSale(int index) {
        throw UnsupportedOperationException("not implemented yet");
    }
    public void displaySales() {
        throw UnsupportedOperationException("not implemented yet");
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
        //menu work in here
    }
}
