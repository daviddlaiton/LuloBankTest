package main;

import java.util.ArrayList;

//Class that represents the account.
public class Account {
	
	private int id;
	private boolean activeCard;
	private int availableLimit;
	private ArrayList<String> violations;
	//The transient is included in order to avoid this attribute to be in the JSON parse from GSON.
	private transient ArrayList<Transaction> transactions = new ArrayList<Transaction>(); 
	
	public Account(int id, boolean activeCard, int availableLimit) {
		this.id = id;
		this.activeCard = activeCard;
		this.availableLimit = availableLimit;
		transactions = new ArrayList<Transaction>();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isActiveCard() {
		return activeCard;
	}
	public void setActiveCard(boolean activeCard) {
		this.activeCard = activeCard;
	}
	public int getAvailableLimit() {
		return availableLimit;
	}
	public void setAvailableLimit(int availableLimit) {
		this.availableLimit = availableLimit;
	}

	public ArrayList<String> getViolations() {
		return violations;
	}

	public void setViolations(ArrayList<String> violations) {
		this.violations = violations;
	}

	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(ArrayList<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	public void addTransaction(Transaction transaction) {
		transactions.add(transaction);
	}

}
