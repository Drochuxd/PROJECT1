package management; 

public class Product {
   private String name;
   private double cost;
   private int currentStock;
   private int maxStock;
   private int lowPercentage;
   
   public Product(String name, double cost, int currentStock, int maxStock) {
      this.name = name;
      this.cost = cost; 
      if (currentStock > maxStock) { //exceeding max capacity
         this.currentStock = maxStock;
         this.maxStock = maxStock;
      }
      else {
         this.currentStock = currentStock;
         this.maxStock = maxStock;
      }
      lowPercentage = 20;
   }
   public Product(String name, double cost, int currentStock, int maxStock, int lowPercentage) {
      this.name = name;
      this.cost = cost; 
      if (currentStock > maxStock) { //exceeding max capacity
         this.currentStock = maxStock;
         this.maxStock = maxStock;
      }
      else {
         this.currentStock = currentStock;
         this.maxStock = maxStock;
      }
      this.lowPercentage = lowPercentage;
   }
   
   public double getStockPercentage() { //returns the current stock level as a whole percentage
      return (int)(((double)currentStock / (double)maxStock) * 10000.0) / 100.0;
   }
   public double increasePrice(double percentage) { //increases price based on percentage passed (ex. 0.5 = 50%)
      cost = cost + (cost*percentage);
      return cost;
   }
   public double decreasePrice(double percentage) { //decreases price based on percentage passed (ex. 0.5 = 50%)
      cost = cost - (cost*percentage);
      return cost;
   }
   
   public String getName() {
      return name;
   }
   public double getCost() {
      return cost;
   }
   public int getCurrentStock() {
      return currentStock;
   }
   public int getMaxStock() {
      return maxStock;
   }
   public int getLowPercentage() {
      return lowPercentage;
   }
   
   public void setName(String newName) {
      name = newName;
   }
   public void setCost(double newCost) {
      cost = newCost;
   }
   public void setCurrentStock(int newCurrentStock) {
      currentStock = newCurrentStock;
   }
   public void setMaxStock(int newMaxStock) {
      maxStock = newMaxStock;
   }
   public void setLowPercentage(int newLowPercentage) {
      lowPercentage = newLowPercentage;
   }
   /*
   public static void main (String[] args) {
      //testing all parameter constructor
      Product apple = new Product ("Apple", 3.5, 20, 30, 30);
      //testing paramter w/ lowPercentage not passed and currentStock > maxStock
      Product banana = new Product ("Banana", 4.5, 50, 20);
      System.out.println("Current stock of bananas are: " + banana.getCurrentStock());
      System.out.println("Max stock of bananas are: " + banana.getMaxStock()); 
      System.out.println("Stock level of bananas is: " + banana.getStockPercentage()); 
      System.out.println("Current stock of apples are: " + apple.getCurrentStock());
      System.out.println("Max stock of apples are: " + apple.getMaxStock());
      System.out.println("Stock level of apples is: " + apple.getStockPercentage());
      
      System.out.println("Current price of apples is: " + apple.getCost());
      System.out.println("Increasing price of apples by 35% to " + apple.increasePrice(0.35));
      System.out.println("Decreasing price of apples by 35%: to " + apple.decreasePrice(0.35));  
  }
  */
}
