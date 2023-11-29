package casesPack;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;


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
		ExtentTest baa01 = report.createTest("BAA_01");
		//Step 1 - Setup run to launch app before class is started
		
		//Step 2 - Create an account
		createAccount().click();
		baa01.log(Status.INFO,"Create account clicked");
		nameText().sendKeys(configProp.getProperty("user1name"));
		baa01.log(Status.INFO,"Get First user's name and place in the text box");
		add1Text().sendKeys(configProp.getProperty("user1add1"));
		baa01.log(Status.INFO,"Get First user's address 1st line and place in the text box");
		add2Text().sendKeys(configProp.getProperty("user1add2"));
		baa01.log(Status.INFO,"Get First user's address 2nd line and place in the text box");
		accNumText().sendKeys(configProp.getProperty("user1acc"));
		baa01.log(Status.INFO,"Get First user's acc name and place in the text box");
		pinNumText().sendKeys(configProp.getProperty("user1pin"));
		baa01.log(Status.INFO,"Get First user's pin number and place in the text box");
		currBalText().sendKeys(configProp.getProperty("user1currbal"));
		baa01.log(Status.INFO,"Get First user's current balance and place in the text box");
		saveUserBtn().click();
		baa01.log(Status.INFO,"Save all inputs - Create new account");
		mobileAlertHandle();
		baa01.log(Status.INFO,"Handle Save Alert");
		
		//Step 3 - Login using mpin
		loginApp().click();	
		baa01.log(Status.INFO,"Click on login");
		enterPin().sendKeys(configProp.getProperty("user1pin"));
		baa01.log(Status.INFO,"Use the 1st User's pin");
		loginBtn().click();
		baa01.log(Status.INFO,"Login");
		
		//Step 4 - Click on Account informations
		accInfo().click();
		baa01.log(Status.INFO,"Click on Account Info");
		
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
			baa01.log(Status.PASS,"Account information page is the same with input items");
		}else {
			BAA01_Ver = "Failed";
			baa01.log(Status.FAIL,"Account information page does not meet input items");
			baa01.fail(MediaEntityBuilder.createScreenCaptureFromPath("img.png").build());
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
		baa01.log(Status.INFO,"Logging out");
		
		
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
		ExtentTest baa02 = report.createTest("BAA_02");
		//Step 1 - App is already launch. Closing the app from the previous TC causes data to be not present
		
		//Step 2 - Login into account
		loginApp().click();	
		baa02.log(Status.INFO,"Click on login");
		enterPin().sendKeys(configProp.getProperty("user1pin"));
		baa02.log(Status.INFO,"Enter Pin");
		loginBtn().click();
		baa02.log(Status.INFO,"Send pin");
		
		//Step 3 - Click on Add Transaction
		addTranc().click();
		baa02.log(Status.INFO,"Click on add Transaction");
		
		//Step 4 - Edit information for transfer
		editTextDesc().sendKeys(configProp.getProperty("user1trandesc"));
		baa02.log(Status.INFO,"Send keys for description");
		editTextAmt().sendKeys(configProp.getProperty("user1trancamt"));
		baa02.log(Status.INFO,"Send keys for transaction amount");
		addTrancBtn().click();
		baa02.log(Status.INFO,"Click on Transaction");
		
		//Step 5 - Returning after clicking on Alert
		mobileAlertHandle();
		baa02.log(Status.INFO,"Accepting Alert");
		Thread.sleep(5000);
		
		//Step 6 - View transaction
		viewTranc().click();
		baa02.log(Status.INFO,"Click on login");
		
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
			baa02.log(Status.PASS,"Verified input desc and amount is same in this page");
		}else {
			BAA02_Ver = "Failed";
			baa02.log(Status.FAIL,"Failed to verify input description and amount is the same as in this page");
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
		baa02.log(Status.INFO,"Logout and teardown");
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
		ExtentTest baa03 = report.createTest("BAA_03");
		//Step 1 - Launch Banking App with Setup
		setUp();
		baa03.log(Status.INFO,"Reopening App");
		
		//Step 2 - Create another account 
		createAccount().click();
		baa03.log(Status.INFO,"Click create account");
		nameText().sendKeys(configProp.getProperty("user2name"));
		baa03.log(Status.INFO,"Send keys for user2's name");
		add1Text().sendKeys(configProp.getProperty("user2add1"));
		baa03.log(Status.INFO,"Send keys for user2's address line 1");
		add2Text().sendKeys(configProp.getProperty("user2add2"));
		baa03.log(Status.INFO,"Send keys for user2's address line 2");
		accNumText().sendKeys(configProp.getProperty("user2acc"));
		baa03.log(Status.INFO,"Send keys for user2's account number");
		pinNumText().sendKeys(configProp.getProperty("user2pin"));
		baa03.log(Status.INFO,"Send keys for user2's pin ");
		currBalText().sendKeys(configProp.getProperty("user2currbal"));
		baa03.log(Status.INFO,"Send keys for user2's current balance");
		saveUserBtn().click();
		baa03.log(Status.INFO,"Click on Save");
		mobileAlertHandle();
		baa03.log(Status.INFO,"Handle Alert to continue");
		
		//Step 3 - Login app using created account
		loginApp().click();	
		baa03.log(Status.INFO,"Click on Login");
		enterPin().sendKeys(configProp.getProperty("user2pin"));
		baa03.log(Status.INFO,"Send keys for pin login");
		loginBtn().click();
		baa03.log(Status.INFO,"Logging in");

		//Step 4 - Transfer money on add transaction
		addTranc().click();
		baa03.log(Status.INFO,"Click on add transaction");
		editTextDesc().sendKeys(configProp.getProperty("user2trandesc"));
		baa03.log(Status.INFO,"Send keys for user2's transaction description");
		editTextAmt().sendKeys(configProp.getProperty("user2trancamt"));
		baa03.log(Status.INFO,"Send keys for user2's transaction amount");
		addTrancBtn().click();
		baa03.log(Status.INFO,"Click on add transaction button");
		
		//Step 5 - Return by handling the Alert
		mobileAlertHandle();
		baa03.log(Status.INFO,"Handle the alert and return");
		Thread.sleep(5000);
		
		//Step 6 - Click on View Transaction
		viewTranc().click();
		baa03.log(Status.INFO,"Click on view transaction");
		
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
			baa03.log(Status.PASS,"Verified input desc and amount is same in this page");
		}else {
			BAA03_Ver = "Failed";
			baa03.log(Status.FAIL,"Verified input desc and amount is not the same in this page");
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
		report.flush();
	}
	
}
