package main;

//Imports
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//Class that host the main method. 
//This class gets all the logic to manage all the business rules.
public class MainClass {

	//Gson library to parse JSON to Java Objects and viceversa
	private static final Gson gson = new GsonBuilder()
			.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES)
			.create();
	private ArrayList<Account> accounts = new ArrayList<Account>();;

	public static void main(String[] args) {
		new MainClass();
	}

	//Constructor for normal use via stdin
	//Waits for every line in stdin and parse it to JSON
	//If is a valid JSON start the logic of the business rules.
	public MainClass() {
		Scanner sc = new Scanner(System.in); 
		while(sc.hasNextLine()){
			String jsonString = sc.nextLine();
			if(!jsonString.isEmpty()) {
				try {
					ConsoleInput inputObject = gson.fromJson(jsonString, ConsoleInput.class);
					System.out.println("{\"account\":" + checkInputObject(inputObject));
				} catch(Exception e) {
					System.out.println("The input was not a JSON valid format, please check");
				}

			}

		}
	}

	//Constructor for testing purposes
	public MainClass(boolean testing){

	}



	/**
	 * All the logic of the business rules. Returns a String in JSON format from Object parse.
	 * 
	 * @param inputObject
	 *     allowed object is
	 *     {@link ConsoleInput }
	 *
	 * @return 
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	private String checkInputObject(ConsoleInput inputObject) {
		Account newAccount = inputObject.getAccount();
		Transaction newTransaction = inputObject.getTransaction();
		if(newAccount != null) {
			int newAccountId = newAccount.getId();
			Account existingAccount = getAccountById(newAccountId);
			if(!(existingAccount instanceof Account)) {
				createNewAccount(newAccount);
				return gson.toJson(newAccount);
			} else {
				addViolationToAccount(existingAccount, "account-already-initialized");
				return gson.toJson(existingAccount);
			}
		} else {
			int accountId = newTransaction.getAccountId();
			Account existingAccount = getAccountById(accountId);
			if(!(existingAccount instanceof Account)) {
				Account notInitializedAccount = accountNotInitialized(accountId);
				return gson.toJson(notInitializedAccount);
			} else {
				if(!checkIfCardActive(existingAccount)) addViolationToAccount(existingAccount, "card-not-active");
				else if(checkHighFrequencySmallInterval(newTransaction)) addViolationToAccount(existingAccount, "high-frequency-small-interval​​");
				else if(checkSimilarTransactions(newTransaction)) addViolationToAccount(existingAccount, "doubled-transaction​​");
				else if(!checkIfAvailableLimit(existingAccount,newTransaction)) addViolationToAccount(existingAccount, "insufficient-limit​​");
				return gson.toJson(existingAccount);
			}
		}
	}

	/**
	 * Finds an account based on the id
	 * 
	 * @param accountId
	 *     allowed object is
	 *     {@link Integer }
	 * @return
	 *     possible object is
	 *     {@link Account }
	 *     
	 */
	public Account getAccountById(int accountId) {
		Account resp = null;
		for(int accountIterator = 0; accountIterator < accounts.size() && resp == null ;accountIterator++) {
			int id = accounts.get(accountIterator).getId();
			if(id == accountId) resp = accounts.get(accountIterator);
		}
		return resp;
	}

	/**
	 * creates a new account
	 * 
	 * @param account
	 *     allowed object is
	 *     {@link Account }
	 *     Account to create
	 *     
	 */
	public void createNewAccount(Account account) {
		account.setViolations(new ArrayList<String>());
		account.setTransactions(new ArrayList<Transaction>());
		accounts.add(account);
	}

	/**
	 * Create a violation when the account is not initialized
	 * 
	 * @param accountId
	 *     allowed object is
	 *     {@link Integer }
	 * @return 
	 * 	   possible object is
	 *     {@link Account }
	 *     
	 */
	public Account accountNotInitialized(int accountId) {
		Account notInitializedAccount = new Account(accountId, false, 0);
		notInitializedAccount.setViolations(new ArrayList<String>());
		ArrayList<String> violations = notInitializedAccount.getViolations();
		violations.add("account-not-initialized");
		notInitializedAccount.setViolations(violations);
		return notInitializedAccount;
	}

	/**
	 * Checks if the card is active
	 * 
	 * @param account
	 *     allowed object is
	 *     {@link Account }
	 * @return boolean
	 * 	   possible object is
	 *     {@link boolean }
	 *     
	 */
	public boolean checkIfCardActive(Account account) {
		return account.isActiveCard();
	}

	/**
	 * Add a violation to the account
	 * 
	 * @param account
	 *     allowed object is
	 *     {@link Account }
	 * @param violation
	 *     allowed object is
	 *     {@link String }  
	 */	
	public void addViolationToAccount(Account account, String violation) {
		ArrayList<String> violations = new ArrayList<String>();
		violations.add(violation);
		account.setViolations(violations);
	}

	/**
	 * Checks if in a 2 minutes interval are 3 transactions  
	 * 
	 * @param transaction
	 *     allowed object is
	 *     {@link Transaction }
	 * @return 
	 * 		possible object is 
	 *     {@link boolean }
	 *    
	 */	
	public boolean checkHighFrequencySmallInterval(Transaction newTransaction) {
		boolean resp = false;
		int numberOfTransactionsInHighFrequency = 0;
		Date newTransactionTime = newTransaction.getTime();
		for(int accountIterator = 0; accountIterator < accounts.size() && !resp ;accountIterator++) {
			ArrayList<Transaction> transactions = accounts.get(accountIterator).getTransactions();
			for(int transactionIterator = 0; transactionIterator < transactions.size() && !resp ;transactionIterator++) {
				Date transactionTime = transactions.get(transactionIterator).getTime();
				long diffInMillies = Math.abs(transactionTime.getTime() - newTransactionTime.getTime());
				long diff = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
				if(diff < 2) numberOfTransactionsInHighFrequency++;
				if(numberOfTransactionsInHighFrequency > 1) resp = true;
			}
		}
		return resp;
	}


	/**
	 * Checks if 2 transactions have same amount and merchant in a 2 minutes interval.
	 * 
	 * @param transaction
	 *     allowed object is
	 *     {@link Transaction }
	 * @return 
	 * 	   possible object is 
	 *     {@link boolean }
	 *    
	 */	
	public boolean checkSimilarTransactions(Transaction newTransaction) {
		boolean resp = false;
		String newTransactionMerchant = newTransaction.getMerchant();
		Integer newTransactionAmount = newTransaction.getAmount();
		Date newTransactionTime = newTransaction.getTime();
		for(int accountIterator = 0; accountIterator < accounts.size() && !resp ;accountIterator++) {
			ArrayList<Transaction> transactions = accounts.get(accountIterator).getTransactions();
			for(int transactionIterator = 0; transactionIterator < transactions.size() && !resp ;transactionIterator++) {
				Date transactionTime = transactions.get(transactionIterator).getTime();
				long diffInMillies = Math.abs(transactionTime.getTime() - newTransactionTime.getTime());
				long diff = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
				String transactionMerchant = transactions.get(transactionIterator).getMerchant();
				Integer transactionAmount = transactions.get(transactionIterator).getAmount();
				if(newTransactionMerchant.equals(transactionMerchant) && newTransactionAmount.equals(transactionAmount) && diff < 2) resp = true;
			}
		}
		return resp;
	}

	/**
	 * Checks if the account can afford the transaction checking the available limit.
	 * 
	 * @param account
	 *     allowed object is
	 *     {@link Account }
	 * @param transaction
	 *     allowed object is
	 *     {@link Transaction }
	 * @return 
	 * 	   possible object is
	 *     {@link boolean }
	 *    
	 */	
	public boolean checkIfAvailableLimit(Account account, Transaction transaction) {
		int newLimit = getNewAvailableLimit(account, transaction);
		if(newLimit > 0){
			account.setAvailableLimit(newLimit);
			addTransactionToAccount(account, transaction);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns the accounts new available limit according to the transaction amount 
	 * 
	 * @param account
	 *     allowed object is
	 *     {@link Account }
	 * @param transaction
	 *     allowed object is
	 *     {@link Transaction }
	 * @return
	 * 		possible object is 
	 *     {@link Integer }
	 *    
	 */
	public int getNewAvailableLimit(Account account, Transaction transaction) {
		int availableLimit = account.getAvailableLimit();
		int newLimit = availableLimit - transaction.getAmount();
		return newLimit;
	}

	/**
	 * Adds a transaction to the account.
	 * 
	 * @param account
	 *     allowed object is
	 *     {@link Account }
	 * @param transaction
	 *     allowed object is
	 *     {@link Transaction }
	 */
	public void addTransactionToAccount(Account account, Transaction transaction) {
		account.addTransaction(transaction);
	}
}
