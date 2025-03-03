package management;

public class Customer {
    private String firstName;
    private String lastName;
    private int customerId;
    private String phoneNumber;
    private int purchasesMade;
    
    public Customer (String firstName, String lastName, int customerId, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.customerId = customerId;
        this.phoneNumber = phoneNumber;
        purchasesMade = 0;
    }
    
    public void incrementPurchase() {
        purchasesMade++;
    }
    
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public int getCustomerId() {
        return customerId;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public int getPurchasesMade() {
        return purchasesMade;
    }
    
    public void setFirstName(String first) {
        firstName = first;
    }
    public void setLastName(String last) {
        lastName = last;
    }
    public void setCustomerId(int id) {
        customerId = id;
    }
    public void setPhoneNumber(String newPhoneNumber) {
        phoneNumber = newPhoneNumber;
    }
    public void setPurchasesMade(int purchasesMade) {
        this.purchasesMade = purchasesMade;
    }
    /*
    public static void main (String[] args) {
        Customer a = new Customer ("bob", "smith", 2828, "203-189-8888");
        System.out.println("customer's name: " + a.getFirstName() + " " + a.getLastName());
        System.out.println("Customer id and phone number: " + a.getCustomerId() + "    " + a.getPhoneNumber());
        System.out.println("Customer purchases made: " + a.getPurchasesMade());
        a.incrementPurchase(); a.incrementPurchase();
        System.out.println("Customer purchases made: " + a.getPurchasesMade());
    } */
}
