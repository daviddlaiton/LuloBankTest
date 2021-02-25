package main;

import java.util.Date;
import com.google.gson.annotations.SerializedName;

//Class that represents the Transaction.
public class Transaction {
	
	//Gson tag to identify the name comming from the JSON.
	@SerializedName("id")
	private int accountId;
	private String merchant;
	private int amount;
	private Date time;
	
	public Transaction(int accountId, String merchant, int amount, Date time) {
		this.accountId = accountId;
		this.merchant = merchant;
		this.amount = amount;
		this.time = time;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
