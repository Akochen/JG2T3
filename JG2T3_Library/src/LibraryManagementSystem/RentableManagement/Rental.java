package LibraryManagementSystem.RentableManagement;

import java.util.Date;

public class Rental {
	private Date startDate;
	private Date endDate;
	private int timesRenewed;
	private int SKU;
	private int userId;
	
	/**
	 * Creates a rental
	 * @param SKU The SKU of the Rentable being rented out
	 * @param start When the Rental is created
	 * @param end When the Rentable will need to be returned
	 */
	Rental(int SKU, Date start, Date end, int userId, int timesRenewed){
		this.SKU = SKU;
		this.startDate = start;
		this.endDate = end;
		this.userId = userId;
		this.timesRenewed = timesRenewed;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @return the sKU
	 */
	public int getSKU() {
		return SKU;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}
	
	/**
	 * @return the times this Rental has been renewed
	 */
	public int getTimesRenewed() {
		return timesRenewed;
	}


	public enum RenewResult {
		RenewSuccess,
		RenewLimitReached
	}
	
	/**
	 * Renews the Rental. 
	 */
	public RenewResult renew() {
		if (timesRenewed < 5) {
			return RenewResult.RenewLimitReached;
		}
		// TODO: change end time
		this.timesRenewed += 1;
		return RenewResult.RenewSuccess;
	}
	
	
}
