package management;

import management.Cashier;
import management.Customer;
import management.Product;
import java.util.*;

public class Sale {
    private int dayOfSale;
    private int monthOfSale;
    private int yearOfSale;
    private Cashier managingSale;
    private HashMap<Product, Integer> productsSold = new HashMap<>();
    private Customer makingSale;
    
    public Sale(int dayOfSale, int monthOfSale, int yearOfSale, Cashier managingSale) {
        this.managingSale = managingSale;
        this.dayOfSale = dayOfSale;
        this.monthOfSale = monthOfSale;
        this.yearOfSale = yearOfSale;
    }
    public Sale(int dayOfSale, int monthOfSale, int yearOfSale, Cashier managingSale, Customer makingSale) {
        this.managingSale = managingSale;
        this.makingSale = makingSale;
        this.dayOfSale = dayOfSale;
        this.monthOfSale = monthOfSale;
        this.yearOfSale = yearOfSale;
        makingSale.incrementPurchase();
    }
    
    public int numberOfProductsSold() {
        int sum = 0;
        for (int num : productsSold.values()) {
            sum += num;
        }
        return sum;
    }
    public double totalOfSale() {
        double total = 0;
        for (Product item : productsSold.keySet()) {
            total += (item.getCost() * productsSold.get(item));
        }
        return total;
    }
    public void addItem(Product item) {
        if (productsSold.containsKey(item))
            productsSold.put(item, productsSold.get(item)+1);
        else
            productsSold.put(item, 1);
    }
    //decrements the amount of that product sold in map by numItems
    //completely removes the item if the amount falls below 0
    public void removeItem(String productName, int numItems) {
        //does the item exist
        Product found = null; //this will be the key if it exists
        for (Product item : productsSold.keySet()) {
            if (item.getName().equals(productName)) {
                found = item;
                break;
            }
        }
        if (found != null) { //product was found
            productsSold.put(found, productsSold.get(found)-numItems);
            if (productsSold.get(found) < 1)
                productsSold.remove(found);
        }
        else
            System.out.println("Product is not in receipt");
    }
    //returns a string containing all information about the sale
    public String saleInfoToStr() {
        String output = "";
        output += "Cashier: " + managingSale.getFirstName() + " " + managingSale.getLastName() + "\n";
        if (makingSale != null)
            output += "Customer: " + makingSale.getFirstName() + " " + makingSale.getLastName() + "\n";
        output += "Date of Sale: " + this.dateOfSaleToStr() + "\n";
        int i = 1;
        for (Product item : productsSold.keySet()) {
            output += i + ":  " + item.getName() + "  $" + item.getCost() + "   item count: " + productsSold.get(item) + "\n";
            i++; 
        }
        output += "Total: $" + this.totalOfSale();
        return output;
    }
    //helper method to convert date of sale into string, can make public if needed for menu dev.
    private String dateOfSaleToStr() {
        if (monthOfSale < 10) { 
            if (dayOfSale < 10) 
                return "0" + monthOfSale + "/0" + dayOfSale + "/" + yearOfSale;
            else //just the month needs zero
                return "0" + monthOfSale + "/" + dayOfSale + "/" + yearOfSale;
        }
        else if (dayOfSale < 10)
            return monthOfSale + "/0" + dayOfSale + "/" + yearOfSale;
        else
            return monthOfSale + "/" + dayOfSale + "/" + yearOfSale;
    }
    
    public int getDayOfSale() {
        return dayOfSale;
    }
    public int getMonthOfSale() {
        return monthOfSale;    
    }
    public int getYearOfSale() {
        return yearOfSale;
    }
    public Cashier getManagingSale() {
        return managingSale;
    }
    public HashMap<Product, Integer> getProductsSold() {
        return productsSold;
    }
    public Customer getMakingSale() {
        return makingSale;
    }
    
    public void setDayOfSale(int dayOfSale) {
        this.dayOfSale = dayOfSale;
    }
    public void setMonthOfSale(int monthOfSale) {
        this.monthOfSale = monthOfSale;    
    }
    public void setYearOfSale(int yearOfSale) {
        this.yearOfSale = yearOfSale;
    }
    public void setManagingSale(Cashier managingSale) {
        this.managingSale = managingSale;
    }
    public void setMakingSale(Customer makingSale) {
        this.makingSale = makingSale;
    }
    /*
    public static void main (String[] args) {
        Customer testee = new Customer("first", "last", 80, "82-30-89");
        Cashier tester = new Cashier (80, "First", "Last", 800.55);
        System.out.println("Testee Purchases: " + testee.getPurchasesMade());
        Sale a = new Sale(2, 5, 2025, tester, testee);
        Product banana = new Product ("banana", 3.5, 10, 20);
        Product apple = new Product ("apple", 4.5, 20, 40);
        Product zeeple = new Product ("zeeple", 7.5, 1, 10); 
        Product zeeple3 = new Product ("ZeepleNumber3", 8.11, 50, 30);
        a.addItem(banana);
        a.addItem(banana);
        a.addItem(banana);
        a.addItem(apple);
        a.addItem(zeeple);
        System.out.println("Testee Purchases: " + testee.getPurchasesMade());
        System.out.println(a.saleInfoToStr());
        a.removeItem("zeeple", 3);
        a.removeItem("banana", 1);
        System.out.println(a.saleInfoToStr());
        a.addItem(zeeple3);
        System.out.println(a.saleInfoToStr());
    } */
}
