package casesPack;

import java.io.PrintWriter;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.lang.*;

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
	public void BAA_01() throws Exception{
		//Step 1 - Setup run to launch app before class is started
		
		//Step 2 - Create an account
		createAccount().click();
		nameText().sendKeys(configProp.getProperty("user1name"));
		add1Text().sendKeys(configProp.getProperty("user1add1"));
		add2Text().sendKeys(configProp.getProperty("user1add2"));
		accNumText().sendKeys(configProp.getProperty("user1acc"));
		pinNumText().sendKeys(configProp.getProperty("user1pin"));
		currBalText().sendKeys(configProp.getProperty("user1currbal"));
		saveUserBtn().click();
		mobileAlertHandle();
		
		//Step 3 - Login using mpin
		loginApp().click();	
		enterPin().sendKeys(configProp.getProperty("user1pin"));
		loginBtn().click();
		
		//Step 4 - Click on Account informations
		accInfo().click();
		
		//Step 5 - Validate values from the page is equal to the input items
		Assert.assertEquals(nameView().getText(),configProp.getProperty("user1name"));
		Assert.assertEquals(add1View().getText(),configProp.getProperty("user1add1"));
		Assert.assertEquals(add2View().getText(),configProp.getProperty("user1add2"));
		Assert.assertEquals(accNumView().getText(),configProp.getProperty("user1acc"));
		Assert.assertEquals(pinNumView().getText(),configProp.getProperty("user1pin"));
		Float f = Float.parseFloat(currBalView().getText());
		Float g = Float.parseFloat(configProp.getProperty("user1currbal"));
		Assert.assertEquals(f,g);
		String BAA01_Ver = "";
		if(nameView().getText().equals(configProp.getProperty("user1name"))
				&& add1View().getText().equals(configProp.getProperty("user1add1"))
				&& add2View().getText().equals(configProp.getProperty("user1add2"))
				&& accNumView().getText().equals(configProp.getProperty("user1acc"))
				&& pinNumView().getText().equals(configProp.getProperty("user1pin"))
				&& f.equals(g)
				) {
			BAA01_Ver = "Passed";
		}else {
			BAA01_Ver = "Failed";
		}
		
		//logging
		dataLines.add(new String[] {
				"Timestop start",
				java.time.LocalDateTime.now().toString()
		});
		dataLines.add(new String[] {
				"BAA_01",
				BAA01_Ver
		});
		
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
		enterPin().sendKeys(configProp.getProperty("user1pin"));
		loginBtn().click();
		
		//Step 3 - Click on Add Transaction
		addTranc().click();
		
		//Step 4 - Edit information for transfer
		editTextDesc().sendKeys(configProp.getProperty("user1trandesc"));
		editTextAmt().sendKeys(configProp.getProperty("user1trancamt"));
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
		Assert.assertEquals(desc,configProp.getProperty("user1trandesc"));
		Assert.assertEquals(amt, configProp.getProperty("user1trancamt"));
		String BAA02_Ver = "";
		if(desc.equals(configProp.getProperty("user1trandesc"))
				&& amt.equals(configProp.getProperty("user1trancamt"))
				) {
			BAA02_Ver = "Passed";
		}else {
			BAA02_Ver = "Failed";
		}
		
		//logging
		dataLines.add(new String[] {
				"BAA_02",
				BAA02_Ver
		});
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
		nameText().sendKeys(configProp.getProperty("user2name"));
		add1Text().sendKeys(configProp.getProperty("user2add1"));
		add2Text().sendKeys(configProp.getProperty("user2add2"));
		accNumText().sendKeys(configProp.getProperty("user2acc"));
		pinNumText().sendKeys(configProp.getProperty("user2pin"));
		currBalText().sendKeys(configProp.getProperty("user2currbal"));
		saveUserBtn().click();
		mobileAlertHandle();
		
		//Step 3 - Login app using created account
		loginApp().click();	
		enterPin().sendKeys(configProp.getProperty("user2pin"));
		loginBtn().click();

		//Step 4 - Transfer money on add transaction
		addTranc().click();
		editTextDesc().sendKeys(configProp.getProperty("user2trandesc"));
		editTextAmt().sendKeys(configProp.getProperty("user2trancamt"));
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
		Assert.assertEquals(desc,configProp.getProperty("user2trandesc"));
		Assert.assertEquals(amt,configProp.getProperty("user2trancamt"));
		
		String BAA03_Ver = "";
		if(desc.equals(configProp.getProperty("user2trandesc"))
				&& amt.equals(configProp.getProperty("user2trancamt"))
				) {
			BAA03_Ver = "Passed";
		}else {
			BAA03_Ver = "Failed";
		}
		
		//logging
		dataLines.add(new String[] {
				"BAA_03",
				BAA03_Ver
		});
		dataLines.add(new String[] {
				"Timestop end",
				java.time.LocalDateTime.now().toString()
		});
	}
	
}
