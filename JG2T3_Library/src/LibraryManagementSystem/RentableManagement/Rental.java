package LibraryManagementSystem.RentableManagement;

import java.time.LocalDate;

public class Rental {
	private LocalDate startDate;
	private LocalDate endDate;
	private int timesRenewed;
	private String rentableId;
	private String userId;
	
	/**
	 * Creates a rental
	 * @param SKU The SKU of the Rentable being rented out
	 * @param start When the Rental is created
	 * @param end When the Rentable will need to be returned
	 */
	Rental(String rentableId, LocalDate start, LocalDate end, String userId, int timesRenewed){
		this.rentableId = rentableId;
		this.startDate = start;
		this.endDate = end;
		this.userId = userId;
		this.timesRenewed = timesRenewed;
	}

	/**
	 * @return the startDate
	 */
	public LocalDate getStartDate() {
		return startDate;
	}

	/**
	 * @return the endDate
	 */
	public LocalDate getEndDate() {
		return endDate;
	}

	/**
	 * @return the sKU
	 */
	public String getRentableId() {
		return rentableId;
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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((rentableId == null) ? 0 : rentableId.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + timesRenewed;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rental other = (Rental) obj;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (rentableId == null) {
			if (other.rentableId != null)
				return false;
		} else if (!rentableId.equals(other.rentableId))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (timesRenewed != other.timesRenewed)
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
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
