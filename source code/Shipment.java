package management;

import java.util.HashMap;
import management.Product;

public class Shipment{
    private int idNumber; 
    private int arrivalMonth;
    private int arrivalDay;
    private int arrivalYear;
    private HashMap<String, Product> items = new HashMap<>(); 
    
    public Shipment(int id, int arrivalMonth, int arrivalDay, int arrivalYear) { //shipment items are empty at creation
        this.idNumber = id;
        this.arrivalMonth = arrivalMonth;
        this.arrivalDay = arrivalDay;
        this.arrivalYear = arrivalYear;
    }
    
    //converts the date of arrival into dd/mm/yyyy format
    public String arrivalDateToStr() {
        if (arrivalMonth < 10) { 
            if (arrivalDay < 10) 
                return "0" + arrivalMonth + "/0" + arrivalDay + "/" + arrivalYear;
            else //just the month needs zero
                return "0" + arrivalMonth + "/" + arrivalDay + "/" + arrivalYear;
        }
        else if (arrivalDay < 10)
            return arrivalMonth + "/0" + arrivalDay + "/" + arrivalYear;
        else
            return arrivalMonth + "/" + arrivalDay + "/" + arrivalYear;
    }
    //adds product to shipment list, modifies the key if it already exists
    //If the product already exists in inventory do not need to ask user about cost, maxStock, or lowPercentage
    //The currentStock attribute is used to determine how much of the product is in the shipment
    public void addProduct(String name, double cost, int currentStock, int maxStock, int lowPercentage) {
        Product newProduct = new Product(name, cost, currentStock, maxStock, lowPercentage);
        items.put(newProduct.getName(), newProduct);
    }
    //removes item of matching name from list
    public void removeProduct(String productName) {
        if (items.get(productName) != null)
            items.remove(productName);
    }
    public Product searchProduct(String productName) {
      return items.get(productName);
    }
    public int getProductCount(String productName) {
      return items.get(productName).getCurrentStock();
    }
    
    public int getIdNumber() {
        return idNumber;
    }
    public int getArrivalMonth() {
        return arrivalMonth;
    }
    public int getArrivalDay() {
        return arrivalDay;
    }
    public int getArrivalYear() {
        return arrivalYear;
    }
    //returns a list of all products
    public HashMap<String, Product> getItems() {
        return items;
    }
    
    public void setArrivalMonth(int arrivalMonth) {
    	if (arrivalMonth <= 12 && arrivalMonth > 0)
    	    this.arrivalMonth = arrivalMonth;
    	else {
    	    System.out.println("Invalid month");
    	    return; } 
    	//if arrivalDay is not valid in the new month, roll over into the next month
    	if (arrivalDay > this.getMaxDay()) {
    	    int difference = arrivalDay - this.getMaxDay(); //how many days into the new month we need to go
    	    if (arrivalMonth < 12) {
    	        this.arrivalMonth++; 
    	        arrivalDay = difference;
    	    }
    	    else { //when the month is december and needs to roll over to january
    	        this.arrivalMonth = 1;
    	        arrivalDay = difference;
    	    }
    	}
    }
    public void setArrivalDay(int arrivalDay) {
        if (arrivalDay > 0 && arrivalDay <= this.getMaxDay())
            this.arrivalDay = arrivalDay;
        else
            System.out.println("Invalid day");
    }
    private int getMaxDay() { //helper method that returns the highest day in the month
        if (arrivalMonth == 2)
            return 28;
        else if (arrivalMonth == 4 || arrivalMonth == 6 || arrivalMonth == 9 || arrivalMonth == 11)
            return 30;
        else
            return 31;
    }
    public void setArrivalYear (int arrivalYear) {
        this.arrivalYear = arrivalYear;
    }
    
    
    /*
    public static void main (String[] args) {
        Shipment a = new Shipment(2345, 1, 5, 1002);
        a.addProduct("Apple", 3.5, 20, 30, 20);
        a.addProduct("Banana", 7.5, 20, 35, 30);
        a.addProduct("Melon", 3.5, 45, 90, 40);
        
        System.out.println("Shipment products");
        for (Product item : a.getItems().values()) {
            System.out.println(item.getName() + " $" + item.getCost());
        }
        
        System.out.println("Arrival date of shipment: " + a.arrivalDateToStr());
        a.setArrivalDay(5000); //should be invalid
        a.setArrivalDay(29);
        a.setArrivalMonth(2);
        System.out.println("Arrival date of shipment: " + a.arrivalDateToStr());
        
        a.addProduct("Apple", 800.0, 20, 30, 20);
        System.out.println("Shipment products");
        for (Product item : a.getItems().values()) {
            System.out.println(item.getName() + " $" + item.getCost());
        }
        
        System.out.println("Current ID for shipment: " + a.getIdNumber()); 
    } */
}
