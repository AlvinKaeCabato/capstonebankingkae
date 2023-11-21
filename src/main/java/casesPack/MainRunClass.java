package casesPack;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import callPack.SetupEnv;

public class MainRunClass extends SetupEnv{
	
	/*
	 * BAA_01
	 * ​Step 1: Launch BankingApp  using .apk file. 
	 * Step 2: Create an account  
	 * Step 3: Login into account using valid mpin 
	 * Step 4: Click on Details  
	 * Step 5: Validated all the details are same as entered. 
	 * 
	 */
	@Test(priority=1)
	public void BAA_01(){
		//Step 1 - Setup run to launch app before class is started
		
		//Step 2 - Create an account
		createAccount().click();
		nameText().sendKeys("Alvin Kae Cabato");
		add1Text().sendKeys("96 Nordstrum Residences");
		add2Text().sendKeys("Quezon City");
		accNumText().sendKeys("123321");
		pinNumText().sendKeys("9231");
		currBalText().sendKeys("15992.0");
		saveUserBtn().click();
		mobileAlertHandle();
		
		//Step 3 - Login using mpin
		loginApp().click();	
		enterPin().sendKeys("9231");
		loginBtn().click();
		
		//Step 4 - Click on Account informations
		accInfo().click();
		
		//Step 5 - Validate values from the page is equal to the inputted items
		Assert.assertEquals(nameView().getText(),"Alvin Kae Cabato");
		Assert.assertEquals(add1View().getText(),"96 Nordstrum Residences");
		Assert.assertEquals(add2View().getText(),"Quezon City");
		Assert.assertEquals(accNumView().getText(),"123321");
		Assert.assertEquals(pinNumView().getText(),"9231");
		Assert.assertEquals(currBalView().getText(),"15992.0");	
		
		//Clean up step - Logging out
		navigateBack();
		logout().click();
		
	}
	
	/*
	 * BAA_02
	 * Step 1: Launch BankingApp app using .apk file. 
	 * Step 2: Login into account using valid pin 
	 * Step 3: Click on Add Transaction 
	 * Step 4: Transfer money with a valid description. 
	 * Step 5: Click on back  
	 * Step 6: Click on View transaction 
	 * Step 7: Validate the  transaction description made is present 
	 */
	@Test(priority=2)
	public void BAA_02() throws Exception{
		//Step 1 - App is already launch. Closing the app from the previous TC causes data to be not present
		
		//Step 2 - Login into account
		loginApp().click();	
		enterPin().sendKeys("9231");
		loginBtn().click();
		
		//Step 3 - Click on Add Transaction
		addTranc().click();
		
		//Step 4 - Edit information for transfer
		editTextDesc().sendKeys("For Loan");
		editTextAmt().sendKeys("5555");
		addTrancBtn().click();
		
		//Step 5 - Returning after clicking on Alert
		mobileAlertHandle();
		Thread.sleep(5000);
		
		//Step 6 - View transaction
		viewTranc().click();
		
		//Step 7 - Validate info on page to input items
		String s = viewTrancText().getText();
		String[] x = s.split("\\n");
		String desc, amt;
		desc = x[0];
		amt = x[1];
		Assert.assertEquals(desc,"For Loan");
		Assert.assertEquals(amt, "5555");
		
		//Clean up Step - Logout and tear down
		navigateBack();
		logout().click();
		tearDown();
	}
	
	/*
	 * BAA_03
	 * ​Step 1: Launch BankingApp  using .apk file. 
	 * Step 2: Create a 2nd account  
	 * Step 3: Login into account using valid mpin 
	 * Step4:  Click on Add Transaction 
	 * Step 4: Transfer money with a valid description. 
	 * Step 5: Click on back  
	 * Step 6: Click on View transaction 
	 * Step 7: Validate the  transaction description from only current user should be present.  
	 */
	@Test(priority=3)
	public void BAA_03() throws Exception{
		//Step 1 - Launch Banking App with Setup
		setUp();
		
		//Step 2 - Create another account 
		createAccount().click();
		nameText().sendKeys("Alvin Jae Cabato");
		add1Text().sendKeys("211 Macapagal Street");
		add2Text().sendKeys("Paranaque City");
		accNumText().sendKeys("112411");
		pinNumText().sendKeys("4522");
		currBalText().sendKeys("153141.0");
		saveUserBtn().click();
		mobileAlertHandle();
		
		//Step 3 - Login app using created account
		loginApp().click();	
		enterPin().sendKeys("4522");
		loginBtn().click();

		//Step 4 - Transfer money on add transaction
		addTranc().click();
		editTextDesc().sendKeys("For Payment");
		editTextAmt().sendKeys("2432");
		addTrancBtn().click();
		
		//Step 5 - Return by handling the Alert
		mobileAlertHandle();
		Thread.sleep(5000);
		
		//Step 6 - Click on View Transaction
		viewTranc().click();
		
		//Step 7 - Validate items on page are same as input items
		String s = viewTrancText().getText();
		String[] x = s.split("\\n");
		String desc, amt;
		desc = x[0];
		amt = x[1];
		Assert.assertEquals(desc,"For Payment");
		Assert.assertEquals(amt, "2432");
	}
	
}
