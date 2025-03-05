package management;
import management.Shipment;
import java.util.*;
/*String name //name of supplier 
String[] products //names of products delivered by the supplier 
HashMap<id: Integer, shipment: Shipment> upcomingShipments 
---------------------------------------------------------------------------------------------------------------- 
Supplier (name, products) //constructor 
String[] getLowProducts (masterReport: String) //parses the total low stock report from the generateLowStockReport() method in manager class, returns an array of all product names that are supplied by this supplier (defined by String[] products attribute) 
void addShipment(id: int, arrivalMonth: int, arrivalDay: int, arrivalYear: int) //creates a new shipment and adds it to upcomingShipments 
void updateShipment(id: int, arrivalMonth: int, arrivalDay: int, arrivalYear: int) //updates shipment with corresponding id number 
void removeShipment(id: int) //removes a shipment by its id, does nothing if it doesn’t exist 

*/

// CSCI 280
// Johnny Caulder
// Assignment 5
// 02/14

public class Supplier {
   private String supplierName;
   private HashMap<String, Product> products = new HashMap<>();
   private HashMap<Integer, Shipment> upcomingShipments = new HashMap<>();
   
   // Getter
  public String getSupplierName() {
    return this.supplierName;
  }

  // Setter
  public void setSupplierName(String supplierName) {
    this.supplierName = supplierName;
  }
    
  public ArrayList<String> getLowProducts (String masterReport){
      String[] lowProducts = masterReport.split(",");
      ArrayList<String> matchingNames = new ArrayList<>(); 
      for(int i = 0; i < lowProducts.length;i++){
         for (String productName : products.keySet()){
            if (productName.equals(lowProducts[i])){
               matchingNames.add(lowProducts[i]);
            }
         } 
      } 
      return matchingNames; 
  }
  public void addShipment(int id, int arrivalMonth, int arrivalDay, int arrivalYear){ //creates a new shipment and adds it to upcomingShipments
      Shipment shipment = new Shipment(id, arrivalMonth, arrivalDay, arrivalYear);
      upcomingShipments.put(id,shipment);
        
  }
  public void updateShipment(int id, int arrivalMonth, int arrivalDay, int arrivalYear){ //updates shipment with corresponding id number 
            Shipment shipment = upcomingShipments.get(id);
            shipment.setArrivalDay(arrivalDay);
            shipment.setArrivalMonth(arrivalMonth);
            shipment.setArrivalYear(arrivalYear);
 
  }
  public void removeShipment(int id){ //removes a shipment by its id, does nothing if it doesn’t exist 
      upcomingShipments.remove(id);
 }
   
  public void addProduct(String name, double cost, int currentStock, int maxStock, int lowPercentage) {
        Product newProduct = new Product(name, cost, currentStock, maxStock, lowPercentage);
        products.put(newProduct.getName(), newProduct);
  }
  public void addProduct(Product product) {
        products.put(product.getName(), product);
  }
  public void removeProduct(String productName){
      products.remove(productName);
  }
  public Supplier(String supplierName){
      this.supplierName = supplierName;
  }
  

   /*public static void main(String[] args) {
         // Create a supplier
      Product[] initialProducts = {new Product("Laptop", 567.98, 20, 30, 50), new Product("Mouse", 50.00, 10, 20, 50), new Product("Keyboard", 40.00, 20, 30, 40)};
      Supplier supplier = new Supplier("TechSupplier");
      for (Product product : initialProducts) {
         supplier.addProduct(product);
      } 

      // Test: Add shipments
      System.out.println("Adding shipments...");
      supplier.addShipment(101, 3, 15, 2025);
      supplier.addShipment(102, 4, 10, 2025);

      // Test: Update shipment
      System.out.println("Updating shipment 101...");
      supplier.updateShipment(101, 3, 20, 2025);

      // Test: Remove shipment
      System.out.println("Removing shipment 102...");
      supplier.removeShipment(102);

      // Test: Get low-stock products
      String masterReport = "Laptop,Monitor,Keyboard,Phone";
      ArrayList<String> lowProducts = supplier.getLowProducts(masterReport);
      System.out.println("Low stock products supplied: " + lowProducts);

      // Test: Add product
      System.out.println("Adding product 'Monitor'...");
      supplier.addProduct("Monitor", 1000.00, 10, 10 ,20);

      // Test: Remove product
      System.out.println("Removing product 'Mouse'...");
      supplier.removeProduct("Mouse");

      // Final check
      System.out.println("Final list of products: " + supplier.products.keySet());
      System.out.println("Upcoming Shipments: " + supplier.upcomingShipments.keySet());
}*/
}