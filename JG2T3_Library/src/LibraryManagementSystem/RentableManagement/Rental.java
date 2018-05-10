package LibraryManagementSystem.RentableManagement;

import java.time.Instant;

public class Rental {
	private Instant startDate;
	private Instant endDate;
	private int timesRenewed;
	private int SKU;
	private String userId;
	
	/**
	 * Creates a rental
	 * @param SKU The SKU of the Rentable being rented out
	 * @param start When the Rental is created
	 * @param end When the Rentable will need to be returned
	 */
	Rental(int SKU, Instant start, Instant end, String userId, int timesRenewed){
		this.SKU = SKU;
		this.startDate = start;
		this.endDate = end;
		this.userId = userId;
		this.timesRenewed = timesRenewed;
	}

	/**
	 * @return the startDate
	 */
	public Instant getStartDate() {
		return startDate;
	}

	/**
	 * @return the endDate
	 */
	public Instant getEndDate() {
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
	public String getUserId() {
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
