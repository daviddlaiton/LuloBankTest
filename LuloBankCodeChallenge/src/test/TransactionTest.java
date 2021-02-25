package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import main.*;

class TransactionTest {

	//This test the logic of the account not having limit to process the transaction
	@Test
	void insufficientLimit() {
		try {
		MainClass mainClass = new MainClass(true);
		Account account = new Account(1,true,100);
		String isoDatePattern = "yyyy-MM-dd'T'HH:mm:ss.000'Z'";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
		Date date = simpleDateFormat.parse("2019-02-13T11:00:00.000Z");
		Transaction transaction = new Transaction(1, "Test Merchant", 200, date);
		boolean availableLimit = mainClass.checkIfAvailableLimit(account, transaction);
		Assert.assertEquals(false, availableLimit);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	//This test when 3 transactions arrives in a 2 minute interval.
	@Test
	void highFrequencySmallInterval​​() {
		try {
			MainClass mainClass = new MainClass(true);
			Account account = new Account(1,true,1000);
			mainClass.createNewAccount(account);
			String isoDatePattern = "yyyy-MM-dd'T'HH:mm:ss.000'Z'";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
			Date date1 = simpleDateFormat.parse("2019-02-13T11:00:00.000Z");
			Transaction transaction1 = new Transaction(1, "Test Merchant", 100, date1);
			mainClass.addTransactionToAccount(account, transaction1);
			Date date2 = simpleDateFormat.parse("2019-02-13T11:01:00.000Z");
			Transaction transaction2 = new Transaction(1, "Test Merchant", 100, date2);
			mainClass.addTransactionToAccount(account, transaction2);
			Date date3 = simpleDateFormat.parse("2019-02-13T11:00:30.000Z");
			Transaction transaction3 = new Transaction(1, "Test Merchant", 200, date3);
			boolean highFrequencySmallInterval = mainClass.checkHighFrequencySmallInterval(transaction3);
			Assert.assertEquals(true, highFrequencySmallInterval);
			} catch (ParseException e) {
				e.printStackTrace();
			}
	}
	
	//This test when 2 transactions arrives in a 2 minute interval and have the same amount and merchant.
	@Test
	void doubleTransaction​​() {
		try {
			MainClass mainClass = new MainClass(true);
			Account account = new Account(1,true,1000);
			mainClass.createNewAccount(account);
			String isoDatePattern = "yyyy-MM-dd'T'HH:mm:ss.000'Z'";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
			Date date = simpleDateFormat.parse("2019-02-13T11:00:00.000Z");
			Transaction transaction1 = new Transaction(1, "Test Merchant", 100, date);
			mainClass.addTransactionToAccount(account, transaction1);
			Transaction transaction2 = new Transaction(1, "Test Merchant", 100, date);
			boolean similarTransaction = mainClass.checkSimilarTransactions(transaction2);
			Assert.assertEquals(true, similarTransaction);
			} catch (ParseException e) {
				e.printStackTrace();
			}
	}

}
