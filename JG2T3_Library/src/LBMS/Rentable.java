package LBMS;

public class Rentable {
	
	private int sku;
	private String title;
	private String isbn;
	private String condition;
	private String genre;
	private String type;
	private String roomNumber;
	
	
	/**
	 * Constructor for creating a DVD
	 * @param sku The sku of the Rentable
	 * @param title The title of the Rentable
	 * @param condition the condition/damage of the Rentable
	 * @param genre the genre of the Rentable
	 */
	public Rentable(int sku, String title, String condition, String genre){
		
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
	public Rentable(int sku, String title, String isbn, String condition, String genre, String type) {
		
	}
	
	public Rentable(String roomNumber) {
		
	}

	/**
	 * @return the condition
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * @param condition the condition to set
	 */
	public boolean setCondition(String condition) {
		this.condition = condition;
		return true;
	}

	/**
	 * @return the sku
	 */
	public int getSku() {
		return sku;
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

	/**
	 * @return the roomNumber
	 */
	public String getRoomNumber() {
		return roomNumber;
	}
	
	
}