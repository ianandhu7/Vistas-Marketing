/**
 * Test
 */
package co.vistafoundation.vlearning.user.dto;

/**
 * Test
 */
public class CheckSMSCredits {
	
	private String balance;
	private Boolean isLowBalance;

	/**
	 * @return the balance
	 */
	public String getBalance() {
		return balance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(String balance) {
		this.balance = balance;
	}

	/**
	 * @return the isLowBalance
	 */
	public Boolean getIsLowBalance() {
		return isLowBalance;
	}

	/**
	 * @param isLowBalance the isLowBalance to set
	 */
	public void setIsLowBalance(Boolean isLowBalance) {
		this.isLowBalance = isLowBalance;
	}

	/**
	 * @param balance
	 * @param isLowBalance
	 */
	public CheckSMSCredits(String balance, Boolean isLowBalance) {
		super();
		this.balance = balance;
		this.isLowBalance = isLowBalance;
	}

	/**
	 * 
	 */
	public CheckSMSCredits() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "CheckSMSCredits [balance=" + balance + ", isLowBalance=" + isLowBalance + "]";
	}

}
