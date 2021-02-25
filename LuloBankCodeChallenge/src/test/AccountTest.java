package test;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import main.*;

class AccountTest {
	
	//This test adding an account with an id that another initialized account have.
	@Test
	void accountAlreadyInitialized() {
		MainClass mainClass = new MainClass(true);
		Account account = new Account(1,true,100);
		mainClass.createNewAccount(account);
		Account newAccount = new Account(1,true,100);
		int newAccountId = newAccount.getId();
		Account alreadyInitialized = mainClass.getAccountById(newAccountId);
		Assert.assertNotNull(alreadyInitialized);
	}
	
	//This test checking if the card is active
	@Test
	void cartNotActive() {
		MainClass mainClass = new MainClass(true);
		Account account = new Account(1,false,100);
		boolean isActive = mainClass.checkIfCardActive(account);
		Assert.assertEquals(false, isActive);
	}

}
