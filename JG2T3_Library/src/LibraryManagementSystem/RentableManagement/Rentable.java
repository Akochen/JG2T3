package LibraryManagementSystem.RentableManagement;

public class Rentable {
	
	private String sku;
	private String title;
	private String isbn;
	private String condition;
	private String genre;
	private String type;
	private String upc;
	
	
	/**
	 * Constructor for creating a DVD
	 * @param sku The sku of the Rentable
	 * @param title The title of the Rentable
	 * @param condition the condition/damage of the Rentable
	 * @param genre the genre of the Rentable
	 */
	public Rentable(String sku, String upc, String title, String condition, String genre){
		this.sku = sku;
		this.upc = upc;
		this.title = title;
		this.condition = condition;
		this.genre = genre;
		type = "DVD";
	}
	
	/**
	 * Constructor for creating a book, or eBook
	 * @param sku The sku of the Rentable
	 * @param title The title of the Rentable
	 * @param isbn The ISBN number of the Rentable
	 * @param condition the condition/damage of the Rentable
	 * @param genre the genre of the Rentable
	 * @param type whether the Rentable is an eBook, or book
	 */
	public Rentable(String sku, String upc, String title, String isbn, String condition, String genre, String type) {
		this.sku = sku;
		this.upc = upc;
		this.title = title;
		this.isbn = isbn;
		this.condition = condition;
		this.genre = genre;
		this.type = type;
	}

	/**
	 * @return the condition
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * @param condition the condition to set
	 * @return True if condition successfully changed
	 */
	public boolean setCondition(String condition) {
		this.condition = condition;
		return true;
	}

	/**
	 * @return the sku
	 */
	public String getSku() {
		return sku;
	}
	
	/**
	 * @return the upc
	 */
	public String getUpc() {
		return upc;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the isbn
	 */
	public String getIsbn() {
		return isbn;
	}

	/**
	 * @return the genre
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	
	
	
}
