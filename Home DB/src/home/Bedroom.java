package home;

/* Class to management bedroom table
 * 
 */
public class Bedroom {

	//Declare all variables as private
	private String name;
	private String store;
	private float price;
	private int quantity;
	private String color;
	
	//Constructor to initialize variables
	public Bedroom(String name, String store, float price, int quantity, String color)
	{
		this.name = name;
		this.store = store;
		this.price = price;
		this.quantity = quantity;
		this.color = color;
	}
	
	//Define getters to all variables
	public String getName() { return name; }
	public String getStore() { return store; }
	public float getPrice() { return price; }
	public int getQuantity() { return quantity; }
	public String getColor() { return color; }
	
	public String apagar() { return "Hello World"; }
			
	
}
