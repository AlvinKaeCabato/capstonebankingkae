package casesPack;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.ITestResult;

import com.relevantcodes.extentreports.ExtentReports;


import callPack.SetupEnv;
import utilsPack.ExtentReportsUtil;
import callPack.BaseMethods;
import pagesPack.AddTransactionPage;
import pagesPack.CreateAccountPage;
import pagesPack.LoginPage;
import pagesPack.SplashPage;
import pagesPack.UserPage;
import pagesPack.ViewAccountPage;
import pagesPack.ViewTransactionPage;

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
	@Test
	public void BAA_01() throws Exception{
		String BAA01_Ver = "";
		ExtentReportsUtil.logstep("BAA_01 - Create Account - " + driver.getCapabilities().getCapability("deviceModel"));
		BaseMethods bm = new BaseMethods(driver);
		//Step 1 - Setup run to launch app before class is started
		ExtentReportsUtil.info("Launching App");
		
		//Step 2 - Create an account
		bm.clickElement(SplashPage.createAccount);
		ExtentReportsUtil.info("Create account clicked");
		bm.sendTextToElement(CreateAccountPage.nameText, configProp.getProperty("user1name"));
		ExtentReportsUtil.info("Get First user's name and place in the text box");
		bm.sendTextToElement(CreateAccountPage.add1Text, configProp.getProperty("user1add1"));
		ExtentReportsUtil.info("Get First user's address 1st line and place in the text box");
		bm.sendTextToElement(CreateAccountPage.add2Text, configProp.getProperty("user1add2"));
		ExtentReportsUtil.info("Get First user's address 2nd line and place in the text box");
		bm.sendTextToElement(CreateAccountPage.accNumText, configProp.getProperty("user1acc"));
		ExtentReportsUtil.info("Get First user's acc name and place in the text box");
		bm.sendTextToElement(CreateAccountPage.pinNumText, configProp.getProperty("user1pin"));
		ExtentReportsUtil.info("Get First user's pin number and place in the text box");
		bm.sendTextToElement(CreateAccountPage.currBalText, configProp.getProperty("user1currbal"));
		ExtentReportsUtil.info("Get First user's current balance and place in the text box");
		bm.clickElement(CreateAccountPage.saveUserBtn);
		ExtentReportsUtil.info("Save all inputs - Create new account");
		mobileAlertHandle();
		ExtentReportsUtil.info("Handle Save Alert");
		
		//Step 3 - Login using mpin
		bm.clickElement(SplashPage.loginApp);
		ExtentReportsUtil.info("Click on login");
		bm.sendTextToElement(LoginPage.enterPin,configProp.getProperty("user1pin"));
		ExtentReportsUtil.info("Use the 1st User's pin");
		bm.clickElement(LoginPage.loginBtn);
		ExtentReportsUtil.info("Login");
		
		//Step 4 - Click on Account informations
		//accInfo().click();
		bm.clickElement(UserPage.accInfo);
		ExtentReportsUtil.info("Click on Account Info");
		
		//Step 5 - Validate values from the page is equal to the input items
		bm.verifyDataIsSame(ViewAccountPage.nameView, configVer.getProperty("user1name"));
		bm.verifyDataIsSame(ViewAccountPage.add1View, configVer.getProperty("user1add1"));
		bm.verifyDataIsSame(ViewAccountPage.add2View, configVer.getProperty("user1add2"));
		bm.verifyDataIsSame(ViewAccountPage.accNumView, configVer.getProperty("user1acc"));
		bm.verifyDataIsSame(ViewAccountPage.pinNumView, configVer.getProperty("user1pin"));
		Float f = Float.parseFloat(bm.getTextFromElement(ViewAccountPage.currBalView));
		Float g = Float.parseFloat(configVer.getProperty("user1currbal"));
		bm.verifyDataIsSame(f, g);
		if(fail==0) {
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
		//logout().click();
		bm.clickElement(UserPage.logout);
		ExtentReportsUtil.info("Logging out");	
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
	@Test
	public void BAA_02() throws Exception{
		ExtentReportsUtil.logstep("BAA_02 - transaction check - " + driver.getCapabilities().getCapability("deviceModel"));
		//Step 1 - App is already launch. Closing the app from the previous TC causes data to be not present
		BaseMethods bm = new BaseMethods(driver);
		//Step 2 - Login into account
		bm.clickElement(SplashPage.loginApp);
		ExtentReportsUtil.info("Click on login");
		bm.sendTextToElement(LoginPage.enterPin, configProp.getProperty("user1pin"));
		ExtentReportsUtil.info("Enter Pin");
		bm.clickElement(LoginPage.loginBtn);
		ExtentReportsUtil.info("Send pin");
		
		//Step 3 - Click on Add Transaction
		bm.clickElement(UserPage.addTranc);
		ExtentReportsUtil.info("Click on add Transaction");
		
		//Step 4 - Edit information for transfer
		bm.sendTextToElement(AddTransactionPage.editTextDesc,configVer.getProperty("user1trandesc"));
		ExtentReportsUtil.info("Send keys for description");
		bm.sendTextToElement(AddTransactionPage.editTextAmt,configVer.getProperty("user1trancamt"));
		ExtentReportsUtil.info("Send keys for transaction amount");
		bm.clickElement(AddTransactionPage.addTrancButton);
		ExtentReportsUtil.info("Click on Transaction");
		
		//Step 5 - Returning after clicking on Alert
		mobileAlertHandle();
		ExtentReportsUtil.info("Accepting Alert");
		Thread.sleep(5000);
		
		//Step 6 - View transaction
		bm.clickElement(UserPage.viewTranc);
		ExtentReportsUtil.info("Click on login");
		
		//Step 7 - Validate info on page to input items
		String s = bm.getTextFromElement(ViewTransactionPage.viewTrancText);
		String[] x = s.split("\\n");
		String desc, amt;
		desc = x[0];
		amt = x[1];

		String BAA02_Ver = "";

		bm.verifyDataIsSame(desc, configProp.getProperty("user1trandesc"));
		bm.verifyDataIsSame(amt, configProp.getProperty("user1trancamt"));
		if(fail==0) {
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
		bm.clickElement(UserPage.logout);
		tearDown();
		ExtentReportsUtil.info("Logout and teardown");
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
	@Test
	public void BAA_03() throws Exception{
		ExtentReportsUtil.logstep("BAA_03 - Combined Use Case - " + driver.getCapabilities().getCapability("deviceModel"));
		//Step 1 - Launch Banking App with Setup
		BaseMethods bm = new BaseMethods(driver);
		setUp();
		ExtentReportsUtil.info("Reopening App");
		
		//Step 2 - Create another account 
		bm.clickElement(SplashPage.createAccount);
		ExtentReportsUtil.info("Create account clicked");
		bm.sendTextToElement(CreateAccountPage.nameText, configProp.getProperty("user2name"));
		ExtentReportsUtil.info("Get Second user's name and place in the text box");
		bm.sendTextToElement(CreateAccountPage.add1Text, configProp.getProperty("user2add1"));
		ExtentReportsUtil.info("Get Second user's address 1st line and place in the text box");
		bm.sendTextToElement(CreateAccountPage.add2Text, configProp.getProperty("user2add2"));
		ExtentReportsUtil.info("Get Second user's address 2nd line and place in the text box");
		bm.sendTextToElement(CreateAccountPage.accNumText, configProp.getProperty("user2acc"));
		ExtentReportsUtil.info("Get Second user's acc name and place in the text box");
		bm.sendTextToElement(CreateAccountPage.pinNumText, configProp.getProperty("user2pin"));
		ExtentReportsUtil.info("Get Second user's pin number and place in the text box");
		bm.sendTextToElement(CreateAccountPage.currBalText, configProp.getProperty("user2currbal"));
		ExtentReportsUtil.info("Get Second user's current balance and place in the text box");
		bm.clickElement(CreateAccountPage.saveUserBtn);
		ExtentReportsUtil.info("Save all inputs - Create new account");
		mobileAlertHandle();
		ExtentReportsUtil.info("Handle Save Alert");
		
		//Step 3 - Login app using created account
		bm.clickElement(SplashPage.loginApp);
		ExtentReportsUtil.info("Click on login");
		bm.sendTextToElement(LoginPage.enterPin,configProp.getProperty("user2pin"));
		ExtentReportsUtil.info("Use the 2nd User's pin");
		bm.clickElement(LoginPage.loginBtn);
		ExtentReportsUtil.info("Login");

		//Step 4 - Transfer money on add transaction
		bm.clickElement(UserPage.addTranc);
		ExtentReportsUtil.info("Click on add Transaction");
		bm.sendTextToElement(AddTransactionPage.editTextDesc,configVer.getProperty("user2trandesc"));
		ExtentReportsUtil.info("Send keys for description");
		bm.sendTextToElement(AddTransactionPage.editTextAmt,configVer.getProperty("user2trancamt"));
		ExtentReportsUtil.info("Send keys for transaction amount");
		bm.clickElement(AddTransactionPage.addTrancButton);
		ExtentReportsUtil.info("Click on Transaction");
		
		//Step 5 - Return by handling the Alert
		mobileAlertHandle();
		ExtentReportsUtil.info("Handle the alert and return");
		Thread.sleep(5000);
		
		//Step 6 - Click on View Transaction
		bm.clickElement(UserPage.viewTranc);
		ExtentReportsUtil.info("Click on view transaction");
		
		//Step 7 - Validate items on page are same as input items
		String s = bm.getTextFromElement(ViewTransactionPage.viewTrancText);
		String[] x = s.split("\\n");
		String desc, amt;
		desc = x[0];
		amt = x[1];
		
		String BAA03_Ver = "";
		bm.verifyDataIsSame(desc, configProp.getProperty("user2trandesc"));
		bm.verifyDataIsSame(amt, configProp.getProperty("user2trancamt"));
		
		//logging
		dataLines.add(new String[] {
				"BAA_03",
				BAA03_Ver
		});
		dataLines.add(new String[] {
				"Timestop end",
				java.time.LocalDateTime.now().toString()
		});
		
		if(fail==0) {
			BAA03_Ver = "Passed";
		}else {
			BAA03_Ver = "Failed";
		}
	}
	
}
