package management;

public class Cashier {
   private String firstName, lastName;
   private int id;
   double salary;
   
   // Getter
  public String getLastName() {
    return this.lastName;
  }

  // setter
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
   // Getter
  public String getFirstName() {
    return this.firstName;
  }

  // Setter
  public void setFirstName(String firstName ) {
    this.firstName = firstName;
  } 
  // Getter
  public int getId() {
    return this.id;
  }

  // Setter
  public void setId(int id) {
    this.id = id;
  }
  
   // Getter
  public double getSalary() {
    return this.salary;
  }

  // Setter
  public void setSalary(double salary) {
    this.salary = salary;
  }
   
   public Cashier(int id, String firstName, String lastName, double salary){
   this.id = id;
   this.firstName = firstName;
   this.lastName = lastName;
   this.salary = salary;
   }

   public void increaseSalary (double percent){ //increases the salary of the cashier by a percentage
         salary = salary + (salary*percent)/100;
   }
   /*
   public static void main(String[] args) {
         Cashier a = new Cashier(0, "bob", "ban", 10);
         a.increaseSalary(20);
         System.out.printf("%.2f", a.getsalary());
   } */
}
